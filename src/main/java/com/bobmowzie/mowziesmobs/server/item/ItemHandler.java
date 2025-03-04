package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.block.BlockHandler;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.creativetab.CreativeTabHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityDart;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = MowziesMobs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(MowziesMobs.MODID)
public final class ItemHandler {
    private ItemHandler() {}

    public static final ItemFoliaathSeed FOLIAATH_SEED = null;
    public static final ItemMobRemover MOB_REMOVER = null;
    public static final ItemWroughtAxe WROUGHT_AXE = null;
    public static final ItemWroughtHelm WROUGHT_HELMET = null;
    public static final ItemUmvuthanaMask UMVUTHANA_MASK_FURY = null;
    public static final ItemUmvuthanaMask UMVUTHANA_MASK_FEAR = null;
    public static final ItemUmvuthanaMask UMVUTHANA_MASK_RAGE = null;
    public static final ItemUmvuthanaMask UMVUTHANA_MASK_BLISS = null;
    public static final ItemUmvuthanaMask UMVUTHANA_MASK_MISERY = null;
    public static final ItemUmvuthanaMask UMVUTHANA_MASK_FAITH = null;
    public static final ItemSolVisage SOL_VISAGE = null;
    public static final ItemDart DART = null;
    public static final ItemSpear SPEAR = null;
    public static final ItemBlowgun BLOWGUN = null;
    public static final ItemGrantSunsBlessing GRANT_SUNS_BLESSING = null;
    public static final ItemIceCrystal ICE_CRYSTAL = null;
    public static final ItemEarthTalisman EARTH_TALISMAN = null;
    public static final ItemCapturedGrottol CAPTURED_GROTTOL = null;
    public static final ItemGlowingJelly GLOWING_JELLY = null;
    public static final ItemNagaFang NAGA_FANG = null;
    public static final ItemNagaFangDagger NAGA_FANG_DAGGER = null;
    public static final ItemEarthboreGauntlet EARTHBORE_GAUNTLET = null;
//    public static final ItemSculptorStaff SCULPTOR_STAFF = null;
    public static final Item LOGO = null;
    public static final RecordItem PETIOLE_MUSIC_DISC = null;

    public static final ForgeSpawnEggItem FOLIAATH_SPAWN_EGG = null;
    public static final ForgeSpawnEggItem WROUGHTNAUT_SPAWN_EGG = null;
    public static final ForgeSpawnEggItem UMVUTHANA_SPAWN_EGG = null;
    public static final ForgeSpawnEggItem UMVUTHANA_RAPTOR_SPAWN_EGG = null;
    public static final ForgeSpawnEggItem UMVUTHANA_CRANE_SPAWN_EGG = null;
    public static final ForgeSpawnEggItem UMVUTHI_SPAWN_EGG = null;
    public static final ForgeSpawnEggItem FROSTMAW_SPAWN_EGG = null;
    public static final ForgeSpawnEggItem GROTTOL_SPAWN_EGG = null;
    public static final ForgeSpawnEggItem LANTERN_SPAWN_EGG = null;
    public static final ForgeSpawnEggItem NAGA_SPAWN_EGG = null;
//    public static final ForgeSpawnEggItem SCULPTOR_SPAWN_EGG = null;

