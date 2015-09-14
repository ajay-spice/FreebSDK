package in.freeb.sdk.interfaces;


/**
 * A {@code FreeBOffersListener}  Callback methods defined in this interface to respond the user in accordance to the desired result sent by the server for fetching Offers.
 *   End users will use a specific subclass of {@code FreeBOffersListener}.
 */

public interface FreeBOffersListener {

    /**
     * Called when something went wrong at server and could not fetch offers from FreeB SDK server
     *
     * @param code failure code
     * @param errorMessage failure response.
     */
    public void onOffersFailed(String code, String errorMessage);

    /**
     * Called when your app pull complete offers from FreeB SDK server
     *
     * @param code   offers loaded code
     * @param errorMessage response when offers available to user
     */

    public void onOffersLoaded(String code, String errorMessage);

    /**
     * Called when an offer is installed successfully by user and server return success.
     *
     * @param code     success code
     * @param response success response
     */

    public void onOffersInstallSuccess(String code, String response);

    /**
     * Called When an offer failed to install through FreeB SDK and server return fail.
     *
     * @param code    failure code
     * @param response failure response
     */
    public void onOffersInstallFailure(String code, String response);

    /**
     * Called when offers are shown in your application.
     */
    public void onShowOffers();

    /**
     * Called when user show interest but have not consumed offer.
     *
     * @param code         offer install code
     * @param errorMessage offer install response
     */
    public void noOfferInstalled(String code, String errorMessage);

    /**
     * Called when if the user exit the offer screen or on back pressed.
     *
     * @param code     offer install code
     * @param response offer install response
     */
    public void onLeaveApplication(String code, String response);

    /**
     * Called on dismissing offers progress dialog.
     *
     * @param errorMessage error message on progress dialog dismiss
     */

    public void onDialogDismiss(String errorMessage);
}
