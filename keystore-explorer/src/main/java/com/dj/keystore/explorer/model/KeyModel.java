package com.dj.keystore.explorer.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KeyModel {
	private String alias;
	private String subject;
	private String serialNumber;
	private String issuer;
	private String validFrom;
	private String validTo;
}
