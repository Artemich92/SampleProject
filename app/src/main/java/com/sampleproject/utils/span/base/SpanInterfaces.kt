package com.sampleproject.utils.span.base

import android.graphics.Paint
import android.text.Spanned
import android.text.style.LeadingMarginSpan
import android.text.style.LineHeightSpan
import android.text.style.UpdateLayout

interface Span
interface ParagraphSpan : Span
interface ListItemSpan : ParagraphWithIndentSpan
interface ParagraphWithIndentSpan : LeadingMarginSpan, ParagraphSpan, BottomSpacingSpan

interface BottomSpacingSpan : LineHeightSpan, UpdateLayout {

    var lastLineBottomSpacing: Int
    var lineBottomSpacing: Int

    override fun chooseHeight(
        text: CharSequence,
        start: Int,
        end: Int,
        spanstartv: Int,
        lineHeight: Int,
        fm: Paint.FontMetricsInt?,
    ) {
        if (fm == null) return

        when {
            text.length == end -> {
                fm.bottom = lineBottomSpacing
                fm.descent = lineBottomSpacing
            }
            selfEnd(end, text, this) -> {
                fm.bottom = lastLineBottomSpacing
                fm.descent = lastLineBottomSpacing
            }
            else -> {
                fm.bottom = lineBottomSpacing
                fm.descent = lineBottomSpacing
            }
        }
    }

    private fun selfEnd(end: Int, text: CharSequence, span: Any?): Boolean {
        val spanEnd = (text as Spanned).getSpanEnd(span)
        return spanEnd == end || spanEnd == end - 1
    }
}
