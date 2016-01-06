package account.intf;


//Provide support for account with deposit, withdraw, and balance query features.
public interface IAccount {
	
	double getBalance();
	void deposit(Double amount);
	boolean withdraw(Double amount);

}
