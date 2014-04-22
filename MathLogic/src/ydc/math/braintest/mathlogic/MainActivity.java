package ydc.math.braintest.mathlogic;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Main Activity
 */
public class MainActivity extends Activity {
	private ImageView imgPlay;

	GoogleApiClient mClient; // initialized in onCreate
	/* ID service google play */
	private String APP_ID = "872812638237";
	private String LEADBOARD_ID = "CgkIneC_vbMZEAIQAA";
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
		// set click
		btnLeadBoard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// sumit point

			}
		});
		/* Play button */
		imgPlay = (ImageView) findViewById(R.id.home_button_start);
		imgPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						PlayGameActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_up,
						R.anim.slide_in_up);

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
}
