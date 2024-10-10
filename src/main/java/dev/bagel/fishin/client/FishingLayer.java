package dev.bagel.fishin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.bagel.fishin.network.payload.C2SUpdateComponent;
import dev.bagel.fishin.registry.ModComponents;
import dev.bagel.fishin.registry.ModItems;
import dev.bagel.fishin.registry.components.RodComponent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class FishingLayer implements LayeredDraw.Layer {
    private static final ResourceLocation HOTBAR_SPRITE = ResourceLocation.withDefaultNamespace("hud/hotbar");
    private static final ResourceLocation HOTBAR_SELECTION_SPRITE = ResourceLocation.withDefaultNamespace("hud/hotbar_selection");

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.isHolding(stack -> stack.is(ModItems.BASIC_ROD.asItem()))) {
            ItemStack rodStack = ItemStack.EMPTY;
            if (player.getMainHandItem().is(ModItems.BASIC_ROD.asItem())) {
                rodStack = player.getMainHandItem();
            } else if (player.getOffhandItem().is(ModItems.BASIC_ROD.asItem())) {
                rodStack = player.getOffhandItem();
            }
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2;
            if (!player.getInventory().getArmor(3).is(ModItems.DEBUG_FISHING_ARMOR)) {
                guiGraphics.drawCenteredString(Minecraft.getInstance().font, "Equip armor for more details", width, height + 20, 0x2323223);
            }
            else {

//            guiGraphics.renderItem(new ItemStack(Items.ANDESITE_SLAB), width - 8, height - 20);
                guiGraphics.drawCenteredString(Minecraft.getInstance().font, "This is some text", width, height + 20, 0x2323223);
                renderItemHotbar(guiGraphics, deltaTracker, player, rodStack);
            }
        }
    }
    private static final List<ItemStack> items = List.of(new ItemStack(Items.ACACIA_BUTTON), new ItemStack(Items.SWEET_BERRIES), new ItemStack(Items.TARGET, 63), new ItemStack(Items.BAMBOO_BUTTON), new ItemStack(Items.YELLOW_DYE), new ItemStack(Items.LADDER), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(Items.LEAD, 63), new ItemStack(Items.MACE), new ItemStack(Items.CLAY));
    private void renderItemHotbar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Player player, ItemStack rodStack) {
//        ItemStack itemstack = player.getOffhandItem();
        RodComponent rodComponent = rodStack.getOrDefault(ModComponents.ROD_COMPONENT, RodComponent.DEFAULT);
        int selected = rodComponent.selected();
        int width = guiGraphics.guiWidth() / 2;
        int height = (guiGraphics.guiHeight() / 2) + 30;
        int k = 91;
        RenderSystem.enableBlend();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, -90.0F);
        guiGraphics.blitSprite(HOTBAR_SPRITE, width - k, height, 182, 22);
        guiGraphics.blitSprite(HOTBAR_SELECTION_SPRITE, width - k - 1 + selected * 20, height - 1, 24, 23);

        guiGraphics.pose().popPose();
        RenderSystem.disableBlend();
        int l = 1;

        for (int i1 = 0; i1 < 9; i1++) {
            int j1 = width - 90 + i1 * 20 + 2;
            int k1 = height + 3;
            this.renderSlot(guiGraphics, j1, k1, deltaTracker, player, items.get(i1), l++);
        }

//        if (!itemstack.isEmpty()) {
//            int i2 = height + 3;
//            this.renderSlot(guiGraphics, width + 91 + 10, i2, deltaTracker, player, itemstack, l++);
//        }

    }

    private void renderSlot(GuiGraphics guiGraphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack stack, int seed) {
        if (!stack.isEmpty()) {
            float f = (float) stack.getPopTime() - deltaTracker.getGameTimeDeltaPartialTick(false);
            if (f > 0.0F) {
                float f1 = 1.0F + f / 5.0F;
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate((float) (x + 8), (float) (y + 12), 0.0F);
                guiGraphics.pose().scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                guiGraphics.pose().translate((float) (-(x + 8)), (float) (-(y + 12)), 0.0F);
            }

            guiGraphics.renderItem(player, stack, x, y, seed);
            if (f > 0.0F) {
                guiGraphics.pose().popPose();
            }

            guiGraphics.renderItemDecorations(Minecraft.getInstance().font, stack, x, y);
        }
    }

    @SubscribeEvent
    public static void onInputMouseScrolling(InputEvent.MouseScrollingEvent e) {
        if (e.getScrollDeltaY() != 0 && Minecraft.getInstance().player != null) {
            for (ItemStack handStack : Minecraft.getInstance().player.getHandSlots()) {
                if (handStack.is(ModItems.BASIC_ROD.asItem())) {
                    RodComponent rodComponent = handStack.getOrDefault(ModComponents.ROD_COMPONENT, RodComponent.DEFAULT);
                    rodComponent = rodComponent.add(e.getScrollDeltaY() > 0 ? -1 : 1);
                    PacketDistributor.sendToServer(new C2SUpdateComponent(rodComponent, handStack));
                    handStack.set(ModComponents.ROD_COMPONENT, rodComponent);
                    e.setCanceled(true);
                    break;
                }
            }
        }
    }
}
