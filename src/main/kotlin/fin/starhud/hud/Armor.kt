package fin.starhud.hud

import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.hud.Position
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.libs.universal.wrappers.UPlayer
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import fin.starhud.util.ItemDurability

class Armor {
    class Helmet : Hud(true) {
        override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
            NanoVGHelper.INSTANCE.setupAndDraw(
                true
            ) { vg: Long ->
                ItemDurability.renderArmorHud(
                    vg,
                    UPlayer.getPlayer(),
                    0,
                    x,
                    y,
                    scale
                )
            }
        }

        override fun getWidth(scale: Float, example: Boolean): Float {
            return 63 * scale
        }

        override fun getHeight(scale: Float, example: Boolean): Float {
            return 13 * scale
        }

        override fun shouldShow(): Boolean {
            return super.shouldShow() && UPlayer.getPlayer()?.inventory?.armorItemInSlot(3) != null
        }

        init {
            this.position = Position(this, 5f, -20f, getWidth(scale, true), getHeight(scale, true))
            this.position.anchor = Position.AnchorPosition.MIDDLE_LEFT
        }
    }

    class Chestplate : Hud(true) {
        override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
            NanoVGHelper.INSTANCE.setupAndDraw(
                true
            ) { vg: Long ->
                ItemDurability.renderArmorHud(
                    vg,
                    UPlayer.getPlayer(),
                    1,
                    x,
                    y,
                    scale
                )
            }
        }

        override fun getWidth(scale: Float, example: Boolean): Float {
            return 63 * scale
        }

        override fun getHeight(scale: Float, example: Boolean): Float {
            return 13 * scale
        }

        override fun shouldShow(): Boolean {
            return super.shouldShow() && UPlayer.getPlayer()?.inventory?.armorItemInSlot(2) != null
        }

        init {
            this.position = Position(this, 5f, -20f + 14f, getWidth(scale, true), getHeight(scale, true))
            this.position.anchor = Position.AnchorPosition.MIDDLE_LEFT
        }
    }

    class Leggings : Hud(true) {
        override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
            NanoVGHelper.INSTANCE.setupAndDraw(
                true
            ) { vg: Long ->
                ItemDurability.renderArmorHud(
                    vg,
                    UPlayer.getPlayer(),
                    2,
                    x,
                    y,
                    scale
                )
            }
        }

        override fun getWidth(scale: Float, example: Boolean): Float {
            return 63 * scale
        }

        override fun getHeight(scale: Float, example: Boolean): Float {
            return 13 * scale
        }

        override fun shouldShow(): Boolean {
            return super.shouldShow() && UPlayer.getPlayer()?.inventory?.armorItemInSlot(1) != null
        }

        init {
            this.position = Position(this, 5f, -20f + (14f * 2), getWidth(scale, true), getHeight(scale, true))
            this.position.anchor = Position.AnchorPosition.MIDDLE_LEFT
        }
    }

    class Boots : Hud(true) {
        override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
            NanoVGHelper.INSTANCE.setupAndDraw(
                true
            ) { vg: Long ->
                ItemDurability.renderArmorHud(
                    vg,
                    UPlayer.getPlayer(),
                    3,
                    x,
                    y,
                    scale
                )
            }
        }

        override fun getWidth(scale: Float, example: Boolean): Float {
            return 63 * scale
        }

        override fun getHeight(scale: Float, example: Boolean): Float {
            return 13 * scale
        }

        override fun shouldShow(): Boolean {
            return super.shouldShow() && UPlayer.getPlayer()?.inventory?.armorItemInSlot(0) != null
        }

        init {
            this.position = Position(this, 5f, -20f + (14f * 3), getWidth(scale, true), getHeight(scale, true))
            this.position.anchor = Position.AnchorPosition.MIDDLE_LEFT
        }
    }
}