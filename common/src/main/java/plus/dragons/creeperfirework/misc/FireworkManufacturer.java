package plus.dragons.creeperfirework.misc;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.FireworkExplosion;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireworkManufacturer {
    public static final Random RNG = new Random();
    public static List<FireworkExplosion> generate(boolean powered) {
        var colors = genFireworkExplosionColorPart();
        List<FireworkExplosion> ret = new ArrayList<>();
        ret.add(new FireworkExplosion(powered ? FireworkExplosion.Shape.SMALL_BALL : randomSpecialType(),
                colors.left(),colors.right(),powered,powered));
        return ret;
    }

    private static FireworkExplosion.Shape randomSpecialType() {
        var i = RNG.nextInt(FireworkExplosion.Shape.values().length-1)+1;
        return FireworkExplosion.Shape.values()[i];
    }

    @NotNull
    private static Pair<IntList,IntList> genFireworkExplosionColorPart() {

        final IntList colors = new IntArrayList();
        final IntList fadeColors = new IntArrayList();

        // Generate Color From DyeColor
        // Maximum is 8 dyes for a firework star
        int dyeCount = RNG.nextInt(8) + 1;
        for (int i = 0; i < dyeCount; i++)
            colors.add(DyeColor.values()[RNG.nextInt(DyeColor.values().length)].getFireworkColor());
        for (int i = 0; i < dyeCount; i++)
            fadeColors.add(DyeColor.values()[RNG.nextInt(DyeColor.values().length)].getFireworkColor());

        return Pair.of(colors,fadeColors);
    }
}
