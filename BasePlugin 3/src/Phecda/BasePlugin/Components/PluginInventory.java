package Phecda.BasePlugin.Components;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import Phecda.BasePlugin.BasePlugin;

public abstract class PluginInventory<T extends BasePlugin<T>> implements Listener {
	
	protected Inventory inventory;
	
	protected T plugin;
	
	protected boolean cancel = true;
	
	private List<String> playerRegistry;
	
	public PluginInventory(T plugin, InventoryHolder owner, int size, String title) {
		this.inventory = Bukkit.createInventory(owner, size, title);
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
		
		this.playerRegistry = new ArrayList<String>();
	}
	
	public PluginInventory(T plugin, InventoryHolder owner, int size, String title, Boolean cancel) {
		this(plugin, owner, size, title);
		this.cancel = cancel;
	}
	
	public PluginInventory(T plugin, Inventory inv) {
		this.inventory = inv;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;

		this.playerRegistry = new ArrayList<String>();
	}
	
	public PluginInventory(T plugin, Inventory inv, Boolean cancel) {
		this(plugin, inv);
		this.cancel = cancel;
	}
	
	public void show(Player player) {
		
		this.playerRegistry.add(player.getName());
		
		player.openInventory(inventory);
	}
	
	protected abstract void handleInventoryClickEvent(InventoryClickEvent event, Player player);
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		
		String name = event.getPlayer().getName();
		
		if (!(this.playerRegistry.contains(name))) return;
		
		if (event.getInventory().getName().equals(inventory.getName())) {
			// We are no longer needed and shall be discarded!
			
			this.playerRegistry.remove(name);
			if (!this.playerRegistry.isEmpty()) return;
			
			HandlerList.unregisterAll(this);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if (!this.playerRegistry.contains(player.getName())) return;
		
		if (event.getInventory().getName().equals(this.inventory.getName())) {

			if (cancel && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) event.setCancelled(true);
			if (cancel && event.getAction() == InventoryAction.COLLECT_TO_CURSOR) event.setCancelled(true);
			
			if (event.getSlot() >= 0 && event.getRawSlot() < inventory.getSize()) {
				event.setCancelled(cancel);
				handleInventoryClickEvent(event, player);
			}
		}
	}
	
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		if (event.getInventory().getName().equals(this.inventory.getName())) {
			event.setCancelled(this.cancel);
		}
	}
	
}

abstract class SupplyInventory<T extends BasePlugin<T>> extends PluginInventory<T> {

	public SupplyInventory(T plugin, InventoryHolder owner, int size, String title) {
		super(plugin, owner, size, title, false);
	}
	public SupplyInventory(T plugin, Inventory inv) {
		super(plugin, inv, false);
	}
	
}
