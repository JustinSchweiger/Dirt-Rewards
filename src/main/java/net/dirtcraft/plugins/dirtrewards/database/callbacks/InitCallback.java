package net.dirtcraft.plugins.dirtrewards.database.callbacks;

import net.dirtcraft.plugins.dirtrewards.data.Block;
import net.dirtcraft.plugins.dirtrewards.data.CommandReward;
import net.dirtcraft.plugins.dirtrewards.data.MoneyReward;

import java.util.List;

public interface InitCallback {
	void onSuccess(List<Block> blocks, List<CommandReward> commands, List<MoneyReward> money);
}
