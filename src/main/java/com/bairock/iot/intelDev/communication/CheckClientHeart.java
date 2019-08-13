package com.bairock.iot.intelDev.communication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 检查客户端心跳
 * 关掉超过90s没响应的连接
 * @author 44489
 * @version 2019年8月12日下午8:36:50
 */
public class CheckClientHeart {

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    
    public void start() {
        CheckThread check = new CheckThread();
        scheduledExecutorService.scheduleWithFixedDelay(check, 1, 60, TimeUnit.SECONDS);
    }
    
    public void stop() {
        scheduledExecutorService.shutdownNow();
    }
    
    class CheckThread extends Thread{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
        }
        
    }
}
