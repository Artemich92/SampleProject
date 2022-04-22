package com.realcosmetology.android.utils.base.toolbar

import androidx.annotation.DrawableRes
import com.realcosmetology.android.R

enum class ToolbarIconType(@DrawableRes val drawable: Int) {
    ICON_BACK(R.drawable.ic_back),
    ICON_ALERT_NON(R.drawable.ic_alert_none),
    ICON_ALERT_YES(R.drawable.ic_alert_yes),
    ICON_HEART(R.drawable.ic_like),
    ICON_IMAGE(R.drawable.ic_image),
    ICON_DELETE(R.drawable.ic_delete),
    ICON_SHARE(R.drawable.ic_share),
    ICON_TEXT_REAL_COSMETOLOGY(R.drawable.ic_text_rcc_for_toolbar),
    ICON_DISMISS(R.drawable.ic_dismiss)
}
