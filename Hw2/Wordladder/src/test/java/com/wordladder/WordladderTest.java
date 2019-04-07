package com.wordladder;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;
public class WordladderTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testMakeupladder() throws IOException {
        String[][] result = {
                {"code","cote","cate","date","data"},
                {"data","date","cate","cade","code"},
                {"gap","bap","gap"},
                {"start","slart","blart","blert","bleat","bleak","break","bream","dream"},
                {"Noladder"},
                {"Noladder"},
                {"Noladder"},
                {"Noladder"},
                {"Noladder"},
                {"Noladder"}
        };
        String[][] tests = {
                {"code","data"},
                {"data","code"},
                {"gap","gap"},
                {"start","dream"},
                {"zz","vv"},
                {"destination","aaaaaaaaaaa"},
                {"aaaaaaaa","bbbbbbbb"},
                {"iquaman","ironman"},
                {"aaaaaa","bbbbbb"},
                {"bbbbbb","aaaaaa"}
        };

        Wordladder test = new Wordladder();
        Set<String> dict = new TreeSet<String>();
        InputStream f = new FileInputStream("./src/dictionary/dictionary.txt");
        InputStreamReader isr = new InputStreamReader(f,"UTF-8");
        BufferedReader bbr = new BufferedReader(isr);
        String word;
        while((word = bbr.readLine())!=null)
        {
            dict.add(word);
        }

        Stack<String> ret;

        for(int i=0;i<result.length;i++)
        {
            ret = test.makeupladder(tests[i][0], tests[i][1], dict);
            for(int j=0;j<result[i].length;j++)
            {
                assertEquals(ret.peek(),result[i][j]);
                ret.pop();
            }
        }

        bbr.close();
    }

    @Test
    public void testCheck() {
        Wordladder test = new Wordladder();
        String[] testString= {"123","abc","Abc"};
        String[] result = {"Error","abc","abc"};
        for(int i = 0;i<3;i++)
        {
            assertEquals(result[i],test.check(testString[i]));
        }
    }
}
