package com.serverless;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLogger implements LambdaLogger {
  private static final Logger LOG = LogManager.getLogger(TestLogger.class);
  public void log(String message){
    LOG.info(message);
  }
  public void log(byte[] message){
    LOG.info(new String(message));
  }
}
