package fin.starhud.hud

import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.hud.Position
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.libs.universal.wrappers.UPlayer
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.TextRenderer
import cc.polyfrost.oneconfig.renderer.asset.Image
import cc.polyfrost.oneconfig.renderer.scissor.ScissorHelper
import fin.starhud.util.ItemDurability
import fin.starhud.util.NVGFlags
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class Hand : Hud(true) {
    @Switch(name = "Show Item Count")
    var showCount = true

    @Switch(name = "Show Item Durability")
    var showDurability = true

    @Color(name = "Color")
    var color = OneColor("#87CEEBFF")

    @Button(name = "Reset Color", text = "Reset")
    var resetColor = Runnable {
        color = OneColor("#87CEEBFF")
    }

    override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
        val item = getHeldItem() ?: return

        NanoVGHelper.INSTANCE.setupAndDraw(
            true
        ) { vg: Long ->
            if (showDurability && item.isItemStackDamageable) {
                val scissor = ScissorHelper.INSTANCE.scissor(
                    vg,
                    x,
                    y,
                    13 * scale,
                    13 * scale
                )
                NanoVGHelper.INSTANCE.drawImage(
                    vg,
                    Image("/assets/starhud/hud/hand.png", NVGFlags.NVG_IMAGE_NEAREST),
                    x,
                    y,
                    47 * scale,
                    13 * scale,
                    color.rgbNoAlpha
                )
                ScissorHelper.INSTANCE.resetScissor(vg, scissor)
                ItemDurability.renderHud(
                    vg,
                    item,
                    x + (14 * scale),
                    y,
                    scale
                )
            } else if (showCount) {
                NanoVGHelper.INSTANCE.drawImage(
                    vg,
                    Image("/assets/starhud/hud/hand.png", NVGFlags.NVG_IMAGE_NEAREST),
                    x,
                    y,
                    47 * scale,
                    13 * scale,
                    color.rgbNoAlpha
                )
            }
        }

        if (showCount && (!showDurability || !item.isItemStackDamageable)) {
            TextRenderer.drawScaledString(
                getItemCount(item).toString(),
                x + (19 * scale),
                y + (3 * scale),
                color.rgbNoAlpha,
                TextRenderer.TextType.toType(0),
                scale
            )
        }
    }

    override fun getWidth(scale: Float, example: Boolean): Float {
        return (if (getHeldItem()?.isItemStackDamageable != false) 63 else 47) * scale
    }

    override fun getHeight(scale: Float, example: Boolean): Float {
        return 13 * scale
    }

    override fun shouldShow(): Boolean {
        return super.shouldShow() && getHeldItem() != null
    }

    private fun getHeldItem(): ItemStack? {
        return UPlayer.getPlayer()?.heldItem
    }

    private fun getItemCount(item: ItemStack): Int {
        val inventory = UPlayer.getPlayer()?.inventory ?: return 0
        val itemId = Item.getIdFromItem(item.item)
        var count = 0

        for (i in 0 until inventory.sizeInventory) {
            val selectedItem: ItemStack? = inventory.getStackInSlot(i)
            if (selectedItem != null && itemId == Item.getIdFromItem(selectedItem.item)) count += item.stackSize
        }

        return count
    }

    init {
        this.position = Position(this, 121f, -25f, getWidth(scale, true), getHeight(scale, true))
        this.position.anchor = Position.AnchorPosition.BOTTOM_CENTER
        this.positionAlignment = 1 // left
    }
}