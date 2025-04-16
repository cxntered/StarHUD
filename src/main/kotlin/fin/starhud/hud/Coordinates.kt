package fin.starhud.hud

import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.hud.Position
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.libs.universal.wrappers.UPlayer
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import cc.polyfrost.oneconfig.renderer.TextRenderer
import cc.polyfrost.oneconfig.renderer.asset.Image
import cc.polyfrost.oneconfig.renderer.scissor.ScissorHelper
import fin.starhud.util.NVGFlags

class Coordinates {
    class XCoordinate : Hud(true) {
        @Color(name = "Color")
        var color = OneColor("#FC7871FF")

        @Button(name = "Reset Color", text = "Reset")
        var resetColor = Runnable {
            color = OneColor("#FC7871FF")
        }

        override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
            NanoVGHelper.INSTANCE.setupAndDraw(
                true
            ) { vg: Long ->
                val scissor = ScissorHelper.INSTANCE.scissor(
                    vg,
                    x,
                    y,
                    65 * scale,
                    13 * scale
                )
                NanoVGHelper.INSTANCE.drawImage(
                    vg,
                    Image("/assets/starhud/hud/coordinate.png", NVGFlags.NVG_IMAGE_NEAREST),
                    x,
                    y,
                    65 * scale,
                    41 * scale,
                    color.rgbNoAlpha
                )
                ScissorHelper.INSTANCE.resetScissor(vg, scissor)
            }

            TextRenderer.drawScaledString(
                UPlayer.getPosX().toInt().toString(),
                x + (19 * scale),
                y + (3 * scale),
                color.rgbNoAlpha,
                TextRenderer.TextType.toType(0),
                scale
            )
        }

        override fun getWidth(scale: Float, example: Boolean): Float {
            return 65 * scale
        }

        override fun getHeight(scale: Float, example: Boolean): Float {
            return 13 * scale
        }

        init {
            this.position = Position(this, 5f, 5f, getWidth(scale, true), getHeight(scale, true))
            this.position.anchor = Position.AnchorPosition.TOP_LEFT
        }
    }

    class YCoordinate : Hud(true) {
        @Color(name = "Color")
        var color = OneColor("#A6F1AFFF")

        @Button(name = "Reset Color", text = "Reset")
        var resetColor = Runnable {
            color = OneColor("#A6F1AFFF")
        }

        override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
            NanoVGHelper.INSTANCE.setupAndDraw(
                true
            ) { vg: Long ->
                val scissor = ScissorHelper.INSTANCE.scissor(
                    vg,
                    x,
                    y,
                    65 * scale,
                    13 * scale
                )
                NanoVGHelper.INSTANCE.drawImage(
                    vg,
                    Image("/assets/starhud/hud/coordinate.png", NVGFlags.NVG_IMAGE_NEAREST),
                    x,
                    y - (14 * scale),
                    65 * scale,
                    41 * scale,
                    color.rgbNoAlpha
                )
                ScissorHelper.INSTANCE.resetScissor(vg, scissor)
            }

            TextRenderer.drawScaledString(
                UPlayer.getPosY().toInt().toString(),
                x + (19 * scale),
                y + (3 * scale),
                color.rgbNoAlpha,
                TextRenderer.TextType.toType(0),
                scale
            )
        }

        override fun getWidth(scale: Float, example: Boolean): Float {
            return 65 * scale
        }

        override fun getHeight(scale: Float, example: Boolean): Float {
            return 13 * scale
        }

        init {
            this.position = Position(this, 5f, 5f + 14f, getWidth(scale, true), getHeight(scale, true))
            this.position.anchor = Position.AnchorPosition.TOP_LEFT
        }
    }

    class ZCoordinate : Hud(true) {
        @Color(name = "Color")
        var color = OneColor("#6CE1FCFF")

        @Button(name = "Reset Color", text = "Reset")
        var resetColor = Runnable {
            color = OneColor("#6CE1FCFF")
        }

        override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
            NanoVGHelper.INSTANCE.setupAndDraw(
                true
            ) { vg: Long ->
                val scissor = ScissorHelper.INSTANCE.scissor(
                    vg,
                    x,
                    y,
                    65 * scale,
                    13 * scale
                )
                NanoVGHelper.INSTANCE.drawImage(
                    vg,
                    Image("/assets/starhud/hud/coordinate.png", NVGFlags.NVG_IMAGE_NEAREST),
                    x,
                    y - (14 * 2 * scale),
                    65 * scale,
                    41 * scale,
                    color.rgbNoAlpha
                )
                ScissorHelper.INSTANCE.resetScissor(vg, scissor)
            }

            TextRenderer.drawScaledString(
                UPlayer.getPosZ().toInt().toString(),
                x + (19 * scale),
                y + (3 * scale),
                color.rgbNoAlpha,
                TextRenderer.TextType.toType(0),
                scale
            )
        }

        override fun getWidth(scale: Float, example: Boolean): Float {
            return 65 * scale
        }

        override fun getHeight(scale: Float, example: Boolean): Float {
            return 13 * scale
        }

        init {
            this.position = Position(this, 5f, 5f + 14f + 14f, getWidth(scale, true), getHeight(scale, true))
            this.position.anchor = Position.AnchorPosition.TOP_LEFT
        }
    }
}