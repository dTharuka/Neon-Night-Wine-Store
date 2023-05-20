package controller;

import modle.order;
import modle.orderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
    public boolean saveOrder(order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO `Order` VALUES(?,?,?,?)",
                order.getOrderID(), order.getCustomerID(), order.getOrderDate(), order.getOrderTime());


    }

    public boolean saveOrderDetails(ArrayList<orderDetails> details) throws SQLException, ClassNotFoundException {
        for (orderDetails det : details
        ) {
            boolean isDetailsSaved = CrudUtil.execute("INSERT INTO `Order Details` VALUES(?,?,?,?,?)",
                    det.getOrderID(), det.getItemCode(), det.getOrderqty(), det.getDiscount(), det.getPrice());
            if (isDetailsSaved) {
                if (!updateQty(det.getItemCode(), det.getOrderqty())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE ITEM SET QtyOnHand=QtyOnHand-? WHERE ItemCode = ?", qty, itemCode);
    }

//    public String getOrderId() throws SQLException, ClassNotFoundException {
//        ResultSet set = CrudUtil.execute("SELECT OrderID FROM  `Order` ORDER BY OrderID DESC LIMIT 1");
//        if(set.next()){
//        int tempId = Integer.parseInt(set.getString(1).split("-")[1]);
//            tempId += 1;
//            if (tempId < 1000) {
//                return "O-00" + tempId;
//            } else {
//                return "0-0" + tempId;
//            }
//        }
//
//        return "O-001";
//    }
public String getOrderId() throws SQLException, ClassNotFoundException {
    ResultSet set = CrudUtil.execute("SELECT OrderID FROM  `Order` ORDER BY OrderID DESC LIMIT 1");
    if (set.next()) {
        int tempId = Integer.parseInt(set.getString(1).split("-")[1]);
//            tempId += 1;
//            if (tempId < 10000) {
//                return "O-0" + tempId;
//            } else {
//                return "-" + tempId;
//            }
        tempId = tempId + 1;
        if (tempId <= 9) {
            return "O-00" + tempId;
        } else if (tempId <= 99) {
            return "O-0" + tempId;
        } else {
            return "O-" + tempId;
        }
    } else {
        return "O-001";
    }

}
}

