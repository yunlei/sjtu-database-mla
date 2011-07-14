package ErrorMsg;

public class ErrorMsg {
	private String msg;
	private int location;
	public ErrorMsg()
	{}
	public ErrorMsg( int location,String msg) {
		 
		this.msg = msg;
		this.location = location;
	}
	
}
