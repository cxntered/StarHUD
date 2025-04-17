package fin.starhud.util

import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.asset.Image
import cc.polyfrost.oneconfig.renderer.scissor.ScissorHelper
import net.minecraft.item.ItemStack
import net.minecraft.util.MathHelper
import kotlin.math.roundToInt

object ItemDurability {
    fun renderHud(vg: Long, item: ItemStack, x: Float, y: Float, scale: Float) {
        val step = getItemBarStep(item)
        val color = getItemBarColor(step)

        // draw the background
        NanoVGHelper.INSTANCE.drawImage(
            vg,
            Image("/assets/starhud/hud/durability_background.png", NVGFlags.NVG_IMAGE_NEAREST),
            x,
            y,
            49 * scale,
            13 * scale
        )

        // draw the durability bar
        val scissor = ScissorHelper.INSTANCE.scissor(
            vg,
            x + (5 * scale),
            y + (3 * scale),
            4 * step * scale,
            7 * scale
        )
        NanoVGHelper.INSTANCE.drawImage(
            vg,
            Image("/assets/starhud/hud/durability.png", NVGFlags.NVG_IMAGE_NEAREST),
            x + (5 * scale),
            y + (3 * scale),
            40 * scale,
            7 * scale,
            color or 0xFF000000.toInt()
        )
        ScissorHelper.INSTANCE.resetScissor(vg, scissor)
    }

    fun renderArmorHud(vg: Long, item: ItemStack, index: Int, x: Float, y: Float, scale: Float) {
        // draw the icon
        val scissor = ScissorHelper.INSTANCE.scissor(
            vg,
            x,
            y,
            65 * scale,
            13 * scale
        )
        NanoVGHelper.INSTANCE.drawImage(
            vg,
            Image("/assets/starhud/hud/armor.png", NVGFlags.NVG_IMAGE_NEAREST),
            x,
            y - (14 * index * scale),
            13 * scale,
            55 * scale
        )
        ScissorHelper.INSTANCE.resetScissor(vg, scissor)

        // draw the durability bar
        renderHud(vg, item, x + (14 * scale), y, scale)
    }

    // get the durability "steps" or progress
    private fun getItemBarStep(item: ItemStack): Int {
        return ((10 - (item.itemDamage.toFloat() * 10 / item.maxDamage.toFloat())).roundToInt()).coerceIn(0, 10)
    }

    // color transition from pastel (red to green)
    private fun getItemBarColor(step: Int): Int {
        return MathHelper.hsvToRGB(0.35F * step / 10.0F, 0.45F, 0.95F);
    }
}