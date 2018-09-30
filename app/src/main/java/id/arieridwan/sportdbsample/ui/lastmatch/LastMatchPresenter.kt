package id.arieridwan.sportdbsample.ui.lastmatch

import id.arieridwan.sportdbsample.data.repository.MatchRepository
import id.arieridwan.sportdbsample.util.CoroutinesContextProvider
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import retrofit2.HttpException
import ru.gildor.coroutines.retrofit.await

/**
 * Created by arieridwan on 30/09/18.
 */

class LastMatchPresenter(private val matchRepository: MatchRepository,
                         private val contextProvider: CoroutinesContextProvider) {

    private var lastMatchView: LastMatchView? = null

    fun attachView(lastMatchView: LastMatchView) {
        this.lastMatchView = lastMatchView
    }

    fun detachView() {
        lastMatchView = null
    }

    fun loadLastMatch(leagueId: Int) {
        lastMatchView?.showLoading()
        launch(contextProvider.main) {
            val data = withContext(contextProvider.io) { matchRepository.loadLastMatch(leagueId) }
            try {
                lastMatchView?.showResultList(data.await())
                lastMatchView?.hideLoading()
            } catch (e: HttpException) {
                lastMatchView?.showError()
                lastMatchView?.hideLoading()
            } catch (e: Throwable) {
                lastMatchView?.showError()
                lastMatchView?.hideLoading()
            }
        }
    }

}