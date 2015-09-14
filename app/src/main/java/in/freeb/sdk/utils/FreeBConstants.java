package in.freeb.sdk.utils;

public class FreeBConstants {

    public static final String API_KEY = "AIzaSyA4bOo1hwAYYA2UvYLR4rundLKS8OYKCz8";
    // Webservice urls
    public static final String CORE_URL = "https://www.spay.in/FreeBapp/";
    public static final String ID = "TRACKING_ID";
    public static final String ADTIME = "FIRSTADTIME";
    public static final String SHOWONEXIT = "ISSHOWONEXIT";
    public static final String WRONG = "Something went wrong. Please try again.";
    public static final String REGISTER = "Unable to register this account";
    public static final String DENSITY = "deviceDensity";
    public static final String CONN_TYPE = "connectionType";
    public static final String TITLE = "OFFER_NAME";
    public static final String OFFERS = "OFFERS";
    public static final String TEXTCOLOR = "textColor";
    public static final String LAYOUTCOLOR = "layoutColor";
    public static final String ITEMID = "itemId";
    public static final String STAGING = "appStatusUserActivity";

    public static final String CORE_REPORT_URL = "https://www.spay.in/FreeBSDK/";
    // History URls

    public static final String DEALS_TRANX_HISTORY_SUFFIX = "dealsTranxHistoryPage?";
    public static final String OFFERS_TRANX_HISTORY_SUFFIX = "offersTranxHistoryPage?";
    public static final String RECHARGE_TRANX_HISTORY_SUFFIX = "rechargeTranxHistoryPage?";
    public static final String WALLET_TRANX_HISTORY_SUFFIX = "fetchTranxHistoryPage?";
    public static final String COIN_TRANX_HISTORY_SUFFIX = "coinsTranxHistoryPage?";

    public static final String TARIFF_PLANS_SUFFIX = "tariffPlans?";
    public static final String GET_BALANCEURL_SUFFIX = "getBalance?";
    public static final String INSTALL_REPORTINGURL_SUFFIX = CORE_URL
            + "installReporting?";
    public static final String FEEDBACK = "feedback?";
    public static final String FEEDBACK_TITLE = "Feedback";
    public static final String UNINSTALL_REPORTINGURL_SUFFIX = "uninstallReporting?";
    public static final String VIDEO_REPORTINGURL_SUFFIX = "videoReporting?";
    public static final String INSTALLED_APPSREPORTING_SUFFIX = "installedAppsReporting?";
    public static final String VIEW_PROFILEURL_SUFFIX = "viewProfile?";
    public static final String UPDATE_PROFILEURL_SUFFIX = "updateProfile?";
    public static final String API_UPDATE_GCM = CORE_URL + "updateGCM";
    public static final String API_FEEDBACK = CORE_URL + "logFeedback";
    public static final String API_APP_REPORTING = CORE_URL + "appReporting";
    public static final String API_VIEW_PROFILE = CORE_URL + "viewProfile";
    public static final String RETAILER_ID = "retailerId";
    public static final String FETCH_URL_SUFFIX = "fetchOffers?";
    public static final String APP_REPORTING = "appReportingAction?";

    public static final String FEEDBACK_CATEGORY_SUFFIX = "fetchFeedbackCategory";
    public static final String OFFER_DESCRIPTION_SUFFIX = "individualOffers";
    public static final String HEART_BEAT = "userTrackAction?";
    public static final String INSTALLED_OFFERS = "logInstalledOffers?";

    public static final String ONE_TIME_SEND_OFFERS = "";
    public static final String ONE_TIME_RECEIVE_OFFERS = "";

    public static final String AGGREGATOR_NAME = "aggregatorName";
    public static final String AFFILIATE_ID = "affiliateId";
    public static final String AFFILIATE_APP_ID = "affiliateAppId";
    public static final String UNIQUE_ID = "uniqueId";
    public static final String TRACKING_ID = "trackingDeviceId";
    public static final String APPINFO = "appInfo";
    public static final String IS_MOBILE_APP = "isMobileApp";
    public static final String HANDSET_MAKE = "deviceManufacturer";
    public static final String HANDSET_MODEL = "deviceModel";
    public static final String MOBILE_APP_VERSION = "ClientAppVersion";
    public static final String MOBILE_SDK_VERSION = "sdkVersion";

