package application;

import storage.file.PersistAccount;

public class StorageApp {

	public static void main(String[] args) throws Exception{
		PersistAccount.persistAccNum(0);		
		System.out.println(PersistAccount.getAccNum());
	}

}
