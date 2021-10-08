package com.popovich.filestorage.mother;

import com.popovich.filestorage.document.File;

import java.util.HashSet;
import java.util.Set;

public class FileObjectMother {
    public static File.FileBuilder valid() {
        return File.builder()
                .name("qweqweqweqwe")
                .size(100L)
                .tags(new HashSet<>(Set.of("qwe", "zxc", "dbe")));
    }
}
