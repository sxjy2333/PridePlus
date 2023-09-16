/*
 * From FDPClient
 */
package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.Render3DEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.value.BoolValue
import net.ccbluex.liquidbounce.features.value.IntegerValue
import net.ccbluex.liquidbounce.features.value.ListValue
import op.wawa.utils.render.RenderWings

@ModuleInfo(name = "Wings", category = ModuleCategory.RENDER, description = "Render a Wing", array = false)
object Wings : Module() {
    private val onlyThirdPerson = BoolValue("OnlyThirdPerson", true)
    val ColourType = ListValue("Color-Type", arrayOf("Custom", "Chroma", "None"), "Chroma")
    val CR = IntegerValue("R", 255, 0, 255).displayable { ColourType.get() == "Custom" }
    val CG = IntegerValue("G", 255, 0, 255).displayable { ColourType.get() == "Custom" }
    val CB = IntegerValue("B", 255, 0, 255).displayable { ColourType.get() == "Custom" }
    var wingStyle = ListValue("WingStyle", arrayOf("Dragon", "Simple"),"Dragon")


    @EventTarget
    fun onRenderPlayer(event: Render3DEvent) {
        if (onlyThirdPerson.get() && mc2.gameSettings.thirdPersonView == 0) return
        val renderWings = RenderWings()
        renderWings.renderWings(event.partialTicks)
    }

}

