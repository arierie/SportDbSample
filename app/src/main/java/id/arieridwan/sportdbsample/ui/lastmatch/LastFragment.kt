package id.arieridwan.sportdbsample.ui.lastmatch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.arieridwan.sportdbsample.data.repository.MatchRepository
import id.arieridwan.sportdbsample.data.service.ApiService
import id.arieridwan.sportdbsample.R

class LastFragment : Fragment() {

    private lateinit var lastMatchPresenter: LastMatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_last, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
