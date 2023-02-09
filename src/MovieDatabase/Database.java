package MovieDatabase;
import java.sql.*;
import java.text.ParseException;
import java.util.Vector;

import MovieData.*; // this imports the entire MovieData package
import TicketManagement.Ticket;
import UserData.*;

public class Database {
    private String username = "myuser";
    private String password = "xxxx";
    private String url = "jdbc:mysql://localhost:3306/movies";
    private volatile MovieList MovieList; // the keyword volatile allows the data member to be multi-thread safe
    private volatile UserList UserList; 
    private volatile Vector<Ticket> TicketList;

    public Database() throws DatabaseException, SQLException, ParseException{ // this method updates the MovieList, UserList and TickeList of the new database
        boolean updated = updateMovieList();
        int attempts = 0;
        while((updated == false) && (attempts < 10)){ // this keeps trying to connect to the database
            updated = updateMovieList();
            attempts++;
        }
        if(updated == false){
            throw new DatabaseException();
        }
        updated = updateUserList();
        attempts = 0;
        while((updated == false) && (attempts < 10)){
            updated = updateUserList();
            attempts++;
        }
        if(updated == false){
            throw new DatabaseException();
        }
        updated = updateTicketList();
        attempts = 0;
        while((updated == false) && (attempts < 10)){
            updated = updateTicketList();
            attempts++;
        }
        if(updated == false){
            throw new DatabaseException();
        }
    }

    public boolean updateMovieList() throws SQLException{ // this updates only the MovieList, which is done after items are added and/or removed from the database
        this.MovieList = null;
        boolean connection = true;
        MovieList MovList = new MovieList();
        try(
            Connection DBConnection = DriverManager.getConnection(url, username, password);
            
            Statement State = DBConnection.createStatement();
        ) {
            String query = "SELECT * FROM MOVIE_LIST";
            ResultSet results = State.executeQuery(query);
            while(results.next()){ // use this loop to build and format Movie objects to add to the list
                int ID = results.getInt(1);
                String title = results.getString(2);
                String theatreName = results.getString(3);
                int time = results.getInt(4);
                String dateString = results.getString(5);
                String seatArray = results.getString(6);
                Theatre theTheatre = new Theatre(theatreName);
                Showtime theShow = new Showtime(time, dateString, seatArray);
                Movie theMovie = new Movie(ID, title, theTheatre, theShow);
                MovList.addMovie(theMovie);
            }
            this.MovieList = MovList;
        } catch(SQLException ex){
            ex.printStackTrace();
        }     
        return(connection); // this is used to ensure the connection to the database was successful
    }
    public MovieList getMovieList(){
        return(this.MovieList);
    }

