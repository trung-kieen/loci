package com.loci.loci_backend.common.user.domain.aggregate;

import java.util.List;

import com.loci.loci_backend.common.user.domain.vo.migration.MigrationError;
import com.loci.loci_backend.common.user.domain.vo.migration.MigrationResultState;
import com.loci.loci_backend.common.user.domain.vo.migration.TotalMigrationFail;
import com.loci.loci_backend.common.user.domain.vo.migration.TotalMigrationUser;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class MigrationResult {
  private final TotalMigrationUser totalSuccess;
  private final TotalMigrationFail totalFail;
  private final MigrationResultState state;
  private final List<MigrationError> errors;


}
