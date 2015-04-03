import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;


public class WritableMotifData implements WritableComparable<WritableMotifData>{
	private IntWritable distance, index;
	private Text match;
	
	public WritableMotifData(){
		this.distance = new IntWritable();
		this.index = new IntWritable();
		this.match = new Text();
	}
	public WritableMotifData(IntWritable Distance, IntWritable Index, Text Match){
		this.distance = Distance;
		this.index = Index;
		this.match = Match;
	}
	public void set(IntWritable Distance, IntWritable Index, Text Match){
		this.distance = Distance;
		this.index = Index;
		this.match = Match;
	}
	public int getDistance(){
		return distance.get();
	}
	public int getIndex(){
		return index.get();
	}
	public String getMatch(){
		return match.toString();
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		distance.readFields(in);
		index.readFields(in);
		match.readFields(in);
	}
	@Override
	public void write(DataOutput out) throws IOException {
		distance.write(out);
		index.write(out);
		match.write(out);
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof WritableMotifData){
			WritableMotifData other = (WritableMotifData) o;
			return (distance.equals(other.distance) && index.equals(other.index) && match.equals(other.match));
		}
		return false;
		
	}
	@Override
	public int hashCode(){
		return match.hashCode();
	}
	@Override
	public int compareTo(WritableMotifData o) {
		if(distance.compareTo(o.distance)==0){
			if(index.compareTo(o.index)==0){
				return (match.compareTo(o.match));
			}
			else{
				return (index.compareTo(o.index));
			}
		}
		else{
			return distance.compareTo(o.distance);
		}
	}
	
	
}
