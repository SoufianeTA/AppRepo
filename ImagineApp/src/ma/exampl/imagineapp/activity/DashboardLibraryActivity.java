package ma.exampl.imagineapp.activity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ma.exampl.imagineapp.R;
import ma.exampl.imagineapp.dao.CategoryDAO;
import ma.exampl.imagineapp.dao.LibraryDAO;
import ma.exampl.imagineapp.dao.RessourceDAO;
import ma.exampl.imagineapp.model.Category;
import ma.exampl.imagineapp.model.Ressource;
import ma.exampl.imagineapp.persistence.SharedPreferencesManager;
import ma.exampl.imagineapp.util.BitmapUtil;
import ma.exampl.imagineapp.util.ConverterUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class DashboardLibraryActivity extends Activity implements
		OnClickListener {

	// ==================================================================================
	private static String LOG_TAG = "SFIAN";

	private LinearLayout linearLayoutAddOrUpdateRessource;
	private Button buttonAddRessource;
	private Button buttonAddCategory;
	private Button buttonUpdateRessource;
	private Button buttonUpdateCategory;
	private Button buttonDeleteRessource;
	private Button buttonDeleteCategory;
	private Button buttonCategorie;
	private Button buttonRemoveLastElement;
	private Button buttonReadSentence;
	private TableLayout tableLayoutRessources;
	private TableLayout tableLayoutCategories;
	private LayoutParams paramButtonCategories;
	private LayoutParams paramtableRowSentence;
	private LayoutParams paramCategoryFrame;
	private TableRow tableRowSentence;
	private RelativeLayout mainLayout;
	private Bitmap bitmapBackground;
	private BitmapDrawable bitmapDrawableBackground;
	private FrameLayout frameLayoutCategory;
	private Animation animat;
	private ImageView imageViewTemp;
	private ImageView imageViewCategorieTemp;
	
	private ImageView imageViewImageUploadResImg;
	private ImageView imageViewImageTakeResImg;
	private ImageView imageViewImageShowResImg;
	private ImageView imageViewImageUploadResSound;
	private ImageView imageViewImageRecordResSound;
	private ImageView imageViewImagePlayResSound;
	
	private Button buttonValiderRessource;
	private Button buttonAnnulerRessource;

	private List<Category> categories;
	private Category category;
	private Ressource ressource;
	private List<Ressource> listeRessources;
	private ArrayList<Ressource> sentenceList = new ArrayList<Ressource>();
	private int[] categoryHistory = new int[50];
	private int categoryHistoryIndex = -1;

	private int selectedLibraryId;
	private int selectedColorId;
	private int imageRessourceSize;
	private int nbrElementsByLine;
	private int LangueDirection;

	private CategoryDAO categotyDao;
	private RessourceDAO ressourceDao;
	private LibraryDAO libraryDao;

	// ==================================================================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customize_library);
		/* instantiate DAO */
		categotyDao = new CategoryDAO(this);
		ressourceDao = new RessourceDAO(this);
		libraryDao = new LibraryDAO(this);

		/* get selected library id FROM shared preferences + other sharedPref */

		selectedLibraryId = getIntent().getIntExtra("idLibrary", 0);
		// imageRessourceSize =
		// SharedPreferencesManager.getImageSizeValue(this);
		imageRessourceSize = (int) ConverterUtil.convertDpToPixel(77, this);

		animat = AnimationUtils.loadAnimation(this, R.anim.animation_ressource);

		selectedColorId = SharedPreferencesManager.getSelectedColorValue(this);
		Log.d(LOG_TAG, String.valueOf(libraryDao
				.getLibraryDirectionById(selectedLibraryId)));

		/* Layout ressources */
		linearLayoutAddOrUpdateRessource=(LinearLayout) findViewById(R.id.Custom_LayoutAddORUpdateRessource);
		tableLayoutCategories = (TableLayout) findViewById(R.id.custom_TableLayoutCategory);
		tableLayoutRessources = (TableLayout) findViewById(R.id.custom_TableLayoutRessources);
		buttonAddCategory = (Button) findViewById(R.id.UpdateLibrairie_ButtonAddCategorie);
		buttonAddRessource = (Button) findViewById(R.id.UpdateLibrairie_ButtonAddRessource);
		buttonUpdateCategory = (Button) findViewById(R.id.UpdateLibrairie_ButtonUpdateCategorie);
		buttonUpdateRessource = (Button) findViewById(R.id.UpdateLibrairie_ButtonUpdateRessource);
		buttonDeleteCategory = (Button) findViewById(R.id.UpdateLibrairie_ButtonDeleteCategorie);
		buttonDeleteRessource = (Button) findViewById(R.id.UpdateLibrairie_ButtonDeleteRessource);
		
		imageViewImageUploadResImg=(ImageView) findViewById(R.id.Custom_leftsideImageUploadResImg);
		imageViewImageTakeResImg=(ImageView) findViewById(R.id.Custom_leftsideImageTakeResImg);
		imageViewImageShowResImg=(ImageView) findViewById(R.id.Custom_leftsideImageShowResImg);
		imageViewImageUploadResSound=(ImageView) findViewById(R.id.Custom_leftsideImageUploadResSound);
		imageViewImageRecordResSound=(ImageView) findViewById(R.id.Custom_leftsideImageRecordResSound);
		imageViewImagePlayResSound=(ImageView) findViewById(R.id.Custom_leftsideImagePlayResSound);
		
		buttonValiderRessource=(Button) findViewById(R.id.Custom_leftsideButtonValiderRessource);
		buttonAnnulerRessource=(Button) findViewById(R.id.Custom_leftsideButtonAnnulerRessource);
		
		imageViewImageUploadResImg.setOnClickListener(this);
		imageViewImageTakeResImg.setOnClickListener(this);
		imageViewImageUploadResSound.setOnClickListener(this);
		imageViewImageRecordResSound.setOnClickListener(this);
		imageViewImagePlayResSound.setOnClickListener(this);
		
		buttonValiderRessource.setOnClickListener(this);
		buttonAnnulerRessource.setOnClickListener(this);

		tableLayoutCategories.setOnClickListener(this);
		tableLayoutRessources.setOnClickListener(this);
		buttonAddCategory.setOnClickListener(this);
		buttonAddRessource.setOnClickListener(this);
		buttonUpdateCategory.setOnClickListener(this);
		buttonUpdateRessource.setOnClickListener(this);
		buttonDeleteCategory.setOnClickListener(this);
		buttonDeleteRessource.setOnClickListener(this);

		
		buttonAddCategory.setEnabled(true);
		buttonUpdateCategory.setEnabled(false);
		buttonDeleteCategory.setEnabled(false);

		buttonAddRessource.setEnabled(true);
		buttonUpdateRessource.setEnabled(false);
		buttonDeleteRessource.setEnabled(false);

		/* nember of Elements in one Tablerow */
		nbrElementsByLine = this.getResources().getDisplayMetrics().widthPixels
				/ imageRessourceSize / 2;

		/* get the default category using library id */
		category = categotyDao.getDefaultCategoryByIdLibrary(selectedLibraryId);

		// -------------- Test ----------------
		// Log.d(LOG_TAG, category.getId()+" - "+category.getCategoryName());
		// ------------ Test END --------------

		showCategories();
		showRessources();
		linearLayoutAddOrUpdateRessource.setVisibility(LinearLayout.GONE);
		// ---------- END onCreate ------------
	}

	// ==================================================================================

	private void showCategories() {
		// TODO Auto-generated method stub

		tableLayoutCategories.removeAllViews();
		categories = categotyDao.getAllCategories(selectedLibraryId);

		int i = 0; // iterator
		while (i < categories.size()) {

			/* instantiate new TableRow */
			TableRow oneTableRow = new TableRow(this);
			oneTableRow.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			int j = 0; // iterator
			while (j < nbrElementsByLine && i < categories.size()) {
				Log.d(LOG_TAG, categories.get(i).getCategoryName());
				final Category category = categories.get(i);
				FrameLayout frameLayoutCategory = new FrameLayout(this);
				final ImageView imageView = new ImageView(this);
				Bitmap resizedbitmap = Bitmap.createScaledBitmap(
						category.getBitmapImage(), imageRessourceSize,
						imageRessourceSize, true);
				imageView.setImageBitmap(resizedbitmap);
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (!(imageViewCategorieTemp == null))
							imageViewCategorieTemp.clearAnimation();
						imageViewCategorieTemp = imageView;
						imageView.startAnimation(animat);

						DashboardLibraryActivity.this.category = category;
						showRessources();

						buttonUpdateRessource.setEnabled(false);
						buttonDeleteRessource.setEnabled(false);

						buttonUpdateCategory.setEnabled(true);
						buttonDeleteCategory.setEnabled(true);

					}
				});

				frameLayoutCategory.addView(imageView);
				oneTableRow.addView(frameLayoutCategory);

				/* increment */
				j++;
				i++;
			}
			// -------- End Of the Loop j -----------

			/* add the TableRow To The TableLayout */
			tableLayoutCategories.addView(oneTableRow,
					new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));

		}
		// -------- End Of the Loop i -----------

	}

	// ==================================================================================
	private void showRessources() {
		tableLayoutRessources.removeAllViews();
		listeRessources = ressourceDao.getRessourcesByCategoryId(category
				.getId());

		int i = 0; // iterator
		while (i < listeRessources.size()) {

			/* instantiate new TableRow */
			TableRow oneTableRow = new TableRow(this);
			oneTableRow.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			int j = 0; // iterator
			while (j < nbrElementsByLine && i < listeRessources.size()) {

				final Ressource ressource = listeRessources.get(i);
				FrameLayout frameLayoutCategory = new FrameLayout(this);
				final ImageView imageView = new ImageView(this);
				Bitmap resizedbitmap = Bitmap.createScaledBitmap(
						ressource.getBitmapImage(), imageRessourceSize,
						imageRessourceSize, true);
				imageView.setImageBitmap(resizedbitmap);
				imageView.setTag(i);
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (!(imageViewTemp == null))
							imageViewTemp.clearAnimation();
						imageViewTemp = imageView;
						imageView.startAnimation(animat);
						
						DashboardLibraryActivity.this.ressource=ressource;
						buttonAddRessource.setEnabled(true);
						buttonUpdateRessource.setEnabled(true);
						buttonDeleteRessource.setEnabled(true);
					}
				});
				frameLayoutCategory.addView(imageView);
				oneTableRow.addView(frameLayoutCategory);

				/* increment */
				j++;
				i++;
			}
			// -------- End Of the Loop j -----------

			/* add the TableRow To The TableLayout */
			tableLayoutRessources.addView(oneTableRow,
					new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT));

		}
		// -------- End Of the Loop i -----------
	}

	// ==================================================================================
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.UpdateLibrairie_ButtonDeleteCategorie:
			deleteCategory();
