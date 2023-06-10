package plus.dragons.creeperfirework.forge.network;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import plus.dragons.creeperfirework.misc.FireworkManufacturer;

public class ClientProxy implements IProxy {
    @Override
    public void handlePacket(BlockPos pos, boolean powered) {
        MinecraftClient.getInstance().world.addFireworkParticle(pos.getX(), pos.getY() + 0.5F, pos.getZ(), 0, 0, 0, FireworkManufacturer.generate(powered));
        if (powered) {
            MinecraftClient.getInstance().world.addFireworkParticle(pos.getX(), pos.getY() + 2.5F, pos.getZ(), 0, 0, 0, FireworkManufacturer.generateRandomSpecial());
        }
        if (powered) {
            MinecraftClient.getInstance().world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, SoundCategory.HOSTILE, 8.0F, 2.0F);
        } else {
            MinecraftClient.getInstance().world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.HOSTILE, 8.0F, 2.0F);
        }
    }
}
