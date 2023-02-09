package UserData;
public class BillingInfo {
    private String card;
    private String cvv;
    private String expDate;

    public BillingInfo(String card, String cvv, String expDate){
        this.card = card;
        this.cvv = cvv;
        this.expDate = expDate;
    }
    
    public String getCard(){
        return(this.card);
    }
    public String getCVV(){
        return(this.cvv);
    }
    public String getExpDate(){
        return(this.expDate);
    }
}
