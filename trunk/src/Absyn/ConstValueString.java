package Absyn;

public class ConstValueString extends ConstValue{
	public String value;
	public ConstValueString(int p, String v){
		pos = p;
		value = v;
	}
	public ConstValueString(String result) {
		value = result;
		// TODO Auto-generated constructor stub
	}
}
