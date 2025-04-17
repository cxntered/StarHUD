package fin.starhud.hud

import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.hud.Position
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.libs.universal.UMinecraft
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.TextRenderer
import cc.polyfrost.oneconfig.renderer.asset.Image
import cc.polyfrost.oneconfig.renderer.scissor.ScissorHelper
import fin.starhud.config.ModConfig
import fin.starhud.util.NVGFlags
import fin.starhud.util.ServerPinger
import kotlin.math.min

class Ping : Hud(true) {
    override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
        if (UMinecraft.getMinecraft().isSingleplayer) return

        val ping = ServerPinger.averagePing.toInt()
        val step = min(ping / 150.0, 3.0).toInt()
        val color = getPingColor(step)

        NanoVGHelper.INSTANCE.setupAndDraw(
            true
        ) { vg: Long ->
            val scissor = ScissorHelper.INSTANCE.scissor(
                vg,
                x,
                y,
                63 * scale,
                13 * scale
            )
            NanoVGHelper.INSTANCE.drawImage(
                vg,
                Image("/assets/starhud/hud/ping.png", NVGFlags.NVG_IMAGE_NEAREST),
                x,
                y - (step * 13 * scale),
                63 * scale,
                52 * scale,
                color
            )
            ScissorHelper.INSTANCE.resetScissor(vg, scissor)
        }

        TextRenderer.drawScaledString(
            "$ping ms",
            x + (19 * scale),
            y + (3 * scale),
            color,
            TextRenderer.TextType.toType(0),
            scale
        )
    }

    override fun getWidth(scale: Float, example: Boolean): Float {
        return 63 * scale
    }

    override fun getHeight(scale: Float, example: Boolean): Float {
        return 13 * scale
    }

    override fun shouldShow(): Boolean {
        return super.shouldShow() && !UMinecraft.getMinecraft().isSingleplayer
    }

    private fun getPingColor(step: Int): Int {
        return when (step) {
            0 -> ModConfig.pingColors.excellent.rgbNoAlpha
            1 -> ModConfig.pingColors.fair.rgbNoAlpha
            2 -> ModConfig.pingColors.poor.rgbNoAlpha
            3 -> ModConfig.pingColors.critical.rgbNoAlpha
            else -> -1
        }
    }

    init {
        this.position = Position(this, -49f - 8f, -5f, getWidth(scale, true), getHeight(scale, true))
        this.position.anchor = Position.AnchorPosition.BOTTOM_RIGHT
    }
}