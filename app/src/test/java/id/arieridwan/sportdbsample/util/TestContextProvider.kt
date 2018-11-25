package id.arieridwan.sportdbsample.util

import kotlinx.coroutines.Dispatchers.Unconfined
import kotlin.coroutines.CoroutineContext

/**
 * Created by arieridwan on 24/11/18.
 */

class TestContextProvider : CoroutinesContextProvider() {
    override val main: CoroutineContext = Unconfined
    override val io: CoroutineContext = Unconfined
}