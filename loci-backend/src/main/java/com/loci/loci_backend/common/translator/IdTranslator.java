package com.loci.loci_backend.common.translator;

/**
 * Convert to external id E id to internal Id I
 *
 */
public interface IdTranslator<E, I> {

  I toInternal(E publicId);

  E toPublic(I internalId);

}
