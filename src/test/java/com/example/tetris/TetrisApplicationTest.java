package com.example.tetris;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TetrisApplicationTest {

    @Test
    void contextLoads() {
        // Spring Bootアプリケーションが正常に起動することを確認
        // このテストが成功すれば、依存関係と設定が正しいことを示す
    }
}
