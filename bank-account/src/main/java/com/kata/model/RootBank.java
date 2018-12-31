package com.kata.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kata.exception.AccountException;
import com.kata.exception.BankException;
import com.kata.exception.RootBankException;

public class RootBank {

	Map<String, Bank> listBank;
	List<TransactionHistory> transactionHistory;

	public RootBank() {
		listBank = new HashMap<String, Bank>();
		transactionHistory = new ArrayList<TransactionHistory>();
	}

	public Bank getBank(String id) throws RootBankException {
		Bank bank = listBank.get(id);
		if (bank == null) {
			throw new RootBankException("Bank Id '" + id + "' not found");
		}
		return bank;
	}

	public void addBank(Bank bank) throws RootBankException {
		if (this.listBank.get(bank.getId()) != null) {
			throw new RootBankException("Bank Id '" + bank.getId() + "' already exists");
		}

		this.listBank.put(bank.getId(), bank);
	}

	public List<TransactionHistory> getTransactionHistory() {
		return transactionHistory;
	}

	public void transfer(String bankIdFrom, String accountIdFrom, String bankIdTo, String accountIdTo,
			double amountTransfer) throws RootBankException, AccountException, BankException {

		Bank bankFrom = this.getBank(bankIdFrom);
		Account accountFrom = bankFrom.getAccount(accountIdFrom);
		Bank bankTo = this.getBank(bankIdTo);
		Account accountTo = bankTo.getAccount(accountIdTo);

		if (amountTransfer < 0) {
			throw new RootBankException("Amount transfer can't be negative");
		}

		if (accountFrom.getAmount() < amountTransfer) {
			throw new RootBankException("The account payer doesn't have enough money to transfer");
		}

		// Begin transaction
		accountFrom.withdrawAmount(amountTransfer);
		accountTo.depositAmount(amountTransfer);
		this.transactionHistory.add(new TransactionHistory(bankFrom, accountFrom, bankTo, accountTo, amountTransfer));
		// End transaction
	}

	public List<TransactionHistory> findAccountTransactionHistory(String bankId, String accountId) {

		List<TransactionHistory> findTransactionHistory = new ArrayList<TransactionHistory>();

		for (TransactionHistory transaction : transactionHistory) {
			if ((transaction.getBankFrom().getId().equals(bankId)
					&& transaction.getAccountFrom().getId().equals(accountId))
					|| (transaction.getBankTo().getId().equals(bankId)
							&& transaction.getAccountTo().getId().equals(accountId))) {

				findTransactionHistory.add(transaction);
			}
		}

		return findTransactionHistory;
	}

}
