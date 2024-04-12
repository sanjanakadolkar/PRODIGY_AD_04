package com.example.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView PlayerOneScore,PlayerTwoScore,PlayerStatus;
    public Button buttons[]=new Button[9];
    public Button ResetBtn;

    public int PlayerOneScoreCnt,PlayerTwoScoreCnt,RoundCnt;
    public boolean activePlayer;

    int gameState[]={2,2,2,2,2,2,2,2,2};
    int winningPositions[][]={
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlayerOneScore=findViewById(R.id.PlayerOneScore);
        PlayerTwoScore=findViewById(R.id.PlayerTwoScore);
        PlayerStatus=findViewById(R.id.PlayerStatus);
        ResetBtn=findViewById(R.id.ResetBtn);

        for(int i=0;i<buttons.length;i++)
        {
            String buttonID="btn"+i;
            int resourceID=getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i]=findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        RoundCnt=0;
        PlayerOneScoreCnt=0;
        PlayerTwoScoreCnt=0;
        activePlayer=true;
    }
    @Override
    public void onClick(View view){
        if(!((Button)view).getText().toString().equals("")){
            return;
        }
        String buttonID=view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer=Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(activePlayer){
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#FFFDFDFD"));
            gameState[gameStatePointer]=0;
        }else{
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#FFFDFDFD"));
            gameState[gameStatePointer]=1;
        }
        RoundCnt++;

        if(checkWinner()){
            if(activePlayer){
                PlayerOneScoreCnt++;
                updatePlayerScore();
                Toast.makeText(this,"Player One Won!!!",Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                PlayerTwoScoreCnt++;
                updatePlayerScore();
                Toast.makeText(this,"Player Two Won!!!",Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(RoundCnt==9){
            playAgain();
            Toast.makeText(this, "No Winner!!!", Toast.LENGTH_SHORT).show();
        }else {
            activePlayer=!activePlayer;
        }

        if(PlayerOneScoreCnt>PlayerTwoScoreCnt){
            PlayerStatus.setText("Player One is Winner!");
        }else if(PlayerTwoScoreCnt>PlayerOneScoreCnt){
            PlayerStatus.setText("Player Two is Winner!");
        }else{
            PlayerStatus.setText("");
        }

        ResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                PlayerOneScoreCnt=0;
                PlayerTwoScoreCnt=0;
                PlayerStatus.setText("");
                updatePlayerScore();
            }
        });

    }
    public boolean checkWinner(){
        boolean winnerResult=false;

        for (int winningPosition[]:winningPositions){
            if(gameState[winningPosition[0]]==gameState[winningPosition[1]] &&
            gameState[winningPosition[1]]==gameState[winningPosition[2]] &&
            gameState[winningPosition[0]]!=2){
                winnerResult=true;
            }
        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        PlayerOneScore.setText(Integer.toString(PlayerOneScoreCnt));
        PlayerTwoScore.setText(Integer.toString(PlayerTwoScoreCnt));
    }
    public void playAgain(){
        RoundCnt=0;
        activePlayer=true;

        for (int i=0;i< buttons.length;i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }
}