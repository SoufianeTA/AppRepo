
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
import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.LinearLayout.LayoutParams;

public class TableActivity extends Activity implements
		View.OnLongClickListener, OnClickListener, OnDragListener {
	// ==================================================================================
	private static String LOG_TAG = "SFIAN";

	private LinearLayout linearLayoutCategory;
	private Button buttonCategoryBack;
	private Button buttonCategorie;
	private Button buttonRemoveLastElement;
	private Button buttonReadSentence;
	private TableLayout tableLayoutRessources;
	private LayoutParams paramButtonCategories;
	private LayoutParams paramtableRowSentence;
	private TableRow tableRowSentence;
	private RelativeLayout mainLayout;
	private Bitmap bitmapBackground;
	private BitmapDrawable bitmapDrawableBackground;

	private List<Category> categories;
	private Category category;
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

		/* instantiate DAO */
		categotyDao = new CategoryDAO(this);
		ressourceDao = new RessourceDAO(this);
		libraryDao=new LibraryDAO(this);
		
		/* get selected library id FROM shared preferences + other sharedPref */

		selectedLibraryId = SharedPreferencesManager
				.getSelectedLibraryValue(this);
//		imageRessourceSize = SharedPreferencesManager.getImageSizeValue(this);
		imageRessourceSize = (int) ConverterUtil.convertDpToPixel(77, this);
		
		selectedColorId = SharedPreferencesManager.getSelectedColorValue(this);
		 Log.d(LOG_TAG,String.valueOf(libraryDao.getLibraryDirectionById(selectedLibraryId)));
		LangueDirection = libraryDao.getLibraryDirectionById(selectedLibraryId);

		/* set layout */
		if (LangueDirection == 1) {
			setContentView(R.layout.activity_table_left);
			mainLayout = (RelativeLayout) findViewById(R.id.TableActivity_MainLayoutLeft);
			
		} else {
			setContentView(R.layout.activity_table);
			mainLayout = (RelativeLayout) findViewById(R.id.TableActivity_MainLayout);
		}

		/* set bg Color */
		setBackground();
		
		/* Layout ressources */
		linearLayoutCategory = (LinearLayout) findViewById(R.id.TableActivity_LinearLayoutCategory);
		tableLayoutRessources = (TableLayout) findViewById(R.id.TableActivity_TableLayoutRessources);
		tableRowSentence = (TableRow) findViewById(R.id.TableActivity_TableRowSentence);
		if (LangueDirection == 1)
			tableRowSentence.setGravity(Gravity.RIGHT);
		buttonCategoryBack = (Button) findViewById(R.id.TableActivity_buttonCategoryBack);
		buttonCategoryBack.setOnClickListener(this);
		buttonRemoveLastElement = (Button) findViewById(R.id.TableActivity_ButtonRemoveLastElement);
		buttonRemoveLastElement.setOnClickListener(this);
		buttonReadSentence = (Button) findViewById(R.id.TableActivity_ButtonRead);
		buttonReadSentence.setOnClickListener(this);

		/* set on drag listener for the table layout sentence */
		findViewById(R.id.TableActivity_TableLayoutSentence).setOnDragListener(
				this);

		/* Layout params */
		paramButtonCategories = new LayoutParams(LayoutParams.MATCH_PARENT, 140);

		/* nember of Elements in one Tablerow */
		nbrElementsByLine = this.getResources().getDisplayMetrics().widthPixels
				/ imageRessourceSize;


		/* get the default category using library id */
		category = categotyDao.getDefaultCategoryByIdLibrary(selectedLibraryId);

		// -------------- Test ----------------
		// Log.d(LOG_TAG, category.getId()+" - "+category.getCategoryName());
		// ------------ Test END --------------

		categoryHistoryIndex++;
		categoryHistory[categoryHistoryIndex] = category.getId();
		selectCategory(categoryHistory[categoryHistoryIndex]);

		// ---------- END onCreate ------------
	}

	// ==================================================================================

	private View.OnClickListener getOnClickChooseCategory(
			Button buttonCategorie2, final int idCategory) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				// -------------- Test ----------------
				// Log.d(LOG_TAG, String.valueOf(idCategory));
				// ------------ Test END --------------
				categoryHistoryIndex++;
				categoryHistory[categoryHistoryIndex] = idCategory;
				selectCategory(idCategory);
			}
		};
	}

	// ==================================================================================
	public void selectCategory(int categoryID) {

		// -------------- Test ----------------
		// Log.d(LOG_TAG, String.valueOf(categoryHistory.length));
		// Log.d(LOG_TAG,
		// String.valueOf(ressource.getId())+" - "+ressource.getRessouceName());
		// ------------ Test END --------------

		category = categotyDao.getCategoryById(categoryID);

		linearLayoutCategory.removeAllViews();
		tableLayoutRessources.removeAllViews();

		/* get categories */
		categories = categotyDao.getCategoriesByCategoryId(category.getId());

		// -------------- Test ----------------
		// for (Iterator iterator = categories.iterator(); iterator.hasNext();)
		// {
		// Category category = (Category) iterator.next();
		// Log.d(LOG_TAG, category.getId()+" - "+category.getCategoryName());
		// }
		// ------------ Test END --------------

		/* fill the category scrollview */
		for (Iterator iterator = categories.iterator(); iterator.hasNext();) {
			Category category = (Category) iterator.next();

			buttonCategorie = new Button(this);
			buttonCategorie.setText(category.getCategoryName());
			// buttonCategorie.setBackgroundResource(R.drawable.button_menu);
			linearLayoutCategory
					.addView(buttonCategorie, paramButtonCategories);

			buttonCategorie.setOnClickListener(getOnClickChooseCategory(
					buttonCategorie, category.getId()));
		}

		/* Load the ressources */
		listeRessources = ressourceDao.getRessourcesByCategoryId(category
				.getId());

		// -------------- Test ----------------
		// Log.d(LOG_TAG, String.valueOf(listeRessources.size()));
		// ------------ Test END --------------

		/* fill TableLayout with ressources */

		int i = 0; // iterator
		while (i < listeRessources.size()) {

			/* instantiate new TableRow */
			TableRow oneTableRow = new TableRow(this);
			oneTableRow.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			int j = 0; // iterator
			while (j < nbrElementsByLine && i < listeRessources.size()) {

				Ressource ressource = listeRessources.get(i);

				/* instansiate imageView that represent a ressource */
				ImageView imageView = new ImageView(this);
				imageView.setTag(i);
				Bitmap resizedbitmap = Bitmap.createScaledBitmap(
						ressource.getBitmapImage(), imageRessourceSize,
						imageRessourceSize, true);
				imageView.setImageBitmap(resizedbitmap);
				imageView.setOnLongClickListener(TableActivity.this);

				// -------------- Test ----------------
				// Log.d(LOG_TAG, String.valueOf(i));
				// Log.d(LOG_TAG,
				// String.valueOf(ressource.getId())+" - "+ressource.getRessouceName());
				// ------------ Test END --------------

				/* Load the sound of the ressource */
				final MediaPlayer mediaPlayer = new MediaPlayer();
				try {
					FileInputStream fileInputStream = ressource
							.getSoundStream(this);
					mediaPlayer.setDataSource(fileInputStream.getFD());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/* Play sound Onclick Imageview */
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							mediaPlayer.prepare();
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mediaPlayer.start();

					}
				});

				/* add imageView to the TableRow */
				oneTableRow.addView(imageView);

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
	private void readSentence() {

		mNext = 0;
		startNextFile();

		/*
		 * for (Iterator iterator = sentenceList.iterator();
		 * iterator.hasNext();) { Ressource ressource = (Ressource)
		 * iterator.next();
		 * 
		 * final MediaPlayer mediaPlayer = new MediaPlayer(); try {
		 * FileInputStream fileInputStream = ressource .getSoundStream(this);
		 * mediaPlayer.setDataSource(fileInputStream.getFD());
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * try { mediaPlayer.prepare(); } catch (IllegalStateException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * 
		 * Thread thread = new Thread(new Runnable() { public void run() {
		 * mediaPlayer.start(); } }); try { thread.run(); thread.sleep(800); }
		 * catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * }
		 */

	}

	// ==================================================================================

	private int mNext = 0;
	private OnCompletionListener mListener = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			mp.release();
			startNextFile();
		}
	};

	void startNextFile() {
		if (mNext < sentenceList.size()) {
			final MediaPlayer mediaPlayer = new MediaPlayer();
			try {
				FileInputStream fileInputStream = sentenceList.get(mNext++)
						.getSoundStream(this);
				mediaPlayer.setDataSource(fileInputStream.getFD());
				mediaPlayer.prepare();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mediaPlayer.setOnCompletionListener(mListener);
			mediaPlayer.start();
		}
	}

	// ==================================================================================

	@Override
	public boolean onLongClick(View imageView) {
		ClipData clipData = ClipData.newPlainText("", "");

		View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
				imageView);
		/*
		 * start the drag - contains the data to be dragged, metadata for this
		 * data and callback for drawing shadow
		 */
		imageView.startDrag(clipData, shadowBuilder, imageView, 0);
		imageView.setVisibility(View.VISIBLE);
		return true;
	}

	// ==================================================================================
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.TableActivity_buttonCategoryBack:

			if (categoryHistoryIndex - 1 <= -1)
				break;
			categoryHistoryIndex--;
			selectCategory(categoryHistory[categoryHistoryIndex]);

			// Log.d(LOG_TAG, String.valueOf(categoryHistoryIndex)+" - "
			// +String.valueOf(categoryHistory[categoryHistoryIndex]));
			break;

		case R.id.TableActivity_ButtonRemoveLastElement:

			if (tableRowSentence.getChildCount() <= 0)
				break;

			if (LangueDirection == 1)
				tableRowSentence.removeViewAt(0);

			else
				tableRowSentence
						.removeViewAt(tableRowSentence.getChildCount() - 1);

			sentenceList.remove(sentenceList.size() - 1);
			break;

		case R.id.TableActivity_ButtonRead:

			readSentence();
			break;

		}

	}

	// ==================================================================================

	@Override
	public boolean onDrag(View receivingLayoutView, DragEvent dragEvent) {
		View draggedImageView = (View) dragEvent.getLocalState();

		// Handles each of the expected events
		switch (dragEvent.getAction()) {

		case DragEvent.ACTION_DRAG_STARTED:
			Log.i(LOG_TAG, "drag action started");
			// Returns false. During the current drag and drop operation, this
			// View will
			// not receive events again until ACTION_DRAG_ENDED is sent.
			return true;

		case DragEvent.ACTION_DRAG_ENTERED:
			Log.i(LOG_TAG, "drag action entered");
			// the drag point has entered the bounding box
			return true;

		case DragEvent.ACTION_DRAG_LOCATION:
			Log.i(LOG_TAG, "drag action location");
			/*
			 * triggered after ACTION_DRAG_ENTERED stops after
			 * ACTION_DRAG_EXITED
			 */
			return true;

		case DragEvent.ACTION_DRAG_EXITED:
			Log.i(LOG_TAG, "drag action exited");
			// the drag shadow has left the bounding box
			return true;

		case DragEvent.ACTION_DROP:
			/*
			 * the listener receives this action type when drag shadow released
			 * over the target view the action only sent here if
			 * ACTION_DRAG_STARTED returned true return true if successfully
			 * handled the drop else false
			 */

			ViewGroup draggedImageViewParentLayout = (ViewGroup) draggedImageView
					.getParent();
			// draggedImageViewParentLayout.removeView(draggedImageView);
			ImageView imageView = new ImageView(this);
			ImageView old = (ImageView) draggedImageView;
			imageView.setImageDrawable(old.getDrawable());
			imageView.setTag(old.getTag());
			//
			TableLayout bottomFrameLayout = (TableLayout) receivingLayoutView;

			if (LangueDirection == 1)
				tableRowSentence.addView(imageView, 0);
			else
				tableRowSentence.addView(imageView);
			sentenceList.add(listeRessources.get((Integer) old.getTag()));
			// lastAddedImage++;

			return false;

		case DragEvent.ACTION_DRAG_ENDED:
			return true;

			// An unknown action type was received.
		default:
			Log.i(LOG_TAG, "Unknown action type received by OnDragListener.");
			break;
		}
		return false;
	}

	// ================================================================================
		public void setBackground() {
			bitmapBackground = BitmapUtil.decodeSampledBitmapFromResource(
					getResources(), selectedColorId, 200, 200);
			BitmapDrawable bm = new BitmapDrawable(
					TableActivity.this.getResources(), bitmapBackground);
			mainLayout.setBackgroundDrawable(bm);
			// if(bitmapBackground!=null && !bitmapBackground.isRecycled()){
			// bitmapBackground.recycle();
			// bitmapBackground=null;
			// bitmapDrawableBackground=null;
			// }
		}

		// ================================================================================
	// ==================================================================================

}

