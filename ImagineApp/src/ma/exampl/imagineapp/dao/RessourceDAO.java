package ma.exampl.imagineapp.dao;

import java.util.ArrayList;
import java.util.List;





import ma.exampl.imagineapp.model.Ressource;
import ma.exampl.imagineapp.persistence.DataBaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RessourceDAO {

	// ==================================================================================

	private SQLiteDatabase database;
	private DataBaseHelper dataBaseHelper;
	private String[] allColumns = { "_id", "category_id", "ressource_name",
			"ressource_image", "ressource_sound", "fixed_ressource" };

	// ==================================================================================

	public RessourceDAO(Context context) {
		dataBaseHelper = new DataBaseHelper(context);
		dataBaseHelper.openDataBase();
		database = dataBaseHelper.getDataBase();
	}

	// ==================================================================================

	private Ressource cursorToRessource(Cursor cursor) {
		Ressource ressource = new Ressource();
		ressource.setId(cursor.getInt(0));
		ressource.setCategoryId(cursor.getInt(1));
		ressource.setRessouceName(cursor.getString(2));
		ressource.setRessourceImage(cursor.getBlob(3));
		ressource.setRessourceSound(cursor.getBlob(4));
		ressource.setFixedRessource(cursor.getInt(5));

		return ressource;
	}

	// ==================================================================================

	public List<Ressource> getRessourcesByCategoryId(int id) {
		Cursor cursor;
		List<Ressource> ressources = new ArrayList<Ressource>();
		try {
			cursor = database.query(DataBaseHelper.TABLE_RESSOURCES,
					allColumns, "category_id=?",
					new String[] { String.valueOf(id) }, null, null, allColumns[5]+" DESC");

			cursor.moveToFirst();

			while (!cursor.isAfterLast()) {
				System.out.println("in the loop ");
				Ressource ressource = cursorToRessource(cursor);
				ressources.add(ressource);
				cursor.moveToNext();

			}
			cursor.close();

			return ressources;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// make sure to close the cursor

	}
	// ==================================================================================

		public void deleteRessource(int id) {
			try {
				database.delete(DataBaseHelper.TABLE_RESSOURCES, "_id=?",
						new String[] { String.valueOf(id) });

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
	// ==================================================================================

	public void addRessource(Ressource ressource) {
		ContentValues row = new ContentValues();
		row.put("category_id", ressource.getCategoryId());
		row.put("ressourceName", ressource.getRessouceName());
		row.put("ressourceImage", ressource.getRessourceImage());
		row.put("ressourceSound", ressource.getRessourceSound());
		row.put("fixed_ressource", ressource.getFixedRessource());
		database.insert(DataBaseHelper.TABLE_RESSOURCES, null, row);
	}
	// ==================================================================================

}
