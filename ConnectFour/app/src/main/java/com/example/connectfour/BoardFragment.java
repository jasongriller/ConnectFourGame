package com.example.connectfour;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;  // Add this import
import androidx.fragment.app.Fragment;
import androidx.core.graphics.drawable.DrawableCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class BoardFragment extends Fragment {
    private final String GAME_STATE = "gameState";
    private ConnectFourGame mGame;
    private GridLayout mGrid;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_board, container, false);

        // Instantiate the member variable of class ConnectFourGame
        mGame = new ConnectFourGame();

        // Find the GridLayout
        mGrid = parentView.findViewById(R.id.board_grid);

        if (savedInstanceState == null) {
            // Call method startGame
            startGame();
        } else {
            // Declare local variable, data type String, to store the game state (i.e., gameState)
            String gameState = savedInstanceState.getString(GAME_STATE);

            // Call method setState in class ConnectFourGame, pass local variable gameState as an argument
            mGame.setState(gameState);

            // Call method setDisc
            setDisc();
        }

        // Add the same click handler to all grid buttons
        mGrid = parentView.findViewById(R.id.board_grid);

        for (int i = 0; i < mGrid.getChildCount(); i++) {
            Button gridButton = (Button) mGrid.getChildAt(i);
            gridButton.setOnClickListener(this::onButtonClick);
        }

        return parentView;
    }

    private void onButtonClick(View view) {
        // Find the button's row and col
        int buttonIndex = mGrid.indexOfChild(view);
        int row = buttonIndex / ConnectFourGame.ROW;
        int col = buttonIndex % ConnectFourGame.COL;

        // Call method selectDisc in class ConnectFourGame
        mGame.selectDisc(row, col);

        // Call method setDisc
        setDisc();

        // If method isGameOver in class ConnectFourGame returns true
        if (mGame.isGameOver()) {
            // Instantiate a Toast to congratulate player if game is won
            congratsToast();

            // Call method newGame in class ConnectFourGame
            mGame.newGame();

            // Call method setDisc
            setDisc();
        }
    }

    private void congratsToast() {
        Toast.makeText(getContext(), "Congrats! You Won!", Toast.LENGTH_SHORT).show();
    }

    private void startGame() {
        // Call method newGame in class ConnectFourGame
        mGame.newGame();

        // Call method setDisc
        setDisc();
    }
    private void setDisc() {
        // Iterate through the collection mGrid.getChildCount()
        for (int buttonIndex = 0; buttonIndex < mGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mGrid.getChildAt(buttonIndex);

            // Find the button's row and column
            int row = buttonIndex / ConnectFourGame.COL;
            int col = buttonIndex % ConnectFourGame.COL;

            // Instantiate an instance of class Drawable for the three drawable discs in res/drawable
            int drawableResourceId;
            switch (mGame.getDisc(row, col)) {
                case ConnectFourGame.BLUE:
                    drawableResourceId = R.drawable.circle_blue;
                    break;
                case ConnectFourGame.RED:
                    drawableResourceId = R.drawable.circle_red;
                    break;
                case ConnectFourGame.EMPTY:
                default:
                    drawableResourceId = R.drawable.circle_white;
                    break;
            }

            // Instantiate an instance of class Drawable using class.method ContextCompat.getDrawable
            Drawable discDrawable = ContextCompat.getDrawable(requireActivity(), drawableResourceId);

            // Set each Drawable object equal to DrawableCompat.wrap
            discDrawable = DrawableCompat.wrap(discDrawable);

            // Get the value of the element stored in the current row and column
            int discValue = mGame.getDisc(row, col);

            // Based on the value of the element (i.e., BLUE, RED, EMPTY),
            switch (discValue) {
                case ConnectFourGame.BLUE:
                case ConnectFourGame.RED:
                case ConnectFourGame.EMPTY:
                    gridButton.setBackgroundResource(drawableResourceId);
                    break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }
}
