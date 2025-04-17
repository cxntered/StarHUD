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
import fin.starhud.hud.*

object ModConfig : Config(Mod(StarHUD.NAME, ModType.HUD, "/${StarHUD.NAME}.png"), "${StarHUD.MODID}.json") {
    @HUD(name = "Helmet", category = "Armor")
    var helmet = Armor.Helmet()

    @HUD(name = "Chestplate", category = "Armor")
    var chestplate = Armor.Chestplate()

    @HUD(name = "Leggings", category = "Armor")
    var leggings = Armor.Leggings()

    @HUD(name = "Boots", category = "Armor")
    var boots = Armor.Boots()

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

    @HUD(name = "X Coordinate", category = "Coordinates")
    var xCoordinate = Coordinates.XCoordinate()

    @HUD(name = "Y Coordinate", category = "Coordinates")
    var yCoordinate = Coordinates.YCoordinate()

    @HUD(name = "Z Coordinate", category = "Coordinates")
    var zCoordinate = Coordinates.ZCoordinate()

    @HUD(name = "Direction", category = "Direction")
    var direction = Direction()

    @Page(name = "Direction Colors", location = PageLocation.BOTTOM, category = "Direction")
    var directionColors = DirectionColors()

    @HUD(name = "FPS", category = "FPS")
    var fps = FPS()
}
