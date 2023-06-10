package plus.dragons.creeperfirework.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import plus.dragons.creeperfirework.fabric.misc.NetworkUtil;
import plus.dragons.creeperfirework.misc.FireworkManufacturer;

public class ClientCreeperFireworkFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkUtil.NOTIFY_CLIENT_FIREWORK, (client, handler, buf, responseSender) -> {
            if (client.world != null) {
                BlockPos pos = buf.readBlockPos();
                boolean b = buf.readBoolean();
                client.world.addFireworkParticle(pos.getX(), pos.getY() + 0.5F, pos.getZ(), 0, 0, 0, FireworkManufacturer.generate(b));
                if (b) {
                    client.world.addFireworkParticle(pos.getX(), pos.getY() + 2.5F, pos.getZ(), 0, 0, 0, FireworkManufacturer.generateRandomSpecial());
                }
                if (b) {
                    client.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, SoundCategory.HOSTILE, 8.0F, 2.0F);
                } else {
                    client.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.HOSTILE, 8.0F, 2.0F);
                }
            }
        });
    }
}
