package plus.dragons.creeperfirework.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Creeper;
import net.neoforged.neoforge.network.PacketDistributor;
import plus.dragons.creeperfirework.network.Payload;

import java.util.List;

public class FireworkEffectImpl {
    public static void create(Creeper creeper) {
        sendEffectPacket((ServerLevel) creeper.level(), creeper.blockPosition(), creeper.isPowered());
    }

    private static void sendEffectPacket(ServerLevel level, BlockPos pos, boolean powered) {
        List<ServerPlayer> players = level.getPlayers(player -> player.blockPosition().closerThan(pos, 192));
        for (var player : players) {
            PacketDistributor.sendToPlayer(player, new Payload(pos, powered));
        }
    }
}
