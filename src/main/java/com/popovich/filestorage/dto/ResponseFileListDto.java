package com.popovich.filestorage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.popovich.filestorage.document.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;


import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseFileListDto {
    private Integer total;
    private List<File> page;
}
