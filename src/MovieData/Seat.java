package MovieData;

public class Seat {
    private int[] seating;
    private float precentReserved;
    public Seat(String seats){ // the seat array comes in as a string, yet gets turned into an array of ints, where 1 represetns and unreserved seat, 2 is a purchased seat and 3 is a registered user reserved seat
        this.seating = new int[seats.length()];
        for(int i = 0; i < seats.length(); i++){
            seating[i] = ((int)seats.charAt(i) - 48);
        }
        precentReserved = updateReserved();
    }
    public float updateReserved(){ // here, it caclulates the percentage of seats held by registered users
        int counter = 0;
        for(int i = 0; i < this.seating.length; i++){
            if(seating[i] == 3){
                counter++;
            }
        }
        float reserved = (counter / (this.seating.length))*100;
        return(reserved);
    }
    public float getReserved(){
        return(this.precentReserved);
    }
    public int[] getSeatArray(){
        return(this.seating);
    }
    public boolean UpdateSeat(int index, int status){ // this returns false if a registered user attempts to reserve a seat when 10% of seats are already reserved
        if(status == 3){
            if(getReserved() >= 10){
                return(false);
            }
        }
        this.seating[index] = status;
        this.precentReserved = updateReserved();
        return(true);
    }
}
