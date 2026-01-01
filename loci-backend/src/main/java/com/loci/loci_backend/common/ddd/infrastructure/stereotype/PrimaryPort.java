
package com.loci.loci_backend.common.ddd.infrastructure.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loci.loci_backend.common.log.Loggable;

import org.springframework.stereotype.Service;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
@Loggable
public @interface PrimaryPort {

}
