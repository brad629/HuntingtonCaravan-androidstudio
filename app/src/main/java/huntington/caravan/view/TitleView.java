package huntington.caravan.view;

import huntington.caravan.R;
import huntington.caravan.activity.GameActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

public class TitleView extends View {
	private Bitmap caravanTitle;
	private Bitmap titleOptions;
	private Bitmap titleOptionsdown;
	private Bitmap titleGraphic;
	private Bitmap playButtonUp;
	private Bitmap playButtonDown;
	private int screenW;
	private int screenH;
	private boolean playButtonPressed;
	private Context myContext;
	private boolean optionPressed;

	public TitleView(Context context) {
		super(context);
		this.myContext = context;
		caravanTitle= BitmapFactory.decodeResource(getResources(), R.drawable.caravan);
		titleOptionsdown = BitmapFactory.decodeResource(getResources(), R.drawable.optionsdown);
		titleOptions = BitmapFactory.decodeResource(getResources(), R.drawable.options);
		titleGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.title_graphic);
		playButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_up);
		playButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_down);
		//playButtonDown.setWidth(titleOptions.getWidth());
		//playButtonUp.setWidth(titleOptions.getWidth());
	}
	
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(titleGraphic,0,0,null);
		canvas.drawBitmap(caravanTitle,(screenW-caravanTitle.getWidth())/2,(int) (screenH*.1),null);
		if (playButtonPressed) {
			canvas.drawBitmap(playButtonDown, (screenW-playButtonUp.getWidth())/2, (int)(screenH*0.7), null);
		} else {
			canvas.drawBitmap(playButtonUp, (screenW-playButtonUp.getWidth())/2, (int)(screenH*0.7), null);
		}
		if (optionPressed) {
			canvas.drawBitmap(titleOptionsdown, (screenW-titleOptions.getWidth())/2, (int)(screenH*0.8), null);
		} else {
			canvas.drawBitmap(titleOptions, (screenW-titleOptions.getWidth())/2, (int)(screenH*0.8), null);
		}
	}

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        System.out.println("SCREEN W: " + screenW);
        System.out.println("SCREEN H: " + screenH);
    }

    @Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		switch(action) {
			case MotionEvent.ACTION_DOWN:
	        	if (x > (screenW-playButtonUp.getWidth())/2 &&
		        		x < ((screenW-playButtonUp.getWidth())/2) + playButtonUp.getWidth() &&
		        		y > (int)(screenH*0.7) &&
		        		y < (int)(screenH*0.7) + playButtonUp.getHeight()) {
		        		playButtonPressed = true;
		        	}
				if (x > (screenW-playButtonUp.getWidth())/2 &&
						x < ((screenW-playButtonUp.getWidth())/2) + playButtonUp.getWidth() &&
						y > (int)(screenH*0.8) &&
						y < (int)(screenH*0.8) + playButtonUp.getHeight()) {
					optionPressed = true;
				}
				break;
				
			case MotionEvent.ACTION_MOVE:
				
				break;
			case MotionEvent.ACTION_UP:
	        	if (playButtonPressed) {
		        	Intent gameIntent = new Intent(myContext, GameActivity.class);
		        	
		        	myContext.startActivity(gameIntent);	        		
	        	}
	        	playButtonPressed = false;
				if (optionPressed) {
					Intent gameIntent = new Intent(myContext, GameActivity.class);

					myContext.startActivity(gameIntent);
				}
				optionPressed = false;
				break;
		}
		
		invalidate();
		return true;
	}
}
