package com.javacourse.stack;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Stack;

/**
 * Реализация стека на базе массива объектов,
 * с возвомжностью поиска наименьшего/наибольшего значения
 */
public class ArrayStack<ItemTypeT extends Comparable<ItemTypeT>> implements ExtremumStack<ItemTypeT> {
	private static final int DEFAULT_LEN = 10;
	private ItemTypeT[] items;
	private int indexItem;
	private Stack<ItemTypeT> maxItems;
	private Stack<ItemTypeT> minItems;

	public ArrayStack() {
		this.indexItem = -1;
		this.maxItems = new Stack<ItemTypeT>();
		this.minItems = new Stack<ItemTypeT>();
	}

	private void expandArray() {
		if (this.indexItem >= items.length)
				this.items = Arrays.copyOf(this.items, this.items.length * 2);
	}

	private void reduceArray() {

		/*
		* Массив уменьшится, если его наполнение составит 25%.
		* Нужно для избежания частого копирования на границе массива.
		*/
		if (this.indexItem >= DEFAULT_LEN / 2 && this.indexItem <= this.items.length / 4) {
			int newLen = this.items.length / 2;
			this.items = Arrays.copyOf(this.items, newLen);
		}
	}

	@Override
	public void push(ItemTypeT item) {
		if (this.indexItem == -1) {
			this.maxItems.push(item);
			this.minItems.push(item);
			this.items = (ItemTypeT[]) Array.newInstance(item.getClass(), DEFAULT_LEN);
		}
		this.indexItem++;
		expandArray();
		this.items[indexItem] = item;


		if (this.indexItem == 0) return;
		if (item != null && this.maxItems.peek().compareTo(item) <= 0) this.maxItems.push(item);
		if (item != null && this.minItems.peek().compareTo(item) >= 0) this.minItems.push(item);
	}

	@Override
	public ItemTypeT pop() {
		if (this.indexItem == -1) throw new RuntimeException("Empty Stack Exception");
		ItemTypeT result = this.items[indexItem];
		this.items[indexItem] = null;
		indexItem--;

		if (result == maxItems.peek()) maxItems.pop();
		if (result == minItems.peek()) minItems.pop();
		reduceArray();
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
