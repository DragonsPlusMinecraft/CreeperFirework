package plus.dragons.creeperfirework.neoforge;

import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.neoforged.neoforge.network.PacketDistributor;
import plus.dragons.creeperfirework.network.Payload;
import plus.dragons.creeperfirework.mixin.CreeperEntityAccessor;

import java.util.List;
import java.util.stream.Collectors;

public class FireworkEffectImpl {
    public static void create(CreeperEntity creeper) {
        sendEffectPacket(creeper.getWorld(), creeper.getBlockPos(), creeper.getDataTracker().get(CreeperEntityAccessor.getChargedTrackedDataKey()));
    }

    private static void sendEffectPacket(World level, BlockPos pos, boolean powered) {
        List<PlayerEntity> players = level.getPlayers().stream().filter(serverPlayerEntity -> serverPlayerEntity.getBlockPos().isWithinDistance(pos, 192)).collect(Collectors.toList());
        for (var player : players) {
            PacketDistributor.sendToPlayer((ServerPlayerEntity) player,new Payload(pos, powered));
        }
    }
}
