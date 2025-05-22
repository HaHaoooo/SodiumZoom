package com.haha.zoom.data;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;
import net.caffeinemc.mods.sodium.client.services.PlatformRuntimeInformation;
import net.caffeinemc.mods.sodium.client.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ZoomStorage implements OptionStorage<ZoomData> {

    private static final Gson GSON = (new GsonBuilder()).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().excludeFieldsWithModifiers(2).create();
    private final ZoomData data = new ZoomData();

    public ZoomStorage() {
        Path path = PlatformRuntimeInformation.getInstance().getConfigDirectory().resolve("sodium-zoom.json");
        if (!Files.exists(path)) {
            save(); // 写入默认配置
        }
    }

    @Override
    public void save() {
        Path path = PlatformRuntimeInformation.getInstance().getConfigDirectory().resolve("sodium-zoom.json");
        try {
            FileUtil.writeTextRobustly(GSON.toJson(data), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ZoomData getData() {
        Path path = PlatformRuntimeInformation.getInstance().getConfigDirectory().resolve("sodium-zoom.json");
        if (path.toFile().exists()) {
            try {
                String json = Files.readString(path);
                ZoomData loaded = GSON.fromJson(json, ZoomData.class);
                if (loaded != null) {
                    copyData(loaded, data);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return data;
    }

    private void copyData(ZoomData src, ZoomData dest) {
        dest.setShowFps(src.isShowFps());
        dest.setShowCoordinate(src.isShowCoordinate());
        dest.setUseBetterChat(src.isUseBetterChat());
        dest.setFullBright(src.isFullBright());
        dest.setZoomSpeed(src.getZoomSpeed());
        dest.setZoomKeyCode(src.getZoomKeyCode());
        dest.setColor(src.getColor());
    }

    private static ZoomStorage instance;

    public static ZoomStorage getInstance() {
        if (instance == null) {
            instance = new ZoomStorage();
        }
        return instance;
    }
}
