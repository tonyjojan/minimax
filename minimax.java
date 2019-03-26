/**
 *
 */
package minimax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author tonyjojan
 * @studentid 1866550757
 * @course CSCI360 - ARTIFICIAL INTELLIGENCE
 *
 */
public class minimax {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			FileReader fileReader = new FileReader("input.txt");
			BufferedReader reader = new BufferedReader(fileReader);

			String numHeroes = reader.readLine();
			String algorithm = reader.readLine();

			ArrayList<Hero> heroes = new ArrayList<Hero>();

			while(reader.readLine() != null) {
				String[] currentHeroArray = reader.readLine().split(",");
				Hero currentHero = new Hero(
						Integer.parseInt(currentHeroArray[0]),
						Double.parseDouble(currentHeroArray[1]),
						Double.parseDouble(currentHeroArray[2]),
						Double.parseDouble(currentHeroArray[3]),
						Integer.parseInt(currentHeroArray[4]));

				heroes.add(currentHero);
			}

			double advantage = advantage(heroes);

			Game game = new Game(heroes, 0, advantage);
			if(algorithm.equals("minimax")) {
				int minimax = minimax(game);
				System.out.println(minimax);
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static int minimax(Game game) {

		//base ca	se - terminal test - 10 heroes accounted for
		double result = maximum(game, 2);

		ArrayList<Game> actions = moves(game, 2);

		for(int i = 0; i < actions.size(); i++) {
			if(result == actions.get(i).advantage) {
				return actions.get(i).move;
			}
		}

		return (int)result;


	}

	static double minimum(Game game, int player) {
		double v  = Double.POSITIVE_INFINITY;
		if(game.isTerminal()) {
			return game.advantage;
		}

		ArrayList<Game> actions = moves(game, player);


		for(int i = 0; i < actions.size(); i++) {
			double curr_v = 0.0;
				if(player == 1) {
					curr_v = maximum(actions.get(i), 2);
				}else {
					curr_v = maximum(actions.get(i), 1);

				}
			v = Math.min(v, curr_v);
		}

		return v;

	}

	static double maximum(Game game, int player) {
		double v  = Double.NEGATIVE_INFINITY;
		if(game.isTerminal()) {
			return game.advantage;
		}

		ArrayList<Game> actions = moves(game, player);


		for(int i = 0; i < actions.size(); i++) {
			double curr_v = 0.0;
				if(player == 1) {
					curr_v = minimum(actions.get(i), 2);
				}else {
					curr_v = minimum(actions.get(i), 1);
				}
			v = Math.max(v, curr_v);
		}

		return v;
	}

	static double advantage(ArrayList<Hero> heroes) {
		double advantage = 0.0;

		for(int i = 0;  i < heroes.size(); i++) {

		}

		return advantage;
	}


	static ArrayList<Game> moves(Game game, int player) {
		ArrayList<Game> moves = new ArrayList<Game>();


		Collections.sort(game.heroes, Hero.HeroIdComparator);

		for(int i = 0; i < game.heroes.size(); i++) {
			if(game.heroes.get(i).membership==0) {
				ArrayList<Hero> currentHeroList  = game.heroes;
				currentHeroList.get(i).membership = player;
				double advantage = advantage(currentHeroList);

				Game currentGame = new Game(currentHeroList, currentHeroList.get(i).id, advantage);
				moves.add(currentGame);

			}

		}

		return moves;
	}




}

class Game {
	public ArrayList<Hero> heroes;
	public int move;
	public double advantage;

	public Game(ArrayList<Hero> heroes, int move, double advantage) {
		this.heroes = heroes;
		this.move = move;
		this.advantage = advantage;
	}

	public boolean isTerminal() {
		for(int i = 0; i < this.heroes.size(); i++) {
			if(heroes.get(i).membership == 0) {
				return false;
			}
		}

		return true;

	}
}

class Hero {
	public int id;
	public double p;
	public double mi;
	public double mj;
	public int membership;
	public int move;

	public Hero(int id, double p, double mi, double mj, int membership) {
		this.id = id;
		this.p = p;
		this.mi = mi;
		this.mj = mj;
		this.membership = membership;
		this.move = 0;
	}


	public static Comparator<Hero> HeroIdComparator = new Comparator<Hero>() {
		public int compare(Hero one, Hero two) {
			return one.id - two.id;
		}
	};
}
