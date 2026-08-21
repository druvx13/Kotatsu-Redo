package org.koitharu.kotatsu.scrobbling.mal.data

import org.koitharu.kotatsu.scrobbling.common.data.ScrobblerAuthenticator
import org.koitharu.kotatsu.scrobbling.common.data.ScrobblerStorage
import org.koitharu.kotatsu.scrobbling.common.domain.model.ScrobblerService
import org.koitharu.kotatsu.scrobbling.common.domain.model.ScrobblerType
import javax.inject.Inject
import javax.inject.Provider

class MALAuthenticator @Inject constructor(
	@ScrobblerType(ScrobblerService.MAL) storage: ScrobblerStorage,
	repositoryProvider: Provider<MALRepository>,
) : ScrobblerAuthenticator(storage, repositoryProvider)
