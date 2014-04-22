package ydc.math.braintest.mathlogic;

import ydc.math.braintest.mathlogic.gameBasic.GameHelper;
import ydc.math.braintest.mathlogic.gameBasic.GameHelper.GameHelperListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

/**
 * Main Activity
 */
public class MainActivity extends Activity {

	GoogleApiClient mClient; // initialized in onCreate
	/* ID service google play */
	private String APP_ID = "872812638237";
	private String LEADBOARD_ID = "CgkIneC_vbMZEAIQAA";
	/* Leadboard button */
	private ImageView btnLeadBoard;
	GameHelper mHelper;
	private boolean DEBUG_BUILD = true;
	private boolean GAMEHELPER_SUCCESS = false;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		/* get data from xml */
		btnLeadBoard = (ImageView) findViewById(R.id.home_button_leader_board);
		// get leadboard
		// create game helper with all APIs (Games, Plus, AppState):
		mHelper = new GameHelper(this, GameHelper.CLIENT_ALL);

		// enable debug logs (if applicable)
		if (DEBUG_BUILD) {
			mHelper.enableDebugLog(true, "GameHelper");
		}

		GameHelperListener listener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInSucceeded() {
				// handle sign-in succeess
				GAMEHELPER_SUCCESS = true;
			}

			@Override
			public void onSignInFailed() {
				// handle sign-in failure (e.g. show Sign In button)
				GAMEHELPER_SUCCESS = false;
			}

		};
		mHelper.setup(listener);
		// set click
		btnLeadBoard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// sumit point
				if (GAMEHELPER_SUCCESS) {
					Games.Leaderboards.submitScore(mHelper.getApiClient(),
							LEADBOARD_ID, 100);
					startActivityForResult(Games.Leaderboards
							.getLeaderboardIntent(mHelper.getApiClient(),
									LEADBOARD_ID), 111);
				}

			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		mHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mHelper.onStop();
	}
}
