package fin.starhud.config.page

import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.core.OneColor

class InGameClockColors {
    @Color(name = "Daytime")
    var day = OneColor("#FFF9B5FF")

    @Color(name = "Nighttime")
    var night = OneColor("#D6CBEFFF")

    @Color(name = "Rain")
    var rain = OneColor("#B5D0E8FF")

    @Color(name = "Thunderstorm")
    var thunder = OneColor("#8FAECBFF")
}