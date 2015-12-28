package Application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class NuSMVExecuter {
	
	private File smvInputFile = new File("NuSMV//2.5.4//bin//smvInput2.smv");
    private File smvOutputFile;
   
	

	

	public NuSMVExecuter(){
		
		
	}
	
	public File getsmvInputFileName() {
		return smvInputFile;
	}

	public void setsmvInputFile(File fileName) {
		this.smvInputFile = fileName;
	}
	
	public File getSmvOutputFile() {
		return this.runSMV();
	}

	public void setSmvOutputFile(File smvOutputFile) {
		this.smvOutputFile = smvOutputFile;
	}

	public File runSMV(){
		smvOutputFile = new File("lib\\output.txt");
		try {
			 ProcessBuilder builder = new ProcessBuilder(
			            "cmd.exe", "/c", "cd \"NuSMV/2.5.4/bin\" && nusmv.exe "+this.getsmvInputFileName().getName());
				
			    builder.redirectErrorStream(true);
			    Process p = builder.start();
			    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			    String line;
			    BufferedWriter output = new BufferedWriter(new FileWriter( smvOutputFile));
			    while (true) {
			        line = r.readLine();
			        if (line == null) { break; }
			        output.write(line);
			        output.newLine();
			    }output.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return smvOutputFile;

	}
	
	
	

}
