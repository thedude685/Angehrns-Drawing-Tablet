package ch.zli.angehrns_drawing_tablet;


import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterPost {

    //static String consumerKeyStr = "MJeQLvIhRm1TvNVLHdywWVYZw";
    //static String consumerSecretStr = "x71coSa5VqKDecCuXJY2yOb10eJRSlgNrGSd4g87K04JR8fdrz";
    static String consumerKeyStr = "VtDmmOX6P9D5BQxJCvacAnZPB";
    static String consumerSecretStr = "06Yo7DFjprzB44teAsPEHWbIQtExnq2ASKVevcVORsyaZUoQQ0";
    static String accessTokenStr = "1367612466356752388-9fOMOB6wKLr5sP0C2KR7pHWujb8X8a";
    static String accessTokenSecretStr = "tyEC2p0TPi9OJpSSwEDEu5IeI14OneOIdUU8c4rNrpj23";

    public static void tweet() {

        try {
            Twitter twitter = new TwitterFactory().getInstance();

            twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
            AccessToken accessToken = new AccessToken(accessTokenStr,
                    accessTokenSecretStr);

            twitter.setOAuthAccessToken(accessToken);

            twitter.updateStatus("Hey I'm drawing with Angehrns_drawing_tablet");

            System.out.println("Successfully updated the status in Twitter.");
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}
