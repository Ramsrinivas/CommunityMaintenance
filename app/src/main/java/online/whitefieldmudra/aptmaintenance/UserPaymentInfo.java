package online.whitefieldmudra.aptmaintenance;

/**
 * Created by welcome on 12-12-2017.
 */

public class UserPaymentInfo {

    private UserInfo userInfoForPayment;
    private String paymentMonth;
    private int paymentYear;
    private String paymentStatus;
    private float actualAmount;
    private float paidAmount;
    //User Details
    public void setResidentData(UserInfo value) {userInfoForPayment = value; }
    public UserInfo getResidentData() { return userInfoForPayment; }

    //Month of Payment
    public void setPaymentMonth(String value) {paymentMonth = value; }
    public String getPaymentMonth() {return paymentMonth; }

    //Year of Payment
    public void setPaymentYear(int value) {paymentYear = value; }
    public int getPaymentoYear() {return paymentYear; }

    //Payment status
    public void setPaymentStatus(String value) {paymentStatus = value; }
    public String getPaymentoStatus() {return paymentStatus; }

    //actualAmount
    public void setactualAmount(float value) {actualAmount = value; }
    public float getactualAmount() {return actualAmount; }

    //paidAmount
    public void setpaidAmount(float value) {paidAmount = value; }
    public float getpaidAmount() {return paidAmount; }
}
