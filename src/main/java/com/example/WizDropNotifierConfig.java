package com.wizdropnotifier;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import java.awt.Color;

@ConfigGroup("wizdropnotifier")
public interface WizDropNotifierConfig extends Config
{
    @ConfigItem(
        keyName = "bulbIp",
        name = "WiZ Light IP Address",
        description = "The local IPv4 address of your WiZ bulb",
        position = 1
    )
    default String bulbIp()
    {
        return "192.168.1.100";
    }

    @ConfigItem(
        keyName = "baseColor",
        name = "Base Ambient Color",
        description = "The normal ambient color the light stays on during gameplay",
        position = 2
    )
    default Color baseColor()
    {
        return new Color(255, 255, 255); // Default White
    }

    @ConfigItem(
        keyName = "flashDuration",
        name = "Flash Duration (ms)",
        description = "How long (in ms) the flash stays on before restoring the Base Color",
        position = 3
    )
    default int flashDuration()
    {
        return 2000;
    }

    @ConfigSection(
        name = "Ground Items Tiers",
        description = "Match thresholds and colors to your RuneLite Ground Items plugin",
        position = 4
    )
    String dropTiers = "dropTiers";

    @ConfigItem(
        keyName = "lowValuePrice",
        name = "Low value price",
        description = "Minimum GP required for Low Value flash color",
        section = "dropTiers",
        position = 5
    )
    default int lowValuePrice() { return 20000; }

    @ConfigItem(
        keyName = "lowValueColor",
        name = "Low value items color",
        description = "Flash color for Low Value items",
        section = "dropTiers",
        position = 6
    )
    default Color lowValueColor() { return Color.decode("#66B2FF"); }

    @ConfigItem(
        keyName = "mediumValuePrice",
        name = "Medium value price",
        description = "Minimum GP required for Medium Value flash color",
        section = "dropTiers",
        position = 7
    )
    default int mediumValuePrice() { return 100000; }

    @ConfigItem(
        keyName = "mediumValueColor",
        name = "Medium value items color",
        description = "Flash color for Medium Value items",
        section = "dropTiers",
        position = 8
    )
    default Color mediumValueColor() { return Color.decode("#99FF99"); }

    @ConfigItem(
        keyName = "highValuePrice",
        name = "High value price",
        description = "Minimum GP required for High Value flash color",
        section = "dropTiers",
        position = 9
    )
    default int highValuePrice() { return 999999; }

    @ConfigItem(
        keyName = "highValueColor",
        name = "High value items color",
        description = "Flash color for High Value items",
        section = "dropTiers",
        position = 10
    )
    default Color highValueColor() { return Color.decode("#FF9600"); }

    @ConfigItem(
        keyName = "insaneValuePrice",
        name = "Insane value price",
        description = "Minimum GP required for Insane Value flash color",
        section = "dropTiers",
        position = 11
    )
    default int insaneValuePrice() { return 10000000; }

    @ConfigItem(
        keyName = "insaneValueColor",
        name = "Insane value items color",
        description = "Flash color for Insane Value items",
        section = "dropTiers",
        position = 12
    )
    default Color insaneValueColor() { return Color.decode("#FF66B2"); }
}
