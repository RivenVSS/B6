package com.javacourse.stack;

import java.util.Comparator;
import java.util.ArrayList;
/**
 * Реализация стека на базе односвязного списка, с возможностью
 * поиска наибольшего и наименьшего элемента стека
 */
public class LinkedStack<ItemTypeT extends Comparable<ItemTypeT>> implements ExtremumStack<ItemTypeT> {
	private Item<ItemTypeT> top;
	private ArrayList<Item<ItemTypeT>> items;

	private class Item<ItemTypeT extends Comparable<ItemTypeT>> {
		private ItemTypeT currentItem;
		private Item<ItemTypeT> prevItem;

		private Item(ItemTypeT currentItem, Item<ItemTypeT> prevItem) {
			this.currentItem = currentItem;
			this.prevItem = prevItem;
		}

		protected ItemTypeT peek() {
			return this.currentItem;
		}

		protected Item<ItemTypeT> getPrev() {
			return this.prevItem;
		}

		protected boolean first() {
			return this.prevItem == null;
		}
	}

	private class ItemCompare implements Comparator<Item<ItemTypeT>> {

		@Override
		public int compare(Item<ItemTypeT> a, Item<ItemTypeT> b) {
			if (a.peek() == null) return -1;
			if (b.peek() == null) return 1;
			return a.peek().compareTo(b.peek());
		}
	}

	/**
	 * Конструктор без аргументов должен создаавать валидный стек
	 */
	public LinkedStack(){
		items = new ArrayList<Item<ItemTypeT>>();
	}

	@Override
	public void push(ItemTypeT item) {
		this.top = new Item<ItemTypeT>(item, this.top);

		if (this.top.peek() != null && items.stream().noneMatch(u -> u.peek().equals(this.top.peek())))
			items.add(this.top);

		items.sort(new ItemCompare());
	}

	@Override
	public ItemTypeT pop() {
		if (this.top == null) throw new RuntimeException("Empty Stack Exception");
		ItemTypeT result = this.top.peek();
		if (!this.top.first()) {
			int index = items.indexOf(this.top);
			if (index != -1) {
				items.remove(index);
			}
			this.top = this.top.getPrev();
		} else {
			this.top = null;
			this.items.clear();
		}
		return result;
	}

	@Override
	public ItemTypeT peek() {
		if (this.top == null) throw new RuntimeException("Empty Stack Exception");
		return this.top.peek();
	}

	@Override
	public ItemTypeT min() {
		if (this.top == null) throw new RuntimeException("Empty Stack Exception");
		return items.get(0).peek();
	}

	@Override
	public ItemTypeT max() {
		if (this.top == null) throw new RuntimeException("Empty Stack Exception");
		return items.get(items.size() - 1).peek();
	}
}