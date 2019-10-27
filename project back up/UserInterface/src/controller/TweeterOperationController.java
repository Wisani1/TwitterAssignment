package controller;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import service.twitterEJB;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import model.TweeterOperation;

@ManagedBean(name="tweeteroperationcontroller")
@SessionScoped

public class TweeterOperationController {
	@EJB
	twitterEJB twitterSrvice;
	
	
	static String connsumerKey = "q7m4w07MGsL80ejdqeze69o6Q";
	static String consumerSecre = "VYvAzfQsXIosXQlJ28i235fBLp1cf5rcX7xuJXQ4kWAanq3Vz1";
	static String accessTokenst = "1187211232740331520-LKp1xwArsaKaaPGwXtD3vvLIdsEpMC";
    static String accessTokenSecret = "sMlyVfJ1I4Eu7iy86Mw3FiCVbZtWJGjzMUrxStfhIbvsw";


    @ManagedProperty(value = "#{tweeterOperation}")
	private TweeterOperation tweeterOperation;
	
	public TweeterOperation getTweeterOperation() {
		return tweeterOperation;
	}
	public void setTweeterOperation(TweeterOperation tweeterOperation) {
		this.tweeterOperation = tweeterOperation;
	}

//	public  void updateStatusonTweeter()
		public void updateStatusonTweeter() {
			
			Twitter twitter = TwitterFactory.getSingleton();
		    String latestStatus  =  tweeterOperation.getTextArea();
		    try {
		// save tweet on db
		    	twitterSrvice.addTwitterMessage(tweeterOperation.getEntity());
		    	
		    	
		    	Status status = twitter.updateStatus(latestStatus);
			   // System.out.println("Successfully updated the status to [" + status.getText() + "].");
			
				OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(connsumerKey,
						consumerSecre);
			    oAuthConsumer.setTokenWithSecret(accessTokenst, accessTokenSecret);

				HttpPost httpPost = new HttpPost("http://api.twitter.com/1.1/statuses/update.json?status= 211232072@ADH Project");

				oAuthConsumer.sign(httpPost);

				@SuppressWarnings("resource")
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResponse = httpClient.execute(httpPost);

				int statusCode = httpResponse.getStatusLine().getStatusCode();
				System.out.println(statusCode + ':'
					+ httpResponse.getStatusLine().getReasonPhrase());
				System.out.println(IOUtils.toString(httpResponse.getEntity().getContent())); 
		    
		    }catch(Exception e) {
		    	System.out.println(e.getMessage());
		    }
		    
		}	
		

		//System.out.println("testing if method is working");
	//ConfigurationBuilder cd = new ConfigurationBuilder();
		
		//cd.setDebugEnabled(true);
		//setOAuthConnsumerKey ("q7m4w07MGsL80ejdqeze69o6Q")
		//.setOAuthConsumerSecret("VYvAzfQsXIosXQlJ28i235fBLp1cf5rcX7xuJXQ4kWAanq3Vz1")		
		//.setOAuthAccessToken("1187211232740331520-LKp1xwArsaKaaPGwXtD3vvLIdsEpMC") 
	//	.setOAuthAccessTokenSecret("sMlyVfJ1I4Eu7iy86Mw3FiCVbZtWJGjzMUrxStfhIbvsw")
		
		//TweeterFactory  tf= new TweeterFactory(cd.build());
		//Tweeter4j.tweeter tw = tf.getinstance();
		
		//tw.updateStatus("");
		
 public void  viewTwitter() {// this mothed  will  retrieve all tweets of  the  user 
	 
	 ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setOAuthConsumerKey(connsumerKey);
	    cb.setOAuthConsumerSecret(consumerSecre);
	    cb.setOAuthAccessToken(accessTokenst);
	    cb.setOAuthAccessTokenSecret(accessTokenSecret);

	    Twitter twitter = new TwitterFactory(cb.build()).getInstance();

	    int pageno = 1;
	    String user = "cnn";
	    List statuses = new ArrayList();

	    while (true) {

	      try {

	        int size = statuses.size(); 
	        Paging page = new Paging(pageno++, 100);
	        statuses.addAll(twitter.getUserTimeline(user, page));
	        if (statuses.size() == size)
	          break;
	      }
	      catch(TwitterException e) {

	        e.printStackTrace();
	      }
	    }

	    System.out.println("Total: "+statuses.size());
	 
	 
 }


}

