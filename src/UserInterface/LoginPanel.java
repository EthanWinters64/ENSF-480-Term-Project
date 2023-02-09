package UserInterface;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Font;

public class LoginPanel extends JPanel implements ActionListener{
	
	private GUIFrame frame;
	private JLabel title;
	private JButton submit;
	private JLabel userLabel;
	private JLabel passLabel;
	private JTextField textField1;
	private JPasswordField textField2;
	private JLabel message;
	
	public LoginPanel(GUIFrame frame) {
		this.frame = frame;
		setPreferredSize(new Dimension(400, 400));
		
		Font font = new Font("SansSerif", Font.BOLD, 20);
		title = new JLabel("Enter Username and Password to Login");
		title.setHorizontalAlignment(SwingConstants.CENTER);
	    title.setBounds(0, 55, 400, 80);
	    title.setFont(font);
	    add(title);
		
		userLabel = new JLabel();
		userLabel.setBounds(44, 155, 70, 23);
		this.setBackground(Color.CYAN);
		userLabel.setText("Username");
		textField1 = new JTextField(15);
		textField1.setBounds(140, 155, 200, 23);
		passLabel = new JLabel();
		passLabel.setBounds(44, 199, 70, 23);
		submit = new JButton("Submit");
		submit.setBounds(140, 260, 135, 23);
		passLabel.setText("Password"); 
		textField2 = new JPasswordField(15);
		textField2.setBounds(140, 199, 200, 23);
		submit.addActionListener(this);
		setLayout(null);
		
		this.add(userLabel);    //set username label to panel  
		this.add(textField1);   //set text field to panel  
		this.add(passLabel);    //set password label to panel  
		this.add(textField2);   //set text field to panel  
		this.add(submit);
		
		Font smallFont = new Font("Tahoma", Font.PLAIN, 11);
		message = new JLabel("");
		message.setFont(smallFont);
		message.setBounds(140, 233, 200, 14);
		add(message);
	}
    
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    // TODO Auto-generated method stub
		String username = textField1.getText();
        String password= String.valueOf(textField2.getPassword());
        int foundUser = frame.getUserList().checkUser(username);
        int foundPass = frame.getUserList().checkPass(password);
        if(foundUser != -1 && foundPass != -1 && foundUser == foundPass){
            System.out.println(username+" - "+password+ " login successful");
            frame.getUC().setRegistered(frame.getUserList().getUserAt(foundUser));
			frame.remove(this);
			HomepagePanel HPage = new HomepagePanel(frame);
			frame.getContentPane().add(HPage);
			frame.pack();
        }
        else {
        	message.setText("*incorrect username or password*");
        }
	}
}

