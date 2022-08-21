package net.dirtcraft.plugins.dirtrewards.data;

import org.bukkit.NamespacedKey;

public class CommandReward {
	private final NamespacedKey blockNameSpacedKey;
	private final String command;
	private final int chance;
	private final String message;

	public CommandReward(NamespacedKey blockNameSpacedKey, String command, int chance, String message) {
		this.blockNameSpacedKey = blockNameSpacedKey;
		this.command = command;
		this.chance = chance;
		this.message = message;
	}


	public NamespacedKey getBlockNameSpacedKey() {
		return blockNameSpacedKey;
	}

	public String getCommand() {
		return command;
	}

	public int getChance() {
		return chance;
	}

	public String getMessage() {
		return message;
	}
}
