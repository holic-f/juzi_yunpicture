package com.yupi.yun_picture.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PictureIdGeneratorTest {

    @Test
    void nextIdShouldGenerateJsSafePositiveNumber() {
        Long pictureId = PictureIdGenerator.nextId();

        assertThat(pictureId).isBetween(1L, 9_007_199_254_740_991L);
    }

    @Test
    void nextIdShouldNotGenerateSequentialNumbers() {
        Long firstPictureId = PictureIdGenerator.nextId();
        Long secondPictureId = PictureIdGenerator.nextId();

        assertThat(secondPictureId - firstPictureId).isNotEqualTo(1L);
    }
}
