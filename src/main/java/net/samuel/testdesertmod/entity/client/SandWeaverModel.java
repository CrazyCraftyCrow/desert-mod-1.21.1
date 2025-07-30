package net.samuel.testdesertmod.entity.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.samuel.testdesertmod.TestDesertMod;
import net.samuel.testdesertmod.entity.custom.SandWeaverEntity;

public class SandWeaverModel<T extends SandWeaverEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer SANDWEAVER = new EntityModelLayer(Identifier.of(TestDesertMod.MOD_ID, "sandweaver"), "main");

    private final ModelPart SandWeaver;
    private final ModelPart head;

    public SandWeaverModel(ModelPart root) {
        this.SandWeaver = root.getChild("SandWeaver");
        this.head = this.SandWeaver.getChild("head");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData SandWeaver = modelPartData.addChild("SandWeaver", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, -2.0F));

        ModelPartData head = SandWeaver.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -5.0F, -3.0F));

        ModelPartData upper_jaw = head.addChild("upper_jaw", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

        ModelPartData bunny_body = upper_jaw.addChild("bunny_body", ModelPartBuilder.create().uv(0, 30).cuboid(-3.0F, -2.0F, -10.0F, 6.0F, 5.0F, 17.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, -7.0F));

        ModelPartData bunny_left_arm = upper_jaw.addChild("bunny_left_arm", ModelPartBuilder.create().uv(70, 69).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 0.0F, -17.0F));

        ModelPartData bunny_right_arm = upper_jaw.addChild("bunny_right_arm", ModelPartBuilder.create().uv(14, 71).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 0.0F, -17.0F));

        ModelPartData bunny_left_thigh = upper_jaw.addChild("bunny_left_thigh", ModelPartBuilder.create().uv(0, 69).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -1.0F, -11.5F));

        ModelPartData bunny_right_thigh = upper_jaw.addChild("bunny_right_thigh", ModelPartBuilder.create().uv(70, 60).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -1.0F, -11.5F));

        ModelPartData head_bunny = upper_jaw.addChild("head_bunny", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, -17.0F));

        ModelPartData bunny_head = head_bunny.addChild("bunny_head", ModelPartBuilder.create().uv(0, 52).cuboid(-2.5F, -4.0F, -5.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.5F, 0.0F));

        ModelPartData bunny_left_ear = head_bunny.addChild("bunny_left_ear", ModelPartBuilder.create().uv(38, 52).cuboid(-0.5F, -5.0F, 0.0F, 2.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -1.5F, -1.0F));

        ModelPartData bunny_right_ear = head_bunny.addChild("bunny_right_ear", ModelPartBuilder.create().uv(32, 63).cuboid(-1.5F, -5.0F, 0.0F, 2.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -1.5F, -1.0F));

        ModelPartData bunny_nose = head_bunny.addChild("bunny_nose", ModelPartBuilder.create().uv(38, 58).cuboid(-0.5F, -2.5F, -5.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.5F, 0.0F));

        ModelPartData lower_jaw = head.addChild("lower_jaw", ModelPartBuilder.create(), ModelTransform.of(0.0F, 3.0F, 1.0F, 0.0873F, 0.0F, 0.0F));

        ModelPartData bunny_left_foot = lower_jaw.addChild("bunny_left_foot", ModelPartBuilder.create().uv(0, 61).cuboid(-7.0F, 5.5F, -4.2F, 1.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(9.0F, -5.8264F, -11.1787F));

        ModelPartData bunny_right_foot = lower_jaw.addChild("bunny_right_foot", ModelPartBuilder.create().uv(16, 63).cuboid(0.0F, 5.5F, -4.2F, 1.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -5.8264F, -11.1787F));

        ModelPartData lower_jaw_main = lower_jaw.addChild("lower_jaw_main", ModelPartBuilder.create().uv(46, 51).cuboid(-3.0F, -4.0F, 7.0F, 6.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.6736F, -15.3787F));

        ModelPartData body = SandWeaver.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData main_body = body.addChild("main_body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -9.0F, -5.0F, 8.0F, 8.0F, 22.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 2.0F));

        ModelPartData front_legs = body.addChild("front_legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.0F));

        ModelPartData right_front = front_legs.addChild("right_front", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, -6.0F, 1.0F));

        ModelPartData cube_r1 = right_front.addChild("cube_r1", ModelPartBuilder.create().uv(38, 60).cuboid(-1.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        ModelPartData claw2 = right_front.addChild("claw2", ModelPartBuilder.create(), ModelTransform.of(0.0F, 6.0F, -2.0F, 0.0F, 0.0F, 0.0436F));

        ModelPartData cube_r2 = claw2.addChild("cube_r2", ModelPartBuilder.create().uv(46, 71).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData cube_r3 = claw2.addChild("cube_r3", ModelPartBuilder.create().uv(38, 71).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -3.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData claw1 = right_front.addChild("claw1", ModelPartBuilder.create(), ModelTransform.of(-2.0F, 6.0F, -2.0F, 0.0F, 0.0F, 0.1745F));

        ModelPartData cube_r4 = claw1.addChild("cube_r4", ModelPartBuilder.create().uv(30, 71).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData cube_r5 = claw1.addChild("cube_r5", ModelPartBuilder.create().uv(22, 71).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -3.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData left_front = front_legs.addChild("left_front", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, -7.0F, 1.0F));

        ModelPartData cube_r6 = left_front.addChild("cube_r6", ModelPartBuilder.create().uv(38, 60).cuboid(-3.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        ModelPartData claw3 = left_front.addChild("claw3", ModelPartBuilder.create(), ModelTransform.of(2.0F, 7.0F, -2.0F, 0.0F, 0.0F, -0.1745F));

        ModelPartData cube_r7 = claw3.addChild("cube_r7", ModelPartBuilder.create().uv(62, 71).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData cube_r8 = claw3.addChild("cube_r8", ModelPartBuilder.create().uv(54, 71).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, -3.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData claw4 = left_front.addChild("claw4", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 7.0F, -2.0F, 0.0F, 0.0F, -0.0436F));

        ModelPartData cube_r9 = claw4.addChild("cube_r9", ModelPartBuilder.create().uv(74, 55).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData cube_r10 = claw4.addChild("cube_r10", ModelPartBuilder.create().uv(74, 51).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.0F, -3.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData back_legs = body.addChild("back_legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 15.0F));

        ModelPartData right_back = back_legs.addChild("right_back", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, -5.0F, 2.0F));

        ModelPartData big_right = right_back.addChild("big_right", ModelPartBuilder.create().uv(20, 52).cuboid(-2.0F, 0.0F, -3.0F, 3.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_foot = right_back.addChild("right_foot", ModelPartBuilder.create().uv(60, 11).cuboid(-2.0F, 0.0F, -7.0F, 3.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 3.0F));

        ModelPartData left_back = back_legs.addChild("left_back", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, -5.0F, 2.0F));

        ModelPartData big_left = left_back.addChild("big_left", ModelPartBuilder.create().uv(60, 0).cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_foot = left_back.addChild("left_foot", ModelPartBuilder.create().uv(60, 19).cuboid(-1.0F, 0.0F, -7.0F, 3.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 3.0F));

        ModelPartData tail = SandWeaver.addChild("tail", ModelPartBuilder.create().uv(46, 30).cuboid(-3.0F, -3.0F, -1.0F, 6.0F, 6.0F, 15.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, 19.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void setAngles(SandWeaverEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(SandWeaverAnimations.ANIM_SANDWEAVER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, SandWeaverAnimations.ANIM_SANDWEAVER_IDLE, ageInTicks, 1f);
        this.updateAnimation(entity.weaverBurrowedAnimationState, SandWeaverAnimations.ANIM_SANDWEAVER_BURROWING, ageInTicks, 1f);
        this.updateAnimation(entity.attackAnimationState, SandWeaverAnimations.ANIM_SANDWEAVER_ATTACK, ageInTicks, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -20.0F, 20.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        SandWeaver.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return SandWeaver;
    }
}