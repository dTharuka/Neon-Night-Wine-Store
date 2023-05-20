package controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

public class SystemReportFormController {

    public AnchorPane incomePainContext;

    private void loadUi(String location) throws IOException {
        incomePainContext.getChildren().clear();
        Parent parent= FXMLLoader.load(getClass().getResource("../views/"+location+".fxml"));
        incomePainContext.getChildren().add(parent);
    }

    public void dailyIncomeBtn(ActionEvent actionEvent) throws IOException {
        loadUi("DailyIncome");
    }

    public void manthlyIncomeBtn(ActionEvent actionEvent) throws IOException {
        loadUi("ManthlyIncome");
    }

    public void annullyIncomeBtn(ActionEvent actionEvent) throws IOException {
        loadUi("AnnullyIncome");
    }


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

}
