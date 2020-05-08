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

    private static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    public static class Common {
        public static Map<ResourceLocation, ForgeConfigSpec.BooleanValue> ENTITIES_WHITELIST = new HashMap<>();

        Common(final ForgeConfigSpec.Builder builder) {
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
            builder.comment("All monsters aquatics").push("aquatics");
            ForgeRegistries.ENTITIES.getEntries().stream()
                    .filter(predicate -> predicate.getValue().getClassification().equals(EntityClassification.WATER_CREATURE))
                    .forEach(entity -> {
                        ENTITIES_WHITELIST.put(entity.getKey(), builder.define(entity.getKey().toString(), true));
                    });
            builder.pop();
            builder.comment("All monsters miscellaneous", "Generally dont touch this!").push("miscellaneous");
            ForgeRegistries.ENTITIES.getEntries().stream()
                    .filter(predicate -> predicate.getValue().getClassification().equals(EntityClassification.MISC))
                    .forEach(entity -> {
                        ENTITIES_WHITELIST.put(entity.getKey(), builder.define(entity.getKey().toString(), true));
                    });
            builder.pop();
        }
    }

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, commonSpec);
    }
}