package in.freeb.sdk.interfaces;


/**
 * A {@code FreeBRegistrationListener} Callback methods defined in this interface to respond the user with desired result of registration.
 *   It is used to do something after a background task completes.
 *   End users will use a specific subclass of {@code FreeBRegistrationListener}.
 */

public interface FreeBRegistrationListener {

    /**
     * Called when registration is failed
     *
     * @param code-  registration failed error code
     * @param errorMessage-  error message for registration failure
     */
    public void onRegistrationFailed(String code, String errorMessage);

    /**
     * Called when registration is done successfully
     *
     * @param code-  successful registration code
     * @param errorMessage-  successful registered response
     */

    public void onRegistrationSuccess(String code, String errorMessage);

}
