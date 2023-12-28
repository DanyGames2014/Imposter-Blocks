package net.danygames2014.imposterblocks.block;

import net.danygames2014.imposterblocks.init.BlockListener;
import net.danygames2014.imposterblocks.tileentity.CamoBlockTileEntity;
import net.danygames2014.uniwrench.api.WrenchMode;
import net.danygames2014.uniwrench.api.Wrenchable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class CamoSlab extends CamoBlock implements Wrenchable {

    public CamoSlab(Identifier identifier) {
        super(identifier);
        this.setOpacity(0);
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    @Override
    public void updateBoundingBox(BlockView blockView, int x, int y, int z) {
        switch (blockView.getBlockMeta(x, y, z)){
            case 0 -> this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            case 1 -> this.setBoundingBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            case 2 -> this.setBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
            case 3 -> this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
            case 4 -> this.setBoundingBox(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            case 5 -> this.setBoundingBox(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void onSteppedOn(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof PlayerEntity) {
            CamoBlockTileEntity tileEntity = (CamoBlockTileEntity) world.method_1777(x, y, z);
            int chompiness = 0;

            for (int i = 0; i < 6; i++) {
                if (tileEntity.textureIdentifier[i].path.equals("carved_pumpkin") && tileEntity.textureSide[i] == 3) {
                    chompiness += 1;
                }
            }

            if (chompiness > 0) {
                entity.damage(null, chompiness * 2);
                ((PlayerEntity) entity).method_490("CHOMP!");
            }
        }
    }

    @Override
    public void wrenchRightClick(ItemStack stack, PlayerEntity player, boolean isSneaking, World world, int x, int y, int z, int side, WrenchMode wrenchMode) {
        super.wrenchRightClick(stack, player, isSneaking, world, x, y, z, side, wrenchMode);

        CamoBlockTileEntity tileEntity = (CamoBlockTileEntity) world.method_1777(x, y, z);

        if (wrenchMode == WrenchMode.MODE_ROTATE) {
            int meta = world.getBlockMeta(x,y,z)+1;
            world.method_223(x, y, z, meta > 5 ? 0 : meta);
            world.method_243(x, y, z);
        }

        tileEntity.method_1073();
        world.method_157(x, y, z, tileEntity);

        world.method_243(x, y, z); // Update The Block
    }

    @Override
    public boolean isFullCube() {
        return false;
    }
}
