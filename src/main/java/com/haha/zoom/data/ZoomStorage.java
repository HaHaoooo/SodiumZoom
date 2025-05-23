package com.haha.zoom.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haha.zoom.ZoomClient;
import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;
import net.caffeinemc.mods.sodium.client.util.FileUtil;
import net.minecraft.client.MinecraftClient;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ZoomStorage implements OptionStorage<ZoomData> {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final ZoomData data = loadFromDisk();

    @Override
    public void save() {
        try {
            writeToDisk(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ZoomClient.logger().info("Flushed changes to Sodium Zoom configuration");
    }

    @Override
    public ZoomData getData() {
        return this.data;
    }

    private static Path getConfigPath() {
        return MinecraftClient.getInstance().runDirectory.toPath().resolve("config").resolve("sodium-zoom.json");
    }

    private static ZoomData loadFromDisk() {
        Path path = getConfigPath();
        ZoomData config;
        if (Files.exists(path)) {
            try (FileReader reader = new FileReader(path.toFile())) {
                config = GSON.fromJson(reader, ZoomData.class);
            } catch (IOException e) {
                throw new RuntimeException("Could not parse config", e);
            }
        } else {
            config = new ZoomData();
        }

        try {
            writeToDisk(config);
            return config;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't update config file", e);
        }
    }

    private static void writeToDisk(ZoomData config) throws IOException {
        Path path = getConfigPath();
        Path dir = path.getParent();
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        } else if (!Files.isDirectory(dir)) {
            throw new IOException("Not a directory: " + dir);
        }
        FileUtil.writeTextRobustly(GSON.toJson(config), path);
    }

    private static ZoomStorage instance;

    public static ZoomStorage getInstance() {
        if (instance == null) {
            instance = new ZoomStorage();
        }
        return instance;
    }
}
