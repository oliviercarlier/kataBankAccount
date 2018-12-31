package com.kata.model;

import com.kata.exception.AccountException;

public class Account {

	String id;
	double amount;

	public Account(String id, double amount) throws AccountException {

		if (id == null || id.isEmpty()) {
			throw new AccountException("Id account can't be null or empty");
		}

		checkAmount(amount);

		this.id = id;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public double getAmount() {
		return amount;
	}

	public void withdrawAmount(double amount) throws AccountException {

		checkAmount(amount);

		if (this.amount < amount) {
			throw new AccountException("Total of account can't be negative");
		}

		this.amount = this.amount - amount;
	}

	public void depositAmount(double amount) throws AccountException {

		checkAmount(amount);

		this.amount = this.amount + amount;
	}

	private void checkAmount(double amount) throws AccountException {
		if (amount < 0) {
			throw new AccountException("Amount can't be negative");
		}
	}

}
