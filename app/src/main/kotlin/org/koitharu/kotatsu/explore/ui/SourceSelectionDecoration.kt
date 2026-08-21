package org.koitharu.kotatsu.explore.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_ID
import org.koitharu.kotatsu.core.ui.list.decor.AbstractRoundedSelectionItemDecoration
import org.koitharu.kotatsu.core.util.ext.getItem
import org.koitharu.kotatsu.explore.ui.model.MangaSourceItem

class SourceSelectionDecoration(context: Context) : AbstractRoundedSelectionItemDecoration(context) {

	override fun getItemId(parent: RecyclerView, child: View): Long {
		val holder = parent.getChildViewHolder(child) ?: return NO_ID
		val item = holder.getItem(MangaSourceItem::class.java) ?: return NO_ID
		return item.id
	}

	override fun onDrawForeground(
		canvas: Canvas,
		parent: RecyclerView,
		child: View,
		bounds: RectF,
		state: RecyclerView.State,
	) {
		drawSelection(canvas, bounds)
	}
}
