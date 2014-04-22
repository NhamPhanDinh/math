package ydc.math.braintest.mathlogic.ultis;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class Untils {
	/**
	 * check connect internet
	 * 
	 */
	public static boolean checkInternetConnection(Activity activity)
	{
		ConnectivityManager conMgr = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// ARE WE CONNECTED TO THE NET
		if (conMgr.getActiveNetworkInfo() != null
				&& conMgr.getActiveNetworkInfo().isAvailable()
				&& conMgr.getActiveNetworkInfo().isConnected())
		{
			return true;
		}
		else
		{
			Log.v("Until", "Internet Connection Not Present");
			return false;
		}
	}

}
