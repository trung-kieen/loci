package com.loci.loci_backend.common.websocket.infrastructure;

public class WsPaths {

  private WsPaths() {
  }

  public static final String TOPIC = "/topic"; // group
  public static final String QUEUE = "/queue"; // individual
  public static final String USER_PREFIX = "/user";
  public static final String APP_PREFIX = "/app";
  public static final String ENDPOINT = "/ws";

  // App specific endpoint
  public static final String MESSAGE_ENDPOINT = ENDPOINT + "/messages";
  public static final String NOTIFICATION_ENDPOINT = ENDPOINT + "/notifications";
  public static final String PRESENCE_ENDPOINT = ENDPOINT + "/presence";
}
