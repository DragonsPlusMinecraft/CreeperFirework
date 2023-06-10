package plus.dragons.creeperfirework.mixin;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.creeperfirework.Configuration;
import plus.dragons.creeperfirework.FireworkEffect;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {

    @Shadow
    private int explosionRadius;

    @Final
    @Shadow
    private static TrackedData<Boolean> CHARGED;

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
            if (Configuration.isFireworkHurtCreature())
                simulateExplodeHurtMob();
            if (Configuration.isFireworkDestroyBlock() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING))
                simulateExplodeDestroyBlock();
            this.discard();
            ci.cancel();
        }

    }

    private float getExplosionPower() {
        return this.getDataTracker().get(CHARGED) ? explosionRadius * 2 : explosionRadius;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if(!this.getWorld().isClient())
            if (Configuration.isCreeperExplodeIntoFireworkWhenDie() && Math.random() < Configuration.becomeFireworkChanceWhenDie()) {
                FireworkEffect.create((CreeperEntity) (Object) this);
                if (Configuration.isDeathFireworkHurtCreature())
                    simulateExplodeHurtMob();
                if (Configuration.isDeathFireworkDestroyBlock() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING))
                    simulateExplodeDestroyBlock();
            }
    }

    private void simulateExplodeHurtMob() {
        Vec3d groundZero = this.getPos();
        Box box = new Box(this.getBlockPos()).expand(getExplosionPower());
        List<LivingEntity> victims = this.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
        victims.remove(this);
        for (LivingEntity victim : victims) {
            if (!victim.isImmuneToExplosion()) {
                float j = getExplosionPower() * 2.0F;
                double h = Math.sqrt(victim.squaredDistanceTo(groundZero)) / (double) j;
                if (h <= 1.0D) {
                    double s = victim.getX() - groundZero.x;
                    double t = victim.getEyeY() - groundZero.y;
                    double u = victim.getZ() - groundZero.z;
                    double blockPos = Math.sqrt(s * s + t * t + u * u);
                    if (blockPos != 0.0D) {
                        s /= blockPos;
                        t /= blockPos;
                        u /= blockPos;
                        double fluidState = Explosion.getExposure(groundZero, victim);
                        double v = (1.0D - h) * fluidState;
                        victim.damage(getDamageSources().explosion((CreeperEntity) (Object) this,null), (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) j + 1.0D)));
                        double w = ProtectionEnchantment.transformExplosionKnockback(victim, v);

                        victim.setVelocity(victim.getVelocity().add(s * w, t * w, u * w));
                    }
                }
            }
        }
    }


    private void simulateExplodeDestroyBlock(){
        this.getWorld().emitGameEvent(this, GameEvent.EXPLODE, this.getBlockPos());
        Set<BlockPos> explosionRange = Sets.newHashSet();
        BlockPos groundZero = this.getBlockPos();
        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                for (int l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d = (float) j / 15.0F * 2.0F - 1.0F;
                        double e = (float) k / 15.0F * 2.0F - 1.0F;
                        double f = (float) l / 15.0F * 2.0F - 1.0F;
                        double g = Math.sqrt(d * d + e * e + f * f);
                        d /= g;
                        e /= g;
                        f /= g;
                        float h = getExplosionPower() * (0.7F + this.getWorld().random.nextFloat() * 0.6F);
                        double m = groundZero.getX();
                        double n = groundZero.getY();
                        double o = groundZero.getZ();
                        for (; h > 0.0F; h -= 0.22500001F) {
                            BlockPos blockPos = new BlockPos((int) m, (int) n, (int) o);
                            BlockState blockState = this.getWorld().getBlockState(blockPos);
                            FluidState fluidState = this.getWorld().getFluidState(blockPos);
                            if (!this.getWorld().isInBuildLimit(blockPos)) {
                                break;
                            }

                            Optional<Float> optional = blockState.isAir() && fluidState.isEmpty() ? Optional.empty() : Optional.of(Math.max(blockState.getBlock().getBlastResistance(), fluidState.getBlastResistance()));
                            if (optional.isPresent()) {
                                h -= (optional.get() + 0.3F) * 0.3F;
                            }

                            if (h > 0.0F) {
                                explosionRange.add(blockPos);
                            }

                            m += d * 0.30000001192092896D;
                            n += e * 0.30000001192092896D;
                            o += f * 0.30000001192092896D;
                        }
                    }
                }
            }
        }

        ObjectArrayList<Pair<ItemStack, BlockPos>> blockDropList = new ObjectArrayList<>();

        /// I really do not want to create an explosion instance here. But there is a method below needs it.
        Explosion simulateExplosionForParameter = new Explosion(this.getWorld(), null, null, null,
                this.getBlockX(), this.getBlockY(), this.getBlockZ(), getExplosionPower(), false, Explosion.DestructionType.DESTROY);

        for (BlockPos affectedPos : explosionRange) {
            BlockState blockStateOfAffected = this.getWorld().getBlockState(affectedPos);
            Block block = blockStateOfAffected.getBlock();
            if (!blockStateOfAffected.isAir()) {
                BlockPos blockPos2 = affectedPos.toImmutable();
                this.getWorld().getProfiler().push("explosion_blocks");

                BlockEntity blockEntity = blockStateOfAffected.hasBlockEntity() ? this.getWorld().getBlockEntity(affectedPos) : null;
                LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld) this.getWorld())
                        .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(affectedPos))
                        .add(LootContextParameters.TOOL, ItemStack.EMPTY)
                        .addOptional(LootContextParameters.BLOCK_ENTITY, blockEntity)
                        .addOptional(LootContextParameters.THIS_ENTITY, this)
                        .add(LootContextParameters.EXPLOSION_RADIUS, getExplosionPower());

                blockStateOfAffected.getDroppedStacks(builder).forEach((stack) -> {
                    ExplosionMethodInvoker.invokeTryMergeStack(blockDropList, stack, blockPos2);
                });

                this.getWorld().setBlockState(affectedPos, Blocks.AIR.getDefaultState(), 3);

                // yes here is what I'm talking. This part cannot be deleted.
                block.onDestroyedByExplosion(this.getWorld(), affectedPos, simulateExplosionForParameter);
                this.getWorld().getProfiler().pop();
            }
        }

        for (Pair<ItemStack, BlockPos> itemStackBlockPosPair : blockDropList) {
            Block.dropStack(this.getWorld(), itemStackBlockPosPair.getSecond(), itemStackBlockPosPair.getFirst());
        }
    }

}
