package com.haha.zoom.data;

import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;

public class ZoomStorage implements OptionStorage<ZoomData> {

    private final ZoomData data = new ZoomData();
    @Override
    public ZoomData getData() {
        return data;
    }

    @Override
    public void save() {
        this.data.showFps = false;
    }
}
