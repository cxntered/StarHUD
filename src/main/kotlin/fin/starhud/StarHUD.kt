package fin.starhud

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.utils.commands.CommandManager
import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import fin.starhud.config.ModConfig
import fin.starhud.util.ServerPinger
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(
    modid = StarHUD.MODID,
    name = StarHUD.NAME,
    version = StarHUD.VERSION,
    clientSideOnly = true,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object StarHUD {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        ModConfig.initialize()
        CommandManager.INSTANCE.registerCommand(ModCommand())
        EventManager.INSTANCE.register(ServerPinger)
    }

    @Command(value = MODID, description = "Access the $NAME GUI.")
    class ModCommand {
        @Main
        fun handle() {
            ModConfig.openGui()
        }
    }
}
