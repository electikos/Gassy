package org.electron.gassy.capability;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import org.electron.gassy.Gassy;
import org.electron.gassy.handler.IGasHandler;
import org.jetbrains.annotations.Nullable;

public class GassyCapability {

    public static final BlockCapability<IGasHandler, @Nullable Direction> GAS_HANDLER =
            BlockCapability.createSided(
                    ResourceLocation.fromNamespaceAndPath(Gassy.MODID, "gas_handler"),
                    IGasHandler.class
            );
    public static final ItemCapability<IGasHandler, Void> GAS_ITEM_HANDLER =
            ItemCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Gassy.MODID, "gas_item_handler"),
                    IGasHandler.class
            );
    public static final EntityCapability<IGasHandler, Void> GAS_HANDLER_ENTITY =
            EntityCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Gassy.MODID, "gas_entity_handler"),
                    IGasHandler.class
            );
}
