package ma.exampl.imagineapp.dao.remote;

import java.util.ArrayList;
import java.util.List;

import ma.exampl.imagineapp.consts.ParseComConsts;
import ma.exampl.imagineapp.model.Library;
import android.content.Context;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LibraryRemoteDAO {
	
	
	public List<Library> fetchAllPublicLibraries(Context context)
	{
		
		Parse.initialize(context,ParseComConsts.USER, ParseComConsts.PASSWORD);
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Librairy");
		query.orderByAscending("librairyName");
		final List<Library> librariesToReturn = new ArrayList<Library>();
		
		FindCallback<ParseObject> findCallback = new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> libraryRemoteList, ParseException parseException) {
				
				
				
				if (parseException != null)
					return;
				
				for (ParseObject parseObject : libraryRemoteList) {
					
					Library library = new Library();
					library.setDateOfCreation(parseObject.getCreatedAt().toString());
					library.setLibraryName(parseObject.getString("libraryName"));
					library.setLibraryAuthorFullName(parseObject.getString("librairieAuthorFullName"));
					library.setDescription(parseObject.getString("libraryDescription"));
					library.setLanguage(parseObject.getString("language"));
					librariesToReturn.add(library);
					
					
				}
				
			}
		};
		
		query.findInBackground(findCallback);
      
		
		
		return librariesToReturn ; 
	}

}
