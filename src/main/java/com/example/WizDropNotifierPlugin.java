package com.wizdropnotifier;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.events.PlayerLootReceived;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;

import java.awt.Color;
import java.util.Collection;

@Slf4j
@PluginDescriptor(
    name = "WiZ Drop Notifier",
    description = "Flashes a WiZ Smart Light with tier colors when loot drops",
    tags = {"loot", "wiz", "light", "notification", "drop"}
)
public class WizDropNotifierPlugin extends Plugin
{
    @Inject
    private WizDropNotifierConfig config;

    @Inject
    private ItemManager itemManager;

    @Provides
    WizDropNotifierConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(WizDropNotifierConfig.class);
    }

    @Override
    protected void startUp()
    {
        WizService.setLightColor(config.bulbIp(), config.baseColor());
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (event.getGroup().equals("wizdropnotifier") && event.getKey().equals("baseColor"))
        {
            WizService.setLightColor(config.bulbIp(), config.baseColor());
        }
    }

    @Subscribe
    public void onNpcLootReceived(NpcLootReceived npcLootReceived)
    {
        processLoot(npcLootReceived.getItems());
    }

    @Subscribe
    public void onPlayerLootReceived(PlayerLootReceived playerLootReceived)
    {
        processLoot(playerLootReceived.getItems());
    }

    private void processLoot(Collection<ItemStack> items)
    {
        long totalValue = 0;
        for (ItemStack item : items)
        {
            totalValue += (long) itemManager.getItemPrice(item.getId()) * item.getQuantity();
        }

        Color flashColor = getFlashColorForValue(totalValue);
        if (flashColor != null)
        {
            log.info("Loot dropped: {} GP. Flashing color: {}", totalValue, flashColor);
            WizService.flashAndRestore(config.bulbIp(), flashColor, config.baseColor(), config.flashDuration());
        }
    }

    private Color getFlashColorForValue(long value)
    {
        if (value >= config.insaneValuePrice()) return config.insaneValueColor();
        if (value >= config.highValuePrice())   return config.highValueColor();
        if (value >= config.mediumValuePrice()) return config.mediumValueColor();
        if (value >= config.lowValuePrice())    return config.lowValueColor();
        
        return null;
    }
}
