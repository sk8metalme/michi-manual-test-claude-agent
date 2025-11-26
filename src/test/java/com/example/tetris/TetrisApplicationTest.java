package com.example.tetris;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * {@link TetrisApplication}の統合テストクラス。
 *
 * <p>Spring Bootアプリケーションコンテキストが正常にロードされることを検証します。</p>
 *
 * <p>テスト環境：</p>
 * <ul>
 *   <li>プロファイル：test</li>
 *   <li>データベース：H2インメモリDB</li>
 *   <li>DDLモード：create-drop</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-26
 */
@SpringBootTest
@ActiveProfiles("test")
class TetrisApplicationTest {

    /**
     * アプリケーションコンテキストが正常にロードされることを確認するテスト。
     *
     * <p>このテストが成功する条件：</p>
     * <ul>
     *   <li>すべてのSpring Beanが正常に生成される</li>
     *   <li>依存関係が正しく解決される</li>
     *   <li>設定ファイル（application-test.yml）が正しい</li>
     *   <li>データベース接続が確立できる</li>
     * </ul>
     */
    @Test
    void contextLoads() {
        // Spring Bootアプリケーションが正常に起動することを確認
        // このテストが成功すれば、依存関係と設定が正しいことを示す
    }
}
