import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * A class for solving linear equations in the form ax + b = 0 (mod n).
 * Has methods for checking how many solutoions a given equation has 
 * and if it has any solutions at all.
 * @author Joseph Whitten
 * @version 3
 *
 */
public class LinearEquationSolver {
	
	//Some useful constants 
	public static final BigInteger ONE = BigInteger.ONE;
	public static final BigInteger ZERO = BigInteger.ZERO;
	
	/**
	 * Checks to see if an equation has a unique solution for x.
	 * @param a : Coefficient of x in (ax + b = 0 (mod n).
	 * @param b : Constant b in (ax + b = 0 (mod n).
	 * @param n : Modulus n in (ax + b = 0 (mod n).
	 * @return x : Solution x such that (ax + b = 0 (mod n) is true, null if its unsolvable. 
	 */
	public static BigInteger solveUnique(BigInteger a, BigInteger b, BigInteger n) {
		
		BigInteger x = null;
		//Get gcd of a and n
		BigInteger gcd = getGCD(a, n);
		
		//Check first if the equation is solvable, then check if it only has one solution.
		//Should only be true if a and n are coprime.
		if(hasSolution(gcd, b) && hasUniqueSolution(gcd)) {
			
			//Mod Inverse of a in n.
			BigInteger modInverseA = a.modInverse(n);
			BigInteger negativeB = b.negate();
			
			//x = -b * a^-1 (mod n).
			x = negativeB.multiply(modInverseA).mod(n);
			
		}
		
		//return the solution.
		return x;
		
	}
	
	/**
	 * Find the gcd of a and n.
	 * @param a : First BigInteger. 
	 * @param n : Second BigInteger. 
	 * @return d : Where d is the greatest common divisor of a and n. 
	 */
	public static BigInteger getGCD(BigInteger a, BigInteger n) {
		return a.gcd(n);
	}
	
	/**
	 * Checks if the gcd cleanly divides b (d | b). 
	 * @param gcd : A gcd of two integers a and n in (ax + b = 0 (mod n).
	 * @param b : A constant b in (ax + b = 0 (mod n).
	 * @return hasSolution : Does (d | b), if it does then the equation has d solutions.
	 */
	public static boolean hasSolution(BigInteger gcd,  BigInteger b) {
		return (b.mod(gcd)).equals(ZERO);
	}
	
	/**
	 * Checks if the equation has a unique solution.
	 * @param gcd : gcd of a and n in (ax + b = 0 (mod n).
	 * @return hasUniqueSolution : Does the gcd equal one if so the equation has one solution.
	 */
	public static boolean hasUniqueSolution(BigInteger gcd) {
		return gcd.equals(ONE);
	}
	
	/**
	 * Check solution for ax + b = 0 (mod n) is correct.
	 * @param a
	 * @param x
	 * @param b
	 * @param n
	 * @return isCorrect
	 */
	public static boolean checkSolution(BigInteger a, BigInteger x, BigInteger b, BigInteger n) {
		return ((a.multiply(x).add(b)).mod(n)).equals(ZERO);
	}
	
	/**
	 * Prints the process of solving the equation to the console.
	 * @param a : Coefficient of x in (ax + b = 0 (mod n).
	 * @param b : Constant b in (ax + b = 0 (mod n).
	 * @param n : Modulus n in (ax + b = 0 (mod n).
	 */
	public static void printSolution(BigInteger a, BigInteger b, BigInteger n) {
		
		System.out.println("Solving x for ax + b = 0 (mod n)");
		//Get GCD
		BigInteger gcd = getGCD(a, n);
		System.out.println("GCD(a, n) : " + gcd);
		boolean hasSolution = hasSolution(gcd, b);
		
		//Does this equation have a solution?
		if(hasSolution) {
			System.out.println("d | b ?: " + hasSolution + ", equation has " + gcd + " solutions");
			
			//Provided the equation has a solution, check if it has a unique solution.
			boolean hasUniqueSolution = hasUniqueSolution(gcd);
			if(hasUniqueSolution) {
				System.out.println("a and n are coprime, x is a unique solution");
			} else {
				System.out.println("a and n are not coprime, there is no unique solution \nThe modular inverse of a is undefined for n");
			}
			
		} else {
			System.out.println("d | b ?: " + hasSolution + ", equation has no solutions");
		}
		
		//Attempt to solve. Even if we know it has no solutions, return null in this case.
		BigInteger x = solveUnique(a, b, n);
		System.out.println("X = " + x);
		System.out.println();
		
	}
	
	/**
	 * Compute the the solution to a File.
	 * @param path : path of the text file you want to create.
	 * @param a : Coefficient of x in (ax + b = 0 (mod n).
	 * @param b : Constant b in (ax + b = 0 (mod n).
	 * @param n : Modulus n in (ax + b = 0 (mod n).
	 */
	public static void computationToFile(String path, BigInteger a, BigInteger b, BigInteger n) {
		
		//Attempt to solve and get gcd.
		BigInteger x = solveUnique(a, b, n);
		BigInteger gcd = getGCD(a, n);
		boolean hasSolution = hasSolution(gcd, b);
		boolean hasUniqueSolution = hasUniqueSolution(gcd);
		
		try {
			
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			out.write("Solving for x in ax + b = 0 (mod n)");
			out.newLine();
			out.newLine();
			out.write("a: " + a);
			out.newLine();
			out.write("b: " + b);
			out.newLine();
			out.write("n: " + n);
			out.newLine();
			out.write("gcd: " + gcd);
			out.newLine();
			out.newLine();
			
			//Does this equation have a solution?
			if(hasSolution) {
				
				out.write("d | b ?: " + hasSolution + ", equation has " + gcd + " solutions");
				
				//Provided the equation has a solution, check if it has a unique solution.
				if(hasUniqueSolution) {
					out.newLine();
					out.write("a and n are coprime, x is a unique solution");
				} else {
					out.newLine();
					out.write("a and n are not coprime, there is no unique solution \nThe modular inverse of a is undefined for n");
				}
			} else {
				
				out.write("d | b ?: " + hasSolution + ", equation has no solutions");
				
			}
			out.newLine();
			out.newLine();
			
			//Write solution to file.
			if(x == null) {
				
				out.write("x: (null) No Unique Solution");
				
			} else {
				
				out.write("x: " + x);
				
			}
			out.close();
			
		} catch (IOException e) {
		
		}
		
	}
	
}
