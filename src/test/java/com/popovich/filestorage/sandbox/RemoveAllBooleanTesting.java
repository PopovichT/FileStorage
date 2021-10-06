package com.popovich.filestorage.sandbox;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class RemoveAllBooleanTesting {

    Set<Integer> set1 = new HashSet<>(Set.of(1, 2, 3, 4, 5));
    Set<Integer> set2 = new HashSet<>(Set.of(1, 5));

    Set<Integer> set3 = new HashSet<>(Set.of(6, 7));
    Set<Integer> set4 = new HashSet<>(Set.of(5, 7));

    Set<Integer> set5 = new HashSet<>(Set.of(4, 7));
    Set<Integer> set6 = new HashSet<>(Set.of(5, 8));

    @Test
    public void shouldSetDistinctCorrectly() {
        boolean bool;

        bool = set1.removeAll(set2);
        assertTrue(bool);
        log.info("1: {}", set1);

        bool = set3.removeAll(set4);
        assertTrue(bool);
        log.info("2: {}", set3);

        bool = set5.removeAll(set6);
        assertFalse(bool);
        log.info("3: {}", set5);
    }
}
