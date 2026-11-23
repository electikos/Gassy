package org.electron.gassy.handler;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum GasHandlerMode implements StringRepresentable {
    NONE, INPUT, OUTPUT, BOTH;

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static final Codec<GasHandlerMode> CODEC =
            StringRepresentable.fromEnum(GasHandlerMode::values);
}
