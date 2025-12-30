package com.loci.loci_backend.common.websocket.infrastructure;

public class WsPaths {

  private WsPaths() {
  }

  public static final String TOPIC = "/topic";
  public static final String QUEUE = "/queue";
  public static final String APP = "/app";
  public static final String ENDPOINT = "/ws";

  // App specific endpoint
  public static final String MESSAGE_ENDPOINT = ENDPOINT + "/messages";
  public static final String NOTIFICATION_ENDPOINT = ENDPOINT + "/notifications";
  public static final String PRESENCE_ENDPOINT = ENDPOINT + "/presence";

}
