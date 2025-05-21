package com.haha.zoom.client;

import com.haha.zoom.client.hud.InfoHud;
import com.haha.zoom.client.key.KeyMonitor;
import com.haha.zoom.data.ZoomStorage;
import com.haha.zoom.manager.ZoomManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.util.Identifier;

import static com.haha.zoom.Zoom.MOD_ID;

@Environment(EnvType.CLIENT)
public class ZoomClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudLayerRegistrationCallback.EVENT.register((registrar) -> registrar.addLayer(IdentifiedLayer.of(Identifier.of(MOD_ID, "info_hud"), new InfoHud())));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ZoomManager.setZooming(KeyMonitor.isKeyDown(ZoomStorage.data.zoomKeyCode));
            ZoomManager.tick();
        });
    }
}
