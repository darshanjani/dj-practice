package com.dj.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Base extends SuperBase {
	private String base1;
	private Integer base2;
	private long base3;
	private double base4;
	private float base5;
	private boolean base6;
	private int base7;
	private Long base8;
	private Float base9;
	private Double base10;
}
