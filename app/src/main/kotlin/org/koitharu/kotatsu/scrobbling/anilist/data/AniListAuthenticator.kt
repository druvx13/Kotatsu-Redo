package org.koitharu.kotatsu.scrobbling.anilist.data

import org.koitharu.kotatsu.scrobbling.common.data.ScrobblerAuthenticator
import org.koitharu.kotatsu.scrobbling.common.data.ScrobblerStorage
import org.koitharu.kotatsu.scrobbling.common.domain.model.ScrobblerService
import org.koitharu.kotatsu.scrobbling.common.domain.model.ScrobblerType
import javax.inject.Inject
import javax.inject.Provider

class AniListAuthenticator @Inject constructor(
	@ScrobblerType(ScrobblerService.ANILIST) storage: ScrobblerStorage,
	repositoryProvider: Provider<AniListRepository>,
) : ScrobblerAuthenticator(storage, repositoryProvider)
