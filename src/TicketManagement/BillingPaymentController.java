package TicketManagement;

import UserData.*;

public class BillingPaymentController { // this class is used to keep track of getting a User's payment information and associating it with a payment object
    private BillingInfo bi;
    private Payment payment;

    public BillingPaymentController(Payment payment){
        this.payment = payment;
    }

    public void setBillingInfo(BillingInfo bi){
        this.bi = bi;
    }
    
    public void setBillingInfo(String creditCardNo, String expDate, String cvv){
        this.bi = new BillingInfo(creditCardNo, expDate, cvv);
    }
    
    public Payment getPayment() {
    	return payment;
    }
}