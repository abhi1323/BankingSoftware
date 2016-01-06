package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import storage.db.DBStorage;
import txs.db.Transaction_DB;
import account.db.Account_DB;
import account.db.SalaryAccount_DB;

public class BankingApp_db {

	static Scanner scan;
	static Transaction_DB txs;
	static DBStorage dbs;

	public static void main(String[] args) {

		scan= new Scanner(System.in);
		int option;
		dbs = new DBStorage();
		txs = new Transaction_DB(dbs);

		dbs.prepareDB();

		do{
			displayMainOptions();

			option=scan.nextInt();
			switch(option){
			case 1:
				createAccount();
				break;
			case 2:
				int accntNum = login();
				if(accntNum!=-1){
					do{
						displayAccountOptions();
						option=scan.nextInt();
						switch(option){
						case 1:
							getBalance(accntNum);
							break;
						case 2:
							deposit(accntNum);
							break;
						case 3:
							withdraw(accntNum);
							break;
						case 4:
							System.out.println("Logged Out");
							break;	
						default:
							System.out.println("Invalid Request");
							break;
						}

					}while(option!=4);
				}
				break;
			case 3:
				System.out.println("Exiting The Application");
				dbs.closeDB();
				System.exit(0);
				break;

			default:
				System.out.println("Invalid Request");
				break;
			}

		}while(true);

	}

	static void displayMainOptions(){
		System.out.println("----------------------");
		System.out.println(" 1. Create Account");
		System.out.println(" 2. Login into Account");
		System.out.println(" 3. Exit Application");		
		System.out.println("Choose Operation:");
	}

	static void displayAccountOptions(){
		displayAccountInfo();
		System.out.println("----------------------");
		System.out.println(" 1. Get Balance");
		System.out.println(" 2. Deposit");
		System.out.println(" 3. WithDraw");
		System.out.println(" 4. Logout");
		System.out.println("Choose Operation:");
	}

	static void displayAccountInfo(){

	}

	static void  createAccount(){

		System.out.println("Choose Account Type:");
		System.out.println(" 1. Simple Account");
		System.out.println(" 2. Salary Account");


		int acType = scan.nextInt();
		System.out.print("Enter Name: ");
		String name = scan.next();

		System.out.print("Enter Opening Balance: ");
		Double balance = scan.nextDouble();

		System.out.print("Enter Pwd: ");
		String pwd = scan.next();

		System.out.println("Creating Account .......");

		if(acType ==1){
			Account_DB acc = new Account_DB(name,balance,pwd);	
			if(dbs.storeAccount(acc))
				System.out.println("New Accout Created Successfylly. ");
			else
				System.out.println("Account creation failed");
		}else{
			SalaryAccount_DB sAcc = new SalaryAccount_DB(name, balance,pwd);
			if(dbs.storeAccount(sAcc))
				System.out.println("New Accout Created Successfylly. ");
			else
				System.out.println("Account creation failed");
		}

		System.out.println("------------------");
	}

	static void deposit(int acntNum){

		System.out.println("Enter Amount: ");		
		double amount = scan.nextDouble();
		
		txs.deposit(acntNum, amount);
		System.out.println("--------------------");
	}

	static void withdraw(int acntNum){

		System.out.println("Enter Amount: ");
		double amount = scan.nextDouble();
		
		txs.withdraw(acntNum, amount);
		System.out.println("--------------------");
	}

	static void getBalance(int acntNum){

		txs.getBalance(acntNum);
		System.out.println("--------------------");


	}

	static int login() {

		String uname;
		String pwd;
		int acntNum=-1;
		int loginAttempt =0;

		InputStreamReader reader=new InputStreamReader(System.in);  
		BufferedReader br=new BufferedReader(reader); 		

		do{
			loginAttempt ++;

			try{

				System.out.print("Enter User Name: ");
				uname = br.readLine();				

				System.out.print("Enter Pwd:");
				pwd = br.readLine();
				
				acntNum=dbs.validateUser(uname, pwd);

				if(acntNum == -1){
					System.out.println("Invalid Credentials...Retry");
				}else{
					System.out.println("Login Successful");					
					break;
				}
			}catch(Exception e){
				e.printStackTrace();

				System.out.println("Exception");
			}
		}while(loginAttempt < 5);

		if(loginAttempt == 5){
			System.out.println("Exceed Allowed Login Attempts ..Retry Later");
		}
		return acntNum;
	}


}
