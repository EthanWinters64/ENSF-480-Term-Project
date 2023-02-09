package UserData;
import java.util.*;

// list of all Registered Users from database
public class UserList {
    private ArrayList<User> theUsers;
    private int size;
    public UserList(){
        this.theUsers = new ArrayList<User>();
        this.size = 0;
    }
    public void addUser(User theUser){
        this.theUsers.add(theUser);
        this.size++;
    }
    public ArrayList<User> getUsers(){
        return(this.theUsers);
    }
    public User getUserAt(int index){
        return(theUsers.get(index));
    }
    public int getSize(){
        return(this.size);
    }
    public int checkUser(String Username){
        for(int i = 0; i < theUsers.size(); i++){
            if(theUsers.get(i).getUsername().equals(Username) == true){
                return(i);
            }
        }
        return(-1);
    }
    public int checkPass(String Password){
        for(int i = 0; i < theUsers.size(); i++){
            if(theUsers.get(i).getPassword().equals(Password) == true){
                return(i);
            }
        }
        return(-1);
    }
}

