import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

//public class MotifReducer extends Reducer<Text, WritableMotifData, Text, Text>{
public class MotifReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	
	public List<String> bestMotifs = new LinkedList<String>();
	public List<Integer> bestDistances = new LinkedList<Integer>();
	
	
	@Override
	public void setup(Context context) throws IOException, InterruptedException{
	
	}
	
	
	@Override
	//public void reduce(Text key, Iterable<WritableMotifData> values, Context output)
	public void reduce(Text key, Iterable<IntWritable> values, Context output)
		throws IOException, InterruptedException {
		int totalDistance = 0;
		
		//for(WritableMotifData value: values){
		for(IntWritable value: values){
			//totalDistance+= value.getDistance();
			totalDistance += value.get();
			/*String row = key.toString() + " | " +
						 value.getMatch().toString() + " | " +
						 Integer.toString(value.getIndex()) + " | " +
						 Integer.toString(value.getDistance());
			output.write(key, new Text(row));
			*/
		}
		bestMotifs.add(key.toString());
		bestDistances.add(new Integer(totalDistance));
		}
	@Override
	public void cleanup(Context output) throws IOException, InterruptedException{
		
		
		List<String> tempMotifs = new LinkedList<String>();
		
		Integer bestDistance = bestDistances.get(0);
		tempMotifs.add(bestMotifs.get(0));
		
		for(int i = 1; i < bestMotifs.size(); i++){
			int compare = bestDistance.compareTo(bestDistances.get(i));
			
			if(compare == 0){
				tempMotifs.add(bestMotifs.get(i));
			}
			else if(compare > 0){
				/*for(int j = 0; j < tempMotifs.size(); j++){
					output.write(new Text(tempMotifs.get(j)), new IntWritable(bestDistance.intValue()));
				}*/
				bestDistance = bestDistances.get(i);
				tempMotifs.clear();
				tempMotifs.add(bestMotifs.get(i));
				
			}
		}
		for(int i = 0; i < tempMotifs.size(); i++){
			output.write(new Text(tempMotifs.get(i)), new IntWritable(bestDistance.intValue()));
		}
		
		
	}
}
