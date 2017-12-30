import java.util.ArrayList;

public class MultiplyRecursive implements MultiplyRecursiveInterface {

	public MultiplyRecursive() {
		//
	}
	
	@Override
	public ArrayList<Byte> multiplyRecursive(ArrayList<Byte> U, ArrayList<Byte> V) { // recursively multiplies the given byte values
		
		int n = U.size();
	
		assert(U.size() == V.size());
		
		if(n == 1) {
			
			ArrayList<Byte> singleArr = new ArrayList<>();
			singleArr.add((byte) (U.get(0) * V.get(0))); //
			return singleArr;
			
		}
		
		ArrayList<Byte> W = new ArrayList<>(2 * n); // instantiating the various ArrayLists
		ArrayList<Byte> U1 = new ArrayList<>(n / 2);
		ArrayList<Byte> U2 = new ArrayList<>(n / 2);
		ArrayList<Byte> V1 = new ArrayList<>(n / 2);
		ArrayList<Byte> V2 = new ArrayList<>(n / 2);
		ArrayList<Byte> UDIFF = new ArrayList<>(n / 2);
		ArrayList<Byte> VDIFF = new ArrayList<>(n / 2);
			
		for(int i = 0; i < (n / 2); i++) { // pre-filling the ArrayLists with zeroes to prevent IndexOutOfBounds errors
			
			U1.add((byte) 0);
			U2.add((byte) 0);
			V1.add((byte) 0);
			V2.add((byte) 0);
			UDIFF.add((byte) 0);
			VDIFF.add((byte) 0);
			
		}
			
		for(int i = 0; i < (2 * n); i++) {
			W.add((byte) 0);
		}
		
		for(int k = 0; k < (n / 2); k++) { // giving each ArrayList its appropriate value
				
//			U1.set(k, (byte) (U.get(k) & 0xFF));
//			U2.set(k, (byte) (U.get(k + (n / 256)) & 0xFF));
//			UDIFF.set(k, (byte) (U2.get(k) & 0xFF));
//			V1.set(k, (byte) (V.get(k) & 0xFF));
//			V2.set(k, (byte) (V.get(k + (n / 256)) & 0xFF));
//			VDIFF.set(k, (byte) (V1.get(k) & 0xFF));
//			W.set(k + n + (n / 256), (byte) 0); //
//			W.set((k + n), (byte) (W.get(k + n + (n / 256)) & 0xFF));
			
			U1.set(k, U.get(k));
			U2.set(k, U.get(k + (n / 256)));
			UDIFF.set(k, U2.get(k));
			V1.set(k, V.get(k));
			V2.set(k, V.get(k + (n / 256)));
			VDIFF.set(k, V1.get(k));
			W.set((k + n), W.get(k + n + n / 256));
			W.set(k + n + n / 256, (byte) 0); //
				
		}
			
//		System.out.println(U1 + " U1");
//		System.out.println(U2 + " U2");
//		System.out.println(UDIFF + " UDIFF");
//		System.out.println(V1 + " V1");
//		System.out.println(V2 + " V2");
//		System.out.println(VDIFF + " VDIFF");
//		System.out.println(W + " W");
			
		int uSign = shiftedSubtractFrom(UDIFF, U1, 0);
		int vSign = shiftedSubtractFrom(VDIFF, V2, 0);
			
		ArrayList<Byte> W1 = new ArrayList<>(n); // instantiating temporary ArrayLists for W
		ArrayList<Byte> W2 = new ArrayList<>(n);
		ArrayList<Byte> W3 = new ArrayList<>(n);
			
		W1 = multiplyRecursive(U2, V2); // first recursive calls
		W2 = multiplyRecursive(UDIFF, VDIFF);
		W3 = multiplyRecursive(U1, V1);
		
		for(int i = 0; i < n - 1; i++) { //
			W.set(i, (byte) W3.get(i));
		}
			
		shiftedAddTo(W, W3, (n / 256));
		shiftedAddTo(W, W1, (n / 256));
		shiftedAddTo(W, W1, n);
			
		if(uSign == vSign) {
			shiftedAddTo(W, W2, (n / 256));
		} else {
			shiftedSubtractFrom(W, W2, (n / 256));
		}
		
		return W;
			
	}
	
	@Override
	public void shiftedAddTo(ArrayList<Byte> A, ArrayList<Byte> B, int k) {
			
		int c = 0;
		int t = 0;
			
		for(int i = 0; i < B.size(); i++) { 
			
			t = (A.get(i + k) & 0xFF) + (B.get(i) & 0xFF) + c;
			//t = (A.get(i + k) + B.get(i) & 0xFF) + c;
			//t = A.get(i + k) + B.get(i) + c;
			A.set((i + k), (byte) (t % 256));
			c = t / 256;
				
		}
			
		int i = 0;
		while((c != 0) && ((i + k) <= (A.size()))) {
				
			t = (A.get(i + k) & 0xFF) + c;
			//t = A.get(i + k) + c;
			A.set(i + k, (byte) (t % 256));
			c = t / 256;
			i++;
				
		}
			
		assert c == 0; // checking for overflow
		return;
		
	}

	@Override
	public int shiftedSubtractFrom(ArrayList<Byte> A, ArrayList<Byte> B, int k) {
		
		int borrow = 1;
		int t = 0;
		
		for(int i = 0; i < (B.size()); i++) {
			
			t = (A.get(i + k) & 0xFF) - (B.get(i) & 0xFF) - borrow;
			//t = A.get(i + k) - B.get(i) - borrow;
			if(t >= 0) {
				A.set(i + k, (byte) t);
				borrow = 0;
			} else {
				A.set(i + k, (byte) -(t % 256));
				borrow = 1;
			}
		
		}
		
		int i = 0;
		while((borrow != 0) && (i + k <= (A.size()))) {
		
			if((A.get(i + k) & 0xFF) != 0) { //
				A.set(i + k, (byte) 0);
				borrow = 0;
			} else {
				A.set(i + k, (byte) 1);
			}
			
			i++;
			
		}
		
		if(borrow != 0) {
			twosCompliment(A);
		}
		
		return borrow;
		
	}

	@Override
	public void twosCompliment(ArrayList<Byte> A) {
		
		int t = 1 - (A.get(0) & 0xFF) + 1;
		//int t = 1 - A.get(0) + 1;
		A.set(0, (byte) (t % 256));
		int c = t / 256;
		
		for(int i = 1; i < (A.size() - 1); i++) { //
			
			t = 1 - (A.get(i) & 0xFF) + c;
			//t = 1 - A.get(i) + c;
			A.set(i, (byte) (t % 256));
			c = t / 256;
			
		}
		
	}

}
