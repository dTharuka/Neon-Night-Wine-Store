package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import util.CrudUtil;
import modle.customer;
import util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class CustomerFormController {
    public TableView<customer> customerDetailsTbl;
    public TableColumn CId;
    public TableColumn Title;
    public TableColumn Name;
    public TableColumn Address;
    public TableColumn City;
    public TableColumn Province;
    public TableColumn PostalCode;
    public TextField CIdtxt;
    public TextField Addresstxt;
    public TextField CNametxt;
    public TextField Citytxt;
    public TextField PostalCodetxt;
    public TextField Titletxt;
    public TextField Provincetxt;
    public Label Datelbl;
    public Label Timelbl;
    public Button CustomerSaveButton;
    LinkedHashMap<TextField, Pattern> CustomerMap = new LinkedHashMap<>();

//    ----------------Show Dat From Table----------------------

    public void initialize(){

        CId.setCellValueFactory(new PropertyValueFactory<>("CId"));
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Name.setCellValueFactory(new PropertyValueFactory<>("CName"));
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        City.setCellValueFactory(new PropertyValueFactory<>("City"));
        Province.setCellValueFactory(new PropertyValueFactory<>("Province"));
        PostalCode.setCellValueFactory(new PropertyValueFactory<>("CPostalCode"));

        CustomerSaveButton.setOnMouseClicked(event -> {
            saveBtnOnAction();
        });

        Pattern CusIdPattern = Pattern.compile("^(C-0)[0-9]{2,4}$");
        Pattern CusMaleOrFemalePattern = Pattern.compile("^(mr|mrs)$");
        Pattern CusAddressPattern = Pattern.compile("^[A-z]{3,30}$");
        Pattern CusNamePattern = Pattern.compile("^[A-z]{3,30}$");
        Pattern CusCityPattern = Pattern.compile("^[A-z]{4,15}$");
        Pattern CusProvincePattern = Pattern.compile("^[A-z]{2,5}$");
        Pattern CusPostalCodePattern = Pattern.compile("^[0-9]{5}$");

//  ------------------MATCHING ENTERED VALUES WITH REG-X PATTERN-------------------

        CustomerMap.put(CIdtxt, CusIdPattern);
        CustomerMap.put(Titletxt, CusMaleOrFemalePattern);
        CustomerMap.put(CNametxt, CusNamePattern);
        CustomerMap.put(Addresstxt, CusAddressPattern);
        CustomerMap.put(Citytxt, CusCityPattern);
        CustomerMap.put(Provincetxt, CusProvincePattern);
        CustomerMap.put(PostalCodetxt, CusPostalCodePattern);

        try {
            loadAllCustomers();
            loadDateAndTime();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

//    -------------------Load All Data To Table---------------------------------

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
        customerDetailsTbl.setItems(obList);
    }

//    ------------------Save Data To Table--------------------------

    public void saveBtnOnAction() {

        customer cus = new customer(
                CIdtxt.getText(),
                Titletxt.getText(),
                CNametxt.getText(),
                Addresstxt.getText(),
                Citytxt.getText(),
                Provincetxt.getText(),
                PostalCodetxt.getText(),
                Datelbl.getText(),
                Timelbl.getText()
        );

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
                if (CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?,?,?,?,?)",
                        cus.getCId(), cus.getTitle(), cus.getCName(),
                        cus.getAddress(), cus.getCity(), cus.getProvince(), cus.getCPostalCode(), cus.getDate(), cus.getTime()

                )) {
//                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                    customerDetailsTbl.refresh();
                    loadAllCustomers();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clear();
    }

//    -----------------Update Values From Table--------------------------------------

    public void updateBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        customer c = new customer(
                CIdtxt.getText(),
                Titletxt.getText(),
                CNametxt.getText(),
                Addresstxt.getText(),
                Citytxt.getText(),
                Provincetxt.getText(),
                PostalCodetxt.getText(),
                Datelbl.getText(),
                Timelbl.getText()

        );
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
                boolean isUpdated = CrudUtil.execute(

                        "UPDATE Customer SET CustomerTitle=? ," +
                                " CustomerName=? ," +
                                " CustomerAddress=? ," +
                                " City=? , Province=? ," +
                                " PostCode=?,Date=?,Time=? WHERE CustomerID=?", c.getTitle(), c.getCName(),
                        c.getAddress(), c.getCity(), c.getProvince(), c.getCPostalCode(), c.getDate(), c.getTime(), c.getCId()
                );
                if (isUpdated) {
//                    new Alert(Alert.AlertType.CONFIRMATION, "Update Confirmed!").show();
                    loadAllCustomers();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }

            }
        } catch (SQLException | ClassNotFoundException e) {

        }
        clear();
    }

//    ------------------------------Delete Values From Table------------------------------------------

    public void deleteBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
                if (CrudUtil.execute("DELETE FROM Customer WHERE CustomerID=?", CIdtxt.getText())) {
//                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                    loadAllCustomers();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }

        } catch (SQLException | ClassNotFoundException e){


        }
        clear();
    }

//    ---------------------Search Values From Table----------------------

    public void save() {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE CustomerID=?", CIdtxt.getText());
            if (result.next()) {
                Titletxt.setText(result.getString(2));
                CNametxt.setText(result.getString(3));
                Addresstxt.setText(result.getString(4));
                Citytxt.setText(result.getString(5));
                Provincetxt.setText(result.getString(6));
                PostalCodetxt.setText(result.getString(7));


            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//--------------------------Load Date And Time To Table-----------------------

    private void loadDateAndTime() {
        //Set Date
        Datelbl.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            Timelbl.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

//    -------------------SET COLOURS INTO TEX FIELD BORDERS-------------------
    public void textFields_Key_Released_Customer(KeyEvent keyEvent) {
        ValidationUtil.validate(CustomerMap, CustomerSaveButton);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(CustomerMap, CustomerSaveButton);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
//                SaveBtnOnAction();
            }
        }
    }


//    --------Clear Text Fields------------

    public void clear(){
        CIdtxt.clear();
        Titletxt.clear();
        CNametxt.clear();
        Addresstxt.clear();
        Citytxt.clear();
        Provincetxt.clear();
        PostalCodetxt.clear();

    }


}
