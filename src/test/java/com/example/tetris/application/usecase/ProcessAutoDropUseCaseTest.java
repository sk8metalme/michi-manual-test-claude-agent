package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.domain.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ProcessAutoDropUseCaseのテストクラス。
 *
 * <p>テトリミノの自動落下ユースケースをテストします。</p>
 *
 * <h3>テストケース:</h3>
 * <ul>
 *   <li>自動落下成功ケース（テトリミノが1マス下に移動）</li>
 *   <li>テトリミノ固定とライン消去（底まで落下した場合）</li>
 *   <li>スコア加算の確認</li>
 *   <li>レベル更新の確認</li>
 *   <li>セッションID管理</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 */
class ProcessAutoDropUseCaseTest {

    private ProcessAutoDropUseCase processAutoDropUseCase;
    private String testSessionId;

    @BeforeEach
    void setUp() {
        // ProcessAutoDropUseCaseの実装インスタンスを作成
        processAutoDropUseCase = new ProcessAutoDropUseCaseImpl();

        // テスト用のセッションIDを生成
        testSessionId = "test-session-auto-drop-001";
    }

    @Test
    @DisplayName("新しいセッションでゲームを初期化し、自動落下でテトリミノが1マス下に移動する")
    void testAutoDropTick_Success_MoveDown() {
        // Arrange: セッションを初期化（内部でGameState.initialize()が呼ばれる想定）
        // 最初の自動落下呼び出しで自動的に初期化される

        // Act: 自動落下を実行
        GameStateDTO result = processAutoDropUseCase.execute(testSessionId);

        // Assert: ゲーム状態が返され、PLAYINGステータスであること
        assertNotNull(result, "GameStateDTOがnullであってはならない");
        assertEquals(GameStatus.PLAYING, result.status(), "ゲームステータスはPLAYINGであるべき");
        assertNotNull(result.currentTetromino(), "currentTetrominoがnullであってはならない");

        // 自動落下後の位置は初期位置より下（y座標が大きい）であることを確認
        // 初期位置はy=0、自動落下後はy=1になるはず
        assertEquals(1, result.currentTetromino().y(),
                "自動落下後のy座標は1であるべき（初期位置0から+1）");
    }

    @Test
    @DisplayName("自動落下を複数回実行すると、テトリミノが段階的に下に移動する")
    void testAutoDropTick_MultipleTimes_GradualDescent() {
        // Act: 自動落下を3回実行
        GameStateDTO result1 = processAutoDropUseCase.execute(testSessionId); // y=1
        GameStateDTO result2 = processAutoDropUseCase.execute(testSessionId); // y=2
        GameStateDTO result3 = processAutoDropUseCase.execute(testSessionId); // y=3

        // Assert: y座標が段階的に増加することを確認
        assertEquals(1, result1.currentTetromino().y(),
                "1回目の自動落下後、y座標は1であるべき");
        assertEquals(2, result2.currentTetromino().y(),
                "2回目の自動落下後、y座標は2であるべき");
        assertEquals(3, result3.currentTetromino().y(),
                "3回目の自動落下後、y座標は3であるべき");
    }