    public static Style TOOLTIP_STYLE = Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.GRAY));

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            new ItemFoliaathSeed(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("foliaath_seed"),
            new ItemMobRemover(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("mob_remover"),
            new ItemWroughtAxe(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).rarity(Rarity.UNCOMMON)).setRegistryName("wrought_axe"),
            new ItemWroughtHelm(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).rarity(Rarity.UNCOMMON)).setRegistryName("wrought_helmet"),
            new ItemUmvuthanaMask(MaskType.FURY, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("umvuthana_mask_fury"),
            new ItemUmvuthanaMask(MaskType.FEAR, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("umvuthana_mask_fear"),
            new ItemUmvuthanaMask(MaskType.RAGE, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("umvuthana_mask_rage"),
            new ItemUmvuthanaMask(MaskType.BLISS, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("umvuthana_mask_bliss"),
            new ItemUmvuthanaMask(MaskType.MISERY, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("umvuthana_mask_misery"),
            new ItemUmvuthanaMask(MaskType.FAITH, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("umvuthana_mask_faith"),
            new ItemSolVisage(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).rarity(Rarity.RARE)).setRegistryName("sol_visage"),
            new ItemDart(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("dart"),
            new ItemSpear(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).stacksTo(1)).setRegistryName("spear"),
            new ItemBlowgun(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).stacksTo(1).durability(300)).setRegistryName("blowgun"),
            new ItemGrantSunsBlessing(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).stacksTo(1).rarity(Rarity.EPIC)).setRegistryName("grant_suns_blessing"),
            new ItemIceCrystal(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).defaultDurability(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.durability.get()).rarity(Rarity.RARE)).setRegistryName("ice_crystal"),
            new ItemEarthTalisman(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).stacksTo(1).rarity(Rarity.EPIC)).setRegistryName("earth_talisman"),
            new ItemCapturedGrottol(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).stacksTo(1)).setRegistryName("captured_grottol"),
            new ItemGlowingJelly( new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).food(ItemGlowingJelly.GLOWING_JELLY_FOOD)).setRegistryName("glowing_jelly"),
            new ItemNagaFang(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("naga_fang"),
            new ItemNagaFangDagger(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("naga_fang_dagger"),
            new ItemEarthboreGauntlet(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).defaultDurability(ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.durability.get()).rarity(Rarity.RARE)).setRegistryName("earthbore_gauntlet"),
//            new ItemSculptorStaff(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).defaultDurability(1000).rarity(Rarity.RARE)).setRegistryName("sculptor_staff"),
//            new ItemSandRake(new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).defaultDurability(64)).setRegistryName("sand_rake"),
            new Item(new Item.Properties()).setRegistryName("logo"),
            new RecordItem(14, MMSounds.MUSIC_PETIOLE, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab).stacksTo(1).rarity(Rarity.RARE)).setRegistryName("music_disc_petiole"),
    
            new ForgeSpawnEggItem(EntityHandler.FOLIAATH, 0x47CC3B, 0xC03BCC, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("foliaath_spawn_egg"),
            new ForgeSpawnEggItem(EntityHandler.WROUGHTNAUT, 0x8C8C8C, 0xFFFFFF, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("wroughtnaut_spawn_egg"),
            new ForgeSpawnEggItem(EntityHandler.UMVUTHANA_MINION, 0xba5f1e, 0x3a2f2f, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("umvuthana_spawn_egg"),
            new ForgeSpawnEggItem(EntityHandler.UMVUTHANA_RAPTOR, 0xba5f1e, 0xf6f2f1, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("umvuthana_raptor_spawn_egg"),
            new ForgeSpawnEggItem(EntityHandler.UMVUTHANA_CRANE, 0xba5f1e, 0xfddc76, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("umvuthana_crane_spawn_egg"),
            new ForgeSpawnEggItem(EntityHandler.UMVUTHI, 0xf6f2f1, 0xba5f1e, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("umvuthi_spawn_egg"),
            new ForgeSpawnEggItem(EntityHandler.FROSTMAW, 0xf7faff, 0xafcdff, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("frostmaw_spawn_egg"),
            new ForgeSpawnEggItem(EntityHandler.GROTTOL, 0x777777, 0xbce0ff, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("grottol_spawn_egg"),
            new ForgeSpawnEggItem(EntityHandler.LANTERN, 0x6dea00, 0x235a10, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("lantern_spawn_egg"),
            new ForgeSpawnEggItem(EntityHandler.NAGA, 0x154850, 0x8dd759, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("naga_spawn_egg"),
//            new ForgeSpawnEggItem(EntityHandler.SCULPTOR, 0xc4a137, 0xfff5e7, new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName("sculptor_spawn_egg"),

            new BlockItem(BlockHandler.PAINTED_ACACIA.get(), new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName(BlockHandler.PAINTED_ACACIA.get().getRegistryName()),
            new BlockItem(BlockHandler.PAINTED_ACACIA_SLAB.get(), new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName(BlockHandler.PAINTED_ACACIA_SLAB.get().getRegistryName()),
            new BlockItem(BlockHandler.THATCH.get(), new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName(BlockHandler.THATCH.get().getRegistryName()),
//            new BlockItem(BlockHandler.GONG.get(), new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName(BlockHandler.GONG.get().getRegistryName()),
//            new BlockItem(BlockHandler.RAKED_SAND.get(), new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName(BlockHandler.RAKED_SAND.get().getRegistryName()),
            new BlockItem(BlockHandler.CLAWED_LOG.get(), new Item.Properties().tab(CreativeTabHandler.INSTANCE.creativeTab)).setRegistryName(BlockHandler.CLAWED_LOG.get().getRegistryName())
        );
    }

    public static void initializeAttributes() {
        WROUGHT_AXE.getAttributesFromConfig();
        WROUGHT_HELMET.getAttributesFromConfig();
        UMVUTHANA_MASK_FURY.getAttributesFromConfig();
        UMVUTHANA_MASK_FEAR.getAttributesFromConfig();
        UMVUTHANA_MASK_RAGE.getAttributesFromConfig();
        UMVUTHANA_MASK_BLISS.getAttributesFromConfig();
        UMVUTHANA_MASK_MISERY.getAttributesFromConfig();
        UMVUTHANA_MASK_FAITH.getAttributesFromConfig();
        SOL_VISAGE.getAttributesFromConfig();
        SPEAR.getAttributesFromConfig();
        NAGA_FANG_DAGGER.getAttributesFromConfig();
        EARTHBORE_GAUNTLET.getAttributesFromConfig();
    }

    public static void initializeDispenserBehaviors() {
        DispenserBlock.registerBehavior(DART, new AbstractProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
                EntityDart dartentity = new EntityDart(EntityHandler.DART.get(), worldIn, position.x(), position.y(), position.z());
                dartentity.pickup = AbstractArrow.Pickup.ALLOWED;
                return dartentity;
            }
        });
    }
}