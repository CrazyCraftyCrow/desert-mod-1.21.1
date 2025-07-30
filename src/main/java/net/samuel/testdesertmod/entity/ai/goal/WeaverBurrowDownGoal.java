package net.samuel.testdesertmod.entity.ai.goal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.samuel.testdesertmod.entity.custom.SandWeaverEntity;

public class WeaverBurrowDownGoal extends Goal {
    private final SandWeaverEntity sandy;
    private int burrowedDownTicks;
    private Block blockUnderMauler;
    private SoundEvent soundForBlockUnderMauler;
    private boolean isRunning;


    public WeaverBurrowDownGoal(SandWeaverEntity sandy) {
        this.sandy = sandy;
    }

    @Override
    public boolean canStart() {
        if (
                this.sandy.getNavigation().isFollowingPath()
                        || this.sandy.getRandom().nextFloat() < 0.999F
                        || this.sandy.getTicksUntilNextBurrowingDown() > 0
        ) {
            return false;
        }

        this.setBlockUnderMauler();

        boolean isRelatedBlock = (
                blockUnderMauler == Blocks.SAND
                        || blockUnderMauler == Blocks.RED_SAND
                        || blockUnderMauler == Blocks.DIRT
                        || blockUnderMauler == Blocks.COARSE_DIRT
                        || blockUnderMauler == Blocks.GRASS_BLOCK
        );

        return isRelatedBlock;
    }

    @Override
    public boolean shouldContinue() {
        return this.burrowedDownTicks > 0;
    }

    public void start() {
        this.isRunning = true;
        this.sandy.getNavigation().setSpeed(0);
        this.sandy.getNavigation().stop();

        if (this.getBurrowedDownTicks() == 0) {
            this.burrowedDownTicks = this.sandy.getRandom().nextBetween(600, 1200);
        }

        this.sandy.setBurrowedDown(true);
        this.sandy.setInvulnerable(true);

        if (this.blockUnderMauler == Blocks.SAND || this.blockUnderMauler == Blocks.RED_SAND) {
            this.soundForBlockUnderMauler = SoundEvents.BLOCK_SAND_BREAK;
        } else {
            this.soundForBlockUnderMauler = SoundEvents.BLOCK_GRASS_BREAK;
        }
    }

    @Override
    public void stop() {
        this.isRunning = false;
        this.sandy.setInvisible(false);
        this.sandy.setInvulnerable(false);
        this.sandy.setBurrowedDown(false);
        this.sandy.setTicksUntilNextBurrowingDown(
                this.sandy.getRandom().nextBetween(
                        SandWeaverEntity.MIN_TICKS_UNTIL_NEXT_BURROWING,
                        SandWeaverEntity.MAX_TICKS_UNTIL_NEXT_BURROWING
                )
        );
    }

    @Override
    public void tick() {
        float burrowingDownAnimationProgress = this.sandy.getBurrowingDownAnimationProgress();
        if (burrowingDownAnimationProgress > 0.0F && burrowingDownAnimationProgress < 1.0F) {
            BlockPos blockPos = this.sandy.getBlockPos();

            if (this.sandy.age % 3 == 0) {
                this.sandy.getWorld().playSound(null, blockPos, this.soundForBlockUnderMauler, SoundCategory.BLOCKS, 0.3F, 1.0F);
            }

            World world = this.sandy.getWorld();

            if (world.isClient()) {
                return;
            }

            for (int i = 0; i < 7; i++) {
                ((ServerWorld) world).spawnParticles(
                        new BlockStateParticleEffect(
                                ParticleTypes.BLOCK,
                                this.blockUnderMauler.getDefaultState()
                        ),
                        this.sandy.getParticleX(0.5D),
                        this.sandy.getRandomBodyY(),
                        this.sandy.getParticleZ(0.5D),
                        1,
                        this.sandy.getRandom().nextGaussian() * 0.02D,
                        this.sandy.getRandom().nextGaussian() * 0.02D,
                        this.sandy.getRandom().nextGaussian() * 0.02D,
                        0.1D
                );
            }
        } else if (this.sandy.isBurrowedDown() && this.sandy.getBurrowingDownAnimationProgress() == 1.0F) {
            //this.mauler.setInvisible(true);
        }

        this.burrowedDownTicks--;
    }

    public void setBlockUnderMauler() {
        BlockPos blockPos = this.sandy.getBlockPos().down();
        BlockState blockState = this.sandy.getWorld().getBlockState(blockPos);
        this.blockUnderMauler = blockState.getBlock();
    }

    public int getBurrowedDownTicks() {
        return this.burrowedDownTicks;
    }

    public void setBurrowedDownTicks(int burrowedDownTicks) {
        this.burrowedDownTicks = burrowedDownTicks;
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}
