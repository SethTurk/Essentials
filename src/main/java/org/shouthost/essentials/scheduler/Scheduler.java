package org.shouthost.essentials.scheduler;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
    private int count = 0;

    public Scheduler() {
        FMLCommonHandler.instance().bus().register(this);
    }

    public void scheduleSyncTask(Runnable runnable) {
        exec.submit(runnable);
    }

    public void scheduleAsyncTask(Runnable runnable) {
        exec.execute(runnable);
    }

    public void scheduleAsyncTaskDelay(Runnable runnable, long seconds) {
        exec.schedule(runnable, seconds, TimeUnit.MILLISECONDS);
    }

    public void scheduleAsyncTimerTask(Runnable runnable, long delay, long seconds) {

    }

    public void cancelAsyncTask() {
    }

    private String generateThreadName() {
        count += 1;
        return "ESS-" + count;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void serverTick(TickEvent.ServerTickEvent event) {

    }

}