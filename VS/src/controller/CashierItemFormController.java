package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modle.item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierItemFormController {
    public TableView<item> ItemAddTbl;
    public TableColumn ItemCode;
    public TableColumn Description;
    public TableColumn PackSize;
    public TableColumn QtyOnHand;
    public TableColumn UnitPrice;
    public TableColumn DateCol;
    public Label lblDate;
    public TextField ItemCodetxt;

    public void initialize() {
//        String ICode;
//        String IDescription;
//        String PackSize;
//        int QtyOnHand;
//        double UPrice;
        ItemCode.setCellValueFactory(new PropertyValueFactory<>("ICode"));
        Description.setCellValueFactory(new PropertyValueFactory<>("IDescription"));
        PackSize.setCellValueFactory(new PropertyValueFactory<>("PackSize"));
        QtyOnHand.setCellValueFactory(new PropertyValueFactory<>("UPrice"));
        UnitPrice.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));


        try {
            loadAllItems();
//            loadDateAndTime();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItems() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Item");

        ObservableList<item> obList = FXCollections.observableArrayList();
        while (result.next()) {
            obList.add(
                    new item(

                            result.getString("ItemCode"),
                            result.getString("Description"),
                            result.getString("PackSize"),
                            result.getDouble("UnitPrice"),
                            result.getInt("QtyOnHand"),
                            result.getString("Date")

                    )
            );
        }
        ItemAddTbl.setItems(obList);
    }

    public void ItemCodeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ObservableList<item> obList = FXCollections.observableArrayList();
        if (ItemCodetxt.getText().trim().isEmpty()) {
            loadAllItems();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM item WHERE ItemCode=? " , ItemCodetxt.getText());


            while (result.next()) {
                obList.add(
                        new item(
                                result.getString("ItemCode"),
                                result.getString("Description"),
                                result.getString("PackSize"),
                                result.getDouble("UnitPrice"),
                                result.getInt("QtyOnHand"),
                                result.getString("Date")

                        )
                );
            }
            ItemAddTbl.setItems(obList);
        }
    }
}
