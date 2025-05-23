package com.haha.zoom.hud;

import com.haha.zoom.ZoomClient;
import com.haha.zoom.data.ZoomStorage;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class InfoHud implements IdentifiedLayer {

    @Override
    public Identifier id() {
        return Identifier.of(ZoomClient.MOD_ID, "info_hud");
    }

    @Override
    public void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.options.hudHidden || client.getDebugHud().shouldShowDebugHud()) return;

        int y = 2;
        int space = 10;
        int color = ZoomClient.getColorARGB();
        if (ZoomStorage.getInstance().getData().isShowFps()) {
            String fps = "[FPS] " + client.getCurrentFps();
            context.drawText(client.textRenderer, Text.literal(fps), 2, y, color, false);
            y += space;
        }
        if (ZoomStorage.getInstance().getData().isShowCoordinate()){
            String coord = "[" + (int) client.player.getX() + ", " + (int) client.player.getY() + ", " + (int) client.player.getZ() + "]";
            context.drawText(client.textRenderer, Text.literal(coord), 2, y, color, false);
        }
    }
}
