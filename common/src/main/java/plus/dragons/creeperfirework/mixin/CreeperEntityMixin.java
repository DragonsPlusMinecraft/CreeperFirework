package plus.dragons.creeperfirework.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.creeperfirework.Configuration;
import plus.dragons.creeperfirework.FireworkEffect;

@Mixin(Creeper.class)
public abstract class CreeperEntityMixin extends Monster {

    @Shadow
    private int explosionRadius;

    @Shadow public abstract boolean isPowered();

    @Shadow private void spawnLingeringCloud() {
        throw new AssertionError();
    }

    protected CreeperEntityMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "explodeCreeper", at = @At(
            value = "INVOKE", 
            target = "Lnet/minecraft/server/level/ServerLevel;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)V"),
            cancellable = true)
    private void injected(CallbackInfo ci) {
        if (Configuration.isCreeperExplodeIntoFirework() && Math.random() < Configuration.becomeFireworkChance()) {
            ServerLevel level = (ServerLevel) this.level();
            this.spawnLingeringCloud();
            FireworkEffect.create((Creeper) (Object) this);
            level.explode(this, Explosion.getDefaultDamageSource(level, this),
                    new EntityBasedExplosionDamageCalculator(this) {

                        @Override
                        public boolean shouldBlockExplode(Explosion explosion, BlockGetter level, BlockPos pos, BlockState state, float power) {
                            if (Configuration.isFireworkDestroyBlock()) return super.shouldBlockExplode(explosion, level, pos, state, power);
                            return false;
                        }

                        @Override
                        public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
                            if (Configuration.isFireworkHurtCreature()) return super.shouldDamageEntity(explosion, entity);
                            return false;
                        }
                    },
                    this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * (this.isPowered()? 2.0F : 1.0F), false, Level.ExplosionInteraction.MOB);
            this.triggerOnDeathMobEffects(level, RemovalReason.KILLED);
            this.discard();
            ci.cancel();
        }

    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        if(!this.level().isClientSide())
            if (Configuration.isCreeperExplodeIntoFireworkWhenDie() && Math.random() < Configuration.becomeFireworkChanceWhenDie()) {
                ServerLevel level = (ServerLevel) this.level();
                FireworkEffect.create((Creeper) (Object) this);
                level.explode(this, Explosion.getDefaultDamageSource(level, this),
                        new EntityBasedExplosionDamageCalculator(this) {

                            @Override
                            public boolean shouldBlockExplode(Explosion explosion, BlockGetter level, BlockPos pos, BlockState state, float power) {
                                if (Configuration.isDeathFireworkDestroyBlock()) return super.shouldBlockExplode(explosion, level, pos, state, power);
                                return false;
                            }

                            @Override
                            public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
                                if (Configuration.isDeathFireworkHurtCreature()) return super.shouldDamageEntity(explosion, entity);
                                return false;
                            }
                        },
                        this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * (this.isPowered()? 2.0F : 1.0F), false, Level.ExplosionInteraction.MOB);
            }
    }
}