    public boolean updateUserList() throws SQLException, ParseException{ // this updates only the UserList, which is done when Users are inserted and/or removed from the database
        this.UserList = null;
        boolean connection = true;
        UserList UList = new UserList();
        try(
            Connection DBConnection = DriverManager.getConnection(url, username, password);
            Statement State = DBConnection.createStatement();
        ) {
            String query = "SELECT * FROM USER_LIST";
            ResultSet results = State.executeQuery(query);
            while(results.next()){ // this block builds User objects to fill the userlist
                int ID = results.getInt(1);
                String Username = results.getString(2);
                String Password = results.getString(3);
                String Date = results.getString(4);
                String Card = results.getString(5);
                String CVV = results.getString(6);
                String expDate = results.getString(7);
                String Email = results.getString(8);
                int hasPaid = results.getInt(9);
                User newUser = new User(ID, Username, Password, Date, Card, CVV, expDate, Email, hasPaid);
                UList.addUser(newUser);
            }
            this.UserList = UList;
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return(connection);
    }
    public UserList getUserList(){
        return(this.UserList);
    }

    public boolean updateSeats(Movie Mov, int seatNum, int status) throws SQLException{ // this method updates the seats objects associated with a movie object, which is done after seat(s) are booked
        StringBuilder seatstring = new StringBuilder();
        int[] theSeats = Mov.getShowTime().getSeats().getSeatArray();
        for(int i = 0; i < theSeats.length; i++){
            if(i == seatNum){
                seatstring.append(status);
            }
            else{
                seatstring.append(theSeats[i]);
            }
        }
        String Seats = seatstring.toString(); // this and the above code involves converting the seat array into a character string to store in the database
        boolean success = true;
        try (
            Connection DBConnection = DriverManager.getConnection(url, username, password);
            Statement State = DBConnection.createStatement();
        ) {
            StringBuilder theUpdate = new StringBuilder(); // use the stringbuilder to construct a query statement
            theUpdate.append("update movie_list set Seats = ");
            theUpdate.append(Seats);
            theUpdate.append(" where MovieID = ");
            theUpdate.append(Mov.getID());
            int UpdatedRows = State.executeUpdate(theUpdate.toString());
            System.out.println(theUpdate.toString());
            System.out.println("Rows Affected: " + UpdatedRows);
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return(success);
    }

    public void addRegisteredUser(User theUser) throws SQLException{ // this is used to add a registered user after one signs up
        try(
            Connection DBConnection = DriverManager.getConnection(url, username, password);
            Statement State = DBConnection.createStatement();
        ) {
            StringBuilder insert = new StringBuilder(); // this stringbuilder is used to build a qeury from the user data
            insert.append("insert into User_List values (");
            insert.append(theUser.getID());
            insert.append(", '");
            insert.append(theUser.getUsername());
            insert.append("', '");
            insert.append(theUser.getPassword());
            insert.append("', '");
            insert.append(theUser.getDate());
            insert.append("', '");
            insert.append(theUser.getCard());
            insert.append("', '");
            insert.append(theUser.getCVV());
            insert.append("', '");
            insert.append(theUser.getExpDate());
            insert.append("', '");
            insert.append(theUser.getEmail());
            insert.append("', ");
            insert.append(theUser.hasPaid());
            insert.append(")");
            System.out.println(insert.toString());
            int UpdatedRows = State.executeUpdate(insert.toString());
            System.out.print("Rows added: " + UpdatedRows + '\n');
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public boolean addToTicketList(Vector<Ticket> theTickets) throws SQLException{ // this is used to add a new ticket to the database
        try(
            Connection DBConnection = DriverManager.getConnection(url, username, password);
            Statement State = DBConnection.createStatement();
        ) {
            StringBuilder insert = new StringBuilder(); // use the stringbuilder to assemble a query
            int i = (theTickets.size()-1);
            insert.append("insert into All_Tickets values ");
            insert.append("(");
            insert.append(theTickets.get(i).getCancelationCode());
            insert.append(", '");
            insert.append(theTickets.get(i).getMovie().getTitle());
            insert.append("', '");
            insert.append(theTickets.get(i).getMovie().getTheatre().getName());
            insert.append("', ");
            insert.append(theTickets.get(i).getMovie().getShowTime().convertTime());
            insert.append(", '");
            insert.append(theTickets.get(i).getMovie().getShowTime().convertDate());
            insert.append("', ");
            insert.append(theTickets.get(i).getSeatNumber());
            insert.append(", ");
            insert.append(theTickets.get(i).getUserID());
            insert.append(")");
            System.out.println(insert.toString());
            int UpdatedRows = State.executeUpdate(insert.toString());
            System.out.print("Rows added: " + UpdatedRows + '\n');
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return(true);
    }

    public boolean updateTicketList() throws SQLException{ // this updates only the ticketlist, which can be done after tickets are added/removed from a database
        this.TicketList = new Vector<Ticket>();
        boolean connection = true;
        try(
            Connection DBConnection = DriverManager.getConnection(url, username, password);
            Statement State = DBConnection.createStatement();
        ) {
            String query = "SELECT * FROM ALL_TICKETS";
            ResultSet results = State.executeQuery(query);
            System.out.println("UPDATING");
            while(results.next()){ // this builds Ticket objects from the database items 
                int cancelCode = results.getInt(1);
                String movieTitle = results.getString(2);
                System.out.println(movieTitle);
                String theatreName = results.getString(3);
                int time = results.getInt(4);
                String date = results.getString(5);
                int seatnum = results.getInt(6);
                int ID = results.getInt(7);

                String timestamp = "AM"; 
                int hour = time / 100;
                int minute = (time - (hour*100));
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
                String newTime = TimeItem.toString();

                for(int k = 0; k < this.MovieList.getSize(); k++){
                    if(this.MovieList.getMovieAt(k).getTitle().equals(movieTitle)){
                        if(this.MovieList.getMovieAt(k).getTheatre().getName().equals(theatreName)){
                            if(this.MovieList.getMovieAt(k).getShowTime().getTime().equals(newTime)){
                                Ticket theTicket = new Ticket(this.MovieList.getMovieAt(k), seatnum, ID, cancelCode);
                                this.TicketList.add(theTicket);
                                //System.out.println(this.TicketList.get(k).getMovie().getTitle());
                            }
                        }
                    }
                }
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }     
        return(connection);
    }
    public Vector<Ticket> getTickets(int UserID){ // this returns the tickets associated with a particular user, denoted by UserID
        Vector<Ticket> theList = new Vector<Ticket>();
        for(int i = 0; i < this.TicketList.size(); i++){
            if(this.TicketList.get(i).getUserID() == UserID){
                theList.add(this.TicketList.get(i));
            }
        }
        return(this.TicketList);
    }
    public boolean removeFromTicketList(Ticket theTicket) throws SQLException{ // this is used to remove Tickets from the database, after they are cancelled
        try(
            Connection DBConnection = DriverManager.getConnection(url, username, password);
            Statement State = DBConnection.createStatement();
        ) {
            String query = ("delete from All_Tickets where TicketID = " + theTicket.getCancelationCode());
            System.out.println(query);
            int rowsEffected = State.executeUpdate(query);
            System.out.println(rowsEffected);
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return(true);
    }
}