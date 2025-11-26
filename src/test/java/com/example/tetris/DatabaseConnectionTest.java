package com.example.tetris;

import com.example.tetris.adapter.outbound.ScoreEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * データベース接続確認テストクラス。
 *
 * <p>各Spring Profileでデータベース接続が正常に機能することを検証します。</p>
 *
 * <p>テスト対象プロファイル：</p>
 * <ul>
 *   <li>test：H2インメモリDB（create-dropモード）</li>
 *   <li>dev：H2インメモリDB（updateモード）</li>
 * </ul>
 *
 * <p>本番環境（prod）のPostgreSQLは、実際のデータベースが必要なため、
 * このテストには含まれません。</p>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-26
 */
class DatabaseConnectionTest {

    /**
     * testプロファイルでのデータベース接続テスト。
     *
     * <p>H2データベース（create-dropモード）での接続と永続化を検証します。</p>
     */
    @Nested
    @DataJpaTest
    @ActiveProfiles("test")
    class TestProfileConnectionTest {

        @Autowired
        private DataSource dataSource;

        @Autowired
        private TestEntityManager entityManager;

        @Test
        void testプロファイルでH2データベースに接続できること() throws Exception {
            // データソースから接続を取得
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();

                // H2データベースであることを確認
                assertThat(metaData.getDatabaseProductName()).isEqualTo("H2");
                assertThat(connection.isValid(1)).isTrue();
            }
        }

        @Test
        void testプロファイルでエンティティを永続化できること() {
            // Given
            ScoreEntity score = new ScoreEntity();
            score.setScore(5000);
            score.setLevel(3);
            score.setTotalLinesCleared(25);
            score.setTimestamp(LocalDateTime.now());

            // When
            ScoreEntity savedScore = entityManager.persistAndFlush(score);

            // Then
            assertThat(savedScore.getId()).isNotNull();
            assertThat(savedScore.getScore()).isEqualTo(5000);
        }
    }

    /**
     * devプロファイルでのデータベース接続テスト。
     *
     * <p>H2データベース（updateモード）での接続と永続化を検証します。</p>
     */
    @Nested
    @DataJpaTest
    @ActiveProfiles("dev")
    class DevProfileConnectionTest {

        @Autowired
        private DataSource dataSource;

        @Autowired
        private TestEntityManager entityManager;

        @Test
        void devプロファイルでH2データベースに接続できること() throws Exception {
            // データソースから接続を取得
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();

                // H2データベースであることを確認
                assertThat(metaData.getDatabaseProductName()).isEqualTo("H2");
                assertThat(connection.isValid(1)).isTrue();
            }
        }

        @Test
        void devプロファイルでエンティティを永続化できること() {
            // Given
            ScoreEntity score = new ScoreEntity();
            score.setScore(8000);
            score.setLevel(4);
            score.setTotalLinesCleared(40);
            score.setTimestamp(LocalDateTime.now());

            // When
            ScoreEntity savedScore = entityManager.persistAndFlush(score);

            // Then
            assertThat(savedScore.getId()).isNotNull();
            assertThat(savedScore.getScore()).isEqualTo(8000);
        }
    }
}
