package mcjty.rftools.blocks.storage;

import mcjty.lib.api.IModuleSupport;
import mcjty.lib.container.GenericGuiContainer;
import mcjty.lib.network.clientinfo.PacketGetInfoFromServer;
import mcjty.lib.varia.ModuleSupport;
import mcjty.rftools.Achievements;
import mcjty.rftools.RFTools;
import mcjty.rftools.blocks.GenericRFToolsBlock;
import mcjty.rftools.network.RFToolsMessages;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static mcjty.rftools.blocks.storage.ModularAmountOverlay.*;
import static mcjty.rftools.blocks.storage.ModularTypeModule.*;

public class ModularStorageBlock extends GenericRFToolsBlock<ModularStorageTileEntity, ModularStorageContainer> {

    public static final PropertyEnum<ModularTypeModule> TYPEMODULE = PropertyEnum.create("type", ModularTypeModule.class);
    public static final PropertyEnum<ModularAmountOverlay> AMOUNT = PropertyEnum.create("amount", ModularAmountOverlay.class);

    public ModularStorageBlock() {
        super(Material.IRON, ModularStorageTileEntity.class, ModularStorageContainer.class, "modular_storage", true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Class<? extends GenericGuiContainer> getGuiClass() {
        return GuiModularStorage.class;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {
        // @@@ temporary
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public int getGuiID() {
        return RFTools.GUI_MODULAR_STORAGE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean whatIsThis) {
        super.addInformation(itemStack, player, list, whatIsThis);

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            list.add(TextFormatting.WHITE + "This modular storage system can store a lot");
            list.add(TextFormatting.WHITE + "of items and allows easy searching and filtering.");
            list.add(TextFormatting.WHITE + "You must first insert a storage module item before");
            list.add(TextFormatting.WHITE + "you can use it");
        } else {
            list.add(TextFormatting.WHITE + RFTools.SHIFT_MESSAGE);
        }
    }

    private static long lastTime = 0;

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        ModularStorageTileEntity te = (ModularStorageTileEntity) world.getTileEntity(pos);
        ItemStack stack = te.getInventoryHelper().getStackInSlot(ModularStorageContainer.SLOT_TYPE_MODULE);

        int level = te.getRenderLevel();
        int remoteId = te.getRemoteId();

        ModularAmountOverlay p = AMOUNT_NONE;
        if (remoteId > 0) {
            switch (level) {
                case -1: p = AMOUNT_NONE; break;
                case 0: p = AMOUNT_R0; break;
                case 1: p = AMOUNT_R1; break;
                case 2: p = AMOUNT_R2; break;
                case 3: p = AMOUNT_R3; break;
                case 4: p = AMOUNT_R4; break;
                case 5: p = AMOUNT_R5; break;
                case 6: p = AMOUNT_R6; break;
                case 7: p = AMOUNT_R7; break;
            }
        } else {
            switch (level) {
                case -1: p = AMOUNT_NONE; break;
                case 0: p = AMOUNT_G0; break;
                case 1: p = AMOUNT_G1; break;
                case 2: p = AMOUNT_G2; break;
                case 3: p = AMOUNT_G3; break;
                case 4: p = AMOUNT_G4; break;
                case 5: p = AMOUNT_G5; break;
                case 6: p = AMOUNT_G6; break;
                case 7: p = AMOUNT_G7; break;
            }
        }

        IBlockState newstate = state.withProperty(AMOUNT, p);

        if (stack == null) {
            return newstate.withProperty(TYPEMODULE, TYPE_NONE);
        } else if (stack.getItem() == ModularStorageSetup.genericTypeItem) {
            return newstate.withProperty(TYPEMODULE, TYPE_GENERIC);
        } else if (stack.getItem() == ModularStorageSetup.oreDictTypeItem) {
            return newstate.withProperty(TYPEMODULE, TYPE_ORE);
        }
        return newstate.withProperty(TYPEMODULE, TYPE_NONE);
    }

    @Override
    protected IModuleSupport getModuleSupport() {
        return new ModuleSupport(ModularStorageContainer.SLOT_FILTER_MODULE) {
            @Override
            public boolean isModule(ItemStack itemStack) {
                return itemStack.getItem() == ModularStorageSetup.storageFilterItem;
            }
        };
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, TYPEMODULE, AMOUNT);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return /*layer == BlockRenderLayer.SOLID || */ layer == BlockRenderLayer.CUTOUT;
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        super.addProbeInfo(mode, probeInfo, player, world, blockState, data);
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof ModularStorageTileEntity) {
            ModularStorageTileEntity modularStorageTileEntity = (ModularStorageTileEntity) te;
            int maxSize = modularStorageTileEntity.getMaxSize();
            if (maxSize == 0) {
                probeInfo.text(TextFormatting.YELLOW + "No storage module!");
            } else {
                ItemStack storageModule = modularStorageTileEntity.getStackInSlot(ModularStorageContainer.SLOT_STORAGE_MODULE);
                if (storageModule != null && storageModule.getTagCompound().hasKey("display")) {
                    probeInfo.text(TextFormatting.YELLOW + "Module: " + TextFormatting.WHITE + storageModule.getDisplayName());
                }
                int stacks = modularStorageTileEntity.getNumStacks();
                if (stacks == -1) {
                    probeInfo.text(TextFormatting.YELLOW + "Maximum size: " + maxSize);
                } else {
                    probeInfo.text(TextFormatting.GREEN + "" + stacks + " out of " + maxSize);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        super.getWailaBody(itemStack, currenttip, accessor, config);
        TileEntity te = accessor.getTileEntity();
        if (te instanceof ModularStorageTileEntity) {
            ModularStorageTileEntity modularStorageTileEntity = (ModularStorageTileEntity) te;
            int maxSize = modularStorageTileEntity.getMaxSize();
            if (maxSize == 0) {
                currenttip.add(TextFormatting.YELLOW + "No storage module!");
            } else {
                if (System.currentTimeMillis() - lastTime > 500) {
                    lastTime = System.currentTimeMillis();
                    RFToolsMessages.INSTANCE.sendToServer(new PacketGetInfoFromServer(RFTools.MODID, new StorageInfoPacketServer(modularStorageTileEntity.getWorld().provider.getDimension(),
                            modularStorageTileEntity.getPos())));
                }
                if (!StorageInfoPacketClient.nameModuleReceived.isEmpty()) {
                    currenttip.add(TextFormatting.YELLOW + "Module: " + TextFormatting.WHITE + StorageInfoPacketClient.nameModuleReceived);
                }
                int stacks = StorageInfoPacketClient.cntReceived;
                if (stacks == -1) {
                    currenttip.add(TextFormatting.YELLOW + "Maximum size: " + maxSize);
                } else {
                    currenttip.add(TextFormatting.GREEN + "" + stacks + " out of " + maxSize);
                }
            }
        }
        return currenttip;
    }

    @Override
    public Container createServerContainer(EntityPlayer entityPlayer, TileEntity tileEntity) {
        // Make sure the client has sufficient information to show the data.
        ((ModularStorageTileEntity) tileEntity).markDirtyClient();
        return super.createServerContainer(entityPlayer, tileEntity);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (placer instanceof EntityPlayer) {
            Achievements.trigger((EntityPlayer) placer, Achievements.allTheItems);
        }
    }
}
