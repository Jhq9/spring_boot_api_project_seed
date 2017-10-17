package com.company.project;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author jinhuaquan
 * @create 2017-10-16 上午9:41
 * @desc Controller接口测试类
 **/
//过滤掉身份认证
@ActiveProfiles("dev")
@WebAppConfiguration
public class ControllerTester extends Tester{

    @Autowired
    public WebApplicationContext context;

    public MockMvc mockMvc;

    @Before
    public void setupMockMvc() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

}
