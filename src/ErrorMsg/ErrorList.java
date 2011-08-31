package ErrorMsg;

public class ErrorList {
	public ErrorMsg msg;
	public ErrorList next;
	public ErrorList(ErrorMsg msg, ErrorList next) {
		this.msg = msg;
		this.next = next;
	}
	public String toString(){
		if(msg==null)
			return "";
		
		return msg.toString()+(next!=null?next.toString():"");
	}
	public ErrorList( ) {
		 
	}
	
}
