package ma.exampl.imagineapp.dao;

import java.util.ArrayList;
import java.util.List;

import ma.exampl.imagineapp.model.Library;
import ma.exampl.imagineapp.persistence.DataBaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LbraryDAO {

	// ==================================================================================

	private SQLiteDatabase database;
	private DataBaseHelper dataBaseHelper;

	private String[] allColumns = { "_id", "libraryName",
			"libraryAuthorFullName", "dateOfCreation", "language" };

	// ==================================================================================

	public LbraryDAO(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		dataBaseHelper.openDataBase();
		database = dataBaseHelper.getDataBase();
	}

	// ==================================================================================

	public List<Library> getAllLibraries() {

		List<Library> libraries = new ArrayList<Library>();
		System.out.println(database.isOpen());
		Cursor cursor = null;
		try {
			cursor = database.query(DataBaseHelper.TABLE_LIBRARIES, allColumns,
					null, null, null, null, null);

			// System.out.println("it should be after this");
		} catch (Exception e) {
			e.printStackTrace();
		}
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Library library = cursorToLibrary(cursor);
			libraries.add(library);
			cursor.moveToNext();
		}

		// make sure to close the cursor
		cursor.close();
		return libraries;
	}

	// ==================================================================================

	private Library cursorToLibrary(Cursor cursor) {
		Library library = new Library();
		library.setId(cursor.getInt(0));
		library.setLibraryName(cursor.getString(1));
		library.setLibraryAuthorFullName(cursor.getString(2));
		library.setDateOfCreation(cursor.getString(3));
		library.setLanguage(cursor.getString(4));

		return library;
	}

	// ==================================================================================

	public Library getLibraryById(int id) {
		Cursor cursor = null;
		try {
			cursor = database.query(DataBaseHelper.TABLE_LIBRARIES, allColumns,
					"_id=?", new String[] { String.valueOf(id) }, null, null,
					null);
			cursor.moveToFirst();
			return cursorToLibrary(cursor);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// ==================================================================================

	public void addLibrary(Library library) {
		ContentValues row = new ContentValues();
		row.put("libraryName", library.getLibraryName());
		row.put("libraryAuthorFullName", library.getLibraryAuthorFullName());
		row.put("dateOfCreation", library.getDateOfCreation());
		row.put("language", library.getLanguage());
		database.insert(DataBaseHelper.TABLE_LIBRARIES, null, row);

	}

	// ==================================================================================

	public int getLastInsertedIndex() {
		Cursor cursor = database.query(DataBaseHelper.TABLE_LIBRARIES,
				new String[] { "MAX(_id)" }, null, null, null, null, null);
		cursor.moveToNext();

		return cursor.getInt(0);
	}

	// ==================================================================================

}
