/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.ChromatiCraft.Block.Dimension.Structure;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import Reika.ChromatiCraft.ChromatiCraft;
import Reika.ChromatiCraft.Auxiliary.CrystalMusicManager;
import Reika.ChromatiCraft.Base.CrystalTypeBlock;
import Reika.ChromatiCraft.Registry.CrystalElement;
import Reika.ChromatiCraft.Render.Particle.EntityRuneFX;
import Reika.DragonAPI.Libraries.Java.ReikaRandomHelper;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMusicTrigger extends Block {

	private final IIcon[] icons = new IIcon[2];

	public BlockMusicTrigger(Material mat) {
		super(mat);

		this.setResistance(60000);
		this.setBlockUnbreakable();
		this.setCreativeTab(ChromatiCraft.tabChromaGen);
	}

	@Override
	public IIcon getIcon(int s, int meta) {
		return s <= 1 ? icons[0] : icons[1];
	}

	@Override
	public void registerBlockIcons(IIconRegister ico) {
		icons[0] = ico.registerIcon("chromaticraft:dimstruct/musictrigger");
		icons[1] = ico.registerIcon("chromaticraft:dimstruct/musictrigger_side");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int s, float a, float b, float c) {
		if (s > 1) {
			int idx = this.getIndex(s, a, b, c);
			if (idx >= 0) {
				Block bk = world.getBlock(x, y+1, z);
				if (bk instanceof CrystalTypeBlock) {
					int meta = world.getBlockMetadata(x, y+1, z);
					CrystalElement e = CrystalElement.elements[meta];
					float p = CrystalMusicManager.instance.getScaledDing(e, idx);
					CrystalTypeBlock.ding(world, x, y, z, e, p);
					if (world.isRemote) {
						this.createParticle(world, x, y, z, e);
					}
				}
			}
		}
		return true;
	}

	@SideOnly(Side.CLIENT)
	private void createParticle(World world, int x, int y, int z, CrystalElement e) {
		double v = ReikaRandomHelper.getRandomPlusMinus(0.125, 0.0625);
		EntityRuneFX fx = new EntityRuneFX(world, x+0.5, y+1, z+0.5, 0, v, 0, e).setGravity(0).setScale(4).setLife(20);
		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
	}

	private int getIndex(int s, float a, float b, float c) { //0-3 or -1 for none
		double m1a = 0.125;
		double m1b = 0.4375;
		double m2a = 0.5625;
		double m2b = 0.875;

		if (s == 2 || s == 5) {
			a = 1-a;
			c = 1-c;
		}

		if (s == 4 || s == 5) { //a == 0 or 1
			if (ReikaMathLibrary.isValueInsideBoundsIncl(m1a, m1b, c) && ReikaMathLibrary.isValueInsideBoundsIncl(m2a, m2b, b))
				return 0;
			if (ReikaMathLibrary.isValueInsideBoundsIncl(m2a, m2b, c) && ReikaMathLibrary.isValueInsideBoundsIncl(m2a, m2b, b))
				return 1;
			if (ReikaMathLibrary.isValueInsideBoundsIncl(m2a, m2b, c) && ReikaMathLibrary.isValueInsideBoundsIncl(m1a, m1b, b))
				return 2;
			if (ReikaMathLibrary.isValueInsideBoundsIncl(m1a, m1b, c) && ReikaMathLibrary.isValueInsideBoundsIncl(m1a, m1b, b))
				return 3;
		}
		else if (s == 2 || s == 3) { //c == 0 or 1
			if (ReikaMathLibrary.isValueInsideBoundsIncl(m1a, m1b, a) && ReikaMathLibrary.isValueInsideBoundsIncl(m2a, m2b, b))
				return 0;
			if (ReikaMathLibrary.isValueInsideBoundsIncl(m2a, m2b, a) && ReikaMathLibrary.isValueInsideBoundsIncl(m2a, m2b, b))
				return 1;
			if (ReikaMathLibrary.isValueInsideBoundsIncl(m2a, m2b, a) && ReikaMathLibrary.isValueInsideBoundsIncl(m1a, m1b, b))
				return 2;
			if (ReikaMathLibrary.isValueInsideBoundsIncl(m1a, m1b, a) && ReikaMathLibrary.isValueInsideBoundsIncl(m1a, m1b, b))
				return 3;
		}
		return -1;
	}

}
