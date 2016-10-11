package compiler_H_java;
//������ʽ��ʶ����
import java.util.*;
//Ϊ�˼򻯣�(?:) (?=) ��������ƥ�� $\^���μ�ƥ��
public class RegularExpRecognize {
	private Stack<String> Optr=new Stack<String>();//����һ������������ջ����
	private Stack<String> Opnd=new Stack<String>();//����һ�������ĸֵ��ջ
	private String regularExp;//������ʽ�洢
	
	public RegularExpRecognize(String Exp)
	{
		regularExp=Exp;//��������ʼ��ֵ��Ҫ�жϵ�����
	}
	private int IsFirst(String fir,String sec)//�ж����ȼ�
	{
		switch(fir)
		{
		//ջ��Ϊ�����Ų��ܺ�����ʲô����ֱ����ջ
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
			 return -1;//���ش���
		}
		
	}
	private boolean IsOptr(String ch)
	{
		if(ch.equals("(")||ch.equals("[")||ch.equals(")")||ch.equals("]")||ch.equals("+")||ch.equals("*")||ch.equals("?")||ch.equals("|"))
			return true;
		else return false;
	}
	//�쳣�������淽��
	private void ErrorRep()
	{
		System.out.println("����ı��ʽ�쳣");
		System.exit(-1);//ֹͣ����
	}
	private void Define(String ch)
	{
			if(IsOptr(ch)) 
			{
				if(Optr.isEmpty()&&ch.equals("|"))//ֻ�е�������ջΪ���ҵ�һ��������Ϊ˫Ŀ��ѹջ,
					//��ζ�Ų�����ջֻ�洢˫Ŀ��������
					Optr.push(ch);
				else if(ch.equals("(")||ch.equals("["))
					Optr.push(ch);
				else if(ch.equals(")"))//���Ϊ�����ţ����������м����
				{
					String newExp;//�����µ���ջ���ʽ
					if(!Opnd.isEmpty()&&!Optr.isEmpty())
					{	
						String optr=Optr.pop();
						while(!optr.equals("("))
							{
								if(!optr.equals("|"))
								{
									if(!Opnd.isEmpty())//�����ֵջ��Ϊ��
									{
										newExp=Opnd.pop()+optr;
										Opnd.push(newExp);//��������ջ
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
							  optr=Optr.pop();//�����ٳ�ջ��
							}
					}
					else ErrorRep();
					
				}
				else if(ch.equals("]"))//���Ϊ�������ţ����������м����
				{
					String newExp;//�����µ���ջ���ʽ
					if(!Opnd.isEmpty()&&!Optr.isEmpty())
					{	
						String optr=Optr.pop();
						while(!optr.equals("["))
							{
								if(!optr.equals("|"))
								{
									if(!Opnd.isEmpty())//�����ֵջ��Ϊ��
									{
										newExp=Opnd.pop()+optr;
										Opnd.push(newExp);//��������ջ
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
						Optr.push(ch);//ջ�����ȼ��ͣ�����ջ��Ϊ������,��ջ
						break;
					case 1://���˸�û��Ҫ���жϣ������Ժ�����µ��������
						if(ch.equals("+")||ch.equals("?")||ch.equals("*"))
						{	
							if(!Opnd.isEmpty())
							{	
								String firCh=(String) Opnd.pop();
								Opnd.push(firCh+ch);//���������Ͳ�����һ����ջ
								System.out.println(Opnd.peek());//test
							}
							else ErrorRep();
						}
							break;
					case 0://���������ȼ���ͬ�����Ϲ����ȼ�����ջ��
						//���˸�û��Ҫ���жϣ������Ժ�����µ��������
						if(ch.equals("+")||ch.equals("?")||ch.equals("*"))
						{
							if(!Opnd.isEmpty())
							{
								String firCh=(String) Opnd.pop();
								String normalOptr=(String) Optr.pop();//��ջ��Ԫ��ȡ��
								Opnd.push(firCh+normalOptr);//�ϳ�һ���ռ�ѹ����ֵջ
								System.out.println(Opnd.peek());//test
							}
							else ErrorRep();
						}
						break;
					default:ErrorRep();
				}
					
			}
			else 
				Opnd.push(ch);//��ֵ������ջ
	}
	public void RegularExtract()//������ʽ��ȡ
	{
		for(int i=0;i<regularExp.length();i++)
		{
			String subStr=regularExp.substring(i, i+1);
			if(!subStr.equals("#"))
			  Define(subStr);
			else break;
		}
		//�������
	}
}
