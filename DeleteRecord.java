package freshWorks;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DeleteRecord {
	
	public String deleteRecordDS(String key, String value, String validTillTime, String isExists) {
		String status="fail";
		String retVal="fail";
		
		if(isExists.equalsIgnoreCase("EXISTS"))
		{
			try
			{
				//FileReader reader = new FileReader("C:/Users/User/Desktop/json_array_output.json");
				//String actual = Files.readString("C:/Users/User/Desktop/json_array_output.json");
				String content = new String ( Files.readAllBytes(Paths.get("C:/Users/User/Desktop/dataStore.json")));
				JSONObject requestBody = new JSONObject(content);
				String ttl="0";
				String cdt="";
				//System.out.println(requestBody.toString());
				
				if(requestBody.has(key))
				{
					ttl=requestBody.getJSONObject(key).getString("Time to live");
					//System.out.println("ttl::"+ttl);
					cdt=requestBody.getJSONObject(key).getString("createdDateTime");
					//System.out.println("cdt::"+cdt);
				}
				if(!ttl.equalsIgnoreCase("0"))
				{
					//System.out.println("ttl!=0");
					Date date=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(cdt);
					Calendar c = Calendar.getInstance();
			        c.setTime(date);
			        c.add(Calendar.SECOND, Integer.parseInt(ttl));
			        Date cdtPlusTtl = c.getTime();
			        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
			        formatter.format(cdtPlusTtl);
			        Date currDate = new Date();
			        formatter.format(currDate);
			       if(currDate.compareTo(cdtPlusTtl)>0)
			       {
			    	   requestBody.remove(key);
						  try
						  { 
							  FileWriter file = new FileWriter("C:/Users/User/Desktop/dataStore.json");
							  file.write(requestBody.toString()); 
							  file.close(); 
							  retVal="deleted";
						  } catch (IOException e)
						  { 
						 // TODO Auto-generated catch block e.printStackTrace();
							  System.out.println("Exception::"+e); 
							  retVal="fail";
						  }
			    	   //System.out.println("The key you're looking for no longer exists in the data store as it has exceeded its time to live");
			       }
			       else
			       {
			    	   if(requestBody.has(key))
						{
							//System.out.println("The data store already has data associated with this key, please enter a unique key.");
							//retVal=requestBody.getJSONObject(key).getString("value");
			    		   requestBody.remove(key);
							  try
							  { 
								  FileWriter file = new FileWriter("C:/Users/User/Desktop/dataStore.json");
								  file.write(requestBody.toString()); 
								  file.close(); 
								  retVal="success";
							  } catch (IOException e)
							  { 
							 // TODO Auto-generated catch block e.printStackTrace();
								  System.out.println("Exception::"+e); 
								  retVal="fail";
							  }

						}
						else
						{
							retVal="key not found";
						}
			       }
				}
				else
				{
					if(requestBody.has(key))
					{
						//System.out.println("The data store already has data associated with this key, please enter a unique key.");
						retVal=requestBody.getJSONObject(key).getString("value");
					}
					else
					{
						retVal="key not found";
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("Exception::"+e);
				retVal="fail";
			}
		}
		return retVal;
	}
	/*public String writeToFile(String requestBody)
	{
		String status="";
		  try
		  { 
			  FileWriter file = new FileWriter("C:/Users/User/Desktop/json_array_output.json");
			  file.write(requestBody.toString()); 
			  file.close(); 
			  status="success";
		  } catch (IOException e)
		  { 
		 // TODO Auto-generated catch block e.printStackTrace();
			  System.out.println("Exception::"+e); 
			  status="fail";
		  }
		  return status;
	}*/
}
