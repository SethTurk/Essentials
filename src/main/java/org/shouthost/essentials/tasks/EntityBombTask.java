package org.shouthost.essentials.tasks;

import net.minecraft.entity.Entity;
import org.shouthost.essentials.core.Essentials;

public class EntityBombTask implements Runnable {
    private final Entity entity;
    private final float blast;

    public EntityBombTask(Entity entity) {
        this(entity, 4.0f);
    }

    public EntityBombTask(Entity entity, float blast) {
        this.entity = entity;
        this.blast = blast;
    }

    @Override
    public void run() {
        synchronized (Essentials.schedule.lock) {
            entity.worldObj.createExplosion(entity, entity.posX, entity.posY, entity.posZ, blast, true);
        }
        entity.setDead();
    }
}
