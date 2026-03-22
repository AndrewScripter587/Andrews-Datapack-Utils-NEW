package com.andrewgaming.aputils.mixin;

import com.andrewgaming.aputils.EnderDragonSpeedAccessor;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(EnderDragon.class)
public class EnderDragonSpeedMixin implements EnderDragonSpeedAccessor {
    @Unique
    private double verticalSpeed = 1.0d;
    @Unique
    private double flySpeed = 1.0d;
    @ModifyArg(method = "aiStep",
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/phases/DragonPhaseInstance;getFlyTargetLocation()Lnet/minecraft/world/phys/Vec3;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;", ordinal = 0), index = 1)
    private double vertical(double y) {
        return y * verticalSpeed;
    }

    // @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/phases/DragonPhaseInstance;getFlySpeed()F",ordinal = -1))
    // public float flySpeed(float original) {
    //     return original * (float)flySpeed;
    // }

    @ModifyArg(method = "aiStep",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"),index = 1)
    public Vec3 flySpeed(Vec3 original) {
        return original.scale(flySpeed);
    }
    @Override
    public double getFlySpeed() {
        return flySpeed;
    }

    @Override
    public void setFlySpeed(double speed) {
        flySpeed = speed;
    }

    @Override
    public double getVerticalFlySpeed() {
        return verticalSpeed;
    }

    @Override
    public void setVerticalFlySpeed(double verticalFlySpeed) {
        verticalSpeed = verticalFlySpeed;
    }
}