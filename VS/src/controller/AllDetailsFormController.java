package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import modle.customer;
import modle.order;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllDetailsFormController {
    public TableColumn CusIdCol;
    public TableColumn CusTitleCol;
    public TableColumn CusNameCol;
    public TableColumn CusAddressCol;
    public TableColumn CusCityCol;
    public TableColumn DateCol;
    public TableColumn TimeCol;
    public TableView<customer> CustomerDetailsContext;
    public TextField SerchCustomerIdtxt;
    public TextField Titletxt;
    public TextField Addresstxt;
    public TextField Nametxt;
    public TextField Timetxt;
    public TextField Datetxt;
    public TextField Citytxt;

//    ------------------------Show Data From Table--------------------
    public void initialize(){

        CusIdCol.setCellValueFactory(new PropertyValueFactory<>("CId"));
        CusTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        CusNameCol.setCellValueFactory(new PropertyValueFactory<>("CName"));
        CusAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        CusCityCol.setCellValueFactory(new PropertyValueFactory<>("City"));
        DateCol .setCellValueFactory(new PropertyValueFactory<>("Date"));
        TimeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));

        try {
            loadAllCustomers();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
//    ------------------------Load All Data To Table---------------------------

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer");
        ObservableList<customer> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new customer(
                            result.getString("CustomerID"),
                            result.getString("CustomerTitle"),
                            result.getString("CustomerName"),
                            result.getString("CustomerAddress"),
                            result.getString("City"),
                            result.getString("Province"),
                            result.getString("PostCode"),
                            result.getString("Date"),
                            result.getString("Time")

                    )
            );
        }
        CustomerDetailsContext.setItems(obList);
    }
//    -------------------------Search Values To Text Fields-----------------------------

    public void save() throws SQLException, ClassNotFoundException {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE CustomerID=?", SerchCustomerIdtxt.getText());
            if (result.next()) {
                Titletxt.setText(result.getString(2));
                Nametxt.setText(result.getString(3));
                Addresstxt.setText(result.getString(4));
                Citytxt.setText(result.getString(5));
                Datetxt.setText(result.getString(8));
                Timetxt.setText(result.getString(9));

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        searchOnKeyPressed();
    }

//-----------------------Search Data In Table--------------------------------------------

    public void searchOnKeyPressed() throws SQLException, ClassNotFoundException {
        if (SerchCustomerIdtxt.getText().trim().isEmpty()) {
            loadAllCustomers();
        } else {
            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE CustomerID=? " , SerchCustomerIdtxt.getText());
            ObservableList<customer> obList = FXCollections.observableArrayList();

            while (result.next()) {
                obList.add(
                        new customer(
                                result.getString("CustomerID"),
                                result.getString("CustomerTitle"),
                                result.getString("CustomerName"),
                                result.getString("CustomerAddress"),
                                result.getString("City"),
                                result.getString("Province"),
                                result.getString("PostCode"),
                                result.getString("Date"),
                                result.getString("Time")

                        )
                );
            }
            CustomerDetailsContext.setItems(obList);
        }

    }
}
