import java.io.File;
import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class tool {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Twitter_tool tool = new Twitter_tool();
		
			
				try {
					tool.getTweets("twitter_users.json");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("error in getTweets");
					e.printStackTrace();
				}
			
		
	}

}
