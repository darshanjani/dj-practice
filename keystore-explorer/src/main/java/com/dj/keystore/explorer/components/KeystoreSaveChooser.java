package com.dj.keystore.explorer.components;

import javafx.stage.FileChooser;
import lombok.Getter;

import java.io.File;

public class KeystoreSaveChooser {
	@Getter final FileChooser instance = new FileChooser();

	public KeystoreSaveChooser() {
		init();
	}

	private void init() {
		String initialDir = System.getProperty("user.home");
		System.out.println("Setting initial save directory: " + initialDir);
		instance.setInitialDirectory(new File(initialDir));
		instance.setTitle("Select directory to Save certificate");
		instance.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("DER encoded certificate", "*.der")
		);
	}
}
