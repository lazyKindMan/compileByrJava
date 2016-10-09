package compiler_H_java;
//正则表达式的识别类
import java.util.*;
//为了简化，(?:) (?=) 都不参与匹配 $\^不参加匹配
public class RegularExpRecognize {
	private Stack<Character> Optr=new Stack<Character>();//建立一个存放正则符的栈对象
	private Stack<Character> Opnd=new Stack<Character>();//建立一个存放字母值得栈
	private String regluarExp;//正则表达式存储
	
	public RegularExpRecognize(String Exp)
	{
		regluarExp=Exp;//构造器初始赋值给要判断的正则
	}
	private int IsFirst(char fir,char sec)//判断优先级
	{
		switch(fir)
		{
		case '(':
			{	
				if(sec==')') return 0;
				else if(sec==']') return -2;
				else return 1;//括号优先级为最高，返回栈顶优先级高
			}
		case '[':
			if(sec==']') return 0;
			else if(sec==')') return -2;
			else return 1;
		case '?':
			if(sec=='+'||sec=='*') return 0;
			else if(sec=='('||sec=='[') return -1;
			else if(sec==')'||sec==']') return -2;
			else return 1;
		case '+':
			if(sec=='?'||sec=='*') return 0;
			else if(sec=='('||sec=='[') return -1;
			else if(sec==')'||sec==']') return -2;
			else return 1;
		case '*':
			if(sec=='+'||sec=='?') return 0;
			else if(sec=='('||sec=='[') return -1;
			else if(sec==')'||sec==']') return -2;
			else return 1;
		default :
			if(sec=='|') return -3;//不符合正则规范
			else return -1;
		}
		
	}
	private boolean IsOptr(char ch)
	{
		if(ch=='('||ch==')'||ch=='['||ch==']'||ch=='+'||ch=='*'||ch=='?'||ch=='|')
			return true;
		else return false;
	}
	private void Define(char ch)
	{
		
			if(IsOptr(ch)) 
			{
				
			}
			else 
				Opnd.push(ch);
	}
	
}
