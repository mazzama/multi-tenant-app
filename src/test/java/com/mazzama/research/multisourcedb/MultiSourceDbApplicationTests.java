package com.mazzama.research.multisourcedb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MultiSourceDbApplicationTests {
    
    @Test
    public void contextLoads() {

    }

    @Test
    public void test() {
        Map mapMock = mock(Map.class);
        assertEquals(mapMock.size(), 0);
    }

}
