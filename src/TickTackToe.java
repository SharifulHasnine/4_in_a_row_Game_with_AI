public class TickTackToe extends Game 
{

	/**
	 * The actual game board
	 * -1 empty, 0 -> O, 1 -> X
	 */
	public int[][] board;
	
	/**
	 * First agent starts with O
	 * @param a
	 * @param b
	 */
	public TickTackToe(Agent a, Agent b) {
		super(a, b);
		// TODO Auto-generated constructor stub
		
		a.setRole(0); // The first argument/agent is always assigned O (0)
		b.setRole(1); // The second argument/agent is always assigned X (1)
					  // NOtice that, here first dows not mean that afent a will make the first move of the game.
					  // Here, first means the first argument of the constructor
					  // Which of a and b will actually give the first move is chosen randomly. See Game class
		
		name = "Tick Tack Toe";
		
            	board = new int[6][7];
		
	}

	/**
	 * Called by the play method of Game class. It must update the winner variable. 
	 * In this implementation, it is done inside checkForWin() function.
	 */
	@Override
	boolean isFinished() {
		// TODO Auto-generated method stub
		if(checkForWin() != -1)
			return true;
		else if(isBoardFull())
			return true;
		else 
                    return false;
	}

	@Override
	void initialize(boolean fromFile) {
		// TODO Auto-generated method stub
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = -1;
			}
		}
	}

	/**
	 * Prints the current board (may be replaced/appended with by GUI)
	 */
	@Override
	void showGameState() {
		// TODO Auto-generated method stub
		 
        System.out.println("-------------");
		
        for (int i = 0; i < 6; i++)    //chcn 
        {
            System.out.print("| ");
            for (int j = 0; j < 7; j++) 
            {
            	if(board[i][j]==-1)
            		System.out.print(" " + " | ");
            	else if	(board[i][j]==0)
            		System.out.print( "O | ");
            	else
            		System.out.print( "X | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
	
    /** Loop through all cells of the board and if one is found to be empty (contains -1) then return false.
    	Otherwise the board is full.
    */
    public boolean isBoardFull() {

		
        for (int i = 0; i < 6; i++) //chnc
        {
            for (int j = 0; j < 7; j++) 
            {
                if (board[i][j] == -1) 
                {
                   return false;
                }
            }
        }
		
        return true;
    }
	
	
    /** Returns role of the winner, if no winner/ still game is going on -1.
     * @return role of the winner, if no winner/ still game is going on -1.
     */
    public int checkForWin() 
    {
    	winner = null;
    	int winRole=-1;
    	//row
        /*for (int i = 0; i < 6; i++)   //row wise change 
        {
            int temp = 0;
            for(int j=0;j<4;j++){
                if (checkRowCol(board[i][j], board[i][j+1], board[i][j+2],board[i][j+3]) == true) 
                {
                    winRole = board[i][j];
                    temp = 1;
                    break;
                }
            }
            if(temp == 1)
                break;
        }*/
        //ROW
        for (int i = 0; i <board.length; i++) 
        {
            for(int j=0;j<board[0].length;j++){
                int index=board[0].length-1;
                if(j+1>index||j+2>index||j+3>index)continue;
                if (checkRowCol(board[i][j], board[i][j+1], board[i][j+2],board[i][j+3]) == true) 
                {
                    winRole = board[i][j];
                  //  System.out.println("yes i am rowwwwwwwwwwwwwwwwww "+ winRole );
                   // return winRole;
                }
            }
        }
        //COLOUMN
        
        
        for (int i = 0; i <board.length; i++) 
        {
            for(int j=0;j<board[0].length;j++){
                int index=board.length-1;
                if(i+1>index||i+2>index||i+3>index)continue;
                if (checkRowCol(board[i][j], board[i+1][j], board[i+2][j],board[i+3][j]) == true) 
                {
                    winRole = board[i][j];
                 //    System.out.println("yes i am comunnnnnnnnnnnnnnnnnnnn "+ winRole);
                   // return winRole;
                }
            }
        }
        
       
        for(int k=0;k<3;k++)
        {
            for(int i=k;i+4<=6;i++){
                if(check(i,i-k)==true){   //lower left
                    winRole = board[i][i-k];
                }
                else if(check(i-k,i+1)==true){ //upper right
                    winRole = board[i-k][i+1];
                }
                else if(check2(i,6-i+k)==true){  //lower right
                    winRole = board[i][6-i+k];
                }
                else if(check2(i-k,5-i)==true){   //upper left
                    winRole = board[i-k][5-i];
                }
            }
        }
        
        // lower left diagonal
        for(int k=0;k<3;k++)
        {
            
                for(int i=k;i+4<=6;i++){
                    if(check(i,i-k)){
                        winRole = board[i][i-k];
                    }
                }
            
            
        }
        //upper right diagonal
        for(int k=0;k<3;k++)
        {
            
                for(int i=k;i+1+4<=7;i++){
                    if(check(i-k,i+1)){
                        winRole = board[i-k][i+1];
                    }
                }
            
            
        }
        
        //lower right diagonal
        for(int k=0;k<3;k++)
        {
            for(int i=k;i+4<=6;i++){
                    if(check2(i,6-k)){
                        winRole = board[i][i];
                    }
            }
        }
        //upper left diagonal
        for(int k=0;k<3;k++)
        {
            
                for(int i=k;i+4<=6;i++){
                    if(check2(i-k,5-k)){
                        winRole = board[i][i];
                    }
                }
        }
        
    	/*if(checkRowCol(board[0][0], board[1][1], board[2][2]))
    		winRole =  board[0][0];
    	
    	if (checkRowCol(board[0][2], board[1][1], board[2][0]))
    		winRole =  board[0][2];
*/
    	if(winRole!=-1)
    	{
    		winner = agent[winRole];
    	}
	return winRole;
    }
	
	
    public boolean check(int starting, int end){
        if( checkEqual(board[starting][end], board[starting+1][end+1],board[starting +2][end+2],board[starting+3][end+3])==true ){
            return true;
        }
        return false;
    }
    
    
    public boolean check2(int starting, int end){
        if( checkEqual(board[starting][end], board[starting+1][end-1],board[starting +2][end-2],board[starting+3][end-3])==true ){
            return true;
        }
        return false;
    }
    
    public boolean checkEqual(int a,int b,int c,int d){
        if((a!=-1)&&(a==b)&&(b==c)&&(c==d)){
            return true;
        }
        return false;
    }
    // Check to see if all three values are the same (and not empty) indicating a win.
    private boolean checkRowCol(int c1, int c2, int c3,int c4) 
    {
        return ((c1 != -1) && (c1 == c2) && (c2 == c3)&&(c3==c4));
    }
	
	public boolean isValidCell(int row, int col)
	{
		if(row<0 || row>6 ||col<0 ||col>7) return false;
		if(board[row][col]!=-1) return false;
		
		return true;
	}

	@Override
	void updateMessage(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}
	
}
