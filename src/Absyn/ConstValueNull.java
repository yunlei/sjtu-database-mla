package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class ConstValueNull extends ConstValue implements Serializable{

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return "NULL";
	}

}
