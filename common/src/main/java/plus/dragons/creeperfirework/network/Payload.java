package plus.dragons.creeperfirework.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record Payload(BlockPos pos, boolean powered)
        implements CustomPayload {

    public static final Id<Payload> ID = new Id<>(Identifier.of("creeper_firework", "firework_data"));

    public static final PacketCodec<ByteBuf, Payload> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.codec(BlockPos.CODEC),
            Payload::pos,
            PacketCodecs.BOOL,
            Payload::powered,
            Payload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
