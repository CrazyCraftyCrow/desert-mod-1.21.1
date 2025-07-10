package net.samuel.testdesertmod.block;


import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class QuickSandBlock extends Block {
    public QuickSandBlock(Settings settings) {
        super(settings);
    }

    private static boolean hasQuickSandBlockEffects(Entity entity) {
        return entity instanceof LivingEntity || entity instanceof AbstractMinecartEntity || entity instanceof TntEntity || entity instanceof BoatEntity;
    }

    private static boolean hasHoneyBlockEffects(Entity entity) {
        return entity instanceof LivingEntity || entity instanceof AbstractMinecartEntity || entity instanceof TntEntity || entity instanceof BoatEntity;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.playSound(SoundEvents.ITEM_BRUSH_BRUSHING_SAND, 1.0F, 1.0F);
        if (!world.isClient) {

        }

        if (entity.handleFallDamage(fallDistance, 0.2F, world.getDamageSources().fall())) {
            entity.playSound(this.soundGroup.getFallSound(), this.soundGroup.getVolume() * 0.5F, this.soundGroup.getPitch() * 0.75F);
        }
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (this.isSliding(pos, entity)) {
            this.updateSlidingVelocity(entity);
            this.addCollisionEffects(world, entity);
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    private boolean isSliding(BlockPos pos, Entity entity) {
        if (entity.isOnGround()) {
            return false;
        } else if (entity.getY() > pos.getY() + 0.9375 - 1.0E-7) {
            return false;
        } else if (entity.getVelocity().y >= -0.08) {
            return false;
        } else {
            double d = Math.abs(pos.getX() + 0.5 - entity.getX());
            double e = Math.abs(pos.getZ() + 0.5 - entity.getZ());
            double f = 0.4375 + entity.getWidth() / 2.0F;
            return d + 1.0E-7 > f || e + 1.0E-7 > f;
        }
    }

    private void updateSlidingVelocity(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < -0.13) {
            double d = -0.05 / vec3d.y;
            entity.setVelocity(new Vec3d(vec3d.x * d, -0.05, vec3d.z * d));
        } else {
            entity.setVelocity(new Vec3d(vec3d.x, -0.05, vec3d.z));
        }

        entity.onLanding();
    }

    private void addCollisionEffects(World world, Entity entity) {
        if (hasHoneyBlockEffects(entity)) {
            if (world.random.nextInt(5) == 0) {
                entity.playSound(SoundEvents.ITEM_BRUSH_BRUSHING_SAND, 1.0F, 1.0F);
            }
        }
    }

    private static void addParticles(Entity entity, int count) {
        if (entity.getWorld().isClient) {
            BlockState blockState = Blocks.HONEY_BLOCK.getDefaultState();

            for (int i = 0; i < count; i++) {
                entity.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), entity.getX(), entity.getY(), entity.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }
}
