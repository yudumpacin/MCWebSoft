package Data;
import java.util.ArrayList;


public class Trace {

	private ArrayList<WebState> stateList;
	private ArrayList<Element> elementList;
	
	public ArrayList<WebState> getState() {
		return stateList;
	}
	public void setState(ArrayList<WebState> webState) {
		this.stateList = webState;
	}
	public ArrayList<Element> getElement() {
		return elementList;
	}
	public void setElement(ArrayList<Element> element) {
		this.elementList = element;
	}
	
}
