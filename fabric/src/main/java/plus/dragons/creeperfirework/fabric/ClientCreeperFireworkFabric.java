package plus.dragons.creeperfirework.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import plus.dragons.creeperfirework.misc.FireworkManufacturer;
import plus.dragons.creeperfirework.network.Payload;

public class ClientCreeperFireworkFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // In your client-only initializer method
        ClientPlayNetworking.registerGlobalReceiver(Payload.ID, (payload, context) -> {
            var client = context.client();
            client.execute(() -> {
                if (client.world != null) {
                    BlockPos pos = payload.pos();
                    boolean b = payload.powered();
                    client.world.addFireworkParticle(pos.getX(), pos.getY() + 0.5F + (b? 2: 0), pos.getZ(), 0, 0, 0, FireworkManufacturer.generate(b));
                    client.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                            b ? SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST: SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST,
                            SoundCategory.HOSTILE, 8.0F, 2.0F);
                }
            });
        });
    }
}
