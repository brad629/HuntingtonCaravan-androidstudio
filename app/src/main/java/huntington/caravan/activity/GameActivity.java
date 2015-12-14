package huntington.caravan.activity;

import huntington.caravan.R;
import huntington.caravan.view.CaravanView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CaravanView myView = new CaravanView(this);
		setContentView(myView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crazy_eights, menu);
		return true;
	}

}
