package com.haha.zoom.data;

import net.minecraft.client.util.InputUtil;

import java.awt.*;

public class ZoomData {
    public boolean showFps;
    public boolean showCoordinate;
    public boolean useBetterChat;
    public boolean fullBright;
    public float zoomSpeed;
    public int zoomKeyCode;
    public Color color;

    public ZoomData() {
        this.showFps = false;
        this.showCoordinate = false;
        this.useBetterChat = false;
        this.fullBright = false;
        this.zoomSpeed = 0.25f;
        this.zoomKeyCode = InputUtil.GLFW_KEY_V;
        this.color = Color.WHITE;
    }
}
