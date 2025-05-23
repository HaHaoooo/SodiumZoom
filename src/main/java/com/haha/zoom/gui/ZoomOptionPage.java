package com.haha.zoom.gui;

import com.google.common.collect.ImmutableList;
import com.haha.zoom.ZoomClient;
import com.haha.zoom.control.ColorControl;
import com.haha.zoom.control.FloatSliderControl;
import com.haha.zoom.control.KeyBindControl;
import com.haha.zoom.data.ZoomData;
import com.haha.zoom.data.ZoomStorage;
import net.caffeinemc.mods.sodium.client.gui.options.*;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ZoomOptionPage {

    public static OptionPage create() {
        List<OptionGroup> groups = new ArrayList<>();
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, ZoomStorage.getInstance())
                        .setName(getName("show_fps"))
                        .setTooltip(getToolTip("show_fps"))
                        .setEnabled(() -> true)
                        .setControl(TickBoxControl::new)
                        .setBinding(ZoomData::setShowFps, ZoomData::isShowFps)
                        .build()
                )
                .add(OptionImpl.createBuilder(boolean.class, ZoomStorage.getInstance())
                        .setName(getName("show_coord"))
                        .setTooltip(getToolTip("show_coord"))
                        .setEnabled(() -> true)
                        .setControl(TickBoxControl::new)
                        .setBinding(ZoomData::setShowCoordinate, ZoomData::isShowCoordinate)
                        .build()
                )
                .add(OptionImpl.createBuilder(boolean.class, ZoomStorage.getInstance())
                        .setName(getName("better_chat"))
                        .setTooltip(getToolTip("better_chat"))
                        .setEnabled(() -> true)
                        .setControl(TickBoxControl::new)
                        .setBinding(ZoomData::setUseBetterChat, ZoomData::isUseBetterChat)
                        .build()
                )
                .add(OptionImpl.createBuilder(boolean.class, ZoomStorage.getInstance())
                        .setName(getName("full_bright"))
                        .setTooltip(getToolTip("full_bright"))
                        .setEnabled(() -> true)
                        .setControl(TickBoxControl::new)
                        .setBinding(ZoomData::setFullBright, ZoomData::isFullBright)
                        .build()
                )
                .build()
        );
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(float.class, ZoomStorage.getInstance())
                        .setName(getName("zoom_speed"))
                        .setTooltip(getToolTip("zoom_speed"))
                        .setEnabled(() -> true)
                        .setControl(FloatSliderControl::new)
                        .setBinding(ZoomData::setZoomSpeed, ZoomData::getZoomSpeed)
                        .build()
                )
                .add(OptionImpl.createBuilder(int.class, ZoomStorage.getInstance())
                        .setName(getName("zoom_key"))
                        .setTooltip(getToolTip("zoom_key"))
                        .setEnabled(() -> true)
                        .setControl(KeyBindControl::new)
                        .setBinding(ZoomData::setZoomKeyCode, ZoomData::getZoomKeyCode)
                        .build()
                )
                .build()
        );
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(int.class, ZoomStorage.getInstance())
                        .setName(getName("color"))
                        .setTooltip(getToolTip("color"))
                        .setEnabled(() -> true)
                        .setControl(ColorControl::new)
                        .setBinding(ZoomData::setColor, ZoomData::getColor)
                        .build()
                )
                .build()
        );
        return new OptionPage(Text.literal("Zoom"), ImmutableList.copyOf(groups));
    }

    private static Text getName(String name) {
        return Text.translatable(Identifier.of(ZoomClient.MOD_ID, name).toTranslationKey());
    }

    private static Text getToolTip(String name) {
        return Text.translatable(Identifier.of(ZoomClient.MOD_ID, name + ".desc").toTranslationKey());
    }
}

