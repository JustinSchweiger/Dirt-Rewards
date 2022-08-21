package net.dirtcraft.plugins.dirtrewards.listener;

import net.dirtcraft.plugins.dirtrewards.DirtRewards;
import net.dirtcraft.plugins.dirtrewards.data.Block;
import net.dirtcraft.plugins.dirtrewards.data.CommandReward;
import net.dirtcraft.plugins.dirtrewards.data.MoneyReward;
import net.dirtcraft.plugins.dirtrewards.data.RewardsManager;
import net.dirtcraft.plugins.dirtrewards.utils.Strings;
import net.dirtcraft.plugins.dirtrewards.utils.Utilities;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BlockBreakListener implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		NamespacedKey namespacedKey = event.getBlock().getType().getKey();

		if (!RewardsManager.doesBlockExist(new Block(namespacedKey))) {
			return;
		}

		List<CommandReward> commandRewards = RewardsManager.getCommandRewards(new Block(namespacedKey));
		MoneyReward moneyReward = RewardsManager.getMoneyReward(new Block(namespacedKey));

		boolean gotReward = false;
		if (commandRewards.size() > 0) {
			ConsoleCommandSender console = DirtRewards.getPlugin().getServer().getConsoleSender();

			for (CommandReward commandReward : commandRewards) {
				if (Utilities.getChance(commandReward.getChance())) {
					Bukkit.dispatchCommand(console, commandReward.getCommand().replace("{PLAYER}", event.getPlayer().getName()));
					if (commandReward.getMessage().length() > 0) {
						event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utilities.format(commandReward.getMessage())));
					}
					gotReward = true;
				}
			}
		}

		if (moneyReward != null) {
			if (Utilities.getChance(moneyReward.getChance())) {
				ThreadLocalRandom random = ThreadLocalRandom.current();
				int min = moneyReward.getMinBal();
				int max = moneyReward.getMaxBal();

				if (min != max) {
					int amount = random.nextInt(moneyReward.getMinBal(), moneyReward.getMaxBal());
					DirtRewards.getEcon().depositPlayer(event.getPlayer(), amount);
					event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Strings.MONEY_REWARD.replace("{amount}", String.valueOf(amount))));
				} else {
					DirtRewards.getEcon().depositPlayer(event.getPlayer(), min);
					event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Strings.MONEY_REWARD.replace("{amount}", String.valueOf(min))));
				}

				gotReward = true;
			}
		}

		if (gotReward) {
			Utilities.playRewardSound(event.getPlayer());
		}
	}
}
