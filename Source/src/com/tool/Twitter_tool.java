import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.kyriakos.json.JSONException;
import com.kyriakos.json.JSONObject;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter_tool {
	final TwitterFactory tf;
	final Twitter twitter;
	final ConfigurationBuilder cb;

	public Twitter_tool() {
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setJSONStoreEnabled(true).setOAuthConsumerKey("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
				.setOAuthConsumerSecret("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
				.setOAuthAccessToken("xxxxxxxxxxxxxxxxxx-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
				.setOAuthAccessTokenSecret("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		tf = new TwitterFactory(cb.build());

		twitter = tf.getInstance();
	}

	public void getTweets(String filename) throws IOException, InterruptedException {
		String line = "";
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		BufferedWriter writer = new BufferedWriter(new FileWriter("Tweets2.json", true));
		int count = 1;
		int forwad = 106757;
		while ((line = reader.readLine()) != null) {
			System.out.println(count);
			if (count <= forwad) {
				count++;
				continue;
			}
			
			String split[] = line.split(",");
			List<Status> status = getUsersTweets(split[1]);

			if (status != null) {
				for (Status st : status) {
					writer.write(TwitterObjectFactory.getRawJSON(st) + "\n");
					writer.flush();
				}
			}

			count++;
		}
		reader.close();
		writer.close();
	}

	
	private List<Status> getUsersTweets(String screen_name) throws IOException {
		
		List<Status> status = null;
		try {
			status = twitter.getUserTimeline(screen_name);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			

			if (e != null && e.getRateLimitStatus().getRemaining() == 0) {
				try {
					System.out.println("Sleeping for 15'");
					Thread.sleep(900000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}

		return status;

	}

	public void findUsersIds(String filename) throws IOException {
		String line = "";
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		Set<String> users = new HashSet<String>();
		int count = 1;
		BufferedWriter writer = new BufferedWriter(new FileWriter("twitter_users.json"));
		while ((line = reader.readLine()) != null) {
			JSONObject root = null;
			try {
				root = new JSONObject(line);
				} catch (JSONException e) {

				continue;
			}
			users.add(root.getJSONObject("user").getString("id_str") + ","
					+ root.getJSONObject("user").getString("screen_name"));
			if (count % 5000 == 0) {
				System.out.println(count);

			}
			count++;
		}
		reader.close();
		for (String str : users) {
			writer.write(str + "\n");
		}
		writer.close();
	}

	public void getRandomTweet(String filename) throws IOException {
		Random rand = new Random();
		int stop = (rand.nextInt(100) + 1);
		int count = 1;
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = "";
		while ((line = reader.readLine()) != null) {
			if (count == stop) {
				System.out.println(new JSONObject(line));
				reader.close();
				break;
			}
			count++;
		}
	}

	public void getCreatedTweets(final File folder) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("data_twitter.json"));
			for (final File fileEntry : folder.listFiles()) {
			System.out.println(fileEntry.toString());
			BufferedReader reader = new BufferedReader(new FileReader(fileEntry.toString()));
			String line = "";
			while ((line = reader.readLine()) != null) {

				if (line.contains("delete")) {
					continue;
				} else {
					writer.write(line);
					writer.newLine();
					writer.flush();

				}
			}
			reader.close();
		}
		writer.close();
	}

}
