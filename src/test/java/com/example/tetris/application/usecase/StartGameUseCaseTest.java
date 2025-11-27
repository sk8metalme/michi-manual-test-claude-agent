package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.dto.TetrominoDTO;
import com.example.tetris.domain.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StartGameUseCaseの単体テストクラス。
 *
 * <p>このテストは、ゲーム開始ユースケースの動作を検証します。</p>
 *
 * <h3>テスト対象:</h3>
 * <ul>
 *   <li>startGame()メソッドの初期化処理</li>
 *   <li>返されるGameStateDTOの各フィールド値</li>
 *   <li>テトリミノの生成</li>
 *   <li>フィールドの初期状態</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class StartGameUseCaseTest {

    private StartGameUseCase useCase;

    @BeforeEach
    void setUp() {
        // 実装クラスをインスタンス化
        useCase = new StartGameUseCaseImpl();
    }

    /**
     * startGame()が非nullのGameStateDTOを返すことを確認。
     */
    @Test
    void testStartGame_ReturnsNonNullGameStateDTO() {
        // When
        GameStateDTO result = useCase.startGame();

        // Then
        assertNotNull(result, "startGame()はnullを返してはいけない");
    }

    /**
     * 初期状態のstatusがPLAYINGであることを確認。
     */
    @Test
    void testStartGame_InitialStatusIsPlaying() {
        // When
        GameStateDTO result = useCase.startGame();

        // Then
        assertEquals(GameStatus.PLAYING, result.status(),
                "初期状態のstatusはPLAYINGであるべき");
    }

    /**
     * currentTetrominoが正しく設定されていることを確認。
     */
    @Test
    void testStartGame_CurrentTetrominoIsSet() {
        // When
        GameStateDTO result = useCase.startGame();

        // Then
        assertNotNull(result.currentTetromino(),
                "currentTetrominoはnullであってはいけない");
        TetrominoDTO current = result.currentTetromino();
        assertNotNull(current.type(), "テトリミノのtypeが設定されているべき");
        assertEquals(4, current.x(), "初期X座標は4であるべき");
        assertEquals(0, current.y(), "初期Y座標は0であるべき");
        assertEquals(0, current.rotation(), "初期回転は0度であるべき");
    }

    /**
     * nextTetrominoが正しく設定されていることを確認。
     */
    @Test
    void testStartGame_NextTetrominoIsSet() {
        // When
        GameStateDTO result = useCase.startGame();

        // Then
        assertNotNull(result.nextTetromino(),
                "nextTetrominoはnullであってはいけない");
        TetrominoDTO next = result.nextTetromino();
        assertNotNull(next.type(), "次のテトリミノのtypeが設定されているべき");
        assertEquals(4, next.x(), "次のテトリミノの初期X座標は4であるべき");
        assertEquals(0, next.y(), "次のテトリミノの初期Y座標は0であるべき");
        assertEquals(0, next.rotation(), "次のテトリミノの初期回転は0度であるべき");
    }

    /**
     * フィールドが正しく初期化されていることを確認（20行×10列、すべてnull）。
     */
    @Test
    void testStartGame_FieldIsInitializedAsEmpty() {
        // When
        GameStateDTO result = useCase.startGame();

        // Then
        assertNotNull(result.field(), "fieldはnullであってはいけない");
        assertEquals(20, result.field().length, "フィールドは20行であるべき");
        assertEquals(10, result.field()[0].length, "フィールドは10列であるべき");

        // すべてのセルがnullであることを確認
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 10; x++) {
                assertNull(result.field()[y][x],
                        "初期状態のフィールド[" + y + "][" + x + "]はnullであるべき");
            }
        }
    }

    /**
     * 初期スコアが0であることを確認。
     */
    @Test
    void testStartGame_InitialScoreIsZero() {
        // When
        GameStateDTO result = useCase.startGame();

        // Then
        assertEquals(0, result.score(), "初期スコアは0であるべき");
    }

    /**
     * 初期レベルが1であることを確認。
     */
    @Test
    void testStartGame_InitialLevelIsOne() {
        // When
        GameStateDTO result = useCase.startGame();

        // Then
        assertEquals(1, result.level(), "初期レベルは1であるべき");
    }

    /**
     * 初期の累計クリア済みライン数が0であることを確認。
     */
    @Test
    void testStartGame_InitialTotalLinesClearedIsZero() {
        // When
        GameStateDTO result = useCase.startGame();

        // Then
        assertEquals(0, result.totalLinesCleared(),
                "初期の累計クリア済みライン数は0であるべき");
    }
}
