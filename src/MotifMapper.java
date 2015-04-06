import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MotifMapper extends Mapper<Object, Text, Text, IntWritable>{

	private final static IntWritable one = new IntWritable();
	
	@Override
	public void map(Object key, Text value, Context output) throws IOException, InterruptedException{
		
		int INPUT_NUM = 8;
		
		DNAGenerator gen = new DNAGenerator();
		gen.generate(INPUT_NUM); //generate every possible sequence of length INPUT_NUM
		
		//for every possible sequence
		for(String motif: gen.allDna){
			
			//start with best match as first 8 characters
			String bestMatch = value.toString().substring(0, INPUT_NUM);
			
			int distance = 0; //current distance
			int bestDistance = INPUT_NUM + 1; //start at INPUT+1 since that will be greater than possible max length
			int currIndex = 0; //current from starting char
			
			//for every possible start index
			for(int startIndex = 0; startIndex < value.toString().length() - INPUT_NUM; startIndex++){
				currIndex = 0;
				distance = 0;
				for(char single: motif.toCharArray()){ //chr every character in choosen motif
					if(single != value.charAt(startIndex + currIndex++)){ //see if current characters equal and increment current index after
						distance++; // if they do not match increment distance
					}

				}
				//Check: is this motif distance less than current best?
				if(distance < bestDistance){
					bestDistance = distance; //if current subString has lower distance set as new best
				}
			}
			
			one.set(bestDistance); //set best distance to intWritable
			output.write(new Text(motif), one); //write key matched with distance
		}
	}
}
