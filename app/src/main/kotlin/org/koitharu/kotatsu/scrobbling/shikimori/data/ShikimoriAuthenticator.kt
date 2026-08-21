package org.koitharu.kotatsu.scrobbling.shikimori.data

import org.koitharu.kotatsu.scrobbling.common.data.ScrobblerAuthenticator
import org.koitharu.kotatsu.scrobbling.common.data.ScrobblerStorage
import org.koitharu.kotatsu.scrobbling.common.domain.model.ScrobblerService
import org.koitharu.kotatsu.scrobbling.common.domain.model.ScrobblerType
import javax.inject.Inject
import javax.inject.Provider

class ShikimoriAuthenticator @Inject constructor(
	@ScrobblerType(ScrobblerService.SHIKIMORI) storage: ScrobblerStorage,
	repositoryProvider: Provider<ShikimoriRepository>,
) : ScrobblerAuthenticator(storage, repositoryProvider)
