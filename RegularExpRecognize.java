package compiler_H_java;
//������ʽ��ʶ����
import java.util.*;
//Ϊ�˼򻯣�(?:) (?=) ��������ƥ�� $\^���μ�ƥ��
public class RegularExpRecognize {
	private Stack<Character> Optr=new Stack<Character>();//����һ������������ջ����
	private Stack<Character> Opnd=new Stack<Character>();//����һ�������ĸֵ��ջ
	private String regluarExp;//������ʽ�洢
	
	public RegularExpRecognize(String Exp)
	{
		regluarExp=Exp;//��������ʼ��ֵ��Ҫ�жϵ�����
	}
	private int IsFirst(char fir,char sec)//�ж����ȼ�
	{
		switch(fir)
		{
		case '(':
			{	
				if(sec==')') return 0;
				else if(sec==']') return -2;
				else return 1;//�������ȼ�Ϊ��ߣ�����ջ�����ȼ���
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
			if(sec=='|') return -3;//����������淶
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
