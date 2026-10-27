package SpringApplication.banking_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringApplication.banking_app.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);

}
