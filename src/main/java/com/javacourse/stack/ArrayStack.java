package com.javacourse.stack;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Реализация стека на базе массива объектов,
 * с возвомжностью поиска наименьшего/наибольшего значения
 */
public class ArrayStack<ItemTypeT extends Comparable<ItemTypeT>> implements ExtremumStack<ItemTypeT> {
	private static final int SEGMENT_LEN = 10;
	private ItemTypeT[] items;
	private int indexItem;
	private LinkedStack<ItemTypeT> maxItems;
	private LinkedStack<ItemTypeT> minItems;

	public ArrayStack() {
		this.indexItem = -1;
		this.maxItems = new LinkedStack<ItemTypeT>();
		this.minItems = new LinkedStack<ItemTypeT>();
	}

	public int compare(ItemTypeT a, ItemTypeT b) {
		return a.compareTo(b);
	}

	@Override
	public void push(ItemTypeT item) {
		if (this.indexItem == -1) {
			this.maxItems.push(item);
			this.minItems.push(item);
			this.items = (ItemTypeT[]) Array.newInstance(item.getClass(), this.SEGMENT_LEN);
		}
		this.indexItem++;
		if (this.indexItem >= items.length)
				this.items = Arrays.copyOf(this.items, this.items.length + this.SEGMENT_LEN);
		this.items[indexItem] = item;
		if (item != null && this.maxItems.peek().compareTo(item) <= 0) this.maxItems.push(item);
		if (item != null && this.minItems.peek().compareTo(item) >= 0) this.minItems.push(item);
	}

	@Override
	public ItemTypeT pop() {
		if (this.indexItem == -1) throw new RuntimeException("Empty Stack Exception");
		if (this.items[indexItem] == maxItems.peek()) maxItems.pop();
		if (this.items[indexItem] == minItems.peek()) minItems.pop();

		ItemTypeT result = this.items[indexItem];
		this.items[indexItem] = null;
		indexItem--;
		if (indexItem == -1) {
			this.maxItems = new LinkedStack<ItemTypeT>();
			this.minItems = new LinkedStack<ItemTypeT>();
		}

		if (this.indexItem < items.length - this.SEGMENT_LEN)
				this.items = Arrays.copyOf(this.items, this.items.length - this.SEGMENT_LEN);
		return result;
	}

	@Override
	public ItemTypeT peek() {
		if (this.indexItem == -1) throw new RuntimeException("Empty Stack Exception");
		return this.items[this.indexItem];
	}

	@Override
	public ItemTypeT min() {
		if (this.indexItem == -1) throw new RuntimeException("Empty Stack Exception");
		return this.minItems.peek();

	}

	@Override
	public ItemTypeT max() {
		if (this.indexItem == -1) throw new RuntimeException("Empty Stack Exception");
		return this.maxItems.peek();
	}
}
