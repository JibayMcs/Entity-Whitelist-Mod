package fr.zeamateis.entitywhitelist;

import net.minecraft.entity.EntityType;
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
            builder.comment("Common config settings")
                    .push("common");
            ForgeConfigSpec.BooleanValue spawningEntity;
            ForgeRegistries.ENTITIES.getEntries().forEach(entity -> {
                if (
                        !entity.getValue().equals(EntityType.ITEM) &&
                                !entity.getValue().equals(EntityType.ITEM_FRAME) &&
                                !entity.getValue().equals(EntityType.PAINTING) &&
                                !entity.getValue().equals(EntityType.PLAYER) &&
                                !entity.getValue().equals(EntityType.FISHING_BOBBER)
                )
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