package fin.starhud.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.annotations.Page
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.config.data.PageLocation
import fin.starhud.StarHUD
import fin.starhud.config.page.DimensionColors
import fin.starhud.config.page.DirectionColors
import fin.starhud.config.page.InGameClockColors
import fin.starhud.hud.Biome
import fin.starhud.hud.Clock
import fin.starhud.hud.Direction
import fin.starhud.hud.FPS

object ModConfig : Config(Mod(StarHUD.NAME, ModType.HUD, "/${StarHUD.NAME}.png"), "${StarHUD.MODID}.json") {
    @HUD(name = "Biome", category = "Biome")
    var biome = Biome()

    @Page(name = "Dimension Colors", location = PageLocation.BOTTOM, category = "Biome")
    var dimensionColors = DimensionColors()

    @HUD(name = "Clock", category = "Clock", subcategory = "In-Game")
    var inGameClock = Clock.InGame()

    @Page(name = "In-Game Clock Colors", location = PageLocation.BOTTOM, category = "Clock", subcategory = "In-Game")
    var inGameClockColors = InGameClockColors()

    @HUD(name = "Clock", category = "Clock", subcategory = "System")
    var realTimeClock = Clock.System()

    @HUD(name = "Direction", category = "Direction")
    var direction = Direction()

    @Page(name = "Direction Colors", location = PageLocation.BOTTOM, category = "Direction")
    var directionColors = DirectionColors()

    @HUD(name = "FPS", category = "FPS")
    var fps = FPS()
}
