package Utilities;
import jade.core.AID;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class MyAID {
	private String name;
	private String addr;
	
	//Introducing the dummy constructor
    public MyAID() {
    }
    
	public MyAID(String requestor_name, String requestor_addr) {
		this.name = requestor_name;
		this.addr = requestor_addr;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String toJSON() {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		String res = null;
		try {
			mapper.writeValue(sw, this);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res = sw.toString();
		return res;
	}
	
	public static MyAID fromJSON(String json) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, MyAID.class);
	}
	
	public static AID toAID(String json) {
		MyAID my_aid = null;
		try {
			my_aid = fromJSON(json);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AID aid = new AID(my_aid.getName(),AID.ISGUID);
		aid.addAddresses(my_aid.getAddr());
		return aid;
	}
}
