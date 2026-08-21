package org.koitharu.kotatsu.scrobbling.kitsu.data

import org.koitharu.kotatsu.scrobbling.common.data.ScrobblerAuthenticator
import org.koitharu.kotatsu.scrobbling.common.data.ScrobblerStorage
import org.koitharu.kotatsu.scrobbling.common.domain.model.ScrobblerService
import org.koitharu.kotatsu.scrobbling.common.domain.model.ScrobblerType
import javax.inject.Inject
import javax.inject.Provider

class KitsuAuthenticator @Inject constructor(
	@ScrobblerType(ScrobblerService.KITSU) storage: ScrobblerStorage,
	repositoryProvider: Provider<KitsuRepository>,
) : ScrobblerAuthenticator(storage, repositoryProvider)
