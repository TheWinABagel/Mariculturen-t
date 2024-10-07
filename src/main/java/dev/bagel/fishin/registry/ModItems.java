package dev.bagel.fishin.registry;

import dev.bagel.fishin.Fishin;
import dev.bagel.fishin.fish.rods.AbstractRod;
import dev.bagel.fishin.fish.rods.BasicRod;
import dev.bagel.fishin.registry.components.RodComponent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Fishin.MODID);
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", ModBlocks.EXAMPLE_BLOCK);
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));
    public static final DeferredItem<AbstractRod> BASIC_ROD = ITEMS.register("basic_rod",() -> new BasicRod(new Item.Properties().fireResistant().component(ModComponents.ROD_COMPONENT, new RodComponent(0, 9)).rarity(Rarity.EPIC), 200));
    public static final DeferredItem<ArmorItem> DEBUG_FISHING_ARMOR = ITEMS.register("fishing_armor",() -> new ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(50))));

    public static void init(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
