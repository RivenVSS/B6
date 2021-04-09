package com.javacourse.stack;

import java.util.Stack;
/**
 * Реализация стека на базе односвязного списка, с возможностью
 * поиска наибольшего и наименьшего элемента стека
 */
public class LinkedStack<ItemTypeT extends Comparable<ItemTypeT>> implements ExtremumStack<ItemTypeT> {
	private Item<ItemTypeT> top;
	private Stack<ItemTypeT> maxItems;
	private Stack<ItemTypeT> minItems;

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

	/**
	 * Конструктор без аргументов должен создаавать валидный стек
	 */
	public LinkedStack(){
		this.maxItems = new Stack<ItemTypeT>();
		this.minItems = new Stack<ItemTypeT>();
	}

	@Override
	public void push(ItemTypeT item) {
		if (this.top == null) {
			this.maxItems.push(item);
			this.minItems.push(item);
		} else {
			if (item != null && item.compareTo(this.maxItems.peek()) >= 0) this.maxItems.push(item);
			if (item != null && item.compareTo(this.minItems.peek()) <= 0) this.minItems.push(item);
		}
		this.top = new Item<ItemTypeT>(item, this.top);
	}

	@Override
	public ItemTypeT pop() {
		if (this.top == null) throw new RuntimeException("Empty Stack Exception");
		ItemTypeT result = this.top.peek();
		if (!this.top.first()) this.top = this.top.getPrev();
		else this.top = null;

		if (result == maxItems.peek()) maxItems.pop();
		if (result == minItems.peek()) minItems.pop();
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
		return this.minItems.peek();
	}

	@Override
	public ItemTypeT max() {
		if (this.top == null) throw new RuntimeException("Empty Stack Exception");
		return this.maxItems.peek();
	}
}