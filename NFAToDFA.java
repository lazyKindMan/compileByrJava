package compiler_H_java;
import java.util.*;
//将RegularExpRecognize生成的NFA转换为DFA的类
public class NFAToDFA {
	private Graph NFA;
	private List<NodeSet> sets;
	private Graph DFA;
	public NFAToDFA(Graph m)
	{
		NFA=m;
		sets=new ArrayList<NodeSet>();
		DFA=new Graph();
	}
	public void CreateStateTable()
	{
		//将图的初始节点当做Set闭包开始计算的值
		Map<Node,List<Edgle>> map=NFA.getMap();
		Map<NodeSet,Node> setToNode=new HashMap<NodeSet,Node>();
		NodeSet startSet=new NodeSet();
		startSet.addNodeToNodes(NFA.GetStartnode());
		startSet.EClosure(map);
		startSet.SetState();
		sets.add(startSet);//加入Set序列
		Node startNode=new Node(String.valueOf(startSet.GetState()));
		DFA.setNode(startNode,new Node(-1));
		setToNode.put(startSet, startNode);
		for(NodeSet se : sets)
		{   
			//因为在循环过程中不能增加sets的内容，所以必须循环找出后再添加
			startNode=setToNode.get(se);
			Set<Node> Nodes=se.getCNodes();
			Set<String> marks=new HashSet<String>();
			for(Node nd: Nodes)
			{
				List<Edgle> eds=map.get(nd);
				for(Edgle ed : eds)
				{
					String mark=ed.GetMark();
					if(!mark.equals("null"))
					{
						marks.add(mark);
					}
				}
			}//计算出新产生的点集相邻的边的mark值
			for(String mark : marks)
			{
				NodeSet set=new NodeSet(Nodes);
				set.XClosure(mark, map);
				boolean res=false;
				for(NodeSet nse : sets)
				{
					if(set.IsEqual(nse.getCNodes())) 
							res=true;
							break;
						//循环检测是否重复
				}
				if(!res)//如果不重复
				{
					Node endNode=new Node(String.valueOf(NodeSet.getState()));
					DFA.AddEdgle(new Edgle(mark,startNode,endNode));
					sets.add(set);
					setToNode.put(set, endNode);
				}
				else//如果重复
				{
					DFA.AddEdgle(new Edgle(mark,startNode,setToNode.get(set)));
					//如果重复只增加边
				}
			}
		}
	}
	public void showDFA()
	{
		Map<Node,List<Edgle>>map=DFA.getMap();
		for(Node nd : map.keySet())
		{
			List<Edgle> eds=map.get(nd);
			for(int i=0;i<eds.size();i++)
			{
				System.out.println(String.format("Node %s <%s,%s,%s>",eds.get(i).GetStartnode().getDFANode(),eds.get(i).GetEndnode().getDFANode(),eds.get(i).GetMark()));
			}
		}
	}
}
