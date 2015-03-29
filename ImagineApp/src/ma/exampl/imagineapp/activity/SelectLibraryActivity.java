package ma.exampl.imagineapp.activity;

import java.util.ArrayList;

import ma.exampl.imagineapp.R;
import ma.exampl.imagineapp.dao.LibraryDAO;
import ma.exampl.imagineapp.model.Library;
import ma.exampl.imagineapp.persistence.SharedPreferencesManager;
import android.app.Activity;

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

public class SelectLibraryActivity extends Activity{
	//================================================================================
		
		private TextView txtMessage;
		private ListView l1;
		
		private int selectedLibraryId;
		private ArrayList<Library> libraries;
		
		private LibraryDAO libraryDao;
		//================================================================================
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_select_library);
			//------------------------
			
			/* get selected library id */
			selectedLibraryId = SharedPreferencesManager.getSelectedLibraryValue(this);
			
			/* Dao lib */
			libraryDao=new LibraryDAO(this);
			
			
			libraries=(ArrayList<Library>) libraryDao.getAllLibraries();
			Log.d("SFIAN", "ok - 1 ");
			if(libraries.size()==0)
				{
				txtMessage=(TextView)findViewById(R.id.SelectLibrary_textMessage);
				txtMessage.setText("VIDE");
				txtMessage.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

				//txtview msg vide
				}
			else
				{
					
				
		
					
					
					l1=(ListView)findViewById(R.id.SelectLibrary_listViewProd);
			        l1.setAdapter(new DataListAdapter(libraries));
			        l1.setOnItemClickListener(new OnItemClickListener() 
			        {
						@Override
						public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
						{
							DataListAdapter library=(DataListAdapter) l1.getAdapter();
							SharedPreferencesManager.setSelectedLibraryValue(SelectLibraryActivity.this, library.getLibraryID(position));
							SelectLibraryActivity.this.finish();
							//Log.d("SFIAN", "ok - "+library.getLibraryID(position));
							
						}
					});
				}
			
			//========================
		}
		//================================================================================
		//================================================================================
		public class DataListAdapter extends BaseAdapter 
		{
			//================================================================================
		    private ArrayList<Library> listLibraries;
		    //================================================================================
		    public DataListAdapter() 
		    {
		    	this.listLibraries=null;
			}
		  //================================================================================
		    public DataListAdapter(ArrayList<Library> listLibraries) 
		    {
				this.listLibraries=listLibraries;
			}
		    //================================================================================
			@Override
			public int getCount() 
			{
				// TODO Auto-generated method stub
				return listLibraries.size();
			}
			
			//================================================================================

			@Override
			public Object getItem(int arg0) 
			{
				// TODO Auto-generated method stub
				return null;
			}
			//================================================================================

			@Override
			public long getItemId(int position) 
			{
				// TODO Auto-generated method stub
				return position;
			}
			//================================================================================

			public int getLibraryID(int position) 
			{
				return listLibraries.get(position).getId();
			}
			//================================================================================

			@Override
			public View getView(int position, View convertView, ViewGroup parent) 
			{
				LayoutInflater inflater = getLayoutInflater();
		        View row;
		        row = inflater.inflate(R.layout.custom_list, parent, false);
		        TextView nom, autor,langage;
		        ImageView image;
		        nom = (TextView) row.findViewById(R.id.nomLib);
		        autor = (TextView) row.findViewById(R.id.autorLib);
		        langage = (TextView) row.findViewById(R.id.langage);
		        image=(ImageView)row.findViewById(R.id.imageLine);
		        nom.setText(listLibraries.get(position).getLibraryName());
		        autor.setText("Autor : "+listLibraries.get(position).getLibraryAuthorFullName());
		        langage.setText(" - Lang : "+listLibraries.get(position).getLanguage());
		        
		        if(selectedLibraryId==listLibraries.get(position).getId())
		        	image.setBackgroundResource(R.drawable.checked);

		        return (row);
			}
			//================================================================================

		}
		//================================================================================
		//================================================================================

}
