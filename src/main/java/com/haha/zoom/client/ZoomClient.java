package com.haha.zoom.client;

import com.haha.zoom.manager.ZoomManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@Environment(EnvType.CLIENT)
public class ZoomClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ZoomManager.setZooming(ZoomManager.ZOOM.isPressed());
            ZoomManager.tick();
        });
    }
}
