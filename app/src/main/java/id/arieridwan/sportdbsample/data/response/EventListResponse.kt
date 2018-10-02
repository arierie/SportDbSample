package id.arieridwan.sportdbsample.data.response

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by arieridwan on 26/07/18.
 */

data class EventListResponse(val events: List<EventResponse>?): Parcelable {

    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(EventResponse))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(events)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventListResponse> {
        override fun createFromParcel(parcel: Parcel): EventListResponse {
            return EventListResponse(parcel)
        }

        override fun newArray(size: Int): Array<EventListResponse?> {
            return arrayOfNulls(size)
        }
    }

}