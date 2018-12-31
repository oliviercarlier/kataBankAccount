package com.kata.model;

import java.util.HashMap;
import java.util.Map;

import com.kata.exception.BankException;

public class Bank {

	String id;
	Map<String, Account> listAccount;

	public Bank(String id) throws BankException {

		if (id == null || id.isEmpty()) {
			throw new BankException("Id bank can't be null or empty");
		}

		this.id = id;
		listAccount = new HashMap<String, Account>();
	}

	public String getId() {
		return id;
	}

	public Account getAccount(String id) throws BankException {
		Account account = listAccount.get(id);
		if (account == null) {
			throw new BankException("Account Id '" + id + "' not found");
		}
		return account;
	}

	public void addAccount(Account account) throws BankException {
		if (this.listAccount.get(account.getId()) != null) {
			throw new BankException("Account Id '" + account.getId() + "' already exists");
		}

		this.listAccount.put(account.getId(), account);
	}

}
