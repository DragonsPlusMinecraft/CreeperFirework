package plus.dragons.creeperfirework.misc;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class FireworkManufacturer {
    public static NbtCompound generate(boolean powered) {
        NbtCompound fireworkInfoNbt = getFireworkInfoNbt();
        fireworkInfoNbt.putBoolean("Flicker", powered);
        fireworkInfoNbt.putByte("Type", (byte) (powered ? 1 : 0));
        fireworkInfoNbt.putBoolean("Trail", powered);
        NbtList mimicFireworkItemNbtStructureContainer = new NbtList();
        mimicFireworkItemNbtStructureContainer.add(fireworkInfoNbt);

        NbtCompound ret = new NbtCompound();
        ret.put("Explosions", mimicFireworkItemNbtStructureContainer);
        return ret;
    }

    public static NbtCompound generateRandomSpecial() {
        NbtCompound fireworkInfoNbt = getFireworkInfoNbt();
        fireworkInfoNbt.putByte("Type", (byte) (new Random().nextInt(3) + 2));
        NbtList mimicFireworkItemNbtStructureContainer = new NbtList();
        mimicFireworkItemNbtStructureContainer.add(fireworkInfoNbt);

        NbtCompound ret = new NbtCompound();
        ret.put("Explosions", mimicFireworkItemNbtStructureContainer);
        return ret;
    }

    @NotNull
    private static NbtCompound getFireworkInfoNbt() {
        NbtCompound fireworkInfoNbt = new NbtCompound();

        // Generate Color From DyeColor
        // Maximum is 8 dyes for a firework star
        List<Integer> list = Lists.newArrayList();
        Random rand = new Random();
        int dyeCount = rand.nextInt(8) + 1;
        for (int i = 0; i < dyeCount; i++)
            list.add(DyeColor.values()[rand.nextInt(DyeColor.values().length)].getFireworkColor());

        // Transform color array into required structure
        int[] colours = new int[list.size()];
        for (int i = 0; i < colours.length; i++)
            colours[i] = list.get(i);

        fireworkInfoNbt.putIntArray("Colors", colours);
        return fireworkInfoNbt;
    }
}
