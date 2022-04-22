package com.sampleproject.utils.span

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import com.sampleproject.R
import com.sampleproject.utils.span.base.ListItemSpan
import com.sampleproject.utils.ui.getColor
import com.sampleproject.utils.ui.getDimen

private const val LAST_BOTTOM_SPACING = -50
private const val PADDING_START = -100

class UnorderedListItemSpan(private val config: UnorderedListItemConfig) : ListItemSpan {
    private val indent = config.indent + config.radius + config.gap
    override var lastLineBottomSpacing = config.lastBottomSpacing
    override var lineBottomSpacing = config.bottomSpacing

    override fun getLeadingMargin(first: Boolean) = 2 * indent + config.paddingStart
    override fun drawLeadingMargin(
        canvas: Canvas,
        paint: Paint,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout?,
    ) {
        if (!first) return

        val style = paint.style
        val oldColor = paint.color

        paint.style = Paint.Style.FILL
        paint.color = config.color

        val ascend = paint.ascent()

        val yPosition = baseline + ascend / 2f + config.radius

        paint.color = config.color
        val addition = indent * 1
        val xPosition: Float = (x + addition + dir * config.radius + config.indent + config.paddingStart).toFloat()
        canvas.drawCircle(xPosition, yPosition, config.radius.toFloat(), paint)

        paint.color = oldColor
        paint.style = style
    }
}

fun getDotsSpanConfig(context: Context) = UnorderedListItemConfig(
    1.getDimen(context), //TODO использовать через Dimens
    1.getDimen(context),
    1.getDimen(context),
    R.color.main_black.getColor(context),
    0,
    LAST_BOTTOM_SPACING,
    PADDING_START
)
