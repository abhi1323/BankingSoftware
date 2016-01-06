package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import account.file.Account;
import account.file.SalaryAccount;
import storage.file.PersistAccount;
import txs.file.Transaction;

public class BankingApp_file {

	static Scanner scan;
	static ArrayList<Account> accntList = new ArrayList<Account>();
	static Transaction txs;
	static HashMap<Integer, String> credentials = new HashMap<Integer, String>();	

	public static void main(String[] args) {

		scan= new Scanner(System.in);
		int option;
		txs = new Transaction();

		populateAcnts();

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
				persistAccounts();
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
			Account acc = new Account(name,balance,pwd);		
			accntList.add(acc);
			credentials.put(acc.getAccountNum(), acc.getPwd());

			System.out.println("New Accout Created with AccNum:  " + acc.getAccountNum() );
		}else{
			SalaryAccount sAcc = new SalaryAccount(name, balance,pwd);
			accntList.add(sAcc);
			credentials.put(sAcc.getAccountNum(), sAcc.getPwd());

			System.out.println("New Accout Created with AccNum:  " + sAcc.getAccountNum() );
		}

		System.out.println("------------------");
	}

	static void deposit(int acntNum){
		
		System.out.println("Enter Amount: ");
		double amount = scan.nextDouble();

		//accntList.get(acc-1).deposit(amount);
		txs.deposit(accntList.get(acntNum-1), amount);

		System.out.println("--------------------");
	}

	static void withdraw(int acntNum){
	
		System.out.println("Enter Amount: ");
		double amount = scan.nextDouble();

		//accntList.get(acc-1).withdraw(amount);
		txs.withdraw(accntList.get(acntNum-1), amount);

		System.out.println("--------------------");
	}

	static void getBalance(int acntNum){

		txs.getBalance(accntList.get(acntNum-1));
	//	System.out.println("Balance: "+ accntList.get(acntNum-1).getBalance());

		System.out.println("--------------------");


	}

	static void populateAcnts(){

		int numAcnts = PersistAccount.getAccNum();
		Account accnt;
		int acntNum;
		for (int i = 0; i < numAcnts; i++) {
			acntNum =i+1;
			
			accnt = PersistAccount.getAccount(acntNum);
			accntList.add(accnt);
			credentials.put(acntNum,accnt.getPwd());
			
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



	static int login() {

		String sAcntNum;
		int iAcntNum;
		String pwd;
		int acntNum=-1;
		int loginAttempt =0;

		InputStreamReader reader=new InputStreamReader(System.in);  
		BufferedReader br=new BufferedReader(reader); 		
		
		do{
			loginAttempt ++;

			try{
	
				System.out.print("Enter User Name: ");
				sAcntNum = br.readLine();
				
				iAcntNum = Integer.parseInt(sAcntNum);
	
				System.out.print("Enter Pwd:");
				pwd = br.readLine();
				
				
				if(credentials.get(iAcntNum)!=null){
					if(!pwd.equals(credentials.get(iAcntNum))){
						System.out.println("Invalid Credentials...Retry");
					}else{
						System.out.println("Login Successful");
						acntNum = iAcntNum;
						break;
					}
				}else	
				{
					System.out.println("Invalid Credentials...Retry");
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
