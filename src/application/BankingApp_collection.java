package application;

import java.util.ArrayList;
import java.util.Scanner;

import storage.file.PersistAccount;
import account.file.Account;
import account.file.SalaryAccount;

public class BankingApp_collection {
	
	static Scanner scan;
	static ArrayList<Account> accntList = new ArrayList<Account>();
	
	public static void main(String[] args) {
				
		scan= new Scanner(System.in);
		int option;
		
		populateAcntList();
		
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
					persistAccounts();
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
			accntList.add(acc);
			
			System.out.println("New Accout Created with AccNum:  " + acc.getAccountNum() );
		}else{
			SalaryAccount sAcc = new SalaryAccount(name, balance);
			accntList.add(sAcc);
			
			System.out.println("New Accout Created with AccNum:  " + sAcc.getAccountNum() );
		}
		
		System.out.println("------------------");
	}
	
	static void deposit(){
		System.out.println("Enter Account Num: ");
		int acc = scan.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scan.nextDouble();

		accntList.get(acc-1).deposit(amount);
		
		System.out.println("--------------------");
	}
	
	static void withdraw(){
		System.out.println("Enter Account Num: ");
		int acc = scan.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scan.nextDouble();
		
		accntList.get(acc-1).withdraw(amount);
		
		
		System.out.println("--------------------");
	}
	
	static void getBalance(){
		
		System.out.println("Enter Account Num: ");
		int acc = scan.nextInt();
		
		System.out.println("Balance: "+ accntList.get(acc-1).getBalance());
		
		System.out.println("--------------------");
		
		
	}
	
	static void populateAcntList(){
		
		int numAcnts = PersistAccount.getAccNum();
		int acntNum;
		for (int i = 0; i < numAcnts; i++) {
			acntNum =i+1;
			accntList.add(PersistAccount.getAccount(acntNum)); 
		}		
		System.out.println(accntList);
		
	}
	
	static void persistAccounts(){
		
		System.out.println(" Persisiting Accounts...");
		int numAcnts = PersistAccount.getAccNum();
		for (int i =0 ; i < numAcnts; i++) {
			PersistAccount.persistAccount(accntList.get(i));
		}	
		
		System.out.println("Persist Successful");
		
	}

}
