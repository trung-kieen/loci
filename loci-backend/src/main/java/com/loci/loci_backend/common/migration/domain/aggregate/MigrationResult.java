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
