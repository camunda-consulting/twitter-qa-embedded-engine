package com.camunda.training;

import twitter4j.TwitterException;

public class TwitterDummy {
    public void updateStatus(String content) throws TwitterException {
        if(content.contains("duplicate")){
            throw new TwitterException("{\"errors\":[{\"message\":\"Tweet is a duplicate\", \"code\":\"187\"}]}", new RuntimeException("oops"));
        }
        System.out.println("Content: " + content);
    }
}
