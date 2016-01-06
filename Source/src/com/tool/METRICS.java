import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class METRICS {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("Tweets.json"));
		String line = "";
		int count = 1;
		while((line =reader.readLine())!=null){
			System.out.println(line);
			System.exit(0);
			count++;
		}
		reader.close();
		System.out.println("Number of tweets " + count);
		
	}
}
