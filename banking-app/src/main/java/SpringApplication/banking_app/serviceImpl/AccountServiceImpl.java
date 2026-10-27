package SpringApplication.banking_app.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import SpringApplication.banking_app.DTO.TransactionDto;
import SpringApplication.banking_app.DTO.TransferFundDTO;
import SpringApplication.banking_app.entity.Account;
import SpringApplication.banking_app.entity.Transaction;
import SpringApplication.banking_app.exception.AccountException;
import SpringApplication.banking_app.repository.AccountRepository;
import SpringApplication.banking_app.repository.TransactionRepository;
import SpringApplication.banking_app.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	
	private AccountRepository accountRepository;
	private TransactionRepository transactionRepository;

	public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
		super();
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}

	private static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
	private static final String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";
	private static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";

	@Override
	public Account createAccount(Account account) {
		Account savedAccount = accountRepository.save(account);
		return savedAccount;
	}


	@Override
	public List<Account> getAllAccount(Account account) {
		List<Account> fetchAllAccounts = accountRepository.findAll();
		return fetchAllAccounts;
	}


	@Override
	public Account getAccountById(Long id) {
		Account getAccount = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
		return getAccount;
	}


	@Override
	public Account deposite(Long id, Double amount) {
		
		Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
		
		double total = account.getBalance() + amount;
		account.setBalance(total);
		Account savedAccount = accountRepository.save(account);
		
		Transaction transaction = new Transaction();
		transaction.setAccountId(id);
		transaction.setAmount(amount);
		transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
		transaction.setTimestamp(LocalDateTime.now());
		transactionRepository.save(transaction);
		
		return savedAccount;
	}


	@Override
	public Account withdraw(Long id, Double amount) {
		
		Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
		
		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficent balance");
		}
		
		double total = account.getBalance() - amount;
		account.setBalance(total);
		Account savedAccount = accountRepository.save(account);
		
		Transaction transaction = new Transaction();
		transaction.setAccountId(id);
		transaction.setAmount(amount);
		transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
		transaction.setTimestamp(LocalDateTime.now());
		transactionRepository.save(transaction);
		
		return savedAccount;
	}


	@Override
	public void delete(Long id) {
		
		accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
		
		accountRepository.deleteById(id);
		
	}


	@Override
	public void transferFunds(TransferFundDTO transferFundDTO) {
		
		Account fromAccount = accountRepository.findById(transferFundDTO.fromAccountId())
											   .orElseThrow(() -> new AccountException("Account does not exist"));
		
		if(fromAccount.getBalance() < transferFundDTO.amount()) {
			throw new RuntimeException("Insufficent balance");
		}
		
		Account toAccount = accountRepository.findById(transferFundDTO.toAccountId())
				   							   .orElseThrow(() -> new AccountException("Account does not exist"));
		
		fromAccount.setBalance(fromAccount.getBalance() - transferFundDTO.amount());
		
		toAccount.setBalance(toAccount.getBalance() + transferFundDTO.amount());
		
		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
		
		Transaction transaction = new Transaction();
		transaction.setAccountId(transferFundDTO.fromAccountId());
		transaction.setAmount(transferFundDTO.amount());
		transaction.setTransactionType(TRANSACTION_TYPE_TRANSFER);
		transaction.setTimestamp(LocalDateTime.now());
		transactionRepository.save(transaction);
	}


	@Override
	public List<TransactionDto> getAccountsTransactions(Long accountId) {
		
		List<Transaction> transactions = transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
		
		return transactions.stream().map((transaction) -> convertEntityToDto(transaction))
								   .collect(Collectors.toList());
	}
	
	private TransactionDto convertEntityToDto(Transaction transaction) {
		return new TransactionDto(transaction.getId(), 
								  transaction.getAccountId(), 
								  transaction.getAmount(), 
								  transaction.getTransactionType(), 
								  transaction.getTimestamp());
	}
}
