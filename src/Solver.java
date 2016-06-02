public class Solver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Agent human = new HumanTTTAgent("Neo");
		//Agent human = new MinimaxTTTAgent("007");
		Agent machine = new MinimaxTTTAgent("Smith");

		//System.out.println(human.name+" vs. "+machine.name);
		
		Game game = new TickTackToe(human,machine);
		game.play();
    }
    
}
