package com.loci.loci_backend.core.messaging.domain.service;

public interface ScheduleService {

  void queueMessage();

  void scheduleReplay();

}
