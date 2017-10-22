package com.dj.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Builder
@Data
public class Response {
	private boolean success;
	private Object data;
	private int total;
}
