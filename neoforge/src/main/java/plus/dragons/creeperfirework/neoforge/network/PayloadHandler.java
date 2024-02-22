package plus.dragons.creeperfirework.neoforge.network;

import net.minecraft.text.Text;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class PayloadHandler {
    private static final PayloadHandler INSTANCE = new PayloadHandler();
    private static IProxy proxy;

    public static PayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final Payload payload, final PlayPayloadContext context) {
        if(proxy==null) proxy = new ClientProxy();
        context.workHandler().submitAsync(() -> proxy.handlePacket(payload.pos(),payload.powered()))
                .exceptionally(e -> {
                    context.packetHandler().disconnect(Text.literal("Network Fail!" + e.getMessage()));
                    return null;
                });
    }
}
