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

public class ManagementDashBordFormController {
    public AnchorPane managementPainContext;
    public AnchorPane ManagementPaneContext;

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        loadMnagementUi("ItemForm");
    }
    private void loadMnagementUi(String location) throws IOException {
        managementPainContext.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"));
        managementPainContext.getChildren().add(parent);
    }

    public void btnAllDetailsOnAction(ActionEvent actionEvent) throws IOException {
        loadMnagementUi("AllDetailsForm");
    }

    public void btnCashierDetailsOnAction(ActionEvent actionEvent) throws IOException {
        loadMnagementUi("CashierDetailsForm");
    }

    public void btnItemDetailsOnAction(ActionEvent actionEvent) throws IOException {
        loadMnagementUi("ItemDetailsForm");
    }

    public void addCashierBtnOnAction(ActionEvent actionEvent) throws IOException {
        loadMnagementUi("AddCashierForm");
    }

    public void MOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        loadMnagementUi("MOrderDetailsForm");

    }

    public void ManagementLogoutBtnOnAction(ActionEvent actionEvent) throws IOException {
//        URL resource = getClass().getResource("/views/LoginForm.fxml");
//        Parent load = FXMLLoader.load(resource);
//        Stage window = (Stage) ManagementPaneContext.getScene().getWindow();
//        window.setScene(new Scene(load));
        Stage stage = (Stage) ManagementPaneContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/LoginForm.fxml"))));
        stage.centerOnScreen();
    }

    public void btnSystemReportOnAction(ActionEvent actionEvent) throws IOException {
        loadMnagementUi("SystemReportForm");
    }

    public void btnOrdersOnAction(ActionEvent actionEvent) throws IOException {
        loadMnagementUi("MOrderForm");
    }
//    ==================================================================================================

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {


        if (mouseEvent.getSource() instanceof Button) {
            Button btn =(Button) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), btn);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.NAVY);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            btn.setEffect(glow);
        }
    }
    public void playMouseExitedAnimation(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() instanceof Button) {
            Button btn =(Button) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), btn);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            btn.setEffect(null);
        }
    }



//    ==================================================================================================
}
