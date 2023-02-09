package MovieData;

import java.time.LocalDateTime;

//contains date and time of the movie, as well as the seat array
public class Showtime {
    private String time;
    private String showdate;
    private LocalDateTime dateTime;
    private Seat seats;
    public Showtime(int theTime, String date, String theSeats){ // this contains both the time and date that the movie is playing at
        String timestamp = "AM"; // this block breaks the 24-hour format of the database into an AM-PM clock string
        int hour = theTime / 100;
        int minute = (theTime - (hour*100));
        if(hour >= 12){
            if(hour > 12){
                hour = hour - 12;
            }
            timestamp = "PM";
        } 
        StringBuilder TimeItem = new StringBuilder();
        TimeItem.append(hour);
        TimeItem.append(":");
        if(minute < 10){
            TimeItem.append("0" + minute);
        }
        else{
            TimeItem.append(minute);
        }
        TimeItem.append(" ");
        TimeItem.append(timestamp);
        this.time = TimeItem.toString();
        this.seats = new Seat(theSeats);
        // this block breaks the date string up into a DD-MM-YYYY format from the database's YYYYMMDD format
        this.showdate = (date.substring(6, 8)) + "-" + (date.substring(4, 6)) + "-" + (date.substring(0,4));

        int day = Integer.valueOf(date.substring(6, 8));
        int month = Integer.valueOf(date.substring(4, 6));
        int year = Integer.valueOf(date.substring(0, 4));
        dateTime = LocalDateTime.of(year, month, day, hour, minute);
    }
    public String getTime(){
        return(this.time);
    }
    public Seat getSeats(){
        return(this.seats);
    }
    public String getDate(){
        return(this.showdate);
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }
    public int convertTime(){ // this method converts the AM-PM clock format back into a 24-hour style fassion for insertion into the database
        int hour = Integer.parseInt(this.time.substring(0,this.time.indexOf(":")));
        if((this.time.substring(7).equals("PM"))||(this.time.substring(6).equals("PM"))){
            hour = hour + 12;
        }
        int minute = Integer.parseInt(this.time.substring(this.time.indexOf(":")+1,this.time.indexOf(":")+3));
        int finalTime = (hour*100) + minute;
        return(finalTime);
    }
    public String convertDate(){ // this method converts the DD-MM-YYYY format of the date into a YYYYMMDD format for insertion into the database
        StringBuilder theDate = new StringBuilder();
        theDate.append(this.showdate.substring(5, 9));
        theDate.append(this.showdate.substring(4,6));
        theDate.append(this.showdate.substring(7,9));
        
        System.out.println(theDate.toString());
        return(theDate.toString());
    }
}
