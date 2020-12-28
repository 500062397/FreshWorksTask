package freshWorks;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class createRecord {
	
	public String createRecordDS(String key, String value, String validTillTime, String isExists) {
		String status="fail";
		int maxObjLen=16000;
		double fileSize=1024;
		int size=1000000000;
		if(isExists.equalsIgnoreCase("EXISTS"))
		{
			double actualFileSize=getFileSize();
			if(Double.compare(fileSize,actualFileSize) < 0)
			{
				return "maxFileSize breach";
			}
			try
			{
				//FileReader reader = new FileReader("C:/Users/User/Desktop/json_array_output.json");
				//String actual = Files.readString("C:/Users/User/Desktop/json_array_output.json");
				String content = new String ( Files.readAllBytes(Paths.get("C:/Users/User/Desktop/dataStore.json")));
				JSONObject requestBody = new JSONObject(content); 
				//System.out.println(requestBody.toString());
				//String resp = requestBody.getString("akhil");
				if(requestBody.has(key))
				{
					//System.out.println("The data store already has data associated with this key, please enter a unique key.");
					return "exists";
				}
				
				else
				{
					JSONObject mainObj = new JSONObject();
					JSONObject obj = new JSONObject();
					String createdDateTime=getDateTime();
					try
					{
						obj.put("value", value);
						obj.put("Time to live",validTillTime);
						obj.put("createdDateTime",createdDateTime);
						int len=obj.toString().length();
						if(len>maxObjLen)
						{
							return "maxLength breach";
						}
						//System.out.println("length::"+len);
						//mainObj.put(key, obj);
						requestBody.put(key, obj);
						int requestBodyLen=requestBody.toString().length();
						//System.out.println("file length::"+requestBodyLen);
						if(requestBodyLen>size)
						{
							return "maxFileSize breach";
						}
						FileWriter file =new FileWriter("C:/Users/User/Desktop/dataStore.json");
						file.write(requestBody.toString()); file.close();
						status="success";
					}
					
					catch(Exception e)
					{
						System.out.println("Exception::"+e);
						status="fail";
					}
				}
			}
			
			catch(Exception e)
			{
				System.out.println("Exception::"+e);
				status="fail";
			}
		}
		
		else
		{	
			JSONObject mainObj = new JSONObject();
			JSONObject obj = new JSONObject();
			String createdDateTime=getDateTime();
			try
			{
			obj.put("value", value);
			obj.put("Time to live",validTillTime);
			obj.put("createdDateTime",createdDateTime);
			int len=obj.toString().length();
			if(len>maxObjLen)
			{
				return "maxLength breach";
			}
			mainObj.put(key, obj);
			  try
			  { 
				  FileWriter file = new FileWriter("C:/Users/User/Desktop/dataStore.json");
				  file.write(mainObj.toString()); 
				  file.close(); 
				  status="success";
			  } catch (IOException e)
			  { 
			 // TODO Auto-generated catch block e.printStackTrace();
				  System.out.println("Exception::"+e); 
				  status="fail";
			  }
			}
			
			catch(Exception e)
			{
				System.out.println("Exception::"+e);
				status="fail";
			}
		}
		return status;
	}
	public String getDateTime()
	{
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date(); 
		return(formatter.format(date));
	}
	
	public double getFileSize()
	{
		try
		{
		String fileName = "C:/Users/User/Desktop/dataStore.json";
		File file = new File(fileName);
		//System.out.println("fileSize::"+(double) file.length() / (1024 * 1024));
		return (double) file.length() / (1024 * 1024);
		}
		catch(Exception e)
		{
			return 0;
		}
	}
}
