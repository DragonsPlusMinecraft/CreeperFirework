package plus.dragons.creeperfirework.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.creeperfirework.Configuration;
import plus.dragons.creeperfirework.FireworkEffect;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {

    @Shadow
    private int explosionRadius;

    @Final
    @Shadow
    private static TrackedData<Boolean> CHARGED;

    @Shadow public abstract boolean shouldRenderOverlay();

    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "explode",  at = @At(
            value = "INVOKE", 
            target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"), 
            cancellable = true)
    private void injected(CallbackInfo ci) {
        if (Configuration.isCreeperExplodeIntoFirework() && Math.random() < Configuration.becomeFireworkChance()) {
            FireworkEffect.create((CreeperEntity) (Object) this);
            this.getWorld().createExplosion(this, Explosion.createDamageSource(this.getWorld(), this),
                    new EntityExplosionBehavior(this) {

                        @Override
                        public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
                            if (Configuration.isFireworkDestroyBlock()) return super.canDestroyBlock(explosion, world, pos, state, power);
                            return false;
                        }

                        @Override
                        public boolean shouldDamage(Explosion explosion, Entity entity) {
                            if (Configuration.isFireworkHurtCreature()) return super.shouldDamage(explosion, entity);
                            return false;
                        }
                    },
                    this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * (this.shouldRenderOverlay()? 2.0F : 1.0F), false, World.ExplosionSourceType.MOB);
            this.onRemoval(RemovalReason.KILLED);
            this.discard();
            ci.cancel();
        }

    }

    @Unique
    private float creeperFirework$getExplosionPower() {
        return this.getDataTracker().get(CHARGED) ? explosionRadius * 2 : explosionRadius;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if(!this.getWorld().isClient())
            if (Configuration.isCreeperExplodeIntoFireworkWhenDie() && Math.random() < Configuration.becomeFireworkChanceWhenDie()) {
                FireworkEffect.create((CreeperEntity) (Object) this);
                this.getWorld().createExplosion(this, Explosion.createDamageSource(this.getWorld(), this),
                        new EntityExplosionBehavior(this) {

                            @Override
                            public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
                                if (Configuration.isDeathFireworkDestroyBlock()) return super.canDestroyBlock(explosion, world, pos, state, power);
                                return false;
                            }

                            @Override
                            public boolean shouldDamage(Explosion explosion, Entity entity) {
                                if (Configuration.isDeathFireworkHurtCreature()) return super.shouldDamage(explosion, entity);
                                return false;
                            }
                        },
                        this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * (this.shouldRenderOverlay()? 2.0F : 1.0F), false, World.ExplosionSourceType.MOB);
            }
    }
}
