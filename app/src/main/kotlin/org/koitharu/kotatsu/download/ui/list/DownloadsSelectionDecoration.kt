package org.koitharu.kotatsu.download.ui.list

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_ID
import org.koitharu.kotatsu.R
import org.koitharu.kotatsu.core.ui.list.decor.AbstractRoundedSelectionItemDecoration
import org.koitharu.kotatsu.core.util.ext.getItem

class DownloadsSelectionDecoration(context: Context) : AbstractRoundedSelectionItemDecoration(
	context = context,
	checkIconOffsetRes = R.dimen.card_indicator_offset,
	checkIconSizeRes = R.dimen.card_indicator_size,
) {

	override fun getItemId(parent: RecyclerView, child: View): Long {
		val holder = parent.getChildViewHolder(child) ?: return NO_ID
		val item = holder.getItem(DownloadItemModel::class.java) ?: return NO_ID
		return item.id.mostSignificantBits
	}

	override fun onDrawForeground(
		canvas: Canvas,
		parent: RecyclerView,
		child: View,
		bounds: RectF,
		state: RecyclerView.State,
	) {
		drawSelection(canvas, bounds, (child as? CardView)?.radius ?: defaultRadius)
		if (child is CardView) {
			drawCheckIcon(canvas, bounds)
		}
	}
}
