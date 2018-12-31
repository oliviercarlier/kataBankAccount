package com.kata.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kata.exception.AccountException;
import com.kata.exception.BankException;

public class BankTest {

	@Test
	public void createBankWithoutError() throws BankException, AccountException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account("account01", 15.0);

		// When
		bank.addAccount(account);

		// Then
		assertEquals(bank.getAccount(account.getId()).getAmount(), 15.0, 0.0);
	}

	@Test(expected = BankException.class)
	public void createBankWithTwiceSameAccount() throws BankException, AccountException {
		// Given
		Bank bank = new Bank("bank01");
		Account account01 = new Account("account01", 15.0);
		Account account02 = new Account("account01", 30.0);

		// When
		bank.addAccount(account01);
		bank.addAccount(account02);

		// Then
	}

	@Test(expected = BankException.class)
	public void findAccoundNotFound() throws BankException, AccountException {
		// Given
		Bank bank = new Bank("bank01");
		Account account01 = new Account("account01", 15.0);
		bank.addAccount(account01);

		// When
		bank.getAccount("account03");

		// Then
	}

	@Test(expected = BankException.class)
	public void createBankWithEmptyId() throws BankException {
		// Given
		new Bank("");

		// When

		// Then
	}

	@Test(expected = BankException.class)
	public void createBankWithNullId() throws BankException {
		// Given
		new Bank(null);

		// When

		// Then
	}

}
