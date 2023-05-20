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
import modle.item;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class ItemFormController {


    public TableView ItemAddTbl;
    public TableColumn ItemCode;
    public TableColumn Description;
    public TableColumn PackSize;
    public TableColumn QtyOnHand;
    public TableColumn UnitPrice;
    public TableColumn DateCol;
    public TextField ItemCodetxt;
    public TextField Descriptiontxt;
    public TextField PackPricetxt;
    public TextField UnitPricetxt;
    public TextField QtyOnHandtxt;
    public TextField Datetxt;
    public Label lblDate;
    public Button itemSaveBtnOnAction;
    LinkedHashMap<TextField, Pattern> itemMap = new LinkedHashMap<>();


//    --------------------Show Dat From Table-----------------------

    public void initialize() {

        ItemCode.setCellValueFactory(new PropertyValueFactory<>("ICode"));
        Description.setCellValueFactory(new PropertyValueFactory<>("IDescription"));
        PackSize.setCellValueFactory(new PropertyValueFactory<>("PackSize"));
        QtyOnHand.setCellValueFactory(new PropertyValueFactory<>("UPrice"));
        UnitPrice.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));

        itemSaveBtnOnAction.setOnMouseClicked(event -> {
            itemSaveBtnOnAction();
        });

        Pattern idItmPattern = Pattern.compile("^I-(0)[0-9]{2,5}$");//true
        Pattern DescriptionItmPattern = Pattern.compile("^[A-z]{2,10}$");//true
        Pattern PackItmPattern = Pattern.compile("^(small|medium|large)(/)(small|large|medium)$");//true
        Pattern UnitPriceItmPattern = Pattern.compile("^([0-9]{2,6}.[0-9]{1,2})$");//true
        Pattern QtyItmPattern = Pattern.compile("^[0-9]{1,5}$");//true

//  ------------------MATCHING ENTERED VALUES WITH REG-X PATTERN-------------------

        itemMap.put(ItemCodetxt, idItmPattern);
        itemMap.put(Descriptiontxt, DescriptionItmPattern);
        itemMap.put(PackPricetxt, PackItmPattern);
        itemMap.put(UnitPricetxt, UnitPriceItmPattern);
        itemMap.put(QtyOnHandtxt, QtyItmPattern);


        try {
            loadAllItems();
            loadDateAndTime();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

//    ------------------------Load All Data To Table---------------------------------

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


    public void itemUpdateBtnOnAction(ActionEvent actionEvent) {
        item it2 = new item(

                ItemCodetxt.getText(),
                Descriptiontxt.getText(),
                PackPricetxt.getText(),
                Double.parseDouble(UnitPricetxt.getText()),
                Integer.parseInt(QtyOnHandtxt.getText()),
                Datetxt.getText()

        );
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
                boolean isUpdated2 = CrudUtil.execute(


                        "UPDATE Item SET Description=? ," +
                                " PackSize=? ," +
                                " UnitPrice=? ," +
                                " QtyOnHand=?," +
                                "Date=?  WHERE ItemCode=?", it2.getIDescription(),
                        it2.getPackSize(), it2.getUPrice(), it2.getQtyOnHand(), it2.getDate(), it2.getICode()
                );
                if (isUpdated2) {
//                    new Alert(Alert.AlertType.CONFIRMATION, "Update Confirmed!").show();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {

        }
        clear();
    }

    public void itemDeleteOnBtnAction(ActionEvent actionEvent) {
        try {
//            ----------------------------------------------
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
//            -------------------------------------------------
                if (CrudUtil.execute("DELETE FROM Item WHERE ItemCode=?", ItemCodetxt.getText())) {
//                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {

        }
        ItemAddTbl.refresh();
        clear();
    }

    public void itemSaveBtnOnAction() {

        item it = new item(

                ItemCodetxt.getText(),
                Descriptiontxt.getText(),
                PackPricetxt.getText(),
                Double.parseDouble(UnitPricetxt.getText()),
                Integer.parseInt(QtyOnHandtxt.getText()),
                lblDate.getText()


        );

        try {
            //            ----------------------------------------------
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure ?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
//            -------------------------------------------------
                if (CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?,?,?)",

                        it.getICode(), it.getIDescription(), it.getPackSize(),
                        it.getQtyOnHand(), it.getUPrice(), it.getDate()

                )) {
//                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                    loadAllItems();
                    ItemAddTbl.refresh();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clear();

    }

    public void save() {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Item WHERE ItemCode=?", ItemCodetxt.getText());
            if (result.next()) {

                Descriptiontxt.setText(result.getString(2));
                PackPricetxt.setText(result.getString(3));
                UnitPricetxt.setText(String.valueOf(result.getDouble(4)));
                QtyOnHandtxt.setText(String.valueOf(result.getInt(5)));
                Datetxt.setText(result.getString(6));


            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


//    ---------------Load Date And Time To Table------------------

    private void loadDateAndTime() {
        //Set Date
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time



    }

//    ---------------------------SET COLOURS INTO TEX FIELD BORDERS----------------

    public void textFields_Key_Released_Item(KeyEvent keyEvent) {
        ValidationUtil.validate(itemMap, itemSaveBtnOnAction);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(itemMap, itemSaveBtnOnAction);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
                //SaveBtnOnAction();
            }
        }
    }
    public void clear(){
        ItemCodetxt.clear();
        Descriptiontxt.clear();
        PackPricetxt.clear();
        UnitPricetxt.clear();
        QtyOnHandtxt.clear();
        Datetxt.clear();

    }


}
