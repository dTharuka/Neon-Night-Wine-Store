package modle;

public class customer {
   private String CId;
   private String Title;
   private String CName;
   private String Address;
   private String City;
   private String Province;
   private String CPostalCode;
   private String Date;
   private String Time;

    public customer() {
    }

    public customer(String CId, String title, String CName, String address, String city, String province, String CPostalCode, String date, String time) {
        this.CId = CId;
        Title = title;
        this.CName = CName;
        Address = address;
        City = city;
        Province = province;
        this.CPostalCode = CPostalCode;
        Date = date;
        Time = time;
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCPostalCode() {
        return CPostalCode;
    }

    public void setCPostalCode(String CPostalCode) {
        this.CPostalCode = CPostalCode;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    @Override
    public String toString() {
        return "customer{" +
                "CId='" + CId + '\'' +
                ", Title='" + Title + '\'' +
                ", CName='" + CName + '\'' +
                ", Address='" + Address + '\'' +
                ", City='" + City + '\'' +
                ", Province='" + Province + '\'' +
                ", CPostalCode='" + CPostalCode + '\'' +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                '}';
    }
}
