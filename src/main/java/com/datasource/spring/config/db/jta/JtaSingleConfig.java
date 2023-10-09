package com.datasource.spring.config.db.jta;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.datasource.spring.config.db.postgresql.jpa.AtomikosJtaPlatform;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.UserTransaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableTransactionManagement
public class JtaSingleConfig {

    @Bean
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);

        AtomikosJtaPlatform.userTransaction = userTransactionImp;

        return userTransactionImp;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public TransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);

        AtomikosJtaPlatform.transactionManager = userTransactionManager;

        return userTransactionManager;
    }

    @Bean
    public PlatformTransactionManager jtaSingleTransactionManager(UserTransaction userTransaction
            , TransactionManager atomikosTransactionManager) {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(userTransaction, atomikosTransactionManager);

        return jtaTransactionManager;
    }
}
