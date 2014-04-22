package ydc.math.braintest.mathlogic;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;
import com.google.ads.AdRequest.ErrorCode;

import io.openkit.OKLeaderboard;
import io.openkit.OKUser;
import io.openkit.OpenKit;
import io.openkit.leaderboards.OKLeaderboardsActivity;
import ydc.math.braintest.mathlogic.ultis.Constance;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * Main Activity
 */
public class MainActivity extends Activity {
	private ImageView imgPlay;
	Dialog dialogGameMode;
	private AdView adView;
	private InterstitialAd interstitial;
	// Grab your app key and secret key
	String myAppKey = "HQzQkwvEuiIKL5sxIlhT";
	String mySecretKey = "8MnOoMRmhKzi9inM9dU3En5mlJiLjS0MPJWVxirb";
	/* Leadboard button */
	private ImageView btnLeadBoard;
	/* Rate button */
	private ImageView btnRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		/* get data from xml */
		btnLeadBoard = (ImageView) findViewById(R.id.home_button_leader_board);
		// get leadboard
		adView = (AdView) findViewById(R.id.ad);
		adView.loadAd(new AdRequest());

		// Create the interstitial.
		interstitial = new InterstitialAd(this, "a1533d75297914b");

		// Create ad request.
		AdRequest adr = new AdRequest();

		// Begin loading your interstitial.
		interstitial.loadAd(adr);
		// Create the interstitial.
		interstitial = new InterstitialAd(this, "a15342a68f8942e");

		// Create ad request.
		adr = new AdRequest();
		interstitial.setAdListener(new AdListener() {

			@Override
			public void onDismissScreen(Ad arg0) {
				// TODO Auto-generated method stub
				Intent launchleaderboard = OKLeaderboard.getLeaderboardIntent(MainActivity.this, 2147);
				startActivity(launchleaderboard);
			}

			@Override
			public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLeaveApplication(Ad arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPresentScreen(Ad arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onReceiveAd(Ad arg0) {
				// TODO Auto-generated method stub

			}

		});
		interstitial.loadAd(adr);
		// Initialize OpenKit.
		OpenKit.configure(this, myAppKey, mySecretKey);
		// set click
		btnLeadBoard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// sumit point
				checkUserLogin();
			}
		});
		/* Play button */
		imgPlay = (ImageView) findViewById(R.id.home_button_start);
		imgPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogGameMode.show();

			}
		});

		dialogGameMode = new Dialog(this,R.style.PauseDialog);
		dialogGameMode.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogGameMode.setContentView(R.layout.dialog_game_mode);
		// dialogGameMode.setTitle("Select game Mode");
		Button easyMode = (Button) dialogGameMode
				.findViewById(R.id.easy_game_mode);
		Button hardMode = (Button) dialogGameMode
				.findViewById(R.id.hard_game_mode);
		easyMode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constance.checkMode = 1;
				Intent intent = new Intent(MainActivity.this,
						PlayGameActivity.class);
				startActivity(intent);
				 overridePendingTransition(R.anim.slide_in_up,R.anim.slide_in_up);
				 dialogGameMode.dismiss();
			}
		});
		hardMode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constance.checkMode = 2;
				Intent intent = new Intent(MainActivity.this,
						PlayGameActivity.class);
				startActivity(intent);
				 overridePendingTransition(R.anim.slide_in_up,R.anim.slide_in_up);
				 dialogGameMode.dismiss();
			}
		});
		/* Rate button */
		btnRate = (ImageView) findViewById(R.id.home_button_rate);
		btnRate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("market://details?id=" + getPackageName());
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				try {
					startActivity(goToMarket);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(MainActivity.this,
							"Couldn't launch the market", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

	}

	/**
	 * This method check user login
	 */
	private void checkUserLogin() {
//		if (OpenKit.getCurrentUser() != null) {
//			// Get the current user
//			OKUser currentUser = OpenKit.getCurrentUser();

			// Show the user's profile pic and nickname
//			showAlertDialog(MainActivity.this, getString(R.string.app_name),
//					"Hello " + currentUser.getUserNick()
//							+ ". You login success. Do you want logout?");
//		} else {
			// Show the login form
//			Intent launchOKLeaderboards = new Intent(MainActivity.this, OKLeaderboardsActivity.class);
//			startActivity(launchOKLeaderboards);
		if (interstitial.isReady()) {
			interstitial.show();
		} else {
			Intent launchleaderboard = OKLeaderboard.getLeaderboardIntent(MainActivity.this, 2147);
			startActivity(launchleaderboard);
		}
			

//		}
	}

	/**
	 * Show alert dialog
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param status
	 */
	@SuppressWarnings("deprecation")
	public void showAlertDialog(final Context context, String title,
			String message) {
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
				.create();

		// Setting Dialog Title
		alertDialog.setTitle(title);
		alertDialog.setCancelable(false);
		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
				OKUser.logoutCurrentUser(MainActivity.this);
			}
		});
		alertDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
}
