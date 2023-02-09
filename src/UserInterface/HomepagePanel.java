package UserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

// Window give users the option to Select Ticket, Make Payment, or Cancel Ticket
public class HomepagePanel extends JPanel implements ActionListener{
	
	private GUIFrame frame;
	private JLabel title;
	private JLabel userName;
	private JButton selectTicket;
	private JButton payment;
	private JButton cancelation;

	//constructor: displays all elements to panel
	public HomepagePanel(GUIFrame frame) {
		this.frame = frame;
		// set size color and layout
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.CYAN);
		setLayout(null);
		
		// display title
		Font font = new Font("SansSerif", Font.BOLD, 20);
		title = new JLabel("Homepage");
		title.setHorizontalAlignment(SwingConstants.CENTER);
	    title.setBounds(0, 55, 400, 80);
	    title.setFont(font);
	    add(title);
	    
		// display username
	    userName = new JLabel("New label");
	    userName.setText(frame.getUC().getUserName());
	    userName.setHorizontalAlignment(SwingConstants.RIGHT);
	    userName.setBounds(199, 0, 190, 23);
	    add(userName);
	    
		// display Select Ticket button
	    selectTicket = new JButton("Select Ticket");
	    selectTicket.setBounds(127, 133, 145, 23);
	    selectTicket.addActionListener(this);
	    add(selectTicket);
	    
		// display Select Payment button
	    payment = new JButton("Payment");
	    payment.setBounds(127, 190, 145, 23);
	    payment.addActionListener(this);
	    add(payment);
	    
		// display Cancelation button
	    cancelation = new JButton("Cancelation");
	    cancelation.setBounds(127, 250, 145, 23);
	    cancelation.addActionListener(this);
	    add(cancelation);
	    
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Check which button was pressed
		if(e.getSource() == selectTicket) { // open new Ticket Selection window
			frame.remove(this);
            TicketSelectionPanel ticSelect = new TicketSelectionPanel(frame);
            frame.add(ticSelect);
            frame.pack();
		}
		else if(e.getSource() == payment){
			frame.remove(this);
			PaymentPanel piface = new PaymentPanel(frame);
			frame.add(piface);
			frame.pack();
		}
		else if(e.getSource() == cancelation && frame.getUC().isRegistered()) {
			frame.remove(this);
			RegisteredCancelationPanel rCanel = new RegisteredCancelationPanel(frame);
			frame.add(rCanel);
            frame.pack();
		}
		else if(e.getSource() == cancelation && !frame.getUC().isRegistered()) {
			frame.remove(this);
			GuestCancelationPanel gCancel = new GuestCancelationPanel(frame);
			frame.add(gCancel);
            frame.pack();
		}
	}
}
