/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

  // individual
  public static final String INDIVIDUAL_RECEIVE_MESSAGE = QUEUE + "/messages.receive";

  public static final String GROUP_RECEIVE_MESSAGE = TOPIC + "/messages.receive";

}
