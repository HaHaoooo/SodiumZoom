package com.haha.zoom.data;

public class ZoomData {
    public boolean showFps;
    public boolean showCoordinate;
    public boolean useBetterChat;
    public boolean fullBright;
    public float zoomSpeed;

    public ZoomData(){
        this.showFps = false;
        this.showCoordinate = false;
        this.useBetterChat = false;
        this.fullBright = false;
        this.zoomSpeed = 0.25f;
    }
}
