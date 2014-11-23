package Reika.ChromatiCraft.Auxiliary.RecipeManagers.CastingRecipes;

import net.minecraft.item.ItemStack;
import Reika.ChromatiCraft.Auxiliary.ChromaStacks;
import Reika.ChromatiCraft.Auxiliary.RecipeManagers.CastingRecipe.MultiBlockCastingRecipe;
import Reika.ChromatiCraft.Registry.ChromaItems;
import Reika.ChromatiCraft.Registry.CrystalElement;

public class UpgradeRecipe extends MultiBlockCastingRecipe {

	public UpgradeRecipe(ItemStack out, ItemStack main) {
		super(out, main);

		this.addAuxItem(ChromaStacks.elementDust, -2, -2);
		this.addAuxItem(ChromaStacks.elementDust, 2, -2);
		this.addAuxItem(ChromaStacks.elementDust, -2, 2);
		this.addAuxItem(ChromaStacks.elementDust, 2, 2);

		this.addAuxItem(ChromaItems.ELEMENTAL.getStackOfMetadata(CrystalElement.PURPLE.ordinal()), -2, 0);
		this.addAuxItem(ChromaItems.ELEMENTAL.getStackOfMetadata(CrystalElement.PURPLE.ordinal()), 2, 0);
		this.addAuxItem(ChromaItems.ELEMENTAL.getStackOfMetadata(CrystalElement.BLACK.ordinal()), 0, -2);
		this.addAuxItem(ChromaStacks.bindingCrystal, 0, 2);
	}

}