package com.wordladder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

import static com.wordladder.Wordladder.check;
import static com.wordladder.Wordladder.print_ladder;

@RestController
public class LadderController {
    static private Set<String> dict;

    public LadderController() throws IOException {
        dict = new TreeSet<String>();
        InputStream f = new FileInputStream("./src/Dictionary/dictionary.txt");
        InputStreamReader isr = new InputStreamReader(f,"UTF-8");
        BufferedReader bbr = new BufferedReader(isr);
        String word;
        while((word = bbr.readLine())!=null)
        {
            dict.add(word);
        }
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping("/scanning")
    public String Receiving(@RequestParam(required=false, defaultValue="null") String start,
                                 @RequestParam(required=false, defaultValue="null") String dest){
        Wordladder lad = new Wordladder();
        String res = print_ladder(check(start),check(dest),dict);
        return res;
    }
}
