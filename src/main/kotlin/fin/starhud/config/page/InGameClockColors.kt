package fin.starhud.config.page

import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.OptionSize

class InGameClockColors {
    @Color(name = "Daytime")
    var day = OneColor("#FFF9B5FF")

    @Color(name = "Nighttime")
    var night = OneColor("#D6CBEFFF")

    @Color(name = "Rain")
    var rain = OneColor("#B5D0E8FF")

    @Color(name = "Thunderstorm")
    var thunder = OneColor("#8FAECBFF")

    @Button(name = "Reset Colors", text = "Reset", size = OptionSize.DUAL)
    var resetColors = Runnable {
        day = OneColor("#FFF9B5FF")
        night = OneColor("#D6CBEFFF")
        rain = OneColor("#B5D0E8FF")
        thunder = OneColor("#8FAECBFF")
    }
}