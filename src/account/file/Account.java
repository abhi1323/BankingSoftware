package account.file;

import java.io.Serializable;

import account.intf.IAccount;
import storage.file.PersistAccount;

public class Account implements IAccount,Serializable{
	
	final Integer accNum ;
	String name;
	Double balance;
	Double minBalance;
	String pwd;
	
	
	private static int objInstance;
	
	static{
		
		System.out.println("In Static Block");
		//objInstance =1;
		objInstance = PersistAccount.getAccNum();
	}
	
	
	public Account(String name, Double balance){
		accNum=generateAccNum();
		this.name = name;
		this.balance = balance;
		minBalance = 500.0;
		PersistAccount.persistAccNum(accNum);
		
	}
	
	public Account(String name, Double balance,String pwd){
		accNum=generateAccNum();
		this.name = name;
		this.balance = balance;
		minBalance = 500.0;
		this.pwd = pwd;
		PersistAccount.persistAccNum(accNum);
		
	}
	
	@Override
	public synchronized double getBalance() {
		
		try {
			/*Just to simulate 0 to 2 Secs Delay, to match real-time Scenario*/
			Thread.sleep((long)(Math.random()*5000));  
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return balance;		
	}

	@Override
	public synchronized void deposit(Double amount) {
		
		try {
			/*Just to simulate 0 to 10 Secs Delay, to match real-time Scenario*/
			Thread.sleep((long)(Math.random()*5000));  
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		balance += amount;
		System.out.println("Outstanding Balance:"+ balance);
		return;	
	}

	@Override
	public synchronized boolean withdraw(Double amount) {
		
		
		try {
			/*Just to simulate 0 to 10 Secs Delay, to match real-time Scenario*/
			Thread.sleep((long)(Math.random()*5000));  
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(amount < (balance-minBalance)){
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
	
	
	private int generateAccNum(){		
		return ++objInstance;
	}
	
	public void getAccountInfo(){
		System.out.println(accNum + ": " + name);		
	}
	
	public int getAccountNum(){
		return accNum;
	}
	
	public String getPwd(){
		return pwd;
	}
	
	public String getName(){
		return name;
	}
	
	public double getMinBalance(){
		return minBalance;
	}
	
	
	/*public static void SimpleInterestCalc(){
		System.out.println("SimpleInterestCalc");
	}*/
	
	

}
