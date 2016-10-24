package compiler_H_java;
import java.util.*;

//图类
public class Graph {
	protected Map<Node,List<Edgle>> map;
	private Node startNode;
	private Node endNode;
	public Graph()
	{
		map=new HashMap<Node,List<Edgle>>();//初始化map
	}
	public Graph(Node st,Node ed)//注意使用克隆让数据封装
	{
		map=new HashMap<Node,List<Edgle>>();//初始化map
		startNode=st;
		endNode=ed;
	}
	public Node GetStartnode()
	{
		return startNode;
	}
	public Node GetEndnode()
	{
		return endNode;
	}
	public Map<Node,List<Edgle>> GetMap()
	{
		return map;
	}
	public void SetMap(Map<Node,List<Edgle>> m)
	{
		map=m;
	}
	//增加节点函数，如果节点没有边的话传入一个空的List
	public void AddNode(Node nd,List<Edgle> li)
	{
		map.put(nd, li);
	}
	//增加边的函数，查询哪个点的边。如果不存在该点的边集，会创建一个点并把该边加入
	public void AddEdgle(Edgle ed)
	{
		Node nd=ed.GetStartnode();
		if(map.containsKey(nd)) 
			map.get(nd).add(ed);//加入边集
		else//如果不存在，新建一个点和边集并将边加入边集 
		{
			List<Edgle> newEds=new ArrayList<Edgle>();
			newEds.add(ed);
			map.put(nd, newEds);
		}
	}
	public Map<Node,List<Edgle>> getMap()
	{
		return map;
	}
	public void setNode(Node st,Node ed )//设置起始和结束节点
	{
		if(st.getNode()!=-1) startNode=st;
		if(ed.getNode()!=-1) endNode=ed;
	}
	public void TraverseGraph()//遍历图操作 就输出，不做深搜广搜
	{
		for(Node nd : map.keySet())
		{
			System.out.print(nd.getNode());
			List<Edgle> eds=map.get(nd);
			for(int i=0;i<eds.size();i++)
			{
				System.out.print(String.format("  Node%d <%d,%d,%s>",nd.getNode(),eds.get(i).GetStartnode().getNode(),eds.get(i).GetEndnode().getNode(),eds.get(i).GetMark()));
			}
			System.out.print("\n");
		}
		System.out.println(String.format("startNode:%d  endNode:%d",startNode.getNode(),endNode.getNode()));
	}
}
