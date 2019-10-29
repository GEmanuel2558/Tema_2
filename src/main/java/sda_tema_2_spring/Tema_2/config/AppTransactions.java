package sda_tema_2_spring.Tema_2.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"sda_tema_2_spring.Tema_2.data.repository"})
@Component
public class AppTransactions implements TransactionManager {
}
