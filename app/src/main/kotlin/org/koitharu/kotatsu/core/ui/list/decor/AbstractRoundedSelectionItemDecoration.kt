package org.koitharu.kotatsu.core.ui.list.decor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import org.koitharu.kotatsu.R
import org.koitharu.kotatsu.core.util.ext.getThemeColor
import androidx.appcompat.R as appcompatR
import com.google.android.material.R as materialR

/**
 * A selection decoration which highlights checked items with a rounded filled and stroked rectangle
 * and, optionally, with a check icon in the top right corner.
 */
abstract class AbstractRoundedSelectionItemDecoration(
	context: Context,
	@DimenRes checkIconOffsetRes: Int = ResourcesCompat.ID_NULL,
	@DimenRes checkIconSizeRes: Int = ResourcesCompat.ID_NULL,
) : AbstractSelectionItemDecoration() {

	protected val paint = Paint(Paint.ANTI_ALIAS_FLAG)
	protected val strokeColor = context.getThemeColor(appcompatR.attr.colorPrimary, Color.RED)
	protected val fillColor = ColorUtils.setAlphaComponent(
		ColorUtils.blendARGB(strokeColor, context.getThemeColor(materialR.attr.colorSurface), 0.8f),
		0x74,
	)
	protected val defaultRadius = context.resources.getDimension(R.dimen.list_selector_corner)

	private val checkIcon = if (checkIconSizeRes == ResourcesCompat.ID_NULL) {
		null
	} else {
		ContextCompat.getDrawable(context, materialR.drawable.ic_mtrl_checked_circle)
	}
	private val checkIconOffset = if (checkIconOffsetRes == ResourcesCompat.ID_NULL) {
		0
	} else {
		context.resources.getDimensionPixelOffset(checkIconOffsetRes)
	}
	private val checkIconSize = if (checkIconSizeRes == ResourcesCompat.ID_NULL) {
		0
	} else {
		context.resources.getDimensionPixelOffset(checkIconSizeRes)
	}

	init {
		hasBackground = false
		hasForeground = true
		isIncludeDecorAndMargins = false

		paint.strokeWidth = context.resources.getDimension(R.dimen.selection_stroke_width)
		checkIcon?.setTint(strokeColor)
	}

	protected fun drawSelection(canvas: Canvas, bounds: RectF, radius: Float = defaultRadius) {
		paint.color = fillColor
		paint.style = Paint.Style.FILL
		canvas.drawRoundRect(bounds, radius, radius, paint)
		paint.color = strokeColor
		paint.style = Paint.Style.STROKE
		canvas.drawRoundRect(bounds, radius, radius, paint)
	}

	protected fun drawCheckIcon(canvas: Canvas, bounds: RectF) {
		checkIcon?.run {
			setBounds(
				(bounds.right - checkIconSize - checkIconOffset).toInt(),
				(bounds.top + checkIconOffset).toInt(),
				(bounds.right - checkIconOffset).toInt(),
				(bounds.top + checkIconOffset + checkIconSize).toInt(),
			)
			draw(canvas)
		}
	}
}
