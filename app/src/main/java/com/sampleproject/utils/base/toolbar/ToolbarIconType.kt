package com.sampleproject.utils.base.toolbar

import androidx.annotation.DrawableRes
import com.sampleproject.R

enum class ToolbarIconType(@DrawableRes val drawable: Int) {
    ICON_BACK(R.drawable.ic_back),
    ICON_ALERT_NON(R.drawable.ic_alert_none),
    ICON_ALERT_YES(R.drawable.ic_alert_yes),
    ICON_HEART(R.drawable.ic_like),
    ICON_SHARE(R.drawable.ic_share)
}
