package ydc.math.braintest.mathlogic;
import ydc.math.braintest.mathlogic.ultis.Constance;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView imgPlay;
	Dialog dialogGameMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		imgPlay = (ImageView)findViewById(R.id.home_button_start);
		imgPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub			
				dialogGameMode.show();

			}
		});
		
		dialogGameMode = new Dialog(this);
		dialogGameMode.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogGameMode.setContentView(R.layout.dialog_game_mode);
		//dialogGameMode.setTitle("Select game Mode");
		Button easyMode = (Button)dialogGameMode.findViewById(R.id.easy_game_mode);
		Button hardMode = (Button)dialogGameMode.findViewById(R.id.hard_game_mode);
		easyMode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constance.checkMode = 1;
				Intent intent = new Intent(MainActivity.this,
						PlayGameActivity.class);
				startActivity(intent);
				 overridePendingTransition(R.anim.slide_in_up,R.anim.slide_in_up);
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
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
