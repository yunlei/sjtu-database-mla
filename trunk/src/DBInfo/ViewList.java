package DBInfo;

public class ViewList {
	public View view;
	public ViewList next;
	public ViewList(View view, ViewList next) {
		this.view = view;
		this.next = next;
	}
	
}
