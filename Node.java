package compiler_H_java;
//图的点类
public class Node implements Cloneable{
	private static int sumNum=0;
	private int nodeNum;
	private String DFAMark;
	public Node()
	{
		nodeNum=-1;
		DFAMark=null;
	}
	public Node(int num)
	{
		nodeNum=num;
		DFAMark=null;
	}
	public Node(String mark)
	{
		nodeNum=-1;
		DFAMark=mark;
	}
	public int getNode()
	{
		return nodeNum;
	}
	public String getDFANode()
	{
		return DFAMark;
	}
	public void setNode(int num)
	{
		nodeNum=num;
	}
	public void setNode(String ma)
	{
		DFAMark=ma;
	}
	public static int getSum()//每次获取点都让点的数字加一
	{
		sumNum++;
		return sumNum-1;
	}
	public static int getNodeSum()
	{
		return sumNum;//返回点的总数
	}
	//Node对象的复制
//	public Object Clone()
//	{
//		Object o=null;
//		try
//		{
//			o=(Node)super.clone();
//		}
//		catch(CloneNotSupportedException e)
//		{
//			System.out.println(e.toString());
//		}
//		return o;
//	}
	//虽然不克隆会破坏封装性，但是鉴于一个点就是一个对象，克隆点虽然值是一样，但是所表示
	//的应当还是另外个点，所以取消克隆对象方法
}
