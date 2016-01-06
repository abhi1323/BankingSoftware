package account.db;

import account.intf.IAccount_DB;


public class Account_DB implements IAccount_DB{

	Integer acntNum ;
	String name;
	Double balance;
	Double minBalance;
	String pwd;

	public Account_DB(String name, Double balance){
		this.name = name;
		this.balance = balance;
		minBalance = 500.0;
	}

	public Account_DB(String name, Double balance,String pwd){
		this.name = name;
		this.balance = balance;
		minBalance = 500.0;
		this.pwd = pwd;
	}

	public String getName(){
		return name;
	}

	public synchronized double getBalance(){
		return balance;
	}

	public synchronized void setBalance(double amnt){
		balance = amnt;
	}

	public double getMinBalance(){
		return minBalance;
	}

	public String getPwd(){
		return pwd;
	}

	public void setAcntNum(int acntNum){
		this.acntNum = acntNum;
	}
	public int getAcntNum(){
		return acntNum;
	}


	@Override
	public synchronized void deposit(Double amount) {

		try {
			/*Just to simulate 0 to 2 Seconds Delay, to match real-time Scenario*/
			Thread.sleep((long)(Math.random()*2000));  

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		balance += amount;
		return;	
	}

	@Override
	public synchronized boolean withdraw(Double amount) {


		try {
			//Just to simulate 0 to 2 Seconds Delay, to match real-time Scenario
			Thread.sleep((long)(Math.random()*2000));  

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if(amount < (balance-minBalance)){
			balance -=amount;

			return true;
		}else{

			return false;
		}
	}

}
