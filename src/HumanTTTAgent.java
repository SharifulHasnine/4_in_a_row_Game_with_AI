import java.util.Scanner;

public class HumanTTTAgent extends Agent
{

	static Scanner in = new Scanner(System.in);
	public HumanTTTAgent(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void makeMove(Game game) {
		// TODO Auto-generated method stub
		
		int row = 0 ,col;
		TickTackToe tttGame = (TickTackToe) game;
		
		boolean first = true;
		do
		{
			if(first) 	
                            System.out.println("Insert  column ([0,6])");
			else 
                            System.out.println("Invalid input! Insert column ([0,6]) again.");
			col = in.nextInt();
                        for(int i=0;i<6;i++){
                            if(tttGame.board[i][col]==-1)
                            {
                                if(i==5){
                                    row = i;
                                    break;
                                }
                                continue;
                            }
                            else
                            {
                                row = i-1;
                                break;
                            }
                        }
			first=false;
		}while(!tttGame.isValidCell(row, col));
		
		tttGame.board[row][col] = role;
	}


	

}
