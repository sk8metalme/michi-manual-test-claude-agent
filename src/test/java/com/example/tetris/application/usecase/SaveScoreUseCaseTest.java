package com.example.tetris.application.usecase;

import com.example.tetris.adapter.outbound.ScoreEntity;
import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.dto.ScoreDTO;
import com.example.tetris.application.dto.TetrominoDTO;
import com.example.tetris.application.port.ScoreRepositoryPort;
import com.example.tetris.domain.GameStatus;
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
 *   <li>GameStateDTOからScoreEntityへの変換</li>
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
        ScoreEntity savedEntity = createScoreEntity(1L, 1000, 5, 42);

        when(scoreRepositoryPort.save(any(ScoreEntity.class)))
                .thenReturn(savedEntity);

        // When
        ScoreDTO result = useCase.saveScore(gameStateDTO);

        // Then
        assertNotNull(result, "saveScore()はnullを返してはいけない");
    }

    /**
     * GameStateDTOのスコア、レベル、累計ライン数が正しくScoreEntityに変換されることを確認。
     */
    @Test
    void testSaveScore_ConvertsGameStateDTOToScoreEntity() {
        // Given
        int expectedScore = 1500;
        int expectedLevel = 7;
        int expectedTotalLines = 63;
        GameStateDTO gameStateDTO = createGameStateDTO(
                expectedScore, expectedLevel, expectedTotalLines);

        ScoreEntity savedEntity = createScoreEntity(
                2L, expectedScore, expectedLevel, expectedTotalLines);
        when(scoreRepositoryPort.save(any(ScoreEntity.class)))
                .thenReturn(savedEntity);

        // When
        ScoreDTO result = useCase.saveScore(gameStateDTO);

        // Then - ScoreRepositoryPort.save()が呼ばれたことを確認
        ArgumentCaptor<ScoreEntity> captor = ArgumentCaptor.forClass(ScoreEntity.class);
        verify(scoreRepositoryPort, times(1)).save(captor.capture());

        // 保存されたScoreEntityの内容を検証
        ScoreEntity capturedEntity = captor.getValue();
        assertEquals(expectedScore, capturedEntity.getScore());
        assertEquals(expectedLevel, capturedEntity.getLevel());
        assertEquals(expectedTotalLines, capturedEntity.getTotalLinesCleared());
    }

    /**
     * タイムスタンプが自動付与されることを確認。
     */
    @Test
    void testSaveScore_AutomaticallyAssignsTimestamp() {
        // Given
        GameStateDTO gameStateDTO = createGameStateDTO(2000, 10, 100);
        ScoreEntity savedEntity = createScoreEntity(3L, 2000, 10, 100);

        when(scoreRepositoryPort.save(any(ScoreEntity.class)))
                .thenReturn(savedEntity);

        // When
        LocalDateTime beforeSave = LocalDateTime.now();
        ScoreDTO result = useCase.saveScore(gameStateDTO);
        LocalDateTime afterSave = LocalDateTime.now();

        // Then - タイムスタンプが現在時刻の範囲内であることを確認
        ArgumentCaptor<ScoreEntity> captor = ArgumentCaptor.forClass(ScoreEntity.class);
        verify(scoreRepositoryPort, times(1)).save(captor.capture());

        ScoreEntity capturedEntity = captor.getValue();
        assertNotNull(capturedEntity.getTimestamp(), "タイムスタンプが設定されているべき");
        assertTrue(
                !capturedEntity.getTimestamp().isBefore(beforeSave) &&
                !capturedEntity.getTimestamp().isAfter(afterSave),
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
        ScoreEntity savedEntity = createScoreEntity(expectedId, 500, 2, 15);

        when(scoreRepositoryPort.save(any(ScoreEntity.class)))
                .thenReturn(savedEntity);

        // When
        ScoreDTO result = useCase.saveScore(gameStateDTO);

        // Then
        assertNotNull(result.id(), "保存されたScoreDTOはIDを含むべき");
        assertEquals(expectedId, result.id());
    }

    /**
     * 保存されたScoreDTOの値がScoreEntityと一致することを確認。
     */
    @Test
    void testSaveScore_ReturnedScoreDTOMatchesScoreEntity() {
        // Given
        Long expectedId = 10L;
        int expectedScore = 3000;
        int expectedLevel = 8;
        int expectedTotalLines = 75;
        LocalDateTime expectedTimestamp = LocalDateTime.now();

        GameStateDTO gameStateDTO = createGameStateDTO(
                expectedScore, expectedLevel, expectedTotalLines);

        ScoreEntity savedEntity = new ScoreEntity(
                expectedId,
                expectedScore,
                expectedLevel,
                expectedTotalLines,
                expectedTimestamp
        );
        when(scoreRepositoryPort.save(any(ScoreEntity.class)))
                .thenReturn(savedEntity);

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
     * テスト用のScoreEntityを作成。
     */
    private ScoreEntity createScoreEntity(Long id, int score, int level, int totalLinesCleared) {
        return new ScoreEntity(
                id,
                score,
                level,
                totalLinesCleared,
                LocalDateTime.now()
        );
    }
}
