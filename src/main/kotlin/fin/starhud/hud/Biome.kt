package fin.starhud.hud

import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.OptionSize
import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.hud.Position
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.libs.universal.UMinecraft
import cc.polyfrost.oneconfig.libs.universal.wrappers.UPlayer
import cc.polyfrost.oneconfig.platform.Platform
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.TextRenderer
import cc.polyfrost.oneconfig.renderer.asset.Image
import fin.starhud.util.NVGFlags

class Biome() : Hud(true) {
    @Color(name = "Color", size = OptionSize.DUAL)
    var color = OneColor("#FFFFFFFF")

    override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
        NanoVGHelper.INSTANCE.setupAndDraw(
            true
        ) { vg: Long ->
            NanoVGHelper.INSTANCE.drawImage(
                vg,
                Image("/assets/starhud/hud/biome.png", NVGFlags.NVG_IMAGE_NEAREST),
                x,
                y,
                13 * scale,
                13 * scale,
                color.rgbNoAlpha
            )

            val width = Platform.getGLPlatform().getStringWidth(getBiomeName())
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
            getBiomeName(),
            x + (19 * scale),
            y + (3 * scale),
            color.rgbNoAlpha,
            TextRenderer.TextType.toType(0),
            scale
        )
    }

    override fun getWidth(scale: Float, example: Boolean): Float {
        return ((14 + Platform.getGLPlatform().getStringWidth(getBiomeName()) + 9) * scale)
    }

    override fun getHeight(scale: Float, example: Boolean): Float {
        return 13 * scale
    }


    private fun getBiomeName(): String {
        return UMinecraft.getWorld()?.getBiomeGenForCoords(UPlayer.getPlayer()?.position)?.biomeName ?: "Unknown"
    }

    init {
        this.position = Position(this, 0f, 5f, getWidth(scale, true), getHeight(scale, true))
        this.position.anchor = Position.AnchorPosition.TOP_CENTER
        this.positionAlignment = 2
    }
}