package UserData;

import java.util.Vector;

import TicketManagement.*;

public class User {
    private int ID;
    private String username;
    private String password;
    private String email;
    
    private String registrationDate;
    private BillingInfo billingInfo;
    private String card;
    private String CVV;
    private String expDate;
    private int hasPaid;
    private Vector<Ticket> purchasedTickets;
    private double storeCredit;

    public User(int ID, String username, String password, 
    String Date, String Card, String CVV, String expDate, String email,int hasPaid){
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.registrationDate = Date;
        this.billingInfo = new BillingInfo(Card, CVV, expDate);
        this.card = Card;
        this.CVV = CVV;
        this.expDate = expDate;
        this.email = email;
        this.hasPaid = hasPaid; 
        this.purchasedTickets = new Vector<Ticket>();
        this.storeCredit = 0;
    }

    public int getID(){
        return(this.ID);
    }
    public String getUsername(){
        return(this.username);
    }
    public String getPassword(){
        return(this.password);
    }
    public String getDate(){
        return(this.registrationDate);
    }
    public String getCard(){
        return(this.card);
    }
    public String getCVV(){
        return(this.CVV);
    }
    public String getExpDate(){
        return(this.expDate);
    }
    public BillingInfo getBillingInfo(){
        return(this.billingInfo);
    }
    public String getEmail(){
        return(this.email);
    }
    public int hasPaid(){
        return(this.hasPaid);
    }
    public void renewPayment(int hasPaid){
        this.hasPaid = hasPaid;
    }

    public void addTickets(Vector<Ticket> newTickets){
        for(Ticket t: newTickets){
            purchasedTickets.add(t);
        }
    }

    public Vector<Ticket> getPurchasedTickets(){
        return purchasedTickets;
    }

    public void updateStoreCredit(double credit){
        storeCredit += credit;
    }

    public double getStoreCredit(){
        return storeCredit;
    }
}
