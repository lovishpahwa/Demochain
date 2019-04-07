package demochain;

import java.util.Date;

public class Block {

	public String hash;
	public String previousHash;
	private String data;
	private long timestamp;
	private int nonce;
	

	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timestamp = new Date().getTime();
		this.hash = calculateHash();
	}

	public String calculateHash() {
		String hash = StringUtils.applySha256(previousHash + Long.toString(timestamp) + Integer.toString(nonce) + data);
		return hash;
	}
	
	//proof of work minning
	public void mineBlock(int difficulty){
		String target = new String(new char[difficulty]).replace('\0', '0');// if difiiculty is 3 then string will be "000".
		while(!hash.substring(0, difficulty).equals(target)){
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined, here is the hash = " + hash);
	}
}
