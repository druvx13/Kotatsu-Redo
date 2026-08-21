package org.koitharu.kotatsu.list.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_ID
import org.koitharu.kotatsu.core.ui.list.decor.AbstractRoundedSelectionItemDecoration
import org.koitharu.kotatsu.core.util.ext.getItem
import org.koitharu.kotatsu.list.ui.model.MangaListModel

open class MangaSelectionDecoration(context: Context) : AbstractRoundedSelectionItemDecoration(context) {

	override fun getItemId(parent: RecyclerView, child: View): Long {
		val holder = parent.getChildViewHolder(child) ?: return NO_ID
		val item = holder.getItem(MangaListModel::class.java) ?: return NO_ID
		return item.id
	}

	override fun onDrawForeground(
		canvas: Canvas,
		parent: RecyclerView,
		child: View,
		bounds: RectF,
		state: RecyclerView.State,
	) {
		drawSelection(canvas, bounds, (child as? CardView)?.radius ?: defaultRadius)
	}
}
