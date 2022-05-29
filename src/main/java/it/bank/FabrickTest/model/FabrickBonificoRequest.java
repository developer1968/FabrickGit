package it.bank.FabrickTest.model;

public class FabrickBonificoRequest {
	Creditor CreditorObject;
	private String executionDate;
	private String description;
	private float amount;
	private String currency;


	// Getter Methods 

	public Creditor getCreditor() {
		return CreditorObject;
	}

	public String getExecutionDate() {
		return executionDate;
	}

	public String getDescription() {
		return description;
	}

	public float getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	// Setter Methods 

	public void setCreditor(Creditor creditorObject) {
		this.CreditorObject = creditorObject;
	}

	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}


