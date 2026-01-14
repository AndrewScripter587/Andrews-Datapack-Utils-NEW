package com.andrewgaming.aputils;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class APUtilsForge {

    public APUtilsForge() {

        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();
        RegisterCommandsEvent.BUS.addListener(this::onRegisterCommands);
        TickEvent.ServerTickEvent.Post.BUS.addListener(this::onServerTick);
    }
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        SetupCommands.Init(dispatcher);
    }
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        SetupCommands.resetDamageFlags(event.server());
    }

}