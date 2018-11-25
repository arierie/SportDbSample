package id.arieridwan.sportdbsample.ui.today

import id.arieridwan.sportdbsample.data.repository.MatchRepository
import id.arieridwan.sportdbsample.util.CoroutinesContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.gildor.coroutines.retrofit.await

/**
 * Created by arieridwan on 30/09/18.
 */

class TodayPresenter(private val matchRepository: MatchRepository,
                     private val contextProvider: CoroutinesContextProvider) {

    private var todayView: TodayView? = null

    fun attachView(todayView: TodayView) {
        this.todayView = todayView
    }

    fun detachView() {
        todayView = null
    }

    fun loadTodayMatch(leagueId: Int, date: String) {
        todayView?.showLoading()
        GlobalScope.launch(contextProvider.main) {
            val data = withContext(contextProvider.io) { matchRepository.loadTodayMatch(leagueId, date) }
            try {
                todayView?.showResultList(data.await())
                todayView?.hideLoading()
            } catch (e: HttpException) {
                todayView?.showError()
                todayView?.hideLoading()
            } catch (e: Throwable) {
                todayView?.showError()
                todayView?.hideLoading()
            }
        }
    }
    
}