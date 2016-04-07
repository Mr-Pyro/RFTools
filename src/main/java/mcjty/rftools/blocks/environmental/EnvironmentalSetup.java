package mcjty.rftools.blocks.environmental;

import mcjty.lib.container.GenericItemBlock;
import mcjty.rftools.blocks.ModBlocks;
import mcjty.rftools.crafting.NBTMatchingRecipe;
import mcjty.rftools.items.envmodules.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentalSetup {
    public static EnvironmentalControllerBlock environmentalControllerBlock;

    public static RegenerationEModuleItem regenerationEModuleItem;
    public static RegenerationPlusEModuleItem regenerationPlusEModuleItem;
    public static SpeedEModuleItem speedEModuleItem;
    public static SpeedPlusEModuleItem speedPlusEModuleItem;
    public static HasteEModuleItem hasteEModuleItem;
    public static HastePlusEModuleItem hastePlusEModuleItem;
    public static SaturationEModuleItem saturationEModuleItem;
    public static SaturationPlusEModuleItem saturationPlusEModuleItem;
    public static FeatherFallingEModuleItem featherFallingEModuleItem;
    public static FeatherFallingPlusEModuleItem featherFallingPlusEModuleItem;
    public static FlightEModuleItem flightEModuleItem;
    public static PeacefulEModuleItem peacefulEModuleItem;
    public static WaterBreathingEModuleItem waterBreathingEModuleItem;
    public static NightVisionEModuleItem nightVisionEModuleItem;

    public static BlindnessEModuleItem blindnessEModuleItem;
    public static WeaknessEModuleItem weaknessEModuleItem;
    public static PoisonEModuleItem poisonEModuleItem;
    public static SlownessEModuleItem slownessEModuleItem;

    public static void setupBlocks() {
        environmentalControllerBlock = new EnvironmentalControllerBlock();
        GameRegistry.registerBlock(environmentalControllerBlock, GenericItemBlock.class, "environmentalControllerBlock");
        GameRegistry.registerTileEntity(EnvironmentalControllerTileEntity.class, "EnvironmentalControllerTileEntity");
    }

    public static void setupItems() {
        regenerationEModuleItem = new RegenerationEModuleItem();
        regenerationPlusEModuleItem = new RegenerationPlusEModuleItem();
        speedEModuleItem = new SpeedEModuleItem();
        speedPlusEModuleItem = new SpeedPlusEModuleItem();
        hasteEModuleItem = new HasteEModuleItem();
        hastePlusEModuleItem = new HastePlusEModuleItem();
        saturationEModuleItem = new SaturationEModuleItem();
        saturationPlusEModuleItem = new SaturationPlusEModuleItem();
        featherFallingEModuleItem = new FeatherFallingEModuleItem();
        featherFallingPlusEModuleItem = new FeatherFallingPlusEModuleItem();
        flightEModuleItem = new FlightEModuleItem();
        peacefulEModuleItem = new PeacefulEModuleItem();
        waterBreathingEModuleItem = new WaterBreathingEModuleItem();
        nightVisionEModuleItem = new NightVisionEModuleItem();
        blindnessEModuleItem = new BlindnessEModuleItem();
        weaknessEModuleItem = new WeaknessEModuleItem();
        poisonEModuleItem = new PoisonEModuleItem();
        slownessEModuleItem = new SlownessEModuleItem();
    }

    public static void setupCrafting() {
        GameRegistry.addRecipe(new ItemStack(environmentalControllerBlock), "oDo", "GMI", "oEo", 'o', Items.ender_pearl, 'M', ModBlocks.machineFrame,
                'D', Blocks.diamond_block, 'E', Blocks.emerald_block, 'G', Blocks.gold_block, 'I', Blocks.iron_block);

        Object inkSac = Item.itemRegistry.getObjectById(351);

        String[] syringeMatcher = new String[] { "level", "mobName" };
        String[] pickMatcher = new String[] { "ench" };

        ItemStack ironGolemSyringe = createMobSyringe("Iron Golem");
        ItemStack ghastSyringe = createMobSyringe("Ghast");
        ItemStack chickenSyringe = createMobSyringe("Chicken");
        ItemStack batSyringe = createMobSyringe("Bat");
        ItemStack horseSyringe = createMobSyringe("Horse");
        ItemStack zombieSyringe = createMobSyringe("Zombie");
        ItemStack squidSyringe = createMobSyringe("Squid");
        ItemStack caveSpiderSyringe = createMobSyringe("Cave Spider");
        ItemStack diamondPick = createEnchantedItem(Items.diamond_pickaxe, Enchantment.enchantmentRegistry.getObject(new ResourceLocation("efficiency")), 3);
        ItemStack reds = new ItemStack(Items.redstone);
        ItemStack gold = new ItemStack(Items.gold_ingot);
        ItemStack ink = new ItemStack((Item) inkSac);
        ItemStack obsidian = new ItemStack(Blocks.obsidian);
        ItemStack lapis = new ItemStack(Items.dye, 1, 4);

        GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                new ItemStack[] {null, chickenSyringe, null, reds, gold, reds, null, ink, null},
                new String[][] {null, syringeMatcher, null, null, null, null, null, null, null},
                new ItemStack(featherFallingEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                new ItemStack[] {null, ironGolemSyringe, null, reds, gold, reds, null, ink, null},
                new String[][] {null, syringeMatcher, null, null, null, null, null, null, null},
                new ItemStack(regenerationEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                new ItemStack[] {null, horseSyringe, null, reds, gold, reds, null, ink, null},
                new String[][] {null, syringeMatcher, null, null, null, null, null, null, null},
                new ItemStack(speedEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3, new ItemStack[] {null, diamondPick, null, reds, gold, reds, null, ink, null},
                new String[][] {null, pickMatcher, null, null, null, null, null, null, null},
                new ItemStack(hasteEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                new ItemStack[] {null, zombieSyringe, null, reds, gold, reds, null, ink, null},
                new String[][] {null, syringeMatcher, null, null, null, null, null, null, null},
                new ItemStack(saturationEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                new ItemStack[] {null, ghastSyringe, null, reds, gold, reds, null, ink, null},
                new String[][] {null, syringeMatcher, null, null, null, null, null, null, null},
                new ItemStack(flightEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                new ItemStack[] {null, squidSyringe, null, reds, gold, reds, null, ink, null},
                new String[][] {null, syringeMatcher, null, null, null, null, null, null, null},
                new ItemStack(waterBreathingEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                new ItemStack[] {null, caveSpiderSyringe, null, reds, gold, reds, null, ink, null},
                new String[][] {null, syringeMatcher, null, null, null, null, null, null, null},
                new ItemStack(nightVisionEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(2, 2,
                new ItemStack[]{new ItemStack(regenerationEModuleItem), ironGolemSyringe, ironGolemSyringe, null},
                new String[][] {null, syringeMatcher, syringeMatcher, null},
                new ItemStack(regenerationPlusEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(2, 2,
                new ItemStack[]{new ItemStack(speedEModuleItem), horseSyringe, horseSyringe, null},
                new String[][] {null, syringeMatcher, syringeMatcher, null},
                new ItemStack(speedPlusEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(2, 2,
                new ItemStack[]{new ItemStack(hasteEModuleItem), diamondPick, null, null},
                new String[][] {null, pickMatcher, null, null},
                new ItemStack(hastePlusEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(2, 2,
                new ItemStack[]{new ItemStack(saturationEModuleItem), zombieSyringe, zombieSyringe, null},
                new String[][] {null, syringeMatcher, syringeMatcher, null},
                new ItemStack(saturationPlusEModuleItem)));

        GameRegistry.addRecipe(new NBTMatchingRecipe(2, 2,
                new ItemStack[]{new ItemStack(featherFallingEModuleItem), chickenSyringe, batSyringe, null},
                new String[][] {null, syringeMatcher, syringeMatcher, null},
                new ItemStack(featherFallingPlusEModuleItem)));

        // @todo
//        GameRegistry.addRecipe(new ItemStack(peacefulEModuleItem, 1), " p ", "rgr", " i ", 'p', DimletConstructionSetup.peaceEssenceItem,
//                'r', reds, 'g', gold, 'i', ink);

        if (EnvironmentalConfiguration.blindnessAvailable) {
            GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                    new ItemStack[]{null, squidSyringe, null, lapis, obsidian, lapis, null, ink, null},
                    new String[][]{null, syringeMatcher, null, null, null, null, null, null, null},
                    new ItemStack(blindnessEModuleItem)));
        }

        if (EnvironmentalConfiguration.weaknessAvailable) {
            GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                    new ItemStack[]{null, batSyringe, null, lapis, obsidian, lapis, null, ink, null},
                    new String[][]{null, syringeMatcher, null, null, null, null, null, null, null},
                    new ItemStack(weaknessEModuleItem)));
        }

        if (EnvironmentalConfiguration.poisonAvailable) {
            GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                    new ItemStack[]{null, caveSpiderSyringe, null, lapis, obsidian, lapis, null, ink, null},
                    new String[][]{null, syringeMatcher, null, null, null, null, null, null, null},
                    new ItemStack(poisonEModuleItem)));
        }

        if (EnvironmentalConfiguration.slownessAvailable) {
            GameRegistry.addRecipe(new NBTMatchingRecipe(3, 3,
                    new ItemStack[]{null, new ItemStack(Items.clock), null, lapis, obsidian, lapis, null, ink, null},
                    new String[][]{null, syringeMatcher, null, null, null, null, null, null, null},
                    new ItemStack(slownessEModuleItem)));
        }
    }

    public static ItemStack createEnchantedItem(Item item, Enchantment effectId, int amount) {
        ItemStack stack = new ItemStack(item);
        Map enchant = new HashMap();
        enchant.put(effectId, amount);
        EnchantmentHelper.setEnchantments(enchant, stack);
        return stack;
    }

    public static ItemStack createMobSyringe(String mobName) {
//        ItemStack syringe = new ItemStack(DimletConstructionSetup.syringeItem);
//        NBTTagCompound tagCompound = new NBTTagCompound();
//        tagCompound.setString("mobName", mobName);
//        tagCompound.setInteger("level", DimletConstructionConfiguration.maxMobInjections);
//        syringe.setTagCompound(tagCompound);
        // @todo
        ItemStack syringe = new ItemStack(Items.glass_bottle);
        return syringe;
    }
}
