package com.dj.utils.common;

import java.sql.Time;

/**
 * Created by Darshan on 8/7/16.
 */
public class Timer {
	private long start = 0;
	private long end = 0;
	private long previousInterval = 0;

	private Timer() {}

	public static Timer newTimer() {
		Timer t = new Timer();
		t.start = System.currentTimeMillis();
		return t;
	}

	public void pause() {
		end = System.currentTimeMillis();
		previousInterval += (end - start);
	}

	public void resume() {
		start = System.currentTimeMillis();
	}

	public long time() {
		end = System.currentTimeMillis();
		return previousInterval + (end - start);
	}

	public void reset() {
		start = end = previousInterval = 0;
	}

	public static void main(String[] args) throws Exception {
		Timer t = Timer.newTimer();
		Thread.sleep(2050);
		System.out.println("Before sleeping: " + t.time());
		t.pause();
		Thread.sleep(3000);
		t.resume();
		Thread.sleep(500);
		System.out.println("After resuming: " + t.time());
	}
}
