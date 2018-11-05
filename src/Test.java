import java.math.BigInteger;
import java.util.ArrayList;

public class Test {

	private static final BigInteger Na = new BigInteger("6438080068035544392301298549614926991513861075340134329180734395241382648423706300"
														+ "613697153947391340909229373325903847203971333359695492563226209790366866332139039"
														+ "52966175107096769180017646161851573147596390153");
	
	private static final BigInteger Aa = new BigInteger("34325464564574564564768795534569998743457687643234566579654234676796634378768434237"
														+ "897634345765879087764242354365767869780876543424");
			
	private static final BigInteger Ba = new BigInteger("4529238420912791724362124239857322093583572346433245235346437643224675723454676574"
														+ "5246457656354765878442547568543334677652352657235");
	
	private static final BigInteger Nb = new BigInteger("34248723532593458235023583785345602939423526832829428589598243238758257023423848762"
														+ "592328952638237952357326596329329383929859507209357320429309270562348527389358293"
														+ "0285732889238492377364284728834632342522323422");
	
	private static final BigInteger Ab = new BigInteger("343254645645745645647687955345699987434576876432345665796542346767966343787684342378"
														+ "97634345765879087764242354365767869780876543424");
	
	private static final BigInteger Bb = new BigInteger("2424325287356293527923658238572395273563923982395792356283583258263528356285225252525"
														+ "6882909285959238420940257295265329820035324646");
			
	//Test 1
	private static final BigInteger T1a = new BigInteger("4");
	private static final BigInteger T1b = new BigInteger("7");
	private static final BigInteger T1n = new BigInteger("13");
	
	//Test2 2
	private static final BigInteger T2a = new BigInteger("7");
	private static final BigInteger T2b = new BigInteger("3");
	private static final BigInteger T2n = new BigInteger("14");
	
	//Test 3
	private static final BigInteger T3a = new BigInteger("28");
	private static final BigInteger T3b = new BigInteger("14");
	private static final BigInteger T3n = new BigInteger("6");
	
	public static void main(String[] args) {
		
		//Question 2a
		BigInteger Xa = LinearEquationSolver.solveUnique(Aa, Ba, Na);
		LinearEquationSolver.printSolution(Aa, Ba, Na);
		
		//Question 2b
		BigInteger Xb = LinearEquationSolver.solveUnique(Ab, Bb, Nb);
		LinearEquationSolver.printSolution(Ab, Bb, Nb);
		
		//Unsolvable test.
		BigInteger Xt1 = LinearEquationSolver.solveUnique(T1a, T1b, T1n);
		LinearEquationSolver.printSolution(T1a, T1b, T1n);
		
		//Unsolvable test 2
		BigInteger Xt2 = LinearEquationSolver.solveUnique(T2a, T2b, T2n);
		LinearEquationSolver.printSolution(T2a, T2b, T2n);
		
		//No unique solutions test, (a, n) not coprime.
		BigInteger Xt3 = LinearEquationSolver.solveUnique(T3a, T3b, T3n);
		LinearEquationSolver.printSolution(T3a, T3b, T3n);
		
		//2a and 2b to file.
		LinearEquationSolver.computationToFile("Question 2a.txt", Aa, Ba, Na);
		LinearEquationSolver.computationToFile("Question 2b.txt", Ab, Bb, Nb);

	}

}