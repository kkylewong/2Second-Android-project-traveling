package com.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.database.DBAdapterFlight;
import com.database.DBAdapterHotel;
import com.database.FlightDatabaseList;
import com.database.HotelDatabaseList;
import com.example.R;
import com.widgets.BtnSingleClick;
import com.widgets.CheckEnteredCity;
import com.widgets.FlyOutContainer;


public class MainActivity extends Activity implements AnimationListener,
		OnClickListener {

	Animation animRotate,imageCityBtnAnim;
	Animation animZoom_in, zoominAndMove45, zoominAndMove135,
			zoominAndMove225, zoominAndMove315;
	private ImageView imageEarth;
	private ImageButton imageBtnMagnifier;
	private ImageView imageHorse;
	private AnimationDrawable animationDrawable;
	private Button dateSelectBtn,resetDateBtn,finishAddCity;

	static final int DATE_DIALOG_ID = 999;
	private static final int DATE_PICKER_ID = 123;

	private TextView departDatetextView;

	// DateFormat format = DateFormat.getDateInstance();
	// DateFormat format;
	SimpleDateFormat format;
	Calendar calender = Calendar.getInstance();

	FlyOutContainer root;

	// private TextView cityEnterlabelTextView, dayEnterLabelTextView;
	private EditText dayEnterEditText;
	// cityEnterEditText
	private Spinner destinationEnterSpinner,departCityEnterSpinner;

	// private DateCalculator dateCal = new DateCalculator();

	private String selectedDate;
	// private String changingSelectedDate;
	private Long firstFlightID, secondFlightID, lastHotelID;
	private String lastCity;

//	private EditText departCityEnterEditText;

	private TextView scheduleDate, scheduleFromCity, scheduleToCity,
			scheduleFlightInfo;
	private TextView scheduleDateBACK, scheduleFromCityBACK,
			scheduleToCityBACK, scheduleFlightInfoBACK;
	private String firstCity = "";

	int mYear, mMonth, mDay;

	DBAdapterHotel myHotelDb;
	DBAdapterFlight myFlightDB;

	private ImageButton imageButtonCityIcon, imageButtonCityIcon2,
			imageButtonCityIcon3, imageButtonCityIcon4;
	int cityIconNum = 1;

	private Button bookingGeneratorButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// format
		// =android.text.format.DateFormat.getDateFormat(getApplicationContext());
		format = new SimpleDateFormat("yyyy-MM-dd");
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_sample, null);

		this.setContentView(root);
		setViewAndButton();

		setAnimation();

		openDB();

		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		populateListViewFromDB();
		// imageButtonTest.setImageResource(R.drawable.london);
		registerListClickCallback();//set listView clickable	
	}

	private void setViewAndButton() {
		imageEarth = (ImageView) findViewById(R.id.imageEarth);
		imageBtnMagnifier = (ImageButton) findViewById(R.id.imageBtnMagfinier);
		imageHorse = (ImageView) findViewById(R.id.imageHorse);
		imageBtnMagnifier.setOnClickListener(this);

		dateSelectBtn = (Button) findViewById(R.id.dateSelectBtn);
		dateSelectBtn.setOnClickListener(this);
		
		resetDateBtn= (Button) findViewById(R.id.resetDateBtn);
		resetDateBtn.setOnClickListener(this);
		
		finishAddCity= (Button) findViewById(R.id.finishAddCity);
		finishAddCity.setOnClickListener(this);
		
		departDatetextView = (TextView) findViewById(R.id.departDatetextView);
		dayEnterEditText = (EditText) findViewById(R.id.dayEnterEditText);
//		departCityEnterEditText = (EditText) findViewById(R.id.departCityEnterEditText);

		scheduleDate = (TextView) findViewById(R.id.scheduleDate);
		scheduleFromCity = (TextView) findViewById(R.id.scheduleFromCity);
		scheduleToCity = (TextView) findViewById(R.id.scheduleToCity);
		scheduleFlightInfo = (TextView) findViewById(R.id.scheduleFlightInfo);

		scheduleDateBACK = (TextView) findViewById(R.id.scheduleDateBACK);
		scheduleFromCityBACK = (TextView) findViewById(R.id.scheduleFromCityBACK);
		scheduleToCityBACK = (TextView) findViewById(R.id.scheduleToCityBACK);
		scheduleFlightInfoBACK = (TextView) findViewById(R.id.scheduleFlightInfoBACK);

		destinationEnterSpinner = (Spinner) findViewById(R.id.destinationEnterSpinner);
		departCityEnterSpinner = (Spinner) findViewById(R.id.departCityEnterSpinner);
		
		imageButtonCityIcon = (ImageButton) findViewById(R.id.imageButtonCityIcon);
		imageButtonCityIcon2 = (ImageButton) findViewById(R.id.imageButtonCityIcon2);
		imageButtonCityIcon3 = (ImageButton) findViewById(R.id.imageButtonCityIcon3);
		imageButtonCityIcon4 = (ImageButton) findViewById(R.id.imageButtonCityIcon4);
		
		bookingGeneratorButton = (Button) findViewById(R.id.bookingGeneratorButton);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}

	private void openDB() {
		myFlightDB = new DBAdapterFlight(this);
		myFlightDB.open();
		myHotelDb = new DBAdapterHotel(this);
		myHotelDb.open();
		populateListViewFromDB();
	}

	private void closeDB() {
		myFlightDB.deleteAll();
		myHotelDb.deleteAll();
		myFlightDB.close();
		myHotelDb.close();
	}

	private void setAnimation() {
		animRotate = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.rotate);
		animRotate.setAnimationListener(this);
		animZoom_in = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.zoom_in);
		animZoom_in.setAnimationListener(this);

		zoominAndMove45 = CheckEnteredCity.zoominAndMove45;
		zoominAndMove135 = CheckEnteredCity.zoominAndMove135;
		zoominAndMove225 = CheckEnteredCity.zoominAndMove225;
		zoominAndMove315 = CheckEnteredCity.zoominAndMove315;

		imageCityBtnAnim= AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.city_icon_scale);
		imageCityBtnAnim.setAnimationListener(this);
		CheckEnteredCity.setAnimation(getApplicationContext(), this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sample, menu);
		return true;
	}

	public void toggleMenu(View v) {
		this.root.toggleMenu();
	}

	private void start_animation(int id) {
		imageHorse.setImageResource(id);
		animationDrawable = (AnimationDrawable) imageHorse.getDrawable();
		animationDrawable.start();
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation == animRotate) {
//			Toast.makeText(getApplicationContext(), "Animation Stopped",
//					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	private String checkEnteredCity(Spinner spinner) {
		return CheckEnteredCity.checkEnteredCity(spinner);
	}

	private void imageBtnMagnfier_clicked() {
		finishAddCity.setVisibility(View.VISIBLE);
		if (cityIconNum < 5) {
			if (cityIconNum == 1) {
				CheckEnteredCity.checkCityIcon(cityIconNum, destinationEnterSpinner,
						imageButtonCityIcon);
			}
			if (cityIconNum == 2) {
				CheckEnteredCity.checkCityIcon(cityIconNum, destinationEnterSpinner,
						imageButtonCityIcon2);
			}
			if (cityIconNum == 3) {
				CheckEnteredCity.checkCityIcon(cityIconNum, destinationEnterSpinner,
						imageButtonCityIcon3);
			}
			if (cityIconNum == 4) {
				CheckEnteredCity.checkCityIcon(cityIconNum, destinationEnterSpinner,
						imageButtonCityIcon4);
			}

			if (myHotelDb.getAllRows().getCount() < 1) {
				myFlightDB.updateDepartToCity(firstFlightID, checkEnteredCity(destinationEnterSpinner));
			}
			imageEarth.startAnimation(animRotate);
			start_animation(R.anim.horse_animation);//horse animation
			start_animation(R.anim.horse_animation_2);//horse animation
			imageBtnMagnifier.startAnimation(animZoom_in);

			// String thecity = cityEnterEditText.getText().toString();

			String thenumDay = dayEnterEditText.getText().toString();
			
			
				int nDay = Integer.parseInt(thenumDay);
				for (int y = 0; y < nDay; y++) {
					// theDate =
					// DateCalculator.getNextFewDays(changingSelectedDate,y +
					// 1);
					// lastHotelID = myHotelDb.insertRow(theDate,
					// checkEnteredCity(),"");
					lastHotelID = myHotelDb.insertRow("", checkEnteredCity(destinationEnterSpinner), "");
					lastCity = checkEnteredCity(destinationEnterSpinner);
				}
				myHotelDb.updateAll_Date(selectedDate);
				// changingSelectedDate = theDate;
				flightScheduleSetText();
				populateListViewFromDB();

				cityIconNum += 1;
			
			// System.out.println("sampleActivity--->num of day--->" + nDay);

			// Long oneHotelID = myHotelDb.insertRow(date, theCity, hotelInfo);

			// String theDate = "";

			
		} else {
			Toast.makeText(getApplicationContext(), "只能添加四个城市",
					Toast.LENGTH_SHORT).show();
		}
		dayEnterEditText.setText("");
		// imageButtonCityIcon.setImageResource(R.drawable.cairo);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.imageBtnMagfinier:
			if (!BtnSingleClick.isFastDoubleClick()) {
				String thenumDay = dayEnterEditText.getText().toString();
				
				if(thenumDay.equals("")){
					Toast.makeText(getApplicationContext(), "请输入天数",
							Toast.LENGTH_SHORT).show();
				}else{
					imageBtnMagnfier_clicked();
					insertDepartDateToFLightDataBase();
				}
				
			}
			break;
		case R.id.dateSelectBtn:
			setDate();
			break;
		case R.id.resetDateBtn:
			resetDate();
			break;
		case R.id.finishAddCity:
			finishAddCity();
			break;
		case R.id.imageButtonCityIcon:
			imageButtonCityIcon.startAnimation(imageCityBtnAnim);
			break;
		case R.id.imageButtonCityIcon2:
			imageButtonCityIcon2.startAnimation(imageCityBtnAnim);
			break;
		case R.id.imageButtonCityIcon3:
			imageButtonCityIcon3.startAnimation(imageCityBtnAnim);
			break;
		case R.id.imageButtonCityIcon4:
			imageButtonCityIcon4.startAnimation(imageCityBtnAnim);
			break;
			
			
		}

	}

	// go flight button action
	public void goFlight(View v) {
		Intent intent = new Intent(this, FlightDatabaseList.class);
		startActivity(intent);
	}

	// go hotel button action
	public void goHotel(View v) {
		Intent intent = new Intent(this, HotelDatabaseList.class);
		startActivity(intent);
	}

	public String updateDateLabel() {
		String a = format.format(calender.getTime());
		departDatetextView.setText("Depart:" + a);

		// String b = dateCal.getNextDay(a, "3");
		System.out.println("SampleActivity---> date format ----> " + a);
		// System.out.println("SampleActivity---> selectedDate format ----> " +
		// selectedDate);
		// System.out.println("SampleActivity---> date format Trans ----> " +
		// b);
		// imageBtnMagfinier.setVisibility(View.VISIBLE);
		return a;
	}

	private void setMagnifierVisible() {
		imageBtnMagnifier.setVisibility(View.VISIBLE);
		// cityEnterlabelTextView.setVisibility(View.VISIBLE);
		// dayEnterLabelTextView.setVisibility(View.VISIBLE);
		// cityEnterEditText.setVisibility(View.VISIBLE);
		dayEnterEditText.setVisibility(View.VISIBLE);
		destinationEnterSpinner.setVisibility(View.VISIBLE);
//		departCityEnterEditText.setVisibility(View.INVISIBLE);
		departCityEnterSpinner.setVisibility(View.INVISIBLE);
		dateSelectBtn.setVisibility(View.INVISIBLE);

	}

	private void insertDepartDateToFLightDataBase() {
		if (firstFlightID == null) {
//			String departCity = departCityEnterEditText.getText().toString();
			String departCity=CheckEnteredCity.checkEnteredCity(departCityEnterSpinner);
			firstFlightID = myFlightDB.insertRow(selectedDate, departCity,
					firstCity, "");
			secondFlightID = myFlightDB.insertRow("", "", departCity, "");

			// depart flight text
			myFlightDB.getStringDataDEPART(firstFlightID);
			scheduleDate.setText(myFlightDB.scheduleDate);
			scheduleFromCity.setText(myFlightDB.scheduleFromPlace);
			scheduleToCity.setText(myFlightDB.scheduleToPlace);
			scheduleFlightInfo.setText(myFlightDB.scheduleFlightInfo);

			// return flight text
			myFlightDB.getStringDataBACK(secondFlightID);
			scheduleDateBACK.setText(myFlightDB.scheduleDate_back);
			scheduleFromCityBACK.setText(myFlightDB.scheduleFromPlace_back);
			scheduleToCityBACK.setText(myFlightDB.scheduleToPlace_back);
			
			scheduleFlightInfoBACK.setText(myFlightDB.scheduleFlightInfo_back);
		} else {
			myFlightDB.updateRow_onlyDate(firstFlightID, selectedDate);
			System.out.println("SampleActivity--->first date is inserted");
			myHotelDb.updateAll_Date(selectedDate);

			if(myHotelDb.getAllRows().moveToFirst()==true){
				String lastDate = myHotelDb.getLastHotelDate_date(lastHotelID);
				System.out.println("SampleActivity---lastDate--->" + lastDate);
				myFlightDB.updateReturnFlightFromCity_date(secondFlightID, lastCity,
						lastDate);
			}
			
			// update the data from DBAdapterHotel.java here
			// theDate = DateCalculator.getNextFewDays(addedSelectedDate, y +
			// 1);

		}
		flightScheduleSetText();
		populateListViewFromDB();
	}

	private void flightScheduleSetText() {
		myFlightDB.getStringDataDEPART(firstFlightID);
		scheduleDate.setText(myFlightDB.scheduleDate);
		scheduleFromCity.setText(myFlightDB.scheduleFromPlace);
		scheduleToCity.setText(myFlightDB.scheduleToPlace);
		scheduleFlightInfo.setText(myFlightDB.scheduleFlightInfo);

		myFlightDB.getStringDataBACK(secondFlightID);
		scheduleDateBACK.setText(myFlightDB.scheduleDate_back);
		scheduleFromCityBACK.setText(myFlightDB.scheduleFromPlace_back);
		scheduleToCityBACK.setText(myFlightDB.scheduleToPlace_back);
		scheduleFlightInfoBACK.setText(myFlightDB.scheduleFlightInfo_back);
	}

	public void setDate() {
		showDialog(DATE_PICKER_ID);
		// this button will only be clicked once forever during the whole
		// process.
	}

	public void resetDate() {
		setDate();
		
		flightScheduleSetText();
		populateListViewFromDB();
	}

	public void bookingGenerator(View v) {
		myHotelDb.insertAllHotelInfo();
		
		myFlightDB.insertFirstFlightInfo(firstFlightID);
		myFlightDB.insertSecondFlightInfo(secondFlightID);
		flightScheduleSetText();
		populateListViewFromDB();
		this.root.toggleMenu();
	}
	
