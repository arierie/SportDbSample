package id.arieridwan.sportdbsample.ui.lastmatch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.arieridwan.sportdbsample.data.repository.MatchRepository
import id.arieridwan.sportdbsample.data.response.EventListResponse
import id.arieridwan.sportdbsample.data.states.*
import id.arieridwan.sportdbsample.util.CoroutinesContextProvider
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import retrofit2.HttpException
import ru.gildor.coroutines.retrofit.await

/**
 * Created by arieridwan on 24/11/18.
 */

class LastMatchViewModel(private val matchRepository: MatchRepository,
                         private val contextProvider: CoroutinesContextProvider): ViewModel() {

    private val loadLastMatches = MutableLiveData<LoadDataState<EventListResponse>>()
    val eventLoadLastMatches get() = loadLastMatches

    fun loadLastMatch(leagueId: Int) {
        loadLastMatches.value = LoadDataState(LoadState.LOADING)
        launch(contextProvider.main) {
            val data = withContext(contextProvider.io) { matchRepository.loadLastMatch(leagueId) }
            try {
                loadLastMatches.value = LoadDataState(LoadState.SUCCESS, data.await())
            } catch (e: HttpException) {
                loadLastMatches.value = LoadDataState(LoadState.FAILED, null, e.message)
            } catch (e: Throwable) {
                loadLastMatches.value = LoadDataState(LoadState.FAILED, null, e.message)
            }
        }
    }

}