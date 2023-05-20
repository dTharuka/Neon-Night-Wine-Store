package modle;

public class item {

   private String ICode;
   private String IDescription;
   private String PackSize;
   private double UPrice;
   private int QtyOnHand;
   private String Date;

    public item() {
    }

    public item(String ICode, String IDescription, String packSize, double UPrice, int qtyOnHand, String date) {
        this.ICode = ICode;
        this.IDescription = IDescription;
        PackSize = packSize;
        this.UPrice = UPrice;
        QtyOnHand = qtyOnHand;
        Date = date;
    }

    public String getICode() {
        return ICode;
    }

    public void setICode(String ICode) {
        this.ICode = ICode;
    }

    public String getIDescription() {
        return IDescription;
    }

    public void setIDescription(String IDescription) {
        this.IDescription = IDescription;
    }

    public String getPackSize() {
        return PackSize;
    }

    public void setPackSize(String packSize) {
        PackSize = packSize;
    }

    public double getUPrice() {
        return UPrice;
    }

    public void setUPrice(double UPrice) {
        this.UPrice = UPrice;
    }

    public int getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        QtyOnHand = qtyOnHand;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "item{" +
                "ICode='" + ICode + '\'' +
                ", IDescription='" + IDescription + '\'' +
                ", PackSize='" + PackSize + '\'' +
                ", UPrice=" + UPrice +
                ", QtyOnHand=" + QtyOnHand +
                ", Date='" + Date + '\'' +
                '}';
    }
}


