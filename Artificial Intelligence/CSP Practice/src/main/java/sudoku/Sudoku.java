/**
 * Sudoku
 */
package sudoku;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;


public class Sudoku {
	
	public static void main(String args[]) {
		System.out.println("Sudoku");
		Model model = new Model("Sudoku");
		IntVar [][] vars = model.intVarMatrix("e", 9, 9, 1, 9);
		for(int i=0;i<9;i++)
		{
			IntVar [] row = new IntVar[9];
			for(int j=0;j<9;j++)
			{
				row[j] = vars[i][j];
			}
			model.allDifferent(row).post();
		}

		for(int j=0;j<9;j++)
		{
			IntVar [] col = new IntVar[9];
			for(int i=0;i<9;i++)
			{
				col[i] = vars[i][j];
			}
			model.allDifferent(col).post();
		}


		for(int x=0;x<9;x+=3)
		{
			for(int y=0;y<9;y+=3)
			{
				IntVar [] grp = new IntVar[9];
				for(int i=0;i<3;i++)
				{
					for(int j=0;j<3;j++)
						grp[i*3+j]=vars[x+i][y+j];
				}
				model.allDifferent(grp).post();
			}
		}

		Solver solver = model.getSolver();
        solver.showStatistics();
        solver.showSolutions();
        solver.findSolution();
		
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
				System.out.printf("%2d ",vars[i][j].getValue() );
			System.out.println("");
		}

	}
}