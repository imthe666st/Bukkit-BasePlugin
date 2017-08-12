package Phecda.NBT;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagByte;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;

public class NBTEditor {
	
	private static Random random = new Random();
	
	public static NBTTagList getNBTTagList(ItemStack item, String s, int c) {
		// Get NMS ItemStack
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

		// Get compound. if it's null create a new tag.
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		
		// create our modifiers we will apply
		return compound.getList(s, c);
	}
	
	public static ItemStack applyNBTTagList(ItemStack item, String s, NBTTagList n) {
		// Get NMS ItemStack
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

		// Get compound. if it's null create a new tag.
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		

		compound.set(s, n);
		
		nmsStack.setTag(compound);
		
		return CraftItemStack.asBukkitCopy(nmsStack);
	}

	public static ItemStack setGenericAttackDamage(ItemStack item, int damage) { return setGenericAttackDamage(item, "mainhand", damage); } 
	public static ItemStack setGenericAttackDamage(ItemStack item, NBTSlotType slot, int damage) { return setGenericAttackDamage(item, slot.toString(), damage); } 
	public static ItemStack setGenericAttackDamage(ItemStack item, String slot, int damage) {
		NBTTagList modifiers = getNBTTagList(item, "AttributeModifiers", 10);
		// create compound.
		NBTTagCompound modCompound = new NBTTagCompound();
		modCompound.set("AttributeName", new NBTTagString("generic.attackDamage"));
		modCompound.set("Name", new NBTTagString("generic.attackDamage"));
		modCompound.set("Amount", new NBTTagInt(damage));
		modCompound.set("UUIDLeast", new NBTTagInt(random.nextInt(0xffffff)));
		modCompound.set("UUIDMost", new NBTTagInt(random.nextInt(0xffffff)));
		// sets the slot.
		modCompound.set("Slot", new NBTTagString(slot));

		modifiers.add(modCompound);

		return applyNBTTagList(item, "AttributeModifiers", modifiers);
	}
	

	public static ItemStack setGenericMaxHealth(ItemStack item, int health) { return setGenericMaxHealth(item, "mainhand", health); }
	public static ItemStack setGenericMaxHealth(ItemStack item, NBTSlotType slot, String hand, int health) { return setGenericMaxHealth(item, slot.toString(), health); }
	public static ItemStack setGenericMaxHealth(ItemStack item, String slot, int health) {
		NBTTagList modifiers = getNBTTagList(item, "AttributeModifiers", 10);

		// create compound.
		NBTTagCompound modCompound = new NBTTagCompound();
		modCompound.set("AttributeName", new NBTTagString("generic.maxHealth"));
		modCompound.set("Name", new NBTTagString("generic.attackDamage"));
		modCompound.set("Amount", new NBTTagInt(health));
		modCompound.set("UUIDLeast", new NBTTagInt(random.nextInt(0xffffff)));
		modCompound.set("UUIDMost", new NBTTagInt(random.nextInt(0xffffff)));
		// sets the slot.
		modCompound.set("Slot", new NBTTagString(slot));

		modifiers.add(modCompound);

		return applyNBTTagList(item, "AttributeModifiers", modifiers);
	}
	
	public static ItemStack setGenericArmor(ItemStack item, int armor) { return setGenericMaxHealth(item, "mainhand", armor); }
	public static ItemStack setGenericArmor(ItemStack item, NBTSlotType slot, String hand, int armor) { return setGenericArmor(item, slot.toString(), armor); }
	public static ItemStack setGenericArmor(ItemStack item, String slot, int armor) {
		NBTTagList modifiers = getNBTTagList(item, "AttributeModifiers", 10);

		// create compound.
		NBTTagCompound modCompound = new NBTTagCompound();
		modCompound.set("AttributeName", new NBTTagString("generic.armor"));
		modCompound.set("Name", new NBTTagString("generic.armor"));
		modCompound.set("Amount", new NBTTagDouble(armor));
		modCompound.set("UUIDLeast", new NBTTagInt(random.nextInt(0xffffff)));
		modCompound.set("UUIDMost", new NBTTagInt(random.nextInt(0xffffff)));
		// sets the slot.
		modCompound.set("Slot", new NBTTagString(slot));

		modifiers.add(modCompound);

		return applyNBTTagList(item, "AttributeModifiers", modifiers);
	}
	
