package UserInterface;

import java.util.Vector;

import MovieData.Movie;
import MovieData.MovieList;
import TicketManagement.Payment;
import TicketManagement.Ticket;

public class TicketSelectionController {
	private MovieList Mov;
	private Vector<String> TitleList;
	
	private String MovieTitle;
    private String TheatreName;
    private String Show;
    private Movie theMovie;
	
    // constructor with movie list, also creates titleList, a string vector containing only unique movie titles
	public TicketSelectionController(MovieList Mov) {
		this.Mov = Mov; 
        
        TitleList = new Vector<String>();
        for(int i=0; i<Mov.getSize(); i++){
            if(!(TitleList.contains((Mov.getMovieAt(i).getTitle())))){
                TitleList.add(Mov.getMovieAt(i).getTitle());
            }
        }
	}

    // checks first if any Titles match the given string, 
    // then check if any titles contain a substring of the given string and return the results as a String Vector
	public Vector<String> SearchResults(String search){
		Vector<String> results = new Vector<String>();
		
		if(TitleList.contains(search)) {
			results.add(search);
		}
		
		for(String title: TitleList) {
			if(title.contains(search) && !results.contains(title)) {
				results.add(title);
			}
		}
		
		return results;
	}
	
    // create a new payment object with the indices of the selected seats
	public Payment getPayment(Vector<Integer> seatNumbers, int UserID){
		Vector<Ticket> ticketList = new Vector<Ticket>();
		for(int i: seatNumbers) {
			ticketList.add(new Ticket(theMovie,i, UserID));
		}
		Payment p = new Payment(ticketList);
		return p;
	}

    // getters and setters
	
	public String getMovieTitle() {
		return MovieTitle;
	}
	
	public String getTheatreName() {
		return TheatreName;
	}
	
	public String getShow() {
		return Show;
	}
	
	public Movie getTheMovie() {
		return theMovie;
	}

    public MovieList getMov() {
		return Mov;
	}

    public Vector<String> getTitleList() {
		return TitleList;
	}
	
	public void setMovieTitle(String movieTitle) {
		this.MovieTitle = movieTitle;
	}
	
	public void setTheatreName(String theatreName) {
		this.TheatreName = theatreName;
	}
	
	public void setShow(String show) {
		this.Show = show;
	}
	
	public void setTheMovie(Movie theMovie) {
		this.theMovie = theMovie;
	}
}
