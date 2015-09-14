package in.freeb.sdk.model_gson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Payload implements Parcelable {

    @Expose
    private List<FetchOffer> fetchOffers = new ArrayList<FetchOffer>();

    /**
     *
     * @return
     * The fetchOffers
     */
    public List<FetchOffer> getFetchOffers() {

        return fetchOffers;
    }

    /**
     *
     * @param fetchOffers
     * The fetchOffers
     */
    public void setFetchOffers(List<FetchOffer> fetchOffers) {
        this.fetchOffers = fetchOffers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public Payload(Parcel in) {
        try {
            fetchOffers = (List<FetchOffer>) in.readParcelable(Payload.class.getClassLoader());
        }catch(Exception e){

        }

    }

    public static final Creator<Payload> CREATOR = new Creator<Payload>() {

        @Override
        public Payload[] newArray(int size) {
            return new Payload[size];
        }

        @Override
        public Payload createFromParcel(Parcel source) {
            return new Payload(source);
        }
    };
}

