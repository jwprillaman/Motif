import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MotifReducer extends Reducer<Text, WritableMotifData, Text, Text>{

	@Override
	public void reduce(Text key, Iterable<WritableMotifData> values, Context output)
		throws IOException, InterruptedException {
		int totalDistance = 0;
		
		for(WritableMotifData value: values){
			totalDistance+= value.getDistance();
			String row = key.toString() + " | " +
						 value.getMatch().toString() + " | " +
						 Integer.toString(value.getIndex()) + " | " +
						 Integer.toString(value.getDistance());
			output.write(key, new Text(row));
		}
		
		String total = Integer.toString(totalDistance);
		output.write(key, new Text(total));
		
	}
}
