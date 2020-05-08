package fr.zeamateis.entitywhitelist;

import net.minecraft.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("entitywhitelist")
public class EntityWhitelistMod {

    public EntityWhitelistMod() {
        WhitelistConfig.register(ModLoadingContext.get());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        WhitelistConfig.WHITELIST.ENTITIES_WHITELIST.forEach((resourceLocation, booleanValue) -> {
            //System.out.println(resourceLocation + " : " + booleanValue.get());
            EntityType entityWhitelisted = ForgeRegistries.ENTITIES.getValue(resourceLocation);
            if (event.getEntity().getType().equals(entityWhitelisted))
                event.setCanceled(!booleanValue.get());
        });
    }
}
