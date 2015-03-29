package ma.exampl.imagineapp.activity;

import android.app.Activity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import ma.exampl.imagineapp.R;
import ma.exampl.imagineapp.consts.ParseComConsts;
import ma.exampl.imagineapp.dao.remote.LibraryRemoteDAO;
import ma.exampl.imagineapp.model.Library;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		getLibrairies();
		
	}
	
	
	
	public void doSomething(List list){
		
		for (int i = 0; i < list.size(); i++) {
		
			System.out.println(list.get(i).toString());
			
			
		}
		
		TextView t =(TextView) findViewById(R.id.textView1);
		t.setText(list.get(0).toString());
	}
	
	
	public Collection<Library> getLibrairies(){
		
		Parse.initialize(this,ParseComConsts.USER, ParseComConsts.PASSWORD);
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Librairy");
		//	query.orderByAscending("librairyName");
			final List<Library> librariesToReturn = new ArrayList<Library>();
			
			query.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> libraryRemoteList, ParseException parseException) {
					
					
					System.out.println(libraryRemoteList.size());
					for (ParseObject parseObject : libraryRemoteList) {
						
						Log.i("montag", "you are here "+parseObject.getClassName());
						Log.i("montag", "you are here "+parseObject.getString("librairyName"));
						
						Library library = new Library();
						library.setDateOfCreation(parseObject.getCreatedAt().toString());
						library.setLibraryName(parseObject.getString("libraryName"));
						library.setLibraryAuthorFullName(parseObject.getString("librairieAuthorFullName"));
						library.setDescription(parseObject.getString("libraryDescription"));
						library.setLanguage(parseObject.getString("language"));
						librariesToReturn.add(library);
						System.out.println(librariesToReturn.size());
						if(librariesToReturn.size()==libraryRemoteList.size())
						{doSomething(librariesToReturn);}
						
					}
					
					
				}
			});
			return librariesToReturn;
		
		
	}
	
	
}
