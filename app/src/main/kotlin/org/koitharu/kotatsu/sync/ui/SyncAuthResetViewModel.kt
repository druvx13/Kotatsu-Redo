package org.koitharu.kotatsu.sync.ui

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import org.koitharu.kotatsu.R
import org.koitharu.kotatsu.core.ui.BaseViewModel
import org.koitharu.kotatsu.core.util.ext.MutableEventFlow
import org.koitharu.kotatsu.core.util.ext.call
import org.koitharu.kotatsu.sync.data.SyncAuthApi
import org.koitharu.kotatsu.sync.data.SyncSettings
import javax.inject.Inject

@HiltViewModel
class SyncAuthResetViewModel @Inject constructor(
	@ApplicationContext private val context: Context,
	private val api: SyncAuthApi,
	private val syncSettings: SyncSettings,
) : BaseViewModel() {

	val onPasswordResetSucceeded = MutableEventFlow<Unit>()
	val onUntrustedHost = MutableEventFlow<Unit>()
	val syncURL = MutableStateFlow(context.resources.getStringArray(R.array.sync_url_list).first())
	val resetToken = MutableStateFlow<String?>(null)

	/**
	 * A reset link may come from an untrusted source (browser, another app), so the server it points to
	 * must be one of the known hosts, otherwise the new password would be sent to an arbitrary server.
	 */
	fun setSyncUrlFromLink(url: String) {
		launchJob(Dispatchers.Default) {
			if (isKnownHost(url)) {
				syncURL.value = url
			} else {
				onUntrustedHost.call(Unit)
			}
		}
	}

	private fun isKnownHost(url: String): Boolean {
		val normalized = url.normalizeUrl()
		return context.resources.getStringArray(R.array.sync_url_list)
			.any { it.normalizeUrl() == normalized } || syncSettings.syncUrl.normalizeUrl() == normalized
	}

	private fun String.normalizeUrl() = trim().trimEnd('/').lowercase()

	fun resetPassword(password: String) {
		val urlValue = syncURL.value
		val resetTokenValue = resetToken.value ?: return // Token should never be null because the fragment exits if none is provided

		launchLoadingJob(Dispatchers.Default) {
			api.resetPassword(urlValue, resetTokenValue, password)
			onPasswordResetSucceeded.call(Unit)
		}
	}
}
