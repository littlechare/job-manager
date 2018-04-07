package com.littlehow.job.test;

import com.littlehow.job.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class LocalTaskTest {
    @Test
    public void testTask() {
        try {
            TimeUnit.MINUTES.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
