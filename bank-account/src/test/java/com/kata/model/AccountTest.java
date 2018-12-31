package com.kata.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kata.exception.AccountException;
import com.kata.exception.BankException;

public class AccountTest {

	@Test
	public void createAccountWithoutError() throws AccountException, BankException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account("account01", 15.0);

		// When
		bank.addAccount(account);

		// Then
		assertEquals(bank.getAccount("account01").getAmount(), 15.0, 0.0);
	}

	@Test(expected = AccountException.class)
	public void createAccountWithEmptyId() throws AccountException, BankException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account("", 15.0);

		// When
		bank.addAccount(account);

		// Then
	}

	@Test(expected = AccountException.class)
	public void createAccountWithNullId() throws AccountException, BankException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account(null, 15.0);

		// When
		bank.addAccount(account);

		// Then
	}

	@Test(expected = AccountException.class)
	public void createAccountWithNegativeAmount() throws AccountException, BankException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account("account01", -15.0);

		// When
		bank.addAccount(account);

		// Then
	}

	@Test
	public void withdrawAmountWithPositiveAmount() throws AccountException, BankException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account("account01", 15.0);
		bank.addAccount(account);

		// When
		bank.getAccount("account01").withdrawAmount(10.0);

		// Then
		assertEquals(bank.getAccount("account01").getAmount(), 5.0, 0.0);
	}

	@Test
	public void withdrawAmountWithZeroTotal() throws AccountException, BankException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account("account01", 15.0);
		bank.addAccount(account);

		// When
		bank.getAccount("account01").withdrawAmount(15.0);

		// Then
		assertEquals(bank.getAccount("account01").getAmount(), 0.0, 0.0);
	}

	@Test(expected = AccountException.class)
	public void withdrawAmountWithNegativeAmount() throws AccountException, BankException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account("account01", 15.0);
		bank.addAccount(account);

		// When
		bank.getAccount("account01").withdrawAmount(-10.0);

		// Then
	}

	@Test(expected = AccountException.class)
	public void withdrawAmountWithNegativeTotal() throws AccountException, BankException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account("account01", 15.0);
		bank.addAccount(account);

		// When
		bank.getAccount("account01").withdrawAmount(16.0);

		// Then
	}

	@Test
	public void depositAmountWithPositiveAmount() throws AccountException, BankException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account("account01", 15.0);
		bank.addAccount(account);

		// When
		bank.getAccount("account01").depositAmount(10.0);

		// Then
		assertEquals(bank.getAccount("account01").getAmount(), 25.0, 0.0);
	}

	@Test(expected = AccountException.class)
	public void depositAmountWithNegativeAmount() throws AccountException, BankException {
		// Given
		Bank bank = new Bank("bank01");
		Account account = new Account("account01", 15.0);
		bank.addAccount(account);

		// When
		bank.getAccount("account01").depositAmount(-10.0);

		// Then
	}

}
