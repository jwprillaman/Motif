import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MotifReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	
	public List<String> bestMotifs = new LinkedList<String>(); //Each Motif
	public List<Integer> bestDistances = new LinkedList<Integer>(); //Best corresponding distance 
	
	
	@Override
	public void setup(Context context) throws IOException, InterruptedException{
	
	}
	
	
	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context output)
		throws IOException, InterruptedException {
		int totalDistance = 0;
		//For every value for Motif(Key)
		for(IntWritable value: values){

			totalDistance += value.get(); //compute total distance

		}
		bestMotifs.add(key.toString()); //Add to motif
		bestDistances.add(new Integer(totalDistance)); // add corresponding distance
		}
	@Override
	public void cleanup(Context output) throws IOException, InterruptedException{
		
		List<String> tempMotifs = new LinkedList<String>(); // holds the current motifs with lowest distance
		
		Integer bestDistance = bestDistances.get(0); //Start with first motif as best
		tempMotifs.add(bestMotifs.get(0));
		
		//for each motif
		for(int i = 1; i < bestMotifs.size(); i++){
			int compare = bestDistance.compareTo(bestDistances.get(i)); //compare
			
			if(compare == 0){ // if total distances equal
				tempMotifs.add(bestMotifs.get(i));
			}
			else if(compare > 0){ //If current motif is less than best motif

				bestDistance = bestDistances.get(i); //set as new best
				tempMotifs.clear(); //clear array of old best
				tempMotifs.add(bestMotifs.get(i)); // add new best motif
				
			}
		}
		for(int i = 0; i < tempMotifs.size(); i++){
			//write best motif to output
			output.write(new Text(tempMotifs.get(i)), new IntWritable(bestDistance.intValue()));
		}
		
		
	}
}
