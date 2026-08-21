package org.koitharu.kotatsu.favourites.ui.categories

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.koitharu.kotatsu.R
import org.koitharu.kotatsu.core.ui.list.decor.AbstractRoundedSelectionItemDecoration
import org.koitharu.kotatsu.core.util.ext.getItem
import org.koitharu.kotatsu.favourites.ui.categories.adapter.CategoryListModel

class CategoriesSelectionDecoration(context: Context) : AbstractRoundedSelectionItemDecoration(context) {

	private val padding = context.resources.getDimension(R.dimen.grid_spacing_outer)

	override fun getItemId(parent: RecyclerView, child: View): Long {
		val holder = parent.getChildViewHolder(child) ?: return RecyclerView.NO_ID
		val item = holder.getItem(CategoryListModel::class.java) ?: return RecyclerView.NO_ID
		return item.category.id
	}

	override fun onDrawForeground(
		canvas: Canvas,
		parent: RecyclerView,
		child: View,
		bounds: RectF,
		state: RecyclerView.State,
	) {
		bounds.inset(padding, padding)
		drawSelection(canvas, bounds)
	}
}