//	123

	public void finishAddCity() {
		String lastDate = myHotelDb.getLastHotelDate_date(lastHotelID);
		System.out.println("SampleActivity---lastDate--->" + lastDate);
		myFlightDB.updateReturnFlightFromCity_date(secondFlightID, lastCity,
				lastDate);
		flightScheduleSetText();
		populateListViewFromDB();

		Toast.makeText(getApplicationContext(), "所有行程已准备好", Toast.LENGTH_SHORT)
				.show();
		bookingGeneratorButton.setVisibility(View.VISIBLE);
		dayEnterEditText.setVisibility(View.INVISIBLE);
		destinationEnterSpinner.setVisibility(View.INVISIBLE);
		
		imageBtnMagnifier.setVisibility(View.INVISIBLE);
		imageBtnMagnifier.setEnabled(false);
		
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:

			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listner to date picker
			return new DatePickerDialog(this, pickerListener, mYear, mMonth,
					mDay);
		}
		return null;
		
	}

	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selcYear, int selcMonth,
				int selcDay) {
			int year, month, day;
			year = selcYear;
			month = selcMonth + 1;// 不知为何，选中的月份会小一个月，所以加1
			day = selcDay;

			selectedDate = year + "-" + month + "-" + day;
			// changingSelectedDate = selectedDate;
			insertDepartDateToFLightDataBase();
			departDatetextView.setText("Depart:" + selectedDate);
			setMagnifierVisible();
			resetDateBtn.setVisibility(View.VISIBLE);
		}
	};
	
	private void registerListClickCallback() {
		ListView myList = (ListView) findViewById(R.id.hotelListViewFromDB);
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, 
					int position, long idInDB) {

				Toast.makeText(getApplicationContext(), "clicked",
						Toast.LENGTH_SHORT).show();
				HotelSelectionDialog hotelDialog = new HotelSelectionDialog(MainActivity.this);
				
				hotelDialog.show();
			}
		});
	}

	private void populateListViewFromDB() {
		Cursor cursor = myHotelDb.getAllRows();

		// Allow activity to manage lifetime of the cursor.
		// DEPRECATED! Runs on the UI thread, OK for small/short queries.
		startManagingCursor(cursor);

		// Setup mapping from cursor to view fields:
		String[] fromFieldNames = new String[] { DBAdapterHotel.KEY_DATE,
				DBAdapterHotel.KEY_CITYICON, DBAdapterHotel.KEY_THECITY,
				DBAdapterHotel.KEY_HOTELINFO };
		int[] toViewIDs = new int[] { R.id.item_name, R.id.item_icon,
				R.id.item_favcolour, R.id.item_studentnum };

		// Create adapter to may columns of the DB onto elemesnt in the UI.
		SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, // Context
				R.layout.hotel_item_list, // Row layout template
				cursor, // cursor (set of DB records to map)
				fromFieldNames, // DB Column names
				toViewIDs // View IDs to put information in
		);

		// Set the adapter for the list view
		ListView myList = (ListView) findViewById(R.id.hotelListViewFromDB);
		myList.setAdapter(myCursorAdapter);
	}

	// 暂时抛弃以下
	public void setDate2(View v) {
		// MyDatePickerDialog mydatepicker = new
		// MyDatePickerDialog(SampleActivity.this, d,
		// calender.get(Calendar.YEAR),
		// calender.get(Calendar.MONTH),
		// calender.get(Calendar.DAY_OF_MONTH));
		// mydatepicker.show();
		DatePickerDialog mydatepicker = new DatePickerDialog(
				MainActivity.this, myDatePickerdialog,
				calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),
				calender.get(Calendar.DAY_OF_MONTH));
		mydatepicker.setCancelable(false);
		mydatepicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (which == DialogInterface.BUTTON_POSITIVE) {
							updateDateLabel();
							// selectedDate =
							// departDatetextView.getText().toString();
							setMagnifierVisible();
							insertDepartDateToFLightDataBase();
						}
					}
				});

		// mydatepicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "取消",
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog,
		// int which) {
		// if (which == DialogInterface.BUTTON_NEGATIVE) {
		// dialog.cancel();
		//
		// }
		// }
		// });
		mydatepicker.show();
	}

	DatePickerDialog.OnDateSetListener myDatePickerdialog = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker arg0, int year, int month, int day) {

			calender.set(Calendar.YEAR, year);
			calender.set(Calendar.MONTH, month);
			calender.set(Calendar.DAY_OF_MONTH, day);
			updateDateLabel();
			System.out.println("updatehere");

		}
	};

}