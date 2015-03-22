package ma.exampl.imagineapp.activity;

import java.nio.IntBuffer;
import java.util.ArrayList;

import ma.exampl.imagineapp.R;
import ma.exampl.imagineapp.persistence.SharedPreferencesManager;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class SelectBgColorActivity extends Activity {
	// ================================================================================

	private ListView l1;

	private int selectedColorId;
	private ArrayList<Colors> colors;

	// ================================================================================
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_bgcolor);
		// ------------------------

		/* get selected library id */
		selectedColorId = SharedPreferencesManager.getSelectedColorValue(this);

		colors = new ArrayList<SelectBgColorActivity.Colors>();
		colors.add(new Colors(R.drawable.background, "Blue",Color.parseColor("#00BAF2")));
		colors.add(new Colors(R.drawable.background_green, "Green",Color.parseColor("#07BA0A")));
		colors.add(new Colors(R.drawable.background_red, "Red",Color.parseColor("#FF4D4D")));
		colors.add(new Colors(R.drawable.background_yellow, "Yellow",Color.parseColor("#FCF917")));
//		colors.add(new Colors(R.drawable.background_pink, "Pink",BitmapFactory.decodeResource(getResources(), R.drawable.background_pink)));

		l1 = (ListView) findViewById(R.id.SelectBgColor_listViewProd);
		l1.setAdapter(new DataListAdapter(colors));
		l1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DataListAdapter color = (DataListAdapter) l1.getAdapter();
				SharedPreferencesManager.setSelectedColorValue(
						SelectBgColorActivity.this, color.getColorsID(position));
				SelectBgColorActivity.this.finish();
				// Log.d("SFIAN", "ok - "+library.getColorsID(position));

			}
		});

		// ========================
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
			TextView nom, selected;
			ImageView image;
			
			nom = (TextView) row.findViewById(R.id.colorName);
			selected = (TextView) row.findViewById(R.id.selected);
			image = (ImageView) row.findViewById(R.id.imageColor);
			nom.setText(listColors.get(position).getNameColor());
			//image.setImageResource(listColors.get(position).getIdColor());
			
			image.setBackgroundColor(listColors.get(position).getBitmapImage());
			 Log.d("SFIAN", "ok - "+position+listColors.get(position).getNameColor()+listColors.get(position).getIdColor());
//			image.setImageResource(listColors.get(position).getIdColor());
//			Bitmap bm = BitmapFactory.decodeResource(getResources(), listColors.get(position).getIdColor());
			//image.setImageBitmap(listColors.get(position).getBitmapImage());
//			((BitmapDrawable)image.getDrawable()).getBitmap().recycle();
			if (selectedColorId == listColors.get(position).getIdColor())
				selected.setText(" - SELECTED");

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

		public Colors(int idColor, String nameColor,int image) {

			this.idColor = idColor;
			this.nameColor = nameColor;
			this.bitmapImage=image;
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

}
