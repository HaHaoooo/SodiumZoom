package com.haha.zoom;

import com.haha.zoom.client.hud.InfoHud;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.util.Identifier;

public class Zoom implements ModInitializer {

    public static final String MOD_ID = "zoom";

    @Override
    public void onInitialize() {
        HudLayerRegistrationCallback.EVENT.register((registrar) -> registrar.addLayer(IdentifiedLayer.of(Identifier.of(MOD_ID, "info_hud"), new InfoHud())));
    }
}
