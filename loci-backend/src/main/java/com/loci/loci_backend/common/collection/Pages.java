package com.loci.loci_backend.common.collection;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class Pages {

  private Pages(){
  }

  public static <S, T> Page<T> map(Page<S> sourcePage, Function<S, T> converter) {
    if (sourcePage == null){
      return null;
    }
    List<T> content = sourcePage.getContent().stream().map(converter).toList();
    return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
  }
}
