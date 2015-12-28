package Data;
import java.io.IOException;
import java.util.Vector;




public interface  IntFormat {
	public void ReadCrawlOutput(String fileName) throws IOException ;
    
    public  void initialize_States();
    public   void initialize_Edges();
    
    public void initialize_Elements();
    
    public  Vector<Element> getElementList(); 

    public void setElementList(Vector<Element> elementList); 

    public  Vector<WebState> getStateList(); 

    public  void setStateList(Vector<WebState> stateList);

    public  Vector<Edge> getEdgeList(); 

    public void setEdgeList(Vector<Edge> edgeList);
    
    
    
}