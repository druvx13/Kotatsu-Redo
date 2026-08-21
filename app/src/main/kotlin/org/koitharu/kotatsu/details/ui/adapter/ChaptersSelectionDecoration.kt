package org.koitharu.kotatsu.details.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import org.koitharu.kotatsu.R
import org.koitharu.kotatsu.core.ui.list.decor.AbstractRoundedSelectionItemDecoration
import org.koitharu.kotatsu.core.util.ext.getItem
import org.koitharu.kotatsu.core.util.ext.getThemeColor
import org.koitharu.kotatsu.details.ui.model.ChapterListItem
import androidx.appcompat.R as appcompatR

class ChaptersSelectionDecoration(context: Context) : AbstractRoundedSelectionItemDecoration(
	context = context,
	checkIconOffsetRes = R.dimen.chapter_check_offset,
	checkIconSizeRes = R.dimen.chapter_check_size,
) {

	private val radius = context.resources.getDimension(appcompatR.dimen.abc_control_corner_material)

	init {
		paint.color = ColorUtils.setAlphaComponent(
			context.getThemeColor(appcompatR.attr.colorPrimary, Color.DKGRAY),
			98,
		)
		paint.style = Paint.Style.FILL
		hasBackground = true
	}

	override fun getItemId(parent: RecyclerView, child: View): Long {
		val holder = parent.getChildViewHolder(child) ?: return RecyclerView.NO_ID
		val item = holder.getItem(ChapterListItem::class.java) ?: return RecyclerView.NO_ID
		return item.chapter.id
	}

	override fun onDrawBackground(
		canvas: Canvas,
		parent: RecyclerView,
		child: View,
		bounds: RectF,
		state: RecyclerView.State,
	) {
		if (child is CardView) {
			return
		}
		canvas.drawRoundRect(bounds, radius, radius, paint)
	}

	override fun onDrawForeground(
		canvas: Canvas,
		parent: RecyclerView,
		child: View,
		bounds: RectF,
		state: RecyclerView.State,
	) {
		if (child !is CardView) {
			return
		}
		drawSelection(canvas, bounds, child.radius)
		drawCheckIcon(canvas, bounds)
	}
}
