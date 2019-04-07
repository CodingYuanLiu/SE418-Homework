package com.wordladder;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=WordladderApplication.class)
@WebAppConfiguration
public class LadderControllorTests {
    private MockMvc mvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testPostWordladder() throws Exception {
        String[] result = {
                "code->cote->cate->date->data",
                "data->date->cate->cade->code",
                "gap->bap->gap",
                "start->slart->blart->blert->bleat->bleak->break->bream->dream",
                "Noladder",
                "Noladder",
                "Noladder",
                "Noladder",
                "Noladder",
                "Noladder"};
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
        for(int i =0 ;i<result.length;i++) {

            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                    .post("/scanning")
                    .param("start", tests[i][0])
                    .param("dest", tests[i][1])
                    .header("Content-Type", "application/json")).andReturn();
            String res = mvcResult.getResponse().getContentAsString();

            Assert.assertEquals(res, result[i]);
        }
    }

}
