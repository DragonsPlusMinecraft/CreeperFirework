package plus.dragons.creeperfirework.neoforge.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record Payload(BlockPos pos, boolean powered)
        implements CustomPayload {
    public static final Identifier ID = new Identifier("creeper_firework", "firework_data");

    public Payload(PacketByteBuf buffer){
        this(buffer.readBlockPos(),buffer.readBoolean());
    }

    @Override
    public void write(PacketByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(powered);
    }

    @Override
    public Identifier id() {
        return ID;
    }

}
