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
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public String toString()
	{
		return "ERROR("+this.location+"):"+msg;
	}
	
	
}
