package com.dj.keystore.explorer.components;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.*;
import java.util.Properties;

import static com.dj.keystore.explorer.KeystoreExplorer.*;

public class InputControls extends HBox {

	private static final int DEFAULT_SPACING = 10;

	private Stage primaryStage;

	private Label keystoreLabel = new Label("Choose Keystore");
	@Getter private TextField selectedKeyStoreText = new TextField();
	private Button chooseKeystoreButton = new Button("...");
	private KeystoreChooser keystoreChooser = new KeystoreChooser();
	private Label passwordLabel = new Label("Keystore Password");
	@Getter private PasswordField keystorePasswordField = new PasswordField();
	@Getter private Button exploreJksButton = new Button("Explore JKS");

	public InputControls(Stage primaryStage) {
		this.primaryStage = primaryStage;
		init();
	}

	private void init() {
		setSpacing(DEFAULT_SPACING);
		setPadding(new Insets(10, 10, 10, 10));

		selectedKeyStoreText.setPrefWidth(450);
		getChildren().addAll(keystoreLabel, selectedKeyStoreText, chooseKeystoreButton, passwordLabel, keystorePasswordField, exploreJksButton);

		chooseKeystoreButton.setOnAction(e -> {
			File file = keystoreChooser.getInstance().showOpenDialog(primaryStage);
			if (file != null) {
				selectedKeyStoreText.setText(file.getAbsolutePath());
			}
		});
		loadInitialStateFromIni();
	}

	private void loadInitialStateFromIni() {
		try {
			File iniFile = new File(USER_HOME + FILE_SEP + KEYSTORE_EXPLORER_INI);
			if (iniFile.exists() && iniFile.canRead()) {
				FileInputStream fis = new FileInputStream(iniFile);
				Properties properties = new Properties();
				properties.load(fis);
				if (properties.containsKey(LAST_USED_KEYSTORE)) {
					selectedKeyStoreText.setText(properties.getProperty(LAST_USED_KEYSTORE));
				}
				if (properties.containsKey(LAST_USED_P_W_D)) {
					keystorePasswordField.setText(properties.getProperty(LAST_USED_P_W_D));
				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}

	public boolean validateKeystoreNotEmpty() {
		if (selectedKeyStoreText.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Keystore must be selected");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	public boolean validateKeystorePasswordSupplied() {
		if (keystorePasswordField.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Keystore password must be supplied");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	public void saveStateToIni() {
		try (FileOutputStream fos = new FileOutputStream(new File(USER_HOME + FILE_SEP + KEYSTORE_EXPLORER_INI))) {
			Properties properties = new Properties();
			properties.put(LAST_USED_KEYSTORE, selectedKeyStoreText.getText());
			properties.put(LAST_USED_P_W_D, keystorePasswordField.getText());
			properties.store(fos, "Keystore-Explorer last used values");
			fos.flush();
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}
}
