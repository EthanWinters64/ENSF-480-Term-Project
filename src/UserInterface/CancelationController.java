package UserInterface;

import java.util.Random;
import java.util.Vector;
import java.sql.SQLException;
import java.time.LocalDateTime;


import MovieData.Seat;
import TicketManagement.*;
import UserData.*;

public class CancelationController {
	private Ticket ticketToCancel;
	private Vector<Ticket> cancelableTickets;
	private String email;
	private User currentUser;
	private GUIFrame frame;
	
	public CancelationController() {}

	public CancelationController(User currentUser, GUIFrame frame) {
		this.frame = frame;
		this.currentUser = currentUser;
		cancelableTickets = new Vector<Ticket>();
		for(Ticket t: frame.getDB().getTickets(currentUser.getID())) {
			if(isCancelable(t)) {
				cancelableTickets.add(t);
			}
		}
	}
	
	public boolean isCancelable(Ticket t) {
		LocalDateTime dt = t.getMovie().getShowTime().getDateTime();
		LocalDateTime currentPlusHours = LocalDateTime.now().plusHours(72);
		if(currentPlusHours.isBefore(dt)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void cancel() {
		// remove from hashMap of purchased tickets
		TicketVoucherMap.getMap().getTicketMap().remove(ticketToCancel.getCancelationCode());
		// update seat to unreserved
		Seat seats = ticketToCancel.getMovie().getShowTime().getSeats();
		seats.UpdateSeat(ticketToCancel.getSeatNumber(), 1);
		try {
			frame.getDB().updateSeats(ticketToCancel.getMovie(), ticketToCancel.getSeatNumber(), 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			boolean the = frame.getDB().removeFromTicketList(ticketToCancel);
		} catch (SQLException e2){
			e2.printStackTrace();
		}
		try {
			boolean the = frame.getDB().updateTicketList();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void cancelTickets(int[] selected) {
		for(int i: selected) {
			ticketToCancel =  cancelableTickets.elementAt(i);
			cancel();
		}
	}
	
	// 
	public String registeredNotifBuilder(int[] selected) {
		double storeCredit = 0;
		String notifReceipt = "Ticket(s) Canceled:\n";
		for(int i: selected) {
			notifReceipt = notifReceipt + cancelableTickets.elementAt(i).TicketFormat();
			storeCredit += cancelableTickets.elementAt(i).getPrice();
		}
		currentUser.updateStoreCredit(storeCredit);
		notifReceipt = notifReceipt + "Total Store Credit: $" + storeCredit +"\n";
		return notifReceipt;
	}
	
	public String voucherBuilder() {
		String voucherReceipt = "Ticket Canceled:\n" + ticketToCancel.TicketFormat();
		Double voucherValue = ticketToCancel.getPrice() * 0.85;
		Random rand = new Random();
		// generate random 4-digit number for code
		Integer voucherCode = rand.nextInt(9999);
		while(TicketVoucherMap.getMap().getVoucherMap().containsKey(voucherCode)) {
			// if code already has a mapping, generate a different code until a unique one is found
			voucherCode = rand.nextInt(9999); 
		}	
		TicketVoucherMap.getMap().getVoucherMap().put(voucherCode, voucherValue);
		
		voucherReceipt = voucherReceipt + "Input this code on your next purchase to apply store credit.\n"
										+ "Voucher Code:" + voucherCode + "\n" 
										+ "Credit Value:" + voucherValue + "\n\n";
		return voucherReceipt;
	}
	
	public void setEmail(String e) {
		email = e;
	}
	
	public String getEmail() {
		return email;
	}
	
	public boolean setTicketToCancel(Ticket t) {
		if(isCancelable(t)) {
			ticketToCancel = t;
		}
		return isCancelable(t);
	}
	
	public Ticket getTicketToCancel() {
		return ticketToCancel;
	}
	
	public Vector<String> getTicketList() {
		Vector<String> ticketList = new Vector<String>();
		for(Ticket t: cancelableTickets) {
			ticketList.add(t.TicketFormat());
		}
		return ticketList;
	}
}
