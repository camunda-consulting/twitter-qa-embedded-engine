package com.camunda.training;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ProcessEngineCoverageExtension.class)
public class ProcessJUnitTest {
  
  @Test
  @Deployment(resources = "TwitterQAProcess.bpmn")
  public void testHappyPath() {
    Mocks.register("createTweetDelegate", new LoggerDelegate());
    
    // Start process with Java API and variables
    identityService().setAuthenticatedUserId("demo");
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("TwitterQAProcess",
        withVariables("content", "I did it from Junit! Cheers Ingo1"));
    
    // Make assertions on the process instance
    assertThat(processInstance).isWaitingAt(findId("Review tweet")).task().hasCandidateGroup("management");
    complete(task(), withVariables("approved", true));
    
    assertThat(processInstance).isEnded();  
  }
}
