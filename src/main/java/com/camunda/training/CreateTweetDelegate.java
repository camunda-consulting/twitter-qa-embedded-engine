package com.camunda.training;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Component
public class CreateTweetDelegate implements JavaDelegate {
  private final Logger LOGGER = LoggerFactory.getLogger(CreateTweetDelegate.class.getName());

  public void execute(DelegateExecution execution) throws Exception {
    String content = (String) execution.getVariable("content");
    LOGGER.info("Publishing tweet: " + content);
    if (content.equals("network error")) {
      throw new RuntimeException("network error occured");
    }

    // Fill out credentials to call twitter
    AccessToken accessToken = new AccessToken("MY_ACCESS_TOKEN", "MY_ACCESS_TOKEN_SECRET");
    Twitter twitter = new TwitterFactory().getInstance();
    twitter.setOAuthConsumer("MY_API_KEY", "MY_API_KEY_SECRET");
    twitter.setOAuthAccessToken(accessToken);
    try {
      //twitter.updateStatus(content);
      new TwitterDummy().updateStatus(content);
    } catch (TwitterException e) {
      if (e.getErrorCode() == 187) {
        throw new BpmnError("duplicateTweet", e.getLocalizedMessage());
      } else {
        throw e;
      }
    }
  }
}
