package com.kazurayam.ks.windowlayout

public class WindowLocation {

	public static final WindowLocation DEFAULT = new WindowLocation(1, 0)
	
	public final int size
	public final int index

	WindowLocation(int size, int index) {
		validate(size, index)
		this.size = size
		this.index = index
	}

	static void validate(int size, int index) {
		if (size <= 0) {
			throw new IllegalArgumentException("size=${size}, must be greater than 0")
		}
		if (index < 0) {
			throw new IllegalArgumentException("index=${index}, must be >= 0")
		}
		if (size - 1 < index) {
			throw new IllegalArgumentException("index=${index}, must be < size(${size})")
		}
	}
}
