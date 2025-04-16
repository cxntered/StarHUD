package fin.starhud.hud

import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.OptionSize
import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.TextRenderer
import cc.polyfrost.oneconfig.renderer.asset.Image
import fin.starhud.util.NVGFlags
import net.minecraft.client.Minecraft

class FPS() : Hud(true, 5f, 1080f - 13f - 5f) {
    @Color(name = "Color", size = OptionSize.DUAL)
    var color = OneColor("#E5ECF8FF")

    override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
        NanoVGHelper.INSTANCE.setupAndDraw(
            true
        ) { vg: Long ->
            NanoVGHelper.INSTANCE.drawImage(
                vg,
                Image("/assets/starhud/hud/fps.png", NVGFlags.NVG_IMAGE_NEAREST),
                x,
                y,
                69 * scale,
                13 * scale,
                color.rgbNoAlpha
            )
        }

        TextRenderer.drawScaledString(
            "${Minecraft.getDebugFPS()} FPS",
            x + (19 * scale),
            y + (3 * scale),
            color.rgbNoAlpha,
            TextRenderer.TextType.toType(0),
            scale
        )
    }

    override fun getWidth(scale: Float, example: Boolean): Float {
        return 69 * scale
    }

    override fun getHeight(scale: Float, example: Boolean): Float {
        return 13 * scale
    }
}