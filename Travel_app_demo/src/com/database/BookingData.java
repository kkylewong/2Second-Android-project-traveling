package com.database;



public class BookingData {
	public static String flightOne = "Southern Airline，12:05-22:50,￥3550"; 
	public static String flightTwo = "Southern Airline，09:25-20:12,￥4200";
	
	public static final String LONDON="London";
	public static final String CAIRO="Cairo";
	public static final String AMSTERDAM="Amsterdam";
	public static final String PARIS="Paris";
	public static final String ROME="Rome";
	
	public static final String GUANGZHOU="Guangzhou";
	public static final String SHENZHEN="Shenzhen";
	public static final String XIANGGANG="Hongkong";
	
	public static final String LONDON_HOTEL_DEFAULT="Epsilon Hotel,￥746";
	public static final String LONDON_HOTEL_1="Oxford Hotel,￥709";
	public static final String LONDON_HOTEL_2="Hotel 65, ￥746";
	public static final String LONDON_HOTEL_3="Hotel Lily, ￥705";
	public static final String LONDON_HOTEL_4="St Mark Hotel, ￥657";
	
	String A ="艾普西龙酒店 牛津酒店 65酒店 百合酒店 圣马克酒店 ";
	
	public static final String ROME_HOTEL_DEFAULT="St Mark Hotel, ￥657";
	public static final String ROME_HOTEL_1="";
	public static final String ROME_HOTEL_2="";
	public static final String ROME_HOTEL_3="";
	public static final String ROME_HOTEL_4="";
	
	public static final String AMESTERDAM_HOTEL_DEFAULT="Hotel Lily, ￥705";
	public static final String AMESTERDAM_HOTEL_1="";
	public static final String AMESTERDAM_HOTEL_2="";
	public static final String AMESTERDAM_HOTEL_3="";
	public static final String AMESTERDAM_HOTEL_4="";
	
	public static final String CAIRO_HOTEL_DEFAULT="Hotel 65, ￥746";
	public static final String CAIRO_HOTEL_1="";
	public static final String CAIRO_HOTEL_2="";
	public static final String CAIRO_HOTEL_3="";
	public static final String CAIRO_HOTEL_4="";
	
	public static final String PARIS_HOTEL_DEFAULT="Oxford Hotel,￥709";
	public static final String PARIS_HOTEL_1="";
	public static final String PARIS_HOTEL_2="";
	public static final String PARIS_HOTEL_3="";
	public static final String PARIS_HOTEL_4="";
	
	
	public static String returnFlight(int a){
		String b="";
		if(a==1){
			b= flightOne;
		}if(a==2){
			b= flightTwo;
		}
		return b;
	}
	
	public static String returnCityDefaultHotel(String city){
		String defualtHotel ="";
		
			switch (city) {
			case LONDON:
				defualtHotel = LONDON_HOTEL_DEFAULT;
				break;
			case ROME:
				defualtHotel = ROME_HOTEL_DEFAULT;
				break;
			case AMSTERDAM:
				defualtHotel = AMESTERDAM_HOTEL_DEFAULT;
				break;
			case CAIRO:
				defualtHotel = CAIRO_HOTEL_DEFAULT;
				break;
			case PARIS:
				defualtHotel = PARIS_HOTEL_DEFAULT;
				break;
			}
		
		return  defualtHotel;
	}
}