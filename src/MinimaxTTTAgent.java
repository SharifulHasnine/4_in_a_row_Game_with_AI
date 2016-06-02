import java.util.ArrayList;

public class MinimaxTTTAgent extends Agent
{
    int searchdeep;
    int previous;
    int level;
    public MinimaxTTTAgent(String name) {
        super(name);
    }

    @Override
    public void makeMove(Game game) {
        
       
        //finout the possible place
       
        CellValueTuple perfect;
        TickTackToe FR=(TickTackToe)game;
        searchdeep=-1;
        level=-1;
        perfect =MAX_VALUE(FR,-10000,10000,6); 
        FR.board[perfect.row][perfect.col]=role;
        
       
    }
    
 	
    public CellValueTuple MAX_VALUE(TickTackToe game,int alpha,int beta,int depth){
        int alp=alpha;
        int bet=beta;
        int dep=depth;
        level++;
        searchdeep++;
        CellValueTuple  perfect=new CellValueTuple();
		perfect.utility = -10000;
		
		int winner = game.checkForWin();
		
		//terminal check
		if(winner == role)
		{
			perfect.utility = 1000; //this agent wins
                        level--;
			return perfect;
		}
		else if(winner!=-1) 
		{
			perfect.utility = -1000; //opponent wins
                        level--;
			return perfect;  
		}
		else if (game.isBoardFull())
		{
			perfect.utility = 0; //draw
                        level--;
			return perfect;  
		}
                
        ArrayList<CellValueTuple> possibleplace=new ArrayList<>();
        possibleplace=getplace(game);
        /*for (int i = 0; i <possibleplace.size(); i++) {
             System.out.println(possibleplace.get(i).toString());
        }*/
        if(depth==0){ 
         //  System.out.println("max is calling evaluating ");
            CellValueTuple eva= new CellValueTuple();
            eva=evaluation(game, role,"max");
          //  System.out.println("evaluation utility= "+eva.utility);
            return eva;
        }
        int value=-10000;
        for(int i=0;i<possibleplace.size();i++){
            game.board[possibleplace.get(i).row][possibleplace.get(i).col]=role; 
          //  System.out.println("i am max giving= "+role);
          //  System.out.println("alpha= "+alp+" beta= "+bet+" value= "+value+" deep= "+dep);
           // game.showGameState();
            CellValueTuple temp=new CellValueTuple();
            temp=MIN_VALUE(game,alp,bet,dep-1);
           
            if(temp.utility>=bet){
                 game.board[possibleplace.get(i).row][possibleplace.get(i).col]=-1;
                perfect.utility=temp.utility;
                level--;
                return perfect;
            }
            if(temp.utility>value){
                value=temp.utility;
                alp=value;
                perfect.row=possibleplace.get(i).row;
                perfect.col=possibleplace.get(i).col;
                perfect.utility=value;
            }
            else if(i!=0&&level==0&&previous>searchdeep&&temp.utility==value){
                value=temp.utility;
                alp=value;
                perfect.row=possibleplace.get(i).row;
                perfect.col=possibleplace.get(i).col;
                perfect.utility=value;
            }
            if(level==0){
                previous=searchdeep;
                searchdeep=0;
            }
            game.board[possibleplace.get(i).row][possibleplace.get(i).col]=-1;
        }
        level--;
        return perfect;
    }

/*function MIN-VALUE(state, alpha, beta, depth) returns a utility value
		if state is terminal return Utility(state)
		if depth == 0 return Evaluate(state) 
		v = +Inf
		for each possible action a possible from state do
			v = MIN(v, MAX-VALUE(RESULTS(state,a),alpha, beta, depth-1))
			if v <= alpha return v
			beta = MIN(beta, v)
		return v*/
    
