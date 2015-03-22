package ma.exampl.imagineapp.activity;

import java.util.ArrayList;

import ma.exampl.imagineapp.R;
import ma.exampl.imagineapp.persistence.SharedPreferencesManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;

public class MainActivity extends Activity implements OnClickListener {
	// ================================================================================

	private Button bStart;
	private Button bSelectLibrary;
	private Button bSelectColor;
	private Button bAbout;
	private ImageView backgroundbuttonStart;
	private Animation animat;
	private LinearLayout layoutLogo;
	private RelativeLayout mainLayout;

	private ListView l1;
	private Dialog dialog;
	private int selectedColorId;
	private ArrayList<Colors> colors;

	
	// ================================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* get selected id for bgColor */
		selectedColorId = SharedPreferencesManager.getSelectedColorValue(this);

		mainLayout = (RelativeLayout) findViewById(R.id.MainActivity_MainLayout);
		mainLayout.setBackgroundResource(selectedColorId);

		/* init colors */
		colors = new ArrayList<Colors>();
		colors.add(new Colors(R.drawable.background, "Blue", Color
				.parseColor("#00BAF2")));
		colors.add(new Colors(R.drawable.background_green, "Green", Color
				.parseColor("#07BA0A")));
		colors.add(new Colors(R.drawable.background_red, "Red", Color
				.parseColor("#FF4D4D")));
		colors.add(new Colors(R.drawable.background_yellow, "Yellow", Color
				.parseColor("#FCF917")));
		colors.add(new Colors(R.drawable.background_pink, "Pink", Color
				.parseColor("#FA7AE3")));

		/* Buttons */
		backgroundbuttonStart = (ImageView) findViewById(R.id.Main_ImageBg_Start);
		animat = AnimationUtils.loadAnimation(this, R.anim.animation_bsync);
		backgroundbuttonStart.startAnimation(animat);

		bStart = (Button) findViewById(R.id.Main_ButtonStart);
		bStart.setOnClickListener(this);
		animat = AnimationUtils.loadAnimation(this,
				R.anim.animation_frame_bstart);
		bStart.startAnimation(animat);

		bSelectLibrary = (Button) findViewById(R.id.MainActivity_buttonSelectLibrary);
		bSelectLibrary.setOnClickListener(this);
		bSelectLibrary.setAnimation(animat);

		bAbout = (Button) findViewById(R.id.MainActivity_buttonAbout);
		bAbout.setOnClickListener(this);
		bAbout.setAnimation(animat);

		bSelectColor = (Button) findViewById(R.id.MainActivity_buttonSelectBgColor);
		bSelectColor.setOnClickListener(this);
		bSelectColor.setAnimation(animat);

		animat = AnimationUtils.loadAnimation(this,
				R.anim.animation_main_logo_show);
		layoutLogo = (LinearLayout) findViewById(R.id.MainActivity_LogoLayout);
		layoutLogo.setAnimation(animat);

		dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.custom_dialog);

		/* listView colors */
		l1 = (ListView) dialog.findViewById(R.id.dialoglist);
		l1.setAdapter(new DataListAdapter(colors));
		l1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DataListAdapter color = (DataListAdapter) l1.getAdapter();
				SharedPreferencesManager.setSelectedColorValue(
						MainActivity.this, color.getColorsID(position));
				mainLayout.setBackgroundResource(SharedPreferencesManager
						.getSelectedColorValue(MainActivity.this));
				dialog.dismiss();

			}
		});

		// ========================

	}

	// ================================================================================
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.Main_ButtonStart:
			startActivity(new Intent(MainActivity.this, TableActivity.class));
			break;

		case R.id.MainActivity_buttonSelectLibrary:
			Log.d("SFIAN", "ok");
			startActivity(new Intent(MainActivity.this,
					SelectLibraryActivity.class));
			break;

		case R.id.MainActivity_buttonSelectBgColor:

			dialog.setTitle("Select Background Color");
			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();

			break;

		case R.id.MainActivity_buttonAbout:

			break;

		}
	}

	// ================================================================================
	// ================================================================================
	public class DataListAdapter extends BaseAdapter {
		// ================================================================================
		private ArrayList<Colors> listColors;

		// ================================================================================
		public DataListAdapter() {
			this.listColors = null;
		}

		// ================================================================================
		public DataListAdapter(ArrayList<Colors> listColors) {
			this.listColors = listColors;
		}

		// ================================================================================
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listColors.size();
		}

		// ================================================================================

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		// ================================================================================

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		// ================================================================================

		public int getColorsID(int position) {
			return listColors.get(position).getIdColor();
		}

		// ================================================================================

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row;
			row = inflater.inflate(R.layout.custom_list_color_select, parent,
					false);
			TextView nom;
			ImageView image;

			nom = (TextView) row.findViewById(R.id.colorName);
			image = (ImageView) row.findViewById(R.id.imageColor);

			nom.setText(listColors.get(position).getNameColor());
			image.setBackgroundColor(listColors.get(position).getBitmapImage());

			return (row);
		}
		// ================================================================================

	}

	// ================================================================================
	private class Colors {
		private int idColor;
		private int bitmapImage;
		private String nameColor;

		public Colors() {

		}

		public Colors(int idColor, String nameColor, int image) {

			this.idColor = idColor;
			this.nameColor = nameColor;
			this.bitmapImage = image;
		}

		public int getIdColor() {
			return idColor;
		}

		public String getNameColor() {
			return nameColor;
		}

		public int getBitmapImage() {
			return bitmapImage;
		}

		public void setBitmapImage(int bitmapImage) {
			this.bitmapImage = bitmapImage;
		}

	}
	// ================================================================================

	// ================================================================================

}
