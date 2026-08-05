package plus.dragons.creeperfirework.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import plus.dragons.creeperfirework.misc.FireworkManufacturer;
import plus.dragons.creeperfirework.network.Payload;

public class ClientCreeperFireworkFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // In your client-only initializer method
        ClientPlayNetworking.registerGlobalReceiver(Payload.TYPE, (payload, context) -> {
            var client = context.client();
            client.execute(() -> {
                if (client.level != null) {
                    BlockPos pos = payload.pos();
                    boolean b = payload.powered();
                    client.level.createFireworks(pos.getX(), pos.getY() + 0.5F + (b? 2: 0), pos.getZ(), 0, 0, 0, FireworkManufacturer.generate(b));
                    client.level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                            b ? SoundEvents.FIREWORK_ROCKET_LARGE_BLAST: SoundEvents.FIREWORK_ROCKET_BLAST,
                            SoundSource.HOSTILE, 8.0F, 2.0F);
                }
            });
        });
    }
}
