package net.dirtcraft.plugins.dirtrewards.data;

import org.bukkit.NamespacedKey;

public class Block {
	private final NamespacedKey namespacedKey;

	public Block(NamespacedKey namespacedKey) {
		this.namespacedKey = namespacedKey;
	}

	public NamespacedKey getNamespacedKey() {
		return namespacedKey;
	}
}
