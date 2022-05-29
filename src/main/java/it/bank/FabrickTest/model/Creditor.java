package it.bank.FabrickTest.model;

public class Creditor {
	private String name;
	private Account AccountObject;


	// Getter Methods 

	public String getName() {
		return name;
	}

	public Account getAccount() {
		return AccountObject;
	}

	// Setter Methods 

	public void setName(String name) {
		this.name = name;
	}

	public void setAccount(Account accountObject) {
		this.AccountObject = accountObject;
	}
}
