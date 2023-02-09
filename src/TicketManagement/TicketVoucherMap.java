package TicketManagement;

import java.util.HashMap;

public class TicketVoucherMap {
	private static TicketVoucherMap map;
	private static HashMap<Integer, Ticket> TicketMap;
	private static HashMap<Integer, Double> VoucherMap;
	
	public TicketVoucherMap() {
		TicketMap = new HashMap<Integer, Ticket>(20);
		VoucherMap = new HashMap<Integer, Double>(20);
	}
    public static TicketVoucherMap getMap(){
        if(map==null){
            map = new TicketVoucherMap();
        }
        return map;
    }
	
	public HashMap<Integer, Ticket> getTicketMap(){
		return TicketMap;
	}
	
	public HashMap<Integer, Double> getVoucherMap(){
		return VoucherMap;
	}
}
