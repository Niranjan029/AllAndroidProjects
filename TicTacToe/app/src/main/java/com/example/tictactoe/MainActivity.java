package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    // 0 - X
    //   1 - O
    boolean gameActive = true ;
    int activePlayer=0;
    int[] gameState={2,2,2,2,2,2,2,2,2};
    // 0 - X
    //  1 - O
    // 2- NULL
    int[][] winPosition={{0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6} };

        public void playerTap(View view)
        {
            ImageView img = (ImageView) view;
            int tappedImage = Integer.parseInt(img.getTag().toString());
            if (!gameActive) {
                gameReset(view);
            }
            if (gameState[tappedImage] == 2) {
                gameState[tappedImage] = activePlayer;
                img.setTranslationY(-1000f);
                if (activePlayer == 0) {
                    img.setImageResource(R.drawable.x);
                    activePlayer = 1 ;
                    TextView status = findViewById(R.id.status);
                    status.setText("O's turn - tap to play");
                } else {
                    img.setImageResource(R.drawable.o);
                    activePlayer = 0;
                    TextView status = findViewById(R.id.status);
                    status.setText("X's turn - tap to play");
                }
                img.animate().translationYBy(1000f).setDuration(300);
            }

            for (int[] winposition : winPosition) {
                if (gameState[winposition[0]] == gameState[winposition[1]] &&
                        gameState[winposition[1]] == gameState[winposition[2]] && gameState[winposition[0]] != 2) {
                    // somebody has won the game
                    String str;
                    if (gameState[winposition[0]] == 0) {
                        str = "X has won";
                    } else {
                        str = "O has won";
                    }
                    TextView status = findViewById(R.id.status) ;
                    status.setText(str);
                    gameActive=false;
                }
            }
        }
        public void gameReset (View view)
        {
            gameActive = true;
            activePlayer = 0;
            for (int i = 0; i < gameState.length; i++) {
                gameState[i] = 2;
            }
            ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
            ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
            ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
            ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
            ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
            ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
            ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
            ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
            ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

            TextView status = findViewById(R.id.status);
            status.setText("X's turn - tap to play");

        }


}
