package com.wordladder;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=WordladderApplication.class)
@WebAppConfiguration
public class ActuatortTests {
    private MockMvc mvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testActuator() throws Exception{
        mvc.perform(get("/actuator")).andExpect(status().isOk());
    }

    @Test
    public void testBeans() throws Exception {
        mvc.perform(get("/actuator/beans")).andExpect(status().isOk());
    }

    @Test
    public void testHealth() throws Exception {
        mvc.perform(get("/actuator/health")).andExpect(status().isOk());
    }

    @Test
    public void testInfo() throws Exception {
        mvc.perform(get("/actuator/info")).andExpect(status().isOk());
    }

    @Test
    public void testAuditevents() throws Exception {
        mvc.perform(get("/actuator/auditevents")).andExpect(status().isOk());
    }

    @Test
    public void testCaches() throws Exception {
        mvc.perform(get("/actuator/caches")).andExpect(status().isOk());
    }

    @Test
    public void testConditions() throws Exception {
        mvc.perform(get("/actuator/conditions")).andExpect(status().isOk());
    }

    @Test
    public void testConfigprops() throws Exception {
        mvc.perform(get("/actuator/configprops")).andExpect(status().isOk());
    }

    @Test
    public void testEnv() throws Exception {
        mvc.perform(get("/actuator/env")).andExpect(status().isOk());
    }

    @Test
    public void testLoggers() throws Exception {
        mvc.perform(get("/actuator/loggers")).andExpect(status().isOk());
    }

    @Test
    public void testHeapdump() throws Exception {
        mvc.perform(get("/actuator/heapdump")).andExpect(status().isOk());
    }

    @Test
    public void testThreaddump() throws Exception {
        mvc.perform(get("/actuator/threaddump")).andExpect(status().isOk());
    }

    @Test
    public void testMetrics() throws Exception {
        mvc.perform(get("/actuator/metrics")).andExpect(status().isOk());
    }

    @Test
    public void testScheduledtasks() throws Exception {
        mvc.perform(get("/actuator/scheduledtasks")).andExpect(status().isOk());
    }

    @Test
    public void testhttptrace() throws Exception {
        mvc.perform(get("/actuator/httptrace")).andExpect(status().isOk());
    }

    @Test
    public void testmappings() throws Exception {
        mvc.perform(get("/actuator/mappings")).andExpect(status().isOk());
    }
}
