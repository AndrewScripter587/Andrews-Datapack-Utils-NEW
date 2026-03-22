package com.andrewgaming.aputils.mixin;

import com.andrewgaming.aputils.PlayerExhaustionAccessor;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FoodData.class)
public class ExposeHungerValuesMixin implements PlayerExhaustionAccessor {
    @Shadow
    private float exhaustionLevel;


    @Override
    public float getExhaustionLevel() {
        return this.exhaustionLevel;
    }

    @Override
    public void setExhaustionLevel(float exhaustionLevel) {
        this.exhaustionLevel = exhaustionLevel;
    }
}
