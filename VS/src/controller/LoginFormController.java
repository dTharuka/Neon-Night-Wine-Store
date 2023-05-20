package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    public AnchorPane anchorePaneContext;
    public TextField txtChaserUsername;
    public PasswordField txtPassword;
    int attempts = 0;

//    -------------------------Login Cashier Area---------------------------------

    public void btnCashierLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query = "SELECT * FROM Cashier";
        PreparedStatement stm = con.prepareStatement(query);

        String CashierName = txtChaserUsername.getText();
        String CPassword = txtPassword.getText();

        ResultSet rst = stm.executeQuery(query);
        if (rst.next()) {
            attempts++;
            if (attempts <= 5) {
                if (CashierName.equals(rst.getString
                        (2)) && CPassword.equals(rst.getString(6))) {
                    URL resource = getClass().getResource("/views/CashierForm.fxml");
                    Parent load = FXMLLoader.load(resource);
                    Stage window = (Stage) anchorePaneContext.getScene().getWindow();
                    window.setScene(new Scene(load));
//                    NotificationController.LoginSuccessfulNotification("Cashier");
                    new Alert(Alert.AlertType.CONFIRMATION, "Successful").show();

                }  else {
                    //try again
                    //NotificationController.LoginUnSuccessfulNotification("Cashier", attempts);
                    new Alert(Alert.AlertType.WARNING, "Your username and password is incorrect.!\nYou Have 5 time trys and this is " + attempts + ".").show();
                }
            } else {
                txtChaserUsername.setEditable(false);
                txtPassword.setEditable(false);
                new Alert(Alert.AlertType.WARNING, "Account is Temporarily Disabled or You Did not Sign in Correctly. ").show();
               /* NotificationController.EmptyDataNotification("Account is Temporarily Disabled or You Did not Sign in Correctly");*/
            }
        }
    }



//------------------------------Go To Management Login--------------------------------------------

    public void btnManagementLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ManagementLogin.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        ManagementLoginController adminFormController = fxmlLoader.getController();
        adminFormController.setController(anchorePaneContext);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("");
        stage.centerOnScreen();
        stage.show();


   /* private void setUi(String URI) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource(URI+".fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }*/

    }
//----------------------Change Next Text Field---------------------------------------

    public void ChangePOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }
}