    public static final String PACKAGE_NAME_UDF1 = "udf6";
    public static final String DEVICE_ID = "deviceId";
    public static final String EMAIL_ID = "emailId";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String MCC = "mcc";
    public static final String MNC = "mnc";
    public static final String CELLID = "cellId";
    public static final String LAC = "lac";
    public static final String TOKEN_ID = "tokenId";
    public static final String MOBILE_OS = "deviceOs";
    public static final String GCM_ID = "gcmId";
    public static final String UDF1 = "udf1";
    public static final String UDF2 = "udf2";
    public static final String UDF3 = "udf3";
    public static final String UDF4 = "udf4";
    public static final String UDF5 = "udf5";
    public static final String IMEI = "imei";
    public static final String PHONE_NO = "mobileNumber";
    public static final String FEEDBACK_MESSAGE = "feedbackMessage";
    public static final boolean IS_MOBILE_APP_VAL = true;
    public static final String APP_NAME = "appName";
    public static final String VERSION_CODE = "version";
    public static final String PACKAGE_NAME_SDK = "packageName";
    public static final String PACKAGE_NAME = "ClientPackageName";
    public static final String REFERRALID = "referenceId";
    public static final String CELL_ID = "celld";
    public static final String ACCURACY = "accuracy";
    public static final String REFERENCE_ID = "referenceId";
    public static final String VERSION = "osVersion";
    public static final String APP_ID = "appId";
    public static final String APP_CATEGORY = "appCategory";
    public static final String APP_URL = "appUrl";
    public static final String APP_STATUS = "appStatus";
    public static final String APP_STATUS_INTEREST = "INTEREST";
    public static final String APP_STATUS_INSTALL = "INSTALL";
    public static final String APP_STATUS_UNINSTALL = "UNINSTALL";
    public static final String APP_STATUS_RUN = "RUN";
    public static final String APP_STATUS_PUPDATE = "PUPDATE";
    public static final String APP_STATUS_SHARE_FACEBOOK = "FSHARE";
    public static final String APP_STATUS_SHARE_GOOGLE = "GSHARE";
    public static final String APP_STATUS_SHARE_FACEBOOK_INVITE = "FBSHARE";

    // Result codes
    public static final String RESULT_CODE_FETCH_URL = "10001";
    public static final String RESULT_CODE_WALLET_BALANCE = "10002";
    public static final String RESULT_CODE_REGISTRATION_SERVICE = "10005";
    public static final String RESULT_CODE_APP_REPORTING = "10003";
    public static final String RESULT_CODE_INSTALLED_APPS = "10004";
    public static final String RESULT_CODE_UPLOAD_CONTACTS = "10006";
    public static final String RESULT_CODE_GCM = "10007";
    public static final String RESULT_CODE_APP_UNINSTALL_REPORTING = "10008";
    public static final String RESULT_CODE_FEEDBACK = "10009";
    public static final String RESULT_CODE_OFFERDESCRIPTION = "100010";

    // Intent keys
    public static final String OFFER_KEY = "offer_key";

    // Prefrences
    // one time server hit prefrence
    public static final String PREF_IS_SERVER_HIT = "is_server_hit";
    public static final String ONE_TIME_SERVER_HIT_PREFRENCES = "ONE_TIME_SERVER_HIT_PREFRENCES";
    public static final String PREF_IS_INSTALL_REPORTING_HIT = "is_install_reporting_hit";

    // Network change receiver prefrence
    public static final String PREF_IS_NETWORK_CHANGE = "is_network_change";
    public static final String ONE_TIME_NETWORK_CHANGE_PREFRENCES = "ONE_TIME_NETWORK_CHANGE_PREFRENCES";

    // Prefrences

    public static final String PREF_USER_PHONENUMBER = "PREF_USER_PHONENUMBER";

