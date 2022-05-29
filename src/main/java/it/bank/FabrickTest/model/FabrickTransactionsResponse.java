package it.bank.FabrickTest.model;

import java.util.ArrayList;
import java.util.List;

public class FabrickTransactionsResponse {
	private String status;
	List < Object > error = new ArrayList < Object > ();
	
	List<PayloadSaldo> payload;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Object> getError() {
		return error;
	}

	public void setError(List<Object> error) {
		this.error = error;
	}

	public List<PayloadSaldo> getPayload() {
		return payload;
	}

	public void setPayload(List<PayloadSaldo> payload) {
		this.payload = payload;
	}


	

}
