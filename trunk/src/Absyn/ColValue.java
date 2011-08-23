package Absyn;

import java.io.Serializable;

/**
 * @author MaYunlei
 *
 */
public class ColValue extends Value implements Serializable{
	public ColName name;
	public ColValue(ColName c)
	{
		name=c;
	}
}
