package storage.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import txs.TxType;
import account.db.Account_DB;
import account.file.Account;


public class DBStorage {
	
	Connection conn = null; 
	
/*	public static void main(String[] args) {
		DBStorage dbs = new DBStorage();
		
		dbs.prepareDB();
		
		Account_DB accnt = new Account_DB("phani", 1500.0,"java");
		dbs.storeAccount(accnt);
		
		Account_DB accnt = dbs.getAccount(1);
		accnt.deposit(100.0);		
		dbs.updateAccount(accnt);
		
		System.out.println("Acnt No: "+ dbs.validateUser("phani", "java"));
		
		
		dbs.closeDB();
	}*/
	
	
	public boolean prepareDB(){
		boolean bRes;
		
		bRes=connect();
		if(bRes){
			bRes=createTables();		
			if(!bRes)			
				System.out.println("Unable to create Tables");
		}
		else
			System.out.println("Unable to Connect to DB");
			
		return bRes;
	}
	
	public boolean closeDB(){
		
		if(disconnect()){
			System.out.println("DB Conneciton Closed");
			return true;
		}else
			return false;
	}
	
	public int validateUser(String uname, String pwd){
		
		int acntNum =-1;
		PreparedStatement pStmt;
		
		try {
			String selectSQL = "select * from cred where NAME = ?";
			pStmt = conn.prepareStatement(selectSQL);
			pStmt.setString(1, uname);
			ResultSet rs = pStmt.executeQuery();

			rs.next();
			if(pwd.equals(rs.getString("PWD")))
				acntNum= rs.getInt("ACCNT_ID");
			else
				acntNum= -1;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return acntNum;
	}
	
	public Account_DB getAccount(int acntNum){
		
		PreparedStatement pStmt;
		Account_DB account=null;
		try {
			String selectSQL = "select * from accounts where ACCNT_ID = ?";
			pStmt = conn.prepareStatement(selectSQL);
			pStmt.setInt(1, acntNum);  
			ResultSet rs = pStmt.executeQuery();

			if(rs.next()){			
				account = new Account_DB(rs.getString("NAME"), rs.getDouble("BALANCE"));
				account.setAcntNum(acntNum);
			}			
			else
				account = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
		
	}
		
	
	
	public boolean updateAccount(Account_DB accnt){
		PreparedStatement pStmt;
		try {
			pStmt = conn.prepareStatement("update  accounts set BALANCE=? where ACCNT_ID=?");
			pStmt.setDouble(1, accnt.getBalance());
			pStmt.setInt(2, accnt.getAcntNum());	
			
			pStmt.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public boolean storeAccount(Account_DB accnt){
		PreparedStatement pStmt;
		try {
			pStmt = conn.prepareStatement("insert into accounts (NAME,BALANCE,MIN_BALANCE)  values(?,?,?)");
			
			pStmt.setString(1, accnt.getName());
			pStmt.setDouble(2, accnt.getBalance());
			pStmt.setDouble(3, accnt.getMinBalance());			
			
			pStmt.executeUpdate();
			
			
			pStmt = conn.prepareStatement("insert into cred (NAME,PWD,ACCNT_ID) values(?,?,LAST_INSERT_ID())");
						
			pStmt.setString(1, accnt.getName());
			pStmt.setString(2, accnt.getPwd());
			
			pStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		return true;
	}
	
	
	public boolean storeTransaction(int acntNum, double amnt, TxType type){
		
		PreparedStatement pStmt;

		try {
			pStmt = conn.prepareStatement("insert into txs (TX_TYPE, AMOUNT,ACCNT_ID) values(?,?,?)");

			
			pStmt.setInt(1,type.ordinal());
			pStmt.setDouble(2, amnt);	
			pStmt.setInt(3, acntNum);			
			
			pStmt.executeUpdate();			
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		return true;
	}
	
	private boolean connect(){
		DBCredentials jDemo = new DBCredentials();

		/* Connect to MySQL server */
		conn = null;

		Properties connectionProps = new Properties();
		connectionProps.put("user", jDemo.userName);
		connectionProps.put("password", jDemo.password);

		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+ jDemo.serverName + ":" + jDemo.portNumber + "/"	+ jDemo.dbName, connectionProps);
						
		} catch (SQLException e) {
			 e.printStackTrace();
		}

		if (conn != null) {
			System.out.println("Connection to Database Established");
			return true;
		} else {
			System.out.println("Unable to connect to Database");
			return false;
		}		
	}
	
	public boolean disconnect(){
		if(conn !=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();				
				return false;
			}
		}
		return true;
	}
	
	
	private boolean createTables(){
		
		boolean res = false;
		
		String accounts_tb_creation = "CREATE TABLE " + "accounts" + " ( "
				+ "ACCNT_ID INTEGER NOT NULL AUTO_INCREMENT," 
				+ "NAME varchar(20) NOT NULL,"			
				+ "BALANCE DOUBLE NOT NULL,"
				+ "MIN_BALANCE DOUBLE NOT NULL,"
				+ "PRIMARY KEY (ACCNT_ID))";
		
		String credentials_tb_creation = "CREATE TABLE " + "cred" + " ( "
				+ "NAME varchar(20) NOT NULL, "
				+ "PWD varchar(20) NOT NULL, "
				+ "ACCNT_ID INTEGER NOT NULL," 
				+ "PRIMARY KEY (NAME),"
				+ "FOREIGN KEY (ACCNT_ID) REFERENCES accounts(ACCNT_ID))";
		
		
		String txs_tb_creation = "CREATE TABLE " + "txs" + " ( "
				+ "TX_ID INTEGER NOT NULL AUTO_INCREMENT, " 			
				+ "TX_TYPE INTEGER NOT NULL,"
				+ "TX_DATE DATETIME,"
				+ "AMOUNT DOUBLE,"
				+ "ACCNT_ID INTEGER NOT NULL,"
				+ "PRIMARY KEY (TX_ID))";
		
		Statement stmt;
		int result;
		try {
			stmt = conn.createStatement();
						
			result = stmt.executeUpdate(accounts_tb_creation);
			if(result ==0)
				result=stmt.executeUpdate(credentials_tb_creation);	
			
			if(result == 0)
				result=stmt.executeUpdate(txs_tb_creation);
			
			if(result ==0){
				res= true;
				System.out.println("All tables created Successfully");				
			}else{
				System.out.println("Unable to create tables");
				res=false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			res=false;
		}		
		
		return res;
	}

}
