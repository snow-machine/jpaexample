package com.springinaction.jpaexample;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

public class FakeTransactionManager implements PlatformTransactionManager {

	public TransactionStatus getTransaction(TransactionDefinition definition)
			throws TransactionException {
		// TODO Auto-generated method stub
		return null;
	}

	public void commit(TransactionStatus status) throws TransactionException {
		// TODO Auto-generated method stub

	}

	public void rollback(TransactionStatus status) throws TransactionException {
		// TODO Auto-generated method stub

	}

}
