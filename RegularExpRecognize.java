package compiler_H_java;
//正则表达式的识别类
import java.util.*;
//为了简化，(?:) (?=) 都不参与匹配 $\^不参加匹配
public class RegularExpRecognize {
	private Stack<String> Optr=new Stack<String>();//建立一个存放正则符的栈对象
	private Stack<String> RPN=new Stack<String>();//存放逆波兰式
	private Stack<Graph> GStack=new Stack<Graph>();//存放图的一个栈
	private String regularExp;//正则表达式存储
	
	public RegularExpRecognize(String Exp)
	{
		regularExp=Exp;//构造器初始赋值给要判断的正则
	}
	private int IsFirst(String fir,String sec)//判断优先级
	{
		switch(fir)
		{
		//栈顶为左括号不管后面是什么符号直接入栈
		case "(":return 1;	
		case "|":if(sec.equals("|")) return 0;
				 else if(sec.equals("&")) return 1; 
				 else return -1; 
		case "&": return 0;
		default:return -1;
		}
	}
	private boolean IsOptr(String ch)
	{
		if(ch.equals("(")||ch.equals(")")||ch.equals("+")||ch.equals("*")||ch.equals("?")||ch.equals("|")||ch.equals("&"))
			return true;
		else return false;
	}
	//异常错误处理报告方法
	private void ErrorRep()
	{
		System.out.println("输入的表达式异常");
		System.exit(-1);//停止程序
	}
	private void Define(String ch)
	{
		if(IsOptr(ch))
		{
			if(ch.equals("("))
				Optr.push(ch);
			else if(ch.equals("|")||ch.equals("&"))
			{
				if(Optr.isEmpty()) Optr.push(ch);//栈为空直接入栈
				else switch(IsFirst(Optr.peek(),ch))
				{
					//1为直接入栈
					case 1: Optr.push(ch);break;
					case 0: 
					{
						//循环到栈为空或者优先级比栈顶高
						while(!Optr.isEmpty())
						{
							if(IsFirst(Optr.peek(),ch)==1) break;
							RPN.push(Optr.pop());
						}
						Optr.push(ch);
						break;
					}
					case -1:ErrorRep();break;
					default:ErrorRep();break;
				}
			}
			else if(ch.equals(")"))
			{
				if(Optr.isEmpty()) ErrorRep();
				while(!Optr.peek().equals("("))
				{
					RPN.push(Optr.pop());
					if(Optr.isEmpty()) ErrorRep();
				}
				Optr.pop();//取出左括号
			}
			else if(ch.equals("+")||ch.equals("?")||ch.equals("*"))
			{//如果是单目操作符
				if(RPN.isEmpty()||RPN.peek().equals("("))
					ErrorRep();
				else RPN.push(ch);//单操作符直接入栈
			}
			else ErrorRep();
		}
		else RPN.push(ch);
	}
	private void GreateSingleMap(String sub)//创建单字符图
	{
		Node startNode=new Node(Node.getSum());
		Node endNode=new Node(Node.getSum());
		List<Edgle> eds=new ArrayList<Edgle>();
		Edgle ed=new Edgle(sub,startNode,endNode);
		Graph temGraph=new Graph(startNode,endNode);
		eds.add(ed);//加入边集
		temGraph.AddNode(startNode, eds);
		GStack.push(temGraph);//将生成的图入栈
	}
	private void GreateAddMap()//创建一个加号性质的图
	{
		if(!GStack.isEmpty())
		{
			Graph preGraph=GStack.pop();//将前面的图栈取出
			//增加一条回溯边
			preGraph.AddEdgle(new Edgle("null",preGraph.GetEndnode(),preGraph.GetStartnode()));
			GStack.push(preGraph);//入栈
		}
		else ErrorRep();
	}
	private void GreateQueMap()//创建一个问号性质的图
	{
		if(!GStack.isEmpty())
		{
			Graph preGraph=GStack.pop();
			//增加一条直达边
			preGraph.AddEdgle(new Edgle("null",preGraph.GetStartnode(),preGraph.GetEndnode()));
			GStack.push(preGraph);//将新生成的栈入栈		
		}
	}
	private void GreateAsteriskMap()//创建星号map 闭包增加两条边，起始到终点，终点到起始
	{
		if(!GStack.isEmpty())
		{
			Graph preGraph=GStack.pop();
//			增加一条直达边
			Node startNode=preGraph.GetStartnode();
			Node endNode=preGraph.GetEndnode();
			preGraph.AddEdgle(new Edgle("null",startNode,endNode));
			Node newStart=new Node(Node.getSum());
			Node newEnd=new Node(Node.getSum());//创建两个新的起始结束节点
			preGraph.AddEdgle(new Edgle("null",newStart,startNode));
			preGraph.AddEdgle(new Edgle("null",endNode,newEnd));
			preGraph.AddEdgle(new Edgle("null",newEnd,newStart));
			preGraph.setNode(newStart, newEnd);//重新设置开始结束节点
			GStack.push(preGraph);//将新生成的栈入栈		 
		}
		else ErrorRep();
	}
	private void GreateAndMap()
	{
		if(!GStack.isEmpty())
		{
			Graph frontGraph=GStack.pop();
			if(!GStack.isEmpty())
			{
				Graph rearGraph=GStack.pop();
				//将后取的图的end节点与先取图的开始节点连接
				Graph newGraph=new Graph(rearGraph.GetStartnode(),frontGraph.GetEndnode());
				for(Node nd : rearGraph.getMap().keySet())
					for(int i=0;i<rearGraph.getMap().get(nd).size();i++)
						newGraph.AddEdgle(rearGraph.getMap().get(nd).get(i));
				for(Node nd : frontGraph.getMap().keySet())
					for(int i=0;i<frontGraph.getMap().get(nd).size();i++)
						newGraph.AddEdgle(frontGraph.getMap().get(nd).get(i));
				newGraph.AddEdgle(new Edgle("null",rearGraph.GetEndnode(),frontGraph.GetStartnode()));
				GStack.push(newGraph);
			}
			else ErrorRep();
		}
		else ErrorRep();
	}
	private void GreateOrMap()
	{
		if(!GStack.isEmpty())
		{
			Graph frontGraph=GStack.pop();
			if(!GStack.isEmpty())
			{
				Graph rearGraph=GStack.pop();
				//新建立一个起始点与一个结束点
				Graph newGraph=new Graph(new Node(Node.getSum()),new Node(Node.getSum()));
				//起始点将两个图的起始点连接
				newGraph.AddEdgle(new Edgle("null",newGraph.GetStartnode(),rearGraph.GetStartnode()));
				newGraph.AddEdgle(new Edgle("null",newGraph.GetStartnode(),frontGraph.GetStartnode()));
				for(Node nd : rearGraph.getMap().keySet())
					for(int i=0;i<rearGraph.getMap().get(nd).size();i++)
						newGraph.AddEdgle(rearGraph.getMap().get(nd).get(i));
				for(Node nd : frontGraph.getMap().keySet())
					for(int i=0;i<frontGraph.getMap().get(nd).size();i++)
						newGraph.AddEdgle(frontGraph.getMap().get(nd).get(i));
				newGraph.AddEdgle(new Edgle("null",rearGraph.GetEndnode(),newGraph.GetEndnode()));
				newGraph.AddEdgle(new Edgle("null",frontGraph.GetEndnode(),newGraph.GetEndnode()));
				GStack.push(newGraph);
			}
			else ErrorRep();
		}
		else ErrorRep();
	}
	private void RegularExtract()//正则表达式提取,图的创建
	{
		//String Opreation;//返回的字符串，
		for(int i=0;i<regularExp.length();i++)
		{
			String ch=regularExp.substring(i, i+1);
			if(!ch.equals("#"))
			{
				Define(ch);
			}
		}
		while(!Optr.isEmpty())
		 RPN.push(Optr.pop());
		//至此逆波兰式已经生成
		String newRPN="";//将栈中的逆波兰式存入改变量
		while(!RPN.isEmpty())
		{
			newRPN=RPN.pop()+newRPN;
		}
		//System.out.println(newRPN);
		for(int i=0;i<newRPN.length();i++)
		{
			String sub=newRPN.substring(i, i+1);
			if(IsOptr(sub))
			{
				switch(sub)
				{
					case "+":GreateAddMap();break;
					case "?":GreateQueMap();break;
					case "*":GreateAsteriskMap();break;
					case "&":GreateAndMap();break;
					case "|":GreateOrMap();break;
					default:ErrorRep();
				}
			}
			else
			{
				GreateSingleMap(sub);
			}
		}
	}
	public void TestOut()//test
	{
		if(!GStack.isEmpty())
		{
			Graph temGraph=GStack.pop();
			temGraph.TraverseGraph();
		}
	}
	public Graph getNFA()
	{
		RegularExtract();
		return GStack.pop();
	}
}
