package net.dirtcraft.plugins.dirtrewards.data;

import org.bukkit.NamespacedKey;

public class MoneyReward {
	private final NamespacedKey blockNameSpacedKey;
	private final int chance;
	private final int minBal;
	private final int maxBal;

	public MoneyReward(NamespacedKey blockNameSpacedKey, int chance, int minBal, int maxBal) {
		this.blockNameSpacedKey = blockNameSpacedKey;
		this.chance = chance;
		this.minBal = minBal;
		this.maxBal = maxBal;
	}

	public NamespacedKey getBlockNameSpacedKey() {
		return blockNameSpacedKey;
	}

	public int getChance() {
		return chance;
	}

	public int getMinBal() {
		return minBal;
	}

	public int getMaxBal() {
		return maxBal;
	}
}
