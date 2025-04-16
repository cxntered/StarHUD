package fin.starhud.hud

import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.OptionSize
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
import net.minecraft.world.World
import java.text.SimpleDateFormat
import java.util.*

class Clock {
    class InGame : Hud(true) {
        @Switch(name = "Use 12 Hour Format", size = OptionSize.DUAL)
        var use12HourFormat = false

        override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
            val world = UMinecraft.getWorld() ?: return

            val time = world.worldTime % 24000
            val hours = ((time / 1000) + 6).toInt() % 24
            val minutes = ((time % 1000) * 3 / 50).toInt()
            val text = if (use12HourFormat) {
                getCivilianTime(hours, minutes)
            } else {
                getMilitaryTime(hours, minutes)
            }

            val icon = getWorldStatus(world)
            val color = getIconColor(icon)

            NanoVGHelper.INSTANCE.setupAndDraw(
                true
            ) { vg: Long ->
                val scissor = ScissorHelper.INSTANCE.scissor(
                    vg,
                    x,
                    y,
                    (if (use12HourFormat) 65 else 49) * scale,
                    13 * scale
                )
                NanoVGHelper.INSTANCE.drawImage(
                    vg,
                    Image(
                        "/assets/starhud/hud/clock_${if (use12HourFormat) "12" else "24"}.png",
                        NVGFlags.NVG_IMAGE_NEAREST
                    ),
                    x,
                    y - (icon * 13 * scale),
                    (if (use12HourFormat) 65 else 49) * scale,
                    65 * scale,
                    color
                )
                ScissorHelper.INSTANCE.resetScissor(vg, scissor)
            }

            TextRenderer.drawScaledString(
                text,
                x + (19 * scale),
                y + (3 * scale),
                color,
                TextRenderer.TextType.toType(0),
                scale
            )
        }

        override fun getWidth(scale: Float, example: Boolean): Float {
            return (if (use12HourFormat) 65 else 49) * scale
        }

        override fun getHeight(scale: Float, example: Boolean): Float {
            return 13 * scale
        }

        private fun getIconColor(icon: Int): Int {
            return when (icon) {
                1 -> ModConfig.inGameClockColors.day.rgbNoAlpha
                2 -> ModConfig.inGameClockColors.night.rgbNoAlpha
                3 -> ModConfig.inGameClockColors.rain.rgbNoAlpha
                4 -> ModConfig.inGameClockColors.thunder.rgbNoAlpha
                else -> -1
            }
        }

        private fun getWorldStatus(world: World): Int {
            return if (world.isThundering) 4
            else if (world.isRaining) 3
            else if (!world.isDaytime) 2
            else 1
        }

        private fun getMilitaryTime(hours: Int, minutes: Int): String {
            val stringBuilder = StringBuilder()

            if (hours < 10) stringBuilder.append('0')
            stringBuilder.append(hours).append(':')

            if (minutes < 10) stringBuilder.append('0')
            stringBuilder.append(minutes)

            return stringBuilder.toString()
        }

        private fun getCivilianTime(hours: Int, minutes: Int): String {
            var adjustedHours = hours
            val stringBuilder = StringBuilder()

            val period = if (adjustedHours >= 12) " PM" else " AM"

            // 01.00 until 12.59 AM / PM
            adjustedHours %= 12
            if (adjustedHours == 0) adjustedHours = 12

            stringBuilder.append(getMilitaryTime(adjustedHours, minutes)).append(period)

            return stringBuilder.toString()
        }

        init {
            this.position = Position(this, -29f, 13f + 6f, getWidth(scale, true), getHeight(scale, true))
            this.position.anchor = Position.AnchorPosition.TOP_CENTER
        }
    }

    class System : Hud(true) {
        @Switch(name = "Use 12 Hour Format", size = OptionSize.DUAL)
        var use12HourFormat = false

        @Color(name = "Color")
        var color = OneColor("#FFFFFFFF")

        @Button(name = "Reset Color", text = "Reset")
        var resetColor = Runnable {
            color = OneColor("#FFFFFFFF")
        }

        override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
            val currentTime: Long = java.lang.System.currentTimeMillis()
            val text = if (use12HourFormat) {
                SimpleDateFormat("hh:mm a").format(Date(currentTime))
            } else {
                SimpleDateFormat("HH:mm").format(Date(currentTime))
            }

            NanoVGHelper.INSTANCE.setupAndDraw(
                true
            ) { vg: Long ->
                val scissor = ScissorHelper.INSTANCE.scissor(
                    vg,
                    x,
                    y,
                    (if (use12HourFormat) 65 else 49) * scale,
                    13 * scale
                )
                NanoVGHelper.INSTANCE.drawImage(
                    vg,
                    Image(
                        "/assets/starhud/hud/clock_${if (use12HourFormat) "12" else "24"}.png",
                        NVGFlags.NVG_IMAGE_NEAREST
                    ),
                    x,
                    y,
                    (if (use12HourFormat) 65 else 49) * scale,
                    65 * scale,
                    color.rgbNoAlpha
                )
                ScissorHelper.INSTANCE.resetScissor(vg, scissor)
            }

            TextRenderer.drawScaledString(
                text,
                x + (19 * scale),
                y + (3 * scale),
                color.rgbNoAlpha,
                TextRenderer.TextType.toType(0),
                scale
            )
        }

        override fun getWidth(scale: Float, example: Boolean): Float {
            return (if (use12HourFormat) 65 else 49) * scale
        }

        override fun getHeight(scale: Float, example: Boolean): Float {
            return 13 * scale
        }

        init {
            this.position = Position(this, -5f, -5f, getWidth(scale, true), getHeight(scale, true))
            this.position.anchor = Position.AnchorPosition.BOTTOM_RIGHT
        }
    }
}