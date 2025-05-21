package com.haha.zoom.client.gui;

import com.google.common.collect.ImmutableList;
import com.haha.zoom.Zoom;
import com.haha.zoom.control.ColorControl;
import com.haha.zoom.control.FloatSliderControl;
import com.haha.zoom.control.KeyBindControl;
import com.haha.zoom.data.ZoomData;
import com.haha.zoom.data.ZoomStorage;
import net.caffeinemc.mods.sodium.client.gui.options.*;
import net.caffeinemc.mods.sodium.client.gui.options.binding.OptionBinding;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ZoomOptionPage {

    public static OptionPage create() {
        List<OptionGroup> groups = new ArrayList<>();
        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(boolean.class, new ZoomStorage())
                                        .setName(getName("show_fps"))
                                        .setTooltip(getToolTip("show_fps"))
                                        .setEnabled(() -> true)
                                        .setControl(TickBoxControl::new)
                                        .setBinding(new OptionBinding<>() {
                                            @Override
                                            public void setValue(ZoomData zoomData, Boolean aBoolean) {
                                                zoomData.showFps = aBoolean;
                                            }

                                            @Override
                                            public Boolean getValue(ZoomData zoomData) {
                                                return zoomData.showFps;
                                            }
                                        })
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
                                        .setBinding(new OptionBinding<>() {
                                            @Override
                                            public void setValue(ZoomData zoomData, Boolean aBoolean) {
                                                zoomData.showCoordinate = aBoolean;
                                            }

                                            @Override
                                            public Boolean getValue(ZoomData zoomData) {
                                                return zoomData.showCoordinate;
                                            }
                                        })
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
                                        .setBinding(new OptionBinding<>() {
                                            @Override
                                            public void setValue(ZoomData zoomData, Boolean aBoolean) {
                                                zoomData.useBetterChat = aBoolean;
                                            }

                                            @Override
                                            public Boolean getValue(ZoomData zoomData) {
                                                return zoomData.useBetterChat;
                                            }
                                        })
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
                                        .setBinding(new OptionBinding<>() {
                                            @Override
                                            public void setValue(ZoomData zoomData, Boolean aBoolean) {
                                                zoomData.fullBright = aBoolean;
                                            }

                                            @Override
                                            public Boolean getValue(ZoomData zoomData) {
                                                return zoomData.fullBright;
                                            }
                                        })
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
                                        .setBinding(new OptionBinding<>() {
                                            @Override
                                            public void setValue(ZoomData zoomData, Float aFloat) {
                                                zoomData.zoomSpeed = aFloat;
                                            }

                                            @Override
                                            public Float getValue(ZoomData zoomData) {
                                                return zoomData.zoomSpeed;
                                            }
                                        })
                                        .build()
                        )
                        .build()
        );

        groups.add(OptionGroup.createBuilder().add(
                                OptionImpl.createBuilder(Integer.class, new ZoomStorage())
                                        .setName(getName("zoom_key"))
                                        .setTooltip(getToolTip("zoom_key"))
                                        .setEnabled(() -> true)
                                        .setControl(KeyBindControl::new)
                                        .setBinding(new OptionBinding<>() {
                                            @Override
                                            public void setValue(ZoomData zoomData, Integer integer) {
                                                zoomData.zoomKeyCode = integer;
                                            }

                                            @Override
                                            public Integer getValue(ZoomData zoomData) {
                                                return zoomData.zoomKeyCode;
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
                                               zoomData.color = color;
                                            }

                                            @Override
                                            public Color getValue(ZoomData zoomData) {
                                                return zoomData.color;
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

