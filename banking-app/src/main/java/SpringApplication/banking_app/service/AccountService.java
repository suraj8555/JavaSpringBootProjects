package SpringApplication.banking_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import SpringApplication.banking_app.DTO.TransactionDto;
import SpringApplication.banking_app.DTO.TransferFundDTO;
import SpringApplication.banking_app.entity.Account;

@Service
public interface AccountService {
	
	Account createAccount(Account account);
	
	List<Account> getAllAccount(Account account);
	
	Account getAccountById(Long id);
	
	Account deposite(Long id, Double amount);
	
	Account withdraw(Long id, Double amount);
	
	void delete(Long id);
	
	void transferFunds(TransferFundDTO transferFundDTO);
	
	List<TransactionDto> getAccountsTransactions(Long accountId);

}
