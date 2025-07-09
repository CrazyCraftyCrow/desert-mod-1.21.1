package net.samuel.testdesertmod.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.samuel.testdesertmod.entity.ModEntities;
import net.samuel.testdesertmod.entity.ai.goal.BurrowDownGoal;
import org.jetbrains.annotations.Nullable;


public class DesertRainFrogEntity extends AnimalEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int burrowedAnimationTimeout = 500;

    public final AnimationState burrowedAnimationState = new AnimationState();

    //Burrowing start
    public static final int MIN_TICKS_UNTIL_NEXT_BURROWING = 3000;
    public static final int MAX_TICKS_UNTIL_NEXT_BURROWING = 6000;

    private static final String IS_BURROWED_DOWN_NBT_NAME = "IsBurrowedDown";
    private static final String TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME = "TicksUntilNextBurrowingDown";
    private static final String BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME = "BurrowingDownAnimationProgress";
    private static final String BURROWED_DOWN_TICKS_NBT_NAME = "BurrowedDownTicks";


    public DesertRainFrogEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public BurrowDownGoal burrowDownGoal;

    private static final TrackedData<Boolean> IS_BURROWED_DOWN;
    private static final TrackedData<Integer> TICKS_UNTIL_NEXT_BURROWING_DOWN;
    private static final TrackedData<Float> BURROWING_DOWN_ANIMATION_PROGRESS;

    static {
        IS_BURROWED_DOWN = DataTracker.registerData(DesertRainFrogEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        TICKS_UNTIL_NEXT_BURROWING_DOWN = DataTracker.registerData(DesertRainFrogEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BURROWING_DOWN_ANIMATION_PROGRESS = DataTracker.registerData(DesertRainFrogEntity.class, TrackedDataHandlerRegistry.FLOAT);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean(IS_BURROWED_DOWN_NBT_NAME, this.isBurrowedDown());
        nbt.putInt(TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME, this.getTicksUntilNextBurrowingDown());
        nbt.putFloat(BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME, this.getBurrowingDownAnimationProgress());

        if (this.burrowDownGoal != null && this.isBurrowedDown() && this.burrowDownGoal.getBurrowedDownTicks() > 0) {
            nbt.putInt(BURROWED_DOWN_TICKS_NBT_NAME, this.burrowDownGoal.getBurrowedDownTicks());
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.setBurrowedDown(nbt.getBoolean(IS_BURROWED_DOWN_NBT_NAME));
        this.setTicksUntilNextBurrowingDown(nbt.getInt(TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME));
        this.setBurrowingDownAnimationProgress(nbt.getFloat(BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME));

        if (this.burrowDownGoal != null && this.isBurrowedDown() && nbt.contains(BURROWED_DOWN_TICKS_NBT_NAME)) {
            this.burrowDownGoal.setBurrowedDownTicks(nbt.getInt(BURROWED_DOWN_TICKS_NBT_NAME));
            this.setInvulnerable(true);
            //this.setInvisible(true);
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(IS_BURROWED_DOWN, false);
        builder.add(TICKS_UNTIL_NEXT_BURROWING_DOWN, this.getRandom().nextBetween(MIN_TICKS_UNTIL_NEXT_BURROWING, MAX_TICKS_UNTIL_NEXT_BURROWING));
        builder.add(BURROWING_DOWN_ANIMATION_PROGRESS, 0.0F);
    }



    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new AnimalMateGoal(this, 1.15D));
        this.goalSelector.add(2, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.CACTUS), false));

        this.goalSelector.add(3, new FollowParentGoal(this, 1.1D));

        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 4.0F));
        this.burrowDownGoal = new BurrowDownGoal(this);
        this.goalSelector.add(6, this.burrowDownGoal);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        this.burrowedAnimationState.setRunning(isBurrowing(), this.age);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }

        if (this.getTicksUntilNextBurrowingDown() > 0) {
            this.setTicksUntilNextBurrowingDown(this.getTicksUntilNextBurrowingDown() - 1);
        }

        this.updateBurrowingDownAnimation();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.CACTUS);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.DESERT_RAIN_FROG.create(world);
    }

    public boolean isBurrowedDown() {
        return this.dataTracker.get(IS_BURROWED_DOWN);
    }

    public void setBurrowedDown(boolean isBurrowedDown) {
        this.dataTracker.set(IS_BURROWED_DOWN, isBurrowedDown);
    }

    public int getTicksUntilNextBurrowingDown() {
        return this.dataTracker.get(TICKS_UNTIL_NEXT_BURROWING_DOWN);
    }

    public void setTicksUntilNextBurrowingDown(int ticksUntilNextBurrowingDown) {
        this.dataTracker.set(TICKS_UNTIL_NEXT_BURROWING_DOWN, ticksUntilNextBurrowingDown);
    }

    public float getBurrowingDownAnimationProgress() {
        return this.dataTracker.get(BURROWING_DOWN_ANIMATION_PROGRESS);
    }

    public void setBurrowingDownAnimationProgress(float burrowingDownAnimationProgress) {
        this.dataTracker.set(BURROWING_DOWN_ANIMATION_PROGRESS, burrowingDownAnimationProgress);
    }

    private void updateBurrowingDownAnimation() {
        if (this.isBurrowedDown()) {
            this.setBurrowingDownAnimationProgress(Math.min(1.0F, this.getBurrowingDownAnimationProgress() + 0.04F));
        } else {
            this.setBurrowingDownAnimationProgress(Math.max(0.0F, this.getBurrowingDownAnimationProgress() - 0.04F));
        }
    }

    public void spawnParticles(
            ParticleEffect particleEffect,
            int amount
    ) {
        World world = this.getWorld();

        if (world.isClient()) {
            return;
        }

        for (int i = 0; i < amount; i++) {
            ((ServerWorld) world).spawnParticles(
                    particleEffect,
                    this.getParticleX(1.0D),
                    this.getRandomBodyY() + 0.5D,
                    this.getParticleZ(1.0D),
                    1,
                    this.getRandom().nextGaussian() * 0.02D,
                    this.getRandom().nextGaussian() * 0.02D,
                    this.getRandom().nextGaussian() * 0.02D,
                    1.0D
            );
        }
    }

    @Override
    public boolean isPushable() {
        return !this.isBurrowedDown() && super.isPushable();
    }

    @Override
    public void pushAway(Entity entity) {
        if (this.isBurrowedDown()) {
            return;
        }

        super.pushAway(entity);
    }

    public boolean damage(
            DamageSource source,
            float amount
    ) {
        if (!this.getWorld().isClient() && this.burrowDownGoal.isRunning()) {
            this.burrowDownGoal.stop();
        }

        return super.damage(source, amount);
    }

    public void tickMovement() {
        if (!this.getWorld().isClient() && this.getBurrowingDownAnimationProgress() > 0.0F) {
            this.getNavigation().setSpeed(0);
            this.getNavigation().stop();
        }

        super.tickMovement();

        if (this.getWorld().isClient()) {
            return;
        }
    }

    public boolean isBurrowing() {
        return this.dataTracker.get(IS_BURROWED_DOWN);
    }

    public static boolean canSpawn(EntityType<DesertRainFrogEntity> entity, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.RABBITS_SPAWNABLE_ON) && isLightLevelValidForNaturalSpawn(world, pos);
    }
}
