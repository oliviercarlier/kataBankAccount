package com.kata.model;

public class TransactionHistory {

	Bank bankFrom;
	Account accountFrom;
	Bank bankTo;
	Account accountTo;
	double amount;

	public TransactionHistory(Bank bankFrom, Account accountFrom, Bank bankTo, Account accountTo, double amount) {
		super();
		this.bankFrom = bankFrom;
		this.accountFrom = accountFrom;
		this.bankTo = bankTo;
		this.accountTo = accountTo;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transfer " + amount + " from (Bank=" + bankFrom.getId() + ";Account=" + accountFrom.getId()
				+ ") to (Bank=" + bankTo.getId() + ";Account=" + accountTo.getId() + ")";
	}

	public Bank getBankFrom() {
		return bankFrom;
	}

	public Account getAccountFrom() {
		return accountFrom;
	}

	public Bank getBankTo() {
		return bankTo;
	}

	public Account getAccountTo() {
		return accountTo;
	}

	public double getAmount() {
		return amount;
	}

}
