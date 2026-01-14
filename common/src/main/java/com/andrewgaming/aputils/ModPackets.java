package com.andrewgaming.aputils;


import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
// Add other packet imports here

public enum ModPackets {
    UPDATE_ENTITY_VELOCITY("update_entity_velocity", ClientboundSetEntityMotionPacket.class);
    // Add more packets here, e.g.,
    // UPDATE_ENTITY_POSITION("update_entity_position", EntityPositionS2CPacket.class);
    // CUSTOM_PAYLOAD("custom_payload", CustomPayloadS2CPacket.class);

    private final String name;
    private final Class<? extends Packet<?>> packetClass;

    ModPackets(String name, Class<? extends Packet<?>> packetClass) {
        this.name = name;
        this.packetClass = packetClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Packet<?>> getPacketClass() {
        return packetClass;
    }

    public static ModPackets fromName(String name) {
        for (ModPackets packet : ModPackets.values()) {
            if (packet.name().equalsIgnoreCase(name)) {
                return packet;
            }
        }
        return null;
    }
}