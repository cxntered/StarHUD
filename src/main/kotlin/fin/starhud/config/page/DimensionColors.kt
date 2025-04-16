package fin.starhud.config.page

import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.OptionSize

class DimensionColors {
    @Color(name = "Overworld")
    var overworld = OneColor("#FFFFFFFF")

    @Color(name = "Nether")
    var nether = OneColor("#FC7871FF")

    @Color(name = "The End")
    var end = OneColor("#C9C7E3FF")

    @Color(name = "Unknown")
    var unknown = OneColor("#FFFFFFFF")

    @Button(name = "Reset Colors", text = "Reset", size = OptionSize.DUAL)
    var resetColors = Runnable {
        overworld = OneColor("#FFFFFFFF")
        nether = OneColor("#FC7871FF")
        end = OneColor("#C9C7E3FF")
        unknown = OneColor("#FFFFFFFF")
    }
}