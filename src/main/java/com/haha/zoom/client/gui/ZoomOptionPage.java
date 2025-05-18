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
                                        .setName(getName("show_fps"))
                                        .setTooltip(getToolTip("show_fps"))
                                        .setEnabled(() -> true)
                                        .setControl(TickBoxControl::new)
                                        .setBinding((opts, value) -> storage.getData().showFps = value, opts -> storage.getData().showFps)
                                        .build()
                        )
                        .build()
        );
        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(boolean.class, new ZoomStorage())
                                        .setName(getName("show_coord"))
                                        .setTooltip(getToolTip("show_coord"))
                                        .setEnabled(() -> true)
                                        .setControl(TickBoxControl::new)
                                        .setBinding((opts, value) -> storage.getData().showCoordinate = value, opts -> storage.getData().showCoordinate)
                                        .build()
                        )
                        .build()
        );
        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(boolean.class, new ZoomStorage())
                                        .setName(getName("better_chat"))
                                        .setTooltip(getToolTip("better_chat"))
                                        .setEnabled(() -> true)
                                        .setControl(TickBoxControl::new)
                                        .setBinding((opts, value) -> storage.getData().useBetterChat = value, opts -> storage.getData().useBetterChat)
                                        .build()
                        )
                        .build()
        );
        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(boolean.class, new ZoomStorage())
                                        .setName(getName("full_bright"))
                                        .setTooltip(getToolTip("full_bright"))
                                        .setEnabled(() -> true)
                                        .setControl(TickBoxControl::new)
                                        .setBinding((opts, value) -> storage.getData().fullBright = value, opts -> storage.getData().fullBright)
                                        .build()
                        )
                        .build()
        );
        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(float.class, new ZoomStorage())
                                        .setName(getName("zoom_speed"))
                                        .setTooltip(getToolTip("zoom_speed"))
                                        .setEnabled(() -> true)
                                        .setControl(FloatSliderControl::new)
                                        .setBinding((opt, value) -> storage.getData().zoomSpeed = value, opt -> storage.getData().zoomSpeed)
                                        .build()
                        )
                        .build()
        );

        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(KeyBinding.class, new ZoomStorage())
                                        .setName(getName("zoom_key"))
                                        .setTooltip(getToolTip("zoom_key"))
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
                                        .setName(getName("color"))
                                        .setTooltip(getToolTip("color"))
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

    private static Text getName(String name){
        return Text.translatable(Identifier.of(Zoom.MOD_ID, name).toTranslationKey());
    }

    private static Text getToolTip(String name){
        return Text.translatable(Identifier.of(Zoom.MOD_ID, name + ".desc").toTranslationKey());
    }
}

