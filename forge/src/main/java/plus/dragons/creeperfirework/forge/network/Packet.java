package plus.dragons.creeperfirework.forge.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPacketListener;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;

import java.util.function.BiConsumer;

public class Packet {
    private final BlockPos pos;
    private final boolean powered;
    private static IProxy proxy;

    public Packet(PacketByteBuf buffer) {
        pos = buffer.readBlockPos();
        powered = buffer.readBoolean();
    }

    public Packet(BlockPos pos, boolean powered) {
        this.pos = pos;
        this.powered = powered;
    }

    public static void handle(Packet buf, CustomPayloadEvent.Context ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            if(proxy==null) proxy = new ClientProxy();
            ctx.enqueueWork(() -> proxy.handlePacket(buf.pos, buf.powered));
            ctx.setPacketHandled(true);
        });

    }

    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeBoolean(powered);
    }
}
