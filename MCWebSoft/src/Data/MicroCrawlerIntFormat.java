package Data;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
//import org.w3c.dom.Element;




public class MicroCrawlerIntFormat implements IntFormat {


static ArrayList<String> linesOfJson = new ArrayList<String>();
private Vector<Element> elementList = new Vector<Element>();
private Vector<WebState> stateList = new Vector<WebState>();
private Vector<Edge> edgeList = new Vector<Edge>();

public Vector<WebState> getStateList() {
	return stateList;
}


public void setStateList(Vector<WebState> stateList) {
	this.stateList = stateList;
}

public Vector<Element> getElementList() {
	return elementList;
}


public void setElementList(Vector<Element> elementList) {
	this.elementList = elementList;
}


public Vector<Edge> getEdgeList() {
	return edgeList;
}


public void setEdgeList(Vector<Edge> edgeList) {
	this.edgeList = edgeList;
}


public MicroCrawlerIntFormat(String fileName){
	
	ReadCrawlOutput(fileName);
	initialize_Edges();
    initialize_States(); 
    initialize_Elements();	
}



	
	public void ReadCrawlOutput(String file){
		
		 BufferedReader br = null;
		 
			try {
	 
				String sCurrentLine;
	 
				br = new BufferedReader(new FileReader(file));
				while ((sCurrentLine = br.readLine()) != null) {
					linesOfJson.add(sCurrentLine);	
				}}catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (br != null)br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}}
		}
  
     public void initialize_Edges(){
    	 
    	 for(int i=0;i<linesOfJson.size();i++){
    		 if(linesOfJson.get(i).contains("->") & linesOfJson.get(i).contains("HTML")){
    			
    			 Edge edge = new Edge();
    			 WebState stateFrom = new WebState();
    			 WebState stateTo = new WebState();
    			 Element xpathh = new Element();
    			 
    			
    			 if(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("->")+3, linesOfJson.get(i).indexOf("[")).toString().trim().equals("1"))
    				 stateTo.setName("index");
    			 else
    			 stateTo.setName(("state"+linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("->")+3, linesOfJson.get(i).indexOf("[")).toString()));
    			 
    			 
    			 if(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("HTML"), linesOfJson.get(i).indexOf("\"];")).toString().endsWith("]"))
    			 {
    			 xpathh.setXpath(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("HTML"), linesOfJson.get(i).indexOf("\"];")).toString());}
    			 else if(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("HTML"), linesOfJson.get(i).indexOf("\"];")).toString().contains(","))
    			 {
    			 xpathh.setXpath(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("HTML"), linesOfJson.get(i).indexOf("]\",")+1).toString());
    			 }
    			 
    			 if(linesOfJson.get(i).subSequence(2, linesOfJson.get(i).indexOf("->")).toString().trim().equals("1"))
    				 stateFrom.setName("index");
    				 else
    			 stateFrom.setName("state"+linesOfJson.get(i).subSequence(2, linesOfJson.get(i).indexOf("->")).toString());
    			
    			 edge.setXpathh(xpathh);
    			 edge.setFrom(stateFrom);
    			 edge.setTo(stateTo);
    			 edgeList.add(edge);
    		 }
    	 }
    	
     }
     
     public void initialize_States(){
    	 
    	 for(int i =0 ;i<linesOfJson.size();i++){
    		 if(linesOfJson.get(i).contains("shape=circle")){
    			 if(linesOfJson.get(i).subSequence(2, linesOfJson.get(i).indexOf("[")).toString().equals("1")){
    				 
    				 WebState webState = new WebState();
    				 webState.setName("index");
        			 webState.setUrl(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("URL=\"")+5,linesOfJson.get(i).indexOf("\"]") ).toString());
        			 stateList.add(webState); 
    			 }else{
    			 WebState webState1 = new WebState();
    			 webState1.setName("state"+linesOfJson.get(i).subSequence(2, linesOfJson.get(i).indexOf("[")).toString()+"");
    			 webState1.setUrl(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("URL=\"")+5,linesOfJson.get(i).indexOf("\"]") ).toString());
    			 stateList.add(webState1);
    			 }
    			 
    			 
    		 }
    		
    		 
    	 }
    	 
     }

     public  void initialize_Elements(){
    	
    	 for(int i=0;i<linesOfJson.size();i++){
    		 if(linesOfJson.get(i).contains("->") & linesOfJson.get(i).contains("HTML")){
    	
    			
    			 
    			
    			 
    			 if(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("HTML"), linesOfJson.get(i).indexOf("\"];")).toString().endsWith("]"))
    			 {
    				Element element = new Element();
    			 element.setXpath(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("HTML"), linesOfJson.get(i).indexOf("\"];")).toString());
    			 
    			 if(elementList.contains(element)){
    					continue;
    					
    					
    				}
    			 
    			 elementList.add(element);}
    			 else if(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("HTML"), linesOfJson.get(i).indexOf("\"];")).toString().contains(","))
    			 {
    				Element element = new Element();
    				 element.setXpath(linesOfJson.get(i).subSequence(linesOfJson.get(i).indexOf("HTML"), linesOfJson.get(i).indexOf("]\",")+1).toString());
    				 
    				 if(elementList.contains(element)){
     					continue;
     					
     					
     				}
    				 elementList.add(element);
    			 }
    			
    			
    			
    			 
    		 }
    		
    	 }
    	int n = elementList.size();
    	for(int i=0;i<n;i++){
    		for(int j=0;j<n;j++){
    			if(i==j) continue;
    			if(i!=j){
    				
    				if(elementList.get(i).getXpath().equals(elementList.get(j).getXpath())){
    					elementList.remove(elementList.elementAt(j));
    					n--;
    					if(i==elementList.size() || j==elementList.size()) break;
    				}
    		//	}
    			}
    		}
    		
    	}
    	int m = elementList.size();
    	for(int i=0;i<m;i++){
    		for(int j=0;j<m;j++){
    			if(i==j) continue;
    			if(i!=j){
    				
    				if(elementList.get(i).getXpath().equals(elementList.get(j).getXpath())){
    					elementList.remove(elementList.elementAt(j));
    					m--;
    					if(i==elementList.size() || j==elementList.size()) break;
    				}
    		
    			}
    		}
    		
    	}
    	
    	 
     }

    
}
    




	
		





