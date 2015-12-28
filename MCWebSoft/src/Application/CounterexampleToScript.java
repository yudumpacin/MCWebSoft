package Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.JUnitCore;


import Data.Element;
import Data.Trace;
import Data.WebState;

 public class CounterexampleToScript {
	
	private NuSMVExecuter nusmv;
	private ArrayList<FileWriter> writerList = new ArrayList<FileWriter>();
	private Vector<Trace> traceList = new Vector<Trace>();
	
	
	
	static ArrayList<String> linesOfSMVOutput = new ArrayList<String>();
	
	public String getRestOfCode(int i){
	
	String restOfcode = 

			"}"+"\n"+"@After"+"\n"+
			  "public void tearDown() throws Exception {"+"\n"+
				"Thread.sleep(10000);"+"\n"+
				getTraceType(i)+";"+"\n"+
				"openDialog(note);"+"\n"+
			   " driver.close();"+"\n"+
			 "   String verificationErrorString = verificationErrors.toString();"+"\n"+
			   " if (!"+"\""+"\""+".equals(verificationErrorString)) {"+"\n"+
			   "   fail(verificationErrorString);"+"\n"+
			  "}"+"\n"+
			" }"+"\n"+

			 " public void openDialog(String note){"+"\n"+
				  
				  "if(note.length()>200){"+"\n"+
					 "JOptionPane optionPane = new NarrowOptionPane();"+"\n"+
					    "optionPane.setMessage(note);"+"\n"+
					    "optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);"+"\n"+
					    "JDialog dialog = optionPane.createDialog(null, "+"\"" +"Width 100"+"\""+");"+"\n"+
					    "dialog.setVisible(true);"+"\n"+
					 " }"+"\n"+
					  "else"+"\n"+
						 " JOptionPane.showMessageDialog(null, note);  "+"\n"+
				  
			 "}"+"\n"+
             "class NarrowOptionPane extends JOptionPane {"+"\n"+

				  "NarrowOptionPane() {"+"\n"+
				  "}"+"\n"+

				  "public int getMaxCharactersPerLineCount() {"+"\n"+
				   " return 100;"+"\n"+
				" }"+"\n"+"}}";
	
	return restOfcode;
	
	}
	
	public CounterexampleToScript() throws IOException{
		nusmv=new NuSMVExecuter();
		if(new File("lib\\output.txt").isFile())
		this.readSmvOutput(nusmv.getSmvOutputFile());
		
	}
	
	
	
	
	public ArrayList<String> readJunitTemplate(){
		ArrayList<String> linesOfJunitOutput = new ArrayList<String>();
		 BufferedReader br = null;
		 
			try {
	 
				String sCurrentLine;
	 
				//br = new BufferedReader(new FileReader("C:\\Users\\yudum\\workspace\\XMLtoSMV\\template.java"));
				br = new BufferedReader(new FileReader("lib\\template.java"));
				while ((sCurrentLine = br.readLine()) != null) {
					linesOfJunitOutput.add(sCurrentLine);	
				}}catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (br != null)br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}}
			
			return linesOfJunitOutput;
		
	}

	public void readSmvOutput(File file){
		 BufferedReader br = null;
		 
			try {
	 
				String sCurrentLine;
	 
				br = new BufferedReader(new FileReader("lib\\output.txt"));
				while ((sCurrentLine = br.readLine()) != null) {
					linesOfSMVOutput.add(sCurrentLine);	
				}}catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (br != null)br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}}
		
	}
	
	public void initializeOutputTrace(){
		
		int occurrences = Collections.frequency(linesOfSMVOutput, "Trace Type: Counterexample ");
		int[] occurrencesArray = new int [occurrences];
		for(int i=0; i<occurrences; i++){
			
			
			if(i==0){
				
				
				occurrencesArray[i] = linesOfSMVOutput.indexOf("Trace Type: Counterexample ");
			}
			else if(i>0){
				
				
				linesOfSMVOutput.remove(linesOfSMVOutput.indexOf("Trace Type: Counterexample "));
				
				occurrencesArray[i] = linesOfSMVOutput.indexOf("Trace Type: Counterexample ");
			}
				
			Trace trace = new Trace();
			ArrayList<WebState> stateList = new ArrayList<WebState>();
			ArrayList<Element> elementList = new ArrayList<Element>();
			
			
	        if(linesOfSMVOutput.get(occurrencesArray[i]+2).contains("webstate")){
		    String [] partOfStateequation = linesOfSMVOutput.get(occurrencesArray[i]+2).split("=");
		    WebState webState = new WebState();
		    webState.setName(partOfStateequation[1].toString());
		    stateList.add(webState);
	        }
		    	
		    
		 
	        if(linesOfSMVOutput.get(occurrencesArray[i]+3).contains("element")){
		    String [] partOfElementequation = linesOfSMVOutput.get(occurrencesArray[i]+3).split("=");
		    Element element = new Element();
		    element.setXpath(partOfElementequation[1].toString());
		    elementList.add(element);
	        }
		    
		   
		   
		    
		   int j = 1;
		  
		   while(linesOfSMVOutput.contains("-> State: "+(i+1)+"."+(j+1)+" <-") & i<=occurrences-1){
			 
			    String [] partOfState2equation = linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+1).split("=");
			    WebState state1 = new WebState();
			    state1.setName(partOfState2equation[1].toString());
			    stateList.add(state1);
			    
			    if(linesOfSMVOutput.size()>linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+2)
			    if(linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+2).contains("element")){
			    String [] partOfElement2equation = linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+2).split("=");
			    Element element1 = new Element();
			    element1.setXpath(partOfElement2equation[1].toString());
			    elementList.add(element1);
			    }
			    ++j;
			   
		   	
		    }
		   
		   
		    trace.setElement(elementList);
		    trace.setState(stateList);
		    traceList.add(trace);
		    
		    
		   
		    
		    
		   
		}
		
	
		 
		
	}
	
	public void initializeOutputTraceWithLogin(){
		
		int occurrences = Collections.frequency(linesOfSMVOutput, "Trace Type: Counterexample ");
		int[] occurrencesArray = new int [occurrences];
		for(int i=0; i<occurrences; i++){
			
			
			if(i==0){
				
				
				occurrencesArray[i] = linesOfSMVOutput.indexOf("Trace Type: Counterexample ");
			}
			else if(i>0){
				
				
				linesOfSMVOutput.remove(linesOfSMVOutput.indexOf("Trace Type: Counterexample "));
				
				occurrencesArray[i] = linesOfSMVOutput.indexOf("Trace Type: Counterexample ");
			}
				
			Trace trace = new Trace();
			ArrayList<WebState> stateList = new ArrayList<WebState>();
			ArrayList<Element> elementList = new ArrayList<Element>();
			
			
	        if(linesOfSMVOutput.get(occurrencesArray[i]+3).contains("webstate")){
		    String [] partOfStateequation = linesOfSMVOutput.get(occurrencesArray[i]+3).split("=");
		    WebState webState = new WebState();
		    webState.setName(partOfStateequation[1].toString());
		    stateList.add(webState);
		    
	        }
		    	
		    
		 
	        if(linesOfSMVOutput.get(occurrencesArray[i]+4).contains("element")){
		    String [] partOfElementequation = linesOfSMVOutput.get(occurrencesArray[i]+4).split("=");
		    Element element = new Element();
		    element.setXpath(partOfElementequation[1].toString());
		    elementList.add(element);
		    
	        }
		    
		   
		   
		    
		   int j = 1;
		  
		   while(linesOfSMVOutput.contains("-> State: "+(i+1)+"."+(j+1)+" <-") & i<=occurrences-1){
			   
			   if(linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+1).contains("login")){
				   if(linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+2).contains("webstate")){
			    String [] partOfState2equation = linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+2).split("=");
			    WebState state1 = new WebState();
			    state1.setName(partOfState2equation[1].toString());
			    stateList.add(state1);
			    
				   }
		
			    if(linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+3).contains("element")){
			    String [] partOfElement2equation = linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+3).split("=");
			    Element element1 = new Element();
			    element1.setXpath(partOfElement2equation[1].toString());
			    elementList.add(element1);
			    
			    }
			    ++j;
			   
		   	
		    }else{
		    	
		    	if(linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+1).contains("webstate")){
		    	String [] partOfState2equation = linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+1).split("=");
			    WebState state1 = new WebState();
			    state1.setName(partOfState2equation[1].toString());
			    stateList.add(state1);
			    
		    	 }
		
			    if(linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+2).contains("element")){
			    String [] partOfElement2equation = linesOfSMVOutput.get(linesOfSMVOutput.indexOf("-> State: "+(i+1)+"."+(j+1)+" <-")+2).split("=");
			    Element element1 = new Element();
			    element1.setXpath(partOfElement2equation[1].toString());
			    elementList.add(element1);
			    
			    }
			    ++j;
		    }
				}
		
		   
		    trace.setElement(elementList);
		    trace.setState(stateList);
		    traceList.add(trace);
		    
		   
		}
	
	
	}
	
	
	public void initializeWithLogin(String xpathLogin, String un, String ui, String pn, String pi, Boolean isCrawljax) throws IOException{
		
		ArrayList<String> linesOfJunitOutput=new ArrayList<>();
		
		
		IntFormatToNuSMV f = new IntFormatToNuSMV();
		
		
		
		for(int i=0 ; i<traceList.size() ; i++){
			
			linesOfJunitOutput=readJunitTemplate();
			String indexUrl = f.initialize_state().get(0).getUrl().toString();
			linesOfJunitOutput.set(25, "baseUrl = "+ "\""+ indexUrl+ "\""+";" );
			linesOfJunitOutput.set(13, "public class Template"+i+"{");
			
			//writerList.add(new FileWriter("C:\\Users\\yudum\\workspace\\XMLtoSMV\\src\\junit\\Template"+i+".java"));
			
			writerList.add(new FileWriter("src\\junit\\Template"+i+".java"));
			
			if(isCrawljax){
				
			for(int j =0 ; j<traceList.get(i).getElement().size() ; j++){
				
				if(!(traceList.get(i).getElement().get(j).getXpath().toString().equals(" null_element") )){
					
					 linesOfJunitOutput.set(34+j,"openDialog("+"\""+"clicking to"+traceList.get(i).getElement().get(j).getXpath().toString().replaceAll("_(\\d+)_", "[$1]").replaceAll("_", "/")+"\""+"); "+"\n"+
												 "//WebElement eleMenuShowtimes = driver.findElement(By.xpath("+"\""+traceList.get(i).getElement().get(j).getXpath().toString().replaceAll("_(\\d+)_", "[$1]").replaceAll("_", "/")+ "\""+"));"+"\n"+
                                                 "//Actions builder = new Actions(driver);"+"\n"+
                                                 "//builder.moveToElement(eleMenuShowtimes).build().perform();"+"\n"+
							                     "driver.findElement(By.xpath("+"\""+traceList.get(i).getElement().get(j).getXpath().toString().replaceAll("_(\\d+)_", "[$1]").replaceAll("_", "/")+ "\""+")).click();");}
			
				
				
				}
		
			}
			
				else if(!isCrawljax){
					for(int j =0 ; j<traceList.get(i).getElement().size() ; j++){
						if(!(traceList.get(i).getElement().get(j).getXpath().toString().equals(" null_element") )){
							
							String output=null;
							Pattern p = Pattern.compile("\\d+");
							Matcher m = p.matcher(traceList.get(i).getElement().get(j).getXpath().toString().replaceAll("_(\\d+)_", "[$1]").replaceAll("_", "/"));
							StringBuffer sb = new StringBuffer();
							while (m.find()) {
							    m.appendReplacement(sb, String.valueOf(Integer.parseInt(m.group()) + 1));
							}
							m.appendTail(sb);

						    output = sb.toString();
					 linesOfJunitOutput.set(33+j, "openDialog("+"\""+"clicking to"+output+"\""+");"+"\n"+
							 "//WebElement eleMenuShowtimes = driver.findElement(By.xpath("+"\""+output+"\""+"));"+"\n"+
                             "//Actions builder = new Actions(driver);"+"\n"+
                             "//builder.moveToElement(eleMenuShowtimes).build().perform();"+"\n"+
						    "driver.findElement(By.xpath("+"\""+output+"\""+")).click();".trim());
					 
					 
					}
					}
					
				
			}
			
			for(int m=0;m<linesOfJunitOutput.size();m++){
				
				if(linesOfJunitOutput.get(m).contains("driver.findElement(By.xpath("+"\""+" "+xpathLogin+ "\""+")).click();")){
					
					linesOfJunitOutput.get(i).toString().replace("driver.findElement(By.xpath("+"\""+" "+xpathLogin+ "\""+")).click();","driver.findElement(By.xpath("+"\""+" "+xpathLogin+ "\""+")).click();"
					+"driver.findElement(By.id("+"\""+ui+"\""+")).sendKeys("+"\""+un+"\""+");"
					+"driver.findElement(By.id("+"\""+pi+"\""+")).clear();"
					+"driver.findElement(By.id("+"\""+pi+"\""+")).sendKeys("+"\""+pn+"\""+");"
			        +"driver.findElement(By.xpath("+"\""+xpathLogin+ "\""+")).click();");
				
					linesOfJunitOutput.set(m, "driver.findElement(By.id("+"\""+ui+"\""+")).sendKeys("+"\""+un+"\""+");"
					+"driver.findElement(By.id("+"\""+pi+"\""+")).clear();"
					+"driver.findElement(By.id("+"\""+pi+"\""+")).sendKeys("+"\""+pn+"\""+");"
			        +"driver.findElement(By.xpath("+"\""+xpathLogin+ "\""+")).click();");
			       
			}}
			linesOfJunitOutput.add(getRestOfCode(i));
			try {
				
				for(int k =0;k< linesOfJunitOutput.size();k++){
				writerList.get(i).write((linesOfJunitOutput.get(k).toString()));
				writerList.get(i).write(System.getProperty( "line.separator" ));}
				writerList.get(i).close();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			linesOfJunitOutput=null;
			
			
		}
		
	
		
		
		
	}
	
	
	
	public void initalizeJunit(Boolean isCrawljax) throws IOException{
	
		ArrayList<String> linesOfJunitOutput2=new ArrayList<>();
		IntFormatToNuSMV f = new IntFormatToNuSMV();
		
		
		
	
		for(int i=0 ; i<traceList.size() ; i++){
			
			linesOfJunitOutput2=readJunitTemplate();
			String indexUrl = f.initialize_state().get(0).getUrl().toString();
			linesOfJunitOutput2.set(25, "baseUrl = "+ "\""+ indexUrl+ "\""+";" );
			linesOfJunitOutput2.set(13, "public class Template"+i+"{");

			//writerList.add(new FileWriter("C:\\Users\\yudum\\workspace\\XMLtoSMV\\src\\junit\\Template"+i+".java"));
			
			writerList.add(new FileWriter("src\\junit\\Template"+i+".java"));
			
		       if(isCrawljax){
				for(int j =0 ; j<traceList.get(i).getElement().size() ; j++){
					if(!(traceList.get(i).getElement().get(j).getXpath().toString().equals(" null_element") )){
						
						
								 linesOfJunitOutput2.set(34+j,
                         "//WebElement eleMenuShowtimes = driver.findElement(By.xpath("+"\""+traceList.get(i).getElement().get(j).getXpath().toString().replaceAll("_(\\d+)_", "[$1]").replaceAll("_", "/")+ "\""+"));"+"\n"+
                         "//Actions builder = new Actions(driver);"+"\n"+
                         "//builder.moveToElement(eleMenuShowtimes).build().perform();"+"\n"+
					     "driver.findElement(By.xpath("+"\""+traceList.get(i).getElement().get(j).getXpath().toString().replaceAll("_(\\d+)_", "[$1]").replaceAll("_", "/")+ "\""+")).click();");}
					}
				
				}
				
					else if(!isCrawljax){
						for(int j =0 ; j<traceList.get(i).getElement().size() ; j++){
							if(!(traceList.get(i).getElement().get(j).getXpath().toString().equals(" null_element") )){
								String output=null;
								Pattern p = Pattern.compile("\\d+");
								Matcher m = p.matcher(traceList.get(i).getElement().get(j).getXpath().toString().replaceAll("_(\\d+)_", "[$1]").replaceAll("_", "/"));
								StringBuffer sb = new StringBuffer();
								while (m.find()) {
								    m.appendReplacement(sb, String.valueOf(Integer.parseInt(m.group()) + 1));
								}
								m.appendTail(sb);

							    output = sb.toString();
						 linesOfJunitOutput2.set(33+j, "openDialog("+"\""+"clicking to"+output+"\""+");"+"\n"+
								 "//WebElement eleMenuShowtimes = driver.findElement(By.xpath("+"\""+output+"\""+"));"+"\n"+
		                         "//Actions builder = new Actions(driver);"+"\n"+
		                         "//builder.moveToElement(eleMenuShowtimes).build().perform();"+"\n"+
							     "driver.findElement(By.xpath("+"\""+output+"\""+")).click();".trim());
							}
						}
					
					
				}
		       linesOfJunitOutput2.add(getRestOfCode(i));
			try {
				
				for(int k =0;k< linesOfJunitOutput2.size();k++){
				writerList.get(i).write((linesOfJunitOutput2.get(k).toString()));
				writerList.get(i).write(System.getProperty( "line.separator" ));}
				writerList.get(i).close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			linesOfJunitOutput2=null;
			
		}
}

	ArrayList<String> noteList = new ArrayList<String>();
	public String getTraceType(int h){
		ArrayList<String> traceTypeList = new ArrayList<String>();
		
		for(int i=0;i<linesOfSMVOutput.size();i++){
			if(linesOfSMVOutput.get(i).equals("-- as demonstrated by the following execution sequence")){
				traceTypeList.add(linesOfSMVOutput.get(i-1));
			}
			
		}
		
		
		for(int i=0;i<traceTypeList.size();i++){
		if(traceTypeList.get(i).contains(" -> EF (webstate = index))")){
		noteList.add(i,"violation of the " +traceTypeList.get(i).substring(17, traceTypeList.get(i).indexOf("is false"))+ "property, Homepage is not reachable from this page");}
		if((traceTypeList.get(i).contains(" AG (webstate = ")) & traceTypeList.get(i).contains(" -> EF webstate = index)")){
		noteList.add(i,"violation of the " +traceTypeList.get(i).substring(17, traceTypeList.get(i).indexOf("is false"))+ "property, Homepage is not reachable from this page");}
		else if (traceTypeList.get(i).contains(" -> EX (") | traceTypeList.get(i).contains(" -> !EX ")){
		noteList.add(i,"violation of the " +traceTypeList.get(i).substring(17, traceTypeList.get(i).indexOf("is false"))+ "property, This page is a dead-end, has not next page to go");}
		else if (traceTypeList.get(i).contains("AG (webstate = index -> EF") ){
		noteList.add(i,"violation of the " +traceTypeList.get(i).substring(17, traceTypeList.get(i).indexOf("is false"))+ "property, This page could not reachable from homepage");}
		
		else if (traceTypeList.get(i).contains("!(EF (webstate = ")){
		noteList.add(i,"violation of the " +traceTypeList.get(i).substring(17, traceTypeList.get(i).indexOf("is false"))+ "property, This page should have been shown after login");}

		//else if ((traceTypeList.get(i).contains("AG (webstate = ") & traceTypeList.get(i).contains("-> EF webstate = ")) | (traceTypeList.get(i).contains("AG (( webstate = ") & traceTypeList.get(i).contains("-> EF webstate = "))){
		else
			noteList.add(i,"violation of the " +traceTypeList.get(i).substring(17, traceTypeList.get(i).indexOf("is false"))+ "property, This page does not hold link consistency");}
		
		//}
		
		
		
		return ("String note="+ "\""+ noteList.get(h).toString()+ "\""+ ";");
	
		
	}
	
	
	public void runOnSelenium() throws IOException{

		Class temp0;
		
		try {
			for(int i=0;i<traceList.size();i++){
				
			temp0 = Class.forName("junit.Template"+i);
			
			
			org.junit.runner.JUnitCore.runClasses(temp0);
			
			deleteJunitFiles();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void deleteJunitFiles(){
		//String path="C:\\Users\\yudum\\workspace\\XMLtoSMV\\src\\junit"; 
		String path="src\\junit"; 
        File file = new File(path);
        File[] files = file.listFiles(); 
        for (File f:files) 
        {if (f.isFile() && f.exists() & f.getName().contains("T")) 
            { f.delete();

            }else{

} }  }
		
	}
	
	