    @Test
    @DisplayName("底まで落下すると、テトリミノが固定され新しいテトリミノが生成される")
    void testAutoDropTick_ReachBottom_FixAndSpawnNew() {
        // Arrange: 初回の自動落下を実行
        processAutoDropUseCase.execute(testSessionId);

        // 20回以上自動落下を繰り返す（テトリミノを底まで落とす）
        // フィールドの高さは20、テトリミノの初期y位置は0
        GameStateDTO current = null;
        for (int i = 0; i < 25; i++) {
            current = processAutoDropUseCase.execute(testSessionId);
        }

        // Assert: フィールドにブロックが配置されていることを確認
        // テトリミノが固定されたら、フィールドのどこかにブロックがあるはず
        boolean hasBlockInField = false;
        String[][] field = current.field();
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length; x++) {
                if (field[y][x] != null) {
                    hasBlockInField = true;
                    break;
                }
            }
            if (hasBlockInField) {
                break;
            }
        }

        assertTrue(hasBlockInField,
                "底まで落下後、フィールドにブロックが配置されるべき");

        // スコアが0以上であることを確認
        assertTrue(current.score() >= 0,
                "スコアは0以上であるべき");
    }

    @Test
    @DisplayName("異なるセッションIDで独立したゲーム状態を管理できる")
    void testMultipleSessions_IndependentState() {
        // Arrange
        String sessionId1 = "session-auto-drop-1";
        String sessionId2 = "session-auto-drop-2";

        // Act: セッション1で自動落下を1回
        GameStateDTO result1 = processAutoDropUseCase.execute(sessionId1);

        // セッション2で自動落下を2回
        processAutoDropUseCase.execute(sessionId2);
        GameStateDTO result2b = processAutoDropUseCase.execute(sessionId2);

        // Assert: それぞれのセッションで異なる状態が保持されることを確認
        assertEquals(1, result1.currentTetromino().y(),
                "セッション1のy座標は1（1回自動落下）");
        assertEquals(2, result2b.currentTetromino().y(),
                "セッション2のy座標は2（2回自動落下）");
    }

    @Test
    @DisplayName("nullのセッションIDでNullPointerExceptionがスローされる")
    void testNullSessionId_ThrowsException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            processAutoDropUseCase.execute(null);
        }, "nullのセッションIDではNullPointerExceptionがスローされるべき");
    }

    @Test
    @DisplayName("自動落下でテトリミノが固定された後、フィールドにブロックが配置される")
    void testAutoDropTick_FixTetromino_FieldUpdated() {
        // Arrange: 底まで落下させて固定
        GameStateDTO current = processAutoDropUseCase.execute(testSessionId);

        // 底まで自動落下を繰り返す
        for (int i = 0; i < 25; i++) {
            current = processAutoDropUseCase.execute(testSessionId);
        }

        // Assert: フィールドにブロックが配置されていることを確認
        // フィールドの最下部（y=19）にブロックがあるはず
        boolean hasBlockAtBottom = false;
        String[][] field = current.field();
        for (int x = 0; x < 10; x++) {
            if (field[19][x] != null) {
                hasBlockAtBottom = true;
                break;
            }
        }

        assertTrue(hasBlockAtBottom,
                "テトリミノが固定された後、フィールドの最下部にブロックが配置されるべき");
    }

    @Test
    @DisplayName("自動落下処理後もゲームステータスが正しく維持される")
    void testAutoDropTick_GameStatusMaintained() {
        // Act: 自動落下を実行
        GameStateDTO result = processAutoDropUseCase.execute(testSessionId);

        // Assert: ゲームステータスがPLAYINGであることを確認
        assertEquals(GameStatus.PLAYING, result.status(),
                "自動落下後もゲームステータスはPLAYINGであるべき");
    }

    @Test
    @DisplayName("レベルと累計クリア済みライン数が初期状態から変化する可能性を確認")
    void testAutoDropTick_LevelAndTotalLinesClearedInitialState() {
        // Act: 自動落下を1回実行
        GameStateDTO result = processAutoDropUseCase.execute(testSessionId);

        // Assert: 初期状態の確認
        assertEquals(1, result.level(),
                "初期レベルは1であるべき");
        assertEquals(0, result.totalLinesCleared(),
                "初期状態では累計クリア済みライン数は0であるべき");
    }

    @Test
    @DisplayName("removeSession()でセッションが正常に削除される")
    void testRemoveSession_Success() {
        // Arrange: セッションを作成
        processAutoDropUseCase.execute(testSessionId);
        processAutoDropUseCase.execute(testSessionId); // y=2まで移動

        // Act: セッションを削除
        processAutoDropUseCase.removeSession(testSessionId);

        // 再度同じセッションIDで実行すると、新しいセッションとして初期化される
        GameStateDTO result = processAutoDropUseCase.execute(testSessionId);

        // Assert: 新しいセッションとして初期化されている（y座標が1）
        assertEquals(1, result.currentTetromino().y(),
                "セッション削除後、再初期化されてy座標は1であるべき");
    }

    @Test
    @DisplayName("removeSession()にnullを渡すとNullPointerExceptionがスローされる")
    void testRemoveSession_NullSessionId_ThrowsException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            processAutoDropUseCase.removeSession(null);
        }, "nullのセッションIDではNullPointerExceptionがスローされるべき");
    }

    @Test
    @DisplayName("空文字列のセッションIDでIllegalArgumentExceptionがスローされる")
    void testExecute_EmptySessionId_ThrowsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            processAutoDropUseCase.execute("");
        }, "空文字列のセッションIDではIllegalArgumentExceptionがスローされるべき");

        // 空白文字のみのセッションIDもエラー
        assertThrows(IllegalArgumentException.class, () -> {
            processAutoDropUseCase.execute("   ");
        }, "空白文字のみのセッションIDではIllegalArgumentExceptionがスローされるべき");
    }

    @Test
    @DisplayName("256文字を超えるセッションIDでIllegalArgumentExceptionがスローされる")
    void testExecute_TooLongSessionId_ThrowsException() {
        // Arrange: 257文字のセッションIDを作成
        String longSessionId = "a".repeat(257);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            processAutoDropUseCase.execute(longSessionId);
        }, "256文字を超えるセッションIDではIllegalArgumentExceptionがスローされるべき");
    }

    @Test
    @DisplayName("最大セッション数に到達するとIllegalStateExceptionがスローされる")
    void testExecute_MaxSessionsReached_ThrowsException() {
        // Arrange: 10000個のセッションを作成（MAX_SESSIONS = 10000）
        for (int i = 0; i < 10000; i++) {
            processAutoDropUseCase.execute("session-" + i);
        }

        // Act & Assert: 10001個目のセッションを作成しようとするとエラー
        assertThrows(IllegalStateException.class, () -> {
            processAutoDropUseCase.execute("session-10001");
        }, "最大セッション数到達時にはIllegalStateExceptionがスローされるべき");
    }
}
