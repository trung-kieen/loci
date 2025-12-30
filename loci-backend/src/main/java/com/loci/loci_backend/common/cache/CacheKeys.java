package com.loci.loci_backend.common.cache;

public class CacheKeys {

  public static final String USER_UUID_TO_ID = "user:uuid";
  public static final String USER_ID_TO_UUID = "user:id";
  public static final String USER_BATCH_UUID_TO_ID = "user:batch:uuid";


  public static final String USER_PRESENCE= "presence:user";
  // presence => cache for 2 minutes

  private CacheKeys() {
  }
}
