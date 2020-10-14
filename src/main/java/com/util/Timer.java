package com.util;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

public class Timer {
    Instant startTime;
    Instant tmpTime;
    Instant endTime;

    public Timer(){};

    public void setTimer(){
        startTime=Instant.now();
        tmpTime=startTime;
    }

    public long setTmpTimer(){
        Instant tmp=Instant.now();
        long res=Duration.between(tmpTime,tmp).toMillis();
        tmpTime=tmp;
        return res;
    }

    public long endTimer(){
        endTime=Instant.now();
        return Duration.between(startTime,endTime).toMillis();
    }
}
