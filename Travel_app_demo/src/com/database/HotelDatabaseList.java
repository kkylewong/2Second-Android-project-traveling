package com.database;

import com.example.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * Demo application to show how to use the 
 * built-in SQLite database with a cursor to populate
 * a ListView.
 */
public class HotelDatabaseList extends Activity {
	
	DBAdapterHotel myHotelDb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_data_list);
		
		openDB();
		populateListViewFromDB();
		registerListClickCallback();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		closeDB();
	}

	private void openDB() {
		myHotelDb = new DBAdapterHotel(this);
		myHotelDb.open();
	}
	private void closeDB() {
		myHotelDb.close();
	}

	/* 
	 * UI Button Callbacks
	 */
	public void onClick_AddRecord(View v) {

		// Add it to the DB and re-draw the ListView
		myHotelDb.insertRow("2014-09-18", "london", "hotel location");
		populateListViewFromDB();
	}

	public void onClick_ClearAll(View v) {
		myHotelDb.deleteAll();
		populateListViewFromDB();
	}


	private void populateListViewFromDB() {
		Cursor cursor = myHotelDb.getAllRows();
		
		// Allow activity to manage lifetime of the cursor.
		// DEPRECATED! Runs on the UI thread, OK for small/short queries.
		startManagingCursor(cursor);
		
		// Setup mapping from cursor to view fields:
		String[] fromFieldNames = new String[] 
				{DBAdapterHotel.KEY_DATE, DBAdapterHotel.KEY_CITYICON, DBAdapterHotel.KEY_THECITY, DBAdapterHotel.KEY_HOTELINFO};
		int[] toViewIDs = new int[]
				{R.id.item_name,     R.id.item_icon,           R.id.item_favcolour,     R.id.item_studentnum};
		
		// Create adapter to may columns of the DB onto elemesnt in the UI.
		SimpleCursorAdapter myCursorAdapter = 
				new SimpleCursorAdapter(
						this,		// Context
						R.layout.hotel_item_list,	// Row layout template
						cursor,					// cursor (set of DB records to map)
						fromFieldNames,			// DB Column names
						toViewIDs				// View IDs to put information in
						);
		
		// Set the adapter for the list view
		ListView myList = (ListView) findViewById(R.id.listViewFromHotelDB);
		myList.setAdapter(myCursorAdapter);
	}
	
	private void registerListClickCallback() {
		ListView myList = (ListView) findViewById(R.id.listViewFromHotelDB);
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, 
					int position, long idInDB) {

				updateItemForId(idInDB);
				displayToastForId(idInDB);
			}
		});
	}
	
	private void updateItemForId(long idInDB) {
		Cursor cursor = myHotelDb.getRow(idInDB);
		if (cursor.moveToFirst()) {
			long idDB = cursor.getLong(DBAdapterHotel.COL_ROWID);
			String name = cursor.getString(DBAdapterHotel.COL_DATE);
			String studentNum = cursor.getString(DBAdapterHotel.COL_CITYICON);
			String favColour = cursor.getString(DBAdapterHotel.COL_THECITY);
			String hotelInfo = cursor.getString(DBAdapterHotel.COL_HOTELINFO);
			
			favColour += "!";
			myHotelDb.updateRow(idInDB, name, studentNum, favColour,hotelInfo);
		}
		cursor.close();
		populateListViewFromDB();		
	}
	
	private void displayToastForId(long idInDB) {
		Cursor cursor = myHotelDb.getRow(idInDB);
		if (cursor.moveToFirst()) {
			long idDB = cursor.getLong(DBAdapterHotel.COL_ROWID);
			String name = cursor.getString(DBAdapterHotel.COL_DATE);
			String studentNum = cursor.getString(DBAdapterHotel.COL_CITYICON);
			String favColour = cursor.getString(DBAdapterHotel.COL_THECITY);
			String hotelInfo = cursor.getString(DBAdapterHotel.COL_HOTELINFO);
			
			String message = "ID: " + idDB + "\n" 
					+ "Name: " + name + "\n"
					+ "Std#: " + studentNum + "\n"
					+ "FavColour: " + favColour + "\n"
					+ "hotelInfo: " + hotelInfo
					;
//			Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
		}
		cursor.close();
	}
}










