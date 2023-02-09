package UserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class RegisteredCancelationPanel extends JPanel implements ActionListener{

	private GUIFrame frame;
	private CancelationController CControl;
	private JList<String> list;
	private JButton cancel;

	public RegisteredCancelationPanel(GUIFrame frame) {
		this.frame = frame;
		CControl = new CancelationController(frame.getUC().getCurrentUser(), frame);
		
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
		
		
		
		Font itemFont = new Font("SansSerif", Font.BOLD, 15);
		JLabel lblNewLabel = new JLabel("Tickets to Cancel");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(35, 107, 156, 23);
		lblNewLabel.setFont(itemFont);
		add(lblNewLabel);
		
		
		Vector<String> l1 = CControl.getTicketList();
		/*Vector<String> l1 = new Vector<String>();
        l1.addElement("Ticket 1000:   $ 10.00\n"
				 + "  Batman\n"
				 + "  Chinook Theater\n"
				 + "  Jan 2, 3:00 pm\n"
				 + "  Seat 8\n");  
        l1.addElement("Ticket 1001:   $ 10.00\n"
				 + "  Batman\n"
				 + "  Chinook Theater\n"
				 + "  Jan 2, 3:00 pm\n"
				 + "  Seat 8\n");
        l1.addElement("Ticket 1002:   $ 10.00\n"
				 + "  Batman\n"
				 + "  Chinook Theater\n"
				 + "  Jan 2, 3:00 pm\n"
				 + "  Seat 8\n");*/
 
        list = new JList<String>(l1);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //list.addListSelectionListener(this);
		list.setBounds(25, 135, 350, 179);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 128, 352, 186);
		scrollPane.setViewportView(list);
		add(scrollPane);
		
		cancel = new JButton("Cancel this Ticket");
		cancel.setFont(smallFont);
		cancel.setBounds(219, 330, 156, 23);
		cancel.addActionListener(this);
		add(cancel);
		
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		int[] selected = list.getSelectedIndices();
		// send email notification
		String emailNotif = CControl.registeredNotifBuilder(selected);
		Notification n = new Notification(emailNotif);
		n.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		n.setVisible(true);

		CControl.cancelTickets(selected);
		frame.remove(this);
        HomepagePanel HPage = new HomepagePanel(frame);
        frame.getContentPane().add(HPage);
        frame.pack();
    }
}
