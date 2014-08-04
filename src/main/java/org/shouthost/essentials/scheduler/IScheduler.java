package org.shouthost.essentials.scheduler;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IScheduler {

	private final ConcurrentLinkedQueue<Runnable> scheduledTask = new ConcurrentLinkedQueue<Runnable>();
	;
	private final ConcurrentLinkedQueue<Thread> runningTasks = new ConcurrentLinkedQueue<Thread>();
	private int count = 0;

	public IScheduler() {
		FMLCommonHandler.instance().bus().register(this);
	}

	//TODO:create an queue executor for scheduling runnable task
	public void scheduleSyncTaskToQueue(Runnable runnable) {
		scheduledTask.offer(runnable);
	}

//	public Object scheduleSyncTask(Callable callable) {
//		return scheduleSyncTask(callable, generateThreadName());
//	}
//
//	public Object scheduleSyncTask(Callable callable, String name) {
//		Object val = null;
//		ExecutorService service = Executors.newFixedThreadPool(5);
//		Future<Object> future = service.submit(callable);
//		try {
//			val = future.get();
//		} catch (InterruptedException e) {
//		} catch (ExecutionException e) {
//		}
//		return val;
//	}

	public void scheduleSyncTask(Runnable runnable) {
		scheduleSyncTask(runnable, generateThreadName());
	}

	public void scheduleSyncTask(Runnable runnable, String name) {
		Thread thread = new Thread(runnable, name);
		runningTasks.offer(thread);//TODO: V
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
		}
		runningTasks.remove(thread);//TODO: ^ is this really needed?
	}

	//TODO:Redo scheduleTickTask
	public void scheduleTickTask(Runnable task) {
		if (scheduledTask.contains(task)) return;
		scheduledTask.offer(task);
	}

	public void scheduleAsyncTaskDelay(Runnable runnable, long seconds) {
		final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.schedule(runnable, seconds, TimeUnit.MILLISECONDS);
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

	public void cancelAsyncTask() {
		for (Thread e : runningTasks) {
			e.interrupt();
			runningTasks.remove(e);
		}
	}

	private String generateThreadName() {
		count += 1;
		return "ESS-" + count;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void serverTick(TickEvent.ServerTickEvent event) {
//        for(Callable<Object> task : tickTask){
//            if(task.isScheduleOnTime()){
//                task.onScheduleComplete();
//                tickTask.remove(task);
//            }
//            task.execute();
//        }
	}

}