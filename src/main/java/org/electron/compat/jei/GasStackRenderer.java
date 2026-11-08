package org.electron.compat.jei;

import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import org.electron.gassy.capability.GassyAtlases;
import org.electron.gassy.gas.Gas;
import org.electron.gassy.gas.GasIngredient;
import org.electron.gassy.gas.GasStack;
import org.electron.gassy.gas.component.GasComponentType;

import java.util.ArrayList;
import java.util.List;

public class GasStackRenderer implements IIngredientRenderer<GasStack> {

    @Override
    public void render(GuiGraphics guiGraphics, GasStack stack) {
        if (stack == null || stack.getGas() == null) {
            return;
        }

        renderGas(guiGraphics, stack.getGas());

        if (!stack.isEmpty()) {
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    String.valueOf(stack.getAmount()),
                    18, 4,
                    0xFFFFFF,
                    true
            );
        }
    }

    public void renderGas(GuiGraphics guiGraphics, Gas gas) {
        if (gas == null) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();

        TextureAtlasSprite sprite = minecraft
                .getTextureAtlas(GassyAtlases.GAS_ATLAS)
                .apply(gas.getTexture());

        int tint = gas.getTint();

        float red = ((tint >> 16) & 0xFF) / 255.0F;
        float green = ((tint >> 8) & 0xFF) / 255.0F;
        float blue = (tint & 0xFF) / 255.0F;

        guiGraphics.blit(
                0, 0, 0,
                16, 16,
                sprite,
                red, green, blue, 1.0F
        );

    }

    @Override
    public List<Component> getTooltip(GasStack stack, TooltipFlag tooltipFlag) {
        List<Component> tooltip = new ArrayList<>();

        tooltip.add(stack.getGas().getDescription());
        if (!stack.isEmpty()) {
            tooltip.add(Component.literal(stack.getAmount() + " mB"));
        }

        for (var entry : stack.getAllComponents().entrySet()) {
            renderComponent(
                    entry.getKey(),
                    entry.getValue(),
                    tooltip
            );
        }


        return tooltip;
    }

    @SuppressWarnings("unchecked")
    private <T> void renderComponent(
            GasComponentType<T> type,
            Object value,
            List<Component> tooltip
    ) {
        type.getRenderer().ifPresent(renderer ->
                renderer.render((T) value, tooltip)
        );
    }
}