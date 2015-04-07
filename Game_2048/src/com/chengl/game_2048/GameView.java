package com.chengl.game_2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {

	private Card[][] mCardsMap = new Card[4][4];
	private List<Point> mEmptyPoit = new ArrayList<Point>();

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initGameView();
	}

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initGameView();
	}

	public GameView(Context context) {
		super(context);

		initGameView();
	}

	private void initGameView() {
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);

		setOnTouchListener(new View.OnTouchListener() {

			private float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;

				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;

					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX < -5) {
							System.out.println("left");
							slideleft();
						} else if (offsetX > 5) {
							System.out.println("right");
							slideright();
						}
					} else {
						if (offsetY < -5) {
							System.out.println("up");
							slideup();
						} else if (offsetY > 5) {
							System.out.println("down");
							slidedown();
						}
					}
					break;

				default:
					break;
				}

				return true;
			}
		});
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int cardWith = (Math.min(w, h) - 10) / 4;
		addCard(cardWith, cardWith);
		startGame();
	}

	private void addCard(int cardWith, int cardHeight) {

		Card c;

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				c = new Card(getContext());
				c.setNum(0);
				addView(c, cardWith, cardHeight);

				mCardsMap[x][y] = c;
			}
		}
	}
	
	private void startGame() {
		
		MainActivity.getMainActivity().clearScore();
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				mCardsMap[x][y].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
		addRandomNum();
		addRandomNum();
		addRandomNum();
		addRandomNum();
		addRandomNum();
		addRandomNum();
		addRandomNum();
	}
	
	private void addRandomNum() {
		
		mEmptyPoit.clear();
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (mCardsMap[x][y].getNum() <= 0) {
					mEmptyPoit.add(new Point(x, y));
				}
			}
		}
		
		Point p = mEmptyPoit.remove((int)(Math.random()*mEmptyPoit.size()));
		mCardsMap[p.x][p.y].setNum(Math.random() > 0.3 ? 2:4);
	}

	private void slideleft() {
		
		boolean addAction = false;
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				for (int x1 = x + 1; x1 < 4; x1++) {
					if (mCardsMap[x1][y].getNum() > 0) {
						if (mCardsMap[x][y].getNum() <= 0) {
							mCardsMap[x][y].setNum(mCardsMap[x1][y].getNum());
							mCardsMap[x1][y].setNum(0);
							
							x--;
							addAction = true;
						} else if (mCardsMap[x][y].equals(mCardsMap[x1][y])) {
							mCardsMap[x][y].setNum(mCardsMap[x][y].getNum() * 2);
							mCardsMap[x1][y].setNum(0);
							
							MainActivity.getMainActivity().addScore(mCardsMap[x][y].getNum());
							addAction = true;
						}
						
						break;
					}
				}
			}
		}
		
		if (addAction) {
			addRandomNum();
			checkGameOver();
		}

	}

	private void slideright() {
		
		boolean addAction = false;
		
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (mCardsMap[x1][y].getNum() > 0) {
						if (mCardsMap[x][y].getNum() <= 0) {
							mCardsMap[x][y].setNum(mCardsMap[x1][y].getNum());
							mCardsMap[x1][y].setNum(0);
							
							x++;
							addAction = true;
						} else if (mCardsMap[x][y].equals(mCardsMap[x1][y])) {
							mCardsMap[x][y].setNum(mCardsMap[x][y].getNum() * 2);
							mCardsMap[x1][y].setNum(0);
							
							MainActivity.getMainActivity().addScore(mCardsMap[x][y].getNum());
							addAction = true;
						}
						
						break;
					}
				}
			}
		}
		
		if (addAction) {
			addRandomNum();
			checkGameOver();
		}
	}

	private void slideup() {
		
		boolean addAction = false;
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for (int y1 = y + 1; y1 < 4; y1++) {
					if (mCardsMap[x][y1].getNum() > 0) {
						System.out.println(mCardsMap[x][y1].getNum() + "");
						if (mCardsMap[x][y].getNum() <= 0) {
							System.out.println(mCardsMap[x][y].getNum() + "");
							mCardsMap[x][y].setNum(mCardsMap[x][y1].getNum());
							System.out.println(mCardsMap[x][y].getNum() + "");
							mCardsMap[x][y1].setNum(0);
							
							y--;
							addAction = true;
						} else if (mCardsMap[x][y].equals(mCardsMap[x][y1])) {
							mCardsMap[x][y].setNum(mCardsMap[x][y].getNum() * 2);
							mCardsMap[x][y1].setNum(0);
							
							MainActivity.getMainActivity().addScore(mCardsMap[x][y].getNum());
							addAction = true;
						}
						
						break;
					}
				}
			}
		}
		
		if (addAction) {
			addRandomNum();
			checkGameOver();
		}
	}

	private void slidedown() {
		
		boolean addAction = false;
		
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				for (int y1 = y - 1; y1 >= 0; y1--) {
					if (mCardsMap[x][y1].getNum() > 0) {
						if (mCardsMap[x][y].getNum() <= 0) {
							mCardsMap[x][y].setNum(mCardsMap[x][y1].getNum());
							mCardsMap[x][y1].setNum(0);
							
							y++;
							addAction = true;
						} else if (mCardsMap[x][y].equals(mCardsMap[x][y1])) {
							mCardsMap[x][y].setNum(mCardsMap[x][y].getNum() * 2);
							mCardsMap[x][y1].setNum(0);
							
							MainActivity.getMainActivity().addScore(mCardsMap[x][y].getNum());
							addAction = true;
						}
						
						break;
					}
				}
			}
		}
		
		if (addAction) {
			addRandomNum();
			checkGameOver();
		}
	}
	
	private void checkGameOver() {
		
		boolean mGameOver = true;
		
		ALL:
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (gameContinue(x, y)) {
					mGameOver = false;
					break ALL;
				}
			}
		}
		
		if (mGameOver) {
			new AlertDialog.Builder(getContext())
									.setTitle("Game Over!")
									.setMessage("游戏结束")
									.setPositiveButton("重来", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											startGame();
										}
									}).show();
		}
	}

	/**
	 * @param y
	 * @param x
	 * @return
	 */
	private boolean gameContinue(int x, int y) {
		
		//There is empty point.
		if (mCardsMap[x][y].getNum() == 0)
			return true;
		
		//There is the same number on the left.
		if (x > 0 && mCardsMap[x][y].equals(mCardsMap[x - 1][y]))
			return true;
		
		//There is the same number on the right.
		if (x < 3 && mCardsMap[x][y].equals(mCardsMap[x + 1][y]))
			return true;
		
		//There is the same number above.
		if (y > 0 && mCardsMap[x][y].equals(mCardsMap[x][y - 1]))
			return true;
		
		//There is the same number below.
		if (y < 3 && mCardsMap[x][y].equals(mCardsMap[x][y + 1]))
			return true;
		
		return false;
		
		/*return mCardsMap[x][y].getNum() == 0 ||
				(x > 0 && mCardsMap[x][y].equals(mCardsMap[x - 1][y])) ||
				(x < 3 && mCardsMap[x][y].equals(mCardsMap[x + 1][y])) ||
				(y > 0 && mCardsMap[x][y].equals(mCardsMap[x][y - 1])) ||
				(y < 3 && mCardsMap[x][y].equals(mCardsMap[x][y + 1]));*/
	}
}
