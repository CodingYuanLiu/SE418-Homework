package com.wordladder;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.alibaba.fastjson.JSONObject;


import static com.wordladder.Wordladder.check;
import static com.wordladder.Wordladder.print_ladder;

@RestController
@PreAuthorize("hasRole('USER')")
public class LadderController {
    static private Set<String> dict;

    public LadderController() throws IOException {
        dict = new TreeSet<String>();
        InputStream f = new ClassPathResource("static/Dictionary/dictionary.txt").getInputStream();
        InputStreamReader isr = new InputStreamReader(f,"UTF-8");
        BufferedReader bbr = new BufferedReader(isr);
        String word;
        while((word = bbr.readLine())!=null)
        {
            dict.add(word);
        }
    }
    @RequestMapping("/scanning")
    @CrossOrigin(origins = "http://localhost:8081")
    public JSONObject Receiving(@RequestParam(required=false, defaultValue="null") String start,
                                 @RequestParam(required=false, defaultValue="null") String dest,
                            @RequestHeader(required = true, defaultValue = "null") String Authorization) throws Exception{
        JSONObject response=new JSONObject();

        if(Authorization.equals("null")) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
            response.put("timestamp",date);
            response.put("status", "401");
            response.put("error", "Unauthorized");
            response.put("message", "Unauthorized");
            response.put("path", "scanning");
            return response;
        }
        else {
            Wordladder lad = new Wordladder();
            String res = print_ladder(check(start),check(dest),dict);

            Base64.Decoder decoder = Base64.getDecoder();
            String username_and_password = new String(decoder.decode(Authorization.substring(6)));
            String username = username_and_password.substring(0,username_and_password.indexOf(":"));
            String password = username_and_password.substring(username_and_password.indexOf(":")+1);


            String postURL=new String("http://wordladderlogin:8099");
            PostMethod postMethod = new PostMethod(postURL);
            postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=urf-8");
            NameValuePair[] data={
                    new NameValuePair("username",username),
                    new NameValuePair("password",password)
            };
            postMethod.setRequestBody(data);
            org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
            int login_response_int = httpClient.executeMethod(postMethod); // 执行POST方法
            JSONObject login_response = JSON.parseObject(postMethod.getResponseBodyAsString()) ;

            if(login_response.getIntValue("status") == 401){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
                response.put("timestamp",date);
                response.put("status", "401");
                response.put("error", "Unauthorized");
                response.put("message", "Unauthorized");
                response.put("path", "scanning");
            }
            else{
                response.put("result",res);
                response.put("status",200);
            }
        }
        return response;
    }
}
