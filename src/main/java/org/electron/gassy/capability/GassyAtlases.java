package org.electron.gassy.capability;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMaterialAtlasesEvent;
import org.electron.gassy.Gassy;

@EventBusSubscriber(modid = Gassy.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GassyAtlases {

    public static final ResourceLocation GAS_ATLAS = ResourceLocation.fromNamespaceAndPath(Gassy.MODID, "gases");

    @SubscribeEvent
    public static void registerAtlases(RegisterMaterialAtlasesEvent event) {
        event.register(GAS_ATLAS, GAS_ATLAS);
    }
}
