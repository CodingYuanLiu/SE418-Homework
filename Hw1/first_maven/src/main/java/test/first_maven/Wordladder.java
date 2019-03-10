package test.first_maven;

import java.util.*;
import java.io.*;
public class Wordladder {
	public static Stack<String> makeupladder(String start,String destination,Set<String> dict)
	{
		Set<String> useddict = new TreeSet<String>();
		useddict.add(destination);
		
		Stack<String> ladder = new Stack<String>();
		Stack<String> temp = new Stack<String>();

		String top;
		Queue<Stack<String>> ladderqueue = new LinkedList<Stack<String>>();
		ladder.add(destination);
		ladderqueue.offer(ladder);
		while(!ladderqueue.isEmpty())
		{
			ladder = ladderqueue.peek();
			ladderqueue.poll();
			
			for(int i = 0;i<destination.length();i++)
			{
				for(char c = 'a';c<='z';c++)
				{
					top = new String(ladder.peek());
					temp = (Stack<String>)ladder.clone();
					if(top.charAt(i) == c)
					{
						continue;
					}
					top = top.substring(0,i) + c + top.substring(i+1);
					if(top.equals(start))
					{
						ladder.add(top);
						return ladder;
					}
					else if(!useddict.contains(top) && dict.contains(top)) {
						useddict.add(top);
						temp.push(top);
						ladderqueue.offer(temp);
					}
				}
			}
		}
		Stack<String> noladder = new Stack<String>();
		noladder.push(new String("Noladder"));
	 	return noladder;
	}
 
	public static void print_ladder(String start,String destination,Set<String> dict)
	{
		Stack<String> result = makeupladder(start,destination,dict);
		while(!result.isEmpty())
		{
			String word = result.peek();
			result.pop();
			if(result.isEmpty())
			{
				System.out.println(word);
			}
			else
			{
				System.out.print(word+"->");
			}
		}

	}
	
	public static String check(String word)
	{
		word = word.toLowerCase();
		for(int i=0;i<word.length();i++)
		{
			if(word.charAt(i)>'z' || word.charAt(i)<'a')
			{
				word = new String("Error");
			}
		}
		return word;
	}
	
    public static void main(String args[]) throws IOException {
    	String start, dest;
    	Set<String> dict = new TreeSet<String>();
    	InputStream f = new FileInputStream("./src/dictionary/dictionary.txt");
    	InputStreamReader isr = new InputStreamReader(f,"UTF-8");
    	BufferedReader bbr = new BufferedReader(isr);
    	String word;
    	while((word = bbr.readLine())!=null)
    	{
    		dict.add(word);
    	}
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to wordladder!");
    	while(true) {
    		System.out.println("Start word (enter to quit):");
        	start = check(br.readLine());
        	if(start.equals(""))
        		break;
        	if(start.equals("Error"))
        	{
        		System.out.println("Error: Wrong start word format.");
        		break;
        	}
        	System.out.println("Destination word:");
        	dest = check(br.readLine());
        	if(dest.equals(""))
        		break;
        	if(dest.equals("Error"))
        	{
        		System.out.println("Error: Wrong destination word format.");
        		break;
        	}
        	print_ladder(start,dest,dict);
    	}
		System.out.println("Bye Bye");
		bbr.close();
    }
}
