/**
 * https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa
 */
package demochain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class DemoChain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 3;
	public static Wallet walletA;
	public static Wallet walletB;
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions. 

	public static boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);

			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("current hash is not equal");
				return false;
			}

			if (!currentBlock.previousHash.equals(previousBlock.hash)) {
				System.out.println("previous hash is not equal");
				return false;
			}

			if (!currentBlock.hash.substring(0, difficulty).endsWith(hashTarget)) {
				System.out.println("This block is not minned !!!!");
				return false;
			}
		}
		return true;
	}

	public static void main(String arg[]) {

		/*
		 * blockchain.add(new Block("Hi, I am genesis", "0"));
		 * System.out.println("minning genesis block");
		 * blockchain.get(0).mineBlock(difficulty);
		 * 
		 * blockchain.add(new Block("Hi, I am Block two",
		 * blockchain.get(blockchain.size() - 1).hash));
		 * System.out.println("minning block two");
		 * blockchain.get(1).mineBlock(difficulty);
		 * 
		 * blockchain.add(new Block("Hi, I am Block three",
		 * blockchain.get(blockchain.size() - 1).hash));
		 * System.out.println("minning block three");
		 * blockchain.get(2).mineBlock(difficulty);
		 * 
		 * System.out.println("is chain valid : " + isChainValid());
		 * 
		 * String blockchainJson = new
		 * GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		 * System.out.println("\nHere's the chain : ");
		 * System.out.println(blockchainJson);
		 */

		// Setup Bouncey castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		// Create the new wallets
		walletA = new Wallet();
		walletB = new Wallet();
		// Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(StringUtils.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtils.getStringFromKey(walletA.publicKey));
		// Create a test transaction from WalletA to walletB
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		// Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifiySignature());

	}
}
