package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.aac

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.JumpEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed
import net.ccbluex.liquidbounce.features.value.BoolValue
import net.ccbluex.liquidbounce.features.value.FloatValue
import net.minecraft.client.Minecraft

class AACPushVelocity : VelocityMode("AACPush") {
    val mc: Minecraft = Minecraft.getMinecraft()

    private val aacPushXZReducerValue = FloatValue("AACPushXZReducer", 2F, 1F, 3F)
    private val aacPushYReducerValue = BoolValue("AACPushYReducer", true)
    private var jump = false
    override fun onEnable() {
        jump = false
    }

    override fun onVelocity(event: UpdateEvent) {
        if (jump) {
            if (mc.player.onGround) {
                jump = false
            }
        } else {
            // Strafe
            if (mc.player.hurtTime > 0 && mc.player.motionX != 0.0 && mc.player.motionZ != 0.0) {
                mc.player.onGround = true
            }

            // Reduce Y
            if (mc.player.hurtResistantTime > 0 && aacPushYReducerValue.get() &&
                !LiquidBounce.moduleManager[Speed::class.java].state) {
                mc.player.motionY -= 0.014999993
            }
        }

        // Reduce XZ
        if (mc.player.hurtResistantTime >= 19) {
            val reduce = aacPushXZReducerValue.get()

            mc.player.motionX /= reduce
            mc.player.motionZ /= reduce
        }
    }

    override fun onJump(event: JumpEvent) {
        if (mc.player.isInWater || mc.player.isInLava || mc.player.isInWeb || (velocity.onlyGroundValue.get() && !mc.player.onGround)) {
            return
        }

        if ((velocity.onlyGroundValue.get() && !mc.player.onGround) || (velocity.onlyCombatValue.get() && !LiquidBounce.combatManager.inCombat)) {
            return
        }

        jump = true

        if (!mc.player.collidedVertically) {
            event.cancelEvent()
        }
    }
}