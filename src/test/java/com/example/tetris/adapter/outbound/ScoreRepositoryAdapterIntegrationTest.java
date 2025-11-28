package com.example.tetris.adapter.outbound;

import com.example.tetris.application.usecase.StartGameUseCase;
import com.example.tetris.domain.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ScoreRepositoryAdapterの統合テストクラス。
 *
 * <p>このテストは、ScoreRepositoryAdapterがデータベースと正しく連携することを検証します。</p>
 *
 * <h3>テスト対象:</h3>
 * <ul>
 *   <li>スコアの保存（データベース永続化）</li>
 *   <li>過去10件の高スコア取得</li>
 *   <li>スコアの降順ソート</li>
 *   <li>同点時のタイムスタンプ降順ソート</li>
 *   <li>10件制限の動作</li>
 * </ul>
 *
 * <h3>テスト環境:</h3>
 * <ul>
 *   <li>H2インメモリデータベース</li>
 *   <li>@Transactional によるテスト後の自動ロールバック</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 */
@SpringBootTest
@Transactional
class ScoreRepositoryAdapterIntegrationTest {

    @Autowired
    private ScoreRepositoryAdapter scoreRepositoryAdapter;

    @MockBean
    private StartGameUseCase startGameUseCase;

    /**
     * スコアが正しくデータベースに保存されることを確認。
     */
    @Test
    void testSaveScore_PersistsToDatabase() {
        // Given
        Score score = Score.create(1000, 5, 42, LocalDateTime.now());

        // When
        Score savedScore = scoreRepositoryAdapter.save(score);

        // Then
        assertNotNull(savedScore.id(), "保存されたスコアはIDを持つべき");
        assertEquals(1000, savedScore.score());
        assertEquals(5, savedScore.level());
        assertEquals(42, savedScore.totalLinesCleared());
        assertNotNull(savedScore.timestamp());
    }

    /**
     * 過去10件の高スコアが降順で取得されることを確認。
     */
    @Test
    void testFindTop10_ReturnsDescendingOrder() {
        // Given - 5件のスコアを保存（降順ではない順序で保存）
        scoreRepositoryAdapter.save(Score.create(500, 2, 15, LocalDateTime.now()));
        scoreRepositoryAdapter.save(Score.create(1500, 7, 63, LocalDateTime.now()));
        scoreRepositoryAdapter.save(Score.create(1000, 5, 42, LocalDateTime.now()));
        scoreRepositoryAdapter.save(Score.create(2000, 10, 100, LocalDateTime.now()));
        scoreRepositoryAdapter.save(Score.create(300, 1, 8, LocalDateTime.now()));

        // When
        List<Score> top10 = scoreRepositoryAdapter.findTop10ByOrderByScoreDesc();

        // Then
        assertEquals(5, top10.size(), "保存した5件すべてが取得されるべき");
        assertEquals(2000, top10.get(0).score(), "1位は2000点");
        assertEquals(1500, top10.get(1).score(), "2位は1500点");
        assertEquals(1000, top10.get(2).score(), "3位は1000点");
        assertEquals(500, top10.get(3).score(), "4位は500点");
        assertEquals(300, top10.get(4).score(), "5位は300点");
    }

    /**
     * 11件以上のスコアがある場合でも10件に制限されることを確認。
     */
    @Test
    void testFindTop10_LimitsTen() {
        // Given - 15件のスコアを保存
        for (int i = 1; i <= 15; i++) {
            scoreRepositoryAdapter.save(Score.create(i * 100, i, i * 5, LocalDateTime.now()));
        }

        // When
        List<Score> top10 = scoreRepositoryAdapter.findTop10ByOrderByScoreDesc();

        // Then
        assertEquals(10, top10.size(), "10件に制限されるべき");
        assertEquals(1500, top10.get(0).score(), "1位は1500点");
        assertEquals(600, top10.get(9).score(), "10位は600点");
    }

    /**
     * スコアが同点の場合、タイムスタンプの降順でソートされることを確認。
     */
    @Test
    void testFindTop10_SortsByTimestampWhenScoresTied() throws InterruptedException {
        // Given - 同点（1000点）のスコアを3件保存
        LocalDateTime time1 = LocalDateTime.now();
        Thread.sleep(10); // タイムスタンプを確実に異なるようにする
        LocalDateTime time2 = LocalDateTime.now();
        Thread.sleep(10);
        LocalDateTime time3 = LocalDateTime.now();

        scoreRepositoryAdapter.save(Score.create(1000, 5, 42, time1)); // 最古
        scoreRepositoryAdapter.save(Score.create(1000, 5, 42, time3)); // 最新
        scoreRepositoryAdapter.save(Score.create(1000, 5, 42, time2)); // 中間

        // When
        List<Score> top10 = scoreRepositoryAdapter.findTop10ByOrderByScoreDesc();

        // Then
        assertEquals(3, top10.size());
        assertEquals(time3, top10.get(0).timestamp(), "1位は最新のタイムスタンプ");
        assertEquals(time2, top10.get(1).timestamp(), "2位は中間のタイムスタンプ");
        assertEquals(time1, top10.get(2).timestamp(), "3位は最古のタイムスタンプ");
    }

    /**
     * スコアが0件の場合、空リストが返されることを確認。
     */
    @Test
    void testFindTop10_ReturnsEmptyListWhenNoScores() {
        // When
        List<Score> top10 = scoreRepositoryAdapter.findTop10ByOrderByScoreDesc();

        // Then
        assertNotNull(top10, "nullではなく空リストを返すべき");
        assertTrue(top10.isEmpty(), "スコアがない場合は空リスト");
    }
}
