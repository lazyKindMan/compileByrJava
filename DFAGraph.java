package compiler_H_java;
import java.util.*;
public class DFAGraph extends Graph {
	
	private Node startNode;
	private Set<Node> endNodes;//DFA对应好几个终结状态
	
	public DFAGraph()
	{
		startNode.setNode(-1);
		endNodes=null;
		map=new HashMap<Node,List<Edgle>>();
		endNodes=new HashSet<Node>();
	}
	public DFAGraph(Node nd,Set<Node> ed)
	{
		if(nd.getNode()!=-1) startNode=nd;
		if(ed!=null) endNodes=ed;
		map=new HashMap<Node,List<Edgle>>();
		endNodes=new HashSet<Node>();
	}
	public Set<Node> GetEndnodes()
	{
		return endNodes;
	}
	public Node GetStartNode()
	{
		return startNode;
	}
}
