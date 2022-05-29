package it.bank.FabrickTest.model.transazioni;

import java.util.ArrayList;

public class TransazioneResponse{
    private  String status;
   
    private ArrayList<Object> error;
   
    private Payload payload;
    
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<Object> getError() {
		return error;
	}
	public void setError(ArrayList<Object> error) {
		this.error = error;
	}
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
    
    
}