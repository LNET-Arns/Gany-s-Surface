package ganymedes01.ganyssurface.inventory;

import ganymedes01.ganyssurface.inventory.slots.SeedSlot;
import ganymedes01.ganyssurface.tileentities.TileEntityPlanter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Gany's Surface
 * 
 * @author ganymedes01
 * 
 */

public class ContainerPlanter extends Container {

	public ContainerPlanter(InventoryPlayer inventory, TileEntityPlanter tile) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				addSlotToContainer(new SeedSlot(tile, j + i * 3, 62 + j * 18, 17 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();

			if (slotIndex < 9) {
				if (!mergeItemStack(stack, 9, 45, true))
					return null;
			} else if (!mergeItemStack(stack, 0, 9, false))
				return null;

			if (stack.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (stack.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(player, stack);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}