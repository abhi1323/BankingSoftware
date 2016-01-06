package account.db;

public class SalaryAccount_DB  extends Account_DB {

	
	public SalaryAccount_DB(String name, Double amount,String pwd) {
		super(name,amount,pwd);		
		minBalance=0.0;
	}
	
	
}
