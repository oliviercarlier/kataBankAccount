package com.kata.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.kata.exception.AccountException;
import com.kata.exception.BankException;
import com.kata.exception.RootBankException;

public class RootBankTest {

	@Test
	public void createRootBankWithoutError() {
		// Given
		RootBank rootBank = new RootBank();

		// When

		// Then
		assertNotNull(rootBank);
	}

	@Test(expected = RootBankException.class)
	public void createRootBankWithTwiceSameBank() throws BankException, RootBankException {
		// Given
		RootBank rootBank = new RootBank();
		Bank bank01 = new Bank("bank01");
		Bank bank02 = new Bank("bank01");

		// When
		rootBank.addBank(bank01);
		rootBank.addBank(bank02);

		// Then
	}

	@Test(expected = RootBankException.class)
	public void findBankNotFound() throws BankException, AccountException, RootBankException {
		// Given
		RootBank rootBank = new RootBank();
		Bank bank = new Bank("bank01");
		Account account01 = new Account("account01", 15.0);
		bank.addAccount(account01);
		rootBank.addBank(bank);

		// When
		rootBank.getBank("bank03");

		// Then
	}

	@Test
	public void transferWitoutError() throws AccountException, BankException, RootBankException {
		// Given
		RootBank rootBank = new RootBank();
		Bank bank01 = new Bank("bank01");
		Bank bank02 = new Bank("bank02");
		Account account01 = new Account("account01", 100);
		Account account02 = new Account("account02", 200);

		bank01.addAccount(account01);
		bank02.addAccount(account02);
		rootBank.addBank(bank01);
		rootBank.addBank(bank02);

		// When
		rootBank.transfer(bank01.getId(), account01.getId(), bank02.getId(), account02.getId(), 20.0);

		// Then
		assertEquals(rootBank.getBank("bank01").getAccount("account01").getAmount(), 80.0, 0.0);
		assertEquals(rootBank.getBank("bank02").getAccount("account02").getAmount(), 220.0, 0.0);
	}

	@Test(expected = RootBankException.class)
	public void transferWithNotEnoughMoney() throws AccountException, BankException, RootBankException {
		// Given
		RootBank rootBank = new RootBank();
		Bank bank01 = new Bank("bank01");
		Bank bank02 = new Bank("bank02");
		Account account01 = new Account("account01", 100);
		Account account02 = new Account("account02", 200);

		bank01.addAccount(account01);
		bank02.addAccount(account02);
		rootBank.addBank(bank01);
		rootBank.addBank(bank02);

		// When
		rootBank.transfer(bank01.getId(), account01.getId(), bank02.getId(), account02.getId(), 120.0);

		// Then
	}

	@Test(expected = RootBankException.class)
	public void transferWithNegativeAmount() throws AccountException, BankException, RootBankException {
		// Given
		RootBank rootBank = new RootBank();
		Bank bank01 = new Bank("bank01");
		Bank bank02 = new Bank("bank02");
		Account account01 = new Account("account01", 100);
		Account account02 = new Account("account02", 200);

		bank01.addAccount(account01);
		bank02.addAccount(account02);
		rootBank.addBank(bank01);
		rootBank.addBank(bank02);

		// When
		rootBank.transfer(bank01.getId(), account01.getId(), bank02.getId(), account02.getId(), -20.0);

		// Then
	}

	@Test(expected = RootBankException.class)
	public void transferWithBankNotFound() throws AccountException, BankException, RootBankException {
		// Given
		RootBank rootBank = new RootBank();
		Bank bank01 = new Bank("bank01");
		Bank bank02 = new Bank("bank02");
		Account account01 = new Account("account01", 100);
		Account account02 = new Account("account02", 200);

		bank01.addAccount(account01);
		bank02.addAccount(account02);
		rootBank.addBank(bank01);
		rootBank.addBank(bank02);

		// When
		rootBank.transfer("bank03", account01.getId(), bank02.getId(), account02.getId(), -20.0);

		// Then
	}

