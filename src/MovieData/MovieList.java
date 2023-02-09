package MovieData;
import java.util.*;

//list of all movie objects from the database
public class MovieList {
    private ArrayList<Movie> theMovies;
    private int size;
    public MovieList(){
        this.theMovies = new ArrayList<Movie>(); 
        this.size = 0;
    }
    public void addMovie(Movie theMovie){ // add a movie to the list
        this.theMovies.add(theMovie);
        this.size++;
    }
    public ArrayList<Movie> getMovies(){ 
        return(this.theMovies);
    }
    public Movie getMovieAt(int index){
        return(theMovies.get(index));
    }
    public int getSize(){
        return(this.size);
    }
}
