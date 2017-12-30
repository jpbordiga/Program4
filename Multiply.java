import java.util.ArrayList;

public class Multiply implements MultiplyInterface {

	public Multiply() {
		//
	}

	@Override
	public ArrayList<Byte> multiply(ArrayList<Byte> U, ArrayList<Byte> V, int n) { // multiplies the values using the brute force approach	
		
		ArrayList<Byte> W = new ArrayList<>(2 * n);
		
		for(int k = 0; k < (2 * n); k++) {
			W.add((byte) 0);
		}
		
		for(int j = 0; j < n; j++) {
			
			int c = 0;
			
			int i;
			for(i = 0; i < n; i++) {
				
				int t = ((U.get(i) & 0xFF) * (V.get(j) & 0xFF) + (W.get(i + j) & 0xFF) + c);
				W.set((i + j), (byte) (t % 256));
				c = t / 256;
				
			}
			
			int k = i + j;
			
			while((k <= (2 * n)) && (c != 0)) {
				
				int t = ((W.get(k) & 0xFF) + c);
				W.set(k, (byte) (t % 256));
				c = t / 256;
				k++;
				
			}
			
		}
		
		return W;
		
	}

}
