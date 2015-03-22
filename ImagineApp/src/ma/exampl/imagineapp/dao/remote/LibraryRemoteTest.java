package ma.exampl.imagineapp.dao.remote;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ma.exampl.imagineapp.model.Library;

import org.junit.Before;
import org.junit.Test;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.test.AndroidTestCase;
import android.view.Display;

public class LibraryRemoteTest extends AndroidTestCase {

	private LibraryRemoteDAO libraryRemoteDAO;
	
	@Before
	public void setUp()
	{
		libraryRemoteDAO= new LibraryRemoteDAO();
	}
	
	@Test
	public void test() {
		List<Library> listLibraryFromServer = libraryRemoteDAO.fetchAllPublicLibraries(getContext());
		assertFalse(listLibraryFromServer.isEmpty());
	}

}