    // Offers Constants
    public static final String APP_OFFER = "APP";
    public static final String YOUTUBE_OFFER = "YOUTUBE";
    public static final String BANNER_OFFER = "BANNER";
    public static final String BUTTONS_OFFER = "button_offers";
    public static final String MORE_OFFER = "more_offers";
    public static final String EXTRAS_BUTTONS_OFFER = "extra_button_offers";
    public static final String API_UPDATE_PROFILE = CORE_URL + "updateProfile";
    public static final String NAME = "firstName";
    public static final String RESEND_FLAG = "resendFlag";
    public static final String OTP = "otp";

    // Time out value
    public static int TIME_OUT = 120000;
    public static int RETRY = 2;
    public static final String FETCH_DEALURL_SUFFIX = "fetchDeals?";
    public static final String PROCESS_DEALURL_SUFFIX = "buyDeal?";
    public static final String PROCESS_DEALURL_SUFFIX_DIGITAL_CONTENT = "buyContent?";
    public static final String FETCH_OFFERURL_SUFFIX = "fetchOffers?";
    public static final String GET_DEAL_WEBTAG = "2#$2!52";
    public static final String PROCESS_DEAL_WEBTAG = "22!$%53";
    public static final String PROCESS_DEAL_WEBTAG_DIGITAL_CONTENT = "22!$%66";
    public static final String GET_OFFER_WEBTAG = "89$$%#";
    public static final String PROCESS_OFFER_WEBTAG = "90$%@88";
    public static final String ENTER_PHONE_NUMBER = "Please enter your 10 digit mobile number.";
    public static final String PLEASE_ENTER_VALID_PHONE = "Please enter a valid phone number";
    // public static final String PHONE_TAG = "numberPhone";
    public static final String ERROR_MESSAGE = "Something went wrong, please try again!";
    // Prepaid
    public static String PREF_PREPAID_LAST_DATA_AVAILABLE = "PREF_PREPAID_LAST_DATA_AVAILABLE";
    public static String PREF_PREPAID_LASTAMOUNT = "PREF_PREPAID_LASTAMOUNT";
    public static String PREF_PREPAID_LAST_MOBILE_NO = "PREF_PREPAID_LAST_MOBILE_NO";
    public static String PREF_PREPAID_LAST_NETWORK_PROVIDER = "PREF_PREPAID_LAST_NETWORK_PROVIDER";
    public static String PREF_PREPAID_LAST_NETWORK_PROVIDER_PLAN = "PREF_PREPAID_LAST_NETWORK_PROVIDER_PLAN";
    public static String RECHARGEURL_SUFFIX = "prepaidRechargeNew?";
    // Postpaid
    public static String PREF_POSTPAID_LAST_DATA_AVAILABLE = "PREF_POSTPAID_LAST_DATA_AVAILABLE";
    public static String PREF_POSTPAID_LASTAMOUNT = "PREF_POSTPAID_LASTAMOUNT";
    public static String PREF_POSTPAID_LAST_MOBILE_NO = "PREF_POSTPAID_LAST_MOBILE_NO";
    public static String PREF_POSTPAID_LAST_NETWORK_PROVIDER = "PREF_POSTPAID_LAST_NETWORK_PROVIDER";
    public static String PREF_POSTPAID_LAST_NETWORK_PROVIDER_PLAN = "PREF_POSTPAID_LAST_NETWORK_PROVIDER_PLAN";
    public static String PREF_POSTPAID_LAST_ACCOUNT_NUMBER = "PREF_POSTPAID_LAST_NETWORK_ACCOUNT_NUMBER";
    public static String POSTPAIDURL_SUFFIX = "billPaymentNew?";
    public static String PAYMENT_MODE = "payMentMode";
    // DTH
    public static String PREF_DTH_LAST_DATA_AVAILABLE = "PREF_DTH_LAST_DATA_AVAILABLE";
    public static String PREF_DTH_LASTAMOUNT = "PREF_DTH_LASTAMOUNT";
    public static String PREF_DTH_LAST_NETWORK_PROVIDER = "PREF_DTH_LAST_NETWORK_PROVIDER";
    public static String PREF_DTH_CUST_ID = "PREF_CUSTOMER_ID";
    public static String DTHURL_SUFFIX = "dthRechargeNew?";
    // Data Card
    public static String PREF_DATACARD_LAST_DATA_AVAILABLE = "PREF_DATACARD_LAST_DATA_AVAILABLE";
    public static String PREF_DATACARD_LAST_NETWORK_PROVIDER = "PREF_DATACARD_LAST_NETWORK_PROVIDER";
    public static String PREF_DATACARD_LAST_MOBILE_NO = "PREF_DATACARD_LAST_MOBILE_NO";
    public static String PREF_DATACARD_LASTAMOUNT = "PREF_DATACARD_LASTAMOUNT";
    public static String DATACURL_SUFFIX = "dataCardRechargeNew?";
    // History urls
    public static final String HISTORY_INTENT_TAG = "historyIntentTag";
    public static final String ALLHISTORY_SUFFIX = "fetchTranxHistory?";
    public static final String DEALSHISTORY_SUFFIX = "dealsTranxHistory?";
    public static final String RECHARGEHISTORY_SUFFIX = "rechargeTranxHistory?";
    public static final String OFFERHISTORY_SUFFIX = "offersTranxHistory?";
    public static final String HISTORY_DATAOBJECT_PARCELABLE_TAG = "historyparcelable";
    public static final String HISTORY_URL = "history_url";

