package com.datasource.spring.config.db.postgresql.jpa;

import jakarta.transaction.TransactionManager;
import jakarta.transaction.UserTransaction;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

public class AtomikosJtaPlatform extends AbstractJtaPlatform {
    private static final long serialVersionUID = 1L;

    public static TransactionManager transactionManager;
    public static UserTransaction userTransaction;

    @Override
    protected TransactionManager locateTransactionManager() {
        return transactionManager;
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return userTransaction;
    }
}
