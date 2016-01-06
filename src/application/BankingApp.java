package application;

import java.util.Scanner;

import storage.file.PersistAccount;
import account.file.Account;
import account.file.SalaryAccount;

public class BankingApp {
	
	static Scanner scan;
	static Account accounts[] = new Account[10];
	
	public static void main(String[] args) {
				
		scan= new Scanner(System.in);
		int option;
		do{
			displayOptions();			
			option=scan.nextInt();
			switch(option){
				case 1:
					createAccount();
					break;
				case 2:
					deposit();
					break;					
				case 3:
					withdraw();
					break;
					
				case 4:
					getBalance();
					break;
					
				case 9:
					System.out.println("Exiting The Application");
					System.exit(0);
					break;
			}
			
		}while(true);
		
	}
	
	static void displayOptions(){
		System.out.println("----------------------");
		System.out.println(" 1. Create Account");
		System.out.println(" 2. Deposit");
		System.out.println(" 3. WithDraw");
		System.out.println(" 4. GetBalance");
		System.out.println(" 9. Exit Application");
		System.out.println("Choose Operation:");
	}
	
	static void  createAccount(){
		
		System.out.println("Choose Account Type:");
		System.out.println(" 1. Simple Account");
		System.out.println(" 2. Salary Account");
		
		
		int acType = scan.nextInt();
		System.out.println("Enter Name: ");
		String name = scan.next();
		
		System.out.println("Enter Opening Balance: ");
		Double balance = scan.nextDouble();
		
		System.out.println("Creating Account .......");
		
		if(acType ==1){
			Account acc = new Account(name,balance);
			//accounts[acc.getAccountNum()] = acc;
			PersistAccount.persistAccount(acc);
			System.out.println("New Accout Created with AccNum:  " + acc.getAccountNum() );
		}else{
			SalaryAccount sAcc = new SalaryAccount(name, balance);			
			//accounts[sAcc.getAccountNum()] = sAcc;
			PersistAccount.persistAccount(sAcc);
			System.out.println("New Accout Created with AccNum:  " + sAcc.getAccountNum() );
		}
		
		System.out.println("------------------");
	}
	
	static void deposit(){
		System.out.println("Enter Account Num: ");
		int acc = scan.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scan.nextDouble();
		
		//accounts[acc].deposit(amount);
		
		Account acnt = PersistAccount.getAccount(acc); 
		acnt.deposit(amount);
		PersistAccount.persistAccount(acnt);
		
		System.out.println("--------------------");
	}
	
	static void withdraw(){
		System.out.println("Enter Account Num: ");
		int acc = scan.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scan.nextDouble();
		
		//accounts[acc].withdraw(amount);
		
		Account acnt = PersistAccount.getAccount(acc); 
		acnt.withdraw(amount);
		PersistAccount.persistAccount(acnt);
		
		
		System.out.println("--------------------");
	}
	
	static void getBalance(){
		
		System.out.println("Enter Account Num: ");
		int acc = scan.nextInt();
		
		Account acnt = PersistAccount.getAccount(acc); 
		System.out.println("Balance: "+ acnt.getBalance());
		
//		System.out.println("Balance: "+ accounts[acc].getBalance());
		System.out.println("--------------------");
		
		
	}

}
