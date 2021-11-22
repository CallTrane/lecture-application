package com.lecture;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
@WebAppConfiguration
@EnableWebMvc
@AutoConfigureMockMvc
public class StartApplicationTest {

    @Before
    public void setup() {
        // 初始化测试用例类中由Mockito的注解标注的所有模拟对象
        MockitoAnnotations.openMocks(this);
    }

}
