package com.dj.model;

import lombok.Data;

@Data
public class Trade {
	private String tradeId;
	private String agreementId;
	private String feedId;
	private String available;
}
