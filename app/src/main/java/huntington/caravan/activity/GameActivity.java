package huntington.caravan.activity;

import huntington.caravan.R;
import huntington.caravan.view.CaravanView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class GameActivity extends Activity {
	private static final int TOGGLE_SOUND =0 ;
	private static final int TOGGLE_INTERACTIVE =0 ;
	public static boolean soundEnabled=true;
	boolean interactiveEnabled = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CaravanView myView = new CaravanView(this);
		setContentView(myView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem toggleSound = menu.add(0, TOGGLE_SOUND, 0, "Sound");
		MenuItem toggleInteractive = menu.add(0, TOGGLE_INTERACTIVE, 1, "interactive");

		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case TOGGLE_SOUND:
				String soundEnabledText="Sound on";

				if(soundEnabled){

					soundEnabled=false;
					soundEnabledText="Sound off";
				}
				else{
					soundEnabled=true;
				}
				Toast.makeText(this, soundEnabledText, Toast.LENGTH_SHORT).show();

		}
		switch (item.getItemId()) {
			case TOGGLE_INTERACTIVE:
				String interactiveEnabledText = "Interactive On";
				if (interactiveEnabled) {
					interactiveEnabled = false;
					interactiveEnabledText = "Interactive Off";
				} else {
					interactiveEnabled = true;
				}
				Toast.makeText(this, interactiveEnabledText, Toast.LENGTH_SHORT).show();
				break;
		}
		return false;
	}
}
