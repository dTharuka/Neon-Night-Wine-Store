package modle;

public class order {
   private String OrderID;
   private String CustomerID;
   private String OrderDate;
    private String OrderTime;

    public order() {
    }

    public order(String orderID, String customerID, String orderDate, String orderTime) {
        OrderID = orderID;
        CustomerID = customerID;
        OrderDate = orderDate;
        OrderTime = orderTime;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    @Override
    public String toString() {
        return "order{" +
                "OrderID='" + OrderID + '\'' +
                ", CustomerID='" + CustomerID + '\'' +
                ", OrderDate='" + OrderDate + '\'' +
                ", OrderTime='" + OrderTime + '\'' +
                '}';
    }
}