	@Test(expected = BankException.class)
	public void transferWithAccountNotFound() throws AccountException, BankException, RootBankException {
		// Given
		RootBank rootBank = new RootBank();
		Bank bank01 = new Bank("bank01");
		Bank bank02 = new Bank("bank02");
		Account account01 = new Account("account01", 100);
		Account account02 = new Account("account02", 200);

		bank01.addAccount(account01);
		bank02.addAccount(account02);
		rootBank.addBank(bank01);
		rootBank.addBank(bank02);

		// When
		rootBank.transfer(bank01.getId(), "account03", bank02.getId(), account02.getId(), 20.0);

		// Then
	}

	@Test
	public void transferCheckTransactionHistory() throws AccountException, BankException, RootBankException {
		// Given
		RootBank rootBank = new RootBank();
		Bank bank01 = new Bank("bank01");
		Bank bank02 = new Bank("bank02");
		Account account01 = new Account("account01", 100);
		Account account02 = new Account("account02", 200);

		bank01.addAccount(account01);
		bank02.addAccount(account02);
		rootBank.addBank(bank01);
		rootBank.addBank(bank02);

		// When
		rootBank.transfer(bank01.getId(), account01.getId(), bank02.getId(), account02.getId(), 20.0);

		// Then
		assertEquals(rootBank.getBank("bank01").getAccount("account01").getAmount(), 80.0, 0.0);
		assertEquals(rootBank.getBank("bank02").getAccount("account02").getAmount(), 220.0, 0.0);
		assertEquals(rootBank.getTransactionHistory().get(0).toString(),
				"Transfer 20.0 from (Bank=bank01;Account=account01) to (Bank=bank02;Account=account02)");
	}

	@Test
	public void findTransactionHistory() throws AccountException, BankException, RootBankException {
		// Given
		RootBank rootBank = new RootBank();
		Bank bank01 = new Bank("bank01");
		Bank bank02 = new Bank("bank02");
		Account account01 = new Account("account01", 100);
		Account account02 = new Account("account02", 200);

		bank01.addAccount(account01);
		bank02.addAccount(account02);
		rootBank.addBank(bank01);
		rootBank.addBank(bank02);

		rootBank.transfer(bank01.getId(), account01.getId(), bank02.getId(), account02.getId(), 20.0);

		// When
		List<TransactionHistory> listTransaction1 = rootBank.findAccountTransactionHistory("bank01", "account01");
		List<TransactionHistory> listTransaction2 = rootBank.findAccountTransactionHistory("bank02", "account02");

		// Then
		assertEquals(listTransaction1.size(), 1);
		assertEquals(listTransaction1.get(0).getAmount(), 20.0, 0.0);
		assertEquals(listTransaction2.size(), 1);
		assertEquals(listTransaction2.get(0).getAmount(), 20.0, 0.0);
	}

	@Test
	public void findTransactionHistoryWithoutResult() throws AccountException, BankException, RootBankException {
		// Given
		RootBank rootBank = new RootBank();
		Bank bank01 = new Bank("bank01");
		Bank bank02 = new Bank("bank02");
		Account account01 = new Account("account01", 100);
		Account account02 = new Account("account02", 200);

		bank01.addAccount(account01);
		bank02.addAccount(account02);
		rootBank.addBank(bank01);
		rootBank.addBank(bank02);

		rootBank.transfer(bank01.getId(), account01.getId(), bank02.getId(), account02.getId(), 20.0);

		// When
		List<TransactionHistory> listTransaction1 = rootBank.findAccountTransactionHistory("bank01", "account02");
		List<TransactionHistory> listTransaction2 = rootBank.findAccountTransactionHistory("bank02", "account01");

		// Then
		assertEquals(listTransaction1.size(), 0);
		assertEquals(listTransaction2.size(), 0);
	}

}