//			linearLayoutAddOrUpdateRessource.setVisibility(LinearLayout.VISIBLE);
			break;
			
		case R.id.UpdateLibrairie_ButtonDeleteRessource:
			deleteRessource();
//			linearLayoutAddOrUpdateRessource.setVisibility(LinearLayout.GONE);
			break;

		case R.id.Custom_leftsideImageTakeResImg :
			
			Intent takePictureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
				startActivityForResult(takePictureIntent, 0);
			}
			break;
		}

	}
	// ==================================================================================
	private void deleteCategory() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					if (DashboardLibraryActivity.this.category
							.getCategoryName().equals("Default"))
						Toast.makeText(getApplicationContext(),
								"You can not delete the default category",
								Toast.LENGTH_SHORT).show();
					else {
						
						categotyDao.deleteCategory(category.getId());
						category=categotyDao.getDefaultCategoryByIdLibrary(selectedLibraryId);
						tableLayoutRessources.removeAllViews();
						
					}
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(
				DashboardLibraryActivity.this);
		builder.setMessage("Are you sure?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener)
				.show();
		
		
		
	}
	// ==================================================================================
	private void deleteRessource() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
						if(!(ressource==null)){
							ressourceDao.deleteRessource(ressource.getId());
							showRessources();
							Toast.makeText(getApplicationContext(),
									"Ressource Deleted",
									Toast.LENGTH_SHORT).show();
						}
					
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(
				DashboardLibraryActivity.this);
		builder.setMessage("Are you sure?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener)
				.show();
		
		buttonAddRessource.setEnabled(true);
		buttonUpdateRessource.setEnabled(false);
		buttonDeleteRessource.setEnabled(false);
	}
	// ==================================================================================
	// ==================================================================================
	// ==================================================================================
	// ==================================================================================
	// ================================================================================
}
