package org.koitharu.kotatsu.core.parser

import android.content.Context
import androidx.annotation.AnyThread
import androidx.collection.ArrayMap
import dagger.hilt.android.qualifiers.ApplicationContext
import org.koitharu.kotatsu.core.cache.MemoryContentCache
import org.koitharu.kotatsu.core.model.LocalMangaSource
import org.koitharu.kotatsu.core.model.MangaSourceInfo
import org.koitharu.kotatsu.core.model.TestMangaSource
import org.koitharu.kotatsu.core.model.UnknownMangaSource
import org.koitharu.kotatsu.core.parser.external.ExternalMangaRepository
import org.koitharu.kotatsu.core.parser.external.ExternalMangaSource
import org.koitharu.kotatsu.local.data.LocalMangaRepository
import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.model.Manga
import org.koitharu.kotatsu.parsers.model.MangaChapter
import org.koitharu.kotatsu.parsers.model.MangaListFilter
import org.koitharu.kotatsu.parsers.model.MangaListFilterCapabilities
import org.koitharu.kotatsu.parsers.model.MangaListFilterOptions
import org.koitharu.kotatsu.parsers.model.MangaPage
import org.koitharu.kotatsu.parsers.model.MangaParserSource
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.model.SortOrder
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interface defining the contract for accessing manga data.
 * Implementations handle different data sources (local, parser, external).
 */
interface MangaRepository {

	/**
	 * The source associated with this repository.
	 */
	val source: MangaSource

	/**
	 * Supported sort orders for this source.
	 */
	val sortOrders: Set<SortOrder>

	/**
	 * The default sort order.
	 */
	var defaultSortOrder: SortOrder

	/**
	 * Capabilities of the filter for this source.
	 */
	val filterCapabilities: MangaListFilterCapabilities

	/**
	 * Fetches a list of manga based on the provided parameters.
	 *
	 * @param offset The offset for pagination.
	 * @param order The sort order.
	 * @param filter Optional filter criteria.
	 * @return A list of manga.
	 */
	suspend fun getList(offset: Int, order: SortOrder?, filter: MangaListFilter?): List<Manga>

	/**
	 * Fetches details for a specific manga.
	 *
	 * @param manga The manga to fetch details for.
	 * @return The updated manga object with details.
	 */
	suspend fun getDetails(manga: Manga): Manga

	/**
	 * Fetches the pages for a specific chapter.
	 *
	 * @param chapter The chapter to fetch pages for.
	 * @return A list of pages.
	 */
	suspend fun getPages(chapter: MangaChapter): List<MangaPage>

	/**
	 * Resolves the URL for a specific page.
	 *
	 * @param page The page to resolve the URL for.
	 * @return The URL of the page image.
	 */
	suspend fun getPageUrl(page: MangaPage): String

	/**
	 * Fetches available filter options for the source.
	 *
	 * @return The filter options.
	 */
	suspend fun getFilterOptions(): MangaListFilterOptions

	/**
	 * Fetches related manga for the given seed manga.
	 *
	 * @param seed The manga to find related items for.
	 * @return A list of related manga.
	 */
	suspend fun getRelated(seed: Manga): List<Manga>

	/**
	 * Attempts to find a specific manga in the repository.
	 * Default implementation searches by title.
	 *
	 * @param manga The manga to find.
	 * @return The found manga, or null if not found.
	 */
	suspend fun find(manga: Manga): Manga? {
		val list = getList(0, SortOrder.RELEVANCE, MangaListFilter(query = manga.title))
		return list.find { x -> x.id == manga.id }
	}

	@Singleton
	class Factory @Inject constructor(
		@ApplicationContext private val context: Context,
		private val localMangaRepository: LocalMangaRepository,
		private val loaderContext: MangaLoaderContext,
		private val contentCache: MemoryContentCache,
		private val mirrorSwitcher: MirrorSwitcher,
	) {

		private val cache = ArrayMap<MangaSource, WeakReference<MangaRepository>>()

		@AnyThread
		fun create(source: MangaSource): MangaRepository {
			when (source) {
				is MangaSourceInfo -> return create(source.mangaSource)
				LocalMangaSource -> return localMangaRepository
				UnknownMangaSource -> return EmptyMangaRepository(source)
			}
			cache[source]?.get()?.let { return it }
			return synchronized(cache) {
				cache[source]?.get()?.let { return it }
				val repository = createRepository(source)
				if (repository != null) {
					cache[source] = WeakReference(repository)
					repository
				} else {
					EmptyMangaRepository(source)
				}
			}
		}

		private fun createRepository(source: MangaSource): MangaRepository? = when (source) {
			is MangaParserSource -> ParserMangaRepository(
				parser = loaderContext.newParserInstance(source),
				cache = contentCache,
				mirrorSwitcher = mirrorSwitcher,
			)

			TestMangaSource -> TestMangaRepository(
				loaderContext = loaderContext,
				cache = contentCache,
			)

			is ExternalMangaSource -> if (source.isAvailable(context)) {
				ExternalMangaRepository(
					contentResolver = context.contentResolver,
					source = source,
					cache = contentCache,
				)
			} else {
				EmptyMangaRepository(source)
			}

			else -> null
		}
	}
}
