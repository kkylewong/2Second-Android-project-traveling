package com.widgets;

import com.database.BookingData;
import com.example.R;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Spinner;

public class CheckEnteredCity {

	public static Animation zoominAndMove45, zoominAndMove135, zoominAndMove225, zoominAndMove315;


	public static  void setAnimation(Context theContext, AnimationListener a){
		zoominAndMove45 = AnimationUtils.loadAnimation(theContext,
				R.anim.zoomin_and_move_45degree);
		zoominAndMove45.setAnimationListener(a);
		
		zoominAndMove135 = AnimationUtils.loadAnimation(theContext,
				R.anim.zoomin_and_move_135degree);
		zoominAndMove135.setAnimationListener(a);
		
		zoominAndMove225 = AnimationUtils.loadAnimation(theContext,
				R.anim.zoomin_and_move_225degree);
		zoominAndMove225.setAnimationListener(a);
		
		zoominAndMove315 = AnimationUtils.loadAnimation(theContext,
				R.anim.zoomin_and_move_315degree);
		zoominAndMove315.setAnimationListener(a);
	}
	
	public static String checkEnteredCity(Spinner cityEnterSpinner) {
		String thecity = "";
		boolean aLondon = cityEnterSpinner.getSelectedItem().toString()
				.equals(BookingData.LONDON);
		boolean aRome = cityEnterSpinner.getSelectedItem().toString()
				.equals(BookingData.ROME);
		boolean aAmsterdam = cityEnterSpinner.getSelectedItem().toString()
				.equals(BookingData.AMSTERDAM);
		boolean aCairo = cityEnterSpinner.getSelectedItem().toString()
				.equals(BookingData.CAIRO);
		boolean aParis = cityEnterSpinner.getSelectedItem().toString()
				.equals(BookingData.PARIS);
		boolean aGuangzhou = cityEnterSpinner.getSelectedItem().toString()
				.equals(BookingData.GUANGZHOU);
		boolean aShenzhen = cityEnterSpinner.getSelectedItem().toString()
				.equals(BookingData.SHENZHEN);
		boolean aXianggang = cityEnterSpinner.getSelectedItem().toString()
				.equals(BookingData.XIANGGANG);
		
		if (aLondon) {
			thecity = BookingData.LONDON;
		}
		if (aRome) {
			thecity = BookingData.ROME;
		}
		if (aAmsterdam) {
			thecity = BookingData.AMSTERDAM;
		}
		if (aCairo) {
			thecity = BookingData.CAIRO;
		}
		if (aParis) {
			thecity = BookingData.PARIS;
		}
		
		if (aGuangzhou) {
			thecity = BookingData.GUANGZHOU;
		}
		if (aShenzhen) {
			thecity = BookingData.SHENZHEN;
		}
		if (aXianggang) {
			thecity = BookingData.XIANGGANG;
		}

		return thecity;
	}
	
	public static void checkCityIcon(int cityIconNum, Spinner cityEnterSpinner, ImageButton a){
		
		
		if(checkEnteredCity(cityEnterSpinner)==BookingData.LONDON){
			a.setImageResource(R.drawable.london);
		}
		if(checkEnteredCity(cityEnterSpinner)==BookingData.ROME){
			a.setImageResource(R.drawable.rome);
		}
		if(checkEnteredCity(cityEnterSpinner)==BookingData.CAIRO){
			a.setImageResource(R.drawable.cairo);
		}
		if(checkEnteredCity(cityEnterSpinner)==BookingData.PARIS){
			a.setImageResource(R.drawable.paris);
		}
		if(checkEnteredCity(cityEnterSpinner)==BookingData.AMSTERDAM){
			a.setImageResource(R.drawable.amsterdam);
		}
		
		if(cityIconNum==1){
			a.startAnimation(zoominAndMove45);
		}
		else if(cityIconNum==2){
			a.startAnimation(zoominAndMove135);
		}
		else if(cityIconNum==3){
			a.startAnimation(zoominAndMove225);
		}
		else if(cityIconNum==4){
			a.startAnimation(zoominAndMove315);
		}
		
	}
}
