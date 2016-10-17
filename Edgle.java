package compiler_H_java;
//图的边类
public class Edgle {
	private String mark;//边的标记（权值）
	private Node startNode;//起始边
	private Node endNode;//结束边
	public Edgle()
	{
		mark="?";
		startNode=new Node(-1);
		endNode=new Node(-1);
	}
	public Edgle(String ma,Node st,Node end)
	{
		mark=ma;
		startNode=(Node)st.Clone();
		endNode=(Node)end.Clone();
	}
	public void SetMark(String ma)
	{
		mark=ma;
	}
	public String GetMark()
	{
		return mark;
	}
	public void setNode(Node st,Node end)//两个参数，getNodew为-1代表不对该参数进行设置
	{
		if(st.getNode()!=-1) startNode=(Node)st.Clone();
		if(end.getNode()!=-1) endNode=(Node)end.Clone();
	}
	public Node GetStartnode()
	{
		return startNode;
	}
	public Node GetEndnode()
	{
		return endNode;
	}
}
