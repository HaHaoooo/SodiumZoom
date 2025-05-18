package com.haha.zoom.manager;

import com.haha.zoom.client.gui.ZoomOptionPage;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ZoomManager {
    public static KeyBinding ZOOM = new KeyBinding(
            "key.haha.zoom",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "category.haha"
    );
    public static final float TARGET = 22f;
    public static final float BASE = 70f; // 70为原版基础FOV
    private static final float zoomTargetScale = TARGET / BASE;
    private static float prevScale = 1f;
    private static float nextScale = 1f;
    private static boolean zooming = false;

    // 每tick推进缩放倍率
    public static void tick() {
        float goal = zooming ? zoomTargetScale : 1f;
        prevScale = nextScale;
        float speed = ZoomOptionPage.storage.getData().zoomSpeed;
        nextScale += (goal - nextScale) * speed;
        if (Math.abs(nextScale - goal) < 0.001f) nextScale = goal;
    }

    public static float getScale(float tickDelta) {
        return prevScale + (nextScale - prevScale) * tickDelta;
    }

    public static void setZooming(boolean value) {
        zooming = value;
    }
}
