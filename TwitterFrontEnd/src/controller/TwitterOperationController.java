package controller;

import java.util.ArrayList;
import java.util.List;

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

@ManagedBean(name="twitteroperationcontroller")
@SessionScoped

public class TwitterOperationController {
	@EJB
	twitterEJB twitterSrvice;
	
	
	static String connsumerKey = "SPZEcvDPggvGHGMe3kB7x34lJ";
	static String consumerSecre = "l9OQp9pXzhlPKViZHsIRejBI0fSxponv3rqpKbeIrRdDls0003";
	static String accessTokenst = "1188034450082422789-xt3F7aMv0jLk8WOr4cShHnDPKYX1ek";
    static String accessTokenSecret = "3dXlph2hjoh1KAKtJve9zbNqAUCzQZavzkvVZojwp62ER";


    @ManagedProperty(value = "#{twitterOperation}")
	private TwitterOperation twitterOperation;
	
	public TweeterOperation getTweeterOperation() {
		return twitterOperation;
	}
	public void setTwitterOperation(TweeterOperation twitterOperation) {
		this.twitterOperation = twitterOperation;
	}

//	public  void updateStatusonTweeter()
		public void updateStatusonTweeter() {
			
			Twitter twitter = TwitterFactory.getSingleton();
		    String latestStatus  =  twitterOperation.getTextArea();
		    try {
		// save tweet on db
		    	twitterSrvice.addTwitterMessage(twitterOperation.getEntity());
		    	
		    	
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
				//System.out.println(statusCode + ':'
				//	+ httpResponse.getStatusLine().getReasonPhrase());
				//System.out.println(IOUtils.toString(httpResponse.getEntity().getContent())); 
		    
		    }catch(Exception e) {
		    	System.out.println(e.getMessage());
		    }
		    
		}	

		
 public ArrayList  viewTwitter() {// this mothed  will  retrieve all tweets of  the  user 
	 
	 ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setOAuthConsumerKey(connsumerKey);
	    cb.setOAuthConsumerSecret(consumerSecre);
	    cb.setOAuthAccessToken(accessTokenst);
	    cb.setOAuthAccessTokenSecret(accessTokenSecret);

	    Twitter twitter = new TwitterFactory(cb.build()).getInstance();

	    int pageno = 1;
	    String user = "Wisani_3565628";
	    ArrayList statuses = new ArrayList();

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

	    //System.out.println("Total: "+statuses.size());
	  return ArrayList;
	 
 }


}

