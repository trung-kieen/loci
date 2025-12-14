package com.loci.loci_backend.common.migration.domain.aggregate;

import java.util.List;

import com.loci.loci_backend.common.migration.domain.vo.MigrationError;
import com.loci.loci_backend.common.migration.domain.vo.MigrationResultState;
import com.loci.loci_backend.common.migration.domain.vo.TotalMigrationFail;
import com.loci.loci_backend.common.migration.domain.vo.TotalMigrationUser;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MigrationResult {
  private final TotalMigrationUser totalSuccess;
  private final TotalMigrationFail totalFail;
  private final MigrationResultState state;
  private final List<MigrationError> errors;


}
