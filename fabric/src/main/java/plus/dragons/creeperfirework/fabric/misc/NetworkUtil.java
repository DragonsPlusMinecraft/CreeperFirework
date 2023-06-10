package plus.dragons.creeperfirework.fabric.misc;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class NetworkUtil {
    public static Identifier NOTIFY_CLIENT_FIREWORK = new Identifier("creeper_firework", "boom");

    public static void notifyClient(ServerWorld world, BlockPos pos, boolean powered) {
        List<ServerPlayerEntity> watchers = world.getPlayers(serverPlayerEntity -> serverPlayerEntity.getBlockPos().isWithinDistance(pos, 192));
        if (watchers.isEmpty()) return;
        PacketByteBuf packetByteBuf = PacketByteBufs.create();
        packetByteBuf.writeBlockPos(pos);
        packetByteBuf.writeBoolean(powered);
        for (ServerPlayerEntity player : watchers) {
            ServerPlayNetworking.send(player, NOTIFY_CLIENT_FIREWORK, packetByteBuf);
        }
    }
}
