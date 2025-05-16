package com.haha.zoom.client.gui;

import com.google.common.collect.ImmutableList;
import com.haha.zoom.Zoom;
import com.haha.zoom.client.hud.InfoHud;
import com.haha.zoom.control.ColorControl;
import com.haha.zoom.control.FloatSliderControl;
import com.haha.zoom.control.KeyBindControl;
import com.haha.zoom.data.ZoomData;
import com.haha.zoom.data.ZoomStorage;
import com.haha.zoom.manager.ZoomManager;
import net.caffeinemc.mods.sodium.client.gui.options.*;
import net.caffeinemc.mods.sodium.client.gui.options.binding.OptionBinding;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ZoomOptionPage {

    public static final ZoomStorage storage = new ZoomStorage();

    public static OptionPage create() {
        List<OptionGroup> groups = new ArrayList<>();
        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(boolean.class, new ZoomStorage())
                                        .setName(Text.translatable(Identifier.of(Zoom.MOD_ID, "show_fps").toTranslationKey()))
                                        .setTooltip(Text.translatable(Identifier.of(Zoom.MOD_ID, "show_fps_desc").toTranslationKey()))
                                        .setEnabled(() -> true)
                                        .setControl(TickBoxControl::new)
                                        .setBinding((opts, value) -> storage.getData().showFps = value, opts -> storage.getData().showFps)
                                        .build()
                        )
                        .build()
        );
        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(boolean.class, new ZoomStorage())
                                        .setName(Text.translatable(Identifier.of(Zoom.MOD_ID, "show_coord").toTranslationKey()))
                                        .setTooltip(Text.translatable(Identifier.of(Zoom.MOD_ID, "show_coord_desc").toTranslationKey()))
                                        .setEnabled(() -> true)
                                        .setControl(TickBoxControl::new)
                                        .setBinding((opts, value) -> storage.getData().showCoordinate = value, opts -> storage.getData().showCoordinate)
                                        .build()
                        )
                        .build()
        );
        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(float.class, new ZoomStorage())
                                        .setName(Text.translatable(Identifier.of(Zoom.MOD_ID, "zoom_speed").toTranslationKey()))
                                        .setTooltip(Text.translatable(Identifier.of(Zoom.MOD_ID, "zoom_speed_desc").toTranslationKey()))
                                        .setEnabled(() -> true)
                                        .setControl(FloatSliderControl::new)
                                        .setBinding((opt, value) -> storage.getData().zoomSpeed = value, opt -> storage.getData().zoomSpeed)
                                        .build()
                        )
                        .build()
        );

        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(KeyBinding.class, new ZoomStorage())
                                        .setName(Text.translatable(Identifier.of(Zoom.MOD_ID, "zoom_key").toTranslationKey()))
                                        .setTooltip(Text.translatable(Identifier.of(Zoom.MOD_ID, "zoom_key_desc").toTranslationKey()))
                                        .setEnabled(() -> true)
                                        .setControl(KeyBindControl::new)
                                        .setBinding(new OptionBinding<>() {
                                            @Override
                                            public void setValue(ZoomData haHaData, KeyBinding keyBinding) {
                                                ZoomManager.ZOOM = keyBinding;
                                            }

                                            @Override
                                            public KeyBinding getValue(ZoomData zoomData) {
                                                return ZoomManager.ZOOM;
                                            }
                                        })
                                        .build()
                        )
                        .build()
        );

        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(Color.class, new ZoomStorage())
                                        .setName(Text.translatable(Identifier.of(Zoom.MOD_ID, "color").toTranslationKey()))
                                        .setTooltip(Text.translatable(Identifier.of(Zoom.MOD_ID, "color_desc").toTranslationKey()))
                                        .setEnabled(() -> true)
                                        .setControl(ColorControl::new)
                                        .setBinding(new OptionBinding<>() {
                                            @Override
                                            public void setValue(ZoomData zoomData, Color color) {
                                                InfoHud.COLOR = color;
                                            }

                                            @Override
                                            public Color getValue(ZoomData zoomData) {
                                                return InfoHud.COLOR;
                                            }
                                        })
                                        .build()
                        )
                        .build()
        );

        return new OptionPage(Text.literal("Zoom"), ImmutableList.copyOf(groups));
    }
}

