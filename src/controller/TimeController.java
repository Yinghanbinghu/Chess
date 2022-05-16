package controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeController {
    private volatile int limitSec ; //记录倒计时时间
    private int curSec;   //记录倒计时当下时间
    public TimeController(int limitSec) throws InterruptedException{
        this.limitSec = limitSec;
        this.curSec = limitSec;
        System.out.println("count down form "+limitSec);

        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(new Task(),0,1, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(limitSec);   //暂停本线程
        exec.shutdownNow();
        System.out.println("Time out！");
    }
    private class Task implements Runnable{
        public void run(){
            System.out.println("Time remains "+ --curSec +" s");
        }
    }
}
