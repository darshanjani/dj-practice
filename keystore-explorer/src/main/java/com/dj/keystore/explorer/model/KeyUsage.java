package com.dj.keystore.explorer.model;

public enum KeyUsage {
	DIGITAL_SIGNATURE ("Digital Signature"),
	NON_REPUDIATION ("Non Repudiation"),
	KEY_ENCIPHERMENT ("Key Encipherment"),
	DATA_ENCIPHERMENT ("Data Encipherment"),
	KEY_AGREEMENT ("Key Agreement"),
	KEY_CERT_SIGN ("Certificate Sign"),
	CRL_SIGN ("CRL Sign"),
	ENCIPHER_ONLY ("Encipher Only"),
	DECIPHER_ONLY ("Decipher Only");

	String description;

	KeyUsage(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
