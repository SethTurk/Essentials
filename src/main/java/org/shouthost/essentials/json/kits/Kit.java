package org.shouthost.essentials.json.kits;

import java.util.List;

public class Kit {
    private String name;
    private List<KitsInternal> kit;
    private int cooldown;

    public String getKitName() {
        return name;
    }

    public int getCooldown() {
        return cooldown;
    }

    public List<KitsInternal> getKit() {
        return kit;
    }

}
