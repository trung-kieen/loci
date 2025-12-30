package com.loci.loci_backend.core.groups.domain.repository;

import com.loci.loci_backend.common.translator.BatchIdTranslator;
import com.loci.loci_backend.common.translator.IdTranslator;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;

public interface GroupIdTranslator extends IdTranslator<PublicId, GroupId>  {

}
