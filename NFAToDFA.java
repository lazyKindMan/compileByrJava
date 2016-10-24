package compiler_H_java;
import java.util.*;
//将RegularExpRecognize生成的NFA转换为DFA的类
public class NFAToDFA {
	DFAGraph dfa;
	Queue<GetherNd> GNDQu;//一个node集合队列
	ArrayList<GetherNd> listGnd;//node集合列表
	Graph nfa;//
	Map<Node,List<Edgle>> map;
	public NFAToDFA(Graph m)
	{
		nfa=m;
		map=nfa.getMap();
		listGnd=new ArrayList<GetherNd>();
		GNDQu=new LinkedList<GetherNd>();
		CreateDFA();
	}
	private GetherNd EClosure(Node nd)//将nfa的一个点的e闭包放入一个Gether集合中
	{
		GetherNd gnd=new GetherNd();
		List<Edgle> eds=map.get(nd);
		ArrayList<Node> list=gnd.getNodeList();
		list.add(nd);//将自身加入
		for(Edgle ed :eds)
		{
			if(ed.GetMark().equals("null"))
			{
				if(!list.contains(ed.GetEndnode()))
				{
					EClosure(ed.GetEndnode(),gnd);//进入递归
				}
			}
		}
		return gnd;
	}
	private void EClosure(Node nd,GetherNd gnd)
	{
		List<Edgle> eds=map.get(nd);
		ArrayList<Node> list=gnd.getNodeList();
		list.add(nd);//将自身加入
		if(eds!=null)
		for(Edgle ed :eds)
		{
			if(ed.GetMark().equals("null"))
			{
				if(!list.contains(ed.GetEndnode()))
				{
					EClosure(ed.GetEndnode(),gnd);//进入递归
				}
			}
		}
	}
	private void XClosure(GetherNd gnd)
	{
		Set<String> marks=new HashSet<String>();
		for(Node nd : gnd.getNodeList())
		{
			if(map.containsKey(nd))
			{
				List<Edgle> eds=map.get(nd);
				for(Edgle ed :eds)
				{
					if(!ed.GetMark().equals("null"))
						marks.add(ed.GetMark());
				}
			}
		}
		for(Node nd : gnd.getNodeList())
		{
			if(map.containsKey(nd))
			{
				List<Edgle> eds=map.get(nd);
				for(String mark : marks)
				{
					GetherNd newNd=new GetherNd();
					for(Edgle ed : eds)
					{
						if(ed.GetMark().equals(mark))
						{
							EClosure(ed.GetEndnode(),newNd);//计算闭包
						}
					}
					int res=0;
					for(GetherNd tgnd : listGnd)
					{
						if(tgnd.IsEqual(newNd))//如果相等，不是新状态,只对新的状态进行扫描，不加入队列
						{
							dfa.AddEdgle(new Edgle(mark,gnd.getNode(),tgnd.getNode()));
							res=1;
							break;
						}
					}
					if(res==0&&newNd.getNodeList().size()!=0)//如果找不到相等的状态,且list不为空
					{
						newNd.setNode(new Node(Node.getSum()));//创建新的dfa节点
						dfa.AddEdgle(new Edgle(mark,gnd.getNode(),newNd.getNode()));
						//入队
						GNDQu.add(newNd);
						//加入状态集合中
						listGnd.add(newNd);
					}
				}
			}
		}
	}
	private void CreateDFA()
	{
		//建立第一个node集合
		Node startNode=nfa.GetStartnode();
		GetherNd gnd=EClosure(startNode);
		gnd.setNode(new Node(Node.getSum()));//获取新的对应DFA节点
		listGnd.add(gnd);//加入状态集合
		GNDQu.add(gnd);//加入队列
		dfa=new DFAGraph(gnd.getNode(),null);
		while(!GNDQu.isEmpty())
			XClosure(GNDQu.poll());//出队未处理的元素
		//处理dfa图的可接受状态节点
		for(GetherNd tgnd : listGnd)
		{
			for(Node nd : tgnd.getNodeList())
			{
				if(nd==nfa.GetEndnode())
					{
						dfa.GetEndnodes().add(tgnd.getNode());
						break;
						//如果有终止节点，记录在终止状态中
					}
			}
		}
	}
	public void showDFA()
	{
		 Map<Node,List<Edgle>> dfaMap=dfa.getMap();
		 for(Node nd : dfaMap.keySet())
		 {
			 List<Edgle> eds=dfaMap.get(nd);
			 if(eds!=null)
			 for(Edgle ed : eds)
			 {
				 System.out.println(String.format("Node %d <%d,%d,%s>",nd.getNode(),ed.GetStartnode().getNode(),ed.GetEndnode().getNode(),ed.GetMark()));
			 }
		 }
		 System.out.println(String.format("startNode %d", dfa.GetStartNode().getNode()));
		 System.out.print("endNode:");
		 for(Node nd : dfa.GetEndnodes())
			 System.out.print(String.format("  %d",nd.getNode()));
	}
}
