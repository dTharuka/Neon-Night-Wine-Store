package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import modle.customer;
import modle.item;
import modle.order;
import modle.orderDetails;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import views.TM.cartTM;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class PlaceOrderFormController {
    public TextField PAddresstxt;
    public TextField PCitytxt;
    public TextField PDescriptiontxt;
    public TextField PQtyOnHandtxt;
    public TextField PUnitPricetxt;
    public TextField PQtytxt;
    public TextField PDisconttxt;
    public TextField PNametxt;
    public TableColumn OrderIdCol;
    public TableColumn ItemCodeCol;
    public TableColumn OrderQtyCol;
    public TableColumn UnitPriceCol;
    public TableColumn DiscountCol;
    public TableColumn TotalCol;
    public ComboBox<String> PCustomerCmb;
    public ComboBox<String> PItemCodeCmb;
    public TableView<cartTM> PlaceOrderTbl;
    public Label totalPriceLbl;
    public Button Removebtn;
    public Label OrderIdLbl;
    public Label lblDate;
    public Label lblTime;
    public Label totalPriceLbl1;
    int cartSelectedRowForRemove = -1;
    ObservableList<cartTM> tmList = FXCollections.observableArrayList();

//    -------------------------------Show Data From Table---------------------------

    public void initialize() {
        OrderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        ItemCodeCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        OrderQtyCol.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        UnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        DiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        TotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        setCustomerIds();
        setItemCodes();
        setOrderId();
        loadDateAndTime();

        //--------------------
        PCustomerCmb.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setCustomerDetails(newValue);
                });

        PItemCodeCmb.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setItemDetails(newValue);
                });
        //--------------------
        PlaceOrderTbl.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
    }

    private void loadDateAndTime() {
        //Set Date
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    private void setOrderId() {
        try {
            OrderIdLbl.setText(new OrderCrudController().getOrderId());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




    private void setCustomerDetails(String selectedCustomerId) {
        try {
            customer c = CustomerCrudController.getCustomer(selectedCustomerId);
            if (c != null) {
                PNametxt.setText(c.getCName());
                PAddresstxt.setText(c.getAddress());
                PCitytxt.setText(c.getCity());
            } else {
//                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemDetails(String selectedItemCode) {
        try {

            item i = ItemCrudController.getItem(selectedItemCode);
            if (i != null) {
                PDescriptiontxt.setText(i.getIDescription());
                PQtyOnHandtxt.setText(String.valueOf(i.getQtyOnHand()));
                PUnitPricetxt.setText(String.valueOf(i.getUPrice()));

            } else {
//                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerIds() {
        try {
            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    CustomerCrudController.getCustomerIds()
            );
            PCustomerCmb.setItems(cIdObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemCodes() {
        try {

            PItemCodeCmb.setItems(FXCollections.observableArrayList(ItemCrudController.getItemCodes()));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void moveToAddress(ActionEvent actionEvent) {
    }

    public void moveToDescription(ActionEvent actionEvent) {
    }

    public void moveToCity(ActionEvent actionEvent) {
    }

    public void moveToUnitPrize(ActionEvent actionEvent) {
    }

    public void moveToQTYOnHand(ActionEvent actionEvent) {
    }

    public void validateKeyReleasedOnAction(KeyEvent keyEvent) {
    }

    public void remove() {
        Removebtn.setOnAction(e -> {
            for (cartTM tempTm : tmList
            ) {
                if (tempTm.getItemCode().equals(tempTm.getItemCode())) {
                    tmList.remove(tempTm);
                    calculateTotal();
                }
            }
        });
    }


    public void removeBtnOnAction(ActionEvent actionEvent) {
        remove();
    }



    public void addToCartOnAction(ActionEvent actionEvent) {
        double unitPrice = Double.parseDouble(PUnitPricetxt.getText());
        double discount = Double.parseDouble(PDisconttxt.getText());
        int qty = Integer.parseInt(PQtytxt.getText());
        double Cost = ((unitPrice * qty) * discount / 100);
        double totalCost = (unitPrice * qty) - Cost;

        cartTM isExists = isExists(PItemCodeCmb.getValue());

        if (isExists != null) {
            for (cartTM temp : tmList
            ) {
                if (temp.equals(isExists)) {
                    temp.setTotal(temp.getTotal() + totalCost);
                    temp.setOrderQty((temp.getOrderQty() + qty));
                }
            }
        } else {
//            Button btn = new Button("Delete");

            cartTM tm = new cartTM(

                    OrderIdLbl.getText(),
                    PItemCodeCmb.getValue(),
                    PDescriptiontxt.getText(),
                    qty,
                    unitPrice,
                    discount,
                    totalCost
            );//System.out.println(tmList);

//            Removebtn.setOnAction(e -> {
//                for (cartTM tempTm : tmList
//                ) {
//                    if (tempTm.getItemCode().equals(tm.getItemCode())) {
//                        tmList.remove(tempTm);
//                        calculateTotal();
//                    }
//                }
//            });

            tmList.add(tm);
            PlaceOrderTbl.setItems(tmList);
        }
        PlaceOrderTbl.refresh();
        calculateTotal();
        clear();
    }

    private void calculateTotal() {
        double total = 0;
        for (cartTM tm : tmList
        ) {
            total += tm.getTotal();
        }
        totalPriceLbl.setText(String.valueOf(total));
    }

    private cartTM isExists(String itemCode) {
        for (cartTM tm : tmList
        ) {
            if (tm.getItemCode().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }


    public void PUpdateBtnOnAction(ActionEvent actionEvent) {

    }

    public void PRemoveBtnOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        } else {
            tmList.remove(cartSelectedRowForRemove);
            if (tmList.isEmpty()) {
                //placeOrderBtn.setDisable(true);
            }
            calculateTotal();
            PlaceOrderTbl.refresh();
        }
        clear();
    }

    public void AddToCartBtnOnAction(ActionEvent actionEvent) {
    }

    public void PlaceOrderBtnOnAction(ActionEvent actionEvent) throws SQLException {
        ArrayList<orderDetails> details = new ArrayList<>();
        for (cartTM tm : tmList
        ) {
            details.add(
                    new orderDetails(
                            tm.getOrderId(),
                            tm.getItemCode(),
                            tm.getOrderQty(),
//                            tm.getUnitPrice(),
                            tm.getDiscount(),
                            tm.getTotal()
                    )
            );
            tm.getItemCode();

        }
        order order = new order(

                OrderIdLbl.getText(),
                PCustomerCmb.getValue(),
                lblDate.getText(),
                lblTime.getText()

        );

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = new OrderCrudController().saveOrder(order);
            if (isOrderSaved) {
                boolean isDetailsSaved = new OrderCrudController().saveOrderDetails(details);
                if (isDetailsSaved) {
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION, "Successful").show();
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.WARNING, "Unsuccessful").show();
                }
            } else {
                connection.rollback();
                new Alert(Alert.AlertType.WARNING, "Unsuccessful").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            connection.setAutoCommit(true);
        }
        setOrderId();
        clearAll();
    }

    public void printBillBtnOnAction(ActionEvent actionEvent) {
        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/reports/Bill.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanCollectionDataSource(tmList));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException ignored) {

        }
    }


    public void clearAllOnAction(ActionEvent actionEvent) {
        tmList.clear();
        totalPriceLbl.setText("0.00");
        PlaceOrderTbl.refresh();

    }

    public void clear(){
//        PNametxt.clear();
//        PAddresstxt.clear();
//        PCitytxt.clear();
        PItemCodeCmb.getSelectionModel().clearSelection();
        PDescriptiontxt.clear();
        PQtyOnHandtxt.clear();
        PUnitPricetxt.clear();
        PDisconttxt.clear();
        PQtytxt.clear();
        PItemCodeCmb.requestFocus();

    }

    public void clearAll(){
        PCustomerCmb.getSelectionModel().clearSelection();
        PItemCodeCmb.getSelectionModel().clearSelection();
        PNametxt.clear();
        PAddresstxt.clear();
        PCitytxt.clear();
        PDescriptiontxt.clear();
        PQtyOnHandtxt.clear();
        PUnitPricetxt.clear();
        PDisconttxt.clear();
        PQtytxt.clear();
        PItemCodeCmb.requestFocus();
        PCustomerCmb.requestFocus();

    }

    public void ChangePlOnAction(ActionEvent actionEvent) {
        PDisconttxt.requestFocus();
    }
}

