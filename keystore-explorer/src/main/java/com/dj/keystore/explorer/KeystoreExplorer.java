package com.dj.keystore.explorer;

import com.dj.keystore.explorer.components.CertificateGrid;
import com.dj.keystore.explorer.components.InputControls;
import com.dj.keystore.explorer.model.KeyModel;
import com.dj.keystore.explorer.util.KeystoreUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;

public class KeystoreExplorer extends Application {

	public static final String LAST_USED_KEYSTORE = "last.used.keystore";
	public static final String LAST_USED_P_W_D = "last.used.p_w_d";
	public static final String USER_HOME = System.getProperty("user.home");
	public static final String FILE_SEP = System.getProperty("file.separator");
	public static final String KEYSTORE_EXPLORER_INI = "keystore.explorer.ini";
	public static final String APPLICATION_TITLE = "DJ's Keystore Explorer";

	private final ObservableList<KeyModel> data = FXCollections.observableArrayList();
	private final CertificateGrid<KeyModel> table = new CertificateGrid<>();


	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(APPLICATION_TITLE);
		InputControls inputControls = new InputControls(primaryStage);

		inputControls.getExploreJksButton().setOnAction(e -> {
			if (inputControls.validateKeystoreNotEmpty() && inputControls.validateKeystorePasswordSupplied()) {
				inputControls.saveStateToIni();
				List<KeyModel> keyModels = KeystoreUtils.readKeyStore(inputControls.getSelectedKeyStoreText().getText(), inputControls.getKeystorePasswordField().getText());
				if (keyModels != null && !keyModels.isEmpty()) {
					data.addAll(keyModels);
				}
			}
		});

		table.setItems(data);

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(10, 10, 10, 10));
		vBox.getChildren().addAll(inputControls, table);
		table.prefHeightProperty().bind(vBox.heightProperty());
		primaryStage.setScene(new Scene(vBox, 1080, 684));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
