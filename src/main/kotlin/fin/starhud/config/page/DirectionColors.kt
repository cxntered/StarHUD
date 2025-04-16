package fin.starhud.config.page

import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.OptionSize

class DirectionColors {
    @Color(name = "South")
    var south = OneColor("#FFB5B5FF")

    @Color(name = "Southwest")
    var southwest = OneColor("#FFCBB3FF")

    @Color(name = "West")
    var west = OneColor("#FFD1B7FF")

    @Color(name = "Northwest")
    var northwest = OneColor("#D8CAE8FF")

    @Color(name = "North")
    var north = OneColor("#B7C9E9FF")

    @Color(name = "Northeast")
    var northeast = OneColor("#D4DBF0FF")

    @Color(name = "East")
    var east = OneColor("#FFE5B4FF")

    @Color(name = "Southeast")
    var southeast = OneColor("#FFD0C4FF")

    @Button(name = "Reset Colors", text = "Reset", size = OptionSize.DUAL)
    var resetColors = Runnable {
        south = OneColor("#FFB5B5FF")
        southwest = OneColor("#FFCBB3FF")
        west = OneColor("#FFD1B7FF")
        northwest = OneColor("#D8CAE8FF")
        north = OneColor("#B7C9E9FF")
        northeast = OneColor("#D4DBF0FF")
        east = OneColor("#FFE5B4FF")
        southeast = OneColor("#FFD0C4FF")
    }
}