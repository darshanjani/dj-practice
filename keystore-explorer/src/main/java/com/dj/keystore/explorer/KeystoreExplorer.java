package com.dj.keystore.explorer;

import com.dj.keystore.explorer.components.CertificateDetailGrid;
import com.dj.keystore.explorer.components.CertificateGrid;
import com.dj.keystore.explorer.components.InputControls;
import com.dj.keystore.explorer.components.KeystoreSaveChooser;
import com.dj.keystore.explorer.model.KeyModel;
import com.dj.keystore.explorer.model.KeyValueModel;
import com.dj.keystore.explorer.util.KeystoreUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class KeystoreExplorer extends Application {

	public static final String LAST_USED_KEYSTORE = "last.used.keystore";
	public static final String LAST_USED_P_W_D = "last.used.p_w_d";
	public static final String USER_HOME = System.getProperty("user.home");
	public static final String FILE_SEP = System.getProperty("file.separator");
	public static final String KEYSTORE_EXPLORER_INI = "keystore.explorer.ini";
	public static final String APPLICATION_TITLE = "DJ's Keystore Explorer";

	private final ObservableList<KeyModel> data = FXCollections.observableArrayList();
	private final CertificateGrid<KeyModel> grid = new CertificateGrid<>();
	private final ObservableList<KeyValueModel> detailData = FXCollections.observableArrayList();
	private final CertificateDetailGrid<KeyValueModel> detailGrid = new CertificateDetailGrid<>();
	private KeystoreSaveChooser saveChooser = new KeystoreSaveChooser();

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(APPLICATION_TITLE);
		InputControls inputControls = new InputControls(primaryStage);

		inputControls.getExploreJksButton().setOnAction(e -> {
			if (inputControls.validateKeystoreNotEmpty() && inputControls.validateKeystorePasswordSupplied()) {
				inputControls.saveStateToIni();
				List<KeyModel> keyModels = KeystoreUtils.readKeyStore(inputControls.getSelectedKeyStoreText().getText(), inputControls.getKeystorePasswordField().getText());
				if (keyModels != null && !keyModels.isEmpty()) {
					data.clear();
					data.addAll(keyModels);
				}
			}
		});

		grid.setItems(data);
		detailGrid.setItems(detailData);

		grid.setOnMousePressed(me -> {
			if (me.isPrimaryButtonDown() && me.getClickCount() == 2) {
				KeyModel keyModel = grid.getSelectionModel().getSelectedItem();
				detailData.clear();
				detailData.addAll(KeystoreUtils.readSingleKey(
						inputControls.getSelectedKeyStoreText().getText(),
						inputControls.getKeystorePasswordField().getText(),
						keyModel.getAlias()
				));

				Stage certificateDetail = new Stage();
				certificateDetail.initOwner(primaryStage);
				Label label = new Label("Certificate Details:");
				VBox vBox2 = new VBox();
				vBox2.getChildren().addAll(label, detailGrid);
				vBox2.setPadding(new Insets(10, 10, 10, 10));
				detailGrid.prefHeightProperty().bind(vBox2.heightProperty());
				certificateDetail.setScene(new Scene(vBox2, 800, 600));
				certificateDetail.initModality(Modality.APPLICATION_MODAL);
				certificateDetail.showAndWait();

//				File file = saveChooser.getInstance().showSaveDialog(primaryStage);
//				if (file != null) {
//					System.out.println("Saving File: " + keyModel);
//					KeystoreUtils.exportCert(
//						inputControls.getSelectedKeyStoreText().getText(),
//						inputControls.getKeystorePasswordField().getText(),
//						keyModel.getAlias(),
//						file.getAbsolutePath());
//				}
			}
		});

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(10, 10, 10, 10));

		HBox hBox = new HBox(10);
		Label searchLabel = new Label("Search Anything: ");
		TextField searchAnything = new TextField();
		Button button = new Button("Search");
		button.setOnAction(btnClick -> {
			List<KeyModel> filtered = data.stream().filter(keyModel -> keyModel.toString().contains(searchAnything.getText())).collect(Collectors.toList());
			data.clear();
			data.addAll(filtered);
		});
		hBox.setPadding(new Insets(10, 10, 10, 10));
		hBox.getChildren().addAll(searchLabel, searchAnything, button);

		vBox.getChildren().addAll(inputControls, hBox, grid);
		grid.prefHeightProperty().bind(vBox.heightProperty());
		primaryStage.setScene(new Scene(vBox, 1080, 684));
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
