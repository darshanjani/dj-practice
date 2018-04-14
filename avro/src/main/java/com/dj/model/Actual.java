package com.dj.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Actual extends Base {
	private static final long serialVersionUID = 1L;

	private String act1;
	private String act2;
	private String act3;
	private Date act4;
	private Composition composition;
	private List<Composition> compositions;
}
