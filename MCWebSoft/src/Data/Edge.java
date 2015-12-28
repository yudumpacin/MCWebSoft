package Data;



public class Edge {
 
	private WebState from;
	private WebState to;
	private Element xpathh;

	
	public Edge(){
		from = new WebState();
		to = new WebState();
		xpathh = new Element();
	}
	
	public void setFrom(WebState from) {
		this.from.setName(from.getName());
		//this.from.setUrl(from.getUrl());
	}
	
	
	public void setTo(WebState to) {
		this.to.setName(to.getName());
		//this.to.setUrl(to.getUrl());
	}

	public void setXpathh(Element xpathh) {
		this.xpathh.setXpath(xpathh.getXpath());
	}

	public WebState getFrom() {
		return from;
	}

	public WebState getTo() {
		return to;
	}

	public Element getXpathh() {
		return xpathh;
	}

	
	
		
		
}

