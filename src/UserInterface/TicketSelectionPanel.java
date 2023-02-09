package UserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import MovieData.*;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JButton;

public class TicketSelectionPanel extends JPanel implements ActionListener{

	private GUIFrame frame;
	private TicketSelectionController tsc;
	
	private String MovieTitle;
    private String TheatreName;
    private String Show;
    private Movie theMovie;
	
    private boolean TheatresTriggered = false; 
    private boolean ShowsTriggered = false;

    private Vector<String> TitleList;
    private Vector<String> TheatreList;
    private Vector<String> ShowList;
	
	private JComboBox<String> Movies;
    private JComboBox<String> Theatres;
    private JComboBox<String> Showtimes;
    
    private JTextField searchField;
    private JButton search;
    private JButton viewAll;
    private JButton Continue;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    
    private Font smallFont;
	
	public TicketSelectionPanel(GUIFrame frame) { 
        
        // initialize TicketSelectionController with MovieList
        tsc = new TicketSelectionController(frame.getDB().getMovieList());
        
        this.frame = frame;
		setPreferredSize(new Dimension(400, 400));
        setBackground(Color.CYAN);
        setLayout(null);
        
        smallFont = new Font("Tahoma", Font.PLAIN, 11);
		
		JLabel userName = new JLabel("New label");
	    userName.setText(frame.getUC().getUserName());
	    userName.setHorizontalAlignment(SwingConstants.RIGHT);
	    userName.setBounds(199, 0, 190, 23);
	    add(userName);
		
	    Font font = new Font("SansSerif", Font.BOLD, 20);
		JLabel title = new JLabel("Selection");
		title.setHorizontalAlignment(SwingConstants.CENTER);
	    title.setBounds(120, 20, 160, 40);
	    title.setFont(font);
	    add(title);
        
        lblNewLabel = new JLabel("Select Movie:");
        lblNewLabel.setFont(smallFont);
        lblNewLabel.setBounds(25, 166, 96, 14);
        add(lblNewLabel);
        
        TitleList= new Vector<String>();
        TitleList.add("---Please Select a Movie---");
        
        Movies=new JComboBox<String>(TitleList);
        Movies.setBounds(22, 181, 349, 23);
        Movies.addActionListener(this);
        add(Movies);
        
        searchField = new JTextField();
        searchField.setBounds(22, 87, 199, 20);
        add(searchField);
        searchField.setColumns(10);
        
        search = new JButton("Search Movie");
        search.setFont(smallFont);
        search.setBounds(244, 86, 127, 23);
        search.addActionListener(this);
        add(search);
        
        viewAll = new JButton("View All");
        viewAll.setFont(smallFont);
        viewAll.setBounds(244, 120, 127, 23);
        viewAll.addActionListener(this);
        add(viewAll);

	}
	
	public boolean showTheater() {
		if(TheatresTriggered == true){
			remove(lblNewLabel_1);
            remove(Theatres);
        	remove(lblNewLabel_2);
            remove(Showtimes);
            validate();
            updateUI();
        }
		if(ShowsTriggered == true) {
			remove(Continue);
            validate();
            updateUI();
		}
		lblNewLabel_1 = new JLabel("Select Theatre:");
		lblNewLabel_1.setFont(smallFont);
        lblNewLabel_1.setBounds(25, 216, 96, 14);
        add(lblNewLabel_1);
		Theatres = new JComboBox<String>(TheatreList);
        Theatres.setBounds(22, 231, 349, 23);
        Theatres.addActionListener(this);
      
        add(Theatres);
        validate();
        updateUI();
        return true;
    }
	
	public boolean showShowTime() {
		if(ShowsTriggered == true) {
			remove(lblNewLabel_2);
            remove(Showtimes);
			remove(Continue);
            validate();
            updateUI();
		}
		lblNewLabel_2 = new JLabel("Select Showtime:");
		lblNewLabel_2.setFont(smallFont);
        lblNewLabel_2.setBounds(25, 266, 96, 14);
        add(lblNewLabel_2);
        
        Showtimes = new JComboBox<String>(ShowList);
        Showtimes.setBounds(22, 281, 349, 23);
        Showtimes.addActionListener(this);
        TheatresTriggered = true;
        add(Showtimes);
        validate();
        updateUI();
        return(true);
    }
	
