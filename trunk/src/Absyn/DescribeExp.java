package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class DescribeExp extends Exp implements Serializable{
	public NameList names;

	public DescribeExp(NameList names) {
		this.names = names;
	}
	

}
