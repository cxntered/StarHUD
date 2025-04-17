/*
 * Copyright (c) 2023-2025 odtheking & contributors
 *
 * This file uses code from Odin, available at:
 * https://github.com/odtheking/Odin/blob/main/src/main/kotlin/me/odinmain/utils/ServerUtils.kt
 *
 * Licensed under the BSD 3-Clause License. The full license can be found here:
 * https://raw.githubusercontent.com/odtheking/Odin/refs/heads/main/LICENSE
 */

package fin.starhud.util

import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent
import cc.polyfrost.oneconfig.events.event.WorldLoadEvent
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.libs.universal.wrappers.UPlayer
import net.minecraft.network.play.client.C16PacketClientStatus
import net.minecraft.network.play.server.S01PacketJoinGame
import net.minecraft.network.play.server.S37PacketStatistics
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object ServerPinger {
    private val scheduler = Executors.newScheduledThreadPool(1)
    private var prevTime = 0L
    private var isPinging = false
    private var pingStartTime = 0L
    var averagePing = 0.0

    init {
        scheduler.scheduleAtFixedRate({
            sendPing()
        }, 0, 2000, TimeUnit.MILLISECONDS)
    }

    @Subscribe
    fun onWorldLoad(event: WorldLoadEvent) {
        reset()
    }

    @Subscribe
    fun onPacket(event: ReceivePacketEvent) {
        averagePing = when (event.packet) {
            is S37PacketStatistics -> (System.nanoTime() - pingStartTime) / 1e6
            is S01PacketJoinGame -> 0.0
            else -> return
        }
        isPinging = false
    }

    private fun sendPing() {
        if (isPinging || UPlayer.getPlayer() == null) return
        if (pingStartTime - System.nanoTime() > 10E6) reset()
        pingStartTime = System.nanoTime()
        isPinging = true
        UPlayer.getPlayer()!!.sendQueue.addToSendQueue(C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS))
    }

    private fun reset() {
        prevTime = 0L
        averagePing = 0.0
    }
}