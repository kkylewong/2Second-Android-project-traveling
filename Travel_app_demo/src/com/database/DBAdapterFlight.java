// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.database;

import com.example.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapterFlight {

	// ///////////////////////////////////////////////////////////////////
	// Constants & Data
	// ///////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";

	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String KEY_DATE = "date";
	public static final String KEY_FROMPLACE = "fromPlace";
	public static final String KEY_TOPLACE = "toPlace";
	public static final String KEY_FLIGHTINFO = "flightInfo";

	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_DATE = 1;
	public static final int COL_FROMPLACE = 2;
	public static final int COL_TOPLACE = 3;
	public static final int COL_FLIGHTINFO = 4;

	public static final String[] ALL_KEYS = new String[] { KEY_ROWID, KEY_DATE,
			KEY_FROMPLACE, KEY_TOPLACE, KEY_FLIGHTINFO };

	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "MyFlightDb";
	public static final String DATABASE_TABLE = "flightDB";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 2;

	private static final String DATABASE_CREATE_SQL = "create table "
			+ DATABASE_TABLE
			+ " ("
			+ KEY_ROWID
			+ " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			// - Key is the column name you created above.
			// - {type} is one of: text, integer, real, blob
			// (http://www.sqlite.org/datatype3.html)
			// - "not null" means it is a required field (must be given a
			// value).
			// NOTE: All must be comma separated (end of line!) Last one must
			// have NO comma!!
			+ KEY_DATE + " string not null, " + KEY_FROMPLACE
			+ " string not null, " + KEY_TOPLACE + " string not null, "
			+ KEY_FLIGHTINFO + " string not null"
			// Rest of creation:
			+ ");";

	// Context of application who uses us.
	private final Context context;

	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	public String scheduleDate, scheduleFromPlace, scheduleToPlace, scheduleFlightInfo;
	public String scheduleDate_back, scheduleFromPlace_back, scheduleToPlace_back, scheduleFlightInfo_back;
	// ///////////////////////////////////////////////////////////////////
	// Public methods:
	// ///////////////////////////////////////////////////////////////////

	public DBAdapterFlight(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}

	// Open the database connection.
	public DBAdapterFlight open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}

	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}

	// Add a new set of values to the database.
	public long insertRow(String date, String fromPlace, String toPlace,
			String flightInfo) {
		/*
		 * CHANGE 3:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_DATE, date);
		initialValues.put(KEY_FROMPLACE, fromPlace);
		initialValues.put(KEY_TOPLACE, toPlace);
		initialValues.put(KEY_FLIGHTINFO, flightInfo);
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}

	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();
	}

	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null,
				null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null,
				null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public void getStringDataDEPART(long rowId) {
		Cursor c = getRow(rowId);
		int id = c.getInt(COL_ROWID);
		scheduleDate = c.getString(COL_DATE);
		scheduleFromPlace = c.getString(COL_FROMPLACE);
		scheduleToPlace = c.getString(COL_TOPLACE);
		scheduleFlightInfo = c.getString(COL_FLIGHTINFO);
	}
	
	public void getStringDataBACK(long rowId) {
		Cursor c = getRow(rowId);
		int id = c.getInt(COL_ROWID);
		scheduleDate_back = c.getString(COL_DATE);
		scheduleFromPlace_back = c.getString(COL_FROMPLACE);
		scheduleToPlace_back = c.getString(COL_TOPLACE);
		scheduleFlightInfo_back = c.getString(COL_FLIGHTINFO);
	}
	
	//this method is not used
	public void insertFlightInfo() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		int index = 1;
		if (c.moveToFirst()) {
			do {
				String date = c.getString(DBAdapterFlight.COL_DATE);
				String fromPlace = c.getString(DBAdapterFlight.COL_FROMPLACE);
				String toPlace = c.getString(DBAdapterFlight.COL_TOPLACE);
				
				String flightInfo= BookingData.returnFlight(index);
				updateRow(rowId,date,fromPlace,toPlace,flightInfo);
				System.out.println(index + flightInfo);
				index++;
				
			} while (c.moveToNext());
		}
		c.close();
	}

	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, String date, String fromPlace,
			String toPlace, String flightInfo) {
		String where = KEY_ROWID + "=" + rowId;
		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_DATE, date);
		newValues.put(KEY_FROMPLACE, fromPlace);
		newValues.put(KEY_TOPLACE, toPlace);
		newValues.put(KEY_FLIGHTINFO, flightInfo);
		// Insert it into the database.
		System.out.println("DBAFlight----RowId-date--->"+rowId+" "+ date);
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	public boolean updateRow_onlyDate(long rowId, String date){
		String where = KEY_ROWID + "=" + rowId;

		ContentValues newValues = new ContentValues();
		newValues.put(KEY_DATE, date);
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}

	public boolean updateReturnFlightFromCity_date(long rowId, String fromCity, String date){
		String where = KEY_ROWID + "=" + rowId;
		
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_DATE, date);
		newValues.put(KEY_FROMPLACE, fromCity);
		
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	public boolean insertFirstFlightInfo(Long rowId){
		String where = KEY_ROWID + "=" + rowId;
		ContentValues newValues = new ContentValues();
		String firstFlight=BookingData.flightOne;
		newValues.put(KEY_FLIGHTINFO, firstFlight);
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	public boolean insertSecondFlightInfo(Long rowId){
		String where = KEY_ROWID + "=" + rowId;
		ContentValues newValues = new ContentValues();
		String firstFlight=BookingData.flightTwo;
		newValues.put(KEY_FLIGHTINFO, firstFlight);
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	public boolean updateDepartToCity(long rowId, String city){
		String where = KEY_ROWID + "=" + rowId;

		ContentValues newValues = new ContentValues();
		newValues.put(KEY_TOPLACE, city);
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	// ///////////////////////////////////////////////////////////////////
	// Private Helper Classes:
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Private class which handles database creation and upgrading. Used to
	 * handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version "
					+ oldVersion + " to " + newVersion
					+ ", which will destroy all old data!");

			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

			// Recreate new database:
			onCreate(_db);
		}
	}
}
