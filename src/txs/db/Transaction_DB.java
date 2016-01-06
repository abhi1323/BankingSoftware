package txs.db;

import storage.db.DBStorage;
import txs.TxType;
import account.file.Account;

public class Transaction_DB  {
	
	DBStorage dbs;
	
	public Transaction_DB(	DBStorage dbs) {
		this.dbs = dbs;
	}
	
	public void getBalance(int acntNum){
		
		TxThread_DB tx = new TxThread_DB(acntNum,TxType.GETBALANCE,dbs);
		tx.start();
	}
	
	public void withdraw(int acntNum, double amnt){
		
		TxThread_DB tx = new TxThread_DB(acntNum,amnt,TxType.WITHDRAW,dbs);
		tx.start();
		
	}
	
	public void deposit(int acntNum, double amnt){
		TxThread_DB tx = new TxThread_DB(acntNum,amnt,TxType.DEPOSIT,dbs);
		tx.start();
	}

}
