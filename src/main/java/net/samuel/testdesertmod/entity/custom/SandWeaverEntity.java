package net.samuel.testdesertmod.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.samuel.testdesertmod.entity.ModEntities;
import net.samuel.testdesertmod.entity.ai.goal.SandweaverAttackGoal;
import net.samuel.testdesertmod.entity.ai.goal.WeaverBurrowDownGoal;
import org.jetbrains.annotations.Nullable;

public class SandWeaverEntity extends AnimalEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState weaverBurrowedAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    public int attackAnimationTimeout = 0;

    public static final int MIN_TICKS_UNTIL_NEXT_BURROWING = 10;
    public static final int MAX_TICKS_UNTIL_NEXT_BURROWING = 20;

    private static final String IS_BURROWED_DOWN_NBT_NAME = "IsBurrowedDown";
    private static final String TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME = "TicksUntilNextBurrowingDown";
    private static final String BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME = "BurrowingDownAnimationProgress";
    private static final String BURROWED_DOWN_TICKS_NBT_NAME = "BurrowedDownTicks";


    public SandWeaverEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public WeaverBurrowDownGoal sandyBurrowDownGoal;

    private static final TrackedData<Boolean> IS_BURROWED_DOWN;
    private static final TrackedData<Integer> TICKS_UNTIL_NEXT_BURROWING_DOWN;
    private static final TrackedData<Float> BURROWING_DOWN_ANIMATION_PROGRESS;
    private static final TrackedData<Boolean> ATTACKING =
            DataTracker.registerData(SandWeaverEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    static {
        IS_BURROWED_DOWN = DataTracker.registerData(SandWeaverEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        TICKS_UNTIL_NEXT_BURROWING_DOWN = DataTracker.registerData(SandWeaverEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BURROWING_DOWN_ANIMATION_PROGRESS = DataTracker.registerData(SandWeaverEntity.class, TrackedDataHandlerRegistry.FLOAT);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean(IS_BURROWED_DOWN_NBT_NAME, this.isBurrowedDown());
        nbt.putInt(TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME, this.getTicksUntilNextBurrowingDown());
        nbt.putFloat(BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME, this.getBurrowingDownAnimationProgress());

        if (this.sandyBurrowDownGoal != null && this.isBurrowedDown() && this.sandyBurrowDownGoal.getBurrowedDownTicks() > 0) {
            nbt.putInt(BURROWED_DOWN_TICKS_NBT_NAME, this.sandyBurrowDownGoal.getBurrowedDownTicks());
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.setBurrowedDown(nbt.getBoolean(IS_BURROWED_DOWN_NBT_NAME));
        this.setTicksUntilNextBurrowingDown(nbt.getInt(TICKS_UNTIL_NEXT_BURROWING_DOWN_NBT_NAME));
        this.setBurrowingDownAnimationProgress(nbt.getFloat(BURROWING_DOWN_ANIMATION_PROGRESS_NBT_NAME));

        if (this.sandyBurrowDownGoal != null && this.isBurrowedDown() && nbt.contains(BURROWED_DOWN_TICKS_NBT_NAME)) {
            this.sandyBurrowDownGoal.setBurrowedDownTicks(nbt.getInt(BURROWED_DOWN_TICKS_NBT_NAME));
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
        builder.add(ATTACKING, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new AnimalMateGoal(this, 1.15D));
        this.goalSelector.add(2, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.ROTTEN_FLESH), false));

        this.goalSelector.add(3, new FollowParentGoal(this, 1.1D));

        this.goalSelector.add(4, new SandweaverAttackGoal(this, 1.0, true));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.sandyBurrowDownGoal = new WeaverBurrowDownGoal(this);
        this.goalSelector.add(7, this.sandyBurrowDownGoal);
        this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal(this, ZombieEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal(this, HuskEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, RabbitEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, DesertRainFrogEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5);
    }

    private void setupAnimationStates() {
        this.weaverBurrowedAnimationState.setRunning(isBurrowing(), this.age);

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 20;
            attackAnimationState.start(this.age);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

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
        return stack.isOf(Items.ROTTEN_FLESH);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SANDWEAVER.create(world);
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

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }

    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
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
        if (!this.getWorld().isClient() && this.sandyBurrowDownGoal.isRunning()) {
            this.sandyBurrowDownGoal.stop();
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
}
