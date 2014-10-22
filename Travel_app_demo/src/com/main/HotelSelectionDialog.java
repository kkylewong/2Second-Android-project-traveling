package com.main;



import com.example.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/*
 * user-defined dialog
 * this dialog will be showed when empty tile is clicked
 */
public class HotelSelectionDialog extends Dialog {
	// used to save the button objectives to represent dialog

	// second parameter: used numbers
	public HotelSelectionDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setTitle("Change a hotel:");
		setContentView(R.layout.hotel_selection_dialog);
		findViews();
		

	}

	private void findViews() {
		
	
	}

	

}
