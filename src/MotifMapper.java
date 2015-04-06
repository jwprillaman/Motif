import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MotifMapper extends Mapper<Object, Text, Text, IntWritable>{

	//private final static WritableMotifData data = new WritableMotifData(); 
	private final static IntWritable one = new IntWritable();
	
	@Override
	public void map(Object key, Text value, Context output) throws IOException, InterruptedException{
		
		int INPUT_NUM = 8;
		
		DNAGenerator gen = new DNAGenerator();
		gen.generate(INPUT_NUM);
		
		//for every possible sequence
		for(String motif: gen.allDna){
			//int totalDistance = 0;
			//int bestIndex = 0;
			String bestMatch = value.toString().substring(0, INPUT_NUM);
			
			int distance = 0;
			int bestDistance = INPUT_NUM + 1;
			int currIndex = 0;
			
			//for every possible start index
			for(int startIndex = 0; startIndex < value.toString().length() - INPUT_NUM; startIndex++){
				currIndex = 0;
				distance = 0;
				for(char single: motif.toCharArray()){
					if(single != value.charAt(startIndex + currIndex++)){
						distance++;
					}

				}
				//Add total Distance
				//Check: is this motif distance less?
				if(distance < bestDistance){
					bestDistance = distance;
					//bestIndex = startIndex;
					//bestMatch = value.toString().substring(startIndex, startIndex + INPUT_NUM);
				}
			}
			//data.set(new IntWritable(totalDistance), new IntWritable(bestIndex), new Text(bestMatch));
			//output.write(new Text(subDna), data);
			one.set(bestDistance);
			output.write(new Text(motif), one);
		}
		
		//String subString = value.toString().substring(0, 8);
		
		
		//output.write(new Text(subString), one);
	}
}
