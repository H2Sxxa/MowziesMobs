package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

import java.util.Optional;

public class UmvuthiSunLayer extends GeoLayerRenderer<EntityUmvuthi> {
    protected Matrix4f dispatchedMat = new Matrix4f();
    protected Matrix4f renderEarlyMat = new Matrix4f();
    private final Vec3 v1 = new Vec3(-2,1,-1);
    private final Vec3 v2 = new Vec3(0,1,-1);
    private final Vec3 v3 = new Vec3(-1,0,1);
    private final Vec3 v4 = new Vec3(-1,0,-3);
    private final Vec3 v5 = new Vec3(-3,-1,0);
    private final Vec3 v6 = new Vec3(-3,-1,-2);
    private final Vec3 v7 = new Vec3(1,-1,0);
    private final Vec3 v8 = new Vec3(1,-1,-2);
    private final Vec3 v9 = new Vec3(0,-3,-1);
    private final Vec3 v10 = new Vec3(-2,-3,-1);
    private final Vec3 v11 = new Vec3(-1,-2,1);
    private final Vec3 v12 = new Vec3(-1,-2,-3);
    private final Vec3[] POS = {
            // Face 1
            v1,
            v2,
            v3,
            v1,
            // Face 2
            v1,
            v2,
            v4,
            v1,
            // Face 3
            v1,
            v5,
            v6,
            v1,
            // Face 4
            v2,
            v7,
            v8,
            v2,
            // Face 5
            v2,
            v8,
            v4,
            v2,
            // Face 6
            v1,
            v4,
            v6,
            v1,
            // Face 7
            v1,
            v5,
            v3,
            v1,
            // Face 8
            v2,
            v3,
            v7,
            v2,
            // Face 9
            v9,
            v7,
            v8,
            v9,
            // Face 10
            v5,
            v6,
            v10,
            v5,
            // Face 11
            v9,
            v10,
            v11,
            v9,
            // Face 12
            v9,
            v10,
            v12,
            v9,
            // Face 13
            v4,
            v6,
            v12,
            v4,
            // Face 14
            v4,
            v8,
            v12,
            v4,
            // Face 15
            v3,
            v5,
            v11,
            v3,
            // Face 16
            v3,
            v7,
            v11,
            v3,
            // Face 17
            v5,
            v10,
            v11,
            v5,
            // Face 18
            v7,
            v9,
            v11,
            v7,
            // Face 19
            v8,
            v9,
            v12,
            v8,
            // Face 20
            v6,
            v10,
            v12,
            v6,
    };

    public UmvuthiSunLayer(IGeoRenderer<EntityUmvuthi> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, EntityUmvuthi entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityLivingBaseIn.deathTime < 85 && entityLivingBaseIn.getActiveAbilityType() != EntityUmvuthi.SUPERNOVA_ABILITY) {
            poseStack.pushPose();
            GeoModel model = this.entityRenderer.getGeoModelProvider().getModel(this.entityRenderer.getGeoModelProvider().getModelLocation(entityLivingBaseIn));
            String boneName = "sun_render";
            Optional<GeoBone> bone = model.getBone(boneName);
            if (bone.isPresent() && !bone.get().isHidden()) {
                Matrix4f boneMatrix = bone.get().getModelSpaceXform();
                poseStack.mulPoseMatrix(boneMatrix);
                poseStack.translate(0.06d, 0d, -0.0d);
                poseStack.scale(0.06f, 0.06f, 0.06f);
                VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(new ResourceLocation(MowziesMobs.MODID, "textures/effects/sun_effect.png"), true));
                PoseStack.Pose matrixstack$entry = poseStack.last();
                Matrix4f matrix4f = matrixstack$entry.pose();
                Matrix3f matrix3f = matrixstack$entry.normal();
                drawSun(matrix4f, matrix3f, ivertexbuilder, packedLightIn, entityLivingBaseIn.tickCount + partialTicks);
            }
            poseStack.popPose();
        }
    }

    private void drawSun(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer builder, int packedLightIn, float time) {
        float scale = 0.9f + (float) Math.sin(time * 4) * 0.07f;
        for(int i = 0; i < 4; i++) {
            for (Vec3 vec : POS) {
                vec = vec.multiply(1f + (scale * i), 1f + (scale * i), 1f + (scale * i));
                builder.vertex(matrix4f, (float) ((float) vec.x + (scale * i)), (float) ((float) vec.y+ (scale * i)), (float) ((float) vec.z+ (scale * i)))
                        .color( 1f, 1f, .4f, 0.2f)
                        .uv(0.0f, 0.5f)
                        .overlayCoords(OverlayTexture.NO_OVERLAY)
                        .uv2(15728880)
                        .normal(matrix3f, 1f, 1f, 1f)
                        .endVertex();
            }
        }
        for (Vec3 vec : POS) {
            builder.vertex(matrix4f, (float) ((float) vec.x * 1.2f), (float) ((float) vec.y * 1.2f), (float) ((float) vec.z * 1.2f))
                    .color(1f, 1f, 1f, 1f)
                    .uv(0.0f, 0.5f)
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .uv2(15728880)
                    .normal(matrix3f, 1f, 1f, 1f)
                    .endVertex();
        }
    }
}
