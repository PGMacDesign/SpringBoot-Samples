
package com.pgmacdesign.utils;
import java.lang.reflect.Type;

public class Constants {

    ////////////////
    //Useful Links//
    ////////////////

    /*
    1) https://www.mkyong.com/mongodb/spring-data-mongodb-delete-document/
    2) https://www.mkyong.com/mongodb/spring-data-mongodb-select-fields-to-return/
    3) https://www.mkyong.com/mongodb/spring-data-mongodb-update-document/
    4) https://www.mkyong.com/mongodb/spring-data-mongodb-jsr-310-or-java-8-new-date-apis/
    5) https://www.mkyong.com/mongodb/spring-data-mongodb-aggregation-grouping-example/
    6) https://www.mkyong.com/mongodb/spring-data-mongodb-auto-sequence-id-example/
    7) https://www.mkyong.com/mongodb/spring-data-mongodb-get-last-modified-records-date-sorting/
    8) https://www.mkyong.com/mongodb/spring-data-mongodb-like-query-example/
    9) https://www.mkyong.com/mongodb/spring-data-mongodb-query-document/
    10) https://www.mkyong.com/mongodb/
    11) https://spring.io/guides/gs/accessing-data-mongodb/
    12) https://spring.io/guides/gs/accessing-data-mysql/
    13) http://javasampleapproach.com/spring-framework/spring-data/spring-data-mongooperations-accessing-mongo-database
    14) http://www.baeldung.com/rest-with-spring-series/
    15) http://www.baeldung.com/persistence-with-spring-series/

     */

    ///////////
    //Configs//
    ///////////

    public static final boolean SHOW_ERRORS_IN_LOGCAT = false;
    public static final long TIME_IN_MILLISECONDS_EXTEND_AUTH_TOKEN_USER = (1000 * 60 * 60 * 24); //1 day
    public static final long TIME_IN_MILLISECONDS_EXTEND_AUTH_TOKEN_ADMIN = (1000 * 60 * 60 * 24 * 7); //1 week

    /////////
    //Strs///
    /////////

    public static final String GENERIC_ERROR_STRING = "An unknown error has occurred. Please try your request again in a few moments";
    public final static String HEX = "0123456789ABCDEF";
    public final static String UTF8 = "UTF-8";
    public static boolean USE_MONGODB_OVER_SQL = true;

    ////////////////////
    //Mongo DB Details//
    ////////////////////

    public static final String MONGODB_USERNAME = "PatTestAdmin";
    public static final String MONGODB_PASSWORD = "password";
    public static final String MONGODB_DB_NAME = "pattest";
    public static final String MONGODB_HOST = "localhost";
    public static final int MONGODB_PORT = 27017;
    public static final String MONGODB_DESCRIPTION = "Sample Description for DB";


    /////////////////////////////////////////////////////////////
    //Custom Tags (There is no specific order to these numbers)//
    /////////////////////////////////////////////////////////////

    //Request codes used for permission requests
    public static final int TAG_PERMISSIONS_ACCESS_NETWORK_STATE = 3398;
    //public static final int TAG_PERMISSIONS_REQUEST_GALLERY = 4399;
    public static final int TAG_PERMISSIONS_REQUEST_BASE_CALL = 3300;
    public static final int TAG_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3301;
    public static final int TAG_PERMISSIONS_REQUEST_CAMERA = 3302;
    public static final int TAG_PERMISSIONS_REQUEST_ALL = 3303;
    public static final int TAG_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3304;
    public static final int TAG_PERMISSIONS_REQUEST_READ_PHONE_STATE = 3305;
    public static final int TAG_PERMISSIONS_REQUEST_CONTACTS = 3306;
    public static final int TAG_PERMISSIONS_ACCESS_WIFI_STATE = 3307;
    public static final int TAG_PERMISSIONS_ACCESS_FINE_LOCATION = 3308;
    public static final int TAG_PERMISSIONS_ACCESS_COARSE_LOCATION = 3309;
    public static final int TAG_PERMISSIONS_RECEIVE_BOOT_COMPLETED = 3310;
    public static final int TAG_RETROFIT_PARSE_ERROR = 3311;
    public static final int TAG_RETROFIT_CALL_ERROR = 3312;
    public static final int TAG_TBD2 = 3313;
    public static final int TAG_TBD3 = 3314;
    public static final int TAG_TBD4 = 3315;
    public static final int TAG_TBD5 = 3316;
    public static final int TAG_TBD6 = 3317;
    public static final int TAG_TBD7 = 3318;
    public static final int TAG_TBD8 = 3319;
    public static final int TAG_TBD9 = 3320;

