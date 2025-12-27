package com.loci.loci_backend.core.conversation.domain.vo;

import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;

import org.jilt.Builder;
import org.jilt.BuilderStyle;


@Builder(style = BuilderStyle.STAGED)
public record ConversationQuery(
    ConversationFilter filter,
    SearchQuery query) {
}
