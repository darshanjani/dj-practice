package com.dj.keystore.explorer.components;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CertificateGrid<T> extends TableView<T> {
	private final ContextMenu contextMenu = new ContextMenu();
	private MenuItem exportCert = new MenuItem("Export Cert");

	public CertificateGrid() {
		init();
	}

	private void init() {
		exportCert.setOnAction(event -> {
			ObservableList<T> selectedItems = getSelectionModel().getSelectedItems();
			for (T selectedItem : selectedItems) {
				System.out.println("User selected to export: " + selectedItem);
			}
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Certificates exported to selected path.", ButtonType.OK);
			alert.showAndWait();
			getSelectionModel().clearSelection();
		});
		MenuItem importCert = new MenuItem("Import Cert");
		contextMenu.getItems().addAll(exportCert, importCert);

		addTableColumn("Alias", "alias", 100);
		addTableColumn("Subject", "subject", 300);
		addTableColumn("Serial#", "serialNumber", 150);
		addTableColumn("Issuer DN", "issuer", 300);
		addTableColumn("Valid From", "validFrom", 200);
		addTableColumn("Valid To", "validTo", 200);
		setContextMenu(contextMenu);
		getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

	private void addTableColumn(String header, String propertyInModel, int minWidth) {
		TableColumn tableColumn = new TableColumn(header);
		tableColumn.setMinWidth(minWidth);
		tableColumn.setCellValueFactory(
				new PropertyValueFactory<>(propertyInModel));
		getColumns().addAll(tableColumn);
	}
}
