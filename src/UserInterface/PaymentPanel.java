package UserInterface;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import java.sql.SQLException;

import TicketManagement.BillingPaymentController;
import TicketManagement.TicketVoucherMap;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class PaymentPanel extends JPanel implements ActionListener{

	private GUIFrame frame;
	private BillingPaymentController bpc;
	private String receipt;
	
	private JTextField emailField;
	private JTextField cardField;
	private JTextField cvvField;
	private JTextField expField;
	private JTextField applyCreditField;
	
	private JLabel message;
	private JTextArea textArea;
	
	private JButton enter;
	private JButton applyCredit;
	private JButton makePayment;
	private JButton autofill;
	private JButton addFee;

	public PaymentPanel(GUIFrame frame) {
		this.frame = frame;
		bpc = new BillingPaymentController(frame.getUC().getUserPayment());
		
		setLayout(null);
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.CYAN);
		Font smallFont = new Font("Tahoma", Font.PLAIN, 11);
		
		JLabel userName = new JLabel("New label");
	    userName.setText(frame.getUC().getUserName());
	    userName.setHorizontalAlignment(SwingConstants.RIGHT);
	    userName.setBounds(199, 0, 190, 23);
	    add(userName);
		
	    Font font = new Font("SansSerif", Font.BOLD, 20);
		JLabel title = new JLabel("Payment");
		title.setHorizontalAlignment(SwingConstants.CENTER);
	    title.setBounds(120, 10, 160, 40);
	    title.setFont(font);
	    add(title);

		message = new JLabel("");
		message.setFont(smallFont);
		message.setBounds(50, 50, 200, 14);
		add(message);
		
		JLabel email= new JLabel("Email adress:");
		email.setFont(smallFont);
	    email.setBounds(25, 75, 67, 15);
		emailField = new JTextField(15);
		emailField.setBounds(25, 90, 150, 20);
		add(email);
	    add(emailField);
		
		JLabel card=new JLabel("Card Number:");
		card.setFont(smallFont);
	    card.setBounds(25, 115, 69, 15);
	    cardField = new JTextField(15);
	    cardField.setBounds(25, 130, 150, 20);
	    add(card);
	    add(cardField);
	    
	    JLabel cvv= new JLabel("CVV:");
	    cvv.setFont(smallFont);
	    cvv.setBounds(25, 155, 30, 15);
	    cvvField = new JTextField(15);
	    cvvField.setBounds(25, 170, 65, 20);
	    add(cvv);
	    add(cvvField);
	    
	    JLabel expiry = new JLabel("Expiry date:");
	    expiry.setFont(smallFont);
	    expiry.setBounds(100, 155, 59, 15);
	    expField = new JTextField(15);
	    expField.setBounds(100, 170, 75, 20);
	    add(expiry);
	    add(expField);
	    
	    enter = new JButton("enter");
	    enter.setFont(smallFont);
		enter.setBounds(25, 200, 65, 23);
		enter.addActionListener(this);
		add(enter);
		
		JLabel scc = new JLabel("Store Credit Code:");
		scc.setFont(smallFont);
		scc.setBounds(25, 248, 118, 14);
		applyCreditField = new JTextField();
		applyCreditField.setBounds(25, 260, 150, 20);
		applyCreditField.setColumns(10);
		
		
		applyCredit = new JButton("Apply Store Credit");
		applyCredit.setFont(smallFont);
		applyCredit.setBounds(25, 291, 134, 23);
		applyCredit.addActionListener(this);
		add(applyCredit);

		Font itemFont = new Font("SansSerif", Font.BOLD, 15);
		JLabel lblNewLabel = new JLabel("Items in Cart");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(200, 50, 190, 23);
		lblNewLabel.setFont(itemFont);
		add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(200, 75, 190, 274);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		
		
		if(bpc.getPayment() == null) {
			textArea = new JTextArea("Cart is Empty");
		}
		else {
			receipt = bpc.getPayment().receiptBuilder();
			textArea = new JTextArea(receipt);
		}
		/*textArea = new JTextArea("Ticket 1000:   $ 10.00\n"
										 + "  Batman\n"
										 + "  Chinook Theater\n"
										 + "  Jan 2, 3:00 pm\n"
										 + "  Seat 8\n\n"
										 + "Ticket 1001:   $ 10.00\n"
										 + "  Batman\n"
										 + "  Batman\n"
										 + "  Chinook Theater\n"
										 + "  Jan 2, 3:00 pm\n"
										 + "  Seat 9\n\n"
										 + "Subtotal:      $ 40.00\n"
										 + "GST:           $  2.00\n"
										 + "Store Credit: -$ 10.00\n\n"
										 + "Total:         $ 32.00\n");*/
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scrollPane.setViewportView(textArea);
		add(scrollPane);
		
		makePayment = new JButton("Make Payment");
		makePayment.setFont(smallFont);
		makePayment.setBounds(240, 360, 118, 23);
		makePayment.addActionListener(this);
		add(makePayment);
		
		autofill = new JButton("autofill");
		autofill.setFont(smallFont);
		autofill.setBounds(100, 200, 75, 23);
		autofill.addActionListener(this);
		
		addFee = new JButton("Add Annual Fee");
		addFee.setFont(smallFont);
		addFee.setBounds(25, 360, 118, 23);
		addFee.addActionListener(this);
		
		
		if(frame.getUC().isRegistered()) {
			add(autofill);
			add(addFee);
		}
		else {
			add(scc);
			add(applyCreditField);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == enter) {
			String emailText = emailField.getText();
			String cardText = cardField.getText();
			String cvvText = cvvField.getText();
			String expText = expField.getText();
			
			if(emailText.isEmpty()) {
				emailField.setText("*enter email*");
			}
			else if(cardText.isEmpty()) {
				cardField.setText("*enter card number*");
			}
			else if(cvvText.isEmpty()) {
				cvvField.setText("*enter cvv*");
			}
			else if(expText.isEmpty()) {
				expField.setText("*enter cvv*");
			}
			else {
				bpc.setBillingInfo(cardText, cvvText, expText);
			}
		}
		else if(e.getSource() == applyCredit && bpc.getPayment() != null) {
			if(frame.getUC().isRegistered()) {
				double sc = frame.getUC().getCurrentUser().getStoreCredit();
				double currentTotal = bpc.getPayment().calculateTotal();
				if(sc >= currentTotal) {
					bpc.getPayment().setStoreCredit(currentTotal);
					frame.getUC().getCurrentUser().updateStoreCredit(-currentTotal);
				}
				else {
					bpc.getPayment().setStoreCredit(sc);
					frame.getUC().getCurrentUser().updateStoreCredit(-sc);
				}
			}
			else {
				Integer voucherCode = Integer.valueOf(applyCreditField.getText());
				if(TicketVoucherMap.getMap().getVoucherMap().containsKey(voucherCode)) {
					// if code matches a key in voucherMap, set store credit to corresponding value
					bpc.getPayment().setStoreCredit(TicketVoucherMap.getMap().getVoucherMap().get(voucherCode));
				}
				else{
					applyCreditField.setText("*invalid code*");
				}
			}
			
			receipt = bpc.getPayment().receiptBuilder();
			textArea.setText(receipt);
		}
		else if(e.getSource() == makePayment && bpc.getPayment() != null) {
			//send recipt notification
			receipt = bpc.getPayment().receiptBuilder();
			Notification n = new Notification(receipt);
			n.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			n.setVisible(true);
			
			for(int i = 0; i < bpc.getPayment().getTickets().size(); i++){
				bpc.getPayment().getTickets().get(i).Pay();
				System.out.println(bpc.getPayment().getTickets().get(i).getCancelationCode());
				try {
					boolean the = frame.getDB().updateSeats(bpc.getPayment().getTickets().get(i).getMovie(), bpc.getPayment().getTickets().get(i).getSeatNumber(), 2);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if(bpc.getPayment().getTickets().size() > 0){
				try {
					frame.getDB().addToTicketList(bpc.getPayment().getTickets());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			try {
				boolean the = frame.getDB().updateTicketList();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(frame.getUC().isRegistered()){
				frame.setTicketList(frame.getDB().getTickets(frame.getUC().getCurrentUser().getID()));
			}
			else{
				frame.setTicketList(frame.getDB().getTickets(0));
			}
			frame.remove(this);
            HomepagePanel HPage = new HomepagePanel(frame);
            frame.getContentPane().add(HPage);
            frame.pack();
		}
		else if(e.getSource() == autofill) {
			emailField.setText(frame.getUC().getCurrentUser().getEmail());
			bpc.setBillingInfo(frame.getUC().getCurrentUser().getBillingInfo());
			cardField.setText(frame.getUC().getCurrentUser().getBillingInfo().getCard());
			cvvField.setText(frame.getUC().getCurrentUser().getBillingInfo().getCVV());
			expField.setText(frame.getUC().getCurrentUser().getBillingInfo().getExpDate());
		}
		else if(e.getSource() == addFee && bpc.getPayment() != null) {
			if(frame.getUC().getCurrentUser().hasPaid() == 0){
				bpc.getPayment().setAnnualFeePayment(20);
				receipt = bpc.getPayment().receiptBuilder();
				textArea.setText(receipt);
			}
			else{
				message.setText("You've already paid you fee this year");
				remove(addFee);
			}
		}
	}
}