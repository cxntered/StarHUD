package fin.starhud.hud

import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.OptionSize
import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.hud.Position
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.libs.universal.wrappers.UPlayer
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.TextRenderer
import cc.polyfrost.oneconfig.renderer.asset.Image
import cc.polyfrost.oneconfig.renderer.scissor.ScissorHelper
import fin.starhud.config.ModConfig
import fin.starhud.util.NVGFlags
import net.minecraft.util.MathHelper
import kotlin.math.roundToInt

class Direction : Hud(true) {
    @Switch(name = "Include Ordinal Directions", size = OptionSize.DUAL)
    var includeOrdinalDirections = false

    override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
        val player = UPlayer.getPlayer() ?: return
        val yaw = (MathHelper.wrapAngleTo180_float(player.rotationYaw) * 10.0f).roundToInt() / 10.0f
        val icon = if (includeOrdinalDirections) getOrdinalDirectionIcon(yaw) else getCardinalDirectionIcon(yaw)
        val color = if (includeOrdinalDirections) getDirectionColor(icon) else getDirectionColor(icon * 2)

        NanoVGHelper.INSTANCE.setupAndDraw(
            true
        ) { vg: Long ->
            val scissor = ScissorHelper.INSTANCE.scissor(
                vg,
                x,
                y,
                (if (includeOrdinalDirections) 61 else 55) * scale,
                13 * scale
            )
            NanoVGHelper.INSTANCE.drawImage(
                vg,
                Image(
                    "/assets/starhud/hud/${if (includeOrdinalDirections) "direction_ordinal" else "direction"}.png",
                    NVGFlags.NVG_IMAGE_NEAREST
                ),
                x,
                y - (icon * 13 * scale),
                (if (includeOrdinalDirections) 61 else 55) * scale,
                (if (includeOrdinalDirections) 104 else 52) * scale,
                color
            )
            ScissorHelper.INSTANCE.resetScissor(vg, scissor)
        }

        TextRenderer.drawScaledString(
            yaw.toString(),
            x + ((if (includeOrdinalDirections) 25 else 19) * scale),
            y + (3 * scale),
            color,
            TextRenderer.TextType.toType(0),
            scale
        )
    }

    override fun getWidth(scale: Float, example: Boolean): Float {
        return (if (includeOrdinalDirections) 61 else 55) * scale
    }

    override fun getHeight(scale: Float, example: Boolean): Float {
        return 13 * scale
    }

    private fun getOrdinalDirectionIcon(yaw: Float): Int {
        return if (-22.5 <= yaw && yaw < 22.5) 0  // south
        else if (22.5 <= yaw && yaw < 67.5) 1     // southwest
        else if (67.5 <= yaw && yaw < 112.5) 2    // west
        else if (112.5 <= yaw && yaw < 157.5) 3   // northwest
        else if (157.5 <= yaw || yaw < -157.5) 4  // north
        else if (-157.5 <= yaw && yaw < -112.5) 5 // northeast
        else if (-112.5 <= yaw && yaw < -67.5) 6  // east
        else if (-67.5 <= yaw && yaw < -22.5) 7   // southeast
        else 0
    }

    private fun getCardinalDirectionIcon(yaw: Float): Int {
        return if (-45.0 <= yaw && yaw < 45.0) 0  // south
        else if (45.0 <= yaw && yaw < 135.0) 1    // west
        else if (135.0 <= yaw || yaw < -135.0) 2  // north
        else if (-135.0 <= yaw && yaw < -45.0) 3  // east
        else 0
    }

    private fun getDirectionColor(icon: Int): Int {
        return when (icon) {
            0 -> ModConfig.directionColors.south.rgbNoAlpha
            1 -> ModConfig.directionColors.southwest.rgbNoAlpha
            2 -> ModConfig.directionColors.west.rgbNoAlpha
            3 -> ModConfig.directionColors.northwest.rgbNoAlpha
            4 -> ModConfig.directionColors.north.rgbNoAlpha
            5 -> ModConfig.directionColors.northeast.rgbNoAlpha
            6 -> ModConfig.directionColors.east.rgbNoAlpha
            7 -> ModConfig.directionColors.southeast.rgbNoAlpha
            else -> -1
        }
    }

    init {
        this.position = Position(this, 26f, 13f + 6f, getWidth(scale, true), getHeight(scale, true))
        this.position.anchor = Position.AnchorPosition.TOP_CENTER
    }
}