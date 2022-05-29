package it.bank.FabrickTest.model;

public class PayloadSaldo {
	private String accountId;
	private String iban;
	private String abiCode;
	private String cabCode;
	private String countryCode;
	private String internationalCin;
	private String nationalCin;
	private String account;
	private String alias;
	private String productName;
	private String holderName;
	private String activatedDate;
	private String currency;


	// Getter Methods 

	public String getAccountId() {
		return accountId;
	}

	public String getIban() {
		return iban;
	}

	public String getAbiCode() {
		return abiCode;
	}

	public String getCabCode() {
		return cabCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getInternationalCin() {
		return internationalCin;
	}

	public String getNationalCin() {
		return nationalCin;
	}

	public String getAccount() {
		return account;
	}

	public String getAlias() {
		return alias;
	}

	public String getProductName() {
		return productName;
	}

	public String getHolderName() {
		return holderName;
	}

	public String getActivatedDate() {
		return activatedDate;
	}

	public String getCurrency() {
		return currency;
	}

	// Setter Methods 

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public void setAbiCode(String abiCode) {
		this.abiCode = abiCode;
	}

	public void setCabCode(String cabCode) {
		this.cabCode = cabCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setInternationalCin(String internationalCin) {
		this.internationalCin = internationalCin;
	}

	public void setNationalCin(String nationalCin) {
		this.nationalCin = nationalCin;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public void setActivatedDate(String activatedDate) {
		this.activatedDate = activatedDate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}