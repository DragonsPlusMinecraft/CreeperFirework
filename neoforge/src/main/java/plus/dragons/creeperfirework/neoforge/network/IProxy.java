package plus.dragons.creeperfirework.neoforge.network;

import net.minecraft.util.math.BlockPos;

public interface IProxy {
    void handlePacket(BlockPos pos, boolean powered);
}
