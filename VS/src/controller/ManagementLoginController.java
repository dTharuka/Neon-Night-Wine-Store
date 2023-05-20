package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ManagementLoginController {
    public AnchorPane anchorPane;
    public AnchorPane ManagementPopupContext;
    public PasswordField ManagementLoginPasswordtxt;
    public TextField ManagementLoginUsereNametxt;
    int attempts = 0;
//------------------Login To Management Area-----------------------------------------

    public void managementLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query = "SELECT * FROM Login";
        PreparedStatement stm = con.prepareStatement(query);

        String UserName = ManagementLoginUsereNametxt.getText();
        String Password = ManagementLoginPasswordtxt.getText();

        ResultSet rst = stm.executeQuery(query);
        if (rst.next()) {
            attempts++;
            if (attempts <= 5) {
                if (UserName.equals(rst.getString
                        (1))&& Password.equals(rst.getString(2))) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ManagementDashBordForm.fxml"));
                    Parent parent = fxmlLoader.load();
                    Scene scene = new Scene(parent);
                    Stage stage1 = (Stage) anchorPane.getScene().getWindow();
                    Stage stage2 = (Stage) ManagementPopupContext.getScene().getWindow();
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage1.close();
                    stage2.close();
                    stage.centerOnScreen();
                    stage.show();


                    new Alert(Alert.AlertType.CONFIRMATION, "Successful").show();

                }  else {
                    //try again
                    //NotificationController.LoginUnSuccessfulNotification("Cashier", attempts);
                    new Alert(Alert.AlertType.WARNING, "Your username and password is incorrect.!\nYou Have 5 time trys and this is " + attempts + ".").show();
                }
            } else {
                ManagementLoginUsereNametxt.setEditable(false);
                ManagementLoginPasswordtxt.setEditable(false);
                new Alert(Alert.AlertType.WARNING, "Account is Temporarily Disabled or You Did not Sign in Correctly. ").show();
                /* NotificationController.EmptyDataNotification("Account is Temporarily Disabled or You Did not Sign in Correctly");*/
            }
        }
    }



                public void setController (AnchorPane anchorPane){
                    this.anchorPane = anchorPane;
                }

    public void ChangeOnAction(ActionEvent actionEvent) {
        ManagementLoginPasswordtxt.requestFocus();
    }



            }


