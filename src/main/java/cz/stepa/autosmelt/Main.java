package cz.stepa.autosmelt;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.*;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.plugin.PluginBase;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends PluginBase implements Listener {
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getLogger().info("AutoSmelt enabled!");
    }
    @EventHandler
    public void onMine(BlockBreakEvent e) {
        Block block = e.getBlock();
        Player p = e.getPlayer();
        if(p.isCreative()) return;
        //Used fortune count from native NukkitX
        Enchantment fortune = e.getItem().getEnchantment(18);
        int count = 1;
        if (fortune != null && fortune.getLevel() >= 1) {
            int i = ThreadLocalRandom.current().nextInt(fortune.getLevel() + 2) - 1;
            if (i < 0) {
                i = 0;
            }
            count = i + 1;
        }
        //Iron Ore
        if(block.getName().equals("Iron Ore")) {
            e.setDropExp(count);
            e.setDrops(new Item[]{new ItemIngotIron(0, count)});
        //Gold Ore
        } else if(block.getName().equals("Gold Ore")) {
            e.setDropExp(2*count);
            e.setDrops(new Item[]{new ItemIngotGold(0, count)});
        }
    }
}