package ydc.math.braintest.mathlogic;
import java.util.Random;

import ydc.math.braintest.mathlogic.ultis.Constance;
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
	private int score_easy = 0;
	private int best_easy = 0;
	private int score_hard = 0;
	private int best_hard = 0;
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
    int [] numberDivi = {2,4,6,9,12,14,15,18,20,21,25,27,28,30,32,35,36,38,39,40,42,44,45,46,48,49,50};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_in_game);
		Log.e("aaaaaaaaaa","bbb" + Constance.checkMode);
		preferences = getPreferences(MODE_PRIVATE);
		editor = preferences.edit();
			try{
				best_hard = preferences.getInt("bestScoreHard", -1);
			}
			catch (Exception e) {
				editor.putInt("bestScoreHard", 0);
				// TODO: handle exception
			}
			try{
				best_easy = preferences.getInt("bestScoreEasy", -1);
			}
			catch (Exception e) {
				editor.putInt("bestScoreEasy", 0);
				// TODO: handle exception
			}
		score_easy = 0;
		score_hard = 0;

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

					Random md = new Random();
					int select = 1;
					if(Constance.checkMode == 1){
						select = 1 + md.nextInt(2);
						score_easy ++;
						textScore.setText(""+score_easy);
					
					}else{
						select = 1 + md.nextInt(4);
						score_hard ++;
						textScore.setText(""+score_hard);
					}
					if(select == 1)
						addQuestion();
					else if(select == 2)
						addSubtractionQuestion();
					else if(select == 3)
						addMultiplicationQuestion();
					else if(select ==4)
						addDivisionQuestion();
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
										if(Constance.checkMode == 1){
											best_easy = preferences.getInt("bestScoreEasy", -1);
											if(best_easy< score_easy){
												best_easy = score_easy;
												editor.putInt("bestScoreEasy", best_easy);
											}
											overScore.setText("Score: "+score_easy);
											bestScore.setText("Best: "+best_easy);
										}else{
											best_hard = preferences.getInt("bestScoreHard", -1);
											if(best_hard< score_hard){
												best_hard = score_hard;
												editor.putInt("bestScoreHard", best_hard);
											
										}
											overScore.setText("Score: "+score_hard);
											bestScore.setText("Best: "+best_hard);
										}
										
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
								if(Constance.checkMode == 1){
									best_easy = preferences.getInt("bestScoreEasy", -1);
									if(best_easy< score_easy){
										best_easy = score_easy;
										editor.putInt("bestScoreEasy", best_easy);
									}
									overScore.setText("Score: "+score_easy);
									bestScore.setText("Best: "+best_easy);
								}else{
									best_hard = preferences.getInt("bestScoreHard", -1);
									if(best_hard< score_hard){
										best_hard = score_hard;
										editor.putInt("bestScoreHard", best_hard);
									
								}
									overScore.setText("Score: "+score_hard);
									bestScore.setText("Best: "+best_hard);
								}
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
					int select = 1;
					Random md = new Random();
					if(Constance.checkMode == 1){
						select = 1 + md.nextInt(2);
						score_easy ++;
						textScore.setText(""+score_easy);
					
					}else{
						select = 1 + md.nextInt(4);
						score_hard ++;
						textScore.setText(""+score_hard);
					}

					if(select == 1)
						addQuestion();
					else if(select == 2)
						addSubtractionQuestion();
					else if(select == 3)
						addMultiplicationQuestion();
					else if(select ==4)
						addDivisionQuestion();
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
										if(Constance.checkMode == 1){
											best_easy = preferences.getInt("bestScoreEasy", -1);
											if(best_easy< score_easy){
												best_easy = score_easy;
												editor.putInt("bestScoreEasy", best_easy);
											}
											overScore.setText("Score: "+score_easy);
											bestScore.setText("Best: "+best_easy);
										}else{
											best_hard = preferences.getInt("bestScoreHard", -1);
											if(best_hard< score_hard){
												best_hard = score_hard;
												editor.putInt("bestScoreHard", best_hard);
											
										}
											overScore.setText("Score: "+score_hard);
											bestScore.setText("Best: "+best_hard);
										}
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
								if(Constance.checkMode == 1){
									best_easy = preferences.getInt("bestScoreEasy", -1);
									if(best_easy< score_easy){
										best_easy = score_easy;
										editor.putInt("bestScoreEasy", best_easy);
									}
									overScore.setText("Score: "+score_easy);
									bestScore.setText("Best: "+best_easy);
								}else{
									best_hard = preferences.getInt("bestScoreHard", -1);
									if(best_hard< score_hard){
										best_hard = score_hard;
										editor.putInt("bestScoreHard", best_hard);
									
								}
									overScore.setText("Score: "+score_hard);
									bestScore.setText("Best: "+best_hard);
								}
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
		number2 = 2 + rand.nextInt(9);
		number1 =  2 + rand.nextInt(9);
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
		view = inflater.inflate( R.layout.view_question, null );
		textQuestion = (TextView)view.findViewById(R.id.ingame_tv_question);
		Random rand = new Random();
		int cl = rand.nextInt(7);
		view.setBackgroundColor(Color.parseColor(arrayColors[cl]));	
		int sl = rand.nextInt(numberDivi.length);
		number1 = numberDivi[sl];
		int [] numberDivi2 = new int[40];
		int j = 0;
		for(int i=1; i<= number1; i ++){
			if(number1%i == 0){
				Log.e("ffffffff" , "fffu " + i);
				numberDivi2[j] = i;
				j++;
			}
		}
		j--;
		Log.e("dviviid","abc " + numberDivi2.length);
		int sl2 = rand.nextInt(j);
		Log.e("dviviid","abc " + sl2);
		number2 = numberDivi2[sl2];
		Log.e("dviviid","abc " + number1);
		Log.e("dviviid","abc " + number2);
		int kq = number1/number2;
		nunberRandom = rand.nextInt(2);
		if(nunberRandom  == 0){
			numberresult = (number1/number2);			
		}
		else{
			Random rand2 = new Random();
			if(number1/number2 >=2)
				numberresult = ( kq - 2  + rand2.nextInt(5));
			else
				numberresult = ( kq -2 + rand2.nextInt(4));
		}
		textQuestion.setText("" + number1 + ":" + number2 + "=" + numberresult );
		flipper.addView(view);
		flipper.showNext();
		
		
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
