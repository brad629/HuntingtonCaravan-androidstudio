package huntington.caravan.view;
import huntington.caravan.R;
import huntington.caravan.activity.CaravanActivity;
import huntington.caravan.activity.GameActivity;
import huntington.caravan.deck.Card;
import huntington.caravan.player.ComputerPlayer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * This class is the prototype of Crazy Eights.
 * The concept is from James (2013), although I made some small
 * changes mostly in style plus added sounds.
 * @author Ron Coleman
 *
 */
public class CaravanView extends View {
	boolean newgame = true;
	int noninteractivegames = 0;
	int cp1Wins=0;
	int cp2Wins=0;
	String text="";
	boolean startSound=true;
	public boolean soundOn=true;
	int userWinningCaravans=0;
	int oppWinningCaravans=0;
	int turncounter=0;
	int bonusCaravanA = 0;
	int bonusCaravanB = 0;
	int bonusCaravanC = 0;
	int bonusCaravanD = 0;
	int bonusCaravanE = 0;
	int bonusCaravanF = 0;
	private boolean ascending;
	int userCaravanA_rank=0;
	int usercaravanB_rank=0;
	int usercaravanC_rank=0;
	int oppcaravanA_rank=0;
	int oppcaravanB_rank=0;
	int oppcaravanC_rank=0;
	private TextView mytext=null;
	private boolean toastBoolean=false;
	private int screenW;
	private int screenH;
	private int Caravan1X;
	private int Caravan2X;
	private int Caravan3X;
	private int	myCaravanY;
	private int oppCaravanY;
	List<String> card_list= new ArrayList<String>();
	private List<Card> myDeck = new ArrayList<Card>();
	private List<Card> oppDeck = new ArrayList<Card>();
	private int scaledCardW;
	private int scaledCardH;
	//player hands
	private List<Card> myHand = new ArrayList<Card>();
	private List<Card> oppHand = new ArrayList<Card>();
	//Caravan card lists
	private List<Card> userCaravanA = new ArrayList<Card>();
	private List<Card> userCaravanB = new ArrayList<Card>();
	private List<Card> userCaravanC = new ArrayList<Card>();
	private List<Card> oppCaravanA = new ArrayList<Card>();
	private List<Card> oppCaravanB = new ArrayList<Card>();
	private List<Card> oppCaravanC = new ArrayList<Card>();
	//Face Cards --used to modify caravan rank
	private List<Card> userCaravanAFaceCards = new ArrayList<Card>();
	private List<Card> userCaravanBFaceCards = new ArrayList<Card>();
	private List<Card> userCaravanCFaceCards = new ArrayList<Card>();
	private List<Card> oppCaravanAFaceCards = new ArrayList<Card>();
	private List<Card> oppCaravanBFaceCards = new ArrayList<Card>();
	private List<Card> oppCaravanCFaceCards = new ArrayList<Card>();
	//Used in future development to select a specific card from a caravan to play a face card on. limited to the first card now.
	private List<String> userCaravanAFaceCardsIndex = new ArrayList();
	private List<String> userCaravanBFaceCardsIndex = new ArrayList();
	private List<String> userCaravanCFaceCardsIndex = new ArrayList();
	private List<String> oppCaravanAFaceCardsIndex = new ArrayList();
	private List<String> oppCaravanBFaceCardsIndex = new ArrayList();
	private List<String> oppCaravanCFaceCardsIndex = new ArrayList();
	//discard piles
	private List<Card> myDiscardPile = new ArrayList<Card>();
	private List<Card> oppDiscardPile = new ArrayList<Card>();

	private boolean myTurn=true;
	private float scale;

	//Bit Maps

	private Bitmap back1;
	private Bitmap back2;
	private Bitmap back3;
	private Bitmap back4;
	private Bitmap emptycaravan;
	private Bitmap nextCardButton;
	private Bitmap gameGraphic;
	private Bitmap oppemptycaravan;
	private Paint redPaint;
	private Context myContext;
	private static SoundPool sounds;
	private int dropSound;
	private int yeahSound;
	private int evillaf;
	private int trap;
	private Boolean arrived=true;
	private Point dest = new Point(100,100);
	private Point pos = new Point(100,100);
	private Boolean sound = false;
	private int movingCardIdx = -1;
	private int movingX;
	private int movingY;


	public CaravanView(Context context) {
		super(context);
		myContext = context;
		scale = myContext.getResources().getDisplayMetrics().density;
		redPaint = new Paint();
		redPaint.setAntiAlias(true);
		redPaint.setColor(Color.BLACK);
		redPaint.setStyle(Paint.Style.STROKE);
		redPaint.setTextAlign(Paint.Align.LEFT);
		redPaint.setTextSize(scale * 15);
		gameGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.play_screen);

