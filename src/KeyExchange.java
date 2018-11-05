import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class KeyExchange {
		
	public static void main(String[] args) {
		
		//Alice and Bob.
		Person alice = new Person();
		Person bob = new Person();
		
		//Key exchange as defined in the specification.
		BigInteger[] msg1 = alice.computeMessageAtoB();
		BigInteger msg2 = bob.computeMessageBtoA(msg1[0], msg1[1], msg1[2]);
		BigInteger keyA = alice.computeKeyA(msg2);
		BigInteger keyB = bob.computeKeyB(msg1[0], msg1[1], msg1[2]);
		
		//Transcript to file of above key exchange.
		makeTranscript("transcript.txt", alice.secret, bob.secret, msg1[0], msg1[1], msg1[2], msg2, keyA, keyB);
		
		//Alice, Bob and man in the middle Eve.
		alice = new Person();
		bob = new Person();
		Person eve = new Person();
		
		//Eve intercepts Alice's message and sends back her own values and gets Alice's key
		//By pretending to be Bob.
		BigInteger[] interceptedMsg = alice.computeMessageAtoB();
		BigInteger evePretendingToBeBoB = eve.computeMessageBtoA(interceptedMsg[0], interceptedMsg[1], interceptedMsg[2]);
		BigInteger aKey = alice.computeKeyA(evePretendingToBeBoB);
		BigInteger eKey1 = eve.computeKeyB(interceptedMsg[0], interceptedMsg[1], interceptedMsg[2]);
		
		//Eve substitutes her own msg1 for Alice's and gets Bobs key by pretending to be Alice.
		BigInteger[] evePretendingToBeAlice = eve.computeMessageAtoB();
		BigInteger bobMsg = bob.computeMessageBtoA(evePretendingToBeAlice[0], evePretendingToBeAlice[1], evePretendingToBeAlice[2]);
		BigInteger eKey2 = eve.computeKeyA(bobMsg);
		BigInteger bKey = bob.computeKeyB(evePretendingToBeAlice[0], evePretendingToBeAlice[1], evePretendingToBeAlice[2]);
		
		//Transcript of Eve's man in the middle attacj on Alice and Bob.
		makeAttackTranscript("attackTranscript.txt", alice.secret, bob.secret, eve.secret, interceptedMsg[0], interceptedMsg[1], interceptedMsg[2], 
				evePretendingToBeBoB, evePretendingToBeAlice[0], evePretendingToBeAlice[1], evePretendingToBeAlice[2], bobMsg, aKey, bKey, eKey1, eKey2);
		
	}
	
	/**
	 * Write Transcript of key exchange between 2 people.
	 * @param path
	 * @param secretA
	 * @param secretB
	 * @param msg1Modulus
	 * @param msg1Base
	 * @param msg1ValueA
	 * @param msg2ValueB
	 * @param keyA
	 * @param keyB
	 */
	public static void makeTranscript(String path, BigInteger secretA, BigInteger secretB, BigInteger msg1Modulus, BigInteger msg1Base, 
			BigInteger msg1ValueA, BigInteger msg2ValueB, BigInteger keyA, BigInteger keyB) {

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			out.write("secretA = " + secretA);
			out.newLine();
			out.write("secretB = " + secretB);
			out.newLine();
			out.write("msg1.modulus = " + msg1Modulus);
			out.newLine();
			out.write("msg1.base = " + msg1Base);
			out.newLine();
			out.write("msg1.valueA = " + msg1ValueA);
			out.newLine();
			out.write("msg2.valueB = " + msg2ValueB);
			out.newLine();
			out.write("keyA = " + keyA);
			out.newLine();
			out.write("keyB = " + keyB);
			out.close();
		} catch (IOException e) {
		
		}


	}
	
	/**
	 * Write transcript of key exchange between two people, with a third person performing a man in the middle attack.
	 * @param path
	 * @param secretA
	 * @param secretB
	 * @param secretE
	 * @param interceptedMsgModulus
	 * @param interceptedMsgBase
	 * @param interceptedMsgValueA
	 * @param evePretendingToBeBobValueB
	 * @param evePretendingToBeAliceMsgModulus
	 * @param evePretendingToBeAliceMsgBase
	 * @param evePretendingToBeAliceMsgValueA
	 * @param bobMsgValueB
	 * @param keyA
	 * @param keyB
	 * @param keyEA
	 * @param keyEB
	 */
	public static void makeAttackTranscript(String path, BigInteger secretA, BigInteger secretB, BigInteger secretE, BigInteger interceptedMsgModulus, BigInteger interceptedMsgBase, 
			BigInteger interceptedMsgValueA, BigInteger evePretendingToBeBobValueB, BigInteger evePretendingToBeAliceMsgModulus, BigInteger evePretendingToBeAliceMsgBase, 
			BigInteger evePretendingToBeAliceMsgValueA, BigInteger bobMsgValueB, BigInteger keyA, BigInteger keyB, BigInteger keyEA, BigInteger keyEB) {

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			out.write("secretA = " + secretA);
			out.newLine();
			out.write("secretB = " + secretB);
			out.newLine();
			out.write("secretE = " + secretE);
			out.newLine();
			
			out.write("interceptedMsg.modulus = " + interceptedMsgModulus);
			out.newLine();
			out.write("interceptedMsg.base = " + interceptedMsgBase);
			out.newLine();
			out.write("interceptedMsg.valueA = " + interceptedMsgValueA);
			out.newLine();
			out.write("evePretendingToBeBoB.valueB = " + evePretendingToBeBobValueB);
			out.newLine();
			
			out.write("evePretendingToBeAlice.modulus = " + evePretendingToBeAliceMsgModulus);
			out.newLine();
			out.write("evePretendingToBeAlice.base = " + evePretendingToBeAliceMsgBase);
			out.newLine();
			out.write("evePretendingToBeAlice.valueA = " + evePretendingToBeAliceMsgValueA);
			out.newLine();
			out.write("evePretendingToBeAlice.valueB = " + bobMsgValueB);
			out.newLine();
			
			out.write("keyA = " + keyA);
			out.newLine();
			out.write("keyB = " + keyB);
			out.newLine();
			out.write("keyEA = " + keyEA);
			out.newLine();
			out.write("keyEB = " + keyEB);
			out.close();
		} catch (IOException e) {
		
		}


}

}
