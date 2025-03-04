package com.bobmowzie.mowziesmobs.client;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerFirstPerson;
import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerThirdPerson;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoFirstPersonRenderer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoRenderPlayer;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenCapability;
import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrozenController;
import com.bobmowzie.mowziesmobs.server.item.ItemBlowgun;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

@OnlyIn(Dist.CLIENT)
public enum ClientEventHandler {
    INSTANCE;

    private static final ResourceLocation FROZEN_BLUR = new ResourceLocation("textures/misc/powder_snow_outline.png");
    private static final ResourceLocation BOSS_BAR_LOCATION = new ResourceLocation(MowziesMobs.MODID, "textures/gui/boss_bar/umvuthi_bossbar.png");
    private static final ResourceLocation BOSS_BAR_OVERLAY_LOCATION = new ResourceLocation(MowziesMobs.MODID,"textures/gui/boss_bar/umvuthi_bar_overlay.png");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onHandRender(RenderHandEvent event) {
        if (!ConfigHandler.CLIENT.customPlayerAnims.get()) return;
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        boolean shouldAnimate = false;
        AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
        if (abilityCapability != null) shouldAnimate = abilityCapability.getActiveAbility() != null;
//        shouldAnimate = (player.ticksExisted / 20) % 2 == 0;
        if (shouldAnimate) {
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
            if (playerCapability != null) {
                GeckoPlayer.GeckoPlayerFirstPerson geckoPlayer = GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON;
                if (geckoPlayer != null) {
                    ModelGeckoPlayerFirstPerson geckoFirstPersonModel = (ModelGeckoPlayerFirstPerson) geckoPlayer.getModel();
                    GeckoFirstPersonRenderer firstPersonRenderer = (GeckoFirstPersonRenderer) geckoPlayer.getPlayerRenderer();

                    if (geckoFirstPersonModel != null && firstPersonRenderer != null) {
                        if (!geckoFirstPersonModel.isUsingSmallArms() && ((AbstractClientPlayer) player).getModelName().equals("slim")) {
                            firstPersonRenderer.setSmallArms();
                        }
                        event.setCanceled(true);

                        if (event.isCanceled()) {
                            float delta = event.getPartialTicks();
                            float f1 = Mth.lerp(delta, player.xRotO, player.getXRot());
                            firstPersonRenderer.renderItemInFirstPerson((AbstractClientPlayer) player, f1, delta, event.getHand(), event.getSwingProgress(), event.getItemStack(), event.getEquipProgress(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), geckoPlayer);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void renderLivingEvent(RenderLivingEvent.Pre<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity>> event) {
        if (event.getEntity() instanceof Player) {
            if (!ConfigHandler.CLIENT.customPlayerAnims.get()) return;
            Player player = (Player) event.getEntity();
            if (player == null) return;
            float delta = event.getPartialTick();
            AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(player);
//        shouldAnimate = (player.ticksExisted / 20) % 2 == 0;
            if (abilityCapability != null && abilityCapability.getActiveAbility() != null) {
                PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.PLAYER_CAPABILITY);
                if (playerCapability != null) {
                    GeckoPlayer.GeckoPlayerThirdPerson geckoPlayer = playerCapability.getGeckoPlayer();
                    if (geckoPlayer != null) {
                        ModelGeckoPlayerThirdPerson geckoPlayerModel = (ModelGeckoPlayerThirdPerson) geckoPlayer.getModel();
                        GeckoRenderPlayer animatedPlayerRenderer = (GeckoRenderPlayer) geckoPlayer.getPlayerRenderer();

                        if (geckoPlayerModel != null && animatedPlayerRenderer != null) {
                            event.setCanceled(true);

                            if (event.isCanceled()) {
                                animatedPlayerRenderer.render((AbstractClientPlayer) event.getEntity(), event.getEntity().getYRot(), delta, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), geckoPlayer);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        Player player = Minecraft.getInstance().player;
//        if (player != null) {
//            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
//            if (playerCapability != null && playerCapability.getGeomancy().canUse(player) && playerCapability.getGeomancy().isSpawningBoulder() && playerCapability.getGeomancy().getSpawnBoulderCharge() > 2) {
//                Vector3d lookPos = playerCapability.getGeomancy().getLookPos();
//                Vector3d playerEyes = player.getEyePosition(Minecraft.getInstance().getRenderPartialTicks());
//                Vector3d vec = playerEyes.subtract(lookPos).normalize();
//                float yaw = (float) Math.atan2(vec.z, vec.x);
//                float pitch = (float) Math.asin(vec.y);
//                player.rotationYaw = (float) (yaw * 180f/Math.PI + 90);
//                player.rotationPitch = (float) (pitch * 180f/Math.PI);
//                player.rotationYawHead = player.rotationYaw;
//                player.prevRotationYaw = player.rotationYaw;
//                player.prevRotationPitch = player.rotationPitch;
//                player.prevRotationYawHead = player.rotationYawHead;
//            }
        FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(player, CapabilityHandler.FROZEN_CAPABILITY);
        if (frozenCapability != null && frozenCapability.getFrozen() && frozenCapability.getPrevFrozen()) {
            player.setYRot(frozenCapability.getFrozenYaw());
            player.setXRot(frozenCapability.getFrozenPitch());
            player.yHeadRot = frozenCapability.getFrozenYawHead();
            player.yRotO = player.getYRot();
            player.xRotO = player.getXRot();
            player.yHeadRotO = player.yHeadRot;
        }
    }

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(entity, CapabilityHandler.FROZEN_CAPABILITY);
        if (frozenCapability != null && frozenCapability.getFrozen() && frozenCapability.getPrevFrozen()) {
            entity.setYRot(entity.yRotO = frozenCapability.getFrozenYaw());
            entity.setXRot(entity.xRotO = frozenCapability.getFrozenPitch());
            entity.yHeadRot = entity.yHeadRotO = frozenCapability.getFrozenYawHead();
            entity.yBodyRot = entity.yBodyRotO = frozenCapability.getFrozenRenderYawOffset();
            entity.attackAnim = entity.oAttackAnim = frozenCapability.getFrozenSwingProgress();
            entity.animationSpeed = entity.animationSpeedOld = frozenCapability.getFrozenLimbSwingAmount();
            entity.setShiftKeyDown(false);
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.PostLayer e) {
        final int startTime = 210;
        final int pointStart = 1200;
        final int timePerMillis = 22;
        if (e.getOverlay() == ForgeIngameGui.FROSTBITE_ELEMENT) {
            if (Minecraft.getInstance().player != null) {
                FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(Minecraft.getInstance().player, CapabilityHandler.FROZEN_CAPABILITY);
                if (frozenCapability != null && frozenCapability.getFrozen() && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
                    RenderSystem.setShaderTexture(0, FROZEN_BLUR);
                    Window res = e.getWindow();
                    GuiComponent.blit(e.getMatrixStack(), 0, 0, 0, 0, res.getGuiScaledWidth(), res.getGuiScaledHeight(), res.getGuiScaledWidth(), res.getGuiScaledHeight());
                }
            }
        }
    }

    // Remove frozen overlay
    @SubscribeEvent
    public void onRenderHUD(RenderGameOverlayEvent.PreLayer event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.isPassenger()) {
            if (player.getVehicle() instanceof EntityFrozenController) {
                if (event.getOverlay() == ForgeIngameGui.MOUNT_HEALTH_ELEMENT) {
                    event.setCanceled(true);
                }
                if (event.getType().equals(RenderGameOverlayEvent.ElementType.ALL)) {
                    Minecraft.getInstance().gui.setOverlayMessage(TextComponent.EMPTY, false);
                }
            }
        }
    }

    @SubscribeEvent
    public void updateFOV(FOVModifierEvent event) {
        Player player = event.getEntity();
        if (player.isUsingItem() && player.getUseItem().getItem() instanceof ItemBlowgun) {
            int i = player.getTicksUsingItem();
            float f1 = (float)i / 5.0F;
            if (f1 > 1.0F) {
                f1 = 1.0F;
            } else {
                f1 = f1 * f1;
            }

            event.setNewfov(1.0F - f1 * 0.15F);
        }
    }

    @SubscribeEvent
    public void onSetupCamera(EntityViewRenderEvent.CameraSetup event) {
        Player player = Minecraft.getInstance().player;
        float delta = Minecraft.getInstance().getFrameTime();
        float ticksExistedDelta = player.tickCount + delta;
        if (player != null) {
            if (ConfigHandler.CLIENT.doCameraShakes.get() && !Minecraft.getInstance().isPaused()) {
                float shakeAmplitude = 0;
                for (EntityCameraShake cameraShake : player.level.getEntitiesOfClass(EntityCameraShake.class, player.getBoundingBox().inflate(20, 20, 20))) {
                    if (cameraShake.distanceTo(player) < cameraShake.getRadius()) {
                        shakeAmplitude += cameraShake.getShakeAmount(player, delta);
                    }
                }
                if (shakeAmplitude > 1.0f) shakeAmplitude = 1.0f;
                event.setPitch((float) (event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3 + 2) * 25));
                event.setYaw((float) (event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5 + 1) * 25));
                event.setRoll((float) (event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4) * 25));
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START || event.player == null) {
            return;
        }
        Player player = event.player;
        PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
        if (playerCapability != null && event.side == LogicalSide.CLIENT) {
            GeckoPlayer geckoPlayer = playerCapability.getGeckoPlayer();
            if (geckoPlayer != null) geckoPlayer.tick();
            if (player == Minecraft.getInstance().player) GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON.tick();
        }
//        if(player.getInventory().getArmor(3).is(ItemHandler.SOL_VISAGE.asItem())){
//            int tick = player.tickCount;
//            double orbitSpeed = 50;
//            double orbitSize = 0.6;
//            double xOffset = (Math.sin(tick * orbitSpeed) * orbitSize);
//            double zOffset= (Math.cos(tick * orbitSpeed) * orbitSize);
//            Vec3 particleVec = Vec3.ZERO.add(xOffset, 2.2f, zOffset).yRot((float)Math.toRadians(-player.getYHeadRot())).xRot((float) Math.toRadians(0f)).add(player.position());
//            Vec3 particleVec2 = Vec3.ZERO.add(-xOffset, 2.2f, -zOffset).yRot((float)Math.toRadians(-player.getYHeadRot())).xRot((float) Math.toRadians(0f)).add(player.position());
//
//            player.level.addParticle(ParticleTypes.SMALL_FLAME, particleVec.x, particleVec.y, particleVec.z, 0d, 0d, 0d);
//            player.level.addParticle(ParticleTypes.SMALL_FLAME, particleVec2.x, particleVec2.y, particleVec2.z, 0d, 0d, 0d);
//
//        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBossBar(RenderGameOverlayEvent.BossInfo event){
        if(event.getBossEvent().getName().toString().contains("entity.mowziesmobs.umvuthi")){
            PoseStack stack = event.getMatrixStack();
            event.setCanceled(true);
            int y = event.getY();
            int i = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int j = y - 12;
            int k = i /2 - 91;
            Minecraft.getInstance().getProfiler().push("coolerBossBarBase");

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0,BOSS_BAR_LOCATION);
            drawBar(stack, event.getX() + 2, y , event.getBossEvent());
            Component component = event.getBossEvent().getName().copy().withStyle(ChatFormatting.GOLD);
            Minecraft.getInstance().getProfiler().pop();

            int l = Minecraft.getInstance().font.width(component);
            int i1 = i / 2 - l / 2;
            int j1 = j;
            Minecraft.getInstance().font.drawShadow(stack, component, (float)i1, (float)j1, 16777215);

            Minecraft.getInstance().getProfiler().push("coolerBossBar");
            RenderSystem.setShaderTexture(0,BOSS_BAR_OVERLAY_LOCATION);
            Minecraft.getInstance().gui.blit(stack, event.getX() - 12, y - 5, 0,0,196, 16, 196, 16);
            Minecraft.getInstance().getProfiler().pop();
        }
    }

    private void drawBar(PoseStack stack, int x, int y, BossEvent p_93710_) {
        Minecraft.getInstance().gui.blit(stack, x, y, 0, p_93710_.getColor().ordinal() * 5 * 2, 182, 6);
        int i = (int)(p_93710_.getProgress() * 183.0F);
        if (i > 0) {
            Minecraft.getInstance().gui.blit(stack, x, y, 0, p_93710_.getColor().ordinal() * 5 * 2 + 5, i, 6);
        }

    }
}
