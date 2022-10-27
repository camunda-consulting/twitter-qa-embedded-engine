package com.camunda.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.TwitterException;

public class TwitterDummy {

  private static final Logger LOG = LoggerFactory.getLogger(TwitterDummy.class);

  public void updateStatus(String content) throws TwitterException {
    if (content.contains("duplicate")) {
      throw new TwitterException("{\"errors\":[{\"message\":\"Tweet is a duplicate\", \"code\":\"187\"}]}",
          new RuntimeException("oops"));
    }
    LOG.info("Content: {}", content);
  }
}
