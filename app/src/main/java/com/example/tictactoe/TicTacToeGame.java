package com.example.tictactoe;

import android.widget.Toast;

class TicTacToeGame {
    private char [][]board;
    private int sum;
    private int result[];
    private final int size=3;
    private boolean isAI;

    public int getXwins(){
        return result[0];
    }
    public int getOwins(){
        return result[1];
    }


    public TicTacToeGame(){
        board=new char[size][size];
        sum=0;
        result=new int[2];
        this.isAI=false;
    }
    public void newGame() {
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                board[i][j]=0;
        sum=0;
    }

    public void toggle_AI_mode(){
        isAI=!isAI;
    }

    public void reset_results(){
        result[0]=0;
        result[1]=0;
    }

    public void finish_game(){
        sum=9;
    }

    public boolean is_fully(){
        if(sum==9)
            return true;
        return false;
    }


    public void setX(int row,int col){
        if (isEmpty(row,col)==true) {
            board[row][col] = 'x';
            sum++;
        }
    }

    public boolean isEmpty(int row,int col){ //check if the place is
        if(board[row][col]==0)
            return true;
        return false;
    }

    public int setO(){
        int[] return_val;
        if(this.isAI==true)
            return_val = setOAI();
        else
            return_val = setORegular();

        if(return_val==null)
            return -1;

        board[return_val[0]][return_val[1]] = 'o';
        sum++;
        return (return_val[1] + (return_val[0]*size));
    }

    private int[] setORegular()
    {
        if(sum!=9) {
            int rowTemp, colTemp;
            do {
                rowTemp = (int)(Math.random() * size);
                colTemp = (int)(Math.random() * size);
            } while (isEmpty(rowTemp, colTemp) == false);

            return new int[] {rowTemp,colTemp};
        }
        return null;
    }

    private int[] setOAI(){
        return null;
    }

    public int checkifwin(int user){
//        -1 no win,continue to play
//        0 draw
//        1 player win
//        2 computer win

        boolean check = ifWinhelper();
        if(check==false){//no one win
            if(is_fully())//draw
                return 0;
            else//continue to play
                return -1;
        }

        if(user==1)//player win
        {
            result[0]++;
            return 1;
        }

        else//computer win
            result[1]++;
            return 2;

    }

    public boolean ifWinhelper(){
        for (int i=0; i<size; i++) { //check if win was in col
            char temp=board[i][0];
            for (int j = 1; j < size; j++)
                if (temp!=board[i][j])
                    break;
                else if(j==size-1 && board[i][j]!=0){

                    return true;
                }

        }


        for (int i=0; i<size; i++) { //check if win was in col
            char temp=board[0][i];
            for (int j = 1; j < size; j++)
                if (temp!=board[j][i])
                    break;
                else if(j==size-1 && board[j][i]!=0)
                    return true;
        }

        //slant
        if (board[0][0]==board[1][1] && board[1][1]==board[2][2] && (board[0][0]!=0)) // slant
            return true;
        if (board[0][2]==board[1][1] && board[1][1]==board[2][0] && (board[0][2]!=0)) // slant
            return true;

        return false;
    }

}
