package com.database;

import com.example.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/*
 * Steps to using the DB:
 * 1. [DONE] Instantiate the DB Adapter
 * 2. [DONE] Open the DB
 * 3. [DONE] use get, insert, delete, .. to change data.
 * 4. [DONE]Close the DB
 */

/**
 * Demo application to show how to use the 
 * built-in SQL lite database.
 */
public class FlightDatabaseList extends Activity {
	
	DBAdapterFlight myFlightDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flight_db_test);
		
		openDB();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		closeDB();
	}


	private void openDB() {
		myFlightDB = new DBAdapterFlight(this);
		myFlightDB.open();
		
	}
	private void closeDB() {
		myFlightDB.close();
	}

	
	
	private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.textDisplay);
        textView.setText(message);
	}
	
	

	public void onClick_AddRecord(String date, String cityfrom, String cityto, String flightInfo) {
//		displayText("Clicked add record!");
		
		long newId = myFlightDB.insertRow(date, cityfrom, cityto, flightInfo);
		// Query for the record we just added.
		// Use the ID:
//		Cursor cursor = myFlightDB.getRow(newId);
//		displayRecordSet(cursor);
	}

	
	public void onClick_ClearAll(View v) {
		myFlightDB.deleteAll();
	}

	
	
	public void onClick_DisplayRecords(View v) {
		displayText("Clicked display record!");
		
		Cursor cursor = myFlightDB.getAllRows();
		displayRecordSet(cursor);
	}
	
	// Display an entire recordset to the screen.
	private void displayRecordSet(Cursor cursor) {
		String message = "";
		// populate the message from the cursor
		
		// Reset cursor to start, checking to see if there's data:
		if (cursor.moveToFirst()) {
			do {
				// Process the data:
				int id = cursor.getInt(DBAdapterFlight.COL_ROWID);
				String date = cursor.getString(DBAdapterFlight.COL_DATE);
				String fromPlace = cursor.getString(DBAdapterFlight.COL_FROMPLACE);
				String toPlace = cursor.getString(DBAdapterFlight.COL_TOPLACE);
				String flightInfo = cursor.getString(DBAdapterFlight.COL_FLIGHTINFO);
				// Append data to the message:
				message += date
						   +", 从: " + fromPlace
						   +", 到: " + toPlace
						   +", 信息: " + flightInfo
						   +"\n";
			} while(cursor.moveToNext());
		}
		
		// Close the cursor to avoid a resource leak.
		cursor.close();
		
		displayText(message);
	}
}










