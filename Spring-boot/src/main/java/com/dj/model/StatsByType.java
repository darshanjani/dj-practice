package com.dj.model;

import lombok.Builder;
import lombok.Data;

@Data
public class StatsByType {
	private int completed;
	private int pending;
	private int total;
}
