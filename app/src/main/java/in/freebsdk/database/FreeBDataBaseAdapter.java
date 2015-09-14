package in.freebsdk.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBSDKLogger;

/**
 * Create a SimpleCursorAdapter and attach that to cursor returned from our database custom query selection.
 * The data columns returned from the cursor is then mapped to our custom view for display.
 */
public class FreeBDataBaseAdapter {

    private DataBaseOpenHelper openHelper;
    Context context;
    private ContentValues contentValues = null;

    SQLiteDatabase sqLiteDatabase;

    public FreeBDataBaseAdapter(Context context) {
        this.context = context;
        openHelper = new DataBaseOpenHelper(context);

    }

    /**
     * Creates a Session which opens a database connection and begins a transaction
     *
     * @return Database Object
     * @throws android.database.SQLException Exception while open the database
     */

    public FreeBDataBaseAdapter open() throws SQLException {
        sqLiteDatabase = openHelper.getWritableDatabase();
        return this;
    }

    /**
     * Closes this session, ends the
     * transaction and closes the database connection.
     */
    public void close() {
        try {
            openHelper.close();
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
    }

    /**
     * Executes an INSERT.
     * @see SQLiteDatabase#insertData
     */

    /**
     * Inserting Offer Records
     *
     * @param appName          input App Name
     * @param packName         input Package Name
     * @param appStatus        input App Status
     * @param timeInSeconds    input Time In Seconds
     * @param offerId          input Offer ID
     * @param offerPrice       input Offer Price
     * @param offerAction      input Offer Action
     * @param offerType        input Offer Type
     * @param alreadyConsumed  input Already Consumed
     * @param appCategory      input App Category
     * @param appUrl           input App URL
     * @param referenceId      input Reference ID
     * @param udf2             input udf2
     * @param udf3             input udf3
     * @param servereferanceid input Server Reference ID
     * @param trackingid       input Tracking ID
     * @param itemId           input Item ID
     * @return Query Result
     */


    public long insertData(String appName, String packName, String appStatus,
                           String timeInSeconds, String offerId, String offerPrice,
                           String offerAction, String offerType, String alreadyConsumed,
                           String appCategory, String appUrl, String referenceId, long udf2,
                           String udf3, String servereferanceid, String trackingid,
                           String itemId) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(DataBaseOpenHelper.COLUMN_APP_NAME, appName);
        contentValues.put(DataBaseOpenHelper.COLUMN_PACKAGE_NAME, packName);
        contentValues.put(DataBaseOpenHelper.COLUMN_APP_STATUS, appStatus);
        contentValues.put(DataBaseOpenHelper.COLUMN_TIME_IN_SECONDS,
                timeInSeconds);
        contentValues.put(DataBaseOpenHelper.COLUMN_OFFER_ID, offerId);
        contentValues.put(DataBaseOpenHelper.COLUMN_OFFER_PRICE, offerPrice);
        contentValues.put(DataBaseOpenHelper.COLUMN_OFFER_ACTION, offerAction);
        contentValues.put(DataBaseOpenHelper.COLUMN_OFFER_TYPE, offerType);
        contentValues.put(DataBaseOpenHelper.COLUMN_ALREADY_CONSUMED,
                alreadyConsumed);
        contentValues.put(DataBaseOpenHelper.COLUMN_APP_CATEGORY, appCategory);
        contentValues.put(DataBaseOpenHelper.COLUMN_APP_URL, appUrl);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF1, referenceId);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF2, udf2);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF3, udf3);
        contentValues.put(DataBaseOpenHelper.COLUMN_SERVERREFERANCE_ID,
                servereferanceid);
        contentValues.put(DataBaseOpenHelper.COLUMN_TRACKING_ID, trackingid);
        contentValues.put(DataBaseOpenHelper.COLUMN_ITEM_ID, itemId);
        long id = sqLiteDatabase.insert(
                DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, null, contentValues);

        return id;
    }

    /**
     * Executes an UPDATE.
     * @see SQLiteDatabase#updateData
     */
    /**
     * Update Offer Records
     *
     * @param appName          input App Name
     * @param packName         input Package Name
     * @param appStatus        input App Status
     * @param timeInSeconds    input Time In Seconds
     * @param offerId          input Offer ID
     * @param offerPrice       input Offer Price
     * @param offerAction      input Offer Action
     * @param offerType        input Offer Type
     * @param alreadyConsumed  input Already Consumed
     * @param appCategory      input App Category
     * @param appUrl           input App URL
     * @param referenceId      input Reference ID
     * @param udf2             input udf2
     * @param udf3             input udf3
     * @param servereferanceid input Server Reference ID
     * @param trackingid       input Tracking ID
     * @param itemId           input Item ID
     * @return Query Result
     */
    public long updateData(String appName, String packName, String appStatus,
                           String timeInSeconds, String offerId, String offerPrice,
                           String offerAction, String offerType, String alreadyConsumed,
                           String appCategory, String appUrl, String referenceId, long udf2,
                           String udf3, String servereferanceid, String trackingid,
                           String itemId) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(DataBaseOpenHelper.COLUMN_APP_NAME, appName);
        contentValues.put(DataBaseOpenHelper.COLUMN_PACKAGE_NAME, packName);
        contentValues.put(DataBaseOpenHelper.COLUMN_APP_STATUS, appStatus);
        contentValues.put(DataBaseOpenHelper.COLUMN_TIME_IN_SECONDS,
                timeInSeconds);
        contentValues.put(DataBaseOpenHelper.COLUMN_OFFER_ID, offerId);
        contentValues.put(DataBaseOpenHelper.COLUMN_OFFER_PRICE, offerPrice);
        contentValues.put(DataBaseOpenHelper.COLUMN_OFFER_ACTION, offerAction);
        contentValues.put(DataBaseOpenHelper.COLUMN_OFFER_TYPE, offerType);
        contentValues.put(DataBaseOpenHelper.COLUMN_ALREADY_CONSUMED,
                alreadyConsumed);
        contentValues.put(DataBaseOpenHelper.COLUMN_APP_CATEGORY, appCategory);
        contentValues.put(DataBaseOpenHelper.COLUMN_APP_URL, appUrl);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF1, referenceId);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF2, udf2);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF3, udf3);
        contentValues.put(DataBaseOpenHelper.COLUMN_SERVERREFERANCE_ID,
                servereferanceid);
        contentValues.put(DataBaseOpenHelper.COLUMN_TRACKING_ID, trackingid);
        contentValues.put(DataBaseOpenHelper.COLUMN_ITEM_ID, itemId);
        // long id = sqLiteDatabase.insert(
        // DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, null, contentValues);
        String[] whereargs = {offerId};
        int noOfRows = sqLiteDatabase.update(
                DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, contentValues,
                DataBaseOpenHelper.COLUMN_OFFER_ID + " =?", whereargs);

        return noOfRows;
    }

    /**
     * @return All the Available Offer Records from the Table
     */
    public String selectData() {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper._ID,
                DataBaseOpenHelper.COLUMN_APP_NAME,
                DataBaseOpenHelper.COLUMN_PACKAGE_NAME,
                DataBaseOpenHelper.COLUMN_APP_STATUS,
                DataBaseOpenHelper.COLUMN_TIME_IN_SECONDS,
                DataBaseOpenHelper.COLUMN_OFFER_ID,
                DataBaseOpenHelper.COLUMN_OFFER_PRICE,
                DataBaseOpenHelper.COLUMN_OFFER_ACTION,
                DataBaseOpenHelper.COLUMN_OFFER_TYPE,
                DataBaseOpenHelper.COLUMN_ALREADY_CONSUMED,
                DataBaseOpenHelper.COLUMN_UDF1, DataBaseOpenHelper.COLUMN_UDF2,
                DataBaseOpenHelper.COLUMN_UDF3};
        int id = 0;
        String appName = null;
        String packName = null;
        String install_status = null;
        String open_status = null;
        String uninstall_status = null;
        String install_sent_status = null;
        String uninstall_sent_status = null;
        String open_sent_status = null;

        StringBuffer buffer = new StringBuffer();
        ArrayList<String> packageNameList = new ArrayList<String>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns, null,
                    null, null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    id = cursor.getShort(0);
                    appName = cursor.getString(1);
                    packName = cursor.getString(2);
                    install_status = cursor.getString(3);
                    uninstall_status = cursor.getString(4);
                    open_status = cursor.getString(5);
                    install_sent_status = cursor.getString(6);
                    uninstall_sent_status = cursor.getString(7);
                    open_sent_status = cursor.getString(8);
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_UDF1);
                    String referId = cursor.getString(colIndex);
                    buffer.append(id + " " + appName + " " + packName + " "
                            + install_status + " " + uninstall_status + " "
                            + open_status + " " + install_sent_status + " "
                            + uninstall_sent_status + " " + open_sent_status
                            + " " + referId + "\n");
                    packageNameList.add(packName);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return buffer.toString();
    }

    /**
     * Returns Package Name
     *
     * @return Package Name
     */
    public ArrayList<String> getPackageName() {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_PACKAGE_NAME};

        String packName = "";

        ArrayList<String> packageNameList = new ArrayList<String>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns, null,
                    null, null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_PACKAGE_NAME);
                    packName = cursor.getString(colIndex);
                    packageNameList.add(packName);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return packageNameList;
    }

    /**
     * Get Application Name From Package Name
     *
     * @param packageName Input Package Name
     * @return app name
     */
    public String getAppName(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_APP_NAME};
        String[] selArgs = {packageName};

        String appName = "";

        ArrayList<String> appNameList = new ArrayList<String>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_APP_NAME);
                    appName = cursor.getString(colIndex);
                    appNameList.add(appName);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return appName;
    }

    /**
     * @param packageName input Package Name
     * @return Offer ID
     */
    public String getOfferId(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_OFFER_ID};
        String[] selArgs = {packageName};
        Cursor cursor = null;
        String offerId = "";
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_OFFER_ID);
                    offerId = cursor.getString(colIndex);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return offerId;
    }

    /**
     * @param packageName input Package Name
     * @return App Status
     */
    public String getAppStatus(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_APP_STATUS};
        String[] selArgs = {packageName};
        String appStatus = "";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_APP_STATUS);
                    appStatus = cursor.getString(colIndex);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return appStatus;
    }

    /**
     * @param packageName input Package Name
     * @return App Category
     */
    public String getAppCategory(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_APP_CATEGORY};
        String[] selArgs = {packageName};
        String appCategory = "";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_APP_CATEGORY);
                    appCategory = cursor.getString(colIndex);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return appCategory;
    }

    /**
     * @param packageName input Package Name
     * @return App Url
     */
    public String getAppURL(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_APP_URL};
        String[] selArgs = {packageName};
        String appURL = "";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_APP_URL);
                    appURL = cursor.getString(colIndex);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return appURL;
    }

    /**
     * @param packageName input Package Name
     * @return App Server Reference ID
     */
    public String getAppServerReferanceId(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_SERVERREFERANCE_ID};
        String[] selArgs = {packageName};
        String serverReferanceId = "";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_SERVERREFERANCE_ID);
                    serverReferanceId = cursor.getString(colIndex);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return serverReferanceId;
    }

    public String getItemId(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_ITEM_ID};
        String[] selArgs = {packageName};
        String serverReferanceId = "";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_ITEM_ID);
                    serverReferanceId = cursor.getString(colIndex);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return serverReferanceId;
    }

    /**
     * @param packageName input Package Name
     * @return Time in Seconds
     */
    public String getTimeInSeconds(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_TIME_IN_SECONDS};
        String[] selArgs = {packageName};
        String timeInSeconds = "";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_TIME_IN_SECONDS);
                    timeInSeconds = cursor.getString(colIndex);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return timeInSeconds;
    }

    public long getInterestTime(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_UDF2};
        String[] selArgs = {packageName};
        long timeInSeconds = 0;
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_UDF2);
                    timeInSeconds = cursor.getLong(colIndex);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return timeInSeconds;
    }

    /**
     * get Reference id
     *
     * @param packageName input Package Name
     * @return Reference ID
     */
    public String getReferenceId(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_UDF1};
        String[] selArgs = {packageName};
        String referenceId = "";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_UDF1);
                    referenceId = cursor.getString(colIndex);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return referenceId;
    }

    /**
     * @param packageName input Package Name
     * @param status      App Status
     * @return Update App Status
     */
    public int updateAppStatus(String packageName, String status) {

        // UPDATE TABLE1 SET COL_NAME = 'ABC' WHERE COL_NAME = 'XYZ';

        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] whereargs = {packageName};
        values.put(DataBaseOpenHelper.COLUMN_APP_STATUS, status);
        int noOfRows = db.update(DataBaseOpenHelper.TABLE_NAME_OFFER_INFO,
                values, DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?",
                whereargs);

        return noOfRows;

    }

    /**
     * Set UNINSTALL Sent Status
     *
     * @param packageName input Package Name
     * @param status      input App Status
     * @return Update Sent Status
     */

    public int updateSentStatus(String packageName, String status) {

        // UPDATE TABLE1 SET COL_NAME = 'ABC' WHERE COL_NAME = 'XYZ';

        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] whereargs = {packageName};
        values.put(DataBaseOpenHelper.COLUMN_UDF3, status);
        int noOfRows = db.update(DataBaseOpenHelper.TABLE_NAME_OFFER_INFO,
                values, DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?",
                whereargs);

        return noOfRows;

    }

    public ArrayList<String> getUninstall_UnSent() {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_APP_STATUS,
                DataBaseOpenHelper.COLUMN_UDF3,
                DataBaseOpenHelper.COLUMN_PACKAGE_NAME};
        String[] selArgs = {FreeBConstants.APP_STATUS_UNINSTALL, "false"};
        Cursor cursor = null;
        int rowCount = 0;
        String packageName = "";
        ArrayList<String> packageNameList = new ArrayList<>();

        try {
            // cursor = sqLiteDatabase.query(
            // DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
            // DataBaseOpenHelper.COLUMN_APP_STATUS
            // +" AND "+DataBaseOpenHelper.COLUMN_UDF3 + " =?", selArgs,
            // null, null, null);

            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_APP_STATUS + "=" + "'"
                            + FreeBConstants.APP_STATUS_UNINSTALL + "'"
                            + " AND " + DataBaseOpenHelper.COLUMN_UDF3
                            + "='false'", null, null, null, null);
            rowCount = cursor.getCount();
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_PACKAGE_NAME);
                    packageName = cursor.getString(colIndex);
                    packageNameList.add(packageName);
                }
            }
            // sqLiteDatabase.rawQuery("select , selectionArgs)
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            cursor.close();
        }

        return packageNameList;
    }

    public String getSentStatus(String packageName) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_UDF3};
        String[] selArgs = {packageName};
        String sentStatus = "";
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_OFFER_INFO, columns,
                    DataBaseOpenHelper.COLUMN_PACKAGE_NAME + " =?", selArgs,
                    null, null, null);
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);

        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    int colIndex = cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_UDF3);
                    sentStatus = cursor.getString(colIndex);
                }
            }
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return sentStatus;
    }

    // Methods for GCM notification table
    public long insertDataGCMNotificationTable(String message, String title,
                                               String longDescription, String type, String udf1, String udf2,
                                               String udf3, String udf4, String udf5, String udf6, String udf7,
                                               String udf8, String udf9, String udf10) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(DataBaseOpenHelper.MESSAGE, message);
        contentValues.put(DataBaseOpenHelper.TITLE, title);
        contentValues.put(DataBaseOpenHelper.LONG_DESCRIPTION, longDescription);
        contentValues.put(DataBaseOpenHelper.TYPE, type);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF1, udf1);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF2, udf2);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF3, udf3);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF4, udf4);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF5, udf5);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF6, udf6);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF7, udf7);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF8, udf8);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF9, udf9);
        contentValues.put(DataBaseOpenHelper.COLUMN_UDF10, udf10);
        long id = sqLiteDatabase.insert(
                DataBaseOpenHelper.TABLE_NAME_GCM_NOTIFICATION, null,
                contentValues);

        return id;
    }

    public int updateNotificationStatus(String serialId, String status) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] whereargs = {serialId};
        values.put(DataBaseOpenHelper.COLUMN_UDF7, status);
        int noOfRows = db.update(
                DataBaseOpenHelper.TABLE_NAME_GCM_NOTIFICATION, values,
                DataBaseOpenHelper.COLUMN_UDF8 + " =?", whereargs);

        return noOfRows;
    }

    public void deleteItemAtPosition(int position) {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        try {
            sqLiteDatabase.delete(
                    DataBaseOpenHelper.TABLE_NAME_GCM_NOTIFICATION,
                    DataBaseOpenHelper._ID + "=" + position, null);
        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
    }

    public void deleteAllNotification() {
        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        try {
            sqLiteDatabase.delete(
                    DataBaseOpenHelper.TABLE_NAME_GCM_NOTIFICATION, null, null);
        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
    }

    public int selectNotificationStatus() {

        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_UDF7};
        String[] selArgs = {"UNREAD"};

        Cursor cursor = null;
        String status;
        ArrayList<String> statusArrayList = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_GCM_NOTIFICATION, columns,
                    DataBaseOpenHelper.COLUMN_UDF7 + " =?", selArgs, null,
                    null, null);
        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    status = cursor.getString(cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_UDF7));
                    statusArrayList.add(status);
                }
            }
        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        return statusArrayList.size();
    }

    public String selectNotificationStatusViaPosition(String position) {

        SQLiteDatabase sqLiteDatabase = openHelper.getWritableDatabase();
        String[] columns = {DataBaseOpenHelper.COLUMN_UDF7};
        Cursor cursor = null;
        String status = "";
        ArrayList<String> statusArrayList = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(
                    DataBaseOpenHelper.TABLE_NAME_GCM_NOTIFICATION, columns,
                    DataBaseOpenHelper.COLUMN_UDF8 + "=" + position, null,
                    null, null, null);
        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        try {
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    status = cursor.getString(cursor
                            .getColumnIndex(DataBaseOpenHelper.COLUMN_UDF7));
                }
            }
        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
        return status;
    }


    /**
     * Create sqlite "Offer Information" database
     * To insert and get data from SQLite database by extending the SQLiteOpenHelper
     *
     * @author Temp-01161 , Pragya
     */

    public class DataBaseOpenHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "FreeBSDKOfferDB";
        public static final String TABLE_NAME_OFFER_INFO = "offerInfo";
        public static final String _ID = "_id";
        public static final String COLUMN_APP_NAME = "app_name";
        public static final String COLUMN_PACKAGE_NAME = "package_name";
        public static final String COLUMN_APP_STATUS = "app_status";
        public static final String COLUMN_TIME_IN_SECONDS = "time_in_seconds";
        public static final String COLUMN_OFFER_ID = "offer_id";
        public static final String COLUMN_OFFER_PRICE = "offer_price";
        public static final String COLUMN_OFFER_ACTION = "offer_action";
        public static final String COLUMN_OFFER_TYPE = "offer_type";
        public static final String COLUMN_ALREADY_CONSUMED = "already_consumed";
        public static final String COLUMN_APP_CATEGORY = "app_category";
        public static final String COLUMN_APP_URL = "app_url";

        public static final String COLUMN_UDF1 = "udf1"; // refernce id , screen
        // name
        public static final String COLUMN_UDF2 = "udf2"; // interest_time ,
        // should rate us
        public static final String COLUMN_UDF3 = "udf3"; // bitmap url

        public static final String COLUMN_SERVERREFERANCE_ID = "serverreferanceid";
        public static final String COLUMN_TRACKING_ID = "trackingid";
        public static final String COLUMN_ITEM_ID = "itemid";

        // GCM NOTIFICATION DATABASE
        public static final String TABLE_NAME_GCM_NOTIFICATION = "gcm_notification";
        public static final String MESSAGE = "message";
        public static final String TITLE = "title";
        public static final String LONG_DESCRIPTION = "long_description"; // html_message
        public static final String TYPE = "type";
        public static final String COLUMN_UDF4 = "udf4"; // current time in
        // milis
        public static final String COLUMN_UDF5 = "udf5"; // url
        public static final String COLUMN_UDF6 = "udf6"; // package name
        public static final String COLUMN_UDF7 = "udf7";// status - read/unread
        public static final String COLUMN_UDF8 = "udf8";// serialId
        public static final String COLUMN_UDF9 = "udf9";
        public static final String COLUMN_UDF10 = "udf10";

        // RECHARGE SUGGESTIONS
        public static final String TABLE_NAME_RECHARGE_SUGGESTIONS = "recharge_suggestions";
        public static final String RECHARGE_NUMBER = "recharge_number";
        public static final String RECHARGE_TYPE = "recharge_type";

        public static final int VERSION_CODE = 1;

        private static final String CREATE_TABLE_OFFER_INFO = "CREATE TABLE "
                + TABLE_NAME_OFFER_INFO + "(" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_APP_NAME
                + " TEXT," + COLUMN_PACKAGE_NAME + " TEXT," + COLUMN_APP_STATUS
                + " TEXT," + COLUMN_TIME_IN_SECONDS + " TEXT,"
                + COLUMN_OFFER_ID + " TEXT," + COLUMN_OFFER_PRICE + " TEXT,"
                + COLUMN_OFFER_ACTION + " TEXT," + COLUMN_OFFER_TYPE + " TEXT,"
                + COLUMN_ALREADY_CONSUMED + " Text," + COLUMN_APP_CATEGORY
                + " Text," + COLUMN_APP_URL + " Text," + COLUMN_UDF1 + " TEXT,"
                + COLUMN_UDF2 + " INTEGER," + COLUMN_UDF3 + " TEXT,"
                + COLUMN_SERVERREFERANCE_ID + " TEXT," + COLUMN_TRACKING_ID
                + " TEXT," + COLUMN_ITEM_ID + " TEXT" + ")";

        private static final String CREATE_TABLE_GCM_NOTIFICATION = "CREATE TABLE "
                + TABLE_NAME_GCM_NOTIFICATION
                + "("
                + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MESSAGE
                + " TEXT,"
                + TITLE
                + " TEXT,"
                + LONG_DESCRIPTION
                + " TEXT,"
                + TYPE
                + " TEXT,"
                + COLUMN_UDF1
                + " TEXT,"
                + COLUMN_UDF2
                + " TEXT,"
                + COLUMN_UDF3
                + " TEXT,"
                + COLUMN_UDF4
                + " TEXT,"
                + COLUMN_UDF5
                + " TEXT,"
                + COLUMN_UDF6
                + " TEXT,"
                + COLUMN_UDF7
                + " TEXT,"
                + COLUMN_UDF8
                + " TEXT,"
                + COLUMN_UDF9
                + " TEXT,"
                + COLUMN_UDF10 + " TEXT" + ")";

        private static final String CREATE_TABLE_RECHARGE_SUGGESTIONS = "create table "
                + TABLE_NAME_RECHARGE_SUGGESTIONS
                + " ( "
                + _ID
                + " integer primary key autoincrement not null,"
                + RECHARGE_NUMBER
                + " text,"
                + RECHARGE_TYPE
                + " text,"
                + COLUMN_UDF1 + " text," + COLUMN_UDF2 + " text" + " )";

        private Context context;

        public DataBaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION_CODE);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_OFFER_INFO);
            db.execSQL(CREATE_TABLE_GCM_NOTIFICATION);
            db.execSQL(CREATE_TABLE_RECHARGE_SUGGESTIONS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_OFFER_INFO);
            // db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_GCM_NOTIFICATION);
            // onCreate(db);
            if (oldVersion == 1) {
                db.execSQL(CREATE_TABLE_GCM_NOTIFICATION);
                db.execSQL(CREATE_TABLE_RECHARGE_SUGGESTIONS);
            } else if (oldVersion == 2) {
                db.execSQL(CREATE_TABLE_RECHARGE_SUGGESTIONS);
            }
        }

    }

}
