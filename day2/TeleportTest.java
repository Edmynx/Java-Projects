
public class TeleportTest {

	public static void main(String[] args) {
		//define teleporter as Teleporter
		Teleporter t1 = new Teleporter(1.0,1.0,5,5);
		t1.teleport(); //calls teleport method, this is ok
		t1.step(); //uses Blob step method (Teleporter doesn't implement step)
		
		//define teleporter as Blob
		Blob t2 = new Teleporter(5.0, 5.0, 5, 5);
		((Teleporter)t2).teleport(); //need to cast to Teleporter because declared as Blob
		t2.step(); //no need to cast -- uses Blob's step method

		Blob t3 = new Blob(); //normal Blob
		t3.step(); //this is ok
		//t3.teleport(); //not allowed, Blob has no teleport method
	}

}
