package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
abstract public class ConstValue extends Value implements Serializable{
	public abstract Object getValue(); 
}
