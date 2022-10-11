package com.camunda.training;

import twitter4j.TwitterException;

public class TwitterDummy {
    public void updateStatus(String content) throws TwitterException {
        if(content.contains("duplicate")){
            throw new TwitterException("Tweet is a duplicate", null, 187);
        }
        System.out.println("Content: " + content);
    }
}
