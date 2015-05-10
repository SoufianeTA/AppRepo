package ma.exampl.imagineapp.dao;

import java.util.ArrayList;
import java.util.List;

import ma.exampl.imagineapp.model.Category;
import ma.exampl.imagineapp.model.Library;
import ma.exampl.imagineapp.persistence.DataBaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CategoryDAO {
	// ==================================================================================

	private SQLiteDatabase database;
	private DataBaseHelper dataBaseHelper;

	private String[] allColumns = { "_id", "library_id", "category_name",
			"category_image", "fk_id_category" };

	// ==================================================================================

	public CategoryDAO(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		dataBaseHelper.openDataBase();
		database = dataBaseHelper.getDataBase();
	}

	// ==================================================================================
	private Category cursorToCategory(Cursor cursor) {
		Category category = new Category();
		category.setId(cursor.getInt(0));
		category.setIdLibrary((cursor.getInt(1)));
		category.setCategoryName(cursor.getString(2));
		category.setCategoryImage(cursor.getBlob(3));
		category.setFkIdCategory(cursor.getInt(4));

		return category;
	}

	// ==================================================================================
	public List<Category> getCategoriesByCategoryId(int id) {
		Cursor cursor;
		List<Category> categories = new ArrayList<Category>();
		try {
			cursor = database.query(DataBaseHelper.TABLE_CATEGORIES,
					allColumns, "fk_id_category=?",
					new String[] { String.valueOf(id) }, null, null, null);

			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				System.out.println("in the loop ");
				Category category = cursorToCategory(cursor);
				categories.add(category);
				cursor.moveToNext();

			}
			cursor.close();

			return categories;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// make sure to close the cursor

	}

	// ==================================================================================
	public Category getDefaultCategoryByIdLibrary(int id) {
		Cursor cursor;
		List<Category> categories = new ArrayList<Category>();
		try {
			cursor = database.query(DataBaseHelper.TABLE_CATEGORIES,
					allColumns, "library_id=? " + " AND " + "category_name=? ",
					new String[] { String.valueOf(id), "Default" }, null, null,
					null);

			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				System.out.println("in the loop ");
				Category category = cursorToCategory(cursor);
				categories.add(category);
				cursor.moveToNext();

			}
			cursor.close();

			return categories.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// make sure to close the cursor

	}
	// ==================================================================================

		public void deleteCategory(int id) {
			try {
				database.delete(DataBaseHelper.TABLE_CATEGORIES, "_id=?",
						new String[] { String.valueOf(id) });

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
	// ==================================================================================
	public Category getCategoryById(int id) {
		Cursor cursor;
		List<Category> categories = new ArrayList<Category>();
		try {
			cursor = database.query(DataBaseHelper.TABLE_CATEGORIES,
					allColumns, "_id=? ", new String[] { String.valueOf(id) },
					null, null, null);

			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				System.out.println("in the loop ");
				Category category = cursorToCategory(cursor);
				categories.add(category);
				cursor.moveToNext();

			}
			cursor.close();

			return categories.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// make sure to close the cursor

	}

	// ==================================================================================
	public void addCategorie(Category category, Library library) {
		ContentValues row = new ContentValues();
		row.put("category_name", category.getCategoryName());
		row.put("category_image", category.getCategoryImage());
		row.put("library_id", library.getId());
		database.insert(DataBaseHelper.TABLE_CATEGORIES, null, row);
	}

	// ==================================================================================

	public List<Category> getAllCategories(int id) {

		List<Category> categories = new ArrayList<Category>();
		System.out.println(database.isOpen());
		Cursor cursor = null;
		try {
			cursor = database.query(DataBaseHelper.TABLE_CATEGORIES,
					allColumns, "library_id=? ", new String[] { String.valueOf(id) },
					null, null, null);

			// System.out.println("it should be after this");
		} catch (Exception e) {
			e.printStackTrace();
		}
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Category category = cursorToCategory(cursor);
			categories.add(category);
			cursor.moveToNext();
		}

		// make sure to close the cursor
		cursor.close();
		return categories;
	}
	// ==================================================================================
}
