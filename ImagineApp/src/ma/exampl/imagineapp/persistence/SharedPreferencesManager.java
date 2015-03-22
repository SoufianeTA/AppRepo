package ma.exampl.imagineapp.persistence;

import ma.exampl.imagineapp.R;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
	// ==================================================================================

	private static final String APP_SETTINGS = "APP_SETTINGS";

	private static final String SELECTED_LIBRARY = "SELECTED_LIBRARY";
	private static final String SELECTED_COLOR = "SELECTED_COLOR";
	private static final String IMAGE_SIZE = "IMAGE_SIZE";

	// ==================================================================================
	private SharedPreferencesManager() {
	}

	// ==================================================================================

	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
	}

	// ==================================================================================

	public static int getSelectedLibraryValue(Context context) {
		return getSharedPreferences(context).getInt(SELECTED_LIBRARY, 1);
	}

	// ==================================================================================

	public static void setSelectedLibraryValue(Context context, int newValue) {
		final SharedPreferences.Editor editor = getSharedPreferences(context)
				.edit();
		editor.putInt(SELECTED_LIBRARY, newValue);
		editor.commit();
	}

	// ==================================================================================

	public static int getSelectedColorValue(Context context) {
		return getSharedPreferences(context).getInt(SELECTED_COLOR, R.drawable.background);
	}

	// ==================================================================================

	public static void setSelectedColorValue(Context context, int newValue) {
		final SharedPreferences.Editor editor = getSharedPreferences(context)
				.edit();
		editor.putInt(SELECTED_COLOR, newValue);
		editor.commit();
	}

	// ==================================================================================

	public static int getImageSizeValue(Context context) {
		return getSharedPreferences(context).getInt(IMAGE_SIZE, 200);
	}

	// ==================================================================================

	public static void setImageSizeValue(Context context, int newValue) {
		final SharedPreferences.Editor editor = getSharedPreferences(context)
				.edit();
		editor.putInt(IMAGE_SIZE, newValue);
		editor.commit();
	}
	// ==================================================================================
	// ==================================================================================
	// ==================================================================================

}
