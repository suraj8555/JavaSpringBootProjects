package SpringApplication.banking_app.controller;

import org.springframework.web.bind.annotation.RestController;

import SpringApplication.banking_app.DTO.TransactionDto;
import SpringApplication.banking_app.DTO.TransferFundDTO;
import SpringApplication.banking_app.entity.Account;
import SpringApplication.banking_app.service.AccountService;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private AccountService accountService;

	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@PostMapping("/create")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		Account accountCreated = accountService.createAccount(account);
		return new ResponseEntity<>(accountCreated, HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Account>> getAllAccounts(Account account) {
		List<Account> fetchAllAccounts = accountService.getAllAccount(account);
		return ResponseEntity.ok(fetchAllAccounts);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
		Account accountById = accountService.getAccountById(id);
		return ResponseEntity.ok(accountById);
	}

	@PutMapping("/{id}/deposite")
	public ResponseEntity<Account> deposite(@PathVariable Long id, @RequestBody Map<String, Double> request) {

		Double amount = request.get("amount");
		Account account = accountService.deposite(id, amount);
		return ResponseEntity.ok(account);
	}

	@PutMapping("/{id}/withdraw")
	public ResponseEntity<Account> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {

		Double amount = request.get("amount");
		Account account = accountService.withdraw(id, amount);
		return ResponseEntity.ok(account);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		accountService.delete(id);

		return ResponseEntity.ok("Account deleted successfully!!!!");
	}

	@PostMapping("/transfer")
	public ResponseEntity<String> transferFund(@RequestBody TransferFundDTO transferFundDTO) {

		accountService.transferFunds(transferFundDTO);

		return ResponseEntity.ok("Transfer successfull!!");
	}

	@GetMapping("/{accountId}/transactions")
	public ResponseEntity<List<TransactionDto>> fetchAccountTransactions(@PathVariable Long accountId) {
		
		List<TransactionDto> fetchAllAccounts = accountService.getAccountsTransactions(accountId);
		
		return ResponseEntity.ok(fetchAllAccounts);
	}
}
