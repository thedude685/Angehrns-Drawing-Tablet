package ch.zli.angehrns_drawing_tablet;


import java.io.File;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterPost {

    static String consumerKeyStr = "2SWQWE4GqNKY1IBachVGFOt8A";
    static String consumerSecretStr = "6eeX9Kj7ENZSHbXfvep0gsRGzI4Poyec3rwWMCEDbHwGWZPtBn";
    static String accessTokenStr = "1367612466356752388-wukgGrHGnPewWPECA0iSswCui3kUuJ";
    static String accessTokenSecretStr = "skvr31c95hmkF0e0assj53knaUWhWGLxtHTNf1LLK92h3 ";

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
            status.setMedia(file); // set the image to be uploaded here.
            twitter.updateStatus(status);

        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}
