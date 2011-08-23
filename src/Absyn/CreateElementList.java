package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class CreateElementList implements Serializable{
	public CreateElement first;
	public CreateElementList next;
	public CreateElementList(CreateElement f,CreateElementList n){
		first=f;next=n;
	}
}
