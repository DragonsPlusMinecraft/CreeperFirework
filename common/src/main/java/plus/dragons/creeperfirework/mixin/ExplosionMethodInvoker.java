package plus.dragons.creeperfirework.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(Explosion.class)
public interface ExplosionMethodInvoker {
    @Invoker("tryMergeStack")
    static void invokeTryMergeStack(List<Pair<ItemStack, BlockPos>> stacks, ItemStack stack, BlockPos pos) {
        throw new AssertionError();
    }
}
