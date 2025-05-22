package com.haha.zoom.data;

import net.minecraft.client.util.InputUtil;

import java.awt.*;

public class ZoomData {
    private boolean showFps;
    private boolean showCoordinate;
    private boolean useBetterChat;
    private boolean fullBright;
    private float zoomSpeed;
    private int zoomKeyCode;
    private Color color;

    public ZoomData() {
        this.showFps = false;
        this.showCoordinate = false;
        this.useBetterChat = false;
        this.fullBright = false;
        this.zoomSpeed = 0.25f;
        this.zoomKeyCode = InputUtil.GLFW_KEY_C;
        this.color = Color.WHITE;
    }

    public void setShowFps(boolean showFps) {
        this.showFps = showFps;
    }
    public void setShowCoordinate(boolean showCoordinate) {
        this.showCoordinate = showCoordinate;
    }
    public void setUseBetterChat(boolean useBetterChat) {
        this.useBetterChat = useBetterChat;
    }
    public void setFullBright(boolean fullBright) {
        this.fullBright = fullBright;
    }
    public void setZoomSpeed(float zoomSpeed) {
        this.zoomSpeed = zoomSpeed;
    }
    public void setZoomKeyCode(int zoomKeyCode) {
        this.zoomKeyCode = zoomKeyCode;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isShowFps() {
        return showFps;
    }
    public boolean isShowCoordinate() {
        return showCoordinate;
    }
    public boolean isUseBetterChat() {
        return useBetterChat;
    }
    public boolean isFullBright() {
        return fullBright;
    }
    public float getZoomSpeed() {
        return zoomSpeed;
    }
    public int getZoomKeyCode() {
        return zoomKeyCode;
    }
    public Color getColor() {
        return color;
    }
}