	public boolean setupSeats(){
		Continue = new JButton("Select Seat");
		Continue.setFont(smallFont);
        Continue.setBounds(244, 342, 127, 23);
        Continue.addActionListener(this);
        ShowsTriggered = true;
        add(Continue);
        validate();
        updateUI();
        return(true);
    }

	@Override
    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == viewAll) {
			for(String Title: tsc.getTitleList()){
                TitleList.add(Title);
	        }
		}
		else if(e.getSource() == search) {
			String searchString = searchField.getText();
			for(int i = TitleList.size()-1; i>0; i--) {
				TitleList.remove(i);
			}
			for(String s: tsc.SearchResults(searchString)) {
				TitleList.add(s);
			}
			if(tsc.SearchResults(searchString).isEmpty()) {
				TitleList.add("*no results found*");
			}
		}
		else if((e.getSource() == Movies)){
            TheatreList = new Vector<String>();
            TheatreList.add("---Please Select a Theatre---");
            if(Movies.getSelectedItem().toString() != "---Please Select a Movie---"){
                for(int g=0;g< tsc.getMov().getSize() ;g++){
                    if(Movies.getSelectedItem()==tsc.getMov().getMovieAt(g).getTitle()){
                        boolean duplicate = false;
                        String name = tsc.getMov().getMovieAt(g).getTheatre().getName();
                        for(int i = 0; i < TheatreList.size(); i++){
                            if(TheatreList.get(i) == name){
                                duplicate = true;
                            }
                        }
                        if(duplicate == false){
                            TheatreList.add(tsc.getMov().getMovieAt(g).getTheatre().getName());
                        }
                        System.out.println(Movies.getSelectedItem().toString());
                    }
                }
                boolean the = showTheater();
            }
        }
        else if(e.getSource()==Theatres){
            ShowList = new Vector<String>();
            ShowList.add("--Please Select a Showing--");
            if(Theatres.getSelectedItem().toString() != "---Please Select a Theatre---"){
                for(int g=0; g<TheatreList.size(); g++){
                    if(Theatres.getSelectedItem().toString()==TheatreList.get(g)){
                        String title = Movies.getSelectedItem().toString();
                        System.out.println(title);
                        for(int i = 0; i < tsc.getMov().getSize(); i++){
                            if(tsc.getMov().getMovieAt(i).getTitle().equals(title)){
                                String time = tsc.getMov().getMovieAt(i).getShowTime().getTime();
                                String date = tsc.getMov().getMovieAt(i).getShowTime().getDate();
                                ShowList.add(time + " " + date);
                            }   
                        }
                    }
                }
                System.out.println(Theatres.getSelectedItem());
                boolean the = showShowTime();
            }
        }
        else if(e.getSource()==Showtimes){
            if(Showtimes.getSelectedItem().toString() != "---Please Select a Showing---"){
                boolean the = setupSeats();
            }
        }
        else if(e.getSource()==Continue){
            this.MovieTitle = Movies.getSelectedItem().toString();
            tsc.setMovieTitle(MovieTitle);
            this.TheatreName = Theatres.getSelectedItem().toString();
            tsc.setTheatreName(TheatreName);
            this.Show = Showtimes.getSelectedItem().toString();
            tsc.setShow(Show);
            System.out.println("Movie: " + MovieTitle);
            System.out.println("Theatre: " + TheatreName);
            System.out.println("ShowTime: " + Show);

            for(int i = 0; i < tsc.getMov().getSize(); i++){
                if(tsc.getMov().getMovieAt(i).getTitle().equals(MovieTitle)){
                    if(tsc.getMov().getMovieAt(i).getTheatre().getName().equals(TheatreName)){
                        System.out.println(Show.substring(0, 8));
                        System.out.println(tsc.getMov().getMovieAt(i).getShowTime().getTime());
                        if((Show.substring(0, 7).equals(tsc.getMov().getMovieAt(i).getShowTime().getTime()))||(Show.substring(0, 8).equals(tsc.getMov().getMovieAt(i).getShowTime().getTime()))){
                            System.out.println("Found");
                            //this.MovieSeats = Mov.getMovieAt(i).getShowTime().getSeats();
                            this.theMovie = tsc.getMov().getMovieAt(i);
                            tsc.setTheMovie(theMovie);
                        }
                    }
                }
            }
            frame.remove(this);
            SeatSelectionPanel seatSelect = new SeatSelectionPanel(frame, tsc);
            frame.add(seatSelect);
            frame.pack();
        }
    }
}