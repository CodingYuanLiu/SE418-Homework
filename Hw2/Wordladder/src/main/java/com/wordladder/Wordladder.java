package com.wordladder;

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

    public static String print_ladder(String start,String destination,Set<String> dict)
    {
        Stack<String> result = makeupladder(start,destination,dict);
        String res="";
        while(!result.isEmpty())
        {
            String word = result.peek();
            result.pop();
            if(result.isEmpty())
            {
                res = res + word;
            }
            else
            {
                res = res + word + "->";
            }
        }
        return res;

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

}
