package com.haha.zoom;

import com.haha.zoom.control.ColorControl;
import com.haha.zoom.hud.InfoHud;
import com.haha.zoom.key.KeyMonitor;
import com.haha.zoom.data.ZoomStorage;
import com.haha.zoom.manager.ZoomManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class ZoomClient implements ClientModInitializer {

    public static final String MOD_ID = "zoom";


    private static final Logger LOGGER = LoggerFactory.getLogger("Sodium-[Zoom]");

    @Override
    public void onInitializeClient() {
        HudLayerRegistrationCallback.EVENT.register((registrar) -> registrar.addLayer(IdentifiedLayer.of(Identifier.of(MOD_ID, "info_hud"), new InfoHud())));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ZoomManager.setZooming(KeyMonitor.isKeyDown(ZoomStorage.getInstance().getData().getZoomKeyCode()));
            ZoomManager.tick();
        });
    }

    public static Logger logger() {
        if (LOGGER == null) {
            throw new IllegalStateException("Logger not yet available");
        } else {
            return LOGGER;
        }
    }

    public static int getColorARGB() {
        int color = ZoomStorage.getInstance().getData().getColor();
        // 如果是动态色标记，计算当前动态色
        if (color == ColorControl.DYNAMIC_COLOR_MARKER) {
            long time = System.currentTimeMillis() % 5000;
            float hue = (time / 5000f);
            int rgb = java.awt.Color.HSBtoRGB(hue, 1f, 1f);
            return 0xFF000000 | rgb;
        }
        return color;
    }
}
