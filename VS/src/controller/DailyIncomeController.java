package controller;


import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import modle.IncomeReport;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DailyIncomeController implements Initializable {
    public AnchorPane DIncomeAnc;

    public TableView<IncomeReport> tblReport;
    public TableColumn colDate;
    public TableColumn colOrderCost;
    public TableColumn colItemQty;
    public TableColumn colCost;
    public AnchorPane dailyIncomeReportContext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderCost.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("numberOfSoldItem"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("finalCost"));


        try {
            tblReport.setItems(loadDailyIncomeReport());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    ------------------------Get Daily Income----------------------------------------------

    private ObservableList<IncomeReport> loadDailyIncomeReport() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT `Order`.OrderDate,count(`Order`.OrderID),sum(`Order Details`.Price) FROM `Order` INNER JOIN `Order Details` ON `Order`.OrderID = `Order Details`.OrderID GROUP BY OrderDate");
        ResultSet rst = stm.executeQuery();
        ObservableList<IncomeReport> obList = FXCollections.observableArrayList();
        ArrayList<IncomeReport> data = getCountItems();
        int i = 0;
        while (rst.next()) {
            String date = rst.getString(1);
            int countOrderId = rst.getInt(2);
            double sumOfTotal = rst.getDouble(3);

            obList.add(new IncomeReport(date, countOrderId, data.get(i).getNumberOfSoldItem(), sumOfTotal));
            i++;
        }
        return obList;
    }

//    ----------------------------Get Daily Item Count--------------------------

    private ArrayList<IncomeReport> getCountItems() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT DISTINCT(`Order`.OrderDate),sum(`Order Details`.OrderQty) FROM `Order` INNER JOIN `Order Details` ON  `Order`.OrderID = `Order Details`.OrderID GROUP BY `Order`.OrderDate");
        ResultSet rst = stm.executeQuery();
        ArrayList<IncomeReport> temp = new ArrayList();

        while (rst.next()) {
            temp.add(new IncomeReport(
                    rst.getString(1), rst.getInt(2)));
        }
        return temp;
    }


}
