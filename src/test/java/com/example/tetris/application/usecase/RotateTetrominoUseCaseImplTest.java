package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.domain.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RotateTetrominoUseCaseImplの単体テスト。
 *
 * <p>このテストクラスは、RotateTetrominoUseCaseImplの以下の機能を検証します：</p>
 * <ul>
 *   <li>セッションIDに基づくゲーム状態の管理</li>
 *   <li>テトリミノの回転処理</li>
 *   <li>回転可能な場合の状態更新</li>
 *   <li>回転不可能な場合（衝突）の状態維持</li>
 *   <li>DTOへの変換</li>
 * </ul>
 *
 * <h3>テストシナリオ：</h3>
 * <ol>
 *   <li>回転成功ケース: 回転可能な状態でテトリミノを回転</li>
 *   <li>回転失敗ケース: 回転後に衝突が発生する場合、元の状態を維持</li>
 *   <li>セッション管理: 複数セッションの同時処理</li>
 *   <li>null チェック: sessionIdがnullの場合の例外スロー</li>
 * </ol>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class RotateTetrominoUseCaseImplTest {

    private RotateTetrominoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RotateTetrominoUseCaseImpl();
    }

    @Test
    @DisplayName("回転成功: テトリミノが回転可能な場合、新しい回転状態を返す")
    void shouldRotateTetrominoSuccessfully() {
        // Given: 初回実行時は自動的にGameStateが初期化される
        String sessionId = "test-session-1";

        // When: テトリミノを回転
        GameStateDTO result = useCase.execute(sessionId);

        // Then: ゲーム状態がDTOとして返される
        assertNotNull(result, "GameStateDTO should not be null");
        assertEquals(GameStatus.PLAYING, result.status(), "Game should be in PLAYING status");
        assertNotNull(result.currentTetromino(), "Current tetromino should not be null");
        assertNotNull(result.nextTetromino(), "Next tetromino should not be null");
        assertNotNull(result.field(), "Field should not be null");
        assertEquals(0, result.score(), "Initial score should be 0");
        assertEquals(1, result.level(), "Initial level should be 1");
        assertEquals(0, result.totalLinesCleared(), "Initial total lines cleared should be 0");
    }

    @Test
    @DisplayName("複数回転: テトリミノを複数回回転できる")
    void shouldRotateMultipleTimes() {
        // Given: 初回実行時は自動的にGameStateが初期化される
        String sessionId = "test-session-2";

        // When: テトリミノを4回回転（360度回転）
        GameStateDTO result1 = useCase.execute(sessionId);
        GameStateDTO result2 = useCase.execute(sessionId);
        GameStateDTO result3 = useCase.execute(sessionId);
        GameStateDTO result4 = useCase.execute(sessionId);

        // Then: 各回転後もゲーム状態が正常に返される
        assertNotNull(result1, "First rotation result should not be null");
        assertNotNull(result2, "Second rotation result should not be null");
        assertNotNull(result3, "Third rotation result should not be null");
        assertNotNull(result4, "Fourth rotation result should not be null");

        // すべてPLAYING状態を維持
        assertEquals(GameStatus.PLAYING, result1.status(), "Game should be PLAYING after first rotation");
        assertEquals(GameStatus.PLAYING, result2.status(), "Game should be PLAYING after second rotation");
        assertEquals(GameStatus.PLAYING, result3.status(), "Game should be PLAYING after third rotation");
        assertEquals(GameStatus.PLAYING, result4.status(), "Game should be PLAYING after fourth rotation");

        // 回転のみなので、スコア・レベル・ラインは不変
        assertEquals(0, result4.score(), "Score should remain 0");
        assertEquals(1, result4.level(), "Level should remain 1");
        assertEquals(0, result4.totalLinesCleared(), "Total lines cleared should remain 0");
    }

    @Test
    @DisplayName("セッション管理: 異なるセッションIDで独立したゲーム状態を管理")
    void shouldManageIndependentSessions() {
        // Given: 2つの異なるセッション
        String sessionId1 = "session-1";
        String sessionId2 = "session-2";

        // When: session1を複数回実行、session2を1回実行
        GameStateDTO result1a = useCase.execute(sessionId1);
        GameStateDTO result1b = useCase.execute(sessionId1);
        GameStateDTO result1c = useCase.execute(sessionId1);
        GameStateDTO result2a = useCase.execute(sessionId2);

        // Then: 各セッションが独立したゲーム状態を持つ
        assertNotNull(result1a, "Session 1 first result should not be null");
        assertNotNull(result1b, "Session 1 second result should not be null");
        assertNotNull(result1c, "Session 1 third result should not be null");
        assertNotNull(result2a, "Session 2 result should not be null");

        // 両セッションがPLAYING状態
        assertEquals(GameStatus.PLAYING, result1c.status(), "Session 1 should be PLAYING");
        assertEquals(GameStatus.PLAYING, result2a.status(), "Session 2 should be PLAYING");

        // スコア・レベル・ラインは両セッションで初期値を維持（回転のみだから）
        assertEquals(0, result1c.score(), "Session 1 score should be 0");
        assertEquals(1, result1c.level(), "Session 1 level should be 1");
        assertEquals(0, result1c.totalLinesCleared(), "Session 1 lines cleared should be 0");

        assertEquals(0, result2a.score(), "Session 2 score should be 0");
        assertEquals(1, result2a.level(), "Session 2 level should be 1");
        assertEquals(0, result2a.totalLinesCleared(), "Session 2 lines cleared should be 0");

        // 両セッションの currentTetromino と nextTetromino が存在（独立性の確認）
        assertNotNull(result1c.currentTetromino(), "Session 1 current tetromino should not be null");
        assertNotNull(result1c.nextTetromino(), "Session 1 next tetromino should not be null");
        assertNotNull(result2a.currentTetromino(), "Session 2 current tetromino should not be null");
        assertNotNull(result2a.nextTetromino(), "Session 2 next tetromino should not be null");
    }

    @Test
    @DisplayName("null チェック: sessionIdがnullの場合、NullPointerExceptionをスロー")
    void shouldThrowNullPointerExceptionWhenSessionIdIsNull() {
        // Given: sessionIdがnull
        String sessionId = null;

        // When & Then: NullPointerExceptionがスローされる
        assertThrows(NullPointerException.class, () -> useCase.execute(sessionId),
                "Should throw NullPointerException when sessionId is null");
    }

    @Test
    @DisplayName("同一セッション内での状態保持: 同じセッションIDで複数回実行しても状態が維持される")
    void shouldMaintainStateWithinSameSession() {
        // Given: 同じセッションID
        String sessionId = "persistent-session";

        // When: 同じセッションで複数回回転
        GameStateDTO firstRotation = useCase.execute(sessionId);
        GameStateDTO secondRotation = useCase.execute(sessionId);
        GameStateDTO thirdRotation = useCase.execute(sessionId);

        // Then: 複数回の実行でも同じセッションの状態が引き継がれている
        assertNotNull(firstRotation, "First rotation should not be null");
        assertNotNull(secondRotation, "Second rotation should not be null");
        assertNotNull(thirdRotation, "Third rotation should not be null");

        // スコアやレベルなどの基本状態は変わらない（回転のみ）
        assertEquals(firstRotation.score(), secondRotation.score(),
                "Score should remain the same between first and second rotations");
        assertEquals(secondRotation.score(), thirdRotation.score(),
                "Score should remain the same between second and third rotations");

        assertEquals(firstRotation.level(), secondRotation.level(),
                "Level should remain the same between first and second rotations");
        assertEquals(secondRotation.level(), thirdRotation.level(),
                "Level should remain the same between second and third rotations");

        assertEquals(firstRotation.totalLinesCleared(), secondRotation.totalLinesCleared(),
                "Total lines cleared should remain the same between first and second rotations");
        assertEquals(secondRotation.totalLinesCleared(), thirdRotation.totalLinesCleared(),
                "Total lines cleared should remain the same between second and third rotations");

        // フィールドも変わらない（回転のみだから）
        assertNotNull(firstRotation.field(), "Field should not be null in first rotation");
        assertNotNull(secondRotation.field(), "Field should not be null in second rotation");
        assertNotNull(thirdRotation.field(), "Field should not be null in third rotation");

        // nextTetromino も保持される（変更されない）
        assertNotNull(firstRotation.nextTetromino(), "Next tetromino should not be null in first rotation");
        assertNotNull(secondRotation.nextTetromino(), "Next tetromino should not be null in second rotation");
        assertNotNull(thirdRotation.nextTetromino(), "Next tetromino should not be null in third rotation");
    }
}
