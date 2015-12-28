package Application;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import Data.Edge;
import Data.Element;
import Data.WebState;


		
public class IntFormatToNuSMV {

	NuSMVExecuter nusmv;
	
	BufferedWriter output;
	CreateIntFormat intFormat = new CreateIntFormat();

	private File smvInput;
    

	public File getSmvInput(Boolean crawlType) {
		return this.createSMV(nusmv.getsmvInputFileName(),crawlType);
	}


	public void setSmvInput(File smvInput, Boolean crawlType) {
		this.smvInput = this.createSMV(nusmv.getsmvInputFileName(), crawlType);
	}
	
	public File getOutputFile(){
		return this.smvInput;
		
	}
	public void setOutputFile (BufferedWriter output){
		this.output = output;
		
		
	}


	
	
	static ArrayList<String> linesOfXML =null;
	
	public IntFormatToNuSMV() throws IOException{

		this.ReadFile();
		this.initialize_state();
		this.initialize_element();
		this.initialize_edge();

		
		}

			public void ReadFile(){
				
				String file = "lib\\IntFormatv1.xml";
				linesOfXML=new ArrayList<>();
				 BufferedReader br = null;
				 
					try {
			 
						String sCurrentLine;
			 
						br = new BufferedReader(new FileReader(file));
						while ((sCurrentLine = br.readLine()) != null) {
							linesOfXML.add(sCurrentLine);	
						}}catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								if (br != null)br.close();
								
							} catch (IOException ex) {
								ex.printStackTrace();
							}}
				}
			
		
		
			public Vector<WebState> initialize_state(){		
				
				Vector<WebState> stateList = new Vector<WebState>();
				
				int offSet=linesOfXML.indexOf("<webstates>");
				int occurrences = Collections.frequency(linesOfXML, "<webstate>");
				for(int i=0 ; i< occurrences ; i++){
			      WebState webState=new WebState();
			      
			      webState.setName(linesOfXML.get(offSet+i*8+3).toString());
			      webState.setUrl(linesOfXML.get(offSet+i*8+6).toString());
			      stateList.add(webState);
				}
				
				return stateList;
							}
			
			public Vector<Edge> initialize_edge(){
				
				
				Vector<Edge> edgeList = new Vector<Edge>();
				
				int offSet = linesOfXML.indexOf("<edges>");
				int occurrences = Collections.frequency(linesOfXML, "<edge>");
				for(int i=0 ; i< occurrences ; i++){
					Edge edge = new Edge();
					WebState stateFrom = new WebState();
					WebState stateTo = new WebState();
					Element element = new Element();
					stateFrom.setName(linesOfXML.get(offSet+i*11+3).toString());
					stateTo.setName(linesOfXML.get(offSet+i*11+6).toString());
					element.setXpath(linesOfXML.get(offSet+11*i+9).toString());
			        edge.setFrom(stateFrom);
					edge.setTo(stateTo);
					edge.setXpathh(element);
					edgeList.add(edge);
					
				}
					return edgeList;
			}
			public Vector<Element> initialize_element(){		
				
				Vector<Element> elementList = new Vector<Element>();
				
				
				int occurrences = Collections.frequency(linesOfXML, "<element>");
				for(int i=0 ; i< occurrences ; i++){
				Element element = new Element();
				element.setXpath(linesOfXML.get(linesOfXML.indexOf("<xpath>")+1+5*i).toString());
				elementList.add(element);
				}
				
				return elementList;
				}				
	
        		
		
			public StringBuffer printStates(){
				Vector<WebState> stateList=initialize_state();
				StringBuffer states = new StringBuffer();
				states.append("webstate:{");
				for(int i=0;i<stateList.size();i++){
					states.append(stateList.elementAt(i).getName().toString());
					if(i!=stateList.size()-1)
						states.append(",");
					else
						states.append(",dead_end_webstate};");
				}

				return states;
				}
			
			public StringBuffer printElements(){
				Vector<Element> elementList=initialize_element();
				StringBuffer elements = new StringBuffer();
				elements.append("element:{");
				for(int i=0;i<elementList.size();i++){
					elements.append(elementList.elementAt(i).getXpath().toString().replace('[', '_').replace(']', '_').replace('/', '_'));
					if(i!=elementList.size()-1)
						elements.append(",");
					else
						elements.append(",null_element};");
				}
		
				return elements;
					
				}
			
				
			
			
			public StringBuffer printSMVStateCase() {
				Vector<Edge> edgeList=initialize_edge();
				
				StringBuffer stateCase = new StringBuffer();
				for(int i=0;i<edgeList.size();i++){
					
				stateCase.append("webstate="+edgeList.elementAt(i).getFrom().getName()+" & next(element)="+edgeList.elementAt(i).getXpathh().getXpath().replace('[', '_').replace(']', '_').replace('/', '_'));
			    stateCase.append(":"+edgeList.elementAt(i).getTo().getName()+";");
				stateCase.append(System.getProperty("line.separator"));}
					
				return stateCase;
			}
			
			public StringBuffer printSMVElementCase() {
				Vector<WebState> stateList=initialize_state();
				Vector<Edge> edgeList=initialize_edge();
				
				String manyElementCase="";
				int n = 0;
				StringBuffer elementCase = new StringBuffer();
				
				
				ArrayList<String> edgeListString = new ArrayList<String>();
				
				for(int i=0;i<edgeList.size();i++){
					
					edgeListString.add(edgeList.get(i).getFrom().getName().toString());
					edgeListString.add(edgeList.get(i).getXpathh().getXpath().toString());
				}
				
				
				int occurrences[] = new int[stateList.size()];
				int occurrences2[] = new int[stateList.size()];
				for(String str:edgeListString)  
				for(int i =0; i< stateList.size();i++){
					
					
					
				if(stateList.get(i).getName().equals("index"))
					occurrences[i]	= Collections.frequency(edgeListString, stateList.get(i).getName().toString());
				else
				occurrences[i] = Collections.frequency(edgeListString, stateList.get(i).getName().toString()+" ");
				
				occurrences2[i]=Collections.frequency(edgeListString, stateList.get(i).getName().toString());
					
				
				
				
				}
				for(int i=0;i<stateList.size();i++){
					if((occurrences[i]+occurrences2[i]==1)){
						String oneElementCase = "";
						
						elementCase.append("webstate="+stateList.get(i).getName().toString()+" "+":{");
						for(int j=0;j<edgeList.size();j++){
						if((stateList.get(i).getName().toString()+" ").equals(edgeList.get(j).getFrom().getName().toString()) || (stateList.get(i).getName().toString().equals(edgeList.get(j).getFrom().getName().toString()) )){
							n++;
							oneElementCase=oneElementCase.concat(edgeList.get(j).getXpathh().getXpath().replace('[', '_').replace(']', '_').replace('/', '_'));
							}}
							elementCase.append(oneElementCase+"};");
							elementCase.append(System.getProperty("line.separator")); 
							
							
							
								}
						
						
	else if(occurrences[i] + occurrences2[i]==2){
						
						for(int j=0;j<edgeList.size();j++){
							if((stateList.get(i).getName().toString()+" ").equals(edgeList.get(j).getFrom().getName().toString())  || (stateList.get(i).getName().toString().equals(edgeList.get(j).getFrom().getName().toString()) ))
						manyElementCase=manyElementCase.concat(edgeList.get(j).getXpathh().getXpath().replace('[', '_').replace(']', '_').replace('/', '_')+",");
							}
						
                        manyElementCase=manyElementCase.concat("};");
						manyElementCase=manyElementCase.toString().replace(",}","}");
						manyElementCase=manyElementCase.toString().replace("};HTML",",HTML");
						elementCase.append("webstate="+stateList.get(i).getName().toString()+":{"+manyElementCase);
						
						elementCase.append(System.getProperty("line.separator")); 
						
				}
						
					
					else if((occurrences[i] >= 1) || (occurrences2[i]>=1)){
						
						for(int j=0;j<edgeList.size();j++){
							if((stateList.get(i).getName().toString()+" ").equals(edgeList.get(j).getFrom().getName().toString())  || (stateList.get(i).getName().toString().equals(edgeList.get(j).getFrom().getName().toString()) ))
						manyElementCase=manyElementCase.concat(edgeList.get(j).getXpathh().getXpath().replace('[', '_').replace(']', '_').replace('/', '_')+",");
							}
						
                        manyElementCase=manyElementCase.concat("};");
						manyElementCase=manyElementCase.toString().replace(",}","}");
						manyElementCase=manyElementCase.toString().replace("};HTML",",HTML");
						elementCase.append("webstate="+stateList.get(i).getName().toString()+":{"+manyElementCase);
						
						elementCase.append(System.getProperty("line.separator")); 
						
				}}
				
				return elementCase;
					}
			
			
			public StringBuffer printSMVElementCaseCrawl() {
				Vector<Element> elementList=initialize_element();
				Vector<WebState> stateList=initialize_state();
				Vector<Edge> edgeList=initialize_edge();
				int n = 0;
				StringBuffer elementCase = new StringBuffer();
				
				
				ArrayList<String> edgeListString = new ArrayList<String>();
				
				for(int i=0;i<edgeList.size();i++){
					edgeListString.add(edgeList.get(i).getFrom().getName().toString());
					edgeListString.add(edgeList.get(i).getXpathh().getXpath().toString());
				}
				int occurrences[] = new int[stateList.size()];
				for(int i =0; i< stateList.size();i++)
				occurrences[i] = Collections.frequency(edgeListString, stateList.get(i).getName());
				
				
				for(int i=0;i<stateList.size();i++){
					if(occurrences[i]==1){
						String oneElementCase = "";
						
						elementCase.append("webstate="+stateList.get(i).getName()+":{");
						
						for(int j=0;j<edgeList.size();j++){
							if(stateList.get(i).getName().toString().equals(edgeList.get(j).getFrom().getName().toString())){
								n++;
							oneElementCase=oneElementCase.concat(edgeList.get(j).getXpathh().getXpath().replace('[', '_').replace(']', '_').replace('/', '_'));
							}	}
						
						elementCase.append(oneElementCase+"};");
						elementCase.append(System.getProperty("line.separator")); 
					}
					else if(occurrences[i] >= 1){
						String manyElementCase="";
						for(int j=0;j<edgeList.size();j++){
							if(stateList.get(i).getName().equals(edgeList.get(j).getFrom().getName().toString()))
						manyElementCase=manyElementCase.concat(edgeList.get(j).getXpathh().getXpath().replace('[', '_').replace(']', '_').replace('/', '_')+",");
							
						}
						manyElementCase=manyElementCase.concat("};");
						manyElementCase=manyElementCase.toString().replace(",}","}");
						elementCase.append("webstate="+stateList.get(i).getName()+":{"+manyElementCase);
						elementCase.append(System.getProperty("line.separator")); 
						
					}
				}	
					
				return elementCase;
				
			}
			
			
			public StringBuffer printReachabilityProperty2(){
				Vector<WebState> stateList=initialize_state();
				StringBuffer prop = new StringBuffer();
				for(int i=1;i<stateList.size();i++){
				prop.append("SPEC "+"AG(webstate="+stateList.get(0).getName()+" -> EF(webstate="+stateList.get(i).getName()+"));");
				prop.append(System.getProperty("line.separator"));
				
				}
				return prop;
				
			}
			
			public StringBuffer printIndexReachFromProperty(){
				Vector<WebState> stateList=initialize_state();
				StringBuffer prop = new StringBuffer();
				for(int i=1;i<stateList.size();i++){
					prop.append("SPEC "+"AG(webstate="+stateList.get(i).getName()+" -> EF(webstate="+stateList.get(0).getName()+"));");
					prop.append(System.getProperty("line.separator"));
				}
				return prop;
				
				
			}
			
				
		
		 
		 public StringBuffer printBrokenLinkProperty(){
			 Vector<WebState> stateList=initialize_state();
			 StringBuffer brokenLinkProp = new StringBuffer();
			 for(int i=0;i<stateList.size();i++){
				 brokenLinkProp.append("SPEC AG(webstate="+stateList.elementAt(i).getName().toString()+" -> EX(");
				 for(int j=0;j<stateList.size()-1;){
					 if(!(stateList.get(i).getName().equals(stateList.get(j).getName()))){
						 if(i==stateList.size()-1){
							 brokenLinkProp.append("webstate="+stateList.elementAt(j).getName().toString());
							 if( j< stateList.size()-2){
								 brokenLinkProp.append("|");
						 }}
						 else
						  brokenLinkProp.append("webstate="+stateList.elementAt(j).getName().toString()+"|");
						  ++j;
							}
					 
				 else if(stateList.get(i).getName().equals(stateList.get(j).getName())){
					 ++j;	 
					 }	 
				 } 
				 if(i==stateList.size()-1)
				 brokenLinkProp.append("));"); 
				 else
				 brokenLinkProp.append("webstate="+stateList.elementAt(stateList.size()-1).getName().toString()+"));"); 
				 
				 brokenLinkProp.append(System.getProperty("line.separator"));
					 }
			 return brokenLinkProp;
		 }
		   
			
			
		public File createSMV(File file, Boolean crawlType){
			Vector<WebState> stateList=initialize_state();
                   try {
                	   if(file == null){
                		   System.out.println("smv input file could not be found");
                	   }else
                output = new BufferedWriter(new FileWriter(file));
				output.write("MODULE main"); output.newLine();
				output.write("VAR"); output.newLine();
				output.write(printStates().toString());output.newLine();
				output.write(printElements().toString());output.newLine();
				
				output.write("ASSIGN");output.newLine();
				output.write("init(webstate):="+stateList.get(0).getName().toString()+";");output.newLine();
				output.write("init(element):=null_element;");output.newLine();output.newLine();
				
				output.write("next(webstate):= case");output.newLine();
				output.write(printSMVStateCase().toString());
			    output.write("TRUE:dead_end_webstate;");output.newLine();
			    output.write("esac;");output.newLine();output.newLine();
		        output.write("next(element):= case");output.newLine();
		        if(crawlType==true){
		        output.write(printSMVElementCaseCrawl().toString());
		        }
		        else{
		        	output.write(printSMVElementCase().toString()); 
		        	}
		        output.write("webstate=dead_end_webstate:null_element;"); output.newLine();
		        output.write("TRUE:element;"); output.newLine();
		        output.write("esac;"); output.newLine();output.newLine();
		        
		        
		       
		        output.write("--INDEX IS REACHABLE FROM ALL STATES");output.newLine();
		        
		     
		        output.write(printIndexReachFromProperty().toString());
		        
		      
		        output.write("--NO BROKEN LINK OR DEAD END");output.newLine();
		        output.write(printBrokenLinkProperty().toString()); output.newLine();
		        
		        output.flush();
		       
			} catch (IOException ex) {
				
				ex.printStackTrace();
			}
			return file;
			
			
			
		
		}
		
		
		

		
}				
					

		   
		
		
		
		
	
		
		
		
		
	
	
	
	
	


