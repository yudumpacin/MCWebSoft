package Application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import Data.Edge;
import Data.Element;
import Data.IntFormat;
import Data.WebState;


public class CreateIntFormat {
	private String path;
	
private IntFormat intFormat;

	

	

	public  StringBuffer printStates(Vector<WebState> stateList){
    	StringBuffer states = new StringBuffer();
    	states.append("<webstates>");
    	states.append(System.getProperty("line.separator"));
    	for(int i = 0; i<stateList.size(); i++){
    		states.append("<webstate>");states.append(System.getProperty("line.separator"));
    		states.append("<name>");states.append(System.getProperty("line.separator"));
    		states.append(stateList.get(i).getName()); states.append(System.getProperty("line.separator"));
    		states.append("</name>"); states.append(System.getProperty("line.separator"));
    		states.append("<url>");states.append(System.getProperty("line.separator"));
    		states.append(stateList.get(i).getUrl()); states.append(System.getProperty("line.separator"));
    		states.append("</url>");states.append(System.getProperty("line.separator"));
    		states.append("</webstate>");states.append(System.getProperty("line.separator"));
    	}
    	states.append("</webstates>");states.append(System.getProperty("line.separator"));
    	return states;
    }
    
    public StringBuffer printElements(Vector<Element> elementList){
    	StringBuffer elements = new StringBuffer();
    	elements.append("<elements>");
    	elements.append(System.getProperty("line.separator"));
    	for(int i =0;i<elementList.size();i++){
    		elements.append("<element>");elements.append(System.getProperty("line.separator"));
    		elements.append("<xpath>");elements.append(System.getProperty("line.separator"));
    		elements.append(elementList.get(i).getXpath());elements.append(System.getProperty("line.separator"));
    		elements.append("</xpath>");elements.append(System.getProperty("line.separator"));
    		elements.append("</element>");elements.append(System.getProperty("line.separator"));	
    }
    	elements.append("</elements>"); elements.append(System.getProperty("line.separator"));
    	return elements;
 }
    

  
  public StringBuffer printEdges(Vector<Edge> edgeList){
	StringBuffer states = new StringBuffer();
  	states.append("<edges>");
  	states.append(System.getProperty("line.separator"));
  	for(int i = 0; i<edgeList.size(); i++){
  		states.append("<edge>");states.append(System.getProperty("line.separator"));
  		states.append("<from>");states.append(System.getProperty("line.separator"));
  		states.append(edgeList.get(i).getFrom().getName()); states.append(System.getProperty("line.separator"));
  		states.append("</from>"); states.append(System.getProperty("line.separator"));
  		states.append("<to>");states.append(System.getProperty("line.separator"));
  		states.append(edgeList.get(i).getTo().getName()); states.append(System.getProperty("line.separator"));
  		states.append("</to>");states.append(System.getProperty("line.separator"));
  		states.append("<xpathOfElement>");states.append(System.getProperty("line.separator"));
  		states.append(edgeList.get(i).getXpathh().getXpath()); states.append(System.getProperty("line.separator"));
  		states.append("</xpathOfElement>");states.append(System.getProperty("line.separator"));
  	    states.append("</edge>");states.append(System.getProperty("line.separator"));
  	}
  	states.append("</edges>");states.append(System.getProperty("line.separator"));
  	return states;
  
	  
  }
    
    
    
    public File createIntFormat(IntFormat crawltype ) throws IOException{
    	
            setPath("lib\\IntFormatv1.xml");
    	    File intFormatFile = new File(getPath().toString());
    	    BufferedWriter writer = new BufferedWriter(new FileWriter(intFormatFile));
			writer.write("<?xml version='1.0' encoding='UTF-8' ?>"); writer.newLine();
			writer.write(printStates(crawltype.getStateList()).toString()); writer.newLine();
			writer.write(printElements(crawltype.getElementList()).toString()); writer.newLine();
			writer.write(printEdges(crawltype.getEdgeList()).toString()); writer.newLine();
			writer.close();
		
    	return intFormatFile;
    	
 }
    public File createIntFormat(Vector<WebState> stateList, Vector<Element> elementList, Vector<Edge> edgeList ) throws IOException{
        setPath("lib\\IntFormatv1.xml");
	    File intFormatFile = new File(getPath().toString());
	    BufferedWriter writer = new BufferedWriter(new FileWriter(intFormatFile));
		writer.write("<?xml version='1.0' encoding='UTF-8' ?>"); writer.newLine();
		writer.write(printStates(stateList).toString()); writer.newLine();
		writer.write(printElements(elementList).toString()); writer.newLine();
		writer.write(printEdges(edgeList).toString()); writer.newLine();

		writer.close();
	
	return intFormatFile;
   
    }
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public IntFormat getIntFormat() {
		return intFormat;
	}

	public void setIntFormat(IntFormat intFormat) {
		this.intFormat = intFormat;
	}

}
