package plus.dragons.creeperfirework.neoforge.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import plus.dragons.creeperfirework.misc.FireworkManufacturer;
import plus.dragons.creeperfirework.network.Payload;

public class ClientPayloadHandler {
    public static void handleDataOnMain(final Payload data, final IPayloadContext context) {
        var pos = data.pos();
        var powered = data.powered();
        context.enqueueWork(() -> {
                    var level = Minecraft.getInstance().level;
                    if (level == null) return;
                    level.createFireworks(pos.getX(), pos.getY() + 0.5F + (powered? 2: 0), pos.getZ(), 0, 0, 0, FireworkManufacturer.generate(powered));
                    level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                            powered? SoundEvents.FIREWORK_ROCKET_LARGE_BLAST: SoundEvents.FIREWORK_ROCKET_BLAST,
                            SoundSource.HOSTILE, 8.0F, 2.0F);
                })
                .exceptionally(e -> {
                    context.disconnect(Component.literal("Network Fail for Creeper Firework! Please Report! Error:" + e.getMessage()));
                    return null;
                });
    }
}
