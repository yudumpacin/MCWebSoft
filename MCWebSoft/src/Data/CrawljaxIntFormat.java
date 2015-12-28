package Data;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Vector;
import java.util.Map.Entry;
import java.util.Set;


//import org.w3c.dom.Element;


import com.google.gson.*;
import com.google.gson.stream.JsonReader;






public class CrawljaxIntFormat implements IntFormat {


static ArrayList<String> linesOfJson = new ArrayList<String>();
private Vector<Element> elementList = new Vector<Element>();
private Vector<WebState> stateList = new Vector<WebState>();
private Vector<Edge> edgeList = new Vector<Edge>();


public Vector<Element> getElementList() {
	return elementList;
}

public void setElementList(Vector<Element> elementList) {
	this.elementList = elementList;
}

public Vector<WebState> getStateList() {
	return stateList;
}

public void setStateList(Vector<WebState> stateList) {
	this.stateList = stateList;
}

public Vector<Edge> getEdgeList() {
	return edgeList;
}

public void setEdgeList(Vector<Edge> edgeList) {
	this.edgeList = edgeList;
}





public CrawljaxIntFormat(String fileName) throws IOException{
	 ReadCrawlOutput(fileName);
	 initialize_States();
     initialize_Elements();
     initialize_Edges();
	
}

	public void ReadCrawlOutput(String fileName) throws IOException{
		BufferedReader reader=null;
        StringBuilder content=null;
        String result=null;

            FileReader read = null;
			try {
		    read = new FileReader(fileName);
            reader = new BufferedReader(new FileReader(fileName));
            
            String line = null;
            content= new StringBuilder();

            while ((line = reader.readLine()) != null) {
            content.append(line);
            }
            reader.close();} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
            result= content.toString();
            JsonReader rd = new JsonReader(read);
            JsonElement jelement = new JsonParser().parse(rd);
			
           printJsonRecursive(jelement); 
          
           
          
	}
	
    public  void printJsonRecursive(JsonElement jelement){

        if(jelement.isJsonPrimitive()){
        	
            linesOfJson.add(jelement.getAsString());
            return;
        }
        if(jelement.isJsonArray()){

            JsonArray jarray= jelement.getAsJsonArray();
            for(int i=0;i<jarray.size();i++){
                JsonElement element= jarray.get(i);
                printJsonRecursive(element);
            }
            return;

        }
        JsonObject  jobject= jelement.getAsJsonObject();

        Set<Entry<String, JsonElement>> set= jobject.entrySet();

        for (Entry<String, JsonElement> s : set) {

        	linesOfJson.add(s.getKey());
            printJsonRecursive(s.getValue());
            
            

        }

    }
    
   
    
   
    
    
    public  void initialize_States(){
  
    	int startPoint = linesOfJson.indexOf("states");
    	int endPoint = linesOfJson.indexOf("edges");

    	for(int i = startPoint ; i < endPoint ;i++){
    		if(linesOfJson.get(i).contains("name") & (linesOfJson.get(i+1).contains("state") | linesOfJson.get(i+1).contains("index") )){
    			WebState webState = new WebState();
    		    webState.setName(linesOfJson.get(i+1));
    		    webState.setUrl(linesOfJson.get(i+3));
    		    if(webState.getName().equals("index")){
    		    	stateList.add(0,webState);
    		    }
    		    else  stateList.add(webState);}
    	}
    		
    		
    	}
    
    public  void initialize_Edges(){
    	int startPoint = linesOfJson.indexOf("edges");
    	int endPoint = linesOfJson.indexOf("statistics");
    	
    	for(int i =startPoint ;i<endPoint;i++){
    		if(linesOfJson.get(i).contains("from") & linesOfJson.get(i).toString().length()==4){
    			WebState stateFrom = new WebState();
    			stateFrom.setName(linesOfJson.get(i+1));
    		
    			WebState stateTo = new WebState();
    			stateTo.setName(linesOfJson.get(i+3));
    			
    		
    		
    			Element element = new Element();
    			
    			element.setXpath(linesOfJson.get(i+7).substring(6));
    			
    			Edge edge = new Edge();
    			edge.setFrom(stateFrom);
    			edge.setTo(stateTo);
    			edge.setXpathh(element);
    			edgeList.add(edge);
    		
    		}
    	}
    	
    }
    
    public  void initialize_Elements(){
    	
    	
    	int startPoint = linesOfJson.indexOf("states");
    	int endPoint = linesOfJson.indexOf("edges");
    	for(int i=endPoint;i<linesOfJson.size();i++){
    		if(linesOfJson.get(i).contains("xpath")){
    		Element element3 = new Element();
    	     if(linesOfJson.get(i).substring(6).toString().contains("/HTML")){
			element3.setXpath(linesOfJson.get(i).substring(6).toString());
			if(elementList.contains(element3)){
				continue;
				
				
			}
			
			elementList.add(element3);
			
    		}}
    	}
    		}
    	
    	
    	
    	
  
    	}
    	
   
    	
    	
    	
    	
	
    




	
		


