package modle;

public class cashier {
    String cashierId;
    String cashierName;
    String cashierNIC;
    String cashierContact;
    double cashierSalary;
    String cashierPwd;

    public cashier() {
    }

    public cashier(String cashierId, String cashierName, String cashierNIC, String cashierContact, double cashierSalary, String cashierPwd) {
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.cashierNIC = cashierNIC;
        this.cashierContact = cashierContact;
        this.cashierSalary = cashierSalary;
        this.cashierPwd = cashierPwd;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getCashierPwd() {
        return cashierPwd;
    }

    public void setCashierPwd(String cashierPwd) {
        this.cashierPwd = cashierPwd;
    }

    public String getCashierContact() {
        return cashierContact;
    }

    public void setCashierContact(String cashierContact) {
        this.cashierContact = cashierContact;
    }

    public String getCashierNIC() {
        return cashierNIC;
    }

    public void setCashierNIC(String cashierNIC) {
        this.cashierNIC = cashierNIC;
    }

    public double getCashierSalary() {
        return cashierSalary;
    }

    public void setCashierSalary(double cashierSalary) {
        this.cashierSalary = cashierSalary;
    }

    @Override
    public String toString() {
        return "cashier{" +
                "cashierId='" + cashierId + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", cashierNIC='" + cashierNIC + '\'' +
                ", cashierContact='" + cashierContact + '\'' +
                ", cashierSalary=" + cashierSalary +
                ", cashierPwd='" + cashierPwd + '\'' +
                '}';
    }
}