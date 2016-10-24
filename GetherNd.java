package compiler_H_java;

import java.util.ArrayList;

//Node集合类，同时集合对应的DFA点
public class GetherNd {
	private ArrayList<Node> nodeList;
	private Node node;//对应得DFA点
	public GetherNd()
	{
		nodeList=new ArrayList<Node>();
		node=new Node(-1);
	}
	public GetherNd(ArrayList<Node> nl,Node nd)
	{
		nodeList=nl;
		node=nd;
	}
	public void setNode(Node nd)
	{
		node=nd;
	}
	public void setList(ArrayList<Node> li)
	{
		nodeList=li;
	}
	public void addList(Node nd)
	{
		nodeList.add(nd);
	}
	public ArrayList<Node> getNodeList()
	{
		return nodeList;
	}
	public Node getNode()
	{
		return node;
	}
	public boolean IsEqual(GetherNd comp)
	{
		if(nodeList.size()!=comp.getNodeList().size())
			return false;
		for(Node nd : nodeList)
		{
			int res=0;
			for(Node cnd : comp.getNodeList())
			{
				if(nd==cnd) 
				{
						res=1;
						break;
				}
			}
			if(res==0)
				return false;
		}
		return true;
	}
}
