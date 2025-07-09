package net.samuel.testdesertmod.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.samuel.testdesertmod.TestDesertMod;
import net.samuel.testdesertmod.entity.custom.DesertRainFrogEntity;


public class DesertRainFrogModel <T extends DesertRainFrogEntity> extends SinglePartEntityModel<T>{

        public static final EntityModelLayer DESERT_RAIN_FROG = new EntityModelLayer(Identifier.of(TestDesertMod.MOD_ID, "desert_rain_frog"), "main");

        private final ModelPart frog;
        private final ModelPart body;

        public DesertRainFrogModel(ModelPart root) {
            this.frog = root.getChild("frog");
            this.body = this.frog.getChild("body");
        }
        public static TexturedModelData getTexturedModelData() {
            ModelData modelData = new ModelData();
            ModelPartData modelPartData = modelData.getRoot();
            ModelPartData frog = modelPartData.addChild("frog", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 23.0F, 1.0F));

            ModelPartData body = frog.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -2.0F, -3.0F, 5.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -1.0F, 0.0F));

            ModelPartData front_leg_left = frog.addChild("front_leg_left", ModelPartBuilder.create().uv(4, 14).cuboid(0.0F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                    .uv(0, 12).cuboid(0.0F, 2.0F, -2.0F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -1.0F, -1.0F, 0.0F, -0.1745F, 0.0F));

            ModelPartData front_leg_right = frog.addChild("front_leg_right", ModelPartBuilder.create().uv(8, 14).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                    .uv(6, 12).cuboid(-1.0F, 2.0F, -2.0F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -1.0F, -1.0F, 0.0F, 0.1745F, 0.0F));

            ModelPartData back_leg_right = frog.addChild("back_leg_right", ModelPartBuilder.create().uv(12, 12).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                    .uv(8, 10).cuboid(-2.0F, 2.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -1.0F, 3.0F));

            ModelPartData back_leg_left = frog.addChild("back_leg_left", ModelPartBuilder.create().uv(0, 14).cuboid(0.0F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                    .uv(0, 10).cuboid(0.0F, 2.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -1.0F, 3.0F));
            return TexturedModelData.of(modelData, 32, 32);
        }
        @Override
        public void setAngles(DesertRainFrogEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.getPart().traverse().forEach(ModelPart::resetTransform);
            this.setHeadAngles(netHeadYaw, headPitch);


            this.animateMovement(DesertRainFrogAnimations.ANIM_DESERT_RAIN_FROG_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
            this.updateAnimation(entity.idleAnimationState, DesertRainFrogAnimations.ANIM_DESERT_RAIN_FROG_IDLE, ageInTicks, 1f);
            this.updateAnimation(entity.burrowedAnimationState, DesertRainFrogAnimations.BURROWINGDOWN, ageInTicks, 1f);

        }


        private void setHeadAngles(float headYaw, float headPitch) {
            headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
            headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

            this.body.yaw = headYaw * 0.017453292F;
            this.body.pitch = headPitch * 0.017453292F;
        }


        @Override
        public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
            frog.render(matrices, vertexConsumer, light, overlay, color);
        }

        @Override
        public ModelPart getPart() {
            return frog;
        }
    }

