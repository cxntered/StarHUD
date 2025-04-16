package fin.starhud.hud

import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.hud.Position
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.libs.universal.UMinecraft
import cc.polyfrost.oneconfig.libs.universal.wrappers.UPlayer
import cc.polyfrost.oneconfig.platform.Platform
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.TextRenderer
import cc.polyfrost.oneconfig.renderer.asset.Image
import cc.polyfrost.oneconfig.renderer.scissor.ScissorHelper
import fin.starhud.config.ModConfig
import fin.starhud.util.NVGFlags

class Biome : Hud(true) {
    override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
        val icon = getDimensionIcon(UMinecraft.getWorld()?.provider?.dimensionId ?: 2)
        val color = getTextColorFromDimension(icon)

        NanoVGHelper.INSTANCE.setupAndDraw(
            true
        ) { vg: Long ->
            val scissor = ScissorHelper.INSTANCE.scissor(
                vg,
                x,
                y,
                24 * scale,
                13 * scale
            )
            NanoVGHelper.INSTANCE.drawImage(
                vg,
                Image("/assets/starhud/hud/biome.png", NVGFlags.NVG_IMAGE_NEAREST),
                x,
                y - (icon * 13 * scale),
                13 * scale,
                52 * scale
            )
            ScissorHelper.INSTANCE.resetScissor(vg, scissor)

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
            color,
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

    private fun getDimensionIcon(dimensionId: Int): Int {
        return when (dimensionId) {
            0 -> 0    // overworld
            -1 -> 1   // nether
            1 -> 2    // end
            else -> 3 // unknown
        }
    }

    private fun getTextColorFromDimension(dimension: Int): Int {
        return when (dimension) {
            0 -> ModConfig.dimensionColors.overworld.rgbNoAlpha
            1 -> ModConfig.dimensionColors.nether.rgbNoAlpha
            2 -> ModConfig.dimensionColors.end.rgbNoAlpha
            else -> ModConfig.dimensionColors.unknown.rgbNoAlpha
        }
    }

    init {
        this.position = Position(this, 0f, 5f, getWidth(scale, true), getHeight(scale, true))
        this.position.anchor = Position.AnchorPosition.TOP_CENTER
        this.positionAlignment = 2 // center
    }
}