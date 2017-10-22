package com.dj.drools.firealarm;

import lombok.Data;

@Data
public class Sprinkler {
	private Room room;
	private boolean on;
}
