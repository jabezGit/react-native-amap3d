package cn.qiuxiang.react.amap3d.maps

import android.view.View
import com.amap.api.maps.model.LatLng
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp

/**
 * Created by jin on 2018/1/16.
 */

@Suppress("unused")
internal class AMapAircraftManager : ViewGroupManager<AMapAircraft>() {
    override fun getName(): String {
        return "AMapAircraft"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): AMapAircraft {
        return AMapAircraft(reactContext)
    }

    override fun addView(aircraft: AMapAircraft, view: View, index: Int) {
           super.addView(aircraft, view, index)
    }

    override fun getExportedCustomDirectEventTypeConstants(): Map<String, Any>? {
        return MapBuilder.of(
                "onPress", MapBuilder.of("registrationName", "onPress"),
                "onDragStart", MapBuilder.of("registrationName", "onDragStart"),
                "onDrag", MapBuilder.of("registrationName", "onDrag"),
                "onDragEnd", MapBuilder.of("registrationName", "onDragEnd"),
                "onInfoWindowPress", MapBuilder.of("registrationName", "onInfoWindowPress")
        )
    }

    companion object {
        val UPDATE = 1
        val ACTIVE = 2
        val LOCK_TO_SCREEN = 3
    }

    override fun getCommandsMap(): Map<String, Int> {
        return mapOf(
                "update" to UPDATE,
                "active" to ACTIVE,
                "lockToScreen" to LOCK_TO_SCREEN
        )
    }

    override fun receiveCommand(aircraft: AMapAircraft, commandId: Int, args: ReadableArray?) {
        when (commandId) {
            UPDATE -> aircraft.updateIcon()
            LOCK_TO_SCREEN -> aircraft.lockToScreen(args)
        }
    }

    /***
     * GSM_ID
     */
    @ReactProp(name = "gsmId")
    fun setGsmId(aircraft: AMapAircraft, gsmId: String) {
        aircraft.gsmId = gsmId
    }

    @ReactProp(name = "pathLog")
    fun setCoordinate(aircraft: AMapAircraft, coordinates: ReadableArray) {
        aircraft.setPathLog(coordinates)
    }


    @ReactProp(name = "title")
    fun setTitle(aircraft: AMapAircraft, title: String) {
        aircraft.title = title
    }

    @ReactProp(name = "description")
    fun setSnippet(aircraft: AMapAircraft, description: String) {
        aircraft.snippet = description
    }

    @ReactProp(name = "coordinate")
    fun setCoordinate(aircraft: AMapAircraft, coordinate: ReadableMap) {
        aircraft.position = LatLng(
                coordinate.getDouble("latitude"),
                coordinate.getDouble("longitude"))
    }

    @ReactProp(name = "flat")
    fun setFlat(aircraft: AMapAircraft, flat: Boolean) {
        aircraft.flat = flat
    }

    @ReactProp(name = "opacity")
    override fun setOpacity(aircraft: AMapAircraft, opacity: Float) {
        aircraft.opacity = opacity
    }

    @ReactProp(name = "draggable")
    fun setDraggable(aircraft: AMapAircraft, draggable: Boolean) {
        aircraft.draggable = draggable
    }

    @ReactProp(name = "clickDisabled")
    fun setClickDisabled(aircraft: AMapAircraft, disabled: Boolean) {
        aircraft.clickDisabled = disabled
    }



    @ReactProp(name = "rotateAngle")
    fun setRotateAngle(aircraft: AMapAircraft, rotateAngle: Float) {
        aircraft.rotateAngle = rotateAngle
    }


    @ReactProp(name = "pathColor",customType = "Color")
    fun setIcon(aircraft: AMapAircraft, Color: Int) {
        aircraft.pathColor= Color
    }

    @ReactProp(name = "pathWidth")
    fun setPathWidth(aircraft: AMapAircraft, pathWidth: Float) {
        aircraft.pathWidth= pathWidth
    }

    @ReactProp(name = "image")
    fun setImage(aircraft: AMapAircraft, image: String) {
        aircraft.setImage(image)
    }

    @ReactProp(name = "zIndex")
    fun setZInex(aircraft: AMapAircraft, zIndex: Float) {
        aircraft.zIndex = zIndex
    }

    @ReactProp(name = "anchor")
    fun setAnchor(aircraft: AMapAircraft, coordinate: ReadableMap) {
        aircraft.setAnchor(
                coordinate.getDouble("x"),
                coordinate.getDouble("y"))
    }
}
