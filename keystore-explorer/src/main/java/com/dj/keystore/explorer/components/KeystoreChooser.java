package com.dj.keystore.explorer.components;

import javafx.stage.FileChooser;
import lombok.Getter;

import java.io.File;

public class KeystoreChooser {
	@Getter final FileChooser instance = new FileChooser();

	public KeystoreChooser() {
		init();
	}

	private void init() {
		String initialDir = System.getProperty("java.home") + "/lib/security/";
		System.out.println("Initial System directory: " + initialDir);
		instance.setInitialDirectory(new File(initialDir));
	}
}
