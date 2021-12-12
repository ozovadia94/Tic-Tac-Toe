package com.example.tictactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class TicTacToeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[] buttons = new Button[9];
    private TicTacToeGame game = new TicTacToeGame();
    private Button reset_game;
    private ImageButton reset_board;
    private ImageButton exit;
    private Button ok_name;
    private String playername;
    private EditText text_name;
    private TextView welcome;
    private TextView result1;
    private TextView result2;
    private TextView res1;
    private TextView res2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_tic_tac_toe);

        buttons[0] = (Button) findViewById(R.id.a1);
        buttons[1] = (Button) findViewById(R.id.a2);
        buttons[2] = (Button) findViewById(R.id.a3);
        buttons[3] = (Button) findViewById(R.id.b1);
        buttons[4] = (Button) findViewById(R.id.b2);
        buttons[5] = (Button) findViewById(R.id.b3);
        buttons[6] = (Button) findViewById(R.id.c1);
        buttons[7] = (Button) findViewById(R.id.c2);
        buttons[8] = (Button) findViewById(R.id.c3);
        reset_board = (ImageButton) findViewById(R.id.reset_board);
        exit = (ImageButton) findViewById(R.id.exit);
        reset_game = (Button) findViewById(R.id.reset_game);
        ok_name = (Button) findViewById(R.id.ok_name);
        text_name = (EditText) findViewById(R.id.text_name);
        res1 = (TextView) findViewById(R.id.comp_res);
        res2 = (TextView) findViewById(R.id.comp_res2);
        result1 = (TextView) findViewById(R.id.result1);
        result2 = (TextView) findViewById(R.id.result2);
        welcome = (TextView) findViewById(R.id.welcome);

        for(int i=0;i<9;i++)
            buttons[i].setOnClickListener(this);

        reset_game.setOnClickListener(this);
        reset_board.setOnClickListener(this);
        ok_name.setOnClickListener(this);

        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ok_name:
                name_method(v);
                break;
            case R.id.reset_board:
                reset_board();
                break;
            case R.id.reset_game:
                reset_game();
                break;
            case R.id.exit:
                app_exit();
                break;
            case R.id.a1:
                move(0, 0, 0);
                break;
            case R.id.a2:
                move(0, 1, 1);
                break;
            case R.id.a3:
                move(0, 2, 2);
                break;
            case R.id.b1:
                move(1, 0, 3);
                break;
            case R.id.b2:
                move(1, 1, 4);
                break;
            case R.id.b3:
                move(1, 2, 5);
                break;
            case R.id.c1:
                move(2, 0, 6);
                break;
            case R.id.c2:
                move(2, 1, 7);
                break;
            case R.id.c3:
                move(2, 2, 8);
                break;
        }
    }

    private void move(int row, int col, int i) { //it is how the game will be conducted
        if (game.isEmpty(row,col)) {//when a button wasn't pressed
            buttons[i].setText("x");
            game.setX(row, col);

            if (check_if_game_finish(1)==false) {
                computer_turn();
            }
        }
    }

    private boolean check_if_game_finish(int code_name){
        int play = game.checkifwin(code_name);
        switch (play) {
            case -1:
                return false;
            case 0:
                Toast.makeText(this, "Draw - 'Teko'", Toast.LENGTH_LONG).show();
                game.finish_game();

                break;
            case 1:
                Toast.makeText(this, playername + " Win", Toast.LENGTH_LONG).show();
                update_results();
                game.finish_game();
                break;
            case 2:
                Toast.makeText(this, "Computer Win", Toast.LENGTH_LONG).show();
                update_results();
                game.finish_game();
                break;
        }
        return true;
    }

    private void computer_turn() {
        int i = game.setO();
        if (i != -1 && game.check_if_game_over()==false) {
            buttons[i].setText("o");
            check_if_game_finish(2);
        }
    }

    private void reset_board() {//reset the board
        game.newGame();
        for (int i = 0; i < 9; i++)
            buttons[i].setText("");
    }

    private void reset_game() {
        AlertDialog.Builder altdial = new AlertDialog.Builder(TicTacToeActivity.this);
        altdial.setMessage("Do you want to reset the result?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset_board();
                        game.reset_results();
                        update_results();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = altdial.create();
        alert.show();




    }

    private void name_method(View v) {//name of the the player
        playername = text_name.getText().toString();
        if (playername.isEmpty())
            playername = "Player";

        //INVISIBLE - welcome,ok_button(of name), text of name
        welcome.setVisibility(View.INVISIBLE);
        ok_name.setVisibility(View.INVISIBLE);
        text_name.setVisibility(View.INVISIBLE);



        //Visible the button and things for the game
        for(int i=0;i<9;i++){
            buttons[i].setVisibility(View.VISIBLE);
        }
        reset_board.setVisibility(View.VISIBLE);
        reset_game.setVisibility(View.VISIBLE);

        result1.setText(playername);
        result2.setText("Computer");
        update_results();

        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    private void update_results() {//name of the the player
        res1.setText(game.getXwins() + "");
        res2.setText(game.getOwins() + "");
    }

    private void app_exit() {
        AlertDialog.Builder altdial = new AlertDialog.Builder(TicTacToeActivity.this);
        altdial.setMessage("Do you want to Quit this app ???").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = altdial.create();
        alert.show();
    }
}
