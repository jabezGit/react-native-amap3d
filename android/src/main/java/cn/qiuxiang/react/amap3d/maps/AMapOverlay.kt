package cn.qiuxiang.react.amap3d.maps

import android.view.View
import com.amap.api.maps.AMap

interface AMapOverlay {
    fun add(map: AMap)
    fun remove()
    fun update(child: View)
}