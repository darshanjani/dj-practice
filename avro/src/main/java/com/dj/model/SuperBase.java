package com.dj.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class SuperBase implements Intf {
	private String super1;
	private String super2;
	private String super3;
}
