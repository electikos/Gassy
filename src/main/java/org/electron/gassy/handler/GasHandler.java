package org.electron.gassy.handler;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import org.electron.gassy.gas.GasTank;

import java.util.ArrayList;
import java.util.List;

public class GasHandler implements IGasHandler{
    private final List<GasTank> tanks;

    public GasHandler(){
        this.tanks = new ArrayList<>();
    }


    @Override
    public int getTanks() {
        return this.tanks.size();
    }

    @Override
    public IGasTankHandler getTank(int index) {
        return this.tanks.get(index);
    }

    public boolean hasTank(GasTank tank){
        return this.tanks.contains(tank);
    }

    public void add(GasTank tank){
        if (this.tanks.contains(tank)) return;
        this.tanks.add(tank);
    }

    public void remove(int index){
        this.tanks.remove(index);
    }

    public void remove(GasTank tank) {
        this.tanks.remove(tank);
    }

    public void clear(){
        this.tanks.clear();

    }

    private static final Codec<List<GasTank>> TANKS_CODEC =
            GasTank.CODEC.listOf();

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        TANKS_CODEC.encodeStart(NbtOps.INSTANCE, this.tanks)
                .result()
                .ifPresent(result -> tag.put("tanks", result));

        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        if (!tag.contains("tanks")) return;

        TANKS_CODEC.parse(NbtOps.INSTANCE, tag.get("tanks"))
                .result()
                .ifPresent(decodedTanks -> {
                    this.tanks.clear();
                    this.tanks.addAll(decodedTanks);
                });
    }
}
