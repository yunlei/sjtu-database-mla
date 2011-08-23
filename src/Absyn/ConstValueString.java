package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class ConstValueString extends ConstValue implements Serializable{
	public String value;
	public ConstValueString(int p, String v){
		pos = p;
		value = v;
	}
	public ConstValueString(String result) {
		value = result;
		// TODO Auto-generated constructor stub
	}
	public String getValue()
	{
		return value;
	}
}
