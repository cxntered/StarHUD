package fin.starhud.hud

import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.hud.Position
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.libs.universal.UMinecraft
import cc.polyfrost.oneconfig.platform.Platform
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.TextRenderer
import cc.polyfrost.oneconfig.renderer.asset.Image
import fin.starhud.util.NVGFlags

class Day : Hud(true) {
    @Color(name = "Color")
    var color = OneColor("#FFFFFFFF")

    @Button(name = "Reset Color", text = "Reset")
    var resetColor = Runnable {
        color = OneColor("#FFFFFFFF")
    }

    override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
        val dayCount = getDayCount()

        NanoVGHelper.INSTANCE.setupAndDraw(
            true
        ) { vg: Long ->
            NanoVGHelper.INSTANCE.drawImage(
                vg,
                Image("/assets/starhud/hud/day.png", NVGFlags.NVG_IMAGE_NEAREST),
                x,
                y,
                13 * scale,
                13 * scale,
                color.rgbNoAlpha
            )

            val width = Platform.getGLPlatform().getStringWidth(dayCount)
            NanoVGHelper.INSTANCE.drawRect(
                vg,
                x + (14 * scale),
                y,
                (width + 8) * scale,
                13 * scale,
                OneColor("#00000080").rgb
            )
            NanoVGHelper.INSTANCE.drawRect(
                vg,
                x + ((14 + width + 8) * scale),
                y + (scale),
                scale,
                (11 * scale),
                OneColor("#00000080").rgb
            )
        }

        TextRenderer.drawScaledString(
            dayCount,
            x + (19 * scale),
            y + (3 * scale),
            color.rgbNoAlpha,
            TextRenderer.TextType.toType(0),
            scale
        )
    }

    override fun getWidth(scale: Float, example: Boolean): Float {
        return ((14 + Platform.getGLPlatform().getStringWidth(getDayCount()) + 9) * scale)
    }

    override fun getHeight(scale: Float, example: Boolean): Float {
        return 13 * scale
    }

    private fun getDayCount(): String {
        val world = UMinecraft.getWorld() ?: return "0"
        return (world.worldTime / 24000L).toString()
    }

    init {
        this.position = Position(this, 5f, 47f, getWidth(scale, true), getHeight(scale, true))
        this.position.anchor = Position.AnchorPosition.TOP_LEFT
    }
}