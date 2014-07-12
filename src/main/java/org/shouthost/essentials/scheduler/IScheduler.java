package org.shouthost.essentials.scheduler;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import java.util.concurrent.*;

public class IScheduler {

	private final ConcurrentLinkedQueue<Runnable> scheduledTask;
	private final ConcurrentLinkedQueue<Thread> runningTasks;
    private final ConcurrentLinkedQueue<Callable<Object>> tickTask;
	private int count = 0;

	public IScheduler() {
		scheduledTask = new ConcurrentLinkedQueue<Runnable>();
		runningTasks = new ConcurrentLinkedQueue<Thread>();
        tickTask = new ConcurrentLinkedQueue<Callable<Object>>();
		FMLCommonHandler.instance().bus().register(this);
	}

	public void scheduleSyncTaskToQueue(Runnable runnable){
		scheduledTask.offer(runnable);
	}

	public Object scheduleSyncTask(Callable callable) {
		return scheduleSyncTask(callable, generateThreadName());
	}

	public Object scheduleSyncTask(Callable callable, String name) {
		Object val = null;
		ExecutorService service = Executors.newFixedThreadPool(5);
		Future<Object> future = service.submit(callable);
		try {
			val = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return val;
	}

	public void scheduleSyncTask(Runnable runnable) {
		scheduleSyncTask(runnable, generateThreadName());
	}

	public void scheduleSyncTask(Runnable runnable, String name) {
		Thread thread = new Thread(runnable, name);
		runningTasks.offer(thread);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		runningTasks.remove(thread);

	}

    public void scheduleTickTask(Callable<Object> task){
        if(tickTask.contains(task)) return;
        tickTask.offer(task);
    }

	public long scheduleAsyncTask(Runnable runnable) {
		return scheduleAsyncTask(runnable, generateThreadName());
	}

	public long scheduleAsyncTask(Runnable runnable, String name) {
		if (runnable == null) return -1;
		Thread thread = new Thread(runnable, name);
		thread.start();
		runningTasks.offer(thread);
		return thread.getId();
	}

	public void cancelAsyncTask(String name) {
		for (Thread e : runningTasks)
			if (e.getName().equalsIgnoreCase(name)) {
				e.interrupt();
				runningTasks.remove(e);
			}
	}

	public void cancelAsyncTask(long id) {
		for (Thread e : runningTasks)
			if (e.getId() == id) {
				e.interrupt();
				runningTasks.remove(e);
			}
	}

	public void cancelAllTask(){
		for(Thread e : runningTasks){
			e.interrupt();
			runningTasks.remove(e);
		}
	}

	private String generateThreadName() {
		count += 1;
		return "CT-" + count;
	}

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void serverTick(TickEvent.ServerTickEvent event){
//        for(Callable<Object> task : tickTask){
//            if(task.isScheduleOnTime()){
//                task.onScheduleComplete();
//                tickTask.remove(task);
//            }
//            task.execute();
//        }
    }

}