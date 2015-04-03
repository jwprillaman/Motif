import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MotifMapper extends Mapper<Object, Text, Text, WritableMotifData>{

	private final static WritableMotifData data = new WritableMotifData(); 
	
	@Override
	public void map(Object key, Text value, Context output) throws IOException, InterruptedException{
		
		int INPUT_NUM = 8;
		
		DNAGenerator gen = new DNAGenerator();
		gen.generate(INPUT_NUM);
		
		//for every possible sequence
		for(String subDna: gen.allDna){
			int totalDistance = 0;
			int bestIndex = 0;
			String bestMatch = value.toString().substring(0, INPUT_NUM);
			
			int distance = 0;
			int bestDistance = INPUT_NUM + 1;
			int currIndex = 0;
			
			//for every possible start index
			for(int startIndex = 0; startIndex < value.toString().length() - INPUT_NUM; startIndex++){
				startIndex = 0;
				currIndex = 0;
				distance = 0;
				for(char single: subDna.toCharArray()){
					int singleIndex = 0;
					if(single != value.charAt(startIndex + singleIndex++)){
						distance++;
					}

				}
				//Add total Distance
				totalDistance += distance;
				//Check: is this motif distance less?
				if(distance < bestDistance){
					bestDistance = distance;
					bestIndex = startIndex;
					bestMatch = value.toString().substring(startIndex, startIndex + INPUT_NUM);
				}
			}
			data.set(new IntWritable(totalDistance), new IntWritable(bestIndex), new Text(bestMatch));
			output.write(new Text(subDna), data);
		}
		
		//String subString = value.toString().substring(0, 8);
		
		
		//output.write(new Text(subString), one);
	}
}
