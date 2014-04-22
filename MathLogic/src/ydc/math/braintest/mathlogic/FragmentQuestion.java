package ydc.math.braintest.mathlogic;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentQuestion extends Fragment{
	  @Override
	    public void onCreate(Bundle savedInstanceState) {    	
	    	super.onCreate(savedInstanceState);
	    	Log.d("FragmentLifecycle", "onCreate savedInstanceState is " + (savedInstanceState == null?"":"not ") + "null");    	
	    }
	    
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
			Log.d("FragmentLifecycle", "onCreateView savedInstanceState is " + (savedInstanceState == null?"":"not ") + "null");
				View layout = inflater.inflate(R.layout.view_question, container, false);			
				TextView fragmentText = (TextView)layout.findViewById(R.id.ingame_tv_question);
				Typeface mFont = Typeface.createFromAsset(getActivity().getAssets(), "archive.otf");
				fragmentText.setTypeface(mFont);
				fragmentText.setText("2 + 3 = 5");
			return layout;		
		}

}
