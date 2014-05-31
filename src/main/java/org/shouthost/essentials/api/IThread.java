package org.shouthost.essentials.api;

public interface IThread {

	public void onTaskThread();

	public void onTaskComplete();

	public boolean isTaskCompleted();

}
