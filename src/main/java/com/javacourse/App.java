package com.javacourse;


import com.javacourse.stack.ArrayStack;

public class App {
	public static void main(String[] args) {
		ArrayStack<Integer> stack = new ArrayStack<Integer>();
		stack.push(1);
		stack.push(0);
		stack.push(4);
		stack.push(null);
		System.out.println("Test pop: " + stack.pop());
		System.out.println("Test peek: " + stack.peek());
		System.out.println("Max: " + stack.max());
		System.out.println("Min: " + stack.min());
	}
}
