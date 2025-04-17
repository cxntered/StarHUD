package fin.starhud.config.page

import cc.polyfrost.oneconfig.config.annotations.Button
import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.config.data.OptionSize

class PingColors {
    @Color(name = "Excellent")
    var excellent = OneColor("#85F290FF")

    @Color(name = "Fair")
    var fair = OneColor("#ECF285FF")

    @Color(name = "Poor")
    var poor = OneColor("#FEBC49FF")

    @Color(name = "Critical")
    var critical = OneColor("#FF5C71FF")

    @Button(name = "Reset Colors", text = "Reset", size = OptionSize.DUAL)
    var resetColors = Runnable {
        excellent = OneColor("#85F290FF")
        fair = OneColor("#ECF285FF")
        poor = OneColor("#FEBC49FF")
        critical = OneColor("#FF5C71FF")
    }
}