    //File Creation Tags
    public static final int TAG_TXT_FILE_CREATION = 3400;
    //Date Formatting Tags, used for comparison and formatting
    /**
     * Sample: Wed Oct 16 15:21:20 PST 2018
     */
    public static final int DATE_EEEE_MMM_dd_HH_mm_ss_z_yyyy = 4403;
    /**
     * Sample: 2018-10-16
     */
    public static final int DATE_YYYY_MM_DD_T_HH_MM_SS_SSS_Z = 4403;
    public static final int DATE_YYYY_MM_DD_T_HH_MM_SS_Z = 4404;
    public static final int DATE_MM_DD_YYYY = 4405;
    public static final int DATE_MM_DD_YY = 4406;
    public static final int DATE_MM_DD_YYYY_AMERICANS_NEED_TO_LEARN_TO_FORMAT_CORRECTLY
            = 4405; //AKA DATE_STUPID_AMERICAN_WAY
    public static final int DATE_MM_DD_YY_AMERICANS_NEED_TO_LEARN_TO_FORMAT_CORRECTLY
            = 4406; //AKA DATE_STUPID_AMERICAN_WAY
    public static final int DATE_YYYY_MM_DD = 4407;
    public static final int DATE_MM_DD = 4408;
    public static final int DATE_MM_YY = 4409;
    public static final int DATE_MM_YYYY = 4410;
    public static final int DATE_MILLISECONDS = 4411;
    public static final int DATE_EPOCH = 4412;
    public static final int DATE_MM_DD_YYYY_HH_MM = 4413;

