package compiler_H_java;
import java.util.*;
public class Compiler {
	public static void main(String[] args)
	{
//		String greeting="���";
//		int n=greeting.length();
//		int cpCount=greeting.codePointCount(0,greeting.length());
//		 
//		System.out.println(n);
//		System.out.println(cpCount);
//		Scanner in= new Scanner(System.in);
//		String name=in.nextLine();
//		Stack <Character>stack=new Stack<Character>();
//		stack.push('1');
//		System.out.println(stack.pop());
//		char[] passwd=cons.readPassword();
		System.out.println("请输入你的正则表达式，以#号结尾");
		Scanner in=new Scanner(System.in);
		String exp=in.next();
		RegularExpRecognize rec=new RegularExpRecognize(exp);
		NFAToDFA change=new NFAToDFA(rec.getNFA());
		change.showDFA();
	}
}
