package compiler_H_java;
//正则表达式的识别类
import java.util.*;
//为了简化，(?:) (?=) 都不参与匹配 $\^不参加匹配
public class RegularExpRecognize {
	private Stack<String> Optr=new Stack<String>();//建立一个存放正则符的栈对象
	private Stack<String> RPN=new Stack<String>();//存放逆波兰式
	private Stack<String> Opnd=new Stack<String>();//存放识别的正则式栈
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
				 else return -1; 
//		case "?":
//			if(sec.equals("+")||sec.equals("*")) return 0;
//			else if(sec.equals("(")||sec.equals("[")) return -1;
//			else return 1;
//		case "+":
//			if(sec.equals("?")||sec.equals("*")) return 0;
//			else if(sec.equals("(")||sec.equals("[")) return -1;
//			else return 1;
//		case "*":
//			if(sec.equals("+")||sec.equals("?")) return 0;
//			else if(sec.equals("(")||sec.equals("[")) return -1;
//			else return 1;
		default:return -1;
		}
	}
	private boolean IsOptr(String ch)
	{
		if(ch.equals("(")||ch.equals(")")||ch.equals("+")||ch.equals("*")||ch.equals("?")||ch.equals("|"))
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
			else if(ch.equals("|"))
			{
				if(Optr.isEmpty()) Optr.push(ch);//栈为空直接入栈
				else switch(IsFirst(Optr.peek(),ch))
				{
					//1为直接入栈,0为直接双目运算
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
				if(RPN.isEmpty()||RPN.peek().equals("(")||RPN.peek().equals("|"))
					ErrorRep();
				else RPN.push(ch);//单操作符直接入栈
			}
			else ErrorRep();
		}
		else RPN.push(ch);
	}
	public void RegularExtract()//正则表达式提取,图的创建
	{
		String Opreation;//返回的字符串，
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
		System.out.println(newRPN);
		for(int i=0;i<newRPN.length();i++)
		{
			String tem=newRPN.substring(i, i+1);
			if(IsOptr(tem)&&!tem.equals(")"))
			{
				if(tem.equals("|"))
				{
					if(!Opnd.isEmpty())
					{
						String temp1=Opnd.pop();
						if(!Opnd.isEmpty())
							{
								String temp2=Opnd.pop();
								Opnd.push(temp2+tem+temp1);
//								System.out.println(temp2+tem+temp1);//test
							}
						else ErrorRep();
					}
					else ErrorRep();
				}
				else if(tem.equals("+")||tem.equals("?")||tem.equals("*"))
				{
					if(!Opnd.isEmpty())
					{
						Opnd.push(Opnd.pop()+tem);
//						System.out.println(Opnd.peek());//test
					}
					else ErrorRep();
				}
				else ErrorRep();
			}
			else if(!tem.equals(")"))//如果不是操作符且不是括号，直接存入栈
			{
				Opnd.push(tem);
//				System.out.println(Opnd.peek());//test
			}
			else ErrorRep();
		}
	}
	public void TestOut()//test
	{
		while(!Opnd.isEmpty())
		System.out.print(Opnd.pop());
	}
}
