package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modle.cashier;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddCashierFormController {
    public TableColumn CashierIdCol;
    public TableColumn CashierNameCol;
    public TableColumn CashierNICCol;
    public TableColumn CashierContactCol;
    public TableColumn CashierSalaryCol;
    public TableColumn CashierPasswordCol;
    public TableView CashierTable;
    public TextField idTxt;
    public TextField NicTxt;
    public TextField SalaryTxt;
    public TextField NameTxt;
    public TextField PwdTxt;
    public TextField ContactTxt;
    public Button saveBtn;
    public Button btnSaveItem;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

//-----------------Show Data From Table--------------------------------------
         public void initialize() {

        CashierIdCol.setCellValueFactory(new PropertyValueFactory<>("cashierId"));
        CashierNameCol.setCellValueFactory(new PropertyValueFactory<>("cashierName"));
        CashierNICCol.setCellValueFactory(new PropertyValueFactory<>("cashierNIC"));
        CashierContactCol.setCellValueFactory(new PropertyValueFactory<>("cashierContact"));
        CashierSalaryCol.setCellValueFactory(new PropertyValueFactory<>("cashierSalary"));
        CashierPasswordCol.setCellValueFactory(new PropertyValueFactory<>("cashierPwd"));

        btnSaveItem.setOnMouseClicked(event -> {
            SaveBtnOnAction();
        });

        Pattern idTxtPattern = Pattern.compile("^(C00-)[0-9]{2,4}$");                        //true
        Pattern NameTxtPattern = Pattern.compile("^[A-z ]{3,20}$");                          //true
        Pattern NicTxtPattern = Pattern.compile("^([0-9]{12}|[0-9]{12}v)$");                 //true
        Pattern ContactTxtPattern = Pattern.compile("^(07(1|2|4|5|6|7|8)|091)(-)[0-9]{7}$"); //true
        Pattern PwdTxtPattern = Pattern.compile("^[0-9]{4,5}$");                             //true
        Pattern SalaryTxtPattern = Pattern.compile("^[0-9]{3,5}(.)[0]{1,2}$");               //true

//  ------------------MATCHING ENTERED VALUES WITH REG-X PATTERN---------------------------------------
        map.put(idTxt, idTxtPattern);
        map.put(NameTxt, NameTxtPattern);
        map.put(NicTxt, NicTxtPattern);
        map.put(ContactTxt, ContactTxtPattern);
        map.put(SalaryTxt, SalaryTxtPattern);
        map.put(PwdTxt, PwdTxtPattern);


        try {
            loadAllCashiers();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    --------------------Load All Data To Table------------------------------

    private void loadAllCashiers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Cashier");
        ObservableList<cashier> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new cashier(
                            result.getString("CashierId"),
                            result.getString("CashierName"),
                            result.getString("NIC"),
                            result.getString("Contact"),
                            result.getDouble("Salary"),
                            result.getString("CPassword")
                    )
            );
        }
        CashierTable.setItems(obList);
        CashierTable.refresh();
    }
//---------------Save Data To Table----------------------
    public void SaveBtnOnAction() {
        cashier cash = new cashier(
                idTxt.getText(),
                NameTxt.getText(),
                NicTxt.getText(),
                ContactTxt.getText(),
                Double.parseDouble(SalaryTxt.getText()),
                PwdTxt.getText()
        );
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
                if (CrudUtil.execute("INSERT INTO cashier VALUES (?,?,?,?,?,?)",
                        cash.getCashierId(), cash.getCashierName(), cash.getCashierNIC(),
                        cash.getCashierContact(), cash.getCashierSalary(), cash.getCashierPwd()
                )) {
//                NotificationController.SuccessfulTableNotification("Save", "Cashier");
                    CashierTable.refresh();
                    loadAllCashiers();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
//            NotificationController.UnSuccessfulTableNotification("Save", "Cashier");
        }
        clear();
    }

//--------------------Update Values From Table----------------------------

    public void updateBtnOnAction(ActionEvent actionEvent) {
        cashier cash = new cashier(

                idTxt.getText(),
                NameTxt.getText(),
                NicTxt.getText(),
                ContactTxt.getText(),
                Double.parseDouble(SalaryTxt.getText()),
                PwdTxt.getText()
        );

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
                boolean isUpdated2 = CrudUtil.execute(
                        "UPDATE Cashier SET CashierName=? ," +
                                " NIC=? ," +
                                " Contact=? ," +
                                " Salary=? ," +
                                " CPassword=?  WHERE CashierId=?",
                        cash.getCashierName(), cash.getCashierNIC(),
                        cash.getCashierContact(), cash.getCashierSalary(),
                        cash.getCashierPwd(), cash.getCashierId()
                );
                if (isUpdated2) {

//                    new Alert(Alert.AlertType.CONFIRMATION, "Update Confirmed!").show();
                    loadAllCashiers();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            //System.out.println(e);

        }
        clear();
    }
//--------------------------Delete Values From Table---------------------------------------
    public void deleteBtnOnAction(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
                if (CrudUtil.execute("DELETE FROM Cashier WHERE CashierId=?", idTxt.getText())) {
                    loadAllCashiers();
//                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                    loadAllCashiers();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        CashierTable.refresh();
        clear();
    }
//------------------Search Values From Table-------------------------------
    public void save(ActionEvent actionEvent) {

        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Cashier WHERE CashierID=?", idTxt.getText());

            if (result.next()) {

                NameTxt.setText(result.getString(2));
                NicTxt.setText(result.getString(3));
                ContactTxt.setText(result.getString(4));
                SalaryTxt.setText(result.getString(5));
                PwdTxt.setText(result.getString(6));

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
                loadAllCashiers();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//--------------------SET COLOURS INTO TEX FIELD BORDERS----------------------------------

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map, btnSaveItem);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map, btnSaveItem);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
                //System.out.println("Work");
                //SaveBtnOnAction();
            }
        }
    }
//-----------------------Clear Text Fields--------------------
    public void clear() {
        idTxt.clear();
        NameTxt.clear();
        NicTxt.clear();
        ContactTxt.clear();
        SalaryTxt.clear();
        PwdTxt.clear();
    }


}
