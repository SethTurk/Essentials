package org.shouthost.essentials.tickhandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import org.shouthost.essentials.api.ITick;
import org.shouthost.essentials.core.Essentials;

public class ITickEvent {

    public ITickEvent() {
        FMLCommonHandler.instance().bus().register(this);
    }

    public void registerTask(ITick task) {
        Essentials.tasks.offer(task);
    }

    @SubscribeEvent
    public void tickStart(TickEvent.ServerTickEvent event) {

        if (event.phase != TickEvent.Phase.START)
            return;

        for (ITick task : Essentials.tasks) {
            if (task.isTaskCompleted()) {
                task.onTaskComplete();
                Essentials.tasks.remove(task);
            }
            task.onTaskTick();
        }
    }
}
