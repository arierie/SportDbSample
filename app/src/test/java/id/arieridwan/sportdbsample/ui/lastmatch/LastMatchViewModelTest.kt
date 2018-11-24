package id.arieridwan.sportdbsample.ui.lastmatch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import id.arieridwan.sportdbsample.data.repository.MatchRepository
import id.arieridwan.sportdbsample.data.response.EventListResponse
import id.arieridwan.sportdbsample.data.response.EventResponse
import id.arieridwan.sportdbsample.data.states.LoadDataState
import id.arieridwan.sportdbsample.data.states.LoadState
import id.arieridwan.sportdbsample.util.TestContextProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import retrofit2.mock.Calls
import java.io.IOException
import java.util.*

/**
 * Created by arieridwan on 24/11/18.
 */

class LastMatchViewModelTest {

    private val mMatchRepository: MatchRepository = mock()
    private val mObserver: Observer<LoadDataState<EventListResponse>> = mock()
    private val mContextProvider = TestContextProvider()
    private lateinit var mViewModel: LastMatchViewModel

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mViewModel = LastMatchViewModel(mMatchRepository, mContextProvider)
    }

    @Test
    fun `test if load last match is success`() {
        val events = mutableListOf<EventResponse>()
        events.add(getGeneratedFakeEventData())
        val eventListResponse = EventListResponse(events)
        val idLeague = 12

        whenever(mMatchRepository.loadLastMatch(idLeague))
                .thenReturn(Calls.response(eventListResponse))

        mViewModel.eventLoadLastMatches.observeForever(mObserver)
        mViewModel.loadLastMatch(idLeague)

        verify(mMatchRepository).loadLastMatch(idLeague)

        val argumentCaptor = ArgumentCaptor.forClass(LoadDataState::class.java)

        val expectedLoadingState = LoadState.LOADING
        val expectedSuccessState = LoadState.SUCCESS

        argumentCaptor.run {
            verify(mObserver, times(2)).onChanged(capture() as LoadDataState<EventListResponse>?)
            val (loadingState, successState) = allValues
            Assert.assertEquals(loadingState.state, expectedLoadingState)
            Assert.assertEquals(successState.state, expectedSuccessState)
        }
    }

    @Test
    fun `test if load last match is failed`() {
        val idLeague = 12

        whenever(mMatchRepository.loadLastMatch(idLeague))
                .thenReturn(Calls.failure(IOException()))

        mViewModel.eventLoadLastMatches.observeForever(mObserver)
        mViewModel.loadLastMatch(idLeague)

        verify(mMatchRepository).loadLastMatch(idLeague)

        val argumentCaptor = ArgumentCaptor.forClass(LoadDataState::class.java)

        val expectedLoadingState = LoadState.LOADING
        val expectedFailedState = LoadState.FAILED

        argumentCaptor.run {
            verify(mObserver, times(2)).onChanged(capture() as LoadDataState<EventListResponse>?)
            val (loadingState, failedState) = allValues
            Assert.assertEquals(loadingState.state, expectedLoadingState)
            Assert.assertEquals(failedState.state, expectedFailedState)
        }
    }

    private fun getGeneratedFakeEventData(): EventResponse {
        return EventResponse(0, 0, "event", "fileName", "sport", 0, "league", "season", "description",
                "homeTeam", "awayTeam", 0, 0, 0, 0, "homeGoalDetails", "homeRedCards", "homeYellowCards", "homeLineUpGoalKeeper",
                "homeLineUpDeffense", "homeLineUpMidfield", "homeLineUpForward", "homeLineUpSubstitutes", "homeFormation",
                "awayRedCards", "awayYellowCards","homeGoalDetails" ,"awayLineUpGoalKeeper",
                "awayLineUpDeffense", "awayLineUpMidfield", "awayLineUpForward", "awayLineUpSubstitutes", "awayFormation",
                0, 0, Date(), "01-01-2018", "00.00", "tvStation", 0, 0, "result", "circuit", "country", "city", "poster",
                "fanArt", "thumb", "banner", "map", "locked")
    }

}