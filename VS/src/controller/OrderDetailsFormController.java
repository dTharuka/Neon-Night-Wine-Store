package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modle.orderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailsFormController {
    public TableColumn orderIdCol;
    public TableColumn ItemCodeCol;
    public TableColumn QtyCol;
    public TableColumn DiscountCol;
    public TableColumn PriceCol;
    public TableView ODetailFormTbl;
    public TextField SearchOrderId;

    public void initialize() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        ItemCodeCol.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        QtyCol.setCellValueFactory(new PropertyValueFactory<>("Orderqty"));
        DiscountCol.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        try {
            loadAllCustomers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM `Order Details`");
        ObservableList<orderDetails> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new orderDetails(
                            result.getString("OrderID"),
                            result.getString("ItemCode"),
                            result.getInt("Orderqty"),
                            result.getDouble("Discount"),
                            result.getDouble("Price")


                    )
            );
        }
        ODetailFormTbl.setItems(obList);
        ODetailFormTbl.refresh();
    }

    public void searchOrderIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (SearchOrderId.getText().trim().isEmpty()) {
            loadAllCustomers();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `Order Details` WHERE OrderID=? " , SearchOrderId.getText());
            ObservableList<orderDetails> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new orderDetails(
                                result.getString("OrderID"),
                                result.getString("ItemCode"),
                                result.getInt("Orderqty"),
                                result.getDouble("Discount"),
                                result.getDouble("Price")

                        )
                );
            }
            ODetailFormTbl.setItems(obList);
        }
    }
}
