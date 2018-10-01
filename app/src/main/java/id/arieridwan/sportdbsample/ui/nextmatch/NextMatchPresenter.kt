package id.arieridwan.sportdbsample.ui.nextmatch

import id.arieridwan.sportdbsample.data.repository.MatchRepository
import id.arieridwan.sportdbsample.util.CoroutinesContextProvider
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import retrofit2.HttpException
import ru.gildor.coroutines.retrofit.await

/**
 * Created by arieridwan on 30/09/18.
 */

class NextMatchPresenter(private val matchRepository: MatchRepository,
                         private val contextProvider: CoroutinesContextProvider) {

    private var nextMatchView: NextMatchView? = null

    fun attachView(nextMatchView: NextMatchView) {
        this.nextMatchView = nextMatchView
    }

    fun detachView() {
        nextMatchView = null
    }

    fun loadNextMatch(leagueId: Int) {
        nextMatchView?.showLoading()
        launch(contextProvider.main) {
            val data = withContext(contextProvider.io) { matchRepository.loadNextMatch(leagueId) }
            try {
                nextMatchView?.showResultList(data.await())
                nextMatchView?.hideLoading()
            } catch (e: HttpException) {
                nextMatchView?.showError()
                nextMatchView?.hideLoading()
            } catch (e: Throwable) {
                nextMatchView?.showError()
                nextMatchView?.hideLoading()
            }
        }
    }

}