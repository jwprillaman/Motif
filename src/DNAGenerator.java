import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DNAGenerator {

	public char[] holder;
	public char[] available = {'a','c','g','t'};
	public List<String> allDna = new LinkedList<String>();
	
	public void generate(int SubLen){
		int carry;
		int[] indices = new int[SubLen];
		holder = new char[SubLen];
		
		do{
			int stringIndex = 0;
			//Add current combo to list
			for(int index: indices){
				holder[stringIndex] = available[index];
				
				stringIndex++;
			}
			allDna.add(new String(holder));
			
			carry = 1;
			for(int i = indices.length -1; i >=0;i--){
				
				//If index -1 does not need to be incremented break out of for loop
				if(carry == 0){
					break;
				}
				
				//increase Index by one
				indices[i] += carry;
				carry = 0;
				
				//If you were on 't' increment index -1 and reset current index
				if(indices[i] == available.length){
					carry = 1;
					indices[i] = 0;
				}
			}
			
			
			
		}
		while(carry != 1); // continue until index 0 is 't'
	}
		

}
