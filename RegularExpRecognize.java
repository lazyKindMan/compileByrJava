package compiler_H_java;
//正则表达式的识别类
import java.util.*;
//为了简化，(?:) (?=) 都不参与匹配 $\^不参加匹配
public class RegularExpRecognize {
	private Stack<String> Optr=new Stack<String>();//建立一个存放正则符的栈对象
	private Stack<String> Opnd=new Stack<String>();//建立一个存放字母值得栈
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
		case "(":return -1;
		case "[":return -1;	
		case "?":
			if(sec.equals("+")||sec.equals("*")) return 0;
			else if(sec.equals("(")||sec.equals("[")) return -1;
			else return 1;
		case "+":
			if(sec.equals("?")||sec.equals("*")) return 0;
			else if(sec.equals("(")||sec.equals("[")) return -1;
			else return 1;
		case "*":
			if(sec.equals("+")||sec.equals("?")) return 0;
			else if(sec.equals("(")||sec.equals("[")) return -1;
			else return 1;
		default :
			if(sec.equals("|")) return -3;
			 return -1;//返回错误
		}
		
	}
	private boolean IsOptr(String ch)
	{
		if(ch.equals("(")||ch.equals("[")||ch.equals(")")||ch.equals("]")||ch.equals("+")||ch.equals("*")||ch.equals("?")||ch.equals("|"))
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
				if(Optr.isEmpty()&&ch.equals("|"))//只有当操作符栈为空且第一个操作符为双目才压栈,
					//意味着操作符栈只存储双目符和括号
					Optr.push(ch);
				else if(ch.equals("(")||ch.equals("["))
					Optr.push(ch);
				else if(ch.equals(")"))//如果为右括号，弹出所又中间操作
				{
					String newExp;//生成新的入栈表达式
					if(!Opnd.isEmpty()&&!Optr.isEmpty())
					{	
						String optr=Optr.pop();
						while(!optr.equals("("))
							{
								if(!optr.equals("|"))
								{
									if(!Opnd.isEmpty())//如果数值栈不为空
									{
										newExp=Opnd.pop()+optr;
										Opnd.push(newExp);//操作完入栈
										System.out.println(Opnd.peek());//test
									}
									else 
									{
										ErrorRep();
									}
								}
								else
								{
									if(!Opnd.isEmpty())
									{
										newExp=optr+Opnd.pop();
										if(!Opnd.isEmpty())
										{
											newExp=Opnd.pop()+newExp;
											Opnd.push(newExp);
											System.out.println(Opnd.peek());//test
										}
										else ErrorRep();
									}
									else ErrorRep();
								}
							  optr=Optr.pop();//忘记再出栈了
							}
					}
					else ErrorRep();
					
				}
				else if(ch.equals("]"))//如果为右中括号，弹出所又中间操作
				{
					String newExp;//生成新的入栈表达式
					if(!Opnd.isEmpty()&&!Optr.isEmpty())
					{	
						String optr=Optr.pop();
						while(!optr.equals("["))
							{
								if(!optr.equals("|"))
								{
									if(!Opnd.isEmpty())//如果数值栈不为空
									{
										newExp=Opnd.pop()+optr;
										Opnd.push(newExp);//操作完入栈
										System.out.println(Opnd.peek());//test
									}
									else 
									{
										ErrorRep();
									}
								}
								else
								{
									if(!Opnd.isEmpty())
									{
										newExp=optr+Opnd.pop();
										if(!Opnd.isEmpty())
										{
											newExp=Opnd.pop()+newExp;
											Opnd.push(newExp);
											System.out.println(Opnd.peek());//test
										}
										else ErrorRep();
									}
									else ErrorRep();
								}
							}
					}
					else ErrorRep();
					
				}
				else switch(IsFirst(Optr.peek(),ch))
				{
					case -1:
						Optr.push(ch);//栈顶优先级低，或者栈顶为左括号,入栈
						break;
					case 1://加了个没必要的判断，方便以后添加新的正则规则
						if(ch.equals("+")||ch.equals("?")||ch.equals("*"))
						{	
							if(!Opnd.isEmpty())
							{	
								String firCh=(String) Opnd.pop();
								Opnd.push(firCh+ch);//将操作数和操作符一起入栈
								System.out.println(Opnd.peek());//test
							}
							else ErrorRep();
						}
							break;
					case 0://操作符优先级相同，左结合规则先计算入栈的
						//加了个没必要的判断，方便以后添加新的正则规则
						if(ch.equals("+")||ch.equals("?")||ch.equals("*"))
						{
							if(!Opnd.isEmpty())
							{
								String firCh=(String) Opnd.pop();
								String normalOptr=(String) Optr.pop();//将栈顶元素取出
								Opnd.push(firCh+normalOptr);//合成一个空间压入数值栈
								System.out.println(Opnd.peek());//test
							}
							else ErrorRep();
						}
						break;
					default:ErrorRep();
				}
					
			}
			else 
				Opnd.push(ch);//数值符号入栈
	}
	public void RegularExtract()//正则表达式提取
	{
		for(int i=0;i<regularExp.length();i++)
		{
			String subStr=regularExp.substring(i, i+1);
			if(!subStr.equals("#"))
			  Define(subStr);
			else break;
		}
		//如果符号
	}
}
