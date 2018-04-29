package com.szagoret.n26.statistics;

import com.szagoret.n26.statistics.util.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateUtilsTest {

    @Test
    public void testTimeStampCalculation() {
        Long timestampStart = 1525036985000L; //  Apr 29 2018 21:23:05
        Long timestampTarget = 1525036925000L; // Apr 29 2018 21:22:05
        Long seconds = 60L; //sec

        Long timeStampResult = DateUtils.secondsBefore(seconds, timestampStart);

        assertThat(timeStampResult).isEqualTo(timestampTarget);

    }
}
