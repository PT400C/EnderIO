package crazypants.enderio.conduit.item.filter;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import com.enderio.core.api.client.gui.IResourceTooltipProvider;
import com.enderio.core.client.handlers.SpecialTooltipHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crazypants.enderio.EnderIO;
import crazypants.enderio.EnderIOTab;
import crazypants.enderio.ModObject;
import crazypants.enderio.conduit.item.FilterRegister;

public class ItemLimitedItemFilter extends Item implements IItemFilterUpgrade, IResourceTooltipProvider {

    public static ItemLimitedItemFilter create() {
        ItemLimitedItemFilter result = new ItemLimitedItemFilter();
        result.init();
        return result;
    }

    protected ItemLimitedItemFilter() {
        setCreativeTab(EnderIOTab.tabEnderIO);
        setUnlocalizedName(ModObject.itemLimitedItemFilter.unlocalisedName);
        setMaxDamage(0);
        setMaxStackSize(64);
    }

    protected void init() {
        GameRegistry.registerItem(this, ModObject.itemLimitedItemFilter.unlocalisedName);
    }

    @Override
    public IItemFilter createFilterFromStack(ItemStack stack) {
        IItemFilter filter = new ItemFilterLimited();
        if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("filter")) {
            filter.readFromNBT(stack.stackTagCompound.getCompoundTag("filter"));
        }
        return filter;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister IIconRegister) {
        itemIcon = IIconRegister.registerIcon("enderio:limitedItemFilter");
    }

    @Override
    public String getUnlocalizedNameForTooltip(ItemStack stack) {
        return getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (FilterRegister.isFilterSet(par1ItemStack)) {
            if (SpecialTooltipHandler.showAdvancedTooltips()) {
                par3List.add(EnumChatFormatting.ITALIC + EnderIO.lang.localize("itemConduitFilterUpgrade.configured"));
                par3List.add(
                        EnumChatFormatting.ITALIC
                                + EnderIO.lang.localize("itemConduitFilterUpgrade.clearConfigMethod"));
            }
        }
    }
}
