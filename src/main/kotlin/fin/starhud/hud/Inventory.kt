package fin.starhud.hud

import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.hud.Position
import cc.polyfrost.oneconfig.libs.universal.UGraphics.GL
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.libs.universal.UMinecraft
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.asset.Image
import fin.starhud.util.NVGFlags
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.item.ItemStack

class Inventory : Hud(true) {
    @Switch(name = "Draw Vertical")
    var drawVertical = true

    override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
        NanoVGHelper.INSTANCE.setupAndDraw(
            true
        ) { vg: Long ->
            NanoVGHelper.INSTANCE.drawImage(
                vg,
                Image(
                    "/assets/starhud/hud/inventory${if (drawVertical) "_vertical" else ""}.png",
                    NVGFlags.NVG_IMAGE_NEAREST
                ),
                x,
                y,
                (if (drawVertical) 68 else 206) * scale,
                (if (drawVertical) 206 else 68) * scale
            )
        }

        var itemX: Int
        var itemY: Int

        for (i in 0 until 27) {
            val item = UMinecraft.getPlayer()?.inventory?.mainInventory?.get(i + 9) ?: continue

            if (drawVertical) {
                itemX = 49 - (i / 9) * 23
                itemY = 3 + (i % 9) * 23
                renderItem(item, x, y, itemX, itemY, scale)
            } else {
                itemX = 3 + (i % 9) * 23
                itemY = 3 + (i / 9) * 23
                renderItem(item, x, y, itemX, itemY, scale)
            }
        }
    }

    override fun getWidth(scale: Float, example: Boolean): Float {
        return (if (drawVertical) 68 else 206) * scale
    }

    override fun getHeight(scale: Float, example: Boolean): Float {
        return (if (drawVertical) 206 else 68) * scale
    }

    override fun shouldShow(): Boolean {
        return super.shouldShow() && UMinecraft.getPlayer()?.inventory?.mainInventory?.slice(9 until 36)
            ?.any { it != null } == true
    }

    private fun renderItem(item: ItemStack, x: Float, y: Float, itemX: Int, itemY: Int, scale: Float) {
        val scaledX = ((x / scale) + itemX).toInt()
        val scaledY = ((y / scale) + itemY).toInt()
        with(UMinecraft.getMinecraft().renderItem) {
            GL.pushMatrix()
            RenderHelper.enableGUIStandardItemLighting()
            GL.scale(scale, scale, 1f)
            renderItemAndEffectIntoGUI(item, scaledX, scaledY)
            renderItemOverlayIntoGUI(UMinecraft.getFontRenderer(), item, scaledX, scaledY, null)
            RenderHelper.disableStandardItemLighting()
            GL.popMatrix()
        }
    }

    init {
        this.position = Position(this, -5f, 0f, getWidth(scale, true), getHeight(scale, true))
        this.position.anchor = Position.AnchorPosition.MIDDLE_RIGHT
        this.scale = 0.5f
    }
}