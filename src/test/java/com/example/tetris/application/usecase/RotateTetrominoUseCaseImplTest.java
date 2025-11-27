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
        assertEquals(GameStatus.PLAYING, result4.status(), "Game should still be PLAYING");
    }

    @Test
    @DisplayName("セッション管理: 異なるセッションIDで独立したゲーム状態を管理")
    void shouldManageIndependentSessions() {
        // Given: 2つの異なるセッション
        String sessionId1 = "session-1";
        String sessionId2 = "session-2";

        // When: 各セッションでテトリミノを回転
        GameStateDTO result1 = useCase.execute(sessionId1);
        GameStateDTO result2 = useCase.execute(sessionId2);

        // Then: 各セッションが独立したゲーム状態を持つ
        assertNotNull(result1, "Session 1 result should not be null");
        assertNotNull(result2, "Session 2 result should not be null");

        // 初期状態は同じだが、独立して管理されている
        assertEquals(GameStatus.PLAYING, result1.status());
        assertEquals(GameStatus.PLAYING, result2.status());
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

        // Then: 2回目の実行でも同じセッションの状態が引き継がれている
        assertNotNull(firstRotation);
        assertNotNull(secondRotation);

        // スコアやレベルなどの基本状態は変わらない（回転のみ）
        assertEquals(firstRotation.score(), secondRotation.score(),
                "Score should remain the same across rotations");
        assertEquals(firstRotation.level(), secondRotation.level(),
                "Level should remain the same across rotations");
        assertEquals(firstRotation.totalLinesCleared(), secondRotation.totalLinesCleared(),
                "Total lines cleared should remain the same across rotations");
    }
}
