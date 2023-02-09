package UserInterface;

import javax.swing.*;
import MovieDatabase.Database;
import UserData.*;

import java.util.Vector;
import TicketManagement.Ticket;

// main class that creates a singleton frame that can switch between windows
public class GUIFrame extends JFrame{
    private static GUIFrame frame;
    private Database DB;
    private UserList userList;
    private Vector<Ticket> ticketList;
    private UserController uc;
    private JPanel panel;
    
    // constructor: sets class attributes
    private GUIFrame () throws Exception{
    	//for testing with database
        this.DB = new Database();
        this.userList = DB.getUserList();
    	
    	uc = new UserController();
    	
    	this.setTitle("Movie Ticket Reservation");
    	this.setSize(400,400);
      
        //displays new WelcomePanel
    	panel = new WelcomePanel(this);
      
    	this.getContentPane().add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
   	 

    }
    
    public static void main(String[] args) throws Exception {	
        getFrame();
    }

    // singleton pattern
    public static GUIFrame getFrame() throws Exception {
        if(frame==null){
            frame = new GUIFrame();
        }
        return frame;
    }

    //getters/setters
    public UserController getUC() {
    	return uc;
    }

    public Database getDB() {
    	return DB;
    }

    public UserList getUserList() {
    	return userList;
    }

    public Vector<Ticket> getTicketlist(){
        return(ticketList);
    }
    public void setTicketList(Vector<Ticket> list){
        this.ticketList = list;
    }
}