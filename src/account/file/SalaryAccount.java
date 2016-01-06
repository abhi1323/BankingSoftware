package account.file;

import account.intf.IOverDraftAccount;
import account.intf.ISalaryAccount;

public class SalaryAccount  extends Account implements ISalaryAccount,IOverDraftAccount{

	
	public SalaryAccount(String name, Double amount) {
		super(name,amount);		
	}
	
	public SalaryAccount(String name, Double amount,String pwd) {
		super(name,amount,pwd);		
	}
	
	@Override
	public double getEligibleLoanAmount() {
		
		double balance = getBalance();		
		return (6*balance);
	}
	
	public boolean withdraw(Double amount){
		
		try {
			/*Just to simulate 0 to 10 Secs Delay, to match real-time Scenario*/
			Thread.sleep((long)(Math.random()*10000));  
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(amount < (balance)){
			balance -=amount;
			System.out.println("Withdraw of " + amount + "is Successful");
			System.out.println("Outstanding Balance:"+ balance);
			return true;
		}else{
			System.out.println("Withdraw of " + amount + "is Failed");
			System.out.println("Outstanding Balance:"+ balance);
			
			return false;
			}
				
	}

	@Override
	public double getOverDraftLimit() {
		return 0;
	}


	
}
