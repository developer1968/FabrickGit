package it.bank.FabrickTest.exceptions;

public class AccountException extends Exception {
	private static final long serialVersionUID = 1571572420754437868L;
	
	public AccountException(String errorMessage) {
        super(errorMessage);
    }
	
}
