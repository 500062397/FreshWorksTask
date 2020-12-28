package freshWorks;

import java.io.File;
import java.util.*;
import freshWorks.createRecord;
import freshWorks.ReadRecord;
import freshWorks.DeleteRecord;

class Threads implements Runnable {

	public void run() {
		try {
			this.startProcess();
			// Let the thread sleep for a while.
			Thread.sleep(50);

		} catch (Exception e) {
			System.out.println("Exception is caught");
		}
	}

	public void startProcess() {
		String isExists=checkForFile();
		//System.out.println("isExists::"+isExists);
		String key = "";
		String value = "";
		String validTillTime = "";
		int maxKeyLength = 32;
		int maxJsonSize = 16;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Operation you want to perform::");
		String op = sc.nextLine();

		if(validTillTime.equalsIgnoreCase(""))
		{
			validTillTime="0";
		}
		//System.out.println("Operation::" + op);
		//System.out.println("Key::" + key);
		//System.out.println("value::" + key);
		//System.out.println("validTill::" + validTillTime);
		
		 if(op.equalsIgnoreCase("CREATE")) 
		 {
			 System.out.println("Enter the key::");
			 key = sc.nextLine();
				int enteredKeyLength = key.length();

				if (enteredKeyLength > maxKeyLength) {
					System.out.println("Key length cannot exceed 32 chaeacters");
					return;
				}
			 System.out.println("Enter the value for the above entered key::");
			 value = sc.nextLine();
			 if (value.equalsIgnoreCase("")) {
					System.out.println("Value for a key cannot be empty");
					return;
				}
			 System.out.println("Enter the time to live for this record::");
			 validTillTime = sc.nextLine();
			 createRecord cr=new createRecord();
			 String resCreate=cr.createRecordDS(key, value, validTillTime, isExists);
			 if(resCreate.equalsIgnoreCase("Success"))
			 {
				 System.out.println("Record successfully created in data store.");
			 }
			 
			 else if(resCreate.equalsIgnoreCase("Fail"))
			 {
				 System.out.println("Record could not be created, please try again.");
			 }
			 else if(resCreate.equalsIgnoreCase("exists"))
			 {
				 System.out.println("The data store already has data associated with this key, please enter a unique key.");
			 }
			 else if(resCreate.equalsIgnoreCase("maxLength breach"))
			 {
				 System.out.println("Record could not be created as the value exceeds the maximum permitted size(16kb), please try again.");
			 }
			 else if(resCreate.equalsIgnoreCase("maxFileSize breach"))
			 {
				 System.out.println("Record could not be created as the file exceeds the maximum permitted size(1GB), please try again.");
			 }
			 
		 }
		 
		 else if(op.equalsIgnoreCase("READ"))
		 {
			 System.out.println("Enter the key::");
			 key = sc.nextLine();
			 String resRead="";
			 ReadRecord rr=new ReadRecord();
			 
			 if(isExists.equalsIgnoreCase("Does not Exist"))
				 System.out.println("No record exists");
			 else
			 {
				 resRead=rr.readRecordDS(key, value, validTillTime, isExists);
				 if(resRead.equalsIgnoreCase("deleted"))
				 {
					 System.out.println("The key you entered no longer exists in the data store as it has exceeded its time to live");
				 }
				 else if(resRead.equalsIgnoreCase("key not found"))
				 {
					 System.out.println("The key you entered does not exist in the data store");
				 }
				 else if(resRead.equalsIgnoreCase("fail"))
				 {
					 System.out.println("Record could not be read, please try again.");
				 }
				 else
				 {
					 System.out.println("Record read successfully, the value corresponding to key: "+key+" in the data store is: "+resRead); 
				 }
			 }
		 }
		 
		 else if(op.equalsIgnoreCase("DELETE")) 
		 {
			 System.out.println("Enter the key::");
			 key = sc.nextLine();
			 String resDelete="";
			 DeleteRecord dr=new DeleteRecord();
			 if(isExists.equalsIgnoreCase("Does not Exist"))
				 System.out.println("No record exists");
			 else
			 {
				 resDelete=dr.deleteRecordDS(key, value, validTillTime, isExists);
				 //String resDelete=dr.deleteRecordDS(key, value, validTillTime, isExists);
				 if(resDelete.equalsIgnoreCase("Success"))
				 {
					 System.out.println("Record successfully deleted from the data store.");
				 }
				 
				 else if(resDelete.equalsIgnoreCase("Fail"))
				 {
					 System.out.println("Record could not be deleted, please try again");
				 }
				 
				 else if(resDelete.equalsIgnoreCase("deleted"))
				 {
					 System.out.println("The key you entered no longer exists in the data store as it has exceeded its time to live");
				 }
				 else if(resDelete.equalsIgnoreCase("key not found"))
				 {
					 System.out.println("The key you entered does not exist in the data store");
				 }
			}
		 }
		 else
		 {
			 System.out.println("Please enter a valid operation(Create, Read or Delete)");
		 }
	}
	
public String checkForFile()
{
    File f = new File("C:/Users/User/Desktop/dataStore.json"); 
    
    // Check if the specified file 
    // Exists or not 
    if (f.exists()) 
        return "Exists"; 
    else
        return "Does not Exist";
}
}

public class dataSource {
	public static void main(String[] args) {
		int n = 2; // Number of threads
		//for (int i = 0; i < n; i++) {
			Thread t1 = new Thread(new Threads());
			t1.start();
			//Thread t2 = new Thread(new Threads());
			//t2.start();
		//}
	}
}
