package controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class CashierFormController {
    public AnchorPane anchorPane1Context;
    public AnchorPane logoutPainContext;
    public AnchorPane anchorPane2Context;

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("CustomerForm");

    }


    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("OrderForm");

    }

    private void loadUi(String location) throws IOException {
        anchorPane1Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../views/" + location + ".fxml"));
        anchorPane1Context.getChildren().add(parent);
    }

    public void btnOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("OrderDetailsForm");
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("PlaceOrderForm");
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/views/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) logoutPainContext.getScene().getWindow();
        window.setScene(new Scene(load));


    }

    public void CItemDetailsBtnOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("CashierItemForm");
    }

//    ===========================================================================================

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {


        if (mouseEvent.getSource() instanceof Button) {
            Button btn = (Button) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), btn);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.YELLOW);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            btn.setEffect(glow);
        }
    }

    public void playMouseExitedAnimation(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() instanceof Button) {
            Button btn = (Button) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), btn);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            btn.setEffect(null);
        }

//    ===========================================================================================
    }
}
