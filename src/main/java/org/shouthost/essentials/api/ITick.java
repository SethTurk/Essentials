package org.shouthost.essentials.api;

public interface ITick {

	public void onTaskTick();

	public void onTaskComplete();

	public boolean isTaskCompleted();

}
