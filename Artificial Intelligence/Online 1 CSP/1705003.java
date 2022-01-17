package solution;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class Solution
{
	public static void add(Model model ,IntVar [][] vars) {
		//0 
		model.arithm(vars[0][1], "=", 5).post();
		model.arithm(vars[0][2], "=", 4).post();
		model.arithm(vars[0][6], "=", 8).post();
		model.arithm(vars[0][3], "<", vars[0][4]).post();
		
		// 01
		model.arithm(vars[1][0], "<", vars[0][0]).post();
		model.arithm(vars[0][1], "<", vars[1][1]).post();
		model.arithm(vars[1][7], "<", vars[0][7]).post();

		// 1
		model.arithm(vars[1][4], "<", vars[1][5]).post();
		model.arithm(vars[1][7], "<", vars[1][8]).post();

		// 12
		model.arithm(vars[1][2], "<", vars[2][2]).post();
		model.arithm(vars[1][4], ">", vars[2][4]).post();
		model.arithm(vars[1][7], ">", vars[2][7]).post();
		
		//2
		model.arithm(vars[2][4], "=", 6).post();
		model.arithm(vars[2][2], "<", vars[2][3]).post();
		model.arithm(vars[2][7], "<", vars[2][8]).post();

		//23
		model.arithm(vars[2][0], ">", vars[3][0]).post();
		model.arithm(vars[2][2], "<", vars[3][2]).post();
		model.arithm(vars[2][6], ">", vars[3][6]).post();

		//3
		model.arithm(vars[3][1], ">", vars[3][2]).post();
		model.arithm(vars[3][6], ">", vars[3][7]).post();
		model.arithm(vars[3][7], "<", vars[3][8]).post();
		
		//34
		model.arithm(vars[3][4], ">", vars[4][4]).post();

		//4
		model.arithm(vars[4][0], "=", 2).post();;
		model.arithm(vars[4][2], "=", 8).post();;
		model.arithm(vars[4][5], "<", vars[4][6]).post();;

		//45
		model.arithm(vars[4][0], ">", vars[5][0]).post();
		model.arithm(vars[4][1], "<", vars[5][1]).post();

		//5
		model.arithm(vars[5][1], "=", 7).post();
		model.arithm(vars[5][2], "=", 6).post();
		model.arithm(vars[5][3], "=", 2).post();
		model.arithm(vars[5][7], "=", 9).post();

		//56
		model.arithm(vars[5][8], "<", vars[6][8]).post();

		//6
		model.arithm(vars[6][2], "=", 3).post();
		model.arithm(vars[6][8], "=", 5).post();

		//67
		model.arithm(vars[6][8], "<", vars[7][8]).post();

		//7
		model.arithm(vars[7][3], "=", 6).post();
		model.arithm(vars[7][6], "=", 5).post();
		model.arithm(vars[7][2], ">", vars[7][3]).post();
		model.arithm(vars[7][4], ">", vars[7][5]).post();

		//78

		//8
		model.arithm(vars[8][6], ">", vars[8][7]).post();
		model.arithm(vars[8][1], "=", 3).post();
		model.arithm(vars[8][5], "=", 1).post();

	}
	public static void main(String [] args) {
		Model model = new Model("futoshiki");
		IntVar [][] vars = model.intVarMatrix("var", 9,9,1,9);
		for(int i=0;i<9;i++)
			model.allDifferent(vars[i]).post();
		
		for(int j=0;j<9;j++)
		{
			IntVar [] col = new IntVar[9];
			for(int i=0;i<9;i++)
				col[i]=vars[i][j];
			model.allDifferent(col).post();
		}

		add(model,vars);

		

		Solver solver = model.getSolver();

		solver.printStatistics();

		int totalSolution=0;

		while(solver.solve())
		{
			totalSolution++;
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
					System.out.printf("%d ",vars[i][j].getValue());
				System.out.println("");
			}
			System.out.println("----------------");
		}
		System.out.printf("Total Solution:%d\n",totalSolution);

	}
}