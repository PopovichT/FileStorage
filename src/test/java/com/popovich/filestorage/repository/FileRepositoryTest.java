package com.popovich.filestorage.repository;

import com.popovich.filestorage.document.File;
import com.popovich.filestorage.mother.FileObjectMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileRepositoryTest {

    @Autowired
    private FileRepository repository;

    //There is still no spring environment stuff for testing ELK repositories like @DataJpaTest (!!!!!!) (-\/-)
    @Test
    void testRepositoryMainFunctions() {

        var file = FileObjectMother.valid().build();
        var returnFile = repository.save(file);
        assertNotNull(returnFile);

        var savedFileOpt = repository.findById(returnFile.getId());
        assertTrue(savedFileOpt.isPresent());
        assertEquals(savedFileOpt.get().getId(), returnFile.getId());

        var advancedSearchedFilesByName = repository.findByNameContainingIgnoreCase(file.getName().substring(3));
        boolean flag = false;
        for (File foreachFile : advancedSearchedFilesByName) {
            if (foreachFile.getId().equals(returnFile.getId())) {
                flag = true;
            }
        }
        assertTrue(flag);

        repository.deleteById(returnFile.getId());
        var savedFileAfterDeletingOpt = repository.findById(returnFile.getId());
        assertFalse(savedFileAfterDeletingOpt.isPresent());
    }
}