		sound = false;
		if(!CaravanActivity.interactiveEnabled){


			noninteractivegames=0;
			while(noninteractivegames<1000){
				if(newgame=true){
					myInitCards();
					oppInitCards();
					dealCards();
					myDrawCard(oppCaravanA);
					myDrawCard(oppCaravanB);
					myDrawCard(oppCaravanC);
					myDrawCard(userCaravanA);
					myDrawCard(userCaravanB);
					myDrawCard(userCaravanC);
					newgame=false;
				}
				ComputerPlayer.makePlay(oppHand, oppCaravanA, oppCaravanB, oppCaravanC, userCaravanA, userCaravanB, userCaravanC, userCaravanA_rank, usercaravanB_rank, usercaravanC_rank, oppcaravanA_rank, oppcaravanB_rank, oppcaravanC_rank, userCaravanAFaceCards, userCaravanBFaceCards, userCaravanCFaceCards, oppCaravanAFaceCards, oppCaravanBFaceCards, oppCaravanCFaceCards, oppDiscardPile, bonusCaravanD, bonusCaravanE, bonusCaravanF);
				ComputerPlayer.makePlay(myHand, userCaravanA, userCaravanB, userCaravanC, oppCaravanA, oppCaravanB, oppCaravanC, oppcaravanA_rank, oppcaravanB_rank, oppcaravanC_rank, userCaravanA_rank, usercaravanB_rank, usercaravanC_rank, oppCaravanAFaceCards, oppCaravanBFaceCards, oppCaravanCFaceCards, userCaravanAFaceCards, userCaravanBFaceCards, userCaravanCFaceCards, myDiscardPile, bonusCaravanA, bonusCaravanB, bonusCaravanC);
				oppDrawCard(oppHand);
				myDrawCard(myHand);
				//System.out.println("the computer played: "+tempPlay);
				calculateScore();
				System.out.println(cp1Wins);
				System.out.println(cp2Wins);
				if(noninteractivegames==10){

					double z = (cp1Wins - noninteractivegames/2)/Math.sqrt(noninteractivegames/2/2);
					System.out.println(noninteractivegames+" games");
					System.out.println("cp1 wins: "+cp1Wins);
					System.out.println("cp2 wins "+cp2Wins);
					System.out.println("Zscore= "+z);
				}

			}


			}



		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);

		dropSound = sounds.load(myContext, huntington.caravan.R.raw.blip2, 1);




	}
	@Override
	public void onSizeChanged (int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.back1);
		Bitmap tempBitmap2 = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.back2);
		Bitmap tempBitmap3 = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.emptycaravan);
		Bitmap tempBitmap4 = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.emptycaravanopp);
		scaledCardW = (int) (screenW / 8);
		scaledCardH = (int) (scaledCardW * 1.28);
		back1 = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);
		back2 = Bitmap.createScaledBitmap(tempBitmap2, scaledCardW, scaledCardH, false);
		oppemptycaravan = Bitmap.createScaledBitmap(tempBitmap4, scaledCardW, scaledCardH, false);
		emptycaravan = Bitmap.createScaledBitmap(tempBitmap3, scaledCardW, scaledCardH, false);
		nextCardButton = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_next);
		myInitCards();
		oppInitCards();
		dealCards();



	}

	protected void onDraw(Canvas canvas) {

		int value = 0;

		canvas.drawBitmap(gameGraphic, 0, 0, null);
		canvas.drawBitmap(gameGraphic, 0, 0, null);
		//canvas.drawCircle(pos.x, pos.y, radius, redPaint);
		//draw opp hand
		for (int i = 0; i < oppHand.size(); i++) {
			//shows the opponent hand for debugging
//			canvas.drawBitmap(oppHand.get(i).getBitmap(),
//					i*(scaledCardW+1),
//					redPaint.getTextSize()+(100*scale),
//					null);
			canvas.drawBitmap(back1,
					i*(scale+back1.getWidth()),
					redPaint.getTextSize()+(100*scale),
					null);
		}
		// Simulate the card decks
		canvas.drawBitmap(back1, (screenW/2)-back1.getWidth()-300, redPaint.getTextSize()+(scale), null);
		canvas.drawBitmap(back2, (screenW/2)-back1.getWidth()-300, screenH-scaledCardH-redPaint.getTextSize()-(scale), null);
		//check personal discard piles
		if (!oppDiscardPile.isEmpty()) {
			canvas.drawBitmap(oppDiscardPile.get(0).getBitmap(),
					(screenW/2)+300,
					redPaint.getTextSize()+(scale),
					null);
		}
		if (!myDiscardPile.isEmpty()) {
			canvas.drawBitmap(myDiscardPile.get(0).getBitmap(),
					(screenW/2)+300,
					screenH-scaledCardH-redPaint.getTextSize()-(scale),
					null);
		}
		else{
			canvas.drawBitmap(back2, (screenW/2)+300,
					screenH-scaledCardH-redPaint.getTextSize()-(scale),
					null);
		}
		//if hand goes off the screen
		if (myHand.size() > 8) {
			canvas.drawBitmap(nextCardButton,
					screenW-nextCardButton.getWidth()-(30*scale),
					screenH-nextCardButton.getHeight()-scaledCardH-(90*scale),
					null);
		}
		//x,y cords for caravans
		Caravan1X = (screenW/2)-(screenW/4)-emptycaravan.getWidth();
		Caravan2X = (screenW/2)-emptycaravan.getWidth()/2;
		Caravan3X = (screenW/2)+(screenW/4);
		myCaravanY =screenH/2+scaledCardH/4;
		oppCaravanY = screenH/2-scaledCardH/4-100;
		// ints for caravan values

		//draw the caravan place holders or the caravan pile
		if(userCaravanA.isEmpty()){
			canvas.drawBitmap(emptycaravan, Caravan1X, myCaravanY, null);

		}
		else{
			int space= 0;
			int faceCounter = 0;
			for(int i = userCaravanA.size(); --i >= 0;) {

				canvas.drawBitmap(userCaravanA.get(i).getBitmap(), Caravan1X + i + space, myCaravanY, null);

						if (userCaravanAFaceCards.size()!=0&&faceCounter==0) {
							canvas.drawBitmap(userCaravanAFaceCards.get(0).getBitmap(), Caravan1X + i , myCaravanY + 30, null);
							faceCounter = faceCounter + 1;


				}

				space=space+15;

			}

		}
		if(userCaravanB.isEmpty()){
			canvas.drawBitmap(emptycaravan, Caravan2X, myCaravanY, null);

		}
		else {
			int space = 0;
			int faceCounter = 0;
			for (int i = userCaravanB.size(); --i >= 0; ) {

				canvas.drawBitmap(userCaravanB.get(i).getBitmap(), Caravan2X + i + space, myCaravanY, null);


					if (userCaravanBFaceCards.size()!=0&&faceCounter==0) {
						canvas.drawBitmap(userCaravanBFaceCards.get(0).getBitmap(), Caravan2X + i , myCaravanY + 30, null);
						faceCounter = faceCounter + 1;




				}
				space = space + 15;
			}
		}
		if(userCaravanC.isEmpty()){
			canvas.drawBitmap(emptycaravan, Caravan3X, myCaravanY, null);

		}
		else{
			int space= 0;
			int faceCounter=0;
			for(int i = userCaravanC.size(); --i >= 0;) {

				canvas.drawBitmap(userCaravanC.get(i).getBitmap(),Caravan3X+i+space ,myCaravanY , null);
				if (userCaravanCFaceCards.size()!=0&&faceCounter==0) {
						canvas.drawBitmap(userCaravanCFaceCards.get(0).getBitmap(), Caravan3X + i , myCaravanY + 30, null);
						faceCounter = faceCounter + 1;
					}


				space=space+15;
			}

		}
		if(oppCaravanA.isEmpty()){
			canvas.drawBitmap(oppemptycaravan, Caravan1X, oppCaravanY, null);

		}
		else {
			int space = 0;
			int faceCounter = 0;
			for (int i = oppCaravanA.size(); --i >= 0; ) {

				canvas.drawBitmap(oppCaravanA.get(i).getBitmap(), Caravan1X + i + space, oppCaravanY, null);
				if (oppCaravanAFaceCards.size()!=0&&faceCounter==0) {
						canvas.drawBitmap(oppCaravanAFaceCards.get(0).getBitmap(), Caravan1X + i + space, oppCaravanY + 30, null);
						faceCounter = faceCounter + 1;




				}
				space = space + 15;
			}
		}
		if(oppCaravanB.isEmpty()){
			canvas.drawBitmap(oppemptycaravan, Caravan2X, oppCaravanY, null);

		}
		else {
			int space = 0;
			int faceCounter = 0;
			for (int i = oppCaravanB.size(); --i >= 0; ) {

				canvas.drawBitmap(oppCaravanB.get(i).getBitmap(), Caravan2X + i + space, oppCaravanY, null);
				if (oppCaravanBFaceCards.size()!=0&&faceCounter==0) {
						canvas.drawBitmap(oppCaravanBFaceCards.get(0).getBitmap(), Caravan2X + i + space, oppCaravanY + 30, null);
						faceCounter = faceCounter + 1;



				}
				space = space + 15;
			}
		}
		if(oppCaravanC.isEmpty()){
			canvas.drawBitmap(oppemptycaravan, Caravan3X, oppCaravanY, null);

		}
		else{
			int space=0;
			int faceCounter=0;
			for (int i = oppCaravanC.size(); --i >= 0;) {

				canvas.drawBitmap(oppCaravanC.get(i).getBitmap(),Caravan3X+i+space ,oppCaravanY , null);
				if (oppCaravanCFaceCards.size()!=0&&faceCounter==0) {
						canvas.drawBitmap(oppCaravanCFaceCards.get(0).getBitmap(), Caravan3X + i + space, oppCaravanY + 30, null);
						faceCounter = faceCounter + 1;


				}
				space=space+15;
			}

		}
		for (int i = 0; i < myHand.size(); i++) {
			if (i == movingCardIdx) {
				canvas.drawBitmap(myHand.get(i).getBitmap(),
						movingX,
						movingY,
						null);
			} else {
				if (i < 8) {
					canvas.drawBitmap(myHand.get(i).getBitmap(),
							i*(scaledCardW+1),
							screenH-scaledCardH-redPaint.getTextSize()-(100*scale),
							null);
				}
			}
		}

		canvas.drawText("Caravan A: "+userCaravanA_rank,Caravan1X ,myCaravanY-3,redPaint);
		canvas.drawText("Caravan B: "+usercaravanB_rank,Caravan2X ,myCaravanY-3,redPaint);
		canvas.drawText("Caravan C: "+usercaravanC_rank,Caravan3X ,myCaravanY-3,redPaint);
		canvas.drawText("Caravan D: "+oppcaravanA_rank,Caravan1X ,oppCaravanY-3,redPaint);
		canvas.drawText("Caravan E: "+oppcaravanB_rank,Caravan2X ,oppCaravanY-3,redPaint);
		canvas.drawText("Caravan F: "+oppcaravanC_rank,Caravan3X ,oppCaravanY-3,redPaint);

		/*
		if (pos != dest) {
			arrived = false;
			double sx = dest.x - pos.x;
			double sy = dest.y - pos.y;
			double s = Math.sqrt(sx * sx + sy * sy);
			double theta = Math.asin(sx / s);
			double dx = 10 * Math.sin(theta);
			double yt = Math.acos(sy / s);
			double dy = 10 * Math.cos(yt);
			int xx = pos.x + (int) dx;
			int yy = pos.y + (int) dy;
			sx = dest.x - pos.x;
			sy = dest.y - pos.y;
			pos.set(xx, yy);
			if ((Math.abs(sx) <= 10) && (Math.abs(sy) <= 10)) {
				arrived = true;
				pos.set(dest.x, dest.y);
				if (sound ==true){
					AudioManager audioManager = (AudioManager) this.myContext.getSystemService(Context.AUDIO_SERVICE);
					float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
					sounds.play(dropSound, volume, volume, 1, 0, 1);
					sound = false;
				}


			}







		}*/

		invalidate();


	}


	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();

		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action) {
			case MotionEvent.ACTION_DOWN:
				// on Caravan click asks to remove the caravan
				if (myTurn) {
					if (
							x > (59) &&
							x < (231) &&
							y > (myCaravanY) &&
							y < (myCaravanY + (150 * scale))
							) {

							showDeleteCaravanDialog(userCaravanA, userCaravanAFaceCards, userCaravanAFaceCardsIndex);

							break;

					}
					else if (
							x > (310) &&
							x < (510) &&
							y > (myCaravanY) &&
							y < (myCaravanY + (150 * scale))
							) {
						showDeleteCaravanDialog(userCaravanB, userCaravanBFaceCards, userCaravanBFaceCardsIndex);

						break;
					}
					else if (
							x > (540) &&
							x < (734) &&
							y > (myCaravanY) &&
							y < (myCaravanY + (150 * scale))
							) {
						showDeleteCaravanDialog(userCaravanC, userCaravanCFaceCards, userCaravanBFaceCardsIndex);

						break;
					}


					//set movingcardidx
					for (int i = 0; i < 8; i++) {
						if (x > i * (scaledCardW + 1) && x < i * (scaledCardW + 1) + scaledCardW &&
								y > screenH - scaledCardH - redPaint.getTextSize() - (100 * scale)) {
							movingCardIdx = i;
							card_list.clear();
							if (myHand.get(movingCardIdx).getScoreValue() == 0 ) {
								if(userCaravanA.isEmpty()||userCaravanB.isEmpty()||userCaravanC.isEmpty()){
									Toast invalid = Toast.makeText(myContext, "Please start each owned caravan with a number card or ace before playing face cards.", Toast.LENGTH_LONG);
									invalid.show();
									break;

								}
								//if caravans are empty draw place holders
								if(!userCaravanA.isEmpty()){
									card_list.add("Caravan A:" + userCaravanA.get(userCaravanA.size()-1).getName());
								}
								if(!userCaravanB.isEmpty()){
									card_list.add("Caravan B:"+userCaravanB.get(userCaravanB.size()-1).getName());
								}
								if(!userCaravanC.isEmpty()){
									card_list.add("Caravan C:"+userCaravanC.get(userCaravanC.size()-1).getName());
								}
								if(!oppCaravanA.isEmpty()){
									card_list.add("Caravan D:"+oppCaravanA.get(oppCaravanA.size()-1).getName());
								}
								if(!oppCaravanB.isEmpty()){
									card_list.add("Caravan E:"+oppCaravanB.get(oppCaravanB.size()-1).getName());
								}
								if(!oppCaravanC.isEmpty()){
									card_list.add("Caravan F:"+oppCaravanC.get(oppCaravanC.size()-1).getName());
								}
								showChooseCardDialog(movingCardIdx,myHand.get(movingCardIdx).getRank());


							}



						}
						movingX = x - (int) (30 * scale);
						movingY = y - (int) (70 * scale);
					}

				}

				break;
			case MotionEvent.ACTION_MOVE:
				if(movingCardIdx>-1 && myHand.get(movingCardIdx).getScoreValue()==0){
					movingCardIdx = -1;
					break;
				}

				movingX = x - (int) (30 * scale);
				movingY = y - (int) (70 * scale);


				break;
			case MotionEvent.ACTION_UP:
				if (myTurn = true) {

					//checking what caravan the user is trying to make a play on
					//#1
					//System.out.println(((screenW/2)-(screenW/4)-emptycaravan.getWidth())-(100*scale));
					//System.out.println(x);
					//System.out.println(y);
					if((movingCardIdx>-1) && myHand.get(movingCardIdx).getScoreValue()==0){
						movingCardIdx = -1;
						break;
					}
					//these if statements check to see where the card was dropped
					if (movingCardIdx > -1 &&
							x > (700) &&
							y > 950
							) {
						myDiscardPile.add(myHand.get(movingCardIdx));
						myHand.remove(movingCardIdx);
						myDrawCard(myHand);
//						if(CaravanActivity.soundEnabled) {
//							AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
//							float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//							sounds.play(dropSound, volume, volume, 1, 0, 1);
//
//
//						}
						myTurn=false;
						makeComputerPlay();

					}
					if (movingCardIdx > -1 &&
							x > (Caravan1X - (100 * scale)) &&
							x < (Caravan1X + (100 * scale)) &&
							y > (oppCaravanY) &&
							y < (myCaravanY - 15)
							) {
						if (!(myHand.get(movingCardIdx).getScoreValue() == 0)) {
							movingCardIdx = -1;
							Toast invalid = Toast.makeText(myContext, "Only Face Cards can be played on opponent caravans", Toast.LENGTH_SHORT);
							invalid.show();
							break;
						}
					}



					else if (movingCardIdx > -1 &&
							x > (Caravan2X - (100 * scale)) &&
							x < (Caravan2X + (100 * scale)) &&
							y > (oppCaravanY - (100 * scale)) &&
							y < (myCaravanY - 15)
							) {
						if(!(myHand.get(movingCardIdx).getScoreValue()==0)) {
							movingCardIdx=-1;
							Toast invalid = Toast.makeText(myContext, "Only Face Cards can be played on opponent caravans", Toast.LENGTH_SHORT);
							invalid.show();
							break;
						}


					}
					else if (movingCardIdx > -1 &&
							x > (Caravan3X - (100 * scale)) &&
							x < (Caravan3X + (100 * scale)) &&
							y > (oppCaravanY - (100 * scale)) &&
							y < (myCaravanY - 15)
							) {
						if(!(myHand.get(movingCardIdx).getScoreValue()==0)) {
							movingCardIdx=-1;
							Toast invalid = Toast.makeText(myContext, "Only Face Cards can be played on opponent caravans", Toast.LENGTH_SHORT);
							invalid.show();
							break;
						}

					}
					else if (movingCardIdx > -1 &&
							x > (Caravan1X - (100 * scale)) &&
							x < (Caravan1X + (100 * scale)) &&
							y > (myCaravanY) &&
							y < (myCaravanY + (150 * scale))
							) {

						if(userCaravanA.size()>=1){
							if(userCaravanB.isEmpty()||userCaravanC.isEmpty()){
								Toast invalid = Toast.makeText(myContext, "Please start each owned caravan with a number card before adding to this caravan", Toast.LENGTH_LONG);
								invalid.show();
								movingCardIdx=-1;
								break;

							}
							if(myHand.get(movingCardIdx).getRank()==userCaravanA.get(userCaravanA.size()-1).getRank()){
								Toast invalid = Toast.makeText(myContext, "Cards of equal rank cant be played on eachother", Toast.LENGTH_LONG);
								invalid.show();
								movingCardIdx=-1;
								break;

							}

						}
						if (userCaravanA.size() >1) {


							//if the pile is there and already ascending
							if (userCaravanA.get(1).getScoreValue() - userCaravanA.get(0).getScoreValue() > 0 && userCaravanA.get(0).getScoreValue() - myHand.get(movingCardIdx).getScoreValue() > 0) {
								userCaravanA.add(0, myHand.get(movingCardIdx));



								myHand.remove(movingCardIdx);
								myDrawCard(myHand);
//								if(CaravanActivity.soundEnabled) {
//									AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
//									float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//									sounds.play(dropSound, volume, volume, 1, 0, 1);
//
//
//								}

								calculateScore();
								myTurn = false;
								calculateScore();
								makeComputerPlay();
							}
							//if the pile is there and non ascending
							else if (userCaravanA.get(1).getScoreValue() - userCaravanA.get(0).getScoreValue() < 0 && userCaravanA.get(0).getScoreValue() - myHand.get(movingCardIdx).getScoreValue() < 0) {
								userCaravanA.add(0, myHand.get(movingCardIdx));


								myHand.remove(movingCardIdx);
								myDrawCard(myHand);

//								if(CaravanActivity.soundEnabled) {
//									AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
//									float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//									sounds.play(dropSound, volume, volume, 1, 0, 1);
//
//
//								}
								calculateScore();
								myTurn = false;
								calculateScore();
								makeComputerPlay();

							} else {
								Toast invalid = Toast.makeText(myContext, "Please Follow Ascending or Descending Pattern", Toast.LENGTH_SHORT);
								invalid.show();
							}
						}
						else {
							userCaravanA.add(0, myHand.get(movingCardIdx));
							for (int i = userCaravanA.size(); --i >= 0; ) {
								userCaravanA_rank = userCaravanA.get(i).getScoreValue() + userCaravanA_rank;
							}
							myHand.remove(movingCardIdx);
							myDrawCard(myHand);
//							if(CaravanActivity.soundEnabled) {
//								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
//								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//								sounds.play(dropSound, volume, volume, 1, 0, 1);
//
//
//							}

							myTurn = false;
							calculateScore();
							makeComputerPlay();
						}
					} else if (movingCardIdx > -1 &&
							x > (Caravan2X - (100 * scale)) &&
							x < (Caravan2X + (100 * scale)) &&
							y > (myCaravanY) &&
							y < (myCaravanY + (150 * scale))
							) {
						if (userCaravanB.size() >= 1) {

							if(userCaravanA.isEmpty()||userCaravanC.isEmpty()){
								Toast invalid = Toast.makeText(myContext, "Please start each owned caravan with a number card before adding to this caravan", Toast.LENGTH_LONG);
								invalid.show();
								break;

							}
							if(myHand.get(movingCardIdx).getRank()==userCaravanB.get(userCaravanB.size()-1).getRank()){
								Toast invalid = Toast.makeText(myContext, "Cards of equal rank cant be played on eachother", Toast.LENGTH_LONG);
								invalid.show();
								movingCardIdx=-1;
								break;

							}
						}
						if (userCaravanB.size() > 1) {


							//if the pile is there and already ascending
							if (userCaravanB.get(1).getScoreValue() - userCaravanB.get(0).getScoreValue() > 0 && userCaravanB.get(0).getScoreValue() - myHand.get(movingCardIdx).getScoreValue() > 0) {
								userCaravanB.add(0, myHand.get(movingCardIdx));
								myHand.remove(movingCardIdx);
								myDrawCard(myHand);
//								if(CaravanActivity.soundEnabled) {
//									AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
//									float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//									sounds.play(dropSound, volume, volume, 1, 0, 1);
//
//
//								}

								myTurn = false;
								calculateScore();
								makeComputerPlay();
							}
							//if the pile is there and non ascending
							else if (userCaravanB.get(1).getScoreValue() - userCaravanB.get(0).getScoreValue() < 0 && userCaravanB.get(0).getScoreValue() - myHand.get(movingCardIdx).getScoreValue() < 0) {
								userCaravanB.add(0, myHand.get(movingCardIdx));
								myHand.remove(movingCardIdx);
								myDrawCard(myHand);

//								if(CaravanActivity.soundEnabled) {
//									AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
//									float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//									sounds.play(dropSound, volume, volume, 1, 0, 1);
//
//
//								}
								myTurn = false;
								calculateScore();
								makeComputerPlay();

							} else {
								Toast invalid = Toast.makeText(myContext, "Please Follow Ascending or Descending Pattern", Toast.LENGTH_SHORT);
								invalid.show();
							}
						}
						else {
							userCaravanB.add(0, myHand.get(movingCardIdx));
							myHand.remove(movingCardIdx);
							myDrawCard(myHand);

//							if(CaravanActivity.soundEnabled) {
//								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
//								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//								sounds.play(dropSound, volume, volume, 1, 0, 1);
//
//
//							}
							myTurn = false;
							calculateScore();
							makeComputerPlay();
						}
					} else if (movingCardIdx > -1 &&
							x > (Caravan3X - (100 * scale)) &&
							x < (Caravan3X + (100 * scale)) &&
							y > (myCaravanY) &&
							y < (myCaravanY + (150 * scale))
							) {
					if (userCaravanC.size() >= 1) {
							if(userCaravanB.isEmpty()||userCaravanA.isEmpty()){
								Toast invalid = Toast.makeText(myContext, "Please start each owned caravan with a number card before adding to this caravan", Toast.LENGTH_LONG);
								invalid.show();
								break;

							}
							if(myHand.get(movingCardIdx).getRank()==userCaravanC.get(userCaravanC.size()-1).getRank()){
								Toast invalid = Toast.makeText(myContext, "Cards of equal rank cant be played on eachother", Toast.LENGTH_LONG);
								invalid.show();
								movingCardIdx=-1;
								break;

							}

						}
						if (userCaravanC.size() > 1) {


							//if the pile is there and already ascending
							if (userCaravanC.get(1).getScoreValue() - userCaravanC.get(0).getScoreValue() > 0 && userCaravanC.get(0).getScoreValue() - myHand.get(movingCardIdx).getScoreValue() > 0) {
								userCaravanC.add(0, myHand.get(movingCardIdx));
								myHand.remove(movingCardIdx);
								myDrawCard(myHand);

//								if(CaravanActivity.soundEnabled) {
//									AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
//									float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//									sounds.play(dropSound, volume, volume, 1, 0, 1);
//
//
//								}
								myTurn = false;
								calculateScore();
								makeComputerPlay();
							}
							//if the pile is there and non ascending
							else if (userCaravanC.get(1).getScoreValue() - userCaravanC.get(0).getScoreValue() < 0 && userCaravanC.get(0).getScoreValue() - myHand.get(movingCardIdx).getScoreValue() < 0) {
								userCaravanC.add(0, myHand.get(movingCardIdx));

								myHand.remove(movingCardIdx);
								myDrawCard(myHand);

//								if(CaravanActivity.soundEnabled) {
//									AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
//									float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//									sounds.play(dropSound, volume, volume, 1, 0, 1);
//
//
//								}
								myTurn = false;
								calculateScore();
								makeComputerPlay();

							} else {
								Toast invalid = Toast.makeText(myContext, "Please Follow Ascending or Descending Pattern", Toast.LENGTH_SHORT);
								invalid.show();
							}
						}
						else {
							userCaravanC.add(0, myHand.get(movingCardIdx));
							myHand.remove(movingCardIdx);
							myDrawCard(myHand);
//							if(CaravanActivity.soundEnabled) {
//								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
//								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//								sounds.play(dropSound, volume, volume, 1, 0, 1);
//
//
//							}

							myTurn = false;
							calculateScore();
							makeComputerPlay();
						}
					}
					//System.out.println("me C"+(Caravan3X  -(100*scale))+","+(Caravan3X  +(100*scale))+','+(myCaravanY)+','+(myCaravanY-(300*scale)));
					movingCardIdx = -1;


					break;
				}

				invalidate();
				return true;
		}
		invalidate();
		return true;
	}


	private void calculateScore(){

		int rank=0;
		for (int i = userCaravanA.size(); --i >= 0; ) {
			rank = rank+userCaravanA.get(i).getScoreValue();
		}
		userCaravanA_rank=rank+bonusCaravanA;
		rank=0;
		for (int i = userCaravanB.size(); --i >= 0; ) {
			rank = rank+userCaravanB.get(i).getScoreValue();
		}
		usercaravanB_rank=rank+bonusCaravanB;
		rank=0;
		for (int i = userCaravanC.size(); --i >= 0; ) {
			rank = rank+userCaravanC.get(i).getScoreValue();
		}
		usercaravanC_rank=rank+bonusCaravanC;
		rank=0;
		for (int i = oppCaravanA.size(); --i >= 0; ) {

			rank = rank+oppCaravanA.get(i).getScoreValue();
		}
		oppcaravanA_rank=rank+bonusCaravanD;
		rank=0;
		for (int i = oppCaravanB.size(); --i >= 0; ) {
			rank = rank+oppCaravanB.get(i).getScoreValue();
		}
		oppcaravanB_rank=rank+bonusCaravanE;
		rank=0;
		for (int i = oppCaravanC.size(); --i >= 0; ) {
			rank = rank+oppCaravanC.get(i).getScoreValue();
		}
		oppcaravanC_rank=rank+bonusCaravanF;

		if(oppcaravanA_rank>20&&oppcaravanA_rank<27&&oppcaravanA_rank>userCaravanA_rank){
			oppWinningCaravans=oppWinningCaravans+1;
		}
		if(oppcaravanB_rank>20&&oppcaravanB_rank<27&&oppcaravanB_rank>usercaravanB_rank){
			oppWinningCaravans=oppWinningCaravans+1;
		}
		if(oppcaravanC_rank>20&&oppcaravanC_rank<27&&oppcaravanC_rank>usercaravanC_rank){
			oppWinningCaravans=oppWinningCaravans+1;
		}
		if(userCaravanA_rank>20&&userCaravanA_rank<27&&userCaravanA_rank>oppcaravanA_rank){
			userWinningCaravans=userWinningCaravans+1;
		}
		if(usercaravanB_rank>20&&usercaravanB_rank<27&&usercaravanB_rank>oppcaravanB_rank){
			userWinningCaravans=userWinningCaravans+1;
		}
		if(usercaravanC_rank>20&&usercaravanC_rank<27&&usercaravanC_rank>oppcaravanC_rank){
			userWinningCaravans=userWinningCaravans+1;
		}
		if(userWinningCaravans>=2||oppWinningCaravans>=2){
			if(CaravanActivity.interactiveEnabled) {
				showPlayAgainDialog(userWinningCaravans, oppWinningCaravans);
			}
			else if(!CaravanActivity.interactiveEnabled&& userWinningCaravans>=2){
				newgame=true;
				cp2Wins=cp2Wins+1;
				noninteractivegames=noninteractivegames+1;
				oppCaravanA.clear();
				oppCaravanB.clear();
				oppCaravanC.clear();
				userCaravanA.clear();
				userCaravanB.clear();
				userCaravanC.clear();
				oppcaravanA_rank=0;
				oppcaravanC_rank=0;
				oppcaravanB_rank=0;
				userCaravanA_rank=0;
				usercaravanB_rank=0;
				usercaravanC_rank=0;
				userWinningCaravans=0;
				oppWinningCaravans=0;
			}
			else if(!CaravanActivity.interactiveEnabled&& oppWinningCaravans>=2){
				newgame=true;
				cp1Wins=cp1Wins+1;
				noninteractivegames=noninteractivegames+1;
				oppCaravanA.clear();
				oppCaravanB.clear();
				oppCaravanC.clear();
				userCaravanA.clear();
				userCaravanB.clear();
				userCaravanC.clear();
				oppcaravanA_rank=0;
				oppcaravanC_rank=0;
				oppcaravanB_rank=0;
				userCaravanA_rank=0;
				usercaravanB_rank=0;
				usercaravanC_rank=0;
				userWinningCaravans=0;
				oppWinningCaravans=0;
			}
		}






	}
	private void showDeleteCaravanDialog(final List<Card> caravan,final List<Card> faceCards, final List<String>faceIndexValue){
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
					case DialogInterface.BUTTON_POSITIVE:
						//Yes button clicked
						myDiscardPile.addAll(caravan);
						caravan.clear();
						faceCards.clear();
						faceIndexValue.clear();
						calculateScore();

						myTurn=false;
						makeComputerPlay();
						break;

					case DialogInterface.BUTTON_NEGATIVE:
						//No button clicked
						break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
		builder.setMessage("Would you like to remove this Caravan?").setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
	}

	private void showPlayAgainDialog(int userWinningCaravans,int oppWinningCaravans){
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
					case DialogInterface.BUTTON_POSITIVE:
						Intent gameIntent = new Intent(myContext, GameActivity.class);

						myContext.startActivity(gameIntent);

					case DialogInterface.BUTTON_NEGATIVE:
						Intent homescreen = new Intent(myContext, CaravanActivity.class);

						myContext.startActivity(homescreen);
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
		if(userWinningCaravans>oppWinningCaravans){
			 text = "you win! ";

		}
		else{
			 text="you lose! ";
		}
		builder.setMessage(text+"Would you like to play again?").setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
	}



