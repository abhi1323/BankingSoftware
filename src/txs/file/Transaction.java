package txs.file;

import txs.TxType;
import account.file.Account;

public class Transaction  {
	
	
	public void getBalance(Account account){
		
		TxThread tx = new TxThread(account,TxType.GETBALANCE);
		tx.start();
	}
	
	public void withdraw(Account account, double amnt){
		
		TxThread tx = new TxThread(account,amnt,TxType.WITHDRAW);
		tx.start();
		
	}
	
	public void deposit(Account account, double amnt){
		TxThread tx = new TxThread(account,amnt,TxType.DEPOSIT);
		tx.start();
	}

}
