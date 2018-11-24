package id.arieridwan.sportdbsample.data.states

/**
 * Created by arieridwan on 24/11/18.
 */

data class LoadDataState<T>(val state: LoadState, val data: T? = null, val error: String? = null)

enum class LoadState {
    LOADING,
    SUCCESS,
    FAILED
}