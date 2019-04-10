package demochain;

import java.util.ArrayList;
import java.util.Date;

public class Block {

	public String hash;
	public String previousHash;
	private String data;
	private long timestamp;
	private int nonce;
	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
	

	public Block(String previousHash) {
		//this.data = data;
		this.previousHash = previousHash;
		this.timestamp = new Date().getTime();
		this.hash = calculateHash();
	}

	public String calculateHash() {
		String hash = StringUtils.applySha256(previousHash + Long.toString(timestamp) + Integer.toString(nonce) + merkleRoot);
		return hash;
	}
	
	//proof of work minning
	public void mineBlock(int difficulty){
		String target = new String(new char[difficulty]).replace('\0', '0');// if difficulty is 3 then string will be "000"
		while(!hash.substring(0, difficulty).equals(target)){
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined, here is the hash = " + hash);
	}
	
	// Add transactions to this block
	public boolean addTransaction(Transaction transaction) {
		// process transaction and check if valid, unless block is genesis block then ignore.
		if (transaction == null)
			return false;
		if ((previousHash != "0")) {
			if ((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}
}
