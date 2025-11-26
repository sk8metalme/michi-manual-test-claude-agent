package com.example.tetris.adapter.outbound;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ScoreEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void ScoreEntityを永続化できること() {
        // Given: スコアエンティティを作成
        ScoreEntity score = new ScoreEntity();
        score.setScore(10000);
        score.setLevel(5);
        score.setTotalLinesCleared(50);
        score.setTimestamp(LocalDateTime.now());

        // When: エンティティを保存
        ScoreEntity savedScore = entityManager.persistAndFlush(score);

        // Then: IDが自動生成され、データが正しく保存されること
        assertThat(savedScore.getId()).isNotNull();
        assertThat(savedScore.getScore()).isEqualTo(10000);
        assertThat(savedScore.getLevel()).isEqualTo(5);
        assertThat(savedScore.getTotalLinesCleared()).isEqualTo(50);
        assertThat(savedScore.getTimestamp()).isNotNull();
    }

    @Test
    void タイムスタンプはnullであってはならない() {
        // Given: タイムスタンプなしのエンティティ
        ScoreEntity score = new ScoreEntity();
        score.setScore(5000);
        score.setLevel(3);
        score.setTotalLinesCleared(25);
        // timestampを設定しない

        // When/Then: 保存時にエラーが発生すること
        try {
            entityManager.persistAndFlush(score);
            assertThat(false).as("タイムスタンプがnullの場合、保存に失敗すべき").isTrue();
        } catch (Exception e) {
            // 期待通りの動作
            assertThat(e).isNotNull();
        }
    }

    @Test
    void 複数のスコアを保存できること() {
        // Given: 複数のスコアエンティティ
        ScoreEntity score1 = new ScoreEntity();
        score1.setScore(10000);
        score1.setLevel(5);
        score1.setTotalLinesCleared(50);
        score1.setTimestamp(LocalDateTime.now());

        ScoreEntity score2 = new ScoreEntity();
        score2.setScore(20000);
        score2.setLevel(8);
        score2.setTotalLinesCleared(100);
        score2.setTimestamp(LocalDateTime.now());

        // When: 複数のエンティティを保存
        ScoreEntity savedScore1 = entityManager.persistAndFlush(score1);
        ScoreEntity savedScore2 = entityManager.persistAndFlush(score2);

        // Then: それぞれが異なるIDを持つこと
        assertThat(savedScore1.getId()).isNotNull();
        assertThat(savedScore2.getId()).isNotNull();
        assertThat(savedScore1.getId()).isNotEqualTo(savedScore2.getId());
    }
}
