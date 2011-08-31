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
		
		return (next!=null?next.toString():"")+"ERROR "+msg.toString()+"<SQL>";
	}
	public ErrorList( ) {
		 
	}
	
}
