package storage.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import account.file.Account;

public class PersistAccount {
	
	static String  accNumFile = "acc_num";
	static String  accntsFile = "accnts_file";
	
	public static void persistAccNum(int num) {
		
		FileOutputStream out;
		try {
			out = new FileOutputStream(accNumFile);
			out.write(num);			
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static int getAccNum() {
		
		int accNum=0;
		
		FileInputStream in=null;
		try {
			
			File file = new File(accNumFile);
			if(file.exists())
			{
				in = new FileInputStream(accNumFile);
				accNum=in.read();
				in.close();
			}else
				accNum =0;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e){
			//
		}finally{
			//
		}
		
		return accNum;
	}
	
	
	public static void persistAccount(Account a){
		
		String fileName;
		int accNum;
		try {
			
			accNum = a.getAccountNum();
			fileName = accntsFile + accNum;		
			
			
			FileOutputStream out = new FileOutputStream(fileName);		
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			/*Serialization / Flattening */		
			
			objOut.writeObject(a);
			objOut.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Account getAccount(int accNum){
		
		String fileName = accntsFile + accNum;
		Account acnt =null;
		
		try {
		FileInputStream in = new FileInputStream(fileName);
		ObjectInputStream objIn = new ObjectInputStream(in);
		
		/*De-Serialization / Un-Flattening*/
		acnt = (Account)objIn.readObject();		
		objIn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return acnt;
		
	}
	
	

}
