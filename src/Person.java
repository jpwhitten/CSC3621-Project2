import java.math.BigInteger;
import java.util.Random;

/**
 * A class for representing a person in a Key Exchange contains methods for generating a prime modulus, generator and secret key.
 * Also contains methods for sending messages and computing the key.
 * @author Joe
 * @version 2
 */
public class Person {

	private static final BigInteger TWO = new BigInteger("2");
	
	//Values needed for each person.
	public BigInteger base;
	public BigInteger modulus;
	public BigInteger secret;
	public BigInteger key;
	
	//Random generator.
	Random r = new Random();
	
	public Person() {
		
	}
	
	/**
	 * Generate a random generator less than between 2 and p-1 in (Zp)*;
	 * @return base : generator for Prime Modulus 
	 */
	public BigInteger generateGenerator() {
		base = randomBigInteger(modulus);
		return base;
	}
	
	/**
	 * Generate a 1024 bit prime number with low uncertainty.
	 * @return
	 */
	public BigInteger generatePrimeModulus() {
		Random r = new Random();
		modulus = new BigInteger(1024, 20, r);
		return modulus;
	}
	
	/**
	 * Generate a private random number (I have decided to keep it lower than the modulus, but in reality it doesn't matter); 
	 * @param modulus
	 */
	public void generatePrivateRandomNumber(BigInteger modulus) {
		secret = randomBigInteger(modulus);
	}
	
	/**
	 * Generate a random BigInteger to be used as a generator.
	 * @param maxNum : prime number p used as the prime modulus.
	 * @return rndNum : for which rndNum is in {2, 3, ... , p-1}.
	 */
	public BigInteger randomBigInteger(BigInteger maxNum) {
	    Random r = new Random();
	    int maxNumBitLength = r.nextInt(maxNum.bitLength() - 1) + 2;
	    BigInteger rndNum;
	    do {
	        rndNum = new BigInteger(maxNumBitLength, r);
	    } while(rndNum.compareTo(maxNum) >= 0 || rndNum.compareTo(TWO) < 0);
	    return rndNum;
	}

	/**
	 * Compute message from A to B, starting the Key Exchange.
	 * @return BigIntger[] : such that [0] is the modulus, [1] is the generator, [2] is the public value generator by A.
	 */
	public BigInteger[] computeMessageAtoB () {
		BigInteger modulus = generatePrimeModulus();
		BigInteger generator = generateGenerator();
		generatePrivateRandomNumber(modulus);
		BigInteger valueA = generator.modPow(secret, modulus);
		return new BigInteger[] {modulus, generator, valueA};
	}
	
	/**
	 * Compute message B to A, B sending their public value based on A's modulus and generator.
	 * @param modulus : modulus from A.
	 * @param base : base generator from A.
	 * @param valueA : value from A (not used here).
	 * @return valueB : B's public value.
	 */
	public BigInteger computeMessageBtoA(BigInteger modulus, BigInteger base, BigInteger valueA) {
		generatePrivateRandomNumber(modulus);
		BigInteger valueB = base.modPow(secret, modulus);
		return valueB;
	}
	
	/**
	 * Compute A's key.
	 * @param otherValue : B's public value.
	 * @return key : A's key, should match B's 
	 */
	public BigInteger computeKeyA(BigInteger otherValue) {
		key = otherValue.modPow(secret, modulus);
		System.out.println(key.toString());
		return key;
	}
	
	/**
	 * Computer B's key.
	 * @param modulus : modulus from A.
	 * @param base : base generator from A.
	 * @param valueA : value from A.
	 * @return key : B's key, should match A's
	 */
	public BigInteger computeKeyB(BigInteger modulus, BigInteger base, BigInteger valueA) {
		key = valueA.modPow(secret, modulus);
		System.out.println(key.toString());
		return key;
	}
	
}