    public CellValueTuple MIN_VALUE(TickTackToe game,int alpha,int beta,int depth){
        int alp=alpha;
        int bet=beta;
        int dep=depth;
        level++;
        searchdeep++;
        CellValueTuple perfect = new CellValueTuple();
		perfect.utility = 10000  ;
		
		int winner = game.checkForWin();
		
		//terminal check
		if(winner == role)
		{
			perfect.utility = 1000; //max wins
                        level--;
			return perfect;
		}
		else if(winner!=-1) 
		{
			perfect.utility = -1000; //min wins
                        level--;
			return perfect;  
		}
		else if (game.isBoardFull())
		{
			perfect.utility = 0; //draw
                        level--;
			return perfect;  
		}
                
        
        ArrayList<CellValueTuple> possibleplace=new ArrayList<>();
        possibleplace=getplace(game);
       /* for (int i = 0; i <possibleplace.size(); i++) {
             System.out.println(possibleplace.get(i).toString());
        }*/
        if(depth==0){
          //  System.out.println("min is calling evaluating ");
            CellValueTuple eva= new CellValueTuple();
            eva=evaluation(game, minRole(),"min");
         //   System.out.println("evaluation utility= "+eva.utility);
            return eva;
        }
        int value=10000;
        for(int i=0;i<possibleplace.size();i++){
            game.board[possibleplace.get(i).row][possibleplace.get(i).col]=minRole();  
        //    System.out.println("i am min giving= "+minRole());
         //   System.out.println("alpha= "+alp+" beta= "+bet+" value= "+value+" deeep= "+dep);
         //   game.showGameState();
            CellValueTuple temp=new CellValueTuple();
            temp=MAX_VALUE(game,alp,bet,dep-1);
            if(temp.utility<=alp)
            {
                game.board[possibleplace.get(i).row][possibleplace.get(i).col]=-1;
                perfect.utility=temp.utility;
                level--;
                return perfect;
            }
            if(temp.utility<value){
                value=temp.utility;
                bet=value;
                perfect.row=possibleplace.get(i).row;
                perfect.col=possibleplace.get(i).col;
                perfect.utility=value;
            }
            game.board[possibleplace.get(i).row][possibleplace.get(i).col]=-1;
        }
        level--;
       return perfect;
    }
    public CellValueTuple evaluation(TickTackToe FR,int role,String who){
        CellValueTuple prediction=new CellValueTuple();
        //find who min
        int minrole;
        int maxrole;
        if(who=="min"){
            minrole=role;
            maxrole=1-role;
        }
        else{
            maxrole=role;
            minrole=1-role;
        }
        //---------min calculation
        int min,max;
        min=0;
        max=0;
            //----find three consecutive and try to make four consecutive
                min=3*gethelp3(FR, minrole);
                max=3*gethelp3(FR, maxrole);
               
            //----find two consecutive and try to make four
                min=min+2*gethelp2(FR, minrole);
                max=max+2*gethelp2(FR, maxrole);
        
        //---------max calculation
        
        //return max-min
        prediction.utility=max-min;
        return prediction;
    }
    public int gethelp3(TickTackToe FR,int minrole){
        int min=0;
         int a,b,c;
         for(int i=0;i<FR.board.length;i++){
                    for(int j=0;j<FR.board[0].length;j++){

                        int index=FR.board.length-1;
                        if(j+1>index||j+2>index||j+3>index)continue;
                        a=FR.board[i][j];
                        b=FR.board[i][j+1];
                        c=FR.board[i][j+2];
                        if (a==b&&b==c&&a==minrole)
                        {

                            if(FR.board[i][j+3]==-1)min++;
                        }

                    }
                }
                //column check
                 for(int i=0;i<FR.board.length;i++){
                    for(int j=0;j<FR.board[0].length;j++){

                         int index=FR.board.length-1;
                            if(i+1>index||i+2>index||i-1<0)continue;
                            a=FR.board[i][j];
                            b=FR.board[i+1][j];
                            c=FR.board[i+2][j];
                            if (a==b&&b==c&&a==minrole)
                            {

                                 if(FR.board[i-1][j]==-1)min++;
                            }

                    }
                }
                //diagonal check
                 for (int i = 0;i<FR.board.length; i++)
                    {
                        for(int j=0;j<FR.board[0].length;j++){
                            int index=FR.board.length-1;
                            int index2=FR.board[0].length-1;
                            if(i+1>index||j+1>index2||i-1<0||j-1<0||i+2>index||j+2>index2)continue;
                            a=FR.board[i][j];
                            b=FR.board[i+1][j+1];
                            c=FR.board[i-1][j-1];
                            if (a==b&&b==c&&a==minrole)
                            {

                                if(FR.board[i+2][j+2]==-1)min++;
                            }

                        }
                        for(int j=0;j<FR.board[0].length;j++){
                            int index=FR.board.length-1;
                            int index2=FR.board[0].length-1;
                            if(i+1>index||j+1>index2||i-1<0||j-1<0||i-2<0||j-2<0)continue;
                            a=FR.board[i][j];
                            b=FR.board[i+1][j+1];
                            c=FR.board[i-1][j-1];
                            if(a==b&&b==c&&a==minrole)
                             {

                                 if(FR.board[i-2][j-2]==-1)min++;
                             }

                        }


                    }
                    for (int i = 0; i<FR.board.length; i++)
                    {
                        for(int j=0;j<FR.board[0].length;j++){
                            int index=FR.board.length-1;
                            int index2=FR.board[0].length-1;
                            if(i+1>index||j-1<0||i-1<0||j+1>index2||i+2>index||j-2<0)continue;
                            a=FR.board[i][j];
                            b=FR.board[i+1][j-1];
                            c=FR.board[i-1][j+1];
                            if (a==b&&b==c&&a==minrole)
                            {

                                if(FR.board[i+2][j-2]==-1)min++;
                            }

                        }
                        for(int j=0;j<FR.board[0].length;j++){
                            int index=FR.board.length-1;
                            int index2=FR.board[0].length-1;
                           if(i+1>index||j-1<0||i-1<0||j+1>index2||i-2<0||j+2>index2)continue;
                            a=FR.board[i][j];
                            b=FR.board[i+1][j-1];
                            c=FR.board[i-1][j+1];
                             if (a==b&&b==c&&a==minrole)
                            {
                               if(FR.board[i-2][j+2]==-1)min++;
                            }

                        }


                    }
                    return min;
    }
     public int gethelp2(TickTackToe FR,int minrole){
       int min=0;
        int a,b;
        //row check;
         for(int i=0;i<FR.board.length;i++){
                    for(int j=0;j<FR.board[0].length;j++){

                        int index=FR.board.length-1;
                        if(j+1>index||j+2>index)continue;
                        a=FR.board[i][j];
                        b=FR.board[i][j+1];
                        if (a==b&&a==minrole)
                        {
                            if(FR.board[i][j+2]==-1)min++;


                        }

                    }
                }
          for(int i=0;i<FR.board.length;i++){
                    for(int j=0;j<FR.board[0].length;j++){

                         int index=FR.board.length-1;
                            if(i+1>index||i-1<0)continue;
                            a=FR.board[i][j];
                            b=FR.board[i+1][j];
                            if (a==b&&a==minrole)
                            {
                                 if(FR.board[i-1][j]==-1)min++;
                            }

                    }
                }
                //diagonal check
                 for (int i = 0;i<FR.board.length; i++)
                    {
                        for(int j=0;j<FR.board[0].length;j++){
                            int index=FR.board.length-1;
                            int index2=FR.board[0].length-1;
                            if(i+1>index||j+1>index2||i-1<0||j-1<0)continue;
                            a=FR.board[i][j];
                            b=FR.board[i+1][j+1];
                            if (a==b&&a==minrole)
                            {
                                if(FR.board[i-1][j-1]==-1)min++;
                            }
                        }
                        for(int j=0;j<FR.board[0].length;j++){
                            int index=FR.board.length-1;
                            int index2=FR.board[0].length-1;
                            if(i+1>index||j+1>index2||i+2>index||j+2>index2)continue;
                            a=FR.board[i][j];
                            b=FR.board[i+1][j+1];
                            if (a==b&&a==minrole)
                            {
                                if(FR.board[i+2][j+2]==-1)min++;
                            }
                        }
                    }
                    for (int i = 0;i<FR.board.length; i++)
                    {
                        for(int j=0;j<FR.board[0].length;j++){
                            int index=FR.board.length-1;
                            int index2=FR.board[0].length-1;
                            if(i+1>index||j-1<0||i-1<0||j+1>index2)continue;
                            a=FR.board[i][j];
                            b=FR.board[i+1][j-1];
                            if (a==b&&a==minrole)
                            {
                               if(FR.board[i-1][j+1]==-1)min++;
                            }
                        }
                        for(int j=0;j<FR.board[0].length;j++){
                            int index=FR.board.length-1;
                            int index2=FR.board[0].length-1;
                            if(i+1>index||j-1<0||i+2>index||j-2<0)continue;
                            a=FR.board[i][j];
                            b=FR.board[i+1][j-1];
                            if (a==b&&a==minrole)
                            {
                             //   System.out.println(""+(i+2)+","+(j-2));
                               if(FR.board[i+2][j-2]==-1)min++;
                            }
                        }
                    }
                    return min;
    }
    public ArrayList<CellValueTuple> getplace(TickTackToe FR){
        ArrayList<CellValueTuple> list=new ArrayList<>();
        CellValueTuple cell;
        int count=0;
        for(int col=0;col<FR.board[0].length;col++){
            for(int row=0;row<FR.board.length;row++){
                if(FR.isValidCell(FR.board.length-1-row, col)){
                    cell=new CellValueTuple();
                    cell.row=FR.board.length-1-row;
                    cell.col=col;
                    list.add(count++, cell);
                    break;
                }
            }
        }
        return list;
    }
    class CellValueTuple
	{
		int row,col, utility;
		public CellValueTuple() {
			// TODO Auto-generated constructor stub
			row =-1;
			col =-1;
		}
                @Override
                public String toString(){
                    return "("+row+","+col+")utility="+utility;
                }
	}
    private int minRole()
	{
		if(role==0)return 1;
		else return 0;
	}
       

}
