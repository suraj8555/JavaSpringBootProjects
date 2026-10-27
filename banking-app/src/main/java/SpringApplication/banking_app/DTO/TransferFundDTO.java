package SpringApplication.banking_app.DTO;

public record TransferFundDTO(
		Long fromAccountId,
		Long toAccountId,
		double amount) {

}
