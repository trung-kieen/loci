package com.loci.loci_backend.common.time.infrastructure;

import java.time.Instant;
import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

public class TimeFormatter {

  private TimeFormatter() {
  }

  public static String timeAgo(Instant memberSince) {
    PrettyTime prettyTime = new PrettyTime();
    Date date = Date.from(memberSince);
    return prettyTime.format(date);
  }


}