	public static ItemStack setGenericArmorToughness(ItemStack item, int armor) { return setGenericMaxHealth(item, "mainhand", armor); }
	public static ItemStack setGenericArmorToughness(ItemStack item, NBTSlotType slot, String hand, int armor) { return setGenericArmorToughness(item, slot.toString(), armor); }
	public static ItemStack setGenericArmorToughness(ItemStack item, String slot, int armor) {
		NBTTagList modifiers = getNBTTagList(item, "AttributeModifiers", 10);

		// create compound.
		NBTTagCompound modCompound = new NBTTagCompound();
		modCompound.set("AttributeName", new NBTTagString("generic.armorToughness"));
		modCompound.set("Name", new NBTTagString("generic.armorToughness"));
		modCompound.set("Amount", new NBTTagDouble(armor));
		modCompound.set("UUIDLeast", new NBTTagInt(random.nextInt(0xffffff)));
		modCompound.set("UUIDMost", new NBTTagInt(random.nextInt(0xffffff)));
		// sets the slot.
		modCompound.set("Slot", new NBTTagString(slot));

		modifiers.add(modCompound);

		return applyNBTTagList(item, "AttributeModifiers", modifiers);
	}

	public static ItemStack setGenericAttackSpeed(ItemStack item, double speed) { return setGenericMovementSpeed(item, "mainhand", speed); }
	public static ItemStack setGenericAttackSpeed(ItemStack item, NBTSlotType slot, String hand, double speed) { return setGenericAttackSpeed(item, slot.toString(), speed); }
	public static ItemStack setGenericAttackSpeed(ItemStack item, String slot, double speed) {
		NBTTagList modifiers = getNBTTagList(item, "AttributeModifiers", 10);

		// create compound.
		NBTTagCompound modCompound = new NBTTagCompound();
		modCompound.set("AttributeName", new NBTTagString("generic.attackSpeed"));
		modCompound.set("Name", new NBTTagString("generic.attackSpeed"));
		modCompound.set("Amount", new NBTTagDouble(speed));
		modCompound.set("UUIDLeast", new NBTTagInt(random.nextInt(0xffffff)));
		modCompound.set("UUIDMost", new NBTTagInt(random.nextInt(0xffffff)));
		// sets the slot.
		modCompound.set("Slot", new NBTTagString(slot));

		modifiers.add(modCompound);

		return applyNBTTagList(item, "AttributeModifiers", modifiers);
	}

	public static ItemStack setGenericMovementSpeed(ItemStack item, double speed) { return setGenericMovementSpeed(item, "mainhand", speed); }
	public static ItemStack setGenericMovementSpeed(ItemStack item, NBTSlotType slot, String hand, double speed) { return setGenericMovementSpeed(item, slot.toString(), speed); }
	public static ItemStack setGenericMovementSpeed(ItemStack item, String slot, double speed) {
		NBTTagList modifiers = getNBTTagList(item, "AttributeModifiers", 10);

		// create compound.
		NBTTagCompound modCompound = new NBTTagCompound();
		modCompound.set("AttributeName", new NBTTagString("generic.movementSpeed"));
		modCompound.set("Name", new NBTTagString("generic.movementSpeed"));
		modCompound.set("Amount", new NBTTagDouble(speed));
		modCompound.set("UUIDLeast", new NBTTagInt(random.nextInt(0xffffff)));
		modCompound.set("UUIDMost", new NBTTagInt(random.nextInt(0xffffff)));
		// sets the slot.
		modCompound.set("Slot", new NBTTagString(slot));

		modifiers.add(modCompound);

		return applyNBTTagList(item, "AttributeModifiers", modifiers);
	}
	
	public static ItemStack resetGenericAttributeModifiers(ItemStack item) {
		return applyNBTTagList(item, "AttributeModifiers", new NBTTagList());
	}
	
}
