package plus.dragons.creeperfirework.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record Payload(BlockPos pos, boolean powered)
        implements CustomPacketPayload {

    public static final Type<Payload> TYPE = new Type<>(Identifier.fromNamespaceAndPath("creeper_firework", "firework_data"));

    public static final StreamCodec<ByteBuf, Payload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            Payload::pos,
            ByteBufCodecs.BOOL,
            Payload::powered,
            Payload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
