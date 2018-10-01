package id.arieridwan.sportdbsample.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.arieridwan.sportdbsample.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by arieridwan on 01/10/18.
 */

class CommonUtils {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getStringDate(startDate: Date): String? {
            val writeFormat = SimpleDateFormat("EEE, dd MMM yyyy")
            var finalDate: String? = null
            try {
                finalDate = writeFormat.format(startDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return finalDate
        }
    }

}