package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link GameState}クラスの単体テスト。
 *
 * <p>このテストクラスは、GameStateの初期化、移動、回転、ドロップ、
 * スコア計算、ゲームオーバー判定を検証します。</p>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class GameStateTest {

    /**
     * GameStateのコンストラクタが正しく動作することを検証します。
     */
    @Test
    void testConstructor() {
        // Arrange
        GameStatus status = GameStatus.PLAYING;
        Tetromino currentTetromino = new Tetromino(TetrominoType.I, new Position(4, 0), Rotation.DEG_0);
        Tetromino nextTetromino = new Tetromino(TetrominoType.O, new Position(4, 0), Rotation.DEG_0);
        GameField field = GameField.createEmpty();
        int score = 0;
        int level = 1;
        int totalLinesCleared = 0;

        // Act
        GameState gameState = new GameState(
                status,
                currentTetromino,
                nextTetromino,
                field,
                score,
                level,
                totalLinesCleared
        );

        // Assert
        assertNotNull(gameState, "GameStateはnullであってはならない");
        assertEquals(status, gameState.status(), "statusは正しく設定されるべき");
        assertEquals(currentTetromino, gameState.currentTetromino(), "currentTetrominoは正しく設定されるべき");
        assertEquals(nextTetromino, gameState.nextTetromino(), "nextTetrominoは正しく設定されるべき");
        assertEquals(field, gameState.field(), "fieldは正しく設定されるべき");
        assertEquals(score, gameState.score(), "scoreは正しく設定されるべき");
        assertEquals(level, gameState.level(), "levelは正しく設定されるべき");
        assertEquals(totalLinesCleared, gameState.totalLinesCleared(),
                "totalLinesClearedは正しく設定されるべき");
    }

    /**
     * GameStateのフィールドがnullでないことを検証します。
     */
    @Test
    void testFieldsNotNull() {
        // Arrange
        GameState gameState = new GameState(
                GameStatus.PLAYING,
                new Tetromino(TetrominoType.T, new Position(4, 0), Rotation.DEG_0),
                new Tetromino(TetrominoType.Z, new Position(4, 0), Rotation.DEG_0),
                GameField.createEmpty(),
                0,
                1,
                0
        );

        // Assert
        assertNotNull(gameState.status(), "statusはnullであってはならない");
        assertNotNull(gameState.currentTetromino(), "currentTetrominoはnullであってはならない");
        assertNotNull(gameState.nextTetromino(), "nextTetrominoはnullであってはならない");
        assertNotNull(gameState.field(), "fieldはnullであってはならない");
    }

    /**
     * GameStateのスコア、レベル、クリア済みライン数が非負であることを検証します。
     */
    @Test
    void testNumericFieldsNonNegative() {
        // Arrange
        GameState gameState = new GameState(
                GameStatus.PLAYING,
                new Tetromino(TetrominoType.L, new Position(4, 0), Rotation.DEG_0),
                new Tetromino(TetrominoType.J, new Position(4, 0), Rotation.DEG_0),
                GameField.createEmpty(),
                100,
                3,
                10
        );

        // Assert
        assertTrue(gameState.score() >= 0, "scoreは非負であるべき");
        assertTrue(gameState.level() >= 1, "levelは1以上であるべき");
        assertTrue(gameState.totalLinesCleared() >= 0, "totalLinesClearedは非負であるべき");
    }

    /**
     * initialize()メソッドが初期ゲーム状態を正しく生成することを検証します。
     */
    @Test
    void testInitialize() {
        // Act
        GameState gameState = GameState.initialize();

        // Assert
        assertNotNull(gameState, "初期化されたGameStateはnullであってはならない");
        assertEquals(GameStatus.PLAYING, gameState.status(), "初期状態はPLAYINGであるべき");
        assertNotNull(gameState.currentTetromino(), "currentTetrominoはnullであってはならない");
        assertNotNull(gameState.nextTetromino(), "nextTetrominoはnullであってはならない");
        assertNotNull(gameState.field(), "fieldはnullであってはならない");
        assertEquals(0, gameState.score(), "初期スコアは0であるべき");
        assertEquals(1, gameState.level(), "初期レベルは1であるべき");
        assertEquals(0, gameState.totalLinesCleared(), "初期クリアライン数は0であるべき");
    }

    /**
     * initialize()メソッドが空のフィールドを生成することを検証します。
     */
    @Test
    void testInitialize_EmptyField() {
        // Act
        GameState gameState = GameState.initialize();

        // Assert
        GameField field = gameState.field();
        for (int y = 0; y < GameField.HEIGHT; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                assertNull(field.grid()[y][x],
                        "初期フィールドのすべてのセルは空（null）であるべき");
            }
        }
    }

    /**
     * initialize()メソッドが生成するテトリミノが初期位置にあることを検証します。
     */
    @Test
    void testInitialize_TetrominoPosition() {
        // Act
        GameState gameState = GameState.initialize();

        // Assert
        Tetromino currentTetromino = gameState.currentTetromino();
        Tetromino nextTetromino = gameState.nextTetromino();

        // テトリミノの初期位置を確認（x=4, y=0 が標準的な出現位置）
        assertEquals(4, currentTetromino.position().x(),
                "currentTetrominoのx座標は4であるべき");
        assertEquals(0, currentTetromino.position().y(),
                "currentTetrominoのy座標は0であるべき");
        assertEquals(4, nextTetromino.position().x(),
                "nextTetrominoのx座標は4であるべき");
        assertEquals(0, nextTetromino.position().y(),
                "nextTetrominoのy座標は0であるべき");

        // 初期回転はDEG_0
        assertEquals(Rotation.DEG_0, currentTetromino.rotation(),
                "currentTetrominoの初期回転はDEG_0であるべき");
        assertEquals(Rotation.DEG_0, nextTetromino.rotation(),
                "nextTetrominoの初期回転はDEG_0であるべき");
    }

    /**
     * moveTetromino()メソッドで左移動が正しく実行されることを検証します。
     */
    @Test
    void testMoveTetromino_Left() {
        // Arrange
        GameState gameState = GameState.initialize();
        int originalX = gameState.currentTetromino().position().x();

        // Act
        GameState movedState = gameState.moveTetromino(Direction.LEFT);

        // Assert
        assertNotNull(movedState, "移動後のGameStateはnullであってはならない");
        assertEquals(originalX - 1, movedState.currentTetromino().position().x(),
                "左移動後のx座標は1減少するべき");
        assertEquals(gameState.currentTetromino().position().y(),
                movedState.currentTetromino().position().y(),
                "y座標は変わらないべき");
    }

    /**
     * moveTetromino()メソッドで右移動が正しく実行されることを検証します。
     */
    @Test
    void testMoveTetromino_Right() {
        // Arrange
        GameState gameState = GameState.initialize();
        int originalX = gameState.currentTetromino().position().x();

        // Act
        GameState movedState = gameState.moveTetromino(Direction.RIGHT);

        // Assert
        assertNotNull(movedState, "移動後のGameStateはnullであってはならない");
        assertEquals(originalX + 1, movedState.currentTetromino().position().x(),
                "右移動後のx座標は1増加するべき");
        assertEquals(gameState.currentTetromino().position().y(),
                movedState.currentTetromino().position().y(),
                "y座標は変わらないべき");
    }

    /**
     * moveTetromino()メソッドで下移動が正しく実行されることを検証します。
     */
    @Test
    void testMoveTetromino_Down() {
        // Arrange
        GameState gameState = GameState.initialize();
        int originalY = gameState.currentTetromino().position().y();

        // Act
        GameState movedState = gameState.moveTetromino(Direction.DOWN);

        // Assert
        assertNotNull(movedState, "移動後のGameStateはnullであってはならない");
        assertEquals(gameState.currentTetromino().position().x(),
                movedState.currentTetromino().position().x(),
                "x座標は変わらないべき");
        assertEquals(originalY + 1, movedState.currentTetromino().position().y(),
                "下移動後のy座標は1増加するべき");
    }

    /**
     * moveTetromino()メソッドで左境界外への移動がブロックされることを検証します。
     */
    @Test
    void testMoveTetromino_LeftBoundary() {
        // Arrange - テトリミノを左端に配置
        GameState gameState = new GameState(
                GameStatus.PLAYING,
                new Tetromino(TetrominoType.I, new Position(0, 5), Rotation.DEG_0),
                new Tetromino(TetrominoType.O, new Position(4, 0), Rotation.DEG_0),
                GameField.createEmpty(),
                0,
                1,
                0
        );

        // Act
        GameState movedState = gameState.moveTetromino(Direction.LEFT);

        // Assert - 移動できない場合は元の状態を返す
        assertEquals(gameState, movedState,
                "左境界外への移動は元のGameStateを返すべき");
    }

    /**
     * moveTetromino()メソッドで衝突時に移動がブロックされることを検証します。
     */
    @Test
    void testMoveTetromino_Collision() {
        // Arrange - フィールドにブロックを配置
        Block[][] grid = new Block[GameField.HEIGHT][GameField.WIDTH];
        grid[10][3] = new Block(TetrominoType.T);  // 座標(3, 10)にブロック
        GameField fieldWithBlock = new GameField(grid);

        GameState gameState = new GameState(
                GameStatus.PLAYING,
                new Tetromino(TetrominoType.O, new Position(4, 9), Rotation.DEG_0),
                new Tetromino(TetrominoType.I, new Position(4, 0), Rotation.DEG_0),
                fieldWithBlock,
                0,
                1,
                0
        );

        // Act
        GameState movedState = gameState.moveTetromino(Direction.LEFT);

        // Assert - 衝突する場合は元の状態を返す
        assertEquals(gameState, movedState,
                "衝突時の移動は元のGameStateを返すべき");
    }

    /**
     * rotateTetromino()メソッドで回転が正しく実行されることを検証します。
     */
    @Test
    void testRotateTetromino() {
        // Arrange
        GameState gameState = new GameState(
                GameStatus.PLAYING,
                new Tetromino(TetrominoType.T, new Position(5, 5), Rotation.DEG_0),
                new Tetromino(TetrominoType.O, new Position(4, 0), Rotation.DEG_0),
                GameField.createEmpty(),
                0,
                1,
                0
        );

        // Act
        GameState rotatedState = gameState.rotateTetromino();

        // Assert
        assertNotNull(rotatedState, "回転後のGameStateはnullであってはならない");
        assertEquals(Rotation.DEG_90, rotatedState.currentTetromino().rotation(),
                "回転後の回転状態はDEG_90であるべき");
        assertEquals(gameState.currentTetromino().position(),
                rotatedState.currentTetromino().position(),
                "位置は変わらないべき");
    }

    /**
     * rotateTetromino()メソッドで複数回回転が正しく実行されることを検証します。
     */
    @Test
    void testRotateTetromino_Multiple() {
        // Arrange
        GameState gameState = new GameState(
                GameStatus.PLAYING,
                new Tetromino(TetrominoType.I, new Position(5, 5), Rotation.DEG_0),
                new Tetromino(TetrominoType.O, new Position(4, 0), Rotation.DEG_0),
                GameField.createEmpty(),
                0,
                1,
                0
        );

        // Act - 4回回転して元に戻る
        GameState rotated1 = gameState.rotateTetromino();
        GameState rotated2 = rotated1.rotateTetromino();
        GameState rotated3 = rotated2.rotateTetromino();
        GameState rotated4 = rotated3.rotateTetromino();

        // Assert
        assertEquals(Rotation.DEG_90, rotated1.currentTetromino().rotation(), "1回目: DEG_90");
        assertEquals(Rotation.DEG_180, rotated2.currentTetromino().rotation(), "2回目: DEG_180");
        assertEquals(Rotation.DEG_270, rotated3.currentTetromino().rotation(), "3回目: DEG_270");
        assertEquals(Rotation.DEG_0, rotated4.currentTetromino().rotation(), "4回目: DEG_0（元に戻る）");
    }

    /**
     * rotateTetromino()メソッドで衝突時に回転がブロックされることを検証します。
     */
    @Test
    void testRotateTetromino_Collision() {
        // Arrange - フィールドにブロックを配置して回転をブロック
        // I型テトリミノがPosition(5,5)でDEG_0→DEG_90に回転すると、x=5の列（y=4,5,6,7）を占有
        // そのため、x=5の列にブロックを配置すれば回転をブロックできる
        Block[][] grid = new Block[GameField.HEIGHT][GameField.WIDTH];
        grid[6][5] = new Block(TetrominoType.T);  // y=6, x=5 の位置にブロック（回転先と衝突）
        GameField fieldWithBlock = new GameField(grid);

        GameState gameState = new GameState(
                GameStatus.PLAYING,
                new Tetromino(TetrominoType.I, new Position(5, 5), Rotation.DEG_0),
                new Tetromino(TetrominoType.O, new Position(4, 0), Rotation.DEG_0),
                fieldWithBlock,
                0,
                1,
                0
        );

        // Act
        GameState rotatedState = gameState.rotateTetromino();

        // Assert - 回転できない場合は元の状態を返す
        assertEquals(gameState, rotatedState,
                "衝突時の回転は元のGameStateを返すべき");
    }

    /**
     * hardDrop()メソッドでテトリミノが最下部まで落下して固定されることを検証します。
     */
    @Test
    void testHardDrop_BasicDrop() {
        // Arrange - 明示的に異なるタイプのテトリミノを使用（ランダム性を排除）
        Tetromino iTetromino = new Tetromino(TetrominoType.I, new Position(4, 0), Rotation.DEG_0);
        Tetromino oTetromino = new Tetromino(TetrominoType.O, new Position(4, 0), Rotation.DEG_0);

        GameState gameState = new GameState(
                GameStatus.PLAYING,
                iTetromino,  // I型をcurrentに
                oTetromino,  // O型をnextに（異なるタイプを保証）
                GameField.createEmpty(),
                0,
                1,
                0
        );

        // Act
        GameState droppedState = gameState.hardDrop();

        // Assert
        assertNotNull(droppedState, "ハードドロップ後のGameStateはnullであってはならない");
        assertNotEquals(gameState.currentTetromino(), droppedState.currentTetromino(),
                "currentTetrominoは新しいテトリミノに変わるべき");
        assertEquals(gameState.nextTetromino(), droppedState.currentTetromino(),
                "nextTetrominoがcurrentTetrominoになるべき");
    }

    /**
     * hardDrop()メソッドでライン消去が正しく実行されることを検証します。
     */
    @Test
    void testHardDrop_LineClear() {
        // Arrange - 最下行を1ブロック以外埋める
        Block[][] grid = new Block[GameField.HEIGHT][GameField.WIDTH];
        for (int x = 0; x < GameField.WIDTH - 1; x++) {  // x=0-8 を埋める（x=9は空）
            grid[19][x] = new Block(TetrominoType.T);
        }
        GameField fieldNearlyFull = new GameField(grid);

        // I型テトリミノを最下行の空いている位置に配置
        GameState gameState = new GameState(
                GameStatus.PLAYING,
                new Tetromino(TetrominoType.I, new Position(9, 16), Rotation.DEG_90),  // 縦向きで配置
                new Tetromino(TetrominoType.O, new Position(4, 0), Rotation.DEG_0),
                fieldNearlyFull,
                0,
                1,
                0
        );

        // Act
        GameState droppedState = gameState.hardDrop();

        // Assert
        assertEquals(1, droppedState.totalLinesCleared(), "1ライン消去されるべき");
        assertTrue(droppedState.score() > 0, "スコアが加算されるべき");
    }

    /**
     * processAutoDropTick()メソッドでテトリミノが1マス下に移動することを検証します。
     */
    @Test
    void testProcessAutoDropTick() {
        // Arrange
        GameState gameState = GameState.initialize();
        int originalY = gameState.currentTetromino().position().y();

        // Act
        GameState tickedState = gameState.processAutoDropTick();

        // Assert
        assertEquals(originalY + 1, tickedState.currentTetromino().position().y(),
                "自動落下後のy座標は1増加するべき");
    }

    /**
     * getDropInterval()メソッドがレベルに応じた間隔を返すことを検証します。
     */
    @Test
    void testGetDropInterval() {
        // Arrange
        GameState level1State = new GameState(
                GameStatus.PLAYING,
                new Tetromino(TetrominoType.I, new Position(4, 0), Rotation.DEG_0),
                new Tetromino(TetrominoType.O, new Position(4, 0), Rotation.DEG_0),
                GameField.createEmpty(),
                0,
                1,
                0
        );

        GameState level5State = new GameState(
                GameStatus.PLAYING,
                new Tetromino(TetrominoType.I, new Position(4, 0), Rotation.DEG_0),
                new Tetromino(TetrominoType.O, new Position(4, 0), Rotation.DEG_0),
                GameField.createEmpty(),
                0,
                5,
                0
        );

        // Act & Assert
        assertTrue(level1State.getDropInterval() > level5State.getDropInterval(),
                "高いレベルほど落下間隔が短くなるべき");
    }

    /**
     * hardDrop()メソッドが、次のテトリミノがスポーン位置に配置できない場合に
     * ゲームオーバーになることを検証します。
     */
    @Test
    void testHardDrop_GameOver_WhenNextTetrominoCannotBePlaced() {
        // Arrange: フィールドの上部を埋めて、次のテトリミノがスポーン位置に配置できない状況を作る
        GameField emptyField = GameField.createEmpty();
        Block[][] grid = emptyField.grid();

        // y=0-3の行を埋める（I-tetrominoの縦配置がスポーン位置に入らないようにする）
        // スポーン位置は (4, 0) なので、x=3-6、y=0-3 を埋める
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x <= 6; x++) {
                grid[y][x] = new Block(TetrominoType.T);
            }
        }
        GameField almostFullField = new GameField(grid);

        // 現在のテトリミノはフィールドの下部に配置（hardDropで即座に固定される）
        Tetromino currentTetromino = new Tetromino(
                TetrominoType.O,
                new Position(0, 18),  // 左下隅に配置
                Rotation.DEG_0
        );

        // 次のテトリミノ（I-tetromino縦配置、スポーン位置に配置できない）
        Tetromino nextTetromino = new Tetromino(
                TetrominoType.I,
                new Position(4, 0),  // スポーン位置
                Rotation.DEG_90  // 縦配置（y=0-3を占有）
        );

        GameState gameState = new GameState(
                GameStatus.PLAYING,
                currentTetromino,
                nextTetromino,
                almostFullField,
                0,
                1,
                0
        );

        // Act
        GameState result = gameState.hardDrop();

        // Assert
        assertEquals(GameStatus.GAME_OVER, result.status(),
                "次のテトリミノがスポーン位置に配置できない場合、ゲームオーバーになるべき");
        assertEquals(nextTetromino, result.currentTetromino(),
                "ゲームオーバーでも次のテトリミノはcurrentTetrominoに昇格するべき");
    }

    /**
     * ライン消去後にレベルが正しく更新されることを検証します。
     *
     * <p>このテストは、10ライン消去後にレベルが1から2に上がることを確認します。
     * また、レベル上昇により落下間隔が短くなることも検証します。</p>
     */
    @Test
    void testHardDrop_LevelIncreases_After10LinesCleared() {
        // Arrange: 1ライン分のブロックがあるフィールドを作成（簡単なテストケース）
        Block[][] grid = new Block[GameField.HEIGHT][GameField.WIDTH];

        // 最下行を埋める（I型テトリミノの4マス分だけ空ける）
        for (int x = 0; x < GameField.WIDTH; x++) {
            if (x < 4) {
                grid[GameField.HEIGHT - 1][x] = null;  // 左4マスは空ける
            } else {
                grid[GameField.HEIGHT - 1][x] = new Block(TetrominoType.O);  // 残りは埋める
            }
        }

        GameField fieldWith1LineAlmostFull = new GameField(grid);

        // I型テトリミノ（横向き）を上部に配置
        Tetromino iTetromino = new Tetromino(
                TetrominoType.I,
                new Position(1, 0),  // 上部中央
                Rotation.DEG_0  // 横向き
        );

        Tetromino nextTetromino = new Tetromino(
                TetrominoType.O,
                new Position(4, 0),
                Rotation.DEG_0
        );

        // 既に9ライン消去済みの状態でゲームを開始
        GameState gameState = new GameState(
                GameStatus.PLAYING,
                iTetromino,
                nextTetromino,
                fieldWith1LineAlmostFull,
                0,
                1,  // 初期レベル1
                9   // 既に9ライン消去済み
        );

        // 初期状態の確認
        assertEquals(1, gameState.level(), "初期レベルは1であるべき");
        assertEquals(1000, gameState.getDropInterval(), "レベル1の落下間隔は1000msであるべき");

        // Act: hardDropを実行（1行消去 → 累計10行 → レベル2になるはず）
        GameState result = gameState.hardDrop();

        // Assert: レベルが2に上がることを確認
        assertEquals(2, result.level(), "10ライン消去後はレベル2になるべき");
        assertEquals(10, result.totalLinesCleared(), "累計10ライン消去されているべき");
        assertEquals(900, result.getDropInterval(), "レベル2の落下間隔は900msであるべき");

        // スコアも正しく加算されることを確認（1行消去 = 100点）
        assertEquals(100, result.score(), "1行消去で100点獲得するべき");
    }
}
