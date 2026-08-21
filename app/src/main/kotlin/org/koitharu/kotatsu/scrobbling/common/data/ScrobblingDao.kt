package org.koitharu.kotatsu.scrobbling.common.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.koitharu.kotatsu.core.util.ext.pagedFlow

@Dao
abstract class ScrobblingDao {

	@Query("SELECT * FROM scrobblings WHERE scrobbler = :scrobbler AND manga_id = :mangaId")
	abstract suspend fun find(scrobbler: Int, mangaId: Long): ScrobblingEntity?

	@Query("SELECT * FROM scrobblings WHERE scrobbler = :scrobbler AND manga_id = :mangaId")
	abstract fun observe(scrobbler: Int, mangaId: Long): Flow<ScrobblingEntity?>

	@Query("SELECT * FROM scrobblings WHERE scrobbler = :scrobbler")
	abstract fun observe(scrobbler: Int): Flow<List<ScrobblingEntity>>

	@Upsert
	abstract suspend fun upsert(entity: ScrobblingEntity)

	@Query("DELETE FROM scrobblings WHERE scrobbler = :scrobbler AND manga_id = :mangaId")
	abstract suspend fun delete(scrobbler: Int, mangaId: Long)

	@Query("SELECT * FROM scrobblings ORDER BY scrobbler LIMIT :limit OFFSET :offset")
	protected abstract suspend fun findAll(offset: Int, limit: Int): List<ScrobblingEntity>

	fun dumpEnabled(): Flow<ScrobblingEntity> = pagedFlow { offset, limit -> findAll(offset, limit) }
}
