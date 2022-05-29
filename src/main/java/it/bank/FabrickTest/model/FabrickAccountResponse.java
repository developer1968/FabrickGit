package it.bank.FabrickTest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//Modella la response della url accounts/{conto} delle API di Fabrick
public class FabrickAccountResponse implements Serializable{
	private String status;
	
	List < Object > error = new ArrayList < Object > ();
	
	PayloadSaldo PayloadObject;



	public String getStatus() {
		return status;
	}

	public PayloadSaldo getPayload() {
		return PayloadObject;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPayload(PayloadSaldo payloadObject) {
		this.PayloadObject = payloadObject;
	}
}

