package com.haha.zoom.data;

import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;

public class ZoomStorage implements OptionStorage<ZoomData> {

    public static final ZoomData data = new ZoomData();

    @Override
    public ZoomData getData() {
        return data;
    }

    @Override
    public void save() {

    }
}
