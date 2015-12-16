package huntington.caravan.activity;

import huntington.caravan.view.CaravanView;
import huntington.caravan.view.TitleView;

import huntington.caravan.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class CaravanActivity extends Activity {

	private static final int TOGGLE_SOUND =0 ;
	private static final int TOGGLE_INTERACTIVE =1 ;
	public static boolean soundEnabled=true;
	public static boolean interactiveEnabled = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TitleView titleView = new TitleView(this);
		setContentView(titleView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem toggleSound = menu.add(0, TOGGLE_SOUND, 0, "Sound");
		MenuItem toggleInteractive = menu.add(1, TOGGLE_INTERACTIVE, 1, "GenerateZScore");

		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case TOGGLE_SOUND:
				String soundEnabledText = "Sound on";

				if (soundEnabled) {

					soundEnabled = false;
					soundEnabledText = "Sound off";
					Toast.makeText(this, soundEnabledText, Toast.LENGTH_SHORT).show();

				} else {
					soundEnabled = true;
					Toast.makeText(this, soundEnabledText, Toast.LENGTH_SHORT).show();

				}
				break;



			case TOGGLE_INTERACTIVE:
						String interactiveEnabledText = "Interactive mode";
						if (interactiveEnabled) {
							interactiveEnabled = false;
							interactiveEnabledText = "Generating z score please press play";
							Toast.makeText(this, interactiveEnabledText, Toast.LENGTH_SHORT).show();
							break;
						} else {
							interactiveEnabled = true;
							Toast.makeText(this, interactiveEnabledText, Toast.LENGTH_SHORT).show();
							break;
						}

				}

		return false;
	}
}
