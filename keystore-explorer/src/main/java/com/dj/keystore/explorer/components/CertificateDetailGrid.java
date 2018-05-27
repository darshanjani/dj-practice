package com.dj.keystore.explorer.components;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CertificateDetailGrid<T> extends TableView<T> {

	public CertificateDetailGrid() {
		init();
	}

	private void init() {
		addTableColumn("Field", "field", 300);
		addTableColumn("Value", "value", 480);
	}

	private void addTableColumn(String header, String propertyInModel, int minWidth) {
		TableColumn tableColumn = new TableColumn(header);
		tableColumn.setMinWidth(minWidth);
		tableColumn.setCellValueFactory(
				new PropertyValueFactory<>(propertyInModel));
		getColumns().addAll(tableColumn);
	}
}