    public static int[] ALL_DATE_TYPES = {DATE_YYYY_MM_DD_T_HH_MM_SS_SSS_Z,
            DATE_YYYY_MM_DD_T_HH_MM_SS_Z, DATE_MM_DD_YYYY, DATE_MM_DD_YY, DATE_YYYY_MM_DD,
            DATE_MM_DD, DATE_MM_YY, DATE_MM_YYYY, DATE_MM_DD_YYYY_HH_MM};
    //More Misc Tags
    public static final int TAG_OAUTH_DATA_OBJECT = 4414;
    public static final int TAG_OAUTH_ERROR = 4415;
    public static final int TAG_PHOTO_BAD_URL = 4416;
    public static final int TAG_FILE_DOWNLOADED = 4417;
    public static final int TAG_DIALOG_POPUP_YES = 4418;
    public static final int TAG_DIALOG_POPUP_NO = 4419;
    public static final int TAG_DIALOG_POPUP_CANCEL = 4420;
    public static final int TAG_PHONE_QUERY_REGEX_FAIL = 4421;
    public static final int TAG_PHONE_QUERY_REGEX_SUCCESS = 4422;
    public static final int TAG_CONTACT_QUERY_EMAIL = 4423;
    public static final int TAG_CONTACT_QUERY_PHONE = 4424;
    public static final int TAG_CONTACT_QUERY_ADDRESS = 4425;
    public static final int TAG_CONTACT_QUERY_NAME = 4426;
    public static final int TAG_TAKE_PICTURE_WITH_CAMERA = 4427;
    public static final int TAG_PHOTO_FROM_GALLERY = 4428;
    public static final int TAG_CROP_PHOTO = 4429;
    public static final int TAG_RETURN_IMAGE_URL = 4430;
    public static final int TAG_TAKE_VIDEO_WITH_RECORDER = 4431;
    public static final int TAG_MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 4432;
    public static final int TAG_MY_PERMISSIONS_REQUEST_CAMERA = 4433;
    public static final int TAG_PHOTO_UNKNOWN_ERROR = 4434;
    public static final int TAG_CROP_ERROR = 4435;
    public static final int TAG_CROP_SUCCESS = 4436;
    public static final int TAG_PHOTO_CANCEL = 4437;
    public static final int TAG_UPLOAD_ERROR = 4438;
    public static final int TAG_UPLOAD_SUCCESS = 4439;
    public static final int TAG_CLICK_NO_TAG_SENT = 4440;
    public static final int TAG_LONG_CLICK_NO_TAG_SENT = 4441;
    public static final int TAG_RETROFIT_CALL_FAILED = 4442;
    public static final int TAG_RETROFIT_CALL_SUCCESS_BOOLEAN = 4443;
    public static final int TAG_RETROFIT_CALL_SUCCESS_STRING = 4444;
    public static final int TAG_RETROFIT_CALL_SUCCESS_OBJECT = 4445;
    public static final int TAG_TAKE_SELF_PHOTO = 4446;
    public static final int TAG_TAKE_SELF_PHOTO_SUCCESS = 4447;
    public static final int TAG_TAKE_SELF_PHOTO_FAILURE = 4448;
    public static final int TAG_FRAGMENT_SWITCHER_ERROR = 4449;
    public static final int TAG_FRAGMENT_SWITCHER_OBJECT = 4450;
    public static final int TAG_FRAGMENT_SWITCHER_NO_OBJECT = 4451;
    public static final int TAG_TIMER_UTILITIES_FINISHED = 4452;
    public static final int TAG_TIMER_UTILITIES_FINISHED_WITH_DATA = 4453;
    public static final int TAG_SMS_RECEIVED_BROADCAST_RECEIVER = 4454;
    public static final int TAG_SMS_RECEIVED_BROADCAST_RECEIVER_EMPTY = 4455;
    public static final int TAG_VIEW_PARAMS_LOADED = 4456;
    public static final int TAG_VIEW_PARAMS_LOADING_FAILED = 4457;
    public static final int TAG_VIEW_FINISHED_DRAWING = 4458;
    public static final int TAG_FCM_SUCCESS_RESPONSE = 4459;
    public static final int TAG_FCM_FAIL_RESPONSE = 4460;
    public static final int TAG_NO_INTERNET = 4461;
    public static final int TAG_BASE64_IMAGE_ENCODE_SUCCESS = 4462;
    public static final int TAG_BASE64_IMAGE_ENCODE_FAIL = 4463;
    public static final int TAG_MULTIPURPOSE_CHOICE_CLICK_ADAPTER = 4464;
    public static final int TAG_MULTIPURPOSE_CHOICE_LONG_CLICK_ADAPTER = 4465;
    public static final int TAG_MAP_STRING_INTEGER = 4466;
    public static final int TAG_STRING = 4467;
    public static final int TAG_SIMPLE_TEXT_ICON_ADAPTER_CLICK = 4468;
    public static final int TAG_GENERIC_FILTER_RESULTS = 4469;
    public static final int TAG_CONTACT_QUERY_PROGRESS_UPDATE = 4470;
    public static final int TAG_CONTACT_QUERY_NO_RESULTS = 4471;
    public static final int TAG_CONTACT_QUERY_MISSING_CONTACT_PERMISSION = 4472;
    public static final int TAG_CONTACT_QUERY_UNKNOWN_ERROR = 4473;
    public static final int TAG_CONTACT_QUERY_ALL_MERGED_RESULTS = 4474;
    public static final int TAG_INVALID_BASE_64_IMAGE = 4475;
    public static final int TAG_GOOGLE_VISION_SUCCESS_RESULT = 4476;
    public static final int TAG_GOOGLE_VISION_FAIL_RESULT = 4477;
    public static final int TAG_GOOGLE_VISION_UNKNOWN_ERROR = 4478;
    public static final int TAG_INVALID_BITMAP_IMAGE = 4479;
    public static final int TAG_BITMAP_BASE64_CONVERSION_FAIL = 4480;
    public static final int TBD0 = 4481;
    public static final int TBD1 = 4482;
    public static final int TBD2 = 4483;
    public static final int TBD3 = 4484;

    //String Tags
    public static final String TAG_SELF_PHOTO_URI = "tag_self_photo_uri";

    ////////////////
    //Time Values //
    ////////////////

    //Time values in milliseconds
    public static final long ONE_SECOND = (1000);
    public static final long ONE_MINUTE = (1000*60);
    public static final long ONE_HOUR = (1000*60*60);
    public static final long ONE_DAY = (1000*60*60*24);
    public static final long ONE_WEEK = (1000*60*60*24*7);
    public static final long ONE_MONTH = (1000*60*60*24*30);
    public static final long ONE_YEAR = (1000*60*60*24*365);


    //Default Date Formats
    public static final String DEFAULT_ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DEFAULT_ISO_FORMAT_WITHOUT_MILLISECONDS = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    //This is the string format received when calling Date date = new Date().toString(); //<---
    public static final String DEFAULT_JAVA_DATE_FORMAT = "EEEE MMM dd HH:mm:ss z yyyy";
    public static final String DATE_STRING_PATTERN_24_HOUR_TIME = "HH:mm";
    public static final String DATE_STRING_PATTERN_12_HOUR_TIME = "hh:mm a";

    //////////
    //Colors//
    //////////

