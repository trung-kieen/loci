package com.loci.loci_backend.common.validation.domain;

public class TooManyElementsException extends AssertionException {
  private final String field;
  private final String currentSize;
  private final String maxSize;

  public TooManyElementsException(TooManyElementsExceptionBuilder builder) {
    super(builder.field, builder.message());
    field = builder.field;
    maxSize = String.valueOf(builder.maxSize);
    currentSize = String.valueOf(builder.size);
  }

  /*
   * Use static inner class to perform builder exception from method base
   * instead of constructor overloadding
   */
  public static class TooManyElementsExceptionBuilder {
    String field;
    int maxSize;
    int size;

    public TooManyElementsExceptionBuilder field(String field) {
      this.field = field;
      return this;
    }

    public TooManyElementsExceptionBuilder maxSize(int maxSize) {
      this.maxSize = maxSize;
      return this;
    }

    public TooManyElementsExceptionBuilder size(int size) {
      this.size = size;
      return this;
    }

    public TooManyElementsException build() {
      return new TooManyElementsException(this);
    }

    public String message() {
      return new StringBuilder()
          .append("Size of collection \"")
          .append(field)
          .append("\" must at most ")
          .append(maxSize)
          .append(" but was ")
          .append(size)
          .toString();
    }

  }
  public static TooManyElementsExceptionBuilder builder(){
    return new TooManyElementsExceptionBuilder();
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.TOO_MANY_ELEMENTS;
  }

}
