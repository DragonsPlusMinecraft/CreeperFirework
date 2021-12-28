package love.marblegate.creeperfirework.mixin;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import love.marblegate.creeperfirework.misc.Configuration;
import love.marblegate.creeperfirework.misc.NetworkUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Mixin(CreeperEntity.class)
public abstract class MixinCreeperEntity {

    @Shadow
    private int explosionRadius;

    @Final
    @Shadow
    private static TrackedData<Boolean> CHARGED;

    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    private void injected(CallbackInfo ci) {
        if (new Random(((CreeperEntity) (Object) this).getUuid().getLeastSignificantBits()).nextDouble() < Configuration.getRealTimeConfig().TURNING_PROBABILITY) {
            if (!((CreeperEntity) (Object) this).getWorld().isClient()) {

                NetworkUtil.notifyClient((ServerWorld) ((CreeperEntity) (Object) this).getWorld(), ((CreeperEntity) (Object) this).getBlockPos(), ((CreeperEntity) (Object) this).getDataTracker().get(CHARGED));
                if (Configuration.getRealTimeConfig().HURT_CREATURE) {
                    // Following code is following Explosion
                    Vec3d groundZero = ((CreeperEntity) (Object) this).getPos();
                    Box box = new Box(((CreeperEntity) (Object) this).getBlockPos()).expand(getExplosionPower());
                    List<LivingEntity> victims = ((CreeperEntity) (Object) this).getWorld().getNonSpectatingEntities(LivingEntity.class, box);
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
                                    victim.damage(DamageSource.explosion((CreeperEntity) (Object) this), (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) j + 1.0D)));
                                    double w = ProtectionEnchantment.transformExplosionKnockback((LivingEntity) victim, v);
                                    ;
                                    victim.setVelocity(victim.getVelocity().add(s * w, t * w, u * w));
                                }
                            }
                        }
                    }
                }

                if (Configuration.getRealTimeConfig().DESTROY_BLOCK && ((CreeperEntity) (Object) this).getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                    // Following code is following Explosion
                    ((CreeperEntity) (Object) this).getWorld().emitGameEvent(((CreeperEntity) (Object) this), GameEvent.EXPLODE, ((CreeperEntity) (Object) this).getBlockPos());
                    Set<BlockPos> explosionRange = Sets.newHashSet();
                    BlockPos groundZero = ((CreeperEntity) (Object) this).getBlockPos();
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
                                    float h = getExplosionPower() * (0.7F + ((CreeperEntity) (Object) this).getWorld().random.nextFloat() * 0.6F);
                                    double m = groundZero.getX();
                                    double n = groundZero.getY();
                                    double o = groundZero.getZ();
                                    System.out.println("Creeper is at " + groundZero);
                                    for (; h > 0.0F; h -= 0.22500001F) {
                                        BlockPos blockPos = new BlockPos(m, n, o);
                                        BlockState blockState = ((CreeperEntity) (Object) this).getWorld().getBlockState(blockPos);
                                        FluidState fluidState = ((CreeperEntity) (Object) this).getWorld().getFluidState(blockPos);
                                        if (!((CreeperEntity) (Object) this).getWorld().isInBuildLimit(blockPos)) {
                                            break;
                                        }

                                        Optional<Float> optional = blockState.isAir() && fluidState.isEmpty() ? Optional.empty() : Optional.of(Math.max(blockState.getBlock().getBlastResistance(), fluidState.getBlastResistance()));
                                        if (optional.isPresent()) {
                                            h -= (optional.get() + 0.3F) * 0.3F;
                                        }

                                        System.out.println();
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

                    System.out.println(explosionRange);

                    ObjectArrayList<Pair<ItemStack, BlockPos>> blockDropList = new ObjectArrayList<>();

                    /// I really do not want to create an explosion instance here. But there is a method below needs it.
                    Explosion simulateExplosionForParameter = new Explosion(((CreeperEntity) (Object) this).getWorld(), null, null, null,
                            ((CreeperEntity) (Object) this).getBlockX(), ((CreeperEntity) (Object) this).getBlockY(), ((CreeperEntity) (Object) this).getBlockZ(), getExplosionPower(), false, Explosion.DestructionType.DESTROY);

                    for (BlockPos affectedPos : explosionRange) {
                        BlockState blockStateOfAffected = ((CreeperEntity) (Object) this).getWorld().getBlockState(affectedPos);
                        Block block = blockStateOfAffected.getBlock();
                        if (!blockStateOfAffected.isAir()) {
                            BlockPos blockPos2 = affectedPos.toImmutable();
                            ((CreeperEntity) (Object) this).getWorld().getProfiler().push("explosion_blocks");

                            BlockEntity blockEntity = blockStateOfAffected.hasBlockEntity() ? ((CreeperEntity) (Object) this).getWorld().getBlockEntity(affectedPos) : null;
                            LootContext.Builder builder = (new LootContext.Builder((ServerWorld) ((CreeperEntity) (Object) this).getWorld())).random(((CreeperEntity) (Object) this).getWorld().random).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(affectedPos)).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity).optionalParameter(LootContextParameters.THIS_ENTITY, ((CreeperEntity) (Object) this));
                            builder.parameter(LootContextParameters.EXPLOSION_RADIUS, getExplosionPower());

                            blockStateOfAffected.getDroppedStacks(builder).forEach((stack) -> {
                                ExplosionMethodInvoker.invokeTryMergeStack(blockDropList, stack, blockPos2);
                            });

                            ((CreeperEntity) (Object) this).getWorld().setBlockState(affectedPos, Blocks.AIR.getDefaultState(), 3);

                            // yes here is what I'm talking. This part cannot be deleted.
                            block.onDestroyedByExplosion(((CreeperEntity) (Object) this).getWorld(), affectedPos, simulateExplosionForParameter);
                            ((CreeperEntity) (Object) this).getWorld().getProfiler().pop();
                        }
                    }

                    for (Pair<ItemStack, BlockPos> itemStackBlockPosPair : blockDropList) {
                        Block.dropStack(((CreeperEntity) (Object) this).getWorld(), itemStackBlockPosPair.getSecond(), itemStackBlockPosPair.getFirst());
                    }
                }
            }
            ((CreeperEntity) (Object) this).discard();
            ci.cancel();
        }

    }

    private float getExplosionPower() {
        return ((CreeperEntity) (Object) this).getDataTracker().get(CHARGED) ? explosionRadius * 2 : explosionRadius;
    }

}
