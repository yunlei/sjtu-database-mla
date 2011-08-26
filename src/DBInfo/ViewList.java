package DBInfo;

import java.io.Serializable;

public class ViewList implements Serializable{
	public View view;
	public ViewList next;
	public ViewList(View view, ViewList next) {
		this.view = view;
		this.next = next;
	}
}
