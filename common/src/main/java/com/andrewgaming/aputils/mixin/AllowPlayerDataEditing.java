package com.andrewgaming.aputils.mixin;

import net.minecraft.server.commands.data.EntityDataAccessor;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityDataAccessor.class)
public class AllowPlayerDataEditing {


	@ModifyConstant(method = "setData", constant = @Constant(ordinal = 0, classValue = Player.class))
	private static boolean playerCheckBypass(Object obj, Class objclass){
		return false;
	}

}