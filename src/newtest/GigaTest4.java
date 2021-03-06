package newtest;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

import util.Tuple;

//import ai.charles2.Charles_2;
import ai.montecarlo.MonteCarlo;
import ai.montecarloheuristic10.MonteCarloH10;
import ai.montecarloheuristic5.MonteCarloH5;
import ai.montecarloheuristic55.MonteCarloH55;
import ai.montecarloheuristic7.MonteCarloH7;
//import ai.random.LuckyAI;

import core.Board;
import core.Player;
import core.Rules;

/**
 * Classe relativa al gigatest4
 * @author Mina
 *
 */

public class GigaTest4 {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	
	public static void main(String[] args) throws Exception {
		/***********************************************************************
		 * Test #1: (50,000 roll-outs) MCTS_UCT v MCTS + H(5) 3-point board.
		 **********************************************************************/
		// test 1 go
		testOne();
		
		/***********************************************************************
		 * Test #2: (50,000 roll-outs) MCTS_UCT v MCTS + H(7) 3-point board.
		 **********************************************************************/
		// test 2 go
		testTwo ();
			
		
		/***********************************************************************
		 * Test #3: (50,000 roll-outs) MCTS (UCT) v MCTS + H(10).
		 **********************************************************************/
		// test 3 go
		testThree ();

		/***********************************************************************
		 * Test #4: (50,000 roll-outs) MCTS_UCT v MCTS + H(5+5).
		 **********************************************************************/
		// test 4 go
		testFour ();

		/***********************************************************************
		 * Test #5: (50,000 roll-outs) MCTS + H(5) v MCTS + H(7).
		 **********************************************************************/
		// test 5 go
		testFive ();
		
		/***********************************************************************
		 * Test #6: (50,000 roll-outs) MCTS_H(7) v MCTS_H(10).
		 **********************************************************************/
		// test 6 go
		testSix ();
	}
	
public static Integer maxCast (int a){
		
		Integer valInteger = (Integer) a; 
			
		return valInteger;
		}
		
public static void testOne () throws Exception {
	
	//Statistical variables.
			int e1TotalWins = 0,
			totalDraws = 0,
			e1TotalLoses = 0,
			e1WinAsPlayer1 = 0,
			e1DrawAsPlayer1 = 0,
			e1LoseAsPlayer1 = 0,
			e1WinAsPlayer2 = 0,
			e1DrawAsPlayer2 = 0,
			e1LoseAsPlayer2 = 0,
			e2TotalWins = 0,
			e2TotalLoses = 0,
			e2WinAsPlayer1 = 0,
			e2DrawAsPlayer1 = 0,
			e2LoseAsPlayer1 = 0,
			e2WinAsPlayer2 = 0,
			e2DrawAsPlayer2 = 0,
			e2LoseAsPlayer2 = 0;

			

			//Board that is used in games.
			Board boardTest1 = null;

			//Board that keeps copy of initial position, used to quickly reset the 
			//board before new game take place.
			Board initialPositionTest1 = null;

			//Array of all boards that are used in the test case.
			Board[] boardCollectionTest1 = null;

			//Index of player that is entitled to make a move.
			int currentIndexTest1 = 0;

			//Number of all moves that was made during the game.
			int numberOfMoveTest1 = 0;
			int value1_grt4_50000 = 50000; 
			//Players participating in the test case.
			Player[] playersTest1 =  {
					new Player("MCTS_UCT", "MCTS_UCT", "w", value1_grt4_50000),
					new Player("MCTS_H(5)", "MCTS_H(5)", "b", value1_grt4_50000)
			};

			//Number of total moves. It is used to check whether the game is in 
			//terminate state or not (the game finishes when there is no empty 
			//fields in the board).
			int totalNumberOfMovesTest1 = 38;

			//Load board.
			//Load board.
			String name4Board1 = "50_boards_11.sav";
			loadBoard ( boardCollectionTest1, name4Board1);
			
			//The beginning and the end of the test.
			long startTime = 0, endTime = 0;

			//Report when games commenced.
			startTime = System.currentTimeMillis();
			String name4File1 = "results_50k_11b_MCTS_UCTvMCTS_H(5).txt";
			//Define buffers.
			BufferedWriter temporany = null;
			BufferedWriter outputTest1 = defineBuffers ( temporany,  name4File1);
			
			MonteCarlo mc_t1 = new MonteCarlo(
					extracted(boardTest1).duplicate(), 
					playersTest1[currentIndexTest1].getColor(), 
					numberOfMoveTest1, 
					totalNumberOfMovesTest1);
			MonteCarloH5 mc_h5t = new MonteCarloH5(
					extracted(boardTest1).duplicate(), 
					playersTest1[currentIndexTest1].getColor(), 
					numberOfMoveTest1, 
					totalNumberOfMovesTest1);
	
	for(int testIndex = 1; testIndex <= 100; ++testIndex) {
		System.out.println("Test1: " + testIndex + " / 100");
		//Reset settings.
		currentIndexTest1 = 0;
		numberOfMoveTest1 = 0;

		//Swap players.
		Player tmp = playersTest1[0];
		playersTest1[0] = playersTest1[1];
		playersTest1[1] = tmp;

		//Reset the board to an initial state. When index is odd generate a 
		//new random board.
		
		newRandomBoardFour ( testIndex, extracted(boardTest1), 
				boardCollectionTest1, extracted(initialPositionTest1) );


		//Run a single game.
		while(numberOfMoveTest1 < totalNumberOfMovesTest1) {
			if(playersTest1[currentIndexTest1].getType().equals("MCTS_UCT")) {
				//MCTS + H(7) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo will select a move.
				

				move = mc_t1.uct(playersTest1[currentIndexTest1].
						getSimulationNumber());

				extracted(boardTest1).makeMove(move, playersTest1[currentIndexTest1].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest1;

				//Adjust index of current player.
				currentIndexTest1 = (currentIndexTest1 + 1) % 2;
			} else if(playersTest1[currentIndexTest1].getType().equals("MCTS_H(5)")) {
				//MCTS (UCT) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo will select move.
				
				move = mc_h5t.uct(playersTest1[currentIndexTest1].
						getSimulationNumber());


				extracted(boardTest1).makeMove(move, playersTest1[currentIndexTest1].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest1;

				//Adjust index of current player.
				currentIndexTest1 = (currentIndexTest1 + 1) % 2;
			}
		} //end of single game.

		String gameOutcome = Rules.calculateScore(extracted(boardTest1));
		
		outputTest1.append("Match #" + testIndex);
		outputTest1.newLine();
		outputTest1.append("Player 1: " + playersTest1[0].getName() + 
				" Player 2: " + playersTest1[1].getName());
		outputTest1.newLine();

		//Append the result to the text file and update counters..
		String zero = "0";
		
		if(gameOutcome.equals(zero)) {
			//The game was a draw.
			++totalDraws;
			//Append information to the file.
			outputTest1.append("Result: draw");
			outputTest1.newLine();
			outputTest1.close();

			//Update statistics.
			boolean valueUS6A = playersTest1[0].getName().equals("MCTS_H(5)");
			updateStaticsA ( valueUS6A,  e1DrawAsPlayer1,
					 e2DrawAsPlayer2,  e1DrawAsPlayer2,  e2DrawAsPlayer1);
		
		} else {
			boolean value = true;
			//One side wins the game.
			String phrase4_1 = "MCTS_H(5)";
			OneSideWinsTheGame ( gameOutcome,  playersTest1 
					, outputTest1,  value ,  e1TotalWins,
					 e2TotalLoses, e1WinAsPlayer2,  e2LoseAsPlayer1,
					 e2TotalWins,  e1TotalLoses, e2WinAsPlayer2, 
					 e1LoseAsPlayer1, phrase4_1);
			
			
		}			
	} //End of the test case. (for)

	//Report when games ended.
int value1gt4_1000 = 1000;
	//Append total outcome of the test case to the file.
 temporany = null;
BufferedWriter output1Test1 = defineBuffers ( temporany,  name4File1);
	

	output1Test1.append("========================================");
	output1Test1.newLine();
	output1Test1.append("*Summary 11-point board 50k roll-outs*");
	output1Test1.newLine();
	output1Test1.append("Draw occurred: " + totalDraws);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT total wins: " + e2TotalWins);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) total wins: " + e1TotalWins);
	output1Test1.newLine();
	output1Test1.append("Play time: " + (endTime - startTime)/value1gt4_1000 + " seconds.");
	output1Test1.newLine();

	//Write statistics for MCTS.
	output1Test1.append("MCTS_UCT wins as player #1 : " + e2WinAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT wins as player #2 : " + e2WinAsPlayer2);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT draws as player #1 : " + e2DrawAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT draws as player #2 : " + e2DrawAsPlayer2);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT loses as player #1 : " + e2LoseAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_UCT loses as player #2 : " + e2LoseAsPlayer2);
	output1Test1.newLine();

	//Write statistics for Random AI.
	output1Test1.append("MCTS_H(5) wins as player #1 : " + e1WinAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) wins as player #2 : " + e1WinAsPlayer2);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) draws as player #1 : " + e1DrawAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) draws as player #2 : " + e1DrawAsPlayer2);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) loses as player #1 : " + e1LoseAsPlayer1);
	output1Test1.newLine();
	output1Test1.append("MCTS_H(5) loses as player #2 : " + e1LoseAsPlayer2);
	output1Test1.newLine();

	output1Test1.append("========================================");
	output1Test1.close();

}

private static Board extracted(Board boardTest1) {
	return boardTest1;
}




public static void testTwo () throws Exception {
	
	int e1TotalWins = 0,
	totalDraws = 0,
	e1TotalLoses = 0,
	e1WinAsPlayer1 = 0,
	e1DrawAsPlayer1 = 0,
	e1LoseAsPlayer1 = 0,
	e1WinAsPlayer2 = 0,
	e1DrawAsPlayer2 = 0,
	e1LoseAsPlayer2 = 0,
	e2TotalWins = 0,
	e2TotalLoses = 0,
	e2WinAsPlayer1 = 0,
	e2DrawAsPlayer1 = 0,
	e2LoseAsPlayer1 = 0,
	e2WinAsPlayer2 = 0,
	e2DrawAsPlayer2 = 0,
	e2LoseAsPlayer2 = 0;

	//Board that is used in games.
	Board boardTest2 = null;

	//Board that keeps copy of initial position, used to quickly reset the 
	//board before new game take place.
	Board initialPositionTest2 = null;

	//Array of all boards that are used in the test case.
	Board[] boardCollectionTest2 = null;

	//Index of player that is entitled to make a move.
	int currentIndexTest2 = 0;

	//Number of all moves that was made during the game.
	int numberOfMoveTest2 = 0;
	int value2_grt4_50000 = 50000; 
	//Players participating in the test case.
	Player[] playersTest2 =  {
			new Player("MCTS_UCT", "MCTS_UCT", "w", value2_grt4_50000),
			new Player("MCTS_H(7)", "MCTS_H(7)", "b", value2_grt4_50000)
	};

	//Number of total moves. It is used to check whether the game is in 
	//terminate state or not (the game finishes when there is no empty 
	//fields in the board).
	int totalNumberOfMovesTest2 = 38;

	//Load board.
	String name4Board2 = "50_boards_11.sav";
	loadBoard ( boardCollectionTest2, name4Board2);
	
	//The beginning and the end of the test.
	long startTimeTest2 = 0, endTimeTest2 = 0;

	//Report when games commenced.
	startTimeTest2 = System.currentTimeMillis();

	String name4File2 = "results_50k_11b_MCTS_UCTvMCTS_H(7).txt";
	BufferedWriter temporany = null;
	BufferedWriter outputTest2 = defineBuffers ( temporany,  name4File2);
	//Define buffers.
	
	MonteCarloH7 mc_7h = new MonteCarloH7(
			extracted(boardTest2).duplicate(), 
			playersTest2[currentIndexTest2].getColor(), 
			numberOfMoveTest2, 
			totalNumberOfMovesTest2);
	MonteCarlo mc_t2 = new MonteCarlo(
			extracted(boardTest2).duplicate(), 
			playersTest2[currentIndexTest2].getColor(), 
			numberOfMoveTest2, 
			totalNumberOfMovesTest2);
	
	//Boards are OK. Proceed to testing.
	for(int testIndex = 1; testIndex <= 100; ++testIndex) {
		System.out.println("Test2: " + testIndex + " / 100");
		//Reset settings.
		currentIndexTest2 = 0;
		numberOfMoveTest2 = 0;
		
		//Swap players.
		Player tmp = playersTest2[0];
		playersTest2[0] = playersTest2[1];
		playersTest2[1] = tmp;

		//Reset the board to an initial state. When index is odd generate a 
		//new random board.
		newRandomBoardFour( testIndex,  extracted(boardTest2), 
				 boardCollectionTest2,  extracted(initialPositionTest2) );


		//Run a single game.
		while(numberOfMoveTest2 < totalNumberOfMovesTest2) {
			if(playersTest2[currentIndexTest2].getType().equals("MCTS_H(7)")) {
				//MCTS (UCT) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo will select move.
				
				move = mc_7h.uct(playersTest2[currentIndexTest2].
						getSimulationNumber());


				extracted(boardTest2).makeMove(move, playersTest2[currentIndexTest2].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest2;

				//Adjust index of current player.
				currentIndexTest2 = (currentIndexTest2 + 1) % 2;
			} else if(playersTest2[currentIndexTest2].getType().equals("MCTS_UCT")) {
				//MCTS (UCT) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo will select move.
				
				move = mc_t2.uct(playersTest2[currentIndexTest2].
						getSimulationNumber());


				extracted(boardTest2).makeMove(move, playersTest2[currentIndexTest2].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest2;

				//Adjust index of current player.
				currentIndexTest2 = (currentIndexTest2 + 1) % 2;
			}
		} //end of single game.

		String gameOutcome = Rules.calculateScore(extracted(boardTest2));
		
		outputTest2.append("Match #" + testIndex);
		outputTest2.newLine();
		outputTest2.append("Player 1: " + playersTest2[0].getName() + 
				" Player 2: " + playersTest2[1].getName());
		outputTest2.newLine();

		//Append the result to the text file and update counters..
		String zero = "0";
		
		if(gameOutcome.equals(zero)) {
			//The game was a draw.
			++totalDraws;
			//Append information to the file.
			outputTest2.append("Result: draw");
			outputTest2.newLine();
			outputTest2.close();

			//Update statistics.
			boolean valueTestUP2A = playersTest2[0].getName().equals("MCTS_H(7)");
			updateStaticsA ( valueTestUP2A,  e1DrawAsPlayer1,
					 e2DrawAsPlayer2,  e1DrawAsPlayer2,  e2DrawAsPlayer1);

		} else {
			//One side wins the game.
			boolean value = true;
			String phrase2_4 = "MCTS_H(7)";
			OneSideWinsTheGame ( gameOutcome,  playersTest2 
					, outputTest2,  value ,  e1TotalWins,
					 e2TotalLoses, e1WinAsPlayer2,  e2LoseAsPlayer1,
					 e2TotalWins,  e1TotalLoses,
					 e2WinAsPlayer2,  e1LoseAsPlayer1, phrase2_4 );
			
		}			
	} //End of the test case. (for)

	//Report when games ended.
	int value2gt4_1000 = 1000;
	//Append total outcome of the test case to the file.
	 temporany = null;
	BufferedWriter output1Test2 = defineBuffers ( temporany,  name4File2);
	
	output1Test2.append("========================================");
	output1Test2.newLine();
	output1Test2.append("*Summary (50k) 11-point board*");
	output1Test2.newLine();
	output1Test2.append("Draw occurred: " + totalDraws);
	output1Test2.newLine();
	output1Test2.append("MCTS_UCT total wins: " + e2TotalWins);
	output1Test2.newLine();
	output1Test2.append("MCTS_H(7) total wins: " + e1TotalWins);
	output1Test2.newLine();
	output1Test2.append("Play time: " + (endTimeTest2 - startTimeTest2)/value2gt4_1000 + " seconds.");
	output1Test2.newLine();

	//Write statistics for MCTS.
	output1Test2.append("MCTS_UCT wins as player #1 : " + e2WinAsPlayer1);
	output1Test2.newLine();
	output1Test2.append("MCTS_UCT wins as player #2 : " + e2WinAsPlayer2);
	output1Test2.newLine();
	output1Test2.append("MCTS_UCT draws as player #1 : " + e2DrawAsPlayer1);
	output1Test2.newLine();
	output1Test2.append("MCTS_UCT draws as player #2 : " + e2DrawAsPlayer2);
	output1Test2.newLine();
	output1Test2.append("MCTS_UCT loses as player #1 : " + e2LoseAsPlayer1);
	output1Test2.newLine();
	output1Test2.append("MCTS_UCT loses as player #2 : " + e2LoseAsPlayer2);
	output1Test2.newLine();

	//Write statistics for Random AI.
	output1Test2.append("MCTS_H(7) wins as player #1 : " + e1WinAsPlayer1);
	output1Test2.newLine();
	output1Test2.append("MCTS_H(7) wins as player #2 : " + e1WinAsPlayer2);
	output1Test2.newLine();
	output1Test2.append("MCTS_H(7) draws as player #1 : " + e1DrawAsPlayer1);
	output1Test2.newLine();
	output1Test2.append("MCTS_H(7) draws as player #2 : " + e1DrawAsPlayer2);
	output1Test2.newLine();
	output1Test2.append("MCTS_H(7) loses as player #1 : " + e1LoseAsPlayer1);
	output1Test2.newLine();
	output1Test2.append("MCTS_H(7) loses as player #2 : " + e1LoseAsPlayer2);
	output1Test2.newLine();

	output1Test2.append("========================================");
	output1Test2.close();

	
	
}



public static void testThree () throws Exception {
	
		int e1TotalWins = 0,
				totalDraws = 0,
				e1TotalLoses = 0,
				e1WinAsPlayer1 = 0,
				e1DrawAsPlayer1 = 0,
				e1LoseAsPlayer1 = 0,
				e1WinAsPlayer2 = 0,
				e1DrawAsPlayer2 = 0,
				e1LoseAsPlayer2 = 0,
				e2TotalWins = 0,
				e2TotalLoses = 0,
				e2WinAsPlayer1 = 0,
				e2DrawAsPlayer1 = 0,
				e2LoseAsPlayer1 = 0,
				e2WinAsPlayer2 = 0,
				e2DrawAsPlayer2 = 0,
				e2LoseAsPlayer2 = 0;


	//Board that is used in games.
	Board boardTest3 = null;

	//Board that keeps copy of initial position, used to quickly reset the 
	//board before new game take place.
	Board initialPositionTest3 = null;

	//Array of all boards that are used in the test case.
	Board[] boardCollectionTest3 = null;

	//Index of player that is entitled to make a move.
	int currentIndexTest3 = 0;

	//Number of all moves that was made during the game.
	int numberOfMoveTest3 = 0;
	int value3_grt4_50000 = 50000; 
	//Players participating in the test case.
	Player[] playersTest3 =  {
			new Player("MCTS_UCT", "MCTS_UCT", "w", value3_grt4_50000),
			new Player("MCTS_H(10)", "MCTS_H(10)", "b", value3_grt4_50000)
	};

	//Number of total moves. It is used to check whether the game is in 
	//terminate state or not (the game finishes when there is no empty 
	//fields in the board).
	int totalNumberOfMovesTest3 = 38;

	//Load board.
	String name4Board3 = "50_boards_11.sav";
	loadBoard ( boardCollectionTest3, name4Board3);
	
	
	//The beginning and the end of the test.
	long startTimeTest3 = 0, endTimeTest3 = 0;

	//Report when games commenced.
	startTimeTest3 = System.currentTimeMillis();
	String name4File3 ="results_50k_11b_MCTS_UCTvMCTS_H(10).txt";
	BufferedWriter temporany = null;
	BufferedWriter outputTest3 = defineBuffers ( temporany,  name4File3);
	//Define buffers.
	
	MonteCarloH10 mc10 = new MonteCarloH10(
			extracted(boardTest3).duplicate(), 
			playersTest3[currentIndexTest3].getColor(), 
			numberOfMoveTest3, 
			totalNumberOfMovesTest3);
	MonteCarlo mc_t3 = new MonteCarlo(
			extracted(boardTest3).duplicate(), 
			playersTest3[currentIndexTest3].getColor(), 
			numberOfMoveTest3, 
			totalNumberOfMovesTest3);

	
	//Boards are OK. Proceed to testing.
	for(int testIndex = 1; testIndex <= 100; ++testIndex) {
		System.out.println("Test3: " + testIndex + " / 100");
		//Reset settings.
		currentIndexTest3 = 0;
		numberOfMoveTest3 = 0;

		//Swap players.
		Player tmp = playersTest3[0];
		playersTest3[0] = playersTest3[1];
		playersTest3[1] = tmp;

		//Reset the board to an initial state. When index is odd generate a 
		//new random board.
		newRandomBoardFour ( testIndex,  extracted(boardTest3), 
				 boardCollectionTest3,  extracted(initialPositionTest3) );
		


		//Run a single game.
		while(numberOfMoveTest3 < totalNumberOfMovesTest3) {
			if(playersTest3[currentIndexTest3].getType().equals("MCTS_H(10)")) {
				//MCTS + H(5) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo + H(5) will select new move.
				
				move = mc10.uct(playersTest3[currentIndexTest3].
						getSimulationNumber());


				extracted(boardTest3).makeMove(move, playersTest3[currentIndexTest3].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest3;

				//Adjust index of current player.
				currentIndexTest3 = (currentIndexTest3 + 1) % 2;
			} else if(playersTest3[currentIndexTest3].getType().equals("MCTS_UCT")) {
				//MCTS (UCT) to play.
				Tuple<Integer, Integer> move;

				//Pure Monte-Carlo will select move.
				
				move = mc_t3.uct(playersTest3[currentIndexTest3].
						getSimulationNumber());


				extracted(boardTest3).makeMove(move, playersTest3[currentIndexTest3].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest3;

				//Adjust index of current player.
				currentIndexTest3 = (currentIndexTest3 + 1) % 2;
			}
		} //end of single game.

		String gameOutcome = Rules.calculateScore(extracted(boardTest3));
		
		outputTest3.append("Match #" + testIndex);
		outputTest3.newLine();
		outputTest3.append("Player 1: " + playersTest3[0].getName() + 
				" Player 2: " + playersTest3[1].getName());
		outputTest3.newLine();

		//Append the result to the text file and update counters..
		String zero = "0";
		
		if(gameOutcome.equals(zero)) {
			//The game was a draw.
			++totalDraws;
			//Append information to the file.
			outputTest3.append("Result: draw");
			outputTest3.newLine();
			outputTest3.close();

			//Update statistics.
			
			boolean valueplayer3 = playersTest3[0].getName().equals("MCTS_H(10)");
			
			updateStaticsA ( valueplayer3,  e1DrawAsPlayer1,
					 e2DrawAsPlayer2,  e1DrawAsPlayer2,  e2DrawAsPlayer1);

		} else {
			//One side wins the game.
			boolean value = true;
			String phrase3_4 = "MCTS_H(10)";
			OneSideWinsTheGame ( gameOutcome,  playersTest3 
					, outputTest3,  value ,  e1TotalWins,
					 e2TotalLoses, e1WinAsPlayer2,  e2LoseAsPlayer1,
					 e2TotalWins,  e1TotalLoses,
					 e2WinAsPlayer2,  e1LoseAsPlayer1, phrase3_4 );
			
		}			
	} //End of the test case. (for)

	//Report when games ended.
	int value3gt4_1000 = 1000;
	//Append total outcome of the test case to the file.
	 temporany = null;
	BufferedWriter output1Test3 = defineBuffers ( temporany,  name4File3);
	
	output1Test3.append("========================================");
	output1Test3.newLine();
	output1Test3.append("*Summary 50k roll-outs 11 point board*");
	output1Test3.newLine();
	output1Test3.append("Draw occurred: " + totalDraws);
	output1Test3.newLine();
	output1Test3.append("MCTS_UCT total wins: " + e2TotalWins);
	output1Test3.newLine();
	output1Test3.append("MCTS_H(10) total wins: " + e1TotalWins);
	output1Test3.newLine();
	output1Test3.append("Play time: " + (endTimeTest3 - startTimeTest3)/value3gt4_1000 + " seconds.");
	output1Test3.newLine();

	//Write statistics for MCTS.
	output1Test3.append("MCTS_UCT wins as player #1 : " + e2WinAsPlayer1);
	output1Test3.newLine();
	output1Test3.append("MCTS_UCT wins as player #2 : " + e2WinAsPlayer2);
	output1Test3.newLine();
	output1Test3.append("MCTS_UCT draws as player #1 : " + e2DrawAsPlayer1);
	output1Test3.newLine();
	output1Test3.append("MCTS_UCT draws as player #2 : " + e2DrawAsPlayer2);
	output1Test3.newLine();
	output1Test3.append("MCTS_UCT loses as player #1 : " + e2LoseAsPlayer1);
	output1Test3.newLine();
	output1Test3.append("MCTS_UCT loses as player #2 : " + e2LoseAsPlayer2);
	output1Test3.newLine();

	//Write statistics for Random AI.
	output1Test3.append("MCTS_H(10) wins as player #1 : " + e1WinAsPlayer1);
	output1Test3.newLine();
	output1Test3.append("MCTS_H(10) wins as player #2 : " + e1WinAsPlayer2);
	output1Test3.newLine();
	output1Test3.append("MCTS_H(10) draws as player #1 : " + e1DrawAsPlayer1);
	output1Test3.newLine();
	output1Test3.append("MCTS_H(10) draws as player #2 : " + e1DrawAsPlayer2);
	output1Test3.newLine();
	output1Test3.append("MCTS_H(10) loses as player #1 : " + e1LoseAsPlayer1);
	output1Test3.newLine();
	output1Test3.append("MCTS_H(10) loses as player #2 : " + e1LoseAsPlayer2);
	output1Test3.newLine();

	output1Test3.append("========================================");
	output1Test3.close();
	
}



public static void testFour () throws Exception {
	
	int e1TotalWins = 0,
			totalDraws = 0,
			e1TotalLoses = 0,
			e1WinAsPlayer1 = 0,
			e1DrawAsPlayer1 = 0,
			e1LoseAsPlayer1 = 0,
			e1WinAsPlayer2 = 0,
			e1DrawAsPlayer2 = 0,
			e1LoseAsPlayer2 = 0,
			e2TotalWins = 0,
			e2TotalLoses = 0,
			e2WinAsPlayer1 = 0,
			e2DrawAsPlayer1 = 0,
			e2LoseAsPlayer1 = 0,
			e2WinAsPlayer2 = 0,
			e2DrawAsPlayer2 = 0,
			e2LoseAsPlayer2 = 0;

	//Board that is used in games.
	Board boardTest4 = null;

	//Board that keeps copy of initial position, used to quickly reset the 
	//board before new game take place.
	Board initialPositionTest4 = null;

	//Array of all boards that are used in the test case.
	Board[] boardCollectionTest4 = null;

	//Index of player that is entitled to make a move.
	int currentIndexTest4 = 0;

	//Number of all moves that was made during the game.
	int numberOfMoveTest4 = 0;
	int value4_grt4_50000 = 50000; 
	//Players participating in the test case.
	Player[] playersTest4 =  {
			new Player("MCTS_UCT", "MCTS_UCT", "w", value4_grt4_50000),
			new Player("MCTS_H(5+5)", "MCTS_H(5+5)", "b", value4_grt4_50000)
	};

	//Number of total moves. It is used to check whether the game is in 
	//terminate state or not (the game finishes when there is no empty 
	//fields in the board).
	int totalNumberOfMovesTest4 = 38;

	//Load board.
	String name4Board4 = "50_boards_11.sav";
	loadBoard ( boardCollectionTest4, name4Board4);
	
	
	//The beginning and the end of the test.
	long startTimeTest4 = 0, endTimeTest4 = 0;

	//Report when games commenced.
	startTimeTest4 = System.currentTimeMillis();

	String name4File4 = "results_50k_11b_MCTS_UCTvMCTS_H(5+5).txt";
	BufferedWriter temporany = null;
	BufferedWriter outputTest4 = defineBuffers ( temporany,  name4File4);
	//Define buffers.
	
	MonteCarloH55 mc_h55 = new MonteCarloH55(
			extracted(boardTest4).duplicate(), 
			playersTest4[currentIndexTest4].getColor(), 
			numberOfMoveTest4, 
			totalNumberOfMovesTest4);
	MonteCarlo mc_t4 = new MonteCarlo(
			extracted(boardTest4).duplicate(), 
			playersTest4[currentIndexTest4].getColor(), 
			numberOfMoveTest4, 
			totalNumberOfMovesTest4);
	
	//Boards are OK. Proceed to testing.
	for(int testIndex = 1; testIndex <= 100; ++testIndex) {
		System.out.println("Test4: " + testIndex + " / 100");
		//Reset settings.
		currentIndexTest4 = 0;
		numberOfMoveTest4 = 0;

		//Swap players.
		Player tmp = playersTest4[0];
		playersTest4[0] = playersTest4[1];
		playersTest4[1] = tmp;

		//Reset the board to an initial state. When index is odd generate a 
		//new random board.
		newRandomBoardFour ( testIndex,  extracted(boardTest4), 
				 boardCollectionTest4,  extracted(initialPositionTest4) );

		//Run a single game.
		while(numberOfMoveTest4 < totalNumberOfMovesTest4) {
			if(playersTest4[currentIndexTest4].getType().equals("MCTS_H(5+5)")) {
				//MCTS + H(5) to play.
				Tuple<Integer, Integer> move;

				//Pure Monte-Carlo + H(5) will select new move.
				

				move = mc_h55.uct(playersTest4[currentIndexTest4].
						getSimulationNumber());


				extracted(boardTest4).makeMove(move, playersTest4[currentIndexTest4].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest4;

				//Adjust index of current player.
				currentIndexTest4 = (currentIndexTest4 + 1) % 2;
			} else if(playersTest4[currentIndexTest4].getType().equals("MCTS_UCT")) {
				//MCTS (UCT) to play.
				Tuple<Integer, Integer> move;

				//Pure Monte-Carlo will select move.
				

				move = mc_t4.uct(playersTest4[currentIndexTest4].
						getSimulationNumber());


				extracted(boardTest4).makeMove(move, playersTest4[currentIndexTest4].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest4;

				//Adjust index of current player.
				currentIndexTest4 = (currentIndexTest4 + 1) % 2;
			}
		} //end of single game.

		String gameOutcome = Rules.calculateScore(extracted(boardTest4));
		
		outputTest4.append("Match #" + testIndex);
		outputTest4.newLine();
		outputTest4.append("Player 1: " + playersTest4[0].getName() + 
				" Player 2: " + playersTest4[1].getName());
		outputTest4.newLine();

		//Append the result to the text file and update counters..
		String zero = "0";
		
		if(gameOutcome.equals(zero)) {
			//The game was a draw.
			++totalDraws;
			//Append information to the file.
			outputTest4.append("Result: draw");
			outputTest4.newLine();
			outputTest4.close();

			//Update statistics.
			boolean valuePlayer4 = playersTest4[0].getName().equals("MCTS_H(5+5)");
			updateStaticsA ( valuePlayer4,  e1DrawAsPlayer1,
					 e2DrawAsPlayer2,  e1DrawAsPlayer2,  e2DrawAsPlayer1);

		} else {
			//One side wins the game.
			boolean value = true;
			String phrase4_4 = "MCTS_H(5+5)";
			OneSideWinsTheGame ( gameOutcome,  playersTest4 
					, outputTest4,  value ,  e1TotalWins,
					 e2TotalLoses, e1WinAsPlayer2,  e2LoseAsPlayer1,
					 e2TotalWins,  e1TotalLoses,
					 e2WinAsPlayer2,  e1LoseAsPlayer1, phrase4_4 );
			
		}			
	} //End of the test case. (for)

	//Report when games ended.
	int value4gt4_1000 = 1000;
	//Append total outcome of the test case to the file.
	 temporany = null;
	BufferedWriter output1Test4 = defineBuffers ( temporany,  name4File4);
	
	
	output1Test4.append("========================================");
	output1Test4.newLine();
	output1Test4.append("*Summary 50k roll-outs 11 point board*");
	output1Test4.newLine();
	output1Test4.append("Draw occurred: " + totalDraws);
	output1Test4.newLine();
	output1Test4.append("MCTS_UCT total wins: " + e2TotalWins);
	output1Test4.newLine();
	output1Test4.append("MCTS_H(5+5) total wins: " + e1TotalWins);
	output1Test4.newLine();
	output1Test4.append("Play time: " + (endTimeTest4 - startTimeTest4)/value4gt4_1000 + " seconds.");
	output1Test4.newLine();

	//Write statistics for MCTS.
	output1Test4.append("MCTS_UCT wins as player #1 : " + e2WinAsPlayer1);
	output1Test4.newLine();
	output1Test4.append("MCTS_UCT wins as player #2 : " + e2WinAsPlayer2);
	output1Test4.newLine();
	output1Test4.append("MCTS_UCT draws as player #1 : " + e2DrawAsPlayer1);
	output1Test4.newLine();
	output1Test4.append("MCTS_UCT draws as player #2 : " + e2DrawAsPlayer2);
	output1Test4.newLine();
	output1Test4.append("MCTS_UCT loses as player #1 : " + e2LoseAsPlayer1);
	output1Test4.newLine();
	output1Test4.append("MCTS_UCT loses as player #2 : " + e2LoseAsPlayer2);
	output1Test4.newLine();

	//Write statistics for Random AI.
	output1Test4.append("MCTS_H(5+5) wins as player #1 : " + e1WinAsPlayer1);
	output1Test4.newLine();
	output1Test4.append("MCTS_H(5+5) wins as player #2 : " + e1WinAsPlayer2);
	output1Test4.newLine();
	output1Test4.append("MCTS_H(5+5) draws as player #1 : " + e1DrawAsPlayer1);
	output1Test4.newLine();
	output1Test4.append("MCTS_H(5+5) draws as player #2 : " + e1DrawAsPlayer2);
	output1Test4.newLine();
	output1Test4.append("MCTS_H(5+5) loses as player #1 : " + e1LoseAsPlayer1);
	output1Test4.newLine();
	output1Test4.append("MCTS_H(5+5) loses as player #2 : " + e1LoseAsPlayer2);
	output1Test4.newLine();

	output1Test4.append("========================================");
	output1Test4.close();
	
}


public static void testFive () throws Exception {
	
	int e1TotalWins = 0,
			totalDraws = 0,
			e1TotalLoses = 0,
			e1WinAsPlayer1 = 0,
			e1DrawAsPlayer1 = 0,
			e1LoseAsPlayer1 = 0,
			e1WinAsPlayer2 = 0,
			e1DrawAsPlayer2 = 0,
			e1LoseAsPlayer2 = 0,
			e2TotalWins = 0,
			e2TotalLoses = 0,
			e2WinAsPlayer1 = 0,
			e2DrawAsPlayer1 = 0,
			e2LoseAsPlayer1 = 0,
			e2WinAsPlayer2 = 0,
			e2DrawAsPlayer2 = 0,
			e2LoseAsPlayer2 = 0;

	//Board that is used in games.
	Board boardTest5 = null;

	//Board that keeps copy of initial position, used to quickly reset the 
	//board before new game take place.
	Board initialPositionTest5 = null;

	//Array of all boards that are used in the test case.
	Board[] boardCollectionTest5 = null;

	//Index of player that is entitled to make a move.
	int currentIndexTest5 = 0;

	//Number of all moves that was made during the game.
	int numberOfMoveTest5 = 0;
	int value5_grt4_50000 = 50000; 
	//Players participating in the test case.
	Player[] playersTest5 =  {
			new Player("MCTS_H(5)", "MCTS_H(5)", "w", value5_grt4_50000),
			new Player("MCTS_H(7)", "MCTS_H(7)", "b", value5_grt4_50000)
	};

	//Number of total moves. It is used to check whether the game is in 
	//terminate state or not (the game finishes when there is no empty 
	//fields in the board).
	int totalNumberOfMovesTest5 = 38;

	//Load board.
	String name4Board5 = "50_boards_11.sav";
	loadBoard ( boardCollectionTest5, name4Board5);
	
	//The beginning and the end of the test.
	long startTimeTest5 = 0, endTimeTest5 = 0;

	//Report when games commenced.
	startTimeTest5 = System.currentTimeMillis();
	String name4File5 = "results_50k_11b_MCTS_H(5)vMCTS_H(7).txt";
	BufferedWriter temporany = null;
	BufferedWriter outputTest5 = defineBuffers ( temporany,  name4File5);
	//Define buffers.
	
	MonteCarloH7 h7_mc = new MonteCarloH7(
			extracted(boardTest5).duplicate(), 
			playersTest5[currentIndexTest5].getColor(), 
			numberOfMoveTest5, 
			totalNumberOfMovesTest5);
	MonteCarloH5 mc_t5 = new MonteCarloH5(
			extracted(boardTest5).duplicate(), 
			playersTest5[currentIndexTest5].getColor(), 
			numberOfMoveTest5, 
			totalNumberOfMovesTest5);
	
	//Boards are OK. Proceed to testing.
	for(int testIndex = 1; testIndex <= 100; ++testIndex) {
		System.out.println("Test5: " + testIndex + " / 100");
		//Reset settings.
		currentIndexTest5 = 0;
		numberOfMoveTest5 = 0;

		//Swap players.
		Player tmp = playersTest5[0];
		playersTest5[0] = playersTest5[1];
		playersTest5[1] = tmp;

		//Reset the board to an initial state. When index is odd generate a 
		//new random board.
		newRandomBoardFour ( testIndex,  extracted(boardTest5), 
				 boardCollectionTest5,  extracted(initialPositionTest5) );


		//Run a single game.
		while(numberOfMoveTest5 < totalNumberOfMovesTest5) {
			if(playersTest5[currentIndexTest5].getType().equals("MCTS_H(7)")) {
				//MCTS + H(10) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo + H(10) will select new move.
//				Charles_2 charles = new Charles_2(playersTest5[currentIndexTest5].getColor(), boardTest5);
//				move = charles.getMove();
				
				move = h7_mc.uct(playersTest5[currentIndexTest5].
						getSimulationNumber());



				extracted(boardTest5).makeMove(move, playersTest5[currentIndexTest5].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest5;

				//Adjust index of current player.
				currentIndexTest5 = (currentIndexTest5 + 1) % 2;
			} else if(playersTest5[currentIndexTest5].getType().equals("MCTS_H(5)")) {
				//MCTS (UCT) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo will select move.
				
				move = mc_t5.uct(playersTest5[currentIndexTest5].
						getSimulationNumber());


				extracted(boardTest5).makeMove(move, playersTest5[currentIndexTest5].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest5;

				//Adjust index of current player.
				currentIndexTest5 = (currentIndexTest5 + 1) % 2;
			}
		} //end of single game.

		String gameOutcome = Rules.calculateScore(extracted(boardTest5));
		
		outputTest5.append("Match #" + testIndex);
		outputTest5.newLine();
		outputTest5.append("Player 1: " + playersTest5[0].getName() + 
				" Player 2: " + playersTest5[1].getName());
		outputTest5.newLine();

		//Append the result to the text file and update counters..
		String zero = "0";
		
		if(gameOutcome.equals(zero)) {
			//The game was a draw.
			++totalDraws;
			//Append information to the file.
			outputTest5.append("Result: draw");
			outputTest5.newLine();
			outputTest5.close();

			//Update statistics.
			boolean valuePlayers5 = playersTest5[0].getName().equals("MCTS_H(7)");
			updateStaticsA ( valuePlayers5,  e1DrawAsPlayer1,
					 e2DrawAsPlayer2,  e1DrawAsPlayer2,  e2DrawAsPlayer1);

		} else {
			//One side wins the game.
			boolean value = true;
			String phrase5_4 = "MCTS_H(7)";
			OneSideWinsTheGame ( gameOutcome,  playersTest5 
					, outputTest5,  value ,  e1TotalWins,
					 e2TotalLoses, e1WinAsPlayer2,  e2LoseAsPlayer1,
					 e2TotalWins,  e1TotalLoses,
					 e2WinAsPlayer2,  e1LoseAsPlayer1, phrase5_4 );
			
		}			
	} //End of the test case. (for)

	//Report when games ended.
	int value5gt4_1000 = 1000;
	//Append total outcome of the test case to the file.
	 temporany = null;
	BufferedWriter output1Test5 = defineBuffers ( temporany,  name4File5);
	
	
	output1Test5.append("========================================");
	output1Test5.newLine();
	output1Test5.append("*Summary 50k roll-outs 11 point board*");
	output1Test5.newLine();
	output1Test5.append("Draw occurred: " + totalDraws);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(5) total wins: " + e2TotalWins);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(7) total wins: " + e1TotalWins);
	output1Test5.newLine();
	output1Test5.append("Play time: " + (endTimeTest5 - startTimeTest5)/value5gt4_1000 + " seconds.");
	output1Test5.newLine();

	//Write statistics for MCTS.
	output1Test5.append("MCTS_H(5) wins as player #1 : " + e2WinAsPlayer1);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(5) wins as player #2 : " + e2WinAsPlayer2);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(5) draws as player #1 : " + e2DrawAsPlayer1);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(5) draws as player #2 : " + e2DrawAsPlayer2);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(5) loses as player #1 : " + e2LoseAsPlayer1);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(5) loses as player #2 : " + e2LoseAsPlayer2);
	output1Test5.newLine();

	//Write statistics for MCTS_H(7).
	output1Test5.append("MCTS_H(7) wins as player #1 : " + e1WinAsPlayer1);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(7) wins as player #2 : " + e1WinAsPlayer2);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(7) draws as player #1 : " + e1DrawAsPlayer1);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(7) draws as player #2 : " + e1DrawAsPlayer2);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(7) loses as player #1 : " + e1LoseAsPlayer1);
	output1Test5.newLine();
	output1Test5.append("MCTS_H(7) loses as player #2 : " + e1LoseAsPlayer2);
	output1Test5.newLine();

	output1Test5.append("========================================");
	output1Test5.close();

}


public static void testSix () throws Exception {
	
	int e1TotalWins = 0,
			totalDraws = 0,
			e1TotalLoses = 0,
			e1WinAsPlayer1 = 0,
			e1DrawAsPlayer1 = 0,
			e1LoseAsPlayer1 = 0,
			e1WinAsPlayer2 = 0,
			e1DrawAsPlayer2 = 0,
			e1LoseAsPlayer2 = 0,
			e2TotalWins = 0,
			e2TotalLoses = 0,
			e2WinAsPlayer1 = 0,
			e2DrawAsPlayer1 = 0,
			e2LoseAsPlayer1 = 0,
			e2WinAsPlayer2 = 0,
			e2DrawAsPlayer2 = 0,
			e2LoseAsPlayer2 = 0;

	//Board that is used in games.
	Board boardTest6 = null;

	//Board that keeps copy of initial position, used to quickly reset the 
	//board before new game take place.
	Board initialPositionTest6 = null;

	//Array of all boards that are used in the test case.
	Board[] boardCollectionTest6 = null;

	//Index of player that is entitled to make a move.
	int currentIndexTest6 = 0;

	//Number of all moves that was made during the game.
	int numberOfMoveTest6 = 0;
	int value6_grt4_50000 = 50000; 
	//Players participating in the test case.
	Player[] playersTest6 =  {
			new Player("MCTS_H(7)", "MCTS_H(7)", "w", value6_grt4_50000),
			new Player("MCTS_H(10)", "MCTS_H(10)", "b", value6_grt4_50000)
	};

	//Number of total moves. It is used to check whether the game is in 
	//terminate state or not (the game finishes when there is no empty 
	//fields in the board).
	int totalNumberOfMovesTest6 = 38;

	//Load board.
	String name4Board6 = "50_boards_11.sav";
	loadBoard ( boardCollectionTest6, name4Board6);
	
	//The beginning and the end of the test.
	long startTimeTest6 = 0, endTimeTest6 = 0;

	//Report when games commenced.
	startTimeTest6 = System.currentTimeMillis();
	String name4File6 = "results_50k_11b_MCTS_H(7)vMCTS_H(10).txt";
	BufferedWriter temporany = null;
	BufferedWriter outputTest6 = defineBuffers ( temporany,  name4File6);
	//Define buffers.
	
	MonteCarloH10 mc_h10 = new MonteCarloH10(
			extracted(boardTest6).duplicate(), 
			playersTest6[currentIndexTest6].getColor(), 
			numberOfMoveTest6, 
			totalNumberOfMovesTest6);
	MonteCarloH7 mc_t6 = new MonteCarloH7(
			extracted(boardTest6).duplicate(), 
			playersTest6[currentIndexTest6].getColor(), 
			numberOfMoveTest6, 
			totalNumberOfMovesTest6);
	
	//Boards are OK. Proceed to testing.
	for(int testIndex = 1; testIndex <= 100; ++testIndex) {
		System.out.println("Test6: " + testIndex + " / 100");
		//Reset settings.
		currentIndexTest6 = 0;
		numberOfMoveTest6 = 0;

		//Swap players.
		Player tmp = playersTest6[0];
		playersTest6[0] = playersTest6[1];
		playersTest6[1] = tmp;

		//Reset the board to an initial state. When index is odd generate a 
		//new random board.
		newRandomBoardFour ( testIndex,  extracted(boardTest6), 
				boardCollectionTest6,  extracted(initialPositionTest6) );

		//Run a single game.
		while(numberOfMoveTest6 < totalNumberOfMovesTest6) {
			if(playersTest6[currentIndexTest6].getType().equals("MCTS_H(10)")) {
				//MCTS + H(10) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo + H(10) will select new move.
//				Charles_2 charles = new Charles_2(playersTest6[currentIndexTest6].getColor(), boardTest6);
//				move = charles.getMove();
				
				move = mc_h10.uct(playersTest6[currentIndexTest6].
						getSimulationNumber());



				extracted(boardTest6).makeMove(move, playersTest6[currentIndexTest6].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest6;

				//Adjust index of current player.
				currentIndexTest6 = (currentIndexTest6 + 1) % 2;
			} else if(playersTest6[currentIndexTest6].getType().equals("MCTS_H(7)")) {
				//MCTS (UCT) to play.
				Tuple<Integer, Integer> move;
				//Pure Monte-Carlo will select move.
				
				move = mc_t6.uct(playersTest6[currentIndexTest6].
						getSimulationNumber());


				extracted(boardTest6).makeMove(move, playersTest6[currentIndexTest6].getColor());

				//Increment number of currently made moves.
				++numberOfMoveTest6;

				//Adjust index of current player.
				currentIndexTest6 = (currentIndexTest6 + 1) % 2;
			}
		} //end of single game.

		String gameOutcome = Rules.calculateScore(extracted(boardTest6));
		outputTest6.append("Match #" + testIndex);
		outputTest6.newLine();
		outputTest6.append("Player 1: " + playersTest6[0].getName() + 
				" Player 2: " + playersTest6[1].getName());
		outputTest6.newLine();

		//Append the result to the text file and update counters..
		String zero = "0";
		
		if(gameOutcome.equals(zero)) {
			//The game was a draw.
			++totalDraws;
			//Append information to the file.
			outputTest6.append("Result: draw");
			outputTest6.newLine();
			outputTest6.close();
			outputTest6.flush();

			//Update statistics.
			boolean valuePlayers6 = playersTest6[0].getName().equals("MCTS_H(10)");
			updateStaticsA ( valuePlayers6,  e1DrawAsPlayer1,
					 e2DrawAsPlayer2,  e1DrawAsPlayer2,  e2DrawAsPlayer1);
		

		} else {
			boolean value = true;
			//One side wins the game.
			String phrase4_6 = "MCTS_H(10)";
			OneSideWinsTheGame ( gameOutcome,  playersTest6 
					, outputTest6,  value ,  e1TotalWins,
					 e2TotalLoses, e1WinAsPlayer2,  e2LoseAsPlayer1,
					 e2TotalWins,  e1TotalLoses, e2WinAsPlayer2, 
					 e1LoseAsPlayer1, phrase4_6);
			
		}			
	} //End of the test case. (for)

	//Report when games ended.
	int value6gt4_1000 = 1000;
	//Append total outcome of the test case to the file.
	 temporany = null;
	BufferedWriter output1Test6 = defineBuffers ( temporany,  name4File6);
	
	output1Test6.append("========================================");
	output1Test6.newLine();
	output1Test6.append("*Summary 50k roll-outs 11 point board*");
	output1Test6.newLine();
	output1Test6.append("Draw occurred: " + totalDraws);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(7) total wins: " + e2TotalWins);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(10) total wins: " + e1TotalWins);
	output1Test6.newLine();
	output1Test6.append("Play time: " + (endTimeTest6 - startTimeTest6)/value6gt4_1000 + " seconds.");
	output1Test6.newLine();

	//Write statistics for MCTS (UCT).
	output1Test6.append("MCTS_H(7) wins as player #1 : " + e2WinAsPlayer1);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(7) wins as player #2 : " + e2WinAsPlayer2);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(7) draws as player #1 : " + e2DrawAsPlayer1);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(7) draws as player #2 : " + e2DrawAsPlayer2);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(7) loses as player #1 : " + e2LoseAsPlayer1);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(7) loses as player #2 : " + e2LoseAsPlayer2);
	output1Test6.newLine();

	//Write statistics for MCTS + H(5).
	output1Test6.append("MCTS_H(10) wins as player #1 : " + e1WinAsPlayer1);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(10) wins as player #2 : " + e1WinAsPlayer2);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(10) draws as player #1 : " + e1DrawAsPlayer1);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(10) draws as player #2 : " + e1DrawAsPlayer2);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(10) loses as player #1 : " + e1LoseAsPlayer1);
	output1Test6.newLine();
	output1Test6.append("MCTS_H(10) loses as player #2 : " + e1LoseAsPlayer2);
	output1Test6.newLine();

	output1Test6.append("========================================");
	output1Test6.close();
	output1Test6.flush();

	
}

public static void loadBoard (Board[] boardCollectionTest1, String nameBoard) {
	FileInputStream fisTest = null ;
	//Load board.
	try {
		 fisTest = new FileInputStream(nameBoard);
		ObjectInputStream oisTest1 = new ObjectInputStream(fisTest);
		boardCollectionTest1 = (Board[]) oisTest1.readObject();
		oisTest1.close();
	} catch(Exception e) {
		System.err.println("Error" + e.getMessage());
	} finally {
		   if (fisTest != null) {
               try {
            	   fisTest.close (); 
               } catch (java.io.IOException e3) {
                 System.out.println("I/O Exception");
               }	
           	}
		
		}
	}
public static  BufferedWriter defineBuffers (BufferedWriter output1Test, String nameFile) {
	
	try {
	 output1Test = new BufferedWriter(
			new FileWriter(nameFile, true));
	} catch(Exception e) {
		System.err.println("Error occured during saving.");
		System.out.println("Something was wrong");
	}finally {
           if (output1Test != null) {
               try {
            	   output1Test.close (); 
               } catch (java.io.IOException e3) {
                 System.out.println("I/O Exception");
               }	
           }
	}
	return output1Test;
}

public static void OneSideWinsTheGame (String gameOutcome, Player[] playersTest1 
		,BufferedWriter outputTest1, boolean value, int e1TotalWins,
		int e2TotalLoses,int e1WinAsPlayer2, int e2LoseAsPlayer1,
		int e2TotalWins, int e1TotalLoses, int e2WinAsPlayer2, int e1LoseAsPlayer1, String phrase ) throws IOException {
	//One side wins the game.
	if(gameOutcome.equals(playersTest1[0].getColor())) {
		//Player #1, whoever it is, wins the game.

		//Add note about the winner to the file.
		outputTest1.append("Result: " + playersTest1[0].getName() + " wins");

		//Update statistics.
		boolean valuePlayers1 = playersTest1[0].getName().equals(phrase);
		updateStatisticsB ( valuePlayers1,  e1TotalWins,
				 e2TotalLoses, e1WinAsPlayer2,  e2LoseAsPlayer1,
				 e2TotalWins,  e1TotalLoses,  e2WinAsPlayer2,  e1LoseAsPlayer1);

	} else {
		//Player #2, whoever it is, wins the game.

		//Add note about the winner to the file.
		outputTest1.append("Result: " + playersTest1[1].getName() + " wins");

		//Update statistics.
		boolean valuePlayers1 = playersTest1[1].getName().equals(phrase);
		updateStatisticsB ( valuePlayers1,  e1TotalWins,
				 e2TotalLoses, e1WinAsPlayer2,  e2LoseAsPlayer1,
				 e2TotalWins,  e1TotalLoses,  e2WinAsPlayer2,  e1LoseAsPlayer1);
	}
	outputTest1.newLine();
	outputTest1.close();
	
}
public static void newRandomBoardFour (int testIndex, Board boardTest1, 
		Board[] boardCollectionTest1, Board initialPositionTest1 ) {
	if(testIndex % 2 == 1) {
		//Load a new board.
		boardTest1 = boardCollectionTest1[(Integer) testIndex/2];
		initialPositionTest1 = extracted(boardTest1).duplicate();
	} else {
		//Reset the board.
		boardTest1 = extracted(initialPositionTest1).duplicate();
	}

	
}

public static void updateStaticsA (boolean value, int e1DrawAsPlayer1,
		int e2DrawAsPlayer2, int e1DrawAsPlayer2, int e2DrawAsPlayer1) {
	
	if(value) {
		e1DrawAsPlayer1++;
		e2DrawAsPlayer2++;
	} else {
		e1DrawAsPlayer2++;
		e2DrawAsPlayer1++;
	}

	
}

public static void updateStatisticsB (boolean valueUS6C, int e1TotalWins,
		int e2TotalLoses,int e1WinAsPlayer2, int e2LoseAsPlayer1,
		int e2TotalWins, int e1TotalLoses, int e2WinAsPlayer2, int e1LoseAsPlayer1) {
	
	if(valueUS6C) {
		e1TotalWins++;
		e2TotalLoses++;

		e1WinAsPlayer2++;
		e2LoseAsPlayer1++;
	} else {
		e2TotalWins++;
		e1TotalLoses++;

		e2WinAsPlayer2++;
		e1LoseAsPlayer1++;
	}
	
}


}
