package id.arieridwan.sportdbsample.util

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by arieridwan on 30/09/18.
 */

open class CoroutinesContextProvider {
    open val main: CoroutineContext by lazy { UI }
    open val io: CoroutineContext by lazy { CommonPool }
}