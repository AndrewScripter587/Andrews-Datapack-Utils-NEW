package com.andrewgaming.aputils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.hurtingprojectile.DragonFireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.Fireball;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.andrewgaming.aputils.platform.Services.PLATFORM;
import static net.minecraft.commands.Commands.*;

public class SetupCommands{
    public static LiteralCommandNode<CommandSourceStack> Init(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext, CommandSelection commandSelection) {return SetupCommands.Init(dispatcher);}
    public static LiteralCommandNode<CommandSourceStack> Init(CommandDispatcher<CommandSourceStack> dispatcher) {
        Logger LOGGER = AndrewsPackUtilities.LOGGER;
        LiteralArgumentBuilder<CommandSourceStack> main = literal("aputils")
                .then(literal("calc")
                        .then(literal("add")
                                .then(argument("value1", DoubleArgumentType.doubleArg())
                                        .then(argument("value2", DoubleArgumentType.doubleArg())
                                                .executes(context -> {
                                                    // For versions below 1.19, replace "Text.literal" with "new LiteralText".
                                                    // For versions below 1.20, remode "() ->" directly.
                                                    final double value1 = DoubleArgumentType.getDouble(context, "value1");
                                                    final double value2 = DoubleArgumentType.getDouble(context, "value2");
                                                    final double result = value1 + value2;
                                                    context.getSource().sendSuccess(() -> Component.literal(value1 + " + " + value2 + " = " + result), false);

                                                    return (int) result;
                                                })
                                        )
                                ))
                        .then(literal("sub")
                                .then(argument("value1", DoubleArgumentType.doubleArg())
                                        .then(argument("value2", DoubleArgumentType.doubleArg())
                                                .executes(context -> {
                                                    // For versions below 1.19, replace "Text.literal" with "new LiteralText".
                                                    // For versions below 1.20, remode "() ->" directly.
                                                    final double value1 = DoubleArgumentType.getDouble(context, "value1");
                                                    final double value2 = DoubleArgumentType.getDouble(context, "value2");
                                                    final double result = value1 + value2;
                                                    context.getSource().sendSuccess(() -> Component.literal(String.valueOf(value1) + " - " + String.valueOf(value2) + " = " + result), false);

                                                    return (int) result;
                                                })
                                        )
                                )
                        )
                        .then(literal("mul")
                                .then(argument("value1", DoubleArgumentType.doubleArg())
                                        .then(argument("value2", DoubleArgumentType.doubleArg())
                                                .executes(context -> {
                                                    // For versions below 1.19, replace "Text.literal" with "new LiteralText".
                                                    // For versions below 1.20, remode "() ->" directly.
                                                    final double value1 = DoubleArgumentType.getDouble(context, "value1");
                                                    final double value2 = DoubleArgumentType.getDouble(context, "value2");
                                                    final double result = value1 * value2;
                                                    context.getSource().sendSuccess(() -> Component.literal(String.valueOf(value1) + " * " + String.valueOf(value2) + " = " + result), false);

                                                    return (int) result;
                                                })
                                        )
                                )
                        )
                        .then(literal("div")
                                .then(argument("value1", DoubleArgumentType.doubleArg())
                                        .then(argument("value2", DoubleArgumentType.doubleArg())
                                                .executes(context -> {
                                                    // For versions below 1.19, replace "Text.literal" with "new LiteralText".
                                                    // For versions below 1.20, remode "() ->" directly.
                                                    final double value1 = DoubleArgumentType.getDouble(context, "value1");
                                                    final double value2 = DoubleArgumentType.getDouble(context, "value2");
                                                    final double result = value1 / value2;
                                                    context.getSource().sendSuccess(() -> Component.literal(String.valueOf(value1) + " / " + String.valueOf(value2) + " = " + result), false);

                                                    return (int) result;
                                                })
                                        )
                                )
                        )
                        .then(literal("power")
                                .then(argument("value1", DoubleArgumentType.doubleArg())
                                        .then(argument("value2", DoubleArgumentType.doubleArg())
                                                .executes(context -> {
                                                    // For versions below 1.19, replace "Text.literal" with "new LiteralText".
                                                    // For versions below 1.20, remode "() ->" directly.
                                                    final double value1 = DoubleArgumentType.getDouble(context, "value1");
                                                    final double value2 = DoubleArgumentType.getDouble(context, "value2");
                                                    final double result = Math.pow((double) value1, value2);
                                                    context.getSource().sendSuccess(() -> Component.literal(value1 + " ^ " + value2 + " = " + result), false);

                                                    return (int) result;
                                                })
                                        )
                                )
                        )
                        .then(literal("sqrt")
                                .then(argument("value1", DoubleArgumentType.doubleArg())
                                        .executes(context -> {
                                            // For versions below 1.19, replace "Text.literal" with "new LiteralText".
                                            // For versions below 1.20, remode "() ->" directly.
                                            final double value1 = DoubleArgumentType.getDouble(context, "value1");
                                            final double result = Math.sqrt(value1);
                                            context.getSource().sendSuccess(() -> Component.literal("Sqrt of " + value1 + " = " + result), false);

                                            return (int) result;
                                        })
                                )
                        )
                        .then(literal("distance")
                                .requires(serverCommandSource -> Commands.hasPermission(LEVEL_GAMEMASTERS).test(serverCommandSource)))
                        .then(argument("pos1", Vec3Argument.vec3(true))
                                .then(argument("pos2", Vec3Argument.vec3(true))
                                        .executes(context -> calcDist(context, Vec3Argument.getVec3(context, "pos1"), Vec3Argument.getVec3(context, "pos2")))
                                        .then(argument("scale",DoubleArgumentType.doubleArg())
                                                .executes(context -> calcDist(context, Vec3Argument.getVec3(context, "pos1"), Vec3Argument.getVec3(context,"pos2"),DoubleArgumentType.getDouble(context,"scale")))
                                        )
                                )
                        ).then(literal("entities")
                                .then(argument("ent1", EntityArgument.entity())
                                        .then(argument("ent2", EntityArgument.entity())
                                                .executes(context -> calcDist(context, EntityArgument.getEntity(context,"ent1"), EntityArgument.getEntity(context,"ent2")))
                                                .then(argument("scale",DoubleArgumentType.doubleArg())
                                                        .executes(context -> calcDist(context, EntityArgument.getEntity(context,"ent1"), EntityArgument.getEntity(context,"ent2"),DoubleArgumentType.getDouble(context,"scale")))
                                                )
                                        )
                                )
                        )

                )



                .then(literal("heartbeat")
                        .executes(context -> {
                            context.getSource().sendSuccess(() -> Component.literal("This subcommand exists for datapacks to detect this mod being installed. This subcommand does nothing else other than return 1."), false);
                            return 1;
                        })
                )
                .then(literal("loader")
                        .executes(context -> {
                            context.getSource().sendSuccess(() -> Component.literal("This subcommand exists for datapacks to detect which loader version of the mod is installed. This subcommand does nothing else other than return 0 on Fabric, 1 on NeoForge, and 2 on Forge "), false);
                            return switch (PLATFORM.getPlatformName()) {
                                case "Fabric" -> 0;
                                case "NeoForge" -> 1;
                                case "Forge" -> 2;
                                default -> 0;
                            };
                        })
                )
                .then(literal("velocity")
                        .requires(source -> Commands.hasPermission(LEVEL_GAMEMASTERS).test(source))
                        .then(argument("entities", EntityArgument.entities())
                                .then(argument("Vector", Vec3Argument.vec3(false))
                                        .executes(context -> {
                                            try {
                                                Vec3 velocity = Vec3Argument.getVec3(context, "Vector");
                                                Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");
                                                for (Entity entityIndex : entities) {
                                                    entityIndex.setDeltaMovement(velocity);

                                                    if (entityIndex instanceof ServerPlayer) {
                                                        ((ServerPlayer) entityIndex).connection.send(new ClientboundSetEntityMotionPacket(entityIndex));
                                                    }
                                                    // Don't feel like setting up an instance of LOGGER.
                                                    System.out.println("The new velocity is " + entityIndex.getDeltaMovement().toString());
                                                }
                                            } catch (Throwable e) {
                                                context.getSource().sendSuccess(() -> Component.literal("An error occurred: " + e), false);
                                                return -1;
                                            }
                                            return 1;
                                        })
                                        .then(literal("add")
                                                .executes(context -> {
                                                    try {
                                                        Vec3 velocity = Vec3Argument.getVec3(context, "Vector");
                                                        Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");
                                                        for (Entity entityIndex : entities) {

                                                            entityIndex.push(velocity);

                                                            entityIndex.needsSync = true;
                                                            if (entityIndex instanceof ServerPlayer) {
                                                                ((ServerPlayer) entityIndex).connection.send(new ClientboundSetEntityMotionPacket(entityIndex));
                                                            }

                                                            // Don't feel like setting up an instance of LOGGER.
                                                            // System.out.println("The new velocity is " + entityIndex.getVelocity().toString());
                                                        }
                                                    } catch (Throwable e) {
                                                        context.getSource().sendSuccess(() -> Component.literal("An error occurred: " + e), false);
                                                        return -1;
                                                    }
                                                    return 1;
                                                })
                                        )
                                        .then(literal("set")
                                                .executes(context -> {
                                                    try {
                                                        Vec3 velocity = Vec3Argument.getVec3(context, "Vector");
                                                        Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");
                                                        for (Entity entityIndex : entities) {
                                                            entityIndex.setDeltaMovement(velocity);

                                                            if (entityIndex instanceof ServerPlayer) {
                                                                ((ServerPlayer) entityIndex).connection.send(new ClientboundSetEntityMotionPacket(entityIndex));
                                                            }
                                                            // Don't feel like setting up an instance of LOGGER.
                                                            // AndrewsPackUtilities.LOGGER.info("The new velocity is " + entityIndex.getVelocity().toString());
                                                        }
                                                    } catch (Throwable e) {
                                                        context.getSource().sendSuccess(() -> Component.literal("An error occurred: " + e), false);
                                                        return -1;
                                                    }
                                                    return 1;
                                                })
                                        )
                                        .then(literal("multiply")
                                                .executes(context -> {
                                                    try {
                                                        Vec3 velocity = Vec3Argument.getVec3(context, "Vector");
                                                        Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");
                                                        for (Entity entityIndex : entities) {
                                                            Vec3 originalVel = entityIndex.getDeltaMovement();
                                                            entityIndex.setDeltaMovement(originalVel.multiply(velocity));

                                                            entityIndex.needsSync = true;
                                                            if (entityIndex instanceof ServerPlayer) {
                                                                ((ServerPlayer) entityIndex).connection.send(new ClientboundSetEntityMotionPacket(entityIndex));
                                                            }

                                                            // Don't feel like setting up an instance of LOGGER.
                                                            // System.out.println("The new velocity is " + entityIndex.getVelocity().toString());
                                                        }
                                                    } catch (Throwable e) {
                                                        context.getSource().sendSuccess(() -> Component.literal("An error occurred: " + e), false);
                                                        return -1;
                                                    }
                                                    return 1;
                                                })
                                        )
                                        .then(literal("divide")
                                                .executes(context -> {
                                                    try {
                                                        Vec3 velocity = Vec3Argument.getVec3(context, "Vector");
                                                        Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");
                                                        for (Entity entityIndex : entities) {
                                                            Vec3 originalVel = entityIndex.getDeltaMovement();
                                                            entityIndex.setDeltaMovement(divideVector(originalVel,velocity));

                                                            entityIndex.needsSync = true;
                                                            if (entityIndex instanceof ServerPlayer) {
                                                                ((ServerPlayer) entityIndex).connection.send(new ClientboundSetEntityMotionPacket(entityIndex));
                                                            }

                                                            // Don't feel like setting up an instance of LOGGER.
                                                            // System.out.println("The new velocity is " + entityIndex.getVelocity().toString());
                                                        }
                                                    } catch (Throwable e) {
                                                        context.getSource().sendSuccess(() -> Component.literal("An error occurred: " + e), false);
                                                        return -1;
                                                    }
                                                    return 1;
                                                })
                                        )
                                        .then(literal("lerp")
                                                .executes(context -> {
                                                    try {
                                                        double factor = DoubleArgumentType.getDouble(context, "factor");
                                                        Vec3 velocity = Vec3Argument.getVec3(context, "Vector");
                                                        Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");
                                                        for (Entity entityIndex : entities) {
                                                            Vec3 originalVel = entityIndex.getDeltaMovement();
                                                            entityIndex.setDeltaMovement(originalVel.lerp(velocity,factor));

                                                            entityIndex.needsSync = true;
                                                            if (entityIndex instanceof ServerPlayer) {
                                                                ((ServerPlayer) entityIndex).connection.send(new ClientboundSetEntityMotionPacket(entityIndex));
                                                            }

                                                            // Don't feel like setting up an instance of LOGGER.
                                                            // System.out.println("The new velocity is " + entityIndex.getVelocity().toString());
                                                        }
                                                    } catch (Throwable e) {
                                                        context.getSource().sendSuccess(() -> Component.literal("An error occurred: " + e), false);
                                                        return -1;
                                                    }
                                                    return 1;

                                                })
                                                .then(argument("factor",DoubleArgumentType.doubleArg())
                                                .executes(context -> {
                                                    try {
                                                        double factor = DoubleArgumentType.getDouble(context, "factor");
                                                        Vec3 velocity = Vec3Argument.getVec3(context, "Vector");
                                                        Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");
                                                        for (Entity entityIndex : entities) {
                                                            Vec3 originalVel = entityIndex.getDeltaMovement();
                                                            entityIndex.setDeltaMovement(originalVel.lerp(velocity,factor));

                                                            entityIndex.needsSync = true;
                                                            if (entityIndex instanceof ServerPlayer) {
                                                                ((ServerPlayer) entityIndex).connection.send(new ClientboundSetEntityMotionPacket(entityIndex));
                                                            }

                                                            // Don't feel like setting up an instance of LOGGER.
                                                            // System.out.println("The new velocity is " + entityIndex.getVelocity().toString());
                                                        }
                                                    } catch (Throwable e) {
                                                        context.getSource().sendSuccess(() -> Component.literal("An error occurred: " + e), false);
                                                        return -1;
                                                    }
                                                    return 1;
                                                }))
                                        )

                                )
                        )
                )
                .then(literal("attack_cooldown")
                        .then(argument("player", EntityArgument.player())
                                .executes(context -> {
                                    ServerPlayer player = EntityArgument.getPlayer(context,"player");
                                    float cooldown = player.getAttackStrengthScale(0f);
                                    context.getSource().sendSuccess(() -> Component.literal("The attack cooldown progress of %s is %s".formatted(context.getSource().getTextName(),cooldown)),true);
                                    return (int) (cooldown * 100);
                                })
                        )
                )
                .then(literal("despawn")
                        .requires(source -> Commands.hasPermission(LEVEL_GAMEMASTERS).test(source))
                        .then(argument("entities", EntityArgument.entities())
                                .executes(context -> {
                                    Collection<? extends Entity> entities = EntityArgument.getEntities(context,"entities");
                                    for (Entity entity : entities) {
                                        if (entity.isAlwaysTicking()) {
                                            context.getSource().sendSuccess(() -> Component.literal("§cPlayers aren't allowed, but the provided target selector references one or more player(s)."),false);
                                            AndrewsPackUtilities.LOGGER.info("Command failed because a player was included in the target selector. To bypass this (Don't unless you want crashes and/or major issues to happen!), add 'force' to the end of the command you used. Requires permission level 4 to use 'force'.");
                                            return -1;
                                        }
                                    }
                                    for (Entity entity : entities) {
                                        AndrewsPackUtilities.LOGGER.info("Despawning %s (UUID %s)".formatted(entity.getType().toString(),entity.getStringUUID()));
                                        entity.remove(Entity.RemovalReason.DISCARDED);
                                    }
                                    context.getSource().sendSuccess(() -> Component.literal(("Successfully despawned %s " + (entities.toArray().length == 1 ? "entity" : "entities") + ".").formatted(entities.toArray().length)),true);
                                    return 1;
                                })
                                .then(literal("force")
                                        .requires(serverCommandSource -> Commands.hasPermission(LEVEL_OWNERS).test(serverCommandSource))
                                        .executes(context -> {
                                            Collection<? extends Entity> entities = EntityArgument.getEntities(context,"entities");
                                            context.getSource().sendSuccess(() -> Component.literal("Forcefully attempting to despawn. This WILL cause issues if a player is in the provided target selector."),true);
                                            for (Entity entity : entities) {
                                                AndrewsPackUtilities.LOGGER.info("Despawning %s (UUID %s)".formatted(entity.getType().toString(),entity.getStringUUID()));
                                                entity.remove(Entity.RemovalReason.DISCARDED);
                                            }
                                            context.getSource().sendSuccess(() -> Component.literal(("Successfully despawned %s " + (entities.toArray().length == 1 ? "entity" : "entities") + ".").formatted(entities.toArray().length)),true);
                                            return 1;
                                        })
                                )
                        )
                )
                .then(literal("check_damage")
                        .requires(source -> Commands.hasPermission(LEVEL_GAMEMASTERS).test(source))
                        .then(argument("target", EntityArgument.entity())
                                .executes(context -> checkDamage(context.getSource(), EntityArgument.getEntity(context, "target"), null))
                                .then(argument("damage_predicate", StringArgumentType.string())
                                        .executes(context -> checkDamage(context.getSource(), EntityArgument.getEntity(context, "target"), StringArgumentType.getString(context, "damage_predicate")))
                                )
                        )
                )
                .then(literal("pathfind")
                        .requires(source -> Commands.hasPermission(LEVEL_GAMEMASTERS).test(source))

                        .then(argument("mob",EntityArgument.entity())
                                .then(argument("pos",Vec3Argument.vec3(true))
                                        .executes(context -> PathfindTo(EntityArgument.getEntity(context,"mob"),Vec3Argument.getVec3(context,"pos")) )
                                        .then(argument("speed",DoubleArgumentType.doubleArg())
                                                .executes(context -> PathfindTo(EntityArgument.getEntity(context,"mob"),Vec3Argument.getVec3(context,"pos"),DoubleArgumentType.getDouble(context,"speed")))
                                        )

                                )
                                .then(literal("entity")
                                        .then(argument("to_entity",EntityArgument.entity())
                                                .executes(context -> PathfindTo(EntityArgument.getEntity(context,"mob"),EntityArgument.getEntity(context,"to_entity")))
                                                .then(argument("speed",DoubleArgumentType.doubleArg())
                                                        .executes(context -> PathfindTo(EntityArgument.getEntity(context,"mob"),EntityArgument.getEntity(context,"to_entity"),DoubleArgumentType.getDouble(context,"speed")))
                                                )

                                        )

                                )
                        )
                )
                .then(literal("target")
                        .requires(source -> Commands.hasPermission(LEVEL_GAMEMASTERS).test(source))
                        .then(argument("mob",EntityArgument.entity())
                                .then(literal("null")
                                        .executes(context -> SetTarget(EntityArgument.getEntity(context,"mob"),null))
                                )
                                .then(argument("target",EntityArgument.entity())
                                        .executes(context -> SetTarget(EntityArgument.getEntity(context,"mob"),EntityArgument.getEntity(context,"target")))
                                )
                        )
                )
                .then(literal("try_attack")
                        .requires(source -> Commands.hasPermission(LEVEL_GAMEMASTERS).test(source))
                        .then(argument("mob",EntityArgument.entity())
                                .then(argument("target",EntityArgument.entity())
                                        .executes(context -> {
                                            Entity source = EntityArgument.getEntity(context, "mob");
                                            Entity target = EntityArgument.getEntity(context, "target");

                                            int returnVal;
                                            if ((source instanceof LivingEntity thisMob && !(source instanceof Player)) && target instanceof LivingEntity livingTarget) {
                                                returnVal = thisMob.doHurtTarget(context.getSource().getLevel(), livingTarget) ? 1 : 0;

                                                // System.out.println(returnVal);
                                            } else if (source instanceof Player player) {
                                                returnVal = 2;
                                                player.attack(target);

                                            } else {
                                                if (!(source instanceof LivingEntity) && !(target instanceof LivingEntity || source instanceof Player)) {
                                                    context.getSource().sendFailure(Component.translatable("Source %s is not a living entity, cannot attack as the entity! Also, target %s is not a living entity, cannot attack the entity!",source.getDisplayName().getString(),target.getDisplayName().getString()));
                                                    return -3;
                                                } else if (!(source instanceof LivingEntity)) {
                                                    context.getSource().sendFailure(Component.translatable("Source %s is not a living entity, cannot attack as the entity!",source.getDisplayName().getString()));
                                                    return -1;
                                                } else if (!(target instanceof LivingEntity || source instanceof Player)) {
                                                    context.getSource().sendFailure(Component.translatable("Target %s is not a living entity, cannot attack the entity!",target.getDisplayName().getString()));
                                                    return -2;
                                                } else {
                                                    context.getSource().sendFailure(Component.literal("An unknown error occured whilst attempting the attack. This message should never show without direct interference from another mod toward this one, such as a mixin being applied to this command's code by another mod."));
                                                    return -127;
                                                }
                                            }
                                            switch (returnVal) {
                                                case 2 -> context.getSource().sendSuccess(() -> Component.translatable("Successfully made player %s attack %s. It is unknown if the attack succeeded, as the attacker was a player.",source.getDisplayName().getString(),target.getDisplayName().getString()),true);
                                                case 1 -> context.getSource().sendSuccess(() -> Component.translatable("Successfully made %s attack %s",source.getDisplayName().getString(),target.getDisplayName().getString()),true);
                                                case 0 -> context.getSource().sendFailure(Component.translatable("%1$s tried to attack %2$s, but %2$s didn't take damage from it, likely due to invulnerability.",source.getDisplayName().getString(),target.getDisplayName().getString()));
                                            }
                                            return returnVal;
                                        })
                                )
                        )
                )
                .then(literal("check_entity")
                        .requires(source -> Commands.hasPermission(LEVEL_MODERATORS).test(source))
                        .then(argument("entity",EntityArgument.entity())
                                .then(argument("check",StringArgumentType.string())
                                        .executes(context -> {
                                            Entity entity = EntityArgument.getEntity(context,"entity");
                                            String check = StringArgumentType.getString(context, "check");

                                            boolean success;
                                            switch (check) {
                                                case "living" -> success = entity instanceof LivingEntity;
                                                case "mob" -> success = entity instanceof Mob;
                                                case "player" -> success = entity instanceof Player;
                                                case "pickup" -> success = entity instanceof ItemEntity || entity instanceof ExperienceOrb;
                                                case "projectile" -> success = entity instanceof Projectile;
                                                case "monster" -> success = entity instanceof Monster;
                                                case "hostile","monster_no_neutral" -> success = entity instanceof Monster && !(entity instanceof NeutralMob);
                                                case "neutral" -> success = entity instanceof NeutralMob;
                                                case "animal" -> success = entity instanceof Animal;
                                                case "tamable" -> success = entity instanceof TamableAnimal;
                                                case "passive", "animal_no_neutral" -> success = entity instanceof Animal && !(entity instanceof NeutralMob);
                                                case "has_navigation", "path_aware" -> success = entity instanceof PathfinderMob;
                                                case "aquatic", "water_animal" -> success = entity instanceof WaterAnimal;
                                                case "fire_immune" -> success = entity.fireImmune();
                                                case "boss" -> success = entity instanceof WitherBoss || entity instanceof EnderDragonPart || entity instanceof EnderDragon;
                                                case "explosive" -> success = entity instanceof Creeper || entity instanceof PrimedTnt || entity instanceof EndCrystal || (entity instanceof AbstractHurtingProjectile && !(entity instanceof SmallFireball)) || entity instanceof FireworkRocketEntity;
                                                case "fireball" -> success = entity instanceof DragonFireball || entity instanceof Fireball;
                                                case "arrow" -> success = entity instanceof AbstractArrow;
                                                case "is_at_full_health","full_hp" -> success = (entity instanceof LivingEntity livingEntity && livingEntity.getHealth() >= livingEntity.getMaxHealth());
                                                case "is_baby" -> success = (entity instanceof AgeableMob ageableMob && ageableMob.isBaby());
                                                case "is_adult" -> success = (entity instanceof AgeableMob ageableMob && !ageableMob.isBaby()) || !(entity instanceof AgeableMob);
                                                case null, default -> {
                                                    context.getSource().sendFailure(Component.translatable("Unknown entity check: '%s'. Please use a valid check.",check));
                                                    return -1;
                                                }

                                            }

                                            if (success) {
                                                context.getSource().sendSuccess(() -> Component.translatable("Entity %s passed check \"%s\"",entity.getDisplayName().getString(),check),false);
                                            } else {
                                                context.getSource().sendFailure(Component.translatable("Entity %s failed check \"%s\"",entity.getDisplayName().getString(),check));
                                            }
                                            return success ? 1 : 0;
                                        })
                                )
                        )
                )
                .then(literal("manipulate")
                        .requires(source -> Commands.hasPermission(LEVEL_GAMEMASTERS).test(source))
                        .then(argument("entity",EntityArgument.entity())
                                .then(literal("generic")
                                        .then(literal("extinguish").executes(context -> {
                                            Entity entity = EntityArgument.getEntity(context,"entity");
                                            entity.extinguishFire();
                                            context.getSource().sendSuccess(() -> Component.literal("Extinguished the entity."),true);
                                            return 1;
                                        }))
                                        .then(literal("eject_passengers").executes(context -> {
                                            Entity entity = EntityArgument.getEntity(context,"entity");
                                            entity.ejectPassengers();
                                            context.getSource().sendSuccess(() -> Component.literal("Ejected all passengers."),true);
                                            return 1;
                                        }))
                                )
                                .then(literal("living_entity")
                                        .then(literal("health").then(argument("value", FloatArgumentType.floatArg())
                                                .executes(context -> {
                                                    Entity entity = EntityArgument.getEntity(context, "entity");
                                                    if (entity instanceof LivingEntity livingEntity) {
                                                        float original = livingEntity.getHealth();
                                                        livingEntity.setHealth(FloatArgumentType.getFloat(context,"value"));
                                                        context.getSource().sendSuccess(() -> Component.literal("Set the health of %s to %f (from %f)".formatted(livingEntity.getScoreboardName(),livingEntity.getHealth(),original)),true);
                                                        return (int) livingEntity.getHealth();
                                                    } else {
                                                        context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                        return 0;
                                                    }
                                                })
                                                .then(literal("add")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");

                                                            if (entity instanceof LivingEntity livingEntity) {
                                                                float original = livingEntity.getHealth();
                                                                livingEntity.setHealth(livingEntity.getHealth() + FloatArgumentType.getFloat(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the health of %s to %f (from %f)".formatted(livingEntity.getScoreboardName(),livingEntity.getHealth(),original)),true);
                                                                return (int) livingEntity.getHealth();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                )
                                                .then(literal("remove")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof LivingEntity livingEntity) {
                                                                float original = livingEntity.getHealth();
                                                                livingEntity.setHealth(livingEntity.getHealth() - FloatArgumentType.getFloat(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the health of %s to %f (from %f)".formatted(livingEntity.getScoreboardName(),livingEntity.getHealth(),original)),true);
                                                                return (int) livingEntity.getHealth();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                )
                                                .then(literal("multiply")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof LivingEntity livingEntity) {
                                                                float original = livingEntity.getHealth();
                                                                livingEntity.setHealth(livingEntity.getHealth() * FloatArgumentType.getFloat(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the health of %s to %f (from %f)".formatted(livingEntity.getScoreboardName(),livingEntity.getHealth(),original)),true);
                                                                return (int) livingEntity.getHealth();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                )
                                                .then(literal("divide")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof LivingEntity livingEntity) {
                                                                float original = livingEntity.getHealth();
                                                                livingEntity.setHealth(livingEntity.getHealth() / FloatArgumentType.getFloat(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the health of %s to %f (from %f)".formatted(livingEntity.getScoreboardName(),livingEntity.getHealth(),original)),true);
                                                                return (int) livingEntity.getHealth();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                ))

                                        )
                                                .then(literal("heal").then(argument("amount",FloatArgumentType.floatArg()).executes(context -> {
                                                    Entity entity = EntityArgument.getEntity(context, "entity");

                                                    if (entity instanceof LivingEntity livingEntity) {
                                                        float original = livingEntity.getHealth();
                                                        livingEntity.heal(FloatArgumentType.getFloat(context,"amount"));
                                                        context.getSource().sendSuccess(() -> Component.literal("Set the health of %s to %f (from %f)".formatted(livingEntity.getScoreboardName(),livingEntity.getHealth(),original)),true);
                                                        return (int) livingEntity.getHealth();
                                                    } else {
                                                        context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                        return 0;
                                                    }

                                                })))
                                        )

                                .then(literal("player")
                                        .then(literal("food_level").then(argument("value", IntegerArgumentType.integer())
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof ServerPlayer serverPlayer) {
                                                                FoodData foodData = serverPlayer.getFoodData();
                                                                float original = foodData.getFoodLevel();
                                                                foodData.setFoodLevel(IntegerArgumentType.getInteger(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the foodLevel of %s to %d (from %f)".formatted(serverPlayer.getScoreboardName(),foodData.getFoodLevel(),original)),true);
                                                                return foodData.getFoodLevel();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                .then(literal("add")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof ServerPlayer serverPlayer) {
                                                                FoodData foodData = serverPlayer.getFoodData();
                                                                float original = foodData.getFoodLevel();
                                                                foodData.setFoodLevel(foodData.getFoodLevel() + IntegerArgumentType.getInteger(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the foodLevel of %s to %d (from %f)".formatted(serverPlayer.getScoreboardName(),foodData.getFoodLevel(),original)),true);
                                                                return foodData.getFoodLevel();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                )
                                                .then(literal("remove")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof ServerPlayer serverPlayer) {
                                                                FoodData foodData = serverPlayer.getFoodData();
                                                                float original = foodData.getFoodLevel();
                                                                foodData.setFoodLevel(foodData.getFoodLevel() - IntegerArgumentType.getInteger(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the foodLevel of %s to %d (from %f)".formatted(serverPlayer.getScoreboardName(),foodData.getFoodLevel(),original)),true);
                                                                return foodData.getFoodLevel();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                )
                                                .then(literal("multiply")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof ServerPlayer serverPlayer) {
                                                                FoodData foodData = serverPlayer.getFoodData();
                                                                float original = foodData.getFoodLevel();
                                                                foodData.setFoodLevel(foodData.getFoodLevel() * IntegerArgumentType.getInteger(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the foodLevel of %s to %d (from %f)".formatted(serverPlayer.getScoreboardName(),foodData.getFoodLevel(),original)),true);
                                                                return foodData.getFoodLevel();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                )
                                                .then(literal("divide")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof ServerPlayer serverPlayer) {
                                                                FoodData foodData = serverPlayer.getFoodData();
                                                                float original = foodData.getFoodLevel();
                                                                foodData.setFoodLevel(foodData.getFoodLevel() / IntegerArgumentType.getInteger(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the foodLevel of %s to %d (from %f)".formatted(serverPlayer.getScoreboardName(),foodData.getFoodLevel(),original)),true);
                                                                return foodData.getFoodLevel();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                )
                                                )
                                        )
                                        .then(literal("food_saturation_level").then(argument("value", FloatArgumentType.floatArg())
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof ServerPlayer serverPlayer) {

                                                                FoodData foodData = serverPlayer.getFoodData();

                                                                float original = foodData.getSaturationLevel();
                                                                foodData.setSaturation(FloatArgumentType.getFloat(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the foodLevel of %s to %f (from %f)".formatted(serverPlayer.getScoreboardName(),foodData.getSaturationLevel(),original)),true);
                                                                return (int) foodData.getSaturationLevel();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                .then(literal("add")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof ServerPlayer serverPlayer) {
                                                                FoodData foodData = serverPlayer.getFoodData();
                                                                float original = foodData.getSaturationLevel();
                                                                foodData.setSaturation(foodData.getSaturationLevel() + FloatArgumentType.getFloat(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the foodLevel of %s to %f (from %f)".formatted(serverPlayer.getScoreboardName(),foodData.getSaturationLevel(),original)),true);
                                                                return (int) foodData.getSaturationLevel();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a living entity.")));
                                                                return 0;
                                                            }
                                                        })
                                                )
                                                .then(literal("remove")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof ServerPlayer serverPlayer) {
                                                                FoodData foodData = serverPlayer.getFoodData();
                                                                float original = foodData.getSaturationLevel();
                                                                foodData.setSaturation(foodData.getSaturationLevel() - FloatArgumentType.getFloat(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the foodLevel of %s to %f (from %f)".formatted(serverPlayer.getScoreboardName(),foodData.getSaturationLevel(),original)),true);
                                                                return (int) foodData.getSaturationLevel();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a player.")));
                                                                return 0;
                                                            }
                                                        })
                                                )
                                                .then(literal("multiply")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof ServerPlayer serverPlayer) {
                                                                FoodData foodData = serverPlayer.getFoodData();
                                                                float original = foodData.getSaturationLevel();
                                                                foodData.setSaturation(foodData.getSaturationLevel() * FloatArgumentType.getFloat(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the foodLevel of %s to %f (from %f)".formatted(serverPlayer.getScoreboardName(),foodData.getSaturationLevel(),original)),true);
                                                                return (int) foodData.getSaturationLevel();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a player.")));
                                                                return 0;
                                                            }
                                                        })
                                                )
                                                .then(literal("divide")
                                                        .executes(context -> {
                                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                                            if (entity instanceof ServerPlayer serverPlayer) {
                                                                FoodData foodData = serverPlayer.getFoodData();
                                                                float original = foodData.getSaturationLevel();
                                                                foodData.setSaturation(foodData.getSaturationLevel() / FloatArgumentType.getFloat(context,"value"));
                                                                context.getSource().sendSuccess(() -> Component.literal("Set the foodLevel of %s to %f (from %f)".formatted(serverPlayer.getScoreboardName(),foodData.getSaturationLevel(),original)),true);
                                                                return (int) foodData.getSaturationLevel();
                                                            } else {
                                                                context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a player.")));
                                                                return 0;
                                                            }
                                                        })
                                                )

                                        )
                                        .then(literal("add_exhaustion").then(argument("value", FloatArgumentType.floatArg())
                                                .executes(context -> {
                                                    Entity entity = EntityArgument.getEntity(context, "entity");
                                                    if (entity instanceof ServerPlayer serverPlayer) {

                                                        FoodData foodData = serverPlayer.getFoodData();

                                                        float original = ((PlayerExhaustionAccessor)foodData).getExhaustionLevel();
                                                        foodData.addExhaustion(FloatArgumentType.getFloat(context,"value"));
                                                        context.getSource().sendSuccess(() -> Component.literal("Added %f exhaustion to %s (previously %f)".formatted(foodData.getSaturationLevel(),serverPlayer.getScoreboardName(),original)),true);
                                                        return (int) ((PlayerExhaustionAccessor)foodData).getExhaustionLevel();
                                                    } else {
                                                        context.getSource().sendFailure(EntitySelector.joinNames(List.of(entity)).copy().append(Component.literal(" is not a player.")));
                                                        return 0;
                                                    }
                                                }))
                                        )
                                        )
                                )
                                .then(literal("ender_dragon")
                                        .then(literal("fly_speed").then(argument("value", DoubleArgumentType.doubleArg())
                                                .executes(context -> {
                                                        Entity entity = EntityArgument.getEntity(context, "entity");
                                                        if (entity instanceof EnderDragon enderDragon && entity instanceof EnderDragonSpeedAccessor accessor) {
                                                            accessor.setFlySpeed(DoubleArgumentType.getDouble(context,"value"));
                                                            context.getSource().sendSuccess(() -> Component.literal("Set the fly speed of %s to %f".formatted(enderDragon.getScoreboardName(),accessor.getFlySpeed())),true);
                                                            return 1;
                                                        } else {
                                                            context.getSource().sendFailure(Component.literal("The provided entity is not an ender dragon or a mixin failed its loading."));
                                                            return 0;
                                                        }
                                        })
                                ))
                                .then(literal("vertical_speed").then(argument("value", DoubleArgumentType.doubleArg())
                                        .executes(context -> {
                                            Entity entity = EntityArgument.getEntity(context, "entity");
                                            if (entity instanceof EnderDragon enderDragon && entity instanceof EnderDragonSpeedAccessor accessor) {
                                                accessor.setVerticalFlySpeed(DoubleArgumentType.getDouble(context,"value"));
                                                context.getSource().sendSuccess(() -> Component.literal("Set the vertical fly speed of %s to %f".formatted(enderDragon.getScoreboardName(),accessor.getVerticalFlySpeed())),true);
                                                return 1;
                                            } else {
                                                context.getSource().sendFailure(Component.literal("The provided entity is not an ender dragon or a mixin failed its loading."));
                                                return 0;
                                            }

                                        })

                                ))
                        ))
                );
        return dispatcher.register(main);
    }
    public static Vec3 divideVector(Vec3 v1, Vec3 v2) {
        return new Vec3(v1.x / v2.x, v1.y / v2.y, v1.z / v2.z);
    }
    public static void resetDamageFlags(MinecraftServer server) {
        for (net.minecraft.server.level.ServerLevel world : server.getAllLevels()) {
            for (Entity entity : world.getAllEntities()) {
                if (entity instanceof LivingEntity) {
                    if (entity instanceof IEntityDamageAccessor) {
                        ((IEntityDamageAccessor) entity).resetDamageFlags();
                    }
                }
            }
        }
    }
    private static int SetTarget(Entity entity, Entity target) {
        if (entity instanceof Mob mob && target == null) {
            mob.setTarget(null);
            if (entity instanceof Warden warden && warden.getEntityAngryAt().isPresent()) {
                warden.clearAnger(warden.getEntityAngryAt().get());
                
            }

        } else if (entity instanceof Mob mob && target instanceof LivingEntity) {
            mob.setTarget((LivingEntity) target);
            if (entity instanceof Warden warden) {
                warden.increaseAngerAt(target,150,true);
            }

        }
        return 1;
    }

    private static int PathfindTo(Entity entity, Entity targetEntity) {
        if (entity instanceof Mob) {
            ((Mob) entity).getNavigation().moveTo(targetEntity,1);
            return 1;
        }
        return 0;
    }


    private static int PathfindTo(Entity entity, Vec3 position) {
        if (entity instanceof Mob) {
            BlockPos pos = new BlockPos((int) position.x, (int) position.y, (int) position.z);
            ((Mob) entity).getNavigation().moveTo(pos.getX(),pos.getY(),pos.getZ(),1);
            return 1;
        }
        return 0;
    }

    private static int PathfindTo(Entity entity, Entity targetEntity, double speed) {
        if (entity instanceof Mob) {
            ((Mob) entity).getNavigation().moveTo(targetEntity,speed);
            return 1;
        }
        return 0;
    }


    private static int PathfindTo(Entity entity, Vec3 position, double speed) {
        if (entity instanceof Mob) {
            BlockPos pos = new BlockPos((int) position.x, (int) position.y, (int) position.z);
            ((Mob) entity).getNavigation().moveTo(pos.getX(),pos.getY(),pos.getZ(),speed);
            return 1;
        }
        return 0;
    }


    private static int checkDamage(CommandSourceStack source, Entity target, String damagePredicate) throws CommandSyntaxException {
        try {
            if (!(target instanceof IEntityDamageAccessor)) {
                source.sendFailure(Component.literal("Damage detection is not available because the required mixin didn't load, likely because it doesn't work on your Minecraft version."));
                return 0;
            }
            if (((IEntityDamageAccessor) target).hasTakenDamageThisTick()) {
                if (damagePredicate == null) {
                    source.sendSuccess(() -> Component.literal("The provided entity took damage!"), false);
                    return 1;
                } else {

                    DamageSource lastSource = ((IEntityDamageAccessor) target).getLastDamageSourceThisTick();
                    if (lastSource != null) {
                        Optional<ResourceKey<DamageType>> damageTypeId = lastSource.typeHolder().unwrapKey();
                        String damageTypeIdStringified = damageTypeId.orElse(DamageTypes.GENERIC).identifier().toString();
                        if (damagePredicate.equals(damageTypeIdStringified)) {
                            source.sendSuccess(() -> Component.literal("The provided entity took damage of type: " + damageTypeIdStringified), true);
                            return 1;
                        } else {
                            source.sendFailure(Component.literal("The entity took damage, but it did not match the specified damage type."));
                            source.sendSuccess(() -> Component.literal("The type of damage that WAS taken is: " + damageTypeIdStringified), true);
                            return 0;
                        }
                    } else {
                        source.sendFailure(Component.literal("The entity took damage, but the damage source could not be determined."));
                        return 0;
                    }
                }
            } else {
                source.sendFailure(Component.literal("The entity didn't take damage!"));
                return 0;
            }

        } catch (Exception e) {
            source.sendFailure(Component.literal("Received Exception: " + e.toString()));
        } catch (Throwable e) {
            source.sendFailure(Component.literal("Received Throwable: " + e.toString()));
        }
        return 0;
    }
    private static int calcDist(CommandContext<CommandSourceStack> context, Entity ent1, Entity ent2) {
        Vec3 pos1 = ent1.position();
        Vec3 pos2 = ent2.position();
        return calcDist(context, pos1, pos2);
    }
    private static int calcDist(CommandContext<CommandSourceStack> context, Entity ent1, Entity ent2, double scale_factor) {
        Vec3 pos1 = ent1.position();
        Vec3 pos2 = ent2.position();
        return calcDist(context, pos1, pos2, scale_factor);
    }

    private static int calcDist(CommandContext<CommandSourceStack> context, Vec3 pos1, Vec3 pos2, double scale_factor) {
        double dist = pos1.distanceTo(pos2);
        context.getSource().sendSuccess(() -> Component.literal("The distance between " + pos1.toString() + " and " + pos2.toString() + " after scaling factor " + scale_factor + " is " + dist),true);
        return (int) (dist * scale_factor);
    }
    private static int calcDist(CommandContext<CommandSourceStack> context, Vec3 pos1, Vec3 pos2) {
        double dist = pos1.distanceTo(pos2);
        context.getSource().sendSuccess(() -> Component.literal("The distance between " + pos1.toString() + " and " + pos2.toString() + " is " + dist),true);
        return (int) dist;
    }

}
