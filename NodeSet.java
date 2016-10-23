package compiler_H_java;

import java.util.*;

//NFA点的set集合 在计算e闭包的时候将所有状态点放入一个地方，并且标记A-Z符号
public class NodeSet {
	private Set<Node> Nodes;//Node集合
	private static char state='A';//状态符号
	private Set<Node> CNodes;//闭包集合
	private char thisState;
	public static char getState()
	{
		state++;
		return (char) (state-1);
	}
	public NodeSet(Set<Node> nds)
	{
		Nodes=nds;
	    CNodes=new HashSet<Node>();//初始化闭包集合
	}
	public NodeSet()
	{
		Nodes=new HashSet<Node>();
		CNodes=new HashSet<Node>();//初始化闭包集合
	}
	public void addNodeToNodes(Node nd)
	{
		Nodes.add(nd);
	}
	public void addNodeToCNodes(Node nd)
	{
		CNodes.add(nd);
	}
	public void SetState()
	{
		thisState=state;
		state++;
	}
	public void SetState(char st)
	{
		thisState=st;
	}
	public Set<Node> getCNodes()
	{
		return CNodes;
	}
	public Set<Node> getNodes()
	{
		return Nodes;
	}
	public char GetState()
	{
		return thisState;
	}
	private void EClosure(Node nd,Map<Node,List<Edgle>> map)//递归重载函数，只计算传入的Node
	{
		CNodes.add(nd);
		List<Edgle> eds=map.get(nd);
		if(eds!=null)
		for(int i=0;i<eds.size();i++)
		{
			if(eds.get(i).GetMark().equals("null"))
			{
				EClosure(eds.get(i).GetEndnode(),map);
			}
		}
		return;
	}
	public void EClosure(Map<Node,List<Edgle>> map)//计算当前 Nodes的闭包 mark为要遍历的边
	{
		for(Node nd : Nodes)
		{
			 CNodes.add(nd);//加入自身
			List<Edgle> eds=map.get(nd);
			for(int i=0;i<eds.size();i++)
			{
				if(eds.get(i).GetMark().equals("null"))
					EClosure(eds.get(i).GetEndnode(),map);
			}
		}
	}
	private void EClosure(Map<Node,List<Edgle>> map,Set<Node> temNodes)//计算当前 Nodes的闭包 mark为要遍历的边
	{
		for(Node nd : temNodes)
		{
			 CNodes.add(nd);//加入自身
			List<Edgle> eds=map.get(nd);
			for(int i=0;i<eds.size();i++)
			{
				if(eds.get(i).GetMark().equals("null"))
					EClosure(eds.get(i).GetEndnode(),map);
			}
		}
	}
	public void XClosure(String mark,Map<Node,List<Edgle>> map)
	{
		Set<Node> temNodes=new HashSet<Node>();
		
		for(Node nd : Nodes)
		{
			List<Edgle> eds=map.get(nd);
			for(int i=0;i<eds.size();i++)
			{
				if(eds.get(i).GetMark().equals("null"))
					XClosure(mark,eds.get(i).GetEndnode(),map,temNodes);
				else if(eds.get(i).GetMark().equals(mark))
				{
					temNodes.add(eds.get(i).GetEndnode());
					XClosure(mark,eds.get(i).GetEndnode(),map,temNodes);
				}
			}
		}
		EClosure(map,temNodes);//生成move后的e闭包
	}
	public void XClosure(String mark,Node nd,Map<Node,List<Edgle>> map,Set<Node> temNodes)
	{
		List<Edgle> eds=map.get(nd);
		for(int i=0;i<eds.size();i++)
		{
			if(eds.get(i).equals("null"))
				XClosure(mark,eds.get(i).GetEndnode(),map,temNodes);
			else if(eds.get(i).equals(mark))
			{
				temNodes.add(eds.get(i).GetEndnode());
				XClosure(mark,eds.get(i).GetEndnode(),map,temNodes);
			}
		}
		return;
	}
	public boolean IsEqual(Set<Node> se)//对比CNode集是否相等
	{
		if(se.size()!=CNodes.size())
		{
			return false;
		}
		Iterator<Node> ite1=se.iterator();
		Iterator<Node> ite2=CNodes.iterator();
		while(ite1.hasNext())
		{
			if(ite1.next()!=ite2.next())
			{
				return false;
			}
		}
		return true;
	}
}
