package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import modle.order;
import modle.orderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderFormController {
    public AnchorPane cusPaneContext;
    public TableColumn OrderIdCol;
    public TableColumn CIdCol;
    public TableColumn OrderDateCol;
    public TableColumn OrderTimeCol;
    public TextField SearchCusIdtxt;
    public TextField OrderCounttxt;
    public TableView tblOrder;

    public void save(ActionEvent actionEvent) {

    }

    public void initialize() {
        OrderIdCol.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        CIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        OrderDateCol.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
        OrderTimeCol.setCellValueFactory(new PropertyValueFactory<>("OrderTime"));

        try {
            loadAllOrder();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllOrder() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM `Order`");
        ObservableList<order> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new order(
                            result.getString("OrderID"),
                            result.getString("CustomerID"),
                            result.getString("OrderDate"),
                            result.getString("OrderTime")
                    )
            );
        }
        tblOrder.setItems(obList);
    }

    public void search(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (SearchCusIdtxt.getText().trim().isEmpty()) {
            loadAllOrder();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM `Order` WHERE OrderID=? " , SearchCusIdtxt.getText());
            ObservableList<order> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new order(
                                result.getString("OrderID"),
                                result.getString("CustomerID"),
                                result.getString("OrderDate"),
                                result.getString("OrderTime")
                        )
                );
            }
            tblOrder.setItems(obList);
        }
    }
}


