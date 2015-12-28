package Data;



public class Element {
private String xpath;

public String getXpath() {
	return xpath;
}

public void setXpath(String xpath) {
	this.xpath = xpath;
}

public boolean equals(Object o){
	
	if(!(o instanceof Element))
		return false;
	
	Element other = (Element)o;
	return this.xpath.equals(other.xpath);
	
}


}
