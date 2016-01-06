package txs.db;

import storage.db.DBStorage;
import txs.TxType;
import account.db.Account_DB;

public class TxThread_DB extends Thread {
	
	int acntNum;
	Double amnt;
	TxType txType;
	DBStorage dbs;
	
	public TxThread_DB(int acntNum, TxType txType, DBStorage dbs) {
		this.acntNum = acntNum;		
		this.txType = txType;
		amnt =0.0;
		this.dbs = dbs;
	}
	
	TxThread_DB(int acntNum, Double amnt, TxType txType, DBStorage dbs){
		this.acntNum = acntNum;
		this.amnt = amnt;
		this.txType = txType;
		this.dbs = dbs;

	}
	
	private synchronized Account_DB getAccount(){
		Account_DB acnt = dbs.getAccount(acntNum);
		if(acnt == null){
			System.out.println("Unable to retreive Account(" +acntNum+" )");
		}
		return acnt;
	}

	@Override
	public void run() {
		Account_DB acnt=null;
		
		switch(txType){
		
		case GETBALANCE:
			acnt = getAccount();
			if(acnt!=null)
				System.out.println("Balance of Account(" + acntNum  + "):" + acnt.getBalance());
			else
				System.out.println("Unable to get Balance of  Account(" +acntNum+")");			
		
			break;
		case DEPOSIT:
			
			acnt = getAccount();
			if(acnt!=null)
			{
				acnt.deposit(amnt);
				/*Persist changes to DB*/
				if(dbs.updateAccount(acnt))
				{
					System.out.println("Depoist of " + amnt +" into Account(" + acntNum  + ") is Successful");
					System.out.println("Current Balance of Account(" + acntNum  + "):" + acnt.getBalance());
					
					
					dbs.storeTransaction(acntNum,amnt,TxType.DEPOSIT);
				}
				else
					System.out.println("Deposit into Account(" +acntNum+") failed");			
			}	
		
			
			break;
			
		case WITHDRAW:
			acnt = getAccount();
			if(acnt!=null)
			{
				if(acnt.withdraw(amnt)){
					//Persist changes to DB
					if(dbs.updateAccount(acnt))
					{
						System.out.println("Withdraw of " + amnt +" from Account(" + acntNum  + ") is Successful");
						System.out.println("Current Balance of Account(" + acntNum  + "):" + acnt.getBalance());
						
						dbs.storeTransaction(acntNum,amnt,TxType.WITHDRAW);
					}
				}
			}
			break;	
			
		default:
			System.out.println("Invalid Tx Type");
			break;
			
		}
	}

}
