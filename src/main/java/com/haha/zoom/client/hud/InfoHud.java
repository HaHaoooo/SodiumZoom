package com.haha.zoom.client.hud;

import com.haha.zoom.client.gui.ZoomOptionPage;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public class InfoHud implements IdentifiedLayer {

    public static Color COLOR = Color.CYAN;
    @Override
    public Identifier id() {
        return Identifier.of("fps");
    }

    @Override
    public void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.options.hudHidden) return;

        int y = 2;
        int space = 10;
        if (ZoomOptionPage.storage.getData().showFps) {
            String fps = "[FPS] " + client.getCurrentFps();
            context.drawText(client.textRenderer, Text.literal(fps), 2, y, COLOR.getRGB(), true);
            y += space;
        }
        if (ZoomOptionPage.storage.getData().showCoordinate){
            String coord = "[" + (int) client.player.getX() + ", " + (int) client.player.getY() + ", " + (int) client.player.getZ() + "]";
            context.drawText(client.textRenderer, Text.literal(coord), 2, y, COLOR.getRGB(), true);
        }
    }
}
