package com.dj.utils.fibonacci;

/**
 * Created by Darshan on 8/5/16.
 */
public class Fibonacci {

	int counter = 0;

	public static void main(String[] args) throws Exception {
		new Fibonacci().startProcessing();
	}

	private void startProcessing() {
		fibonacci(0,1);
	}

	private void fibonacci(int prev, int next) {
		System.out.println("Fibonacci series: " + prev);
		if (counter > 100) {
			return;
		}
		int next_tmp = next;
		next = next + prev;
		prev = next_tmp;
		counter = next;
		fibonacci(prev, next);
	}
}
