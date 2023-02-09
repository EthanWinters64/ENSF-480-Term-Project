package UserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import TicketManagement.TicketVoucherMap;

public class GuestCancelationPanel extends JPanel implements ActionListener{

	private GUIFrame frame;
	private CancelationController CControl;
	
	private JTextField CCField;
	private JButton enter;
	private JButton cancel;
	
	private JTextArea ticketDisplay;
	private JLabel lblNewLabel_1;
	private JTextField emailField;
	private JButton enterEmail;

	public GuestCancelationPanel(GUIFrame frame) {
		this.frame = frame;
		CControl = new CancelationController();
		
		setLayout(null);
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.CYAN);
		Font smallFont = new Font("Tahoma", Font.PLAIN, 11);
		
		Font font = new Font("SansSerif", Font.BOLD, 20);
		JLabel title = new JLabel("Cancelation");
		title.setHorizontalAlignment(SwingConstants.CENTER);
	    title.setBounds(120, 33, 160, 40);
	    title.setFont(font);
	    add(title);
	    
	    JLabel userName = new JLabel("New label");
	    userName.setText(frame.getUC().getUserName());
	    userName.setHorizontalAlignment(SwingConstants.RIGHT);
	    userName.setBounds(199, 0, 190, 23);
	    add(userName);
		
		JLabel lblNewLabel = new JLabel("Cancelation Code");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(smallFont);
		lblNewLabel.setBounds(25, 95, 90, 14);
		add(lblNewLabel);
		
		CCField = new JTextField();
		CCField.setBounds(119, 92, 175, 20);
		add(CCField);
		CCField.setColumns(10);
		
		enter = new JButton("enter");
		enter.setFont(smallFont);
		enter.setBounds(304, 124, 71, 23);
		enter.addActionListener(this);
		add(enter);
		
		lblNewLabel_1 = new JLabel("email");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setFont(smallFont);
		lblNewLabel_1.setBounds(25, 128, 90, 14);
		add(lblNewLabel_1);
		
		emailField = new JTextField();
		emailField.setColumns(10);
		emailField.setBounds(119, 125, 175, 20);
		add(emailField);
		
		enterEmail = new JButton("enter");
		enterEmail.setFont(smallFont);
		enterEmail.setBounds(304, 91, 71, 23);
		add(enterEmail);
		
		ticketDisplay = new JTextArea();
		ticketDisplay.setBounds(25, 162, 350, 106);
		/*ticketDisplay.setText("Ticket 1000:   $ 10.00\n"
				 + "  Batman\n"
				 + "  Chinook Theater\n"
				 + "  Jan 2, 3:00 pm\n"
				 + "  Seat 8\n");*/
		add(ticketDisplay);
		
		cancel = new JButton("Cancel this Ticket");
		cancel.setFont(smallFont);
		cancel.setBounds(219, 291, 156, 23);
		add(cancel);
		
		
		
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == enter) {
			if(CCField.getText().isEmpty()) {
				CCField.setText("*enter code*");
			}
			else {
				Integer cc = Integer.valueOf(CCField.getText());
				// check if cc matches any previously purchased tickets
				if(TicketVoucherMap.getMap().getTicketMap().containsKey(cc)) {
					if(CControl.setTicketToCancel(TicketVoucherMap.getMap().getTicketMap().get(cc))) {
						String ticketInfo = CControl.getTicketToCancel().TicketFormat();
						ticketDisplay.setText(ticketInfo);
						cancel.addActionListener(this);
					}
					else {
						ticketDisplay.setText("Ticket cannot be canceled within 72 hours of the showtime.");
					}
				}
				else {
					ticketDisplay.setText("Ticket not Found :(");
				}
			}
		}
		else if(e.getSource() == enterEmail) {
			if(emailField.getText().isEmpty()) {
				emailField.setText("*enter email*");
			}
			else {
				String email = emailField.getText();
				CControl.setEmail(email);
			}
		}
		else if(e.getSource() == cancel) {
			// send notification
			String VoucherReceipt = CControl.voucherBuilder();
			Notification n = new Notification(VoucherReceipt);
			n.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			n.setVisible(true);

			CControl.cancel();
			frame.remove(this);
            HomepagePanel HPage = new HomepagePanel(frame);
            frame.getContentPane().add(HPage);
            frame.pack();
		}
	}

}



