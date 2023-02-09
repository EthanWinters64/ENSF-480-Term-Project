package UserInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.Dimension;

//Window that give user options to Login, Register, or continue as guest
public class WelcomePanel extends JPanel implements ActionListener{
	private GUIFrame frame;
	private JButton regular;
    private JButton registered;
    private JButton registerNew;
    private JLabel title;

	//constructor: displays all elements to panel
	public WelcomePanel(GUIFrame frame) {
		this.frame = frame;
		// set size color and layout
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.CYAN);
		setLayout(null);

		// display title
		Font font = new Font("SansSerif", Font.BOLD, 20);
		title = new JLabel("Welcome! Please choose an action.");
	    title.setHorizontalAlignment(SwingConstants.CENTER);
	    title.setBounds(0, 53, 400, 54);
	    title.setFont(font);
	    add(title);
	      
		//display login button
		registered= new JButton("Log in");
	    registered.setBounds(135, 133, 100, 23);
	    registered.addActionListener(this);
	    add(registered);
	    
		//display continue as guest button
	    regular=new JButton("Continue as Guest");
	    regular.setBounds(86, 184, 197, 23);
	    regular.addActionListener(this);
		add(regular);
	    
		//display register button
	    registerNew= new JButton("Register");
	    registerNew.setBounds(135, 232, 100, 23);
	    registerNew.addActionListener(this);
	    add(registerNew);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// checks which button was pressed to open new window
		if(e.getSource()==registered ) { //open new login window
			frame.remove(this);
            LoginPanel lpan = new LoginPanel(frame);
            frame.getContentPane().add(lpan);
            frame.pack();
		}
		else if(e.getSource()==registerNew){ //open new registration window
			frame.remove(this);
			RegistrationPanel rpan = new RegistrationPanel(frame);
            frame.getContentPane().add(rpan);
			frame.pack();
		}
		else if(e.getSource()==regular) { //open new homepage window
			frame.remove(this);
            HomepagePanel HPage = new HomepagePanel(frame);
            frame.getContentPane().add(HPage);
            frame.pack();
		}
    
	}
}
