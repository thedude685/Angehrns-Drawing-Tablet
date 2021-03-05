package ch.zli.angehrns_drawing_tablet;


import java.io.File;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterPost {

    static String consumerKeyStr = "c7cU2exU3he9ZVULpgXSRgkLp";
    static String consumerSecretStr = "QSb5gyFhTP8qmtU0RJBKLhl1QRd44EKYEuOqDRfcExicntUCcI";
    static String accessTokenStr = "1367612466356752388-R0cjpQqsEK9GqTOflJ3TJnOs6OMuwS";
    static String accessTokenSecretStr = "Q7BeZXe8dH9RRP7PAWe7H6KJfN5DCAIIqIhQHBxVBci6U";

    public static void tweet(String filepath) {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKeyStr)
                .setOAuthConsumerSecret(consumerSecretStr)
                .setOAuthAccessToken(accessTokenStr)
                .setOAuthAccessTokenSecret(accessTokenSecretStr);

        try {
            Twitter twitter = new TwitterFactory().getInstance();

            twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
            AccessToken accessToken = new AccessToken(accessTokenStr,
                    accessTokenSecretStr);

            twitter.setOAuthAccessToken(accessToken);

            File file = new File(filepath);

            StatusUpdate status = new StatusUpdate("Hey I'm drawing with Angehrns_drawing_tablet");
            status.setMedia(file);
            twitter.updateStatus(status);

        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}
