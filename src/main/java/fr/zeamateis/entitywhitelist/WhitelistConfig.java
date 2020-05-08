package fr.zeamateis.entitywhitelist;

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class WhitelistConfig {

    private static final ForgeConfigSpec whitelistSpec;
    public static final Whitelist WHITELIST;

    public static class Whitelist {
        public static Map<ResourceLocation, ForgeConfigSpec.BooleanValue> ENTITIES_WHITELIST = new HashMap<>();

        Whitelist(final ForgeConfigSpec.Builder builder) {
            builder.comment("All monsters entities").push("monsters");
            ForgeRegistries.ENTITIES.getEntries().stream()
                    .filter(predicate -> predicate.getValue().getClassification().equals(EntityClassification.MONSTER))
                    .forEach(entity -> {
                        ENTITIES_WHITELIST.put(entity.getKey(), builder.define(entity.getKey().toString(), true));
                    });
            builder.pop();
            builder.comment("All creatures entities").push("creatures");
            ForgeRegistries.ENTITIES.getEntries().stream()
                    .filter(predicate -> predicate.getValue().getClassification().equals(EntityClassification.CREATURE))
                    .forEach(entity -> {
                        ENTITIES_WHITELIST.put(entity.getKey(), builder.define(entity.getKey().toString(), true));
                    });
            builder.pop();
            builder.comment("All ambients entities").push("ambients");
            ForgeRegistries.ENTITIES.getEntries().stream()
                    .filter(predicate -> predicate.getValue().getClassification().equals(EntityClassification.AMBIENT))
                    .forEach(entity -> {
                        ENTITIES_WHITELIST.put(entity.getKey(), builder.define(entity.getKey().toString(), true));
                    });
            builder.pop();
            builder.comment("All aquatics entities").push("aquatics");
            ForgeRegistries.ENTITIES.getEntries().stream()
                    .filter(predicate -> predicate.getValue().getClassification().equals(EntityClassification.WATER_CREATURE))
                    .forEach(entity -> {
                        ENTITIES_WHITELIST.put(entity.getKey(), builder.define(entity.getKey().toString(), true));
                    });
            builder.pop();
            builder.comment("All miscellaneous entities", "Generally dont touch this!").push("miscellaneous");
            ForgeRegistries.ENTITIES.getEntries().stream()
                    .filter(predicate -> predicate.getValue().getClassification().equals(EntityClassification.MISC))
                    .forEach(entity -> {
                        ENTITIES_WHITELIST.put(entity.getKey(), builder.define(entity.getKey().toString(), true));
                    });
            builder.pop();
        }
    }

    static {
        final Pair<Whitelist, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Whitelist::new);
        whitelistSpec = specPair.getRight();
        WHITELIST = specPair.getLeft();
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, whitelistSpec, "entity-whitelist.toml");
    }
}