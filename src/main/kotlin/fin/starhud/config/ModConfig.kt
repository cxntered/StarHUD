package fin.starhud.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import fin.starhud.StarHUD

object ModConfig : Config(Mod(StarHUD.NAME, ModType.HUD, "/${StarHUD.NAME}.png"), "${StarHUD.MODID}.json") {}
