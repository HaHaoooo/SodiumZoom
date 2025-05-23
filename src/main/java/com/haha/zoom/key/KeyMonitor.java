package com.haha.zoom.key;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class KeyMonitor {
    public static boolean isKeyDown(int glfwKeyCode) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen != null) {
            return false;
        }
        long windowHandle = client.getWindow().getHandle();
        return GLFW.glfwGetKey(windowHandle, glfwKeyCode) == GLFW.GLFW_PRESS;
    }
}
