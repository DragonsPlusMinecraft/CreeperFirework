package plus.dragons.creeperfirework.forge.network;

import net.minecraft.util.math.BlockPos;

public interface IProxy {
    void handlePacket(BlockPos pos, boolean powered);
}
