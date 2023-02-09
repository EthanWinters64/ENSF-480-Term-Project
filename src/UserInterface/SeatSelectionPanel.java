package UserInterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Font;

public class SeatSelectionPanel extends JPanel implements ActionListener{

	private GUIFrame frame;
	private TicketSelectionController tsc;
	
	private JButton[] Seats;
	private int[] seatstatus;
	private Vector<Integer> selectedSeats;
	
	private Border blackline;
	private Border thinline;
	private JButton atc;
	
	public SeatSelectionPanel(GUIFrame frame, TicketSelectionController tsc) {
		this.frame = frame;
		this.tsc = tsc;
		setPreferredSize(new Dimension(400, 400));
        setBackground(Color.CYAN);
        setLayout(null);
        
        Font smallFont = new Font("Tahoma", Font.PLAIN, 11);
		
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
        
	    blackline = BorderFactory.createStrokeBorder(new BasicStroke(5.0f));
	    thinline = BorderFactory.createStrokeBorder(new BasicStroke(1.0f));

        selectedSeats = new Vector<Integer>(10);
        seatstatus = tsc.getTheMovie().getShowTime().getSeats().getSeatArray();
        Seats=new JButton[10];
        
        JPanel seatPanel = new JPanel();
        seatPanel.setBounds(50, 200, 300, 125);
        add(seatPanel);
        seatPanel.setLayout(new GridLayout(2, 5, 10, 10));

        
        JLabel lblNewLabel = new JLabel("Select a Seat for:");
        lblNewLabel.setBounds(52, 89, 143, 14);
        add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel(tsc.getMovieTitle());
        lblNewLabel_1.setBounds(52, 114, 143, 14);
        add(lblNewLabel_1);
        
        JLabel lblNewLabel_1_1 = new JLabel(tsc.getTheatreName());
        lblNewLabel_1_1.setBounds(52, 139, 113, 14);
        add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_1_1_1 = new JLabel(tsc.getShow());
        lblNewLabel_1_1_1.setBounds(52, 164, 200, 14);
        add(lblNewLabel_1_1_1);
        
        
        
        for(int i=0;i<10;i++){
        	Seats[i]=new JButton(String.valueOf(i));
        	
            int stat = seatstatus[i];
            if(stat == 1){
            	Seats[i].setBackground(Color.CYAN);
            }
            else if(stat == 2){
            	Seats[i].setBackground(Color.RED);
            }
            else{
            	Seats[i].setBackground(Color.YELLOW);
            }
            Seats[i].setSize(100, 100);
            Seats[i].addActionListener(this);
            seatPanel.add(Seats[i]);
        }
        
        atc = new JButton("Add to Cart");
        atc.setBounds(230, 345, 120, 23);
        atc.addActionListener(this);
        add(atc);
	}

	@Override
    public void actionPerformed(ActionEvent e) {
		for(int i=0; i<10;i++) {
			if(e.getSource()==Seats[i]){
	            if((seatstatus[i] == 2)||(seatstatus[i] == 3)){
	                System.out.println("Cannot Purchase this seat");
	            }
	            else{
	            	if(selectedSeats.contains(i)) {
	            		selectedSeats.remove(Integer.valueOf(i));
	            		Seats[i].setBorder(thinline);
	            	}
	            	else {
	            		selectedSeats.add(i);
	            		Seats[i].setBorder(blackline);
	            	}
	                validate();
	                updateUI();
	                System.out.println("Selected Seat: " + i);
	            }
	        }
		}
		
		if(e.getSource() == atc) {
        	System.out.println("atc");
        	if(selectedSeats.isEmpty()) {
        		//error message
        	}
        	else {
        		frame.getUC().setUserPayment(tsc.getPayment(selectedSeats, frame.getUC().getUserID()));
        		frame.remove(this);
                PaymentPanel HPage = new PaymentPanel(frame);
                frame.add(HPage);
                frame.pack();
        	}
        }
    }
}
