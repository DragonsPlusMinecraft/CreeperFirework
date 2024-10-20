package plus.dragons.creeperfirework.neoforge.network;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import plus.dragons.creeperfirework.misc.FireworkManufacturer;
import plus.dragons.creeperfirework.network.Payload;

public class ClientPayloadHandler {
    public static void handleDataOnMain(final Payload data, final IPayloadContext context) {
        var pos = data.pos();
        var powered = data.powered();
        context.enqueueWork(() -> {
                    MinecraftClient.getInstance().world.addFireworkParticle(pos.getX(), pos.getY() + 0.5F + (powered? 2: 0), pos.getZ(), 0, 0, 0, FireworkManufacturer.generate(powered));
                    MinecraftClient.getInstance().world.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                            powered? SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST: SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST,
                            SoundCategory.HOSTILE, 8.0F, 2.0F);
                })
                .exceptionally(e -> {
                    context.disconnect(Text.literal("Network Fail for Creeper Firework! Please Report! Error:" + e.getMessage()));
                    return null;
                });
    }
}
