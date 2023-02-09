package MovieDatabase;

public class DatabaseException extends Exception{
    public DatabaseException(){
        super("Something went wrong while trying to access the database");
    }
}