    //Color Strings
    public static final String COLOR_TRANSPARENT = "#00000000";
    public static final String COLOR_WHITE = "#FFFFFF";
    public static final String COLOR_BLACK = "#000000";
    public static final String COLOR_YELLOW = "#FFFF00";
    public static final String COLOR_FUCHSIA = "#FF00FF";
    public static final String COLOR_RED = "#FF0000";
    public static final String COLOR_SILVER = "#C0C0C0";
    public static final String COLOR_GRAY = "#808080";
    public static final String COLOR_LIGHT_GRAY = "#D3D3D3";
    public static final String COLOR_DARK_GRAY = "#666666";
    public static final String COLOR_OLIVE = "#808000";
    public static final String COLOR_PURPLE = "#800080";
    public static final String COLOR_MAROON = "#800000";
    public static final String COLOR_AQUA = "#00FFFF";
    public static final String COLOR_LIME = "#00FF00";
    public static final String COLOR_TEAL = "#008080";
    public static final String COLOR_GREEN = "#008000";
    public static final String COLOR_PINK = "#FFC0CB";
    public static final String COLOR_BLUE = "#0000FF";
    public static final String COLOR_NAVY_BLUE = "#000080";
    //For the Semi transparent colors, the higher the number, the darker (less opaque) the color
    public static final String COLOR_SEMI_TRANSPARENT_1 = "#20111111";
    public static final String COLOR_SEMI_TRANSPARENT_2 = "#30111111";
    public static final String COLOR_SEMI_TRANSPARENT_3 = "#40111111";
    public static final String COLOR_SEMI_TRANSPARENT_4 = "#50111111";
    public static final String COLOR_SEMI_TRANSPARENT_5 = "#69111111";
    public static final String COLOR_SEMI_TRANSPARENT_6 = "#79111111";
    public static final String COLOR_SEMI_TRANSPARENT_7 = "#89111111";
    public static final String COLOR_SEMI_TRANSPARENT_8 = "#99111111";
    public static final String COLOR_SEMI_TRANSPARENT_9 = "#A9111111";

    /////////////
    //Raw Types//
    /////////////

    public static final Type TYPE_BOOLEAN = Boolean.TYPE;
    public static final Type TYPE_DOUBLE = Double.TYPE;
    public static final Type TYPE_INTEGER = Integer.TYPE;

    ///////////////////////
    //Regular Expressions//
    ///////////////////////

    //Credit Card Regular Expressions
    public static final String REGEX_CREDIT_CARD_VISA = "^4[0-9]{12}(?:[0-9]{3})?$";
    public static final String REGEX_CREDIT_CARD_MASTERCARD = "^5[1-5][0-9]{14}$";
    public static final String REGEX_CREDIT_CARD_AMERICAN_EXPRESS = "^3[47][0-9]{13}$";
    public static final String REGEX_CREDIT_CARD_DINERS_CLUB = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$";
    public static final String REGEX_CREDIT_CARD_DISCOVER = "^6(?:011|5[0-9]{2})[0-9]{12}$";
    public static final String REGEX_CREDIT_CARD_JCB = "^(?:2131|1800|35\\d{3})\\d{11}$";
    public static final String REGEX_CREDIT_CARD_UNKNOWN = "^unknown$";

    //Misc Regexs
    public static final String REGEX_WEB_URL_ENCODING = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public static final String REGEX_HTML = "<(\\\"[^\\\"]*\\\"|'[^']*'|[^'\\\">])*>";
    public static final String REGEX_HTML2 = "<html>";
    public static final String REGEX_PASSWORD_PATTERN = "^\\S*(?=\\S*[a-zA-Z])(?=\\S*[0-9])\\S*$";
    public static final String REGEX_INTEGER = "^[0-9]+$";
    public static final String REGEX_DECIMAL = "^[0-9]+(?:\\.[0-9]+)?$";
    public static final String REGEX_MONEY = "^[0-9]+(?:\\.[0-9]{0,2})?$";
    public static final String REGEX_MONEY_SIGNED = "^[-+]?[0-9]+(?:\\.[0-9]{0,2})?$";
    public static final String REGEX_PHONE = "^ *\\(?[0-9]{3,3}[-\\.\\)]? ?[0-9]{3,3}[-\\. ]?[0-9]{4,4} *$";
    public static final String REGEX_IPADDRESS = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$";
    public static final String REGEX_COLOR = "^#[0-9a-fA-F]{6,6}$";
    public static final String REGEX_DATE = "^[0-9]{1,2}[-/][0-9]{1,2}[-/][0-9]{2,4}$";
    public static final String REGEX_EMAIL = "^\\s*[^@\\s]+@(?:[^@\\.\\s]+\\.[^@\\.\\s]+)+\\s*$";

}
