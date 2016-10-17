package compiler_H_java;
//图的点类
public class Node implements Cloneable{
	private static int sumNum=0;
	private int nodeNum;
	public Node()
	{
		nodeNum=-1;
	}
	public Node(int num)
	{
		nodeNum=num;
	}
	public int getNode()
	{
		return nodeNum;
	}
	public void setNode(int num)
	{
		nodeNum=num;
	}
	public static int getSum()//每次获取点都让点的数字加一
	{
		sumNum++;
		return sumNum-1;
	}
	//Node对象的复制
	public Object Clone()
	{
		Object o=null;
		try
		{
			o=(Node)super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			System.out.println(e.toString());
		}
		return o;
	}
}
