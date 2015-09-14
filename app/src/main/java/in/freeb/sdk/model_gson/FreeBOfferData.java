package in.freeb.sdk.model_gson;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class FreeBOfferData implements Parcelable {

    @Expose
    private String status;
    @Expose
    private String errorCode;
    @Expose
    private String message;
    @Expose
    private String udf1;
    @Expose
    private String udf2;
    @Expose
    private String udf3;
    @Expose
    private String udf4;
    @Expose
    private String udf5;
    @Expose
    private Payload payload;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     *
     * @param errorCode
     * The errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The udf1
     */
    public String getUdf1() {
        return udf1;
    }

    /**
     *
     * @param udf1
     * The udf1
     */
    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    /**
     *
     * @return
     * The udf2
     */
    public String getUdf2() {
        return udf2;
    }

    /**
     *
     * @param udf2
     * The udf2
     */
    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }

    /**
     *
     * @return
     * The udf3
     */
    public String getUdf3() {
        return udf3;
    }

    /**
     *
     * @param udf3
     * The udf3
     */
    public void setUdf3(String udf3) {
        this.udf3 = udf3;
    }

    /**
     *
     * @return
     * The udf4
     */
    public String getUdf4() {
        return udf4;
    }

    /**
     *
     * @param udf4
     * The udf4
     */
    public void setUdf4(String udf4) {
        this.udf4 = udf4;
    }

    /**
     *
     * @return
     * The udf5
     */
    public String getUdf5() {
        return udf5;
    }

    /**
     *
     * @param udf5
     * The udf5
     */
    public void setUdf5(String udf5) {
        this.udf5 = udf5;
    }

    /**
     *
     * @return
     * The payload
     */
    public Payload getPayload() {
        return payload;
    }

    /**
     *
     * @param payload
     * The payload
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(status);
        dest.writeString(errorCode);
        dest.writeString(message);

        dest.writeString(udf1);
        dest.writeString(udf2);
        dest.writeString(udf3);
        dest.writeString(udf4);
        dest.writeString(udf5);

        dest.writeParcelable(payload, flags);

    }

    public FreeBOfferData(Parcel in) {

        status = in.readString();
        errorCode = in.readString();
        message = in.readString();

        udf1 = in.readString();
        udf2 = in.readString();
        udf3 = in.readString();
        udf4 = in.readString();
        udf5 = in.readString();

        payload = in.readParcelable(FreeBOfferData.class.getClassLoader());
    }

    public static final Creator<FreeBOfferData> CREATOR = new Creator<FreeBOfferData>() {

        @Override
        public FreeBOfferData[] newArray(int size) {
            return new FreeBOfferData[size];
        }

        @Override
        public FreeBOfferData createFromParcel(Parcel source) {
            return new FreeBOfferData(source);
        }
    };
}