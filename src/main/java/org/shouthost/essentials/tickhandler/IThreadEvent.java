package org.shouthost.essentials.tickhandler;

import org.shouthost.essentials.api.IThread;
import org.shouthost.essentials.core.Essentials;

public class IThreadEvent extends Thread {

	public IThreadEvent(){}

	public void registerThreadedTasks(IThread tasks){
		Essentials.ttasks.offer(tasks);
	}

	@Override
	public void run() {
		for (IThread task : Essentials.ttasks) {
			synchronized (this){
				if (task.isTaskCompleted()) {
					task.onTaskComplete();
					Essentials.ttasks.remove();
				}
			}
			task.onTaskThread();
		}
	}
}