    // OTP Validation
    public static final String REGISTER_MOBILE_NUMBER = CORE_URL
            + "registerMobile";
    public static final String VALIDATE_MOBILE_NUMBER = CORE_URL
            + "validateOTP";
    // Intent filter send all aps api
    public static final String INTENT_SEND_ALL_APPS = "sendallApps";

    // Send contacts
    public static final String CONTACTS = CORE_URL + "contactLogs";

    // Sucess and Failure Constants
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    public static final String TOKEN = "tokenId";
    public static final String SCOPE = "audience:server:client_id:40098903451-s18cdniaosujtl7qfniq58a9gipqasmq.apps.googleusercontent.com";

    // My profile extra params
    public static final String DOB = "DOB";
    public static final String GENDER = "gender";
    public static final String STATE = "state";
    public static final String CITY = "city";
    public static final String INCOME = "alternateMobileNo";
    public static final String OCCUPATION = "alternateEmailId";

    public static String GOOGLE_PLUS_LINK_PLAY_STORE = "https://play.google.com/store/apps/details?id=in.freeb.app";
    public static Boolean DEBUG_MODE = true;

    public static int interstitalFailedCode = 1000;

    public static String interstitalFailedMessage = "Unable to load Interstitial";
    public static String interstitalUnavailableMessage = "No interstitial ad available";

    public static int interstitalLoadCode = 1001;

    public static int interstitalDismissCode = 1002;

    public static String interstitalDismissMessage = "Interstitial dismissed";

    public static int bannerFailedCode = 1003;

    public static String bannerFailedMessage = "Unable to load Banner";

    public static int bannerDismissCode = 1004;

    public static String bannerDismissMessage = "Banner dismissed";

    public static String offersFailedCode = "1005";

    public static String offersFailedMessage = "Unable to load Banner";

    public static String offersDismissCode = "1006";

    public static String offersDismissMessage = "No offer installed";

    public static int bannerLoadCode = 1007;
    public static int offersLoadCode = 1008;

    public static String initServiceFailed = "1009";

    public static String initServiceFailedMessage = "Registration Failed";

    public static String initServiceSuccess = "1010";

    public static String initServiceSuccessMessage = "Registration Successfull";

    public static String noBannerAvailableCode = "1011";

    public static String noBannerAvailableMessage = "Currently no bannner available";

    public static String noInterstitialAvailableCode = "1012";

    public static String noInterstitialAvailableMessage = "Currently no interstitial available";

    public static String sdkClose = "0000";

    public static String sdkCloseMessage = "Offers dismissed";

    public static final String SDK_VERSION = "2";
    public static final String SDK_PACKAGENAME = "in.freeb.sdk";
    public static final String diaglogDismiss = "Offers dialog dismissed";

}
