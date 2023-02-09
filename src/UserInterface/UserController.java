package UserInterface;

import TicketManagement.Payment;
import UserData.User;

public class UserController {
	private boolean registeredStatus;
	private User currentUser;
	private String userName;
	private int userID;
	private Payment userPayment;
	
	public UserController() {
		registeredStatus = false;
		userName = "Guest";
	}
	
	public void setRegistered(User currentUser) {
		this.currentUser = currentUser;
		registeredStatus = true;
		userName = currentUser.getUsername();
		userID = currentUser.getID();
	}
	
	public void unRegister() {
		registeredStatus = false;
		this.userName = "Guest";
		this.userID = 0;
	}

	public boolean isRegistered(){
		return registeredStatus;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public String getUserName() {
		return userName;
	}

	public int getUserID() {
		return userID;
	}
	
	public void setUserPayment(Payment uPayment) {
		userPayment = uPayment;
	}
	
	public Payment getUserPayment() {
		return userPayment;
	}
}


