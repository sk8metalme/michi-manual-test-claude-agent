package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.domain.Direction;
import com.example.tetris.domain.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MoveTetrominoUseCaseのテストクラス。
 *
 * <p>テトリミノの移動ユースケースをテストします。</p>
 *
 * <h3>テストケース:</h3>
 * <ul>
 *   <li>移動成功ケース（左、右、下）</li>
 *   <li>移動失敗ケース（境界外、衝突）</li>
 *   <li>セッションID管理</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class MoveTetrominoUseCaseTest {

    private MoveTetrominoUseCase moveTetrominoUseCase;
    private String testSessionId;

    @BeforeEach
    void setUp() {
        // MoveTetrominoUseCaseの実装インスタンスを作成
        moveTetrominoUseCase = new MoveTetrominoUseCaseImpl();

        // テスト用のセッションIDを生成
        testSessionId = "test-session-001";
    }

    @Test
    @DisplayName("新しいセッションでゲームを初期化し、テトリミノを左に移動できる")
    void testMoveLeft_Success() {
        // Arrange: セッションを初期化（内部でGameState.initialize()が呼ばれる想定）
        // 実際の実装では、最初の呼び出しで自動的に初期化される

        // Act: 左方向に移動
        GameStateDTO result = moveTetrominoUseCase.execute(testSessionId, Direction.LEFT);

        // Assert: ゲーム状態が返され、PLAYINGステータスであること
        assertNotNull(result, "GameStateDTOがnullであってはならない");
        assertEquals(GameStatus.PLAYING, result.status(), "ゲームステータスはPLAYINGであるべき");
        assertNotNull(result.currentTetromino(), "currentTetrominoがnullであってはならない");

        // 移動後の位置は移動前より左（x座標が小さい）であることを確認
        // 注: 初期位置はx=4、左移動後はx=3になるはず
        assertEquals(3, result.currentTetromino().x(),
                "左移動後のx座標は3であるべき（初期位置4から-1）");
    }

    @Test
    @DisplayName("テトリミノを右に移動できる")
    void testMoveRight_Success() {
        // Act: 右方向に移動
        GameStateDTO result = moveTetrominoUseCase.execute(testSessionId, Direction.RIGHT);

        // Assert
        assertNotNull(result);
        assertEquals(GameStatus.PLAYING, result.status());

        // 移動後の位置は移動前より右（x座標が大きい）であることを確認
        // 初期位置はx=4、右移動後はx=5になるはず
        assertEquals(5, result.currentTetromino().x(),
                "右移動後のx座標は5であるべき（初期位置4から+1）");
    }

    @Test
    @DisplayName("テトリミノを下に移動できる")
    void testMoveDown_Success() {
        // Act: 下方向に移動
        GameStateDTO result = moveTetrominoUseCase.execute(testSessionId, Direction.DOWN);

        // Assert
        assertNotNull(result);
        assertEquals(GameStatus.PLAYING, result.status());

        // 移動後の位置は移動前より下（y座標が大きい）であることを確認
        // 初期位置はy=0、下移動後はy=1になるはず
        assertEquals(1, result.currentTetromino().y(),
                "下移動後のy座標は1であるべき（初期位置0から+1）");
    }

    @Test
    @DisplayName("境界外への移動は無視される（左端からさらに左）")
    void testMoveLeft_OutOfBounds() {
        // Arrange: 左端に移動するまで左移動を繰り返す
        GameStateDTO current = moveTetrominoUseCase.execute(testSessionId, Direction.LEFT); // x=3
        current = moveTetrominoUseCase.execute(testSessionId, Direction.LEFT); // x=2
        current = moveTetrominoUseCase.execute(testSessionId, Direction.LEFT); // x=1
        current = moveTetrominoUseCase.execute(testSessionId, Direction.LEFT); // x=0（左端）

        int xBeforeInvalidMove = current.currentTetromino().x();

        // Act: 左端からさらに左に移動を試みる
        GameStateDTO result = moveTetrominoUseCase.execute(testSessionId, Direction.LEFT);

        // Assert: 位置が変わらないことを確認
        assertEquals(xBeforeInvalidMove, result.currentTetromino().x(),
                "境界外への移動は無視され、x座標は変わらないべき");
    }

    @Test
    @DisplayName("境界外への移動は無視される（右端からさらに右）")
    void testMoveRight_OutOfBounds() {
        // Arrange: 右端に移動するまで右移動を繰り返す
        // フィールド幅は10、テトリミノの幅を考慮すると、右端は異なる
        // I型テトリミノの場合、横向きだと幅4なので、x=6が限界
        // ただし、初期回転（DEG_0）の場合、縦向きで幅1なので、x=9が限界
        GameStateDTO current = moveTetrominoUseCase.execute(testSessionId, Direction.RIGHT); // x=5
        current = moveTetrominoUseCase.execute(testSessionId, Direction.RIGHT); // x=6
        current = moveTetrominoUseCase.execute(testSessionId, Direction.RIGHT); // x=7
        current = moveTetrominoUseCase.execute(testSessionId, Direction.RIGHT); // x=8
        current = moveTetrominoUseCase.execute(testSessionId, Direction.RIGHT); // x=9（右端）

        int xBeforeInvalidMove = current.currentTetromino().x();

        // Act: 右端からさらに右に移動を試みる
        GameStateDTO result = moveTetrominoUseCase.execute(testSessionId, Direction.RIGHT);

        // Assert: 位置が変わらないことを確認
        assertEquals(xBeforeInvalidMove, result.currentTetromino().x(),
                "境界外への移動は無視され、x座標は変わらないべき");
    }

    @Test
    @DisplayName("異なるセッションIDで独立したゲーム状態を管理できる")
    void testMultipleSessions_IndependentState() {
        // Arrange
        String sessionId1 = "session-1";
        String sessionId2 = "session-2";

        // Act: セッション1で左移動
        GameStateDTO result1 = moveTetrominoUseCase.execute(sessionId1, Direction.LEFT);

        // セッション2で右移動
        GameStateDTO result2 = moveTetrominoUseCase.execute(sessionId2, Direction.RIGHT);

        // Assert: それぞれのセッションで異なる状態が保持されることを確認
        assertEquals(3, result1.currentTetromino().x(),
                "セッション1のx座標は3（左移動）");
        assertEquals(5, result2.currentTetromino().x(),
                "セッション2のx座標は5（右移動）");
    }

    @Test
    @DisplayName("nullのセッションIDでNullPointerExceptionがスローされる")
    void testNullSessionId_ThrowsException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            moveTetrominoUseCase.execute(null, Direction.LEFT);
        }, "nullのセッションIDではNullPointerExceptionがスローされるべき");
    }

    @Test
    @DisplayName("nullのDirectionでNullPointerExceptionがスローされる")
    void testNullDirection_ThrowsException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            moveTetrominoUseCase.execute(testSessionId, null);
        }, "nullのDirectionではNullPointerExceptionがスローされるべき");
    }
}
