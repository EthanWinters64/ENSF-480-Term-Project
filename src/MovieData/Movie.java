package MovieData;

// contains data for a movie object, including Title, Theatre, and Showtime
public class Movie {
    private int ID; // the main key in the database
    private Theatre Theatre; 
    private Showtime Showtime; 
    private String title;
    public Movie(int ID, String title, Theatre Thea, Showtime theShow){
        this.ID = ID;
        this.Theatre = Thea;
        this.Showtime = theShow;
        this.title = title;
    }

    //getters and setters
    public Theatre getTheatre(){
        return(this.Theatre);
    }
    public Showtime getShowTime(){
        return(this.Showtime);
    }
    public String getTitle(){
        return(this.title);
    }
    public int getID(){
        return(this.ID);
    }
}
