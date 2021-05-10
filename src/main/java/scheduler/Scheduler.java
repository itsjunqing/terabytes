package scheduler;

import observer.OSubject;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler{
    public OSubject oSubject;
    private Timer timer = null;
    private static Scheduler uniqueInstance = null;
    private Scheduler(){
            oSubject = new OSubject();
            timer = new Timer();
            runTasks();
    }
    public static Scheduler getInstance()
    {
        if (uniqueInstance == null)
            uniqueInstance = new Scheduler();

        return uniqueInstance;
    }

    private void doTask() {
        System.out.println("Running Instance");
        oSubject.notifyObservers();
    }

    public void runTasks() {
            timer.schedule(new TimerTask(){
            @Override
            public void run() {
                doTask();
            }},
                0,        //initial delay
                1*5000);  //subsequent rate
    }

    public void endScheduler(){
        timer.cancel();
        timer.purge();
    }


}
//package com.company;
//
//        import java.util.TimerTask;
//
//public class RemindTask extends TimerTask {
//    public Subject subject;
//    public Publisher publisher;
//    public RemindTask(Subject subject, Publisher publisher){
//        this.subject = subject;
//        this.publisher = publisher;
//    }
//    public void run() {
//        System.out.println("running timer task");
//        publisher.notifyObservers();
//        subject.simonSays();
//
//    }
//}


//import static java.util.concurrent.TimeUnit.*;
//class BeeperControl {
//    private final ScheduledExecutorService scheduler =
//            Executors.newScheduledThreadPool(1);
//
//    public void beepForAnHour() {
//        final Runnable beeper = new Runnable() {
//            public void run() { System.out.println("beep"); }
//        };
//        final ScheduledFuture<?> beeperHandle =
//                scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
//        scheduler.schedule(new Runnable() {
//            public void run() { beeperHandle.cancel(true); }
//        }, 60 * 60, SECONDS);
//    }
//}