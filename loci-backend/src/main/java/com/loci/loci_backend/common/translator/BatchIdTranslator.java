package com.loci.loci_backend.common.translator;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface BatchIdTranslator<E, I> {

  Set<I> toInternal(Collection<E> publidIds);

  Map<E, I> toInternalLookup(Collection<E> internalIds);
}
