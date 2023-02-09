package UserInterface;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.text.ParseException;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.*;

import TicketManagement.Payment;
import UserData.User;

public class RegistrationPanel extends JPanel implements ActionListener{

	private GUIFrame frame;

    private JTextField textField1;
    private JPasswordField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPasswordField textField5;
    private JFormattedTextField textDate;
	private JButton register;
	private JLabel message;
	
	public RegistrationPanel(GUIFrame frame) {
		this.frame = frame;
		setPreferredSize(new Dimension(400, 400));
        setBackground(Color.CYAN);
        setLayout(null);
        Font smallFont = new Font("Tahoma", Font.PLAIN, 11);
        
        Font font = new Font("SansSerif", Font.BOLD, 20);
		JLabel title = new JLabel("Register New User");
		title.setHorizontalAlignment(SwingConstants.CENTER);
	    title.setBounds(100, 11, 207, 55);
	    title.setFont(font);
	    add(title);
        
        
       textField1 = new JTextField(15);
       textField1.setBounds(138, 80, 200, 23);
       textField2 = new JPasswordField(15);
       textField2.setBounds(138, 120, 200, 23);
       textField3 = new JTextField(15);
       textField3.setBounds(138, 160, 200, 23);
       textField4 = new JTextField(15);
       textField4.setBounds(138, 200, 200, 23);
       textField5 = new JPasswordField(15);
       textField5.setBounds(138, 240, 200, 23);
       
       DateFormat dateFormat = new SimpleDateFormat("MM/YY");
       textDate = new JFormattedTextField(dateFormat);
       textDate.setBounds(138, 280, 200, 23);
       textDate.setName("Today");
       textDate.setColumns(10);
       textDate.setEditable(true);
       textDate.setValue(new Date());
       
       register=new JButton("Register and Pay Annual Fees!");
       register.setFont(smallFont);
       register.setBounds(107, 340, 200, 23);
       
       JLabel username,password,email,card,cvv,expiry;
       username= new JLabel("Username:");
       username.setFont(smallFont);
       username.setHorizontalAlignment(SwingConstants.TRAILING);
       username.setBounds(48, 84, 80, 14);
       password=new JLabel("Password:");
       password.setFont(smallFont);
       password.setHorizontalAlignment(SwingConstants.TRAILING);
       password.setBounds(48, 124, 80, 14);
       email= new JLabel("Email adress:");
       email.setFont(smallFont);
       email.setHorizontalAlignment(SwingConstants.TRAILING);
       email.setBounds(48, 164, 80, 14);
       card=new JLabel("Card Number:");
       card.setFont(smallFont);
       card.setHorizontalAlignment(SwingConstants.TRAILING);
       card.setBounds(33, 204, 95, 14);
       cvv= new JLabel("CVV:");
       cvv.setFont(smallFont);
       cvv.setHorizontalAlignment(SwingConstants.TRAILING);
       cvv.setBounds(48, 244, 80, 14);
       expiry = new JLabel("Expiry date:");
       expiry.setFont(smallFont);
       expiry.setHorizontalAlignment(SwingConstants.TRAILING);
       expiry.setBounds(48, 284, 80, 14);
       
       add(username);
       add(textField1);
       add(password);
       add(textField2);
       add(email);
       add(textField3);
       add(card);
       add(textField4);
       add(cvv);
       add(textField5);
       add(expiry);
       add(textDate);
       add(register);
       register.addActionListener(this);
       
       message = new JLabel("");
       message.setFont(smallFont);
       message.setBounds(138, 310, 200, 14);
       add(message);
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        int newID = (frame.getUserList().getSize()+1);
        String username = textField1.getText();
        String password = String.valueOf(textField2.getPassword());
        String email = textField3.getText();
        String Date = LocalDate.now().toString();
        String card = textField4.getText();
        String CVV = String.valueOf(textField5.getPassword());
        String expDate = textDate.getText();
        Date = Date.replaceAll("\\s+", "");
        
        if(username.isEmpty()) {
        	message.setText("*input username*");
        }
        else if(password.isEmpty()) {
        	message.setText("*input password*");
        }
        else if(email.isEmpty()) {
        	message.setText("*input email address*");
        }
        else if(card.isEmpty()) {
        	message.setText("*input card number*");
        }
        else if(CVV.isEmpty()) {
        	message.setText("*input cvv*");
        }
        else if(card.length() != 16 || CVV.length() != 3){
        	message.setText("*invalid card number or cvv*");
        }
        else{
            User newUser = new User(newID, username, password, Date, card, CVV, expDate, email, 0);
            // call the payment system to make the new user pay their fee
            // update the new user's payment status
            try {
                frame.getDB().addRegisteredUser(newUser);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                boolean fixed = frame.getDB().updateUserList();
            } catch(SQLException e2){
                e2.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            frame.getUserList().addUser(newUser);
            System.out.println(frame.getUserList().getSize());
            
            for(int i=0; i < frame.getUserList().getSize(); i++){
                System.out.println(frame.getUserList().getUserAt(i).getPassword());
            }
            // go to payment screen
            frame.getUC().setRegistered(newUser);
            frame.getUC().setUserPayment(new Payment(20));
            frame.remove(this);
			PaymentPanel piface = new PaymentPanel(frame);
			frame.getContentPane().add(piface);
			frame.pack();
        }
    }
}
