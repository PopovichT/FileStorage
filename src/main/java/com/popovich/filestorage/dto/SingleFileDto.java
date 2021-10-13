package com.popovich.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class SingleFileDto {
    private String ID;
    private String name;
    private Long size;
    private Set<String> tags;
}
