package txs.file;

import txs.TxType;
import account.file.Account;

public class TxThread extends Thread {
	
	Account accnt;
	Double amnt;
	TxType txType;
	
	public TxThread(Account accnt, TxType txType) {
		this.accnt = accnt;		
		this.txType = txType;
		amnt =0.0;
	}
	
	TxThread(Account accnt, Double amnt, TxType txType){
		this.accnt = accnt;
		this.amnt = amnt;
		this.txType = txType;
	}

	@Override
	public void run() {
		
		switch(txType){
		
		case GETBALANCE:
			accnt.getBalance();
		
		case DEPOSIT:
			accnt.deposit(amnt);
			break;
			
		case WITHDRAW:
			accnt.withdraw(amnt);
			break;	
			
		default:
			System.out.println("Invalid Tx Type");
			break;
			
		}
	}

}
