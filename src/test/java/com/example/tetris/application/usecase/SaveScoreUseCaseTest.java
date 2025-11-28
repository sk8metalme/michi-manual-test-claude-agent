package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.dto.ScoreDTO;
import com.example.tetris.application.dto.TetrominoDTO;
import com.example.tetris.application.port.ScoreRepositoryPort;
import com.example.tetris.domain.GameStatus;
import com.example.tetris.domain.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * SaveScoreUseCaseの単体テストクラス。
 *
 * <p>このテストは、スコア保存ユースケースの動作を検証します。</p>
 *
 * <h3>テスト対象:</h3>
 * <ul>
 *   <li>saveScore()メソッドのスコア保存処理</li>
 *   <li>GameStateDTOからScoreドメインモデルへの変換</li>
 *   <li>タイムスタンプの自動付与</li>
 *   <li>@Transactionalの動作</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 */
class SaveScoreUseCaseTest {

    private SaveScoreUseCase useCase;
    private ScoreRepositoryPort scoreRepositoryPort;

    @BeforeEach
    void setUp() {
        // ScoreRepositoryPortのモックを作成
        scoreRepositoryPort = mock(ScoreRepositoryPort.class);

        // 実装クラスをインスタンス化
        useCase = new SaveScoreUseCaseImpl(scoreRepositoryPort);
    }

    /**
     * saveScore()が非nullのScoreDTOを返すことを確認。
     */
    @Test
    void testSaveScore_ReturnsNonNullScoreDTO() {
        // Given
        GameStateDTO gameStateDTO = createGameStateDTO(1000, 5, 42);
        Score savedScore = createScore(1L, 1000, 5, 42);

        when(scoreRepositoryPort.save(any(Score.class)))
                .thenReturn(savedScore);

        // When
        ScoreDTO result = useCase.saveScore(gameStateDTO);

        // Then
        assertNotNull(result, "saveScore()はnullを返してはいけない");
    }

    /**
     * GameStateDTOのスコア、レベル、累計ライン数が正しくScoreドメインモデルに変換されることを確認。
     */
    @Test
    void testSaveScore_ConvertsGameStateDTOToScore() {
        // Given
        int expectedScore = 1500;
        int expectedLevel = 7;
        int expectedTotalLines = 63;
        GameStateDTO gameStateDTO = createGameStateDTO(
                expectedScore, expectedLevel, expectedTotalLines);

        Score savedScore = createScore(
                2L, expectedScore, expectedLevel, expectedTotalLines);
        when(scoreRepositoryPort.save(any(Score.class)))
                .thenReturn(savedScore);

        // When
        ScoreDTO result = useCase.saveScore(gameStateDTO);

        // Then - ScoreRepositoryPort.save()が呼ばれたことを確認
        ArgumentCaptor<Score> captor = ArgumentCaptor.forClass(Score.class);
        verify(scoreRepositoryPort, times(1)).save(captor.capture());

        // 保存されたScoreドメインモデルの内容を検証
        Score capturedScore = captor.getValue();
        assertEquals(expectedScore, capturedScore.score());
        assertEquals(expectedLevel, capturedScore.level());
        assertEquals(expectedTotalLines, capturedScore.totalLinesCleared());
    }

    /**
     * タイムスタンプが自動付与されることを確認。
     */
    @Test
    void testSaveScore_AutomaticallyAssignsTimestamp() {
        // Given
        GameStateDTO gameStateDTO = createGameStateDTO(2000, 10, 100);
        Score savedScore = createScore(3L, 2000, 10, 100);

        when(scoreRepositoryPort.save(any(Score.class)))
                .thenReturn(savedScore);

        // When
        LocalDateTime beforeSave = LocalDateTime.now();
        ScoreDTO result = useCase.saveScore(gameStateDTO);
        LocalDateTime afterSave = LocalDateTime.now();

        // Then - タイムスタンプが現在時刻の範囲内であることを確認
        ArgumentCaptor<Score> captor = ArgumentCaptor.forClass(Score.class);
        verify(scoreRepositoryPort, times(1)).save(captor.capture());

        Score capturedScore = captor.getValue();
        assertNotNull(capturedScore.timestamp(), "タイムスタンプが設定されているべき");
        assertTrue(
                !capturedScore.timestamp().isBefore(beforeSave) &&
                !capturedScore.timestamp().isAfter(afterSave),
                "タイムスタンプは保存処理の前後の範囲内であるべき"
        );
    }

    /**
     * 保存されたScoreDTOにIDが含まれることを確認。
     */
    @Test
    void testSaveScore_ReturnedScoreDTOContainsId() {
        // Given
        Long expectedId = 5L;
        GameStateDTO gameStateDTO = createGameStateDTO(500, 2, 15);
        Score savedScore = createScore(expectedId, 500, 2, 15);

        when(scoreRepositoryPort.save(any(Score.class)))
                .thenReturn(savedScore);

        // When
        ScoreDTO result = useCase.saveScore(gameStateDTO);

        // Then
        assertNotNull(result.id(), "保存されたScoreDTOはIDを含むべき");
        assertEquals(expectedId, result.id());
    }

    /**
     * 保存されたScoreDTOの値がScoreドメインモデルと一致することを確認。
     */
    @Test
    void testSaveScore_ReturnedScoreDTOMatchesScore() {
        // Given
        Long expectedId = 10L;
        int expectedScore = 3000;
        int expectedLevel = 8;
        int expectedTotalLines = 75;
        LocalDateTime expectedTimestamp = LocalDateTime.now();

        GameStateDTO gameStateDTO = createGameStateDTO(
                expectedScore, expectedLevel, expectedTotalLines);

        Score savedScore = new Score(
                expectedId,
                expectedScore,
                expectedLevel,
                expectedTotalLines,
                expectedTimestamp
        );
        when(scoreRepositoryPort.save(any(Score.class)))
                .thenReturn(savedScore);

        // When
        ScoreDTO result = useCase.saveScore(gameStateDTO);

        // Then
        assertEquals(expectedId, result.id());
        assertEquals(expectedScore, result.score());
        assertEquals(expectedLevel, result.level());
        assertEquals(expectedTotalLines, result.totalLinesCleared());
        assertEquals(expectedTimestamp, result.timestamp());
    }

    // ===== ヘルパーメソッド =====

    /**
     * テスト用のGameStateDTOを作成。
     */
    private GameStateDTO createGameStateDTO(int score, int level, int totalLinesCleared) {
        return new GameStateDTO(
                GameStatus.GAME_OVER,
                null,  // currentTetromino
                null,  // nextTetromino
                new String[20][10],  // field
                score,
                level,
                totalLinesCleared
        );
    }

    /**
     * テスト用のScoreドメインモデルを作成。
     */
    private Score createScore(Long id, int score, int level, int totalLinesCleared) {
        return new Score(
                id,
                score,
                level,
                totalLinesCleared,
                LocalDateTime.now()
        );
    }
}
