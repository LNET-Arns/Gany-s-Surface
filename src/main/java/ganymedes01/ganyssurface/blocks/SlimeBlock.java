package ganymedes01.ganyssurface.blocks;

import ganymedes01.ganyssurface.GanysSurface;
import ganymedes01.ganyssurface.core.utils.Utils;
import ganymedes01.ganyssurface.lib.ModSounds;
import ganymedes01.ganyssurface.lib.RenderIDs;
import ganymedes01.ganyssurface.lib.Strings;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Surface
 *
 * @author ganymedes01
 *
 */

public class SlimeBlock extends Block {

	public SlimeBlock() {
		super(Material.cloth);
		setHardness(2.0F);
		setTickRandomly(true);
		setHarvestLevel("shovel", 0);
		setBlockTextureName("slime");
		setStepSound(ModSounds.soundSlime);
		setBlockName(Utils.getUnlocalizedName(Strings.SLIME_BLOCK_NAME));
		setCreativeTab(GanysSurface.enableSlimeBlock ? GanysSurface.surfaceTab : null);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (!entity.isSneaking()) {
			entity.fallDistance = 0.0F;
			entity.motionY = 1.0F;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.875F, z + 1);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (rand.nextInt(40) == 20)
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				Block block = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
				int meta = world.getBlockMetadata(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

				if (block.getMaterial() == Material.water && meta == 0) {
					world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, this);
					return;
				}
			}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(Items.slime_ball, 4));
		return list;
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.SLIME_BLOCK;
	}
}