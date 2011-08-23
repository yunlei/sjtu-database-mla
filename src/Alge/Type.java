package Alge;

public class Type {
	public static int INT=0;
	public static int BOOL=1;
	public static int CHAR=2;
	public static int INTSIZE=11;
	public static int BOOLSIZE=5;
	public int type;
	public int size;
	public Type(int type, int size) {
		this.type = type;
		this.size = size;
	}
	public Type(int type) {
		this.type = type;
		this.size=-1;
	} 
}
