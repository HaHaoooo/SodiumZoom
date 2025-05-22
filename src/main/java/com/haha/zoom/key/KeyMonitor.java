package com.haha.zoom.key;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class KeyMonitor {
    public static boolean isKeyDown(int glfwKeyCode) {
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        return GLFW.glfwGetKey(windowHandle, glfwKeyCode) == GLFW.GLFW_PRESS;
    }
}
