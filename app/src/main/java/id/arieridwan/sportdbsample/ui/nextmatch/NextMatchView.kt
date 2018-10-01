package id.arieridwan.sportdbsample.ui.nextmatch

import id.arieridwan.sportdbsample.data.response.EventListResponse

/**
 * Created by arieridwan on 30/09/18.
 */

interface NextMatchView {

    fun showLoading()
    fun hideLoading()
    fun showResultList(eventListResponse: EventListResponse)
    fun showError()

}