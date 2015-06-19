import java.util.LinkedList;
import java.util.List;

public class DNAGenerator {

	public char[] holder;
	public char[] available = {'a','c','g','t'}; //All available characters for sequence
	public List<String> allDna = new LinkedList<String>();
	
	// generates every combination of available characters for string of length SubLen
	//Stores each string in allDna List
	public void generate(int SubLen){
		int carry; //Indicates
		int[] indices = new int[SubLen]; //array of matching index to available chars
		holder = new char[SubLen]; //temp char array for motif
		
		do{
			int stringIndex = 0; //initialize index in holder
			for(int index: indices){ //for each index in indices
				holder[stringIndex] = available[index]; //current holder index of motif equals library at index
				stringIndex++; //increment index of holder
			}
			allDna.add(new String(holder)); //add the new motif
			
			carry = 1; //set as needs to increment
			
			//loops through each char position of motif
			//Increase by one each time then break
			//UNLESS: hit 't' then increase next significant index and reset current indexes to 'a'
			for(int i = indices.length -1; i >=0;i--){ //for each index going backwards
				
				//If index -1 does not need to be incremented break out of for loop
				if(carry == 0){
					break;
				}
				
				//else increase Index by one
				indices[i] += carry;
				carry = 0; //set as has been increased
				
				//If you were on 't' increment index -1 and reset current index
				if(indices[i] == available.length){
					carry = 1; //set to needs increase
					indices[i] = 0; //set current index back to 'a'
				}
			}
			
			
			
		}
		while(carry != 1); // continue until index 0 is 't'
	}
		

}
