package fin.starhud.util

import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.asset.Image
import cc.polyfrost.oneconfig.renderer.scissor.ScissorHelper
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.item.ItemStack
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
            color
        )
        ScissorHelper.INSTANCE.resetScissor(vg, scissor)
    }

    fun renderArmorHud(vg: Long, player: EntityPlayerSP?, index: Int, x: Float, y: Float, scale: Float) {
        val inventory = player?.inventory ?: return
        val item = inventory.armorItemInSlot(3 - index) ?: return

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
        return hsvToRgb(0.35F * step / 10.0F, 0.45F, 0.95F);
    }

    // taken from modern minecraft and converted to kotlin
    private fun hsvToRgb(hue: Float, saturation: Float, value: Float): Int {
        val hueSector = (hue * 6.0f).toInt() % 6
        val fractionalSector = hue * 6.0f - hueSector

        val p = value * (1.0f - saturation)
        val q = value * (1.0f - fractionalSector * saturation)
        val t = value * (1.0f - (1.0f - fractionalSector) * saturation)

        val (red, green, blue) = when (hueSector) {
            0 -> Triple(value, t, p)
            1 -> Triple(q, value, p)
            2 -> Triple(p, value, t)
            3 -> Triple(p, q, value)
            4 -> Triple(t, p, value)
            5 -> Triple(value, p, q)
            else -> throw RuntimeException("Something went wrong when converting from HSV to RGB. Input HSV: ($hue, $saturation, $value)")
        }

        return OneColor(
            (red * 255).toInt().coerceIn(0, 255),
            (green * 255).toInt().coerceIn(0, 255),
            (blue * 255).toInt().coerceIn(0, 255)
        ).rgbNoAlpha
    }
}