package cn.qiuxiang.react.amap3d.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import cn.qiuxiang.react.amap3d.toPx
import com.amap.api.maps.AMap
import com.amap.api.maps.model.*
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.views.view.ReactViewGroup

/**
 * Created by jin on 2018/1/16.
 */

class AMapAircraft(context: Context) : ReactViewGroup(context), AMapOverlay {

    private var coordinates: ArrayList<LatLng> = ArrayList()

    private var icon: View? = null
    private var bitmapDescriptor: BitmapDescriptor? = null
    private var anchorU: Float = 0.5f
    private var anchorV: Float = 1f

    var icon_me = false
    /***
     * gsmId
     */
    var gsmId = ""

    var polyline: Polyline? = null
        private set

    var pathWidth: Float = 3f
        set(value) {
            field = value
            polyline?.width = value
        }

    /***
     * green color
     */
    var pathColor: Int = -16729328
        set(value) {
            field = value
            polyline?.color = value
        }

    var geodesic: Boolean = false
        set(value) {
            field = value
            polyline?.isGeodesic = value
        }

    var dashed: Boolean = false
        set(value) {
            field = value
            polyline?.isDottedLine = value
        }

    var gradient: Boolean = false

    fun setPathLog(coordinates: ReadableArray) {
        if(coordinates.size()<=2) return
        this.coordinates = ArrayList((0 until coordinates.size())
                .map { coordinates.getMap(it) }
                .map { LatLng(it.getDouble("latitude"), it.getDouble("longitude")) })

        polyline?.points = this.coordinates
    }



    /***************************
     * Marker
     */

    var marker: Marker? = null
        private set

    var position: LatLng = LatLng(0.0,0.0)
        set(value) {
            field = value
            marker?.position = value
            this.coordinates.add(value)
            polyline?.points=this.coordinates;
        }

    var zIndex: Float = 0.0f
        set(value) {
            field = value
            marker?.zIndex = value
        }

    var title = ""
        set(value) {
            field = value
            marker?.title = value
        }

    var snippet = ""
        set(value) {
            field = value
            marker?.snippet = value
        }

    var flat: Boolean = false
        set(value) {
            field = value
            marker?.isFlat = value
        }

    var opacity: Float = 1f
        set(value) {
            field = value
            marker?.alpha = value
        }

    var draggable: Boolean = false
        set(value) {
            field = value
            marker?.isDraggable = value
        }

    var clickDisabled: Boolean = false
        set(value) {
            field = value
            marker?.isClickable = !value
        }

    /***
     * 添加图标的旋转角度
     */
    var rotateAngle: Float = 0.0f
        set(value){
            field = -value
            marker?.rotateAngle = value
        }

    override fun addView(child: View, index: Int) {
        super.addView(child, index)
        icon = child
        icon?.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->updateIcon() }
    }

    override fun add(map: AMap) {

        marker = map.addMarker(MarkerOptions()
                .setFlat(flat)
                .icon(bitmapDescriptor)
                .alpha(opacity)
                .draggable(draggable)
                .position(position)
                .anchor(anchorU, anchorV)
                .title(title)
                .snippet(snippet)
                .zIndex(zIndex)
                .infoWindowEnable(false)
                .rotateAngle(rotateAngle))


        /***
         * set center point
         */
        marker?.setAnchor(0.5f,0.5f);

        polyline = map.addPolyline(PolylineOptions()
                .addAll(coordinates)
                .color(pathColor)
                .width(pathWidth)
                .useGradient(gradient)
                .geodesic(geodesic)
                .setDottedLine(dashed)
                .zIndex(10f))
    }


    override fun update(child : View) {
        if(child is AMapAircraft){
            child.position=position
        }
    }

    override fun remove() {
        marker?.destroy()
        polyline?.remove()
        coordinates?.clear()
    }

    fun setIconColor(icon: String) {
        // TODO
    }

    fun updateIcon() {
        icon?.let {
            val bitmap = Bitmap.createBitmap(
                    it.width, it.height, Bitmap.Config.ARGB_8888)
            it.draw(Canvas(bitmap))
            bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)
            marker?.setIcon(bitmapDescriptor)
        }
    }

    fun setImage(name: String) {
        val drawable = context.resources.getIdentifier(name, "drawable", context.packageName)
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(drawable)
        marker?.setIcon(bitmapDescriptor)
    }

    fun setAnchor(x: Double, y: Double) {
        anchorU = x.toFloat()
        anchorV = y.toFloat()
        marker?.setAnchor(anchorU, anchorV)
    }

    fun lockToScreen(args: ReadableArray?) {
        if (args != null) {
            val x = args.getDouble(0).toFloat().toPx
            val y = args.getDouble(1).toFloat().toPx
            marker?.setPositionByPixels(x, y)
        }
    }
}