//choose a card to play the face card on
	private void showChooseCardDialog(final int amovingCardIdx, final int cardrank) {
		final Dialog choosecardDialog = new Dialog(myContext);
		choosecardDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		choosecardDialog.setContentView(R.layout.make_move);
		final TextView tv = (TextView)findViewById(R.id.chooseCardText);
		//change messege for played card
		if (cardrank==11){
			Toast directions = Toast.makeText(myContext, "Select a card to remove from the table.", Toast.LENGTH_LONG);
			directions.show();
		}

		else if (cardrank==12){
			Toast directions = Toast.makeText(myContext, "Select a card to decrease its value by half.", Toast.LENGTH_LONG);
			directions.show();
		}

		else if (cardrank==13){
			Toast directions = Toast.makeText(myContext, "Select a card to double the vale.", Toast.LENGTH_LONG);
			directions.show();
		}

		String[] array_Card_list = new String[card_list.size()];
		array_Card_list = card_list.toArray(array_Card_list);

		final Spinner cardSpinner = (Spinner) choosecardDialog.findViewById(R.id.CardSpinner);
		ArrayAdapter adapter = new ArrayAdapter(myContext, android.R.layout.simple_spinner_item,new ArrayList<CharSequence>());
		adapter.add("");
		for(int i=0;i<card_list.size();i++){
			adapter.add(array_Card_list[i]);
		}
		cardSpinner.setAdapter(adapter);
		int initialposition = cardSpinner.getSelectedItemPosition();
		cardSpinner.setSelection(initialposition,false);
		cardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				String results = cardSpinner.getSelectedItem().toString();
				//System.out.println(results);

				Toast.makeText(myContext, "You chose to make play on " + results, Toast.LENGTH_SHORT).show();
//MOVE LOGIC UP?
				// if statements for face cards the line up part is in the draw part of code
				String[] sep = results.split(":");
				//System.out.println(sep[0]);
				//if card selected is a jack
				//parse results to decide what face card action to take
				if (cardrank == 11) {
					if (sep[0].equals("Caravan A")) {
						if (userCaravanAFaceCards.size() != 0) {
							userCaravanAFaceCards.clear();
							userCaravanAFaceCardsIndex.clear();
							bonusCaravanA = 0;
						}
						userCaravanA.remove(userCaravanA.size() - 1);

						myHand.remove(amovingCardIdx);
						myDrawCard(myHand);
						if(CaravanActivity.soundEnabled) {
							AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
							float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
							sounds.play(dropSound, volume, volume, 1, 0, 1);


						}
						myTurn = false;
						calculateScore();
						makeComputerPlay();
						choosecardDialog.dismiss();
					}

					if (sep[0].equals("Caravan B")) {
						if (userCaravanBFaceCards.size() != 0) {
							userCaravanBFaceCards.clear();
							userCaravanBFaceCardsIndex.clear();
							bonusCaravanB = 0;
						}
						userCaravanB.remove(userCaravanB.size() - 1);

						myHand.remove(amovingCardIdx);
						if(CaravanActivity.soundEnabled) {
							AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
							float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
							sounds.play(dropSound, volume, volume, 1, 0, 1);


						}
						myDrawCard(myHand);
						myTurn = false;
						calculateScore();
						makeComputerPlay();
						choosecardDialog.dismiss();


					}
					if (sep[0].equals("Caravan C")) {
						if (userCaravanCFaceCards.size() != 0) {
							userCaravanCFaceCards.clear();
							userCaravanCFaceCardsIndex.clear();
							bonusCaravanC = 0;
						}
						userCaravanC.remove(userCaravanC.size() - 1);

						myHand.remove(amovingCardIdx);
						myDrawCard(myHand);
						myTurn = false;
						if(CaravanActivity.soundEnabled) {
							AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
							float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
							sounds.play(dropSound, volume, volume, 1, 0, 1);


						}
						calculateScore();
						makeComputerPlay();
						choosecardDialog.dismiss();


					}
					if (sep[0].equals("Caravan D")) {
						if (oppCaravanAFaceCards.size() != 0) {
							oppCaravanAFaceCards.clear();
							oppCaravanAFaceCardsIndex.clear();
							bonusCaravanD = 0;
						}
						oppCaravanA.remove(oppCaravanA.size() - 1);

						myHand.remove(amovingCardIdx);
						myDrawCard(myHand);
						if(CaravanActivity.soundEnabled) {
							AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
							float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
							sounds.play(dropSound, volume, volume, 1, 0, 1);


						}
						myTurn = false;
						calculateScore();
						makeComputerPlay();
						choosecardDialog.dismiss();


					}
					if (sep[0].equals("Caravan E")) {
						if (oppCaravanBFaceCards.size() != 0) {
							oppCaravanBFaceCards.clear();
							oppCaravanBFaceCardsIndex.clear();
							bonusCaravanE = 0;
						}
						oppCaravanB.remove(oppCaravanB.size() - 1);

						myHand.remove(amovingCardIdx);
						myDrawCard(myHand);
						if(CaravanActivity.soundEnabled) {
							AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
							float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
							sounds.play(dropSound, volume, volume, 1, 0, 1);


						}
						myTurn = false;
						calculateScore();
						makeComputerPlay();
						choosecardDialog.dismiss();


					}
					if (sep[0].equals("Caravan F")) {
						if (oppCaravanCFaceCards.size() != 0) {
							oppCaravanCFaceCards.clear();
							oppCaravanCFaceCardsIndex.clear();
							bonusCaravanD = 0;
						}
						oppCaravanC.remove(oppCaravanC.size() - 1);

						myHand.remove(amovingCardIdx);
						myDrawCard(myHand);
						if(CaravanActivity.soundEnabled) {
							AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
							float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
							sounds.play(dropSound, volume, volume, 1, 0, 1);


						}
						myTurn = false;
						calculateScore();
						makeComputerPlay();
						choosecardDialog.dismiss();


					}
				}
				else if (cardrank == 12) {
					if (sep[0].equals("Caravan A")) {
						if(userCaravanAFaceCards.size()!=0){
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						}
						else{
							userCaravanAFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanA=bonusCaravanA-(userCaravanA.get(userCaravanA.size()-1).getScoreValue()/2);
							myHand.remove(amovingCardIdx);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myDrawCard(myHand);
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}



					}
					if (sep[0].equals("Caravan B")) {
						if(userCaravanBFaceCards.size()!=0){
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						}
						else{
							userCaravanBFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanB=bonusCaravanB-(userCaravanB.get(userCaravanB.size()-1).getScoreValue()/2);
							myHand.remove(amovingCardIdx);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myDrawCard(myHand);
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}
					if (sep[0].equals("Caravan C")) {
						if(userCaravanCFaceCards.size()!=0){
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						}
						else{
							userCaravanCFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanC=bonusCaravanC-(userCaravanC.get(userCaravanC.size()-1).getScoreValue()/2);
							myHand.remove(amovingCardIdx);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myDrawCard(myHand);
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}
					if (sep[0].equals("Caravan D")) {
						if (oppCaravanAFaceCards.size() != 0) {
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						} else {
							oppCaravanAFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanD = bonusCaravanD - (oppCaravanA.get(oppCaravanA.size() - 1).getScoreValue() / 2);
							myHand.remove(amovingCardIdx);
							myDrawCard(myHand);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}
					if (sep[0].equals("Caravan E")) {
						if (oppCaravanBFaceCards.size() != 0) {
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						} else {
							oppCaravanBFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanE = bonusCaravanE - (oppCaravanB.get(oppCaravanB.size() - 1).getScoreValue() / 2);
							myHand.remove(amovingCardIdx);
							myDrawCard(myHand);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}
					if (sep[0].equals("Caravan F")) {
						if (oppCaravanCFaceCards.size() != 0) {
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						} else {
							oppCaravanCFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanF = bonusCaravanF - (oppCaravanC.get(oppCaravanC.size() - 1).getScoreValue() / 2);
							myHand.remove(amovingCardIdx);
							myDrawCard(myHand);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}
				}
				else {
					if (sep[0].equals("Caravan A")) {
						if(userCaravanAFaceCards.size()!=0){
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						}
						else{
							userCaravanAFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanA=bonusCaravanA+(userCaravanA.get(userCaravanA.size() - 1).getScoreValue());
							myHand.remove(amovingCardIdx);
							myDrawCard(myHand);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}
					if (sep[0].equals("Caravan B")) {
						if(userCaravanBFaceCards.size()!=0){
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						}
						else{
							userCaravanBFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanB=bonusCaravanB+(userCaravanB.get(userCaravanB.size() - 1).getScoreValue());
							myHand.remove(amovingCardIdx);
							myDrawCard(myHand);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}
					if (sep[0].equals("Caravan C")) {
						if(userCaravanCFaceCards.size()!=0){
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						}
						else{
							userCaravanCFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanC=bonusCaravanC+(userCaravanC.get(userCaravanC.size() - 1).getScoreValue());
							myHand.remove(amovingCardIdx);
							myDrawCard(myHand);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}
					if (sep[0].equals("Caravan D")) {
						if(oppCaravanAFaceCards.size()!=0){
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						}
						else{
							oppCaravanAFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanD=bonusCaravanD+(oppCaravanA.get(oppCaravanA.size() - 1).getScoreValue());
							myHand.remove(amovingCardIdx);
							myDrawCard(myHand);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}
					if (sep[0].equals("Caravan E")) {
						if(oppCaravanBFaceCards.size()!=0){
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						}
						else{
							oppCaravanBFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanE=bonusCaravanE+(oppCaravanB.get(oppCaravanB.size() - 1).getScoreValue());
							myHand.remove(amovingCardIdx);
							myDrawCard(myHand);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}
					if (sep[0].equals("Caravan F")) {
						if(oppCaravanCFaceCards.size()!=0){
							Toast directions = Toast.makeText(myContext, "This card Already has a face card played on it", Toast.LENGTH_LONG);
							directions.show();
							choosecardDialog.dismiss();

						}
						else{
							oppCaravanCFaceCards.add(myHand.get(amovingCardIdx));
							bonusCaravanF=bonusCaravanF+(oppCaravanC.get(oppCaravanC.size() - 1).getScoreValue());
							myHand.remove(amovingCardIdx);
							myDrawCard(myHand);
							if(CaravanActivity.soundEnabled) {
								AudioManager audioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
								float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								sounds.play(dropSound, volume, volume, 1, 0, 1);


							}
							myTurn = false;
							calculateScore();
							makeComputerPlay();
							choosecardDialog.dismiss();
						}
					}


				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				myTurn = true;
				calculateScore();
			}
		});


		choosecardDialog.show();

	}



	private void makeComputerPlay() {
		String tempPlay=" ";

		calculateScore();
		ComputerPlayer.makePlay(oppHand, oppCaravanA, oppCaravanB, oppCaravanC, userCaravanA, userCaravanB, userCaravanC,userCaravanA_rank,usercaravanB_rank,usercaravanC_rank,oppcaravanA_rank,oppcaravanB_rank,oppcaravanC_rank,userCaravanAFaceCards,userCaravanBFaceCards,userCaravanCFaceCards,oppCaravanAFaceCards,oppCaravanBFaceCards,oppCaravanCFaceCards,oppDiscardPile,bonusCaravanD,bonusCaravanE,bonusCaravanF);

			oppDrawCard(oppHand);
			//System.out.println("the computer played: "+tempPlay);
			myTurn=true;
			calculateScore();
		}



	private void dealCards() {
		Collections.shuffle(myDeck, new Random());
		Collections.shuffle(oppDeck, new Random());
		for (int i = 0; i < 8; i++) {
			myDrawCard(myHand);
			oppDrawCard(oppHand);
		}
	}

	private void myDrawCard(List<Card> handToDraw) {

			handToDraw.add(0, myDeck.get(0));
			//random cards to simulate makeshift deck
			Collections.shuffle(myDeck, new Random());
		}
//		if (myDeck.isEmpty()) {
//			for (int i = myDiscardPile.size()-1; i > 0 ; i--) {
//				myDeck.add(myDiscardPile.get(i));
//				myDiscardPile.remove(i);
//
//				// Looks like this could be outside the loop
//				Collections.shuffle(myDeck,new Random());
//			}


	private void oppDrawCard(List<Card> handToDraw) {
		handToDraw.add(0, oppDeck.get(0));
		Collections.shuffle(oppDeck, new Random());
		//if (oppDeck.isEmpty()) {
//			for (int i = oppDiscardPile.size()-1; i > 0 ; i--) {
//				oppDeck.add(oppDiscardPile.get(i));
//				oppDiscardPile.remove(i);
//
//				// Looks like this could be outside the loop
//				Collections.shuffle(oppDeck,new Random());
//			}
//		}
	}
	private void myInitCards() {
		for (int i = 0; i < 4; i++) {
			for (int j = 102; j < 115; j++) {
				int tempId = j + (i*100);
				Card tempCard = new Card(tempId);

				// pkgName is the java class package name
				String pkgName = myContext.getPackageName();

				// 1) getResources() or myContext.getResources() doesn't matter
				// 2) nowhere is ".png" mentioned
				int resourceId = myContext.getResources().getIdentifier("card" + tempId, "drawable", pkgName);

				// decodeResource apparently interprets resourceId
				Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), resourceId);
				scaledCardW = (int) (screenW/8);
				scaledCardH = (int) (scaledCardW*1.28);
				if(CaravanActivity.interactiveEnabled) {
					Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);

					tempCard.setBitmap(scaledBitmap);
				}
					myDeck.add(tempCard);

			}
		}
	}
	private void oppInitCards() {
		for (int i = 0; i < 4; i++) {
			for (int j = 102; j < 115; j++) {
				int tempId = j + (i*100);
				Card tempCard = new Card(tempId);

				// pkgName is the java class package name
				String pkgName = myContext.getPackageName();

				// 1) getResources() or myContext.getResources() doesn't matter
				// 2) nowhere is ".png" mentioned
				int resourceId = myContext.getResources().getIdentifier("card" + tempId, "drawable", pkgName);

				// decodeResource apparently interprets resourceId
				Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), resourceId);
				scaledCardW = (int) (screenW/8);
				scaledCardH = (int) (scaledCardW*1.28);
				if(CaravanActivity.interactiveEnabled) {
					Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);
					tempCard.setBitmap(scaledBitmap);
				}
					oppDeck.add(tempCard);

			}
		}
	}


}
