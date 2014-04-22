package ydc.math.braintest.mathlogic;

import java.util.Random;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class PlayGameActivity extends Activity {
	private FragmentManager fragMgr;
	ViewFlipper flipper;
	TextView textQuestion;
	TextView textScore;
	TextView overScore;
	TextView bestScore;
	RelativeLayout layoutQuestion;
	private int number1;
	private int number2;
	private int nunberRandom;
	private int numberresult;
	private int score = 0;
	private int best;
	private int checkMath = 0;
	Dialog dialogGameOver;
	ProgressBar mProgressBar;
	CountDownTimer mCountDownTimer;
	LayoutInflater inflater;
	ImageView imgRight;
	ImageView imgWrong;
	CountDownTimer mCountDownTime;
	MediaPlayer mPlayer;
	View view;
	int i = 0;
	Boolean check = false;
	SharedPreferences.Editor editor;
	SharedPreferences preferences;
	static final int TOTAL_TIME = 2000;
	static final int INTERVAL = 1000;
	String[] arrayColors = { "#000000", "#A9A9A9", "#5F9EA0", "#6495ED",
			"#2F4F4F", "#9400D3", "#708090" };
	// SocialAuth Component
	SocialAuthAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_in_game);
		preferences = getPreferences(MODE_PRIVATE);
		score = 0;
		editor = preferences.edit();
		editor.putInt("bestScore", 0);
		flipper = (ViewFlipper) findViewById(R.id.view_flipper);
		imgRight = (ImageView) findViewById(R.id.ingame_button_right);
		imgWrong = (ImageView) findViewById(R.id.ingame_button_wrong);
		textScore = (TextView) findViewById(R.id.ingame_tv_current_score);
		textScore.setText("0");
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_countdown_timer);
		inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.view_question, null);
		flipper.setOutAnimation(AnimationUtils.loadAnimation(view.getContext(),
				R.anim.view_transition_in_left));
		flipper.setInAnimation(AnimationUtils.loadAnimation(view.getContext(),
				R.anim.view_transition_in_left));
		textQuestion = (TextView) view.findViewById(R.id.ingame_tv_question);
		layoutQuestion = (RelativeLayout) view.findViewById(R.id.view_question);
		Typeface mFont = Typeface.createFromAsset(getAssets(), "archive.otf");
		textQuestion.setTypeface(mFont);
		flipper.addView(view);
		addQuestion();

		imgRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkKQ() == true) {
					mPlayer = MediaPlayer.create(PlayGameActivity.this,
							R.raw.passed);
					mPlayer.start();
					mPlayer.setOnCompletionListener(new OnCompletionListener() {

						public void onCompletion(MediaPlayer mp) {
							mPlayer.release();
							mPlayer = null;
						}
					});

					score++;
					textScore.setText("" + score);
					Random md = new Random();
					int select = 1 + md.nextInt(3);
					if (select == 1)
						addQuestion();
					else if (select == 2)
						addSubtractionQuestion();
					else if (select == 3)
						addMultiplicationQuestion();
					i = 0;
					if (check == true)
						mCountDownTimer.cancel();
					mProgressBar.setMax(TOTAL_TIME);
					mProgressBar.setProgress(i);
					mCountDownTimer = new CountDownTimer(2000, 100) {

						@Override
						public void onTick(long millisUntilFinished) {
							check = true;
							// Log.v("Log_tag", "Tick of Progress"+ i+
							// millisUntilFinished);
							i = i + 100;
							mProgressBar.setProgress(i);

						}

						@Override
						public void onFinish() {
							// Do what you want
							i = i + 100;
							mProgressBar.setProgress(i);
							mPlayer = MediaPlayer.create(PlayGameActivity.this,
									R.raw.fail);
							mPlayer.start();
							mPlayer.setOnCompletionListener(new OnCompletionListener() {

								public void onCompletion(MediaPlayer mp) {
									mPlayer.release();
									mPlayer = null;
									if (best < score) {
										best = score;
										editor.putInt("bestScore", best);
									}
									overScore.setText("Score: " + score);
									bestScore.setText("Best: " + best);
									dialogGameOver.show();
								}
							});
						}
					};
					mCountDownTimer.start();
				} else {
					if (check == true)
						mCountDownTimer.cancel();
					mPlayer = MediaPlayer.create(PlayGameActivity.this,
							R.raw.fail);
					mPlayer.start();
					mPlayer.setOnCompletionListener(new OnCompletionListener() {

						public void onCompletion(MediaPlayer mp) {
							mPlayer.release();
							mPlayer = null;
							best = preferences.getInt("bestScore", -1);
							if (best < score) {
								best = score;
								editor.putInt("bestScore", best);
							}
							overScore.setText("Score: " + score);
							bestScore.setText("Best: " + best);
							dialogGameOver.show();
						}
					});
				}

			}
		});

		imgWrong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkKQ() == false) {
					mPlayer = MediaPlayer.create(PlayGameActivity.this,
							R.raw.passed);
					mPlayer.start();
					mPlayer.setOnCompletionListener(new OnCompletionListener() {

						public void onCompletion(MediaPlayer mp) {
							mPlayer.release();
							mPlayer = null;
						}
					});
					score++;
					textScore.setText("" + score);
					Random md = new Random();
					int select = 1 + md.nextInt(3);
					if (select == 1)
						addQuestion();
					else if (select == 2)
						addSubtractionQuestion();
					else if (select == 3)
						addMultiplicationQuestion();
					i = 0;
					if (check == true)
						mCountDownTimer.cancel();
					mProgressBar.setMax(TOTAL_TIME);
					mProgressBar.setProgress(i);
					mCountDownTimer = new CountDownTimer(2000, 100) {

						@Override
						public void onTick(long millisUntilFinished) {
							check = true;
							// Log.v("Log_tag", "Tick of Progress"+ i+
							// millisUntilFinished);
							i = i + 100;
							mProgressBar.setProgress(i);

						}

						@Override
						public void onFinish() {
							// Do what you want
							i = i + 100;
							mProgressBar.setProgress(i);
							mPlayer = MediaPlayer.create(PlayGameActivity.this,
									R.raw.fail);
							mPlayer.start();
							mPlayer.setOnCompletionListener(new OnCompletionListener() {

								public void onCompletion(MediaPlayer mp) {
									mPlayer.release();
									mPlayer = null;
									best = preferences.getInt("bestScore", -1);
									if (best < score) {
										best = score;
										editor.putInt("bestScore", best);
									}
									overScore.setText("Score: " + score);
									bestScore.setText("Best: " + best);
									dialogGameOver.show();
								}
							});
						}
					};
					mCountDownTimer.start();
				} else {
					if (check == true)
						mCountDownTimer.cancel();
					mPlayer = MediaPlayer.create(PlayGameActivity.this,
							R.raw.fail);
					mPlayer.start();
					mPlayer.setOnCompletionListener(new OnCompletionListener() {

						public void onCompletion(MediaPlayer mp) {
							mPlayer.release();
							mPlayer = null;
							best = preferences.getInt("bestScore", -1);
							if (best < score) {
								best = score;
								editor.putInt("bestScore", best);
							}
							overScore.setText("Score: " + score);
							bestScore.setText("Best: " + best);
							dialogGameOver.show();
						}
					});

				}

			}
		});
		/**
		 * Create dialog
		 */
		dialogGameOver = new Dialog(this, R.anim.slide_in_up);
		dialogGameOver.setContentView(R.layout.dialog_game_over);
		ImageView restart = (ImageView) dialogGameOver
				.findViewById(R.id.game_over_restart);
		ImageView home = (ImageView) dialogGameOver
				.findViewById(R.id.game_over_home);
		ImageView comment = (ImageView) dialogGameOver
				.findViewById(R.id.game_over_comment);
		ImageView share = (ImageView) dialogGameOver
				.findViewById(R.id.game_over_share);
		overScore = (TextView) dialogGameOver.findViewById(R.id.tv_score);
		bestScore = (TextView) dialogGameOver.findViewById(R.id.tv_best_score);
		restart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PlayGameActivity.this,
						PlayGameActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_up,
						R.anim.slide_in_up);
				dialogGameOver.cancel();
			}
		});
		/* Home click */
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PlayGameActivity.this,
						MainActivity.class);
				startActivity(intent);
				dialogGameOver.cancel();
				finish();
			}
		});
		/* Commment click */
		comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		

		// fragMgr = getSupportFragmentManager();
		// share network
		// Add it to Library
		adapter = new SocialAuthAdapter(new ResponseListener());

		// Add providers
		adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		adapter.addProvider(Provider.LINKEDIN, R.drawable.linkedin);
		adapter.addProvider(Provider.YAHOO, R.drawable.yahoo);
		adapter.addProvider(Provider.INSTAGRAM, R.drawable.instagram);
		adapter.addProvider(Provider.GOOGLEPLUS, R.drawable.googleplus);
		adapter.addProvider(Provider.YAMMER, R.drawable.yammer);
		adapter.addProvider(Provider.SALESFORCE, R.drawable.salesforce);
		adapter.addProvider(Provider.FLICKR, R.drawable.flickr);
		adapter.addProvider(Provider.FOURSQUARE, R.drawable.foursquare);

		// Providers require setting user call Back url
		adapter.addCallBack(Provider.TWITTER,
				"http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
		adapter.addCallBack(Provider.YAMMER,
				"http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
		//add share button
		// Enable Provider
				adapter.enable(share);
	}

	/**
	 * Listens Response from Library
	 * 
	 */

	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {

			Log.d("ShareButton", "Authentication Successful");

			// Get name of provider after authentication
			final String providerName = values
					.getString(SocialAuthAdapter.PROVIDER);
			Log.d("ShareButton", "Provider Name = " + providerName);

			// Please avoid sending duplicate message. Social Media Providers
			// block duplicate messages.

			// to share on multiple providers
			adapter.updateStatus("Congratulation. You winner "
					+ "market://details?id=" + getPackageName(),
					new MessageListener(), false);
		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("ShareButton", "Authentication Error: " + error.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d("ShareButton", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Share-Button", "Dialog Closed by pressing Back Key");
		}

	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201
					|| status.intValue() == 204)
				Toast.makeText(PlayGameActivity.this,
						"Message posted on " + provider, Toast.LENGTH_LONG)
						.show();
			else
				Toast.makeText(PlayGameActivity.this,
						"Message not posted on " + provider, Toast.LENGTH_LONG)
						.show();
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addQuestion() {
		checkMath = 1;
		view = inflater.inflate(R.layout.view_question, null);
		textQuestion = (TextView) view.findViewById(R.id.ingame_tv_question);
		Random rand = new Random();
		int cl = rand.nextInt(7);
		view.setBackgroundColor(Color.parseColor(arrayColors[cl]));
		number1 = 1 + rand.nextInt(15);
		number2 = 1 + rand.nextInt(15);
		int sum = number1 + number2;
		nunberRandom = rand.nextInt(2);
		if (nunberRandom == 0) {
			numberresult = (number1 + number2);
		} else {
			Random rand2 = new Random();
			if (number1 > 2 && number2 > 2)
				numberresult = (sum - 3 + rand2.nextInt(4));
			else
				numberresult = (sum - 1 + rand2.nextInt(4));
		}
		textQuestion.setText("" + number1 + "+" + number2 + "=" + numberresult);
		flipper.addView(view);
		flipper.showNext();

	}

	public void addSubtractionQuestion() {
		checkMath = 2;
		view = inflater.inflate(R.layout.view_question, null);
		textQuestion = (TextView) view.findViewById(R.id.ingame_tv_question);
		Random rand = new Random();
		int cl = rand.nextInt(7);
		view.setBackgroundColor(Color.parseColor(arrayColors[cl]));
		number2 = 1 + rand.nextInt(15);
		number1 = number2 + 1 + rand.nextInt(15);
		int kq = number1 - number2;
		nunberRandom = rand.nextInt(2);
		if (nunberRandom == 0) {
			numberresult = (number1 - number2);
		} else {
			Random rand2 = new Random();
			if (number1 > 2 && number2 > 2)
				numberresult = (kq - 3 + rand2.nextInt(4));
			else
				numberresult = (kq - 1 + rand2.nextInt(4));
		}
		textQuestion.setText("" + number1 + "-" + number2 + "=" + numberresult);
		flipper.addView(view);
		flipper.showNext();
	}

	public void addMultiplicationQuestion() {
		checkMath = 3;
		view = inflater.inflate(R.layout.view_question, null);
		textQuestion = (TextView) view.findViewById(R.id.ingame_tv_question);
		Random rand = new Random();
		int cl = rand.nextInt(7);
		view.setBackgroundColor(Color.parseColor(arrayColors[cl]));
		number2 = 1 + rand.nextInt(9);
		number1 = 1 + rand.nextInt(9);
		int kq = number1 * number2;
		nunberRandom = rand.nextInt(2);
		if (nunberRandom == 0) {
			numberresult = (number1 * number2);
		} else {
			Random rand2 = new Random();
			if (number1 > 2 && number2 > 2)
				numberresult = (kq - 3 + rand2.nextInt(4));
			else
				numberresult = (kq - 1 + rand2.nextInt(4));
		}
		textQuestion.setText("" + number1 + "x" + number2 + "=" + numberresult);
		flipper.addView(view);
		flipper.showNext();

	}

	public void addDivisionQuestion() {
		checkMath = 4;

	}

	Boolean checkKQ() {
		if (checkMath == 1) {
			if ((number1 + number2) == numberresult)
				return true;
			else
				return false;
		}
		if (checkMath == 2) {
			if ((number1 - number2) == numberresult)
				return true;
			else
				return false;
		}
		if (checkMath == 3) {
			if ((number1 * number2) == numberresult)
				return true;
			else
				return false;
		}
		if (checkMath == 4) {
			if ((number1 / number2) == numberresult)
				return true;
			else
				return false;
		}
		return false;
	}

	// public void addTogglableFragment() {
	// FragmentTransaction transaction = fragMgr.beginTransaction();
	//
	// FragmentQuestion fragment = new FragmentQuestion(); //true - Fragment's
	// text should say that this is removable, rather than that it isn't.
	// //transaction.add(R.id.second_fragment_container, fragment,
	// TAG_TOGGLABLE_FRAG); //note - this Fragment will remain in the UI after
	// an orientation change
	//
	// transaction.commit();
	// }

}
