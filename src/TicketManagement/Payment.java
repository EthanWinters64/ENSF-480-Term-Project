package TicketManagement;

import java.text.DecimalFormat;
import java.util.Vector;

public class Payment { // this class is used to keep track of the money owed for an order, depending on how many tickets a user has and whether or not a registered user has paid their fee
    private double AnnualFeePayment;
    private Vector<Ticket> tickets;
    private double StoreCredit;
    private double subTotal;
    private double gst;
    private double total;

    public Payment(double AnnualFeePayment){
        this.AnnualFeePayment = AnnualFeePayment;
        tickets = new Vector<Ticket>();
        StoreCredit = 0;
    }

    public Payment(Vector<Ticket> tickets){
        this.tickets = tickets;
        AnnualFeePayment = 0;
        StoreCredit = 0;
    }

    public double calculateTotal(){ // this cacluates the total money owed
        for(Ticket t: tickets){
            subTotal += t.getPrice();
        }
        subTotal += AnnualFeePayment;

        gst = subTotal * 0.05;
        total = subTotal + gst - StoreCredit;
        return total;
    }

    public String receiptBuilder() { // this assembles a query-like string that showcases all the information about a transation
    	String receipt = "";
    	calculateTotal();
    	
    	DecimalFormat hundreds = new DecimalFormat("000.00");
    	DecimalFormat tens = new DecimalFormat("00.00");
    	DecimalFormat ones = new DecimalFormat("0.00");
    	for(int i=0; i<tickets.size(); i++){
    		receipt = receipt + tickets.elementAt(i).TicketFormat();
    	}
    	
    	if(AnnualFeePayment != 0) { // flags like this determine what goes into the final string
    		receipt = receipt + "Annual Fee:    $ " + tens.format(AnnualFeePayment) + "\n\n";
    	}
    	
    	if(subTotal >= 100) {
    		receipt = receipt + "Subtotal:      $" + hundreds.format(subTotal) + "\n";
    	}
    	else if(subTotal >= 10) {
    		receipt = receipt + "Subtotal:      $ " + tens.format(subTotal) + "\n";
    	}
    	else{
    		receipt = receipt + "Subtotal:      $  " + ones.format(subTotal) + "\n";
    	}
    	
    	if(gst >= 10) {
    		receipt = receipt + "GST:           $ " + tens.format(gst) + "\n";
    	}
    	else{
    		receipt = receipt + "GST:           $  " + ones.format(gst) + "\n";
    	}
    	
    	if(StoreCredit >= 100) {
    		receipt = receipt + "Store Credit: -$ " + hundreds.format(StoreCredit) + "\n\n";
    	}
    	else if(StoreCredit >= 10) {
    		receipt = receipt + "Store Credit: -$ " + tens.format(StoreCredit) + "\n\n";
    	}
    	else if(StoreCredit >= 10) {
    		receipt = receipt + "Store Credit: -$ " + ones.format(StoreCredit) + "\n\n";
    	}
    	
    	if(total >= 100) {
    		receipt = receipt + "Total:         $" + hundreds.format(total) + "\n";
    	}
    	else if(total >= 10) {
    		receipt = receipt + "Total:         $ " + tens.format(total) + "\n";
    	}
    	else{
    		receipt = receipt + "Total:         $  " + ones.format(total) + "\n";
    	}
    	
    	return receipt;
    }

    public void addTickets(Vector<Ticket> newtickets){
        for(Ticket t:newtickets){
            tickets.add(t);
        }
    }

    public Vector<Ticket> getTickets(){
		return(this.tickets);
	}

    public void setAnnualFeePayment(double afp){
        AnnualFeePayment = afp;
    }

    public void setStoreCredit(double sc){
        StoreCredit = sc;
    }
}