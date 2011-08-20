package Absyn;

/**
 * @author MaYunlei
 *
 */
public class CreateElementList {
	public CreateElement first;
	public CreateElementList next;
	public CreateElementList(CreateElement f,CreateElementList n){
		first=f;next=n;
	}
}
