package Reika.ChromatiCraft.Base;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import Reika.ChromatiCraft.Block.Dimension.BlockDimensionDeco;
import Reika.ChromatiCraft.World.Dimension.ChromaDimensionManager.ChromaDimensionBiomeType;
import Reika.ChromatiCraft.World.Dimension.Generators.WorldGenFissure;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;


public abstract class ChromaDimensionBiomeTerrainShaper {

	protected final long dimensionSeed;
	private final HashSet<ChromaDimensionBiomeType> biomeSet = new HashSet();

	public ChromaDimensionBiomeTerrainShaper(long seed, ChromaDimensionBiomeType... c) {
		this(seed, ReikaJavaLibrary.makeListFrom(c));
	}

	public ChromaDimensionBiomeTerrainShaper(long seed, Collection<ChromaDimensionBiomeType> c) {
		dimensionSeed = seed;
		biomeSet.addAll(c);
	}

	public abstract void generateColumn(World world, int chunkX, int chunkZ, int i, int k, int surface, Random rand, double edgeFactor);

	public final boolean canGenerateIn(ChromaDimensionBiome b) {
		return biomeSet.contains(b.getExactType());
	}

	protected final void cutBlock(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		if ((b == Blocks.bedrock || WorldGenFissure.canCutInto(world, x, y, z)) && !(b instanceof BlockDimensionDeco)) {
			world.setBlock(x, y, z, Blocks.air, 0, 2);
		}
	}

	public abstract double getBiomeSearchDistance();

	protected final double calcR(int chunk, int d, double innerScale, double mainScale) {
		return ((chunk >> 4)+d*innerScale)*mainScale;
	}

}
