package TicketManagement;

import MovieData.*;

import java.text.DecimalFormat;
import java.util.*;

public class Ticket // this class constructs Ticket objects that hold a Movie object and the seat a user has registered for that movie, allowing them to purchase the seat
{
    private int cancelationCode;
    private double price;
    private Movie movie;
    private int seatNumber;
    private int UserID;
    private Boolean purchased;

    public Ticket ()
    {   
        Random rand = new Random();
        cancelationCode = rand.nextInt(9999); 
        price = 10;
        purchased = false;
    }

    public Ticket (Movie movie, int seatNumber, int UserID)// an overloaded constructor to create Ticket Objects for specific movies 
    {   
        this.UserID = UserID;
        this.movie = movie;
        this.seatNumber = seatNumber;
        Random rand = new Random();
        cancelationCode = rand.nextInt(9999); 
        price = 10;
        purchased = false;
    }

    public Ticket (Movie movie, int seatNumber, int UserID , int cancelationCode){ // an overloaded constructor to create Ticket Objects for specific movies for regular users
        this.cancelationCode = cancelationCode;
        this.movie = movie;
        this.seatNumber = seatNumber;
        this.UserID = UserID;
        Random rand = new Random();
        cancelationCode = rand.nextInt(9999); 
        price = 10;
        purchased = false;
    }

    public double getPrice(){
        return price;
    }
    
    public Movie getMovie() {
    	return movie;
    }
    
    public int getSeatNumber() {
    	return seatNumber;
    }
    
    public int getCancelationCode() {
    	return cancelationCode;
    }
    public boolean isPaid(){
        return(this.purchased);
    }
    public void Pay(){
        this.purchased = true;
    }
    public int getUserID(){
        return(this.UserID);
    }
    
    public String TicketFormat() { // this takes the raw format of the data and creates a ticket-formatted string to show on the recicipt
    	DecimalFormat tens = new DecimalFormat("00.00");
    	String ticketInfo = 
    	"Ticket " + cancelationCode + ":    $ " + tens.format(price) + "\n  " 
    	+ movie.getTitle() + "\n  " 
    	+ movie.getTheatre().getName() + "\n  " 
    	+ movie.getShowTime().getTime() + "\n  "
    	+ "Seat " + seatNumber + "\n\n";
    	return ticketInfo;
    }
}