package ydc.math.braintest.mathlogic;
import java.util.Random;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
	CountDownTimer  mCountDownTimer;
	LayoutInflater inflater;
	ImageView imgRight;
	ImageView imgWrong;
	CountDownTimer mCountDownTime;
	MediaPlayer mPlayer;
	View view;
	int i=0;
	Boolean check = false;
	SharedPreferences.Editor editor ;
	SharedPreferences preferences;
	static final int TOTAL_TIME = 2000;
    static final int INTERVAL = 1000;
    String [] arrayColors={"#000000","#A9A9A9", "#5F9EA0", "#6495ED", "#2F4F4F","#9400D3", "#708090"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_in_game);
		preferences = getPreferences(MODE_PRIVATE);
		score = 0;
		editor = preferences.edit();
		editor.putInt("bestScore", 0);
		flipper = (ViewFlipper) findViewById(R.id.view_flipper);
		imgRight = (ImageView)findViewById(R.id.ingame_button_right);
		imgWrong = (ImageView)findViewById(R.id.ingame_button_wrong);
		textScore = (TextView)findViewById(R.id.ingame_tv_current_score);
		textScore.setText("0");
		mProgressBar = (ProgressBar)findViewById(R.id.progress_bar_countdown_timer);
		inflater = (LayoutInflater)this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		view = inflater.inflate( R.layout.view_question, null );
		flipper.setOutAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.view_transition_in_left));
		flipper.setInAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.view_transition_in_left));
		textQuestion = (TextView)view.findViewById(R.id.ingame_tv_question);
		layoutQuestion = (RelativeLayout)view.findViewById(R.id.view_question);
		Typeface mFont = Typeface.createFromAsset(getAssets(), "archive.otf");
		textQuestion.setTypeface(mFont);
		flipper.addView(view);
		addQuestion();
		
		imgRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				if(checkKQ() == true){
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
					textScore.setText(""+score);
					Random md = new Random();
					int select = 1 + md.nextInt(3);
					if(select == 1)
						addQuestion();
					else if(select == 2)
						addSubtractionQuestion();
					else if(select == 3)
						addMultiplicationQuestion();
					i=0;
					if(check == true )
						mCountDownTimer.cancel();
					mProgressBar.setMax(TOTAL_TIME);
					mProgressBar.setProgress(i);
					   mCountDownTimer=new CountDownTimer(2000,100) {

					        @Override
					        public void onTick(long millisUntilFinished) {
					        	check = true;
					            //Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
					            i = i + 100;
					            mProgressBar.setProgress(i);

					        }

					        @Override
					        public void onFinish() {
					        //Do what you want 
					            i = i+ 100;
					            mProgressBar.setProgress(i);
					            mPlayer = MediaPlayer.create(PlayGameActivity.this,
										R.raw.fail);
								mPlayer.start();
								mPlayer.setOnCompletionListener(new OnCompletionListener() {

									public void onCompletion(MediaPlayer mp) {
										mPlayer.release();
										mPlayer = null;
										if(best< score){
											best = score;
											editor.putInt("bestScore", best);
										}
										overScore.setText("Score: "+score);
										bestScore.setText("Best: "+best);
										dialogGameOver.show();
									}
								});
					        }
					    };
					    mCountDownTimer.start();
				}
				else{
					if(check == true )
						mCountDownTimer.cancel();
					 mPlayer = MediaPlayer.create(PlayGameActivity.this,
								R.raw.fail);
						mPlayer.start();
						mPlayer.setOnCompletionListener(new OnCompletionListener() {

							public void onCompletion(MediaPlayer mp) {
								mPlayer.release();
								mPlayer = null;
								best = preferences.getInt("bestScore", -1);
								if(best< score){
									best = score;
									editor.putInt("bestScore", best);
								}
								overScore.setText("Score: "+score);
								bestScore.setText("Best: "+best);
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
				if(checkKQ() == false){
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
					textScore.setText(""+score);
					Random md = new Random();
					int select = 1 + md.nextInt(3);
					if(select == 1)
						addQuestion();
					else if(select == 2)
						addSubtractionQuestion();
					else if(select == 3)
						addMultiplicationQuestion();
					i=0;
					if(check == true )
						mCountDownTimer.cancel();
					mProgressBar.setMax(TOTAL_TIME);
					mProgressBar.setProgress(i);
					   mCountDownTimer=new CountDownTimer(2000,100) {

					        @Override
					        public void onTick(long millisUntilFinished) {
					        	check = true;
					           // Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
					            i = i + 100;
					            mProgressBar.setProgress(i);

					        }

					        @Override
					        public void onFinish() {
					        //Do what you want 
					            i = i+ 100;
					            mProgressBar.setProgress(i);
					            mPlayer = MediaPlayer.create(PlayGameActivity.this,
										R.raw.fail);
								mPlayer.start();
								mPlayer.setOnCompletionListener(new OnCompletionListener() {

									public void onCompletion(MediaPlayer mp) {
										mPlayer.release();
										mPlayer = null;
										best = preferences.getInt("bestScore", -1);
										if(best< score){
											best = score;
											editor.putInt("bestScore", best);
										}
										overScore.setText("Score: "+score);
										bestScore.setText("Best: "+best);
										dialogGameOver.show();
									}
								});
					        }
					    };
					    mCountDownTimer.start();
				}
				else{
					if(check == true )
						mCountDownTimer.cancel();
					 mPlayer = MediaPlayer.create(PlayGameActivity.this,
								R.raw.fail);
						mPlayer.start();
						mPlayer.setOnCompletionListener(new OnCompletionListener() {

							public void onCompletion(MediaPlayer mp) {
								mPlayer.release();
								mPlayer = null;
								best = preferences.getInt("bestScore", -1);
								if(best< score){
									best = score;
									editor.putInt("bestScore", best);
								}
								overScore.setText("Score: "+score);
								bestScore.setText("Best: "+best);
								dialogGameOver.show();
							}
						});
					
				}

			}
		});
		
		
		dialogGameOver = new Dialog(this,R.anim.slide_in_up);
		dialogGameOver.setContentView(R.layout.dialog_game_over);
		ImageView restart = (ImageView)dialogGameOver.findViewById(R.id.game_over_restart);
		overScore = (TextView)dialogGameOver.findViewById(R.id.tv_score);
		bestScore = (TextView)dialogGameOver.findViewById(R.id.tv_best_score);
		restart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				Intent intent = new Intent(PlayGameActivity.this,
						PlayGameActivity.class);
				startActivity(intent);
				 overridePendingTransition(R.anim.slide_in_up,R.anim.slide_in_up);
				 dialogGameOver.cancel();
			}
		});

		//fragMgr = getSupportFragmentManager();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addQuestion(){
		checkMath = 1;
		view = inflater.inflate( R.layout.view_question, null );
		textQuestion = (TextView)view.findViewById(R.id.ingame_tv_question);
		Random rand = new Random();
		int cl = rand.nextInt(7);
		view.setBackgroundColor(Color.parseColor(arrayColors[cl]));
		number1 = 1 + rand.nextInt(15);
		number2 = 1 + rand.nextInt(15);
		int sum = number1 + number2;
		nunberRandom = rand.nextInt(2);
		if(nunberRandom  == 0){
			numberresult = (number1+number2);			
		}
		else{
			Random rand2 = new Random();
			if(number1 >2 && number2 >2)
				numberresult = ( sum - 3  + rand2.nextInt(4));
			else
				numberresult = ( sum -1 + rand2.nextInt(4));
		}
		textQuestion.setText("" + number1 + "+" + number2 + "=" + numberresult );
		flipper.addView(view);
		flipper.showNext();
		
		
	}
	
	public void addSubtractionQuestion(){
		checkMath = 2;
		view = inflater.inflate( R.layout.view_question, null );
		textQuestion = (TextView)view.findViewById(R.id.ingame_tv_question);
		Random rand = new Random();
		int cl = rand.nextInt(7);
		view.setBackgroundColor(Color.parseColor(arrayColors[cl]));		
		number2 = 1 + rand.nextInt(15);
		number1 = number2 + 1 + rand.nextInt(15);
		int kq = number1 - number2;
		nunberRandom = rand.nextInt(2);
		if(nunberRandom  == 0){
			numberresult = (number1 - number2);			
		}
		else{
			Random rand2 = new Random();
			if(number1 >2 && number2 >2)
				numberresult = ( kq - 3  + rand2.nextInt(4));
			else
				numberresult = ( kq -1 + rand2.nextInt(4));
		}
		textQuestion.setText("" + number1 + "-" + number2 + "=" + numberresult );
		flipper.addView(view);
		flipper.showNext();
	}
	public void addMultiplicationQuestion(){
		checkMath = 3;
		view = inflater.inflate( R.layout.view_question, null );
		textQuestion = (TextView)view.findViewById(R.id.ingame_tv_question);
		Random rand = new Random();
		int cl = rand.nextInt(7);
		view.setBackgroundColor(Color.parseColor(arrayColors[cl]));		
		number2 = 1 + rand.nextInt(9);
		number1 =  1 + rand.nextInt(9);
		int kq = number1 * number2;
		nunberRandom = rand.nextInt(2);
		if(nunberRandom  == 0){
			numberresult = (number1*number2);			
		}
		else{
			Random rand2 = new Random();
			if(number1 >2 && number2 >2)
				numberresult = ( kq - 3  + rand2.nextInt(4));
			else
				numberresult = ( kq -1 + rand2.nextInt(4));
		}
		textQuestion.setText("" + number1 + "x" + number2 + "=" + numberresult );
		flipper.addView(view);
		flipper.showNext();
		
	}
	public void addDivisionQuestion(){
		checkMath = 4;
		
	}
	
	Boolean checkKQ(){
		if(checkMath == 1){
		if((number1 + number2) == numberresult)
			return true;
		else
			return false;
		}
		if(checkMath == 2){
			if((number1 - number2) == numberresult)
				return true;
			else
				return false;
			}
		if(checkMath == 3){
			if((number1*number2) == numberresult)
				return true;
			else
				return false;
			}
		if(checkMath == 4){
			if((number1/number2) == numberresult)
				return true;
			else
				return false;
			}
		return false;
	}
	
//	public void addTogglableFragment() {	
//		FragmentTransaction transaction = fragMgr.beginTransaction();
//
//		FragmentQuestion fragment = new FragmentQuestion(); //true - Fragment's text should say that this is removable, rather than that it isn't.
//		//transaction.add(R.id.second_fragment_container, fragment, TAG_TOGGLABLE_FRAG); //note - this Fragment will remain in the UI after an orientation change
//		
//		transaction.commit();		
//	}

}
