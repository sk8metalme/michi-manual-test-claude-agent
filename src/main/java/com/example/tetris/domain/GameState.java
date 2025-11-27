package com.example.tetris.domain;

import java.util.Random;

/**
 * ゲームの状態全体を管理するアグリゲートルート。
 *
 * <p>GameStateは、テトリスゲームの全体的な状態（現在のテトリミノ、
 * 次のテトリミノ、フィールド、スコア、レベル、クリア済みライン数）を保持し、
 * ゲームロジックの中心的な役割を果たします。</p>
 *
 * <h3>フィールド：</h3>
 * <ul>
 *   <li>status: ゲームの進行状態（PLAYING, GAME_OVER）</li>
 *   <li>currentTetromino: 現在操作中のテトリミノ</li>
 *   <li>nextTetromino: 次に落下するテトリミノ</li>
 *   <li>field: ゲームフィールド（10×20）</li>
 *   <li>score: 現在のスコア</li>
 *   <li>level: 現在のレベル</li>
 *   <li>totalLinesCleared: 累計クリア済みライン数</li>
 * </ul>
 *
 * <h3>不変性（Immutability）：</h3>
 * <p>GameStateは不変オブジェクトです。状態の変更は新しいGameStateインスタンスを返します。</p>
 *
 * @param status ゲームの進行状態
 * @param currentTetromino 現在操作中のテトリミノ
 * @param nextTetromino 次に落下するテトリミノ
 * @param field ゲームフィールド
 * @param score 現在のスコア
 * @param level 現在のレベル
 * @param totalLinesCleared 累計クリア済みライン数
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public record GameState(
        GameStatus status,
        Tetromino currentTetromino,
        Tetromino nextTetromino,
        GameField field,
        int score,
        int level,
        int totalLinesCleared
) {
    /**
     * テトリミノの初期出現位置（x座標）。
     */
    private static final int TETROMINO_SPAWN_X = 4;

    /**
     * テトリミノの初期出現位置（y座標）。
     */
    private static final int TETROMINO_SPAWN_Y = 0;

    /**
     * 乱数生成器。
     */
    private static final Random RANDOM = new Random();
    /**
     * Compact constructor: 入力値の検証を行います。
     *
     * <p>すべてのオブジェクトフィールドがnullでないこと、
     * 数値フィールドが適切な範囲内にあることを検証します。</p>
     *
     * @throws NullPointerException いずれかのオブジェクトフィールドがnullの場合
     * @throws IllegalArgumentException 数値フィールドが無効な値の場合
     */
    public GameState {
        if (status == null) {
            throw new NullPointerException("status must not be null");
        }
        if (currentTetromino == null) {
            throw new NullPointerException("currentTetromino must not be null");
        }
        if (nextTetromino == null) {
            throw new NullPointerException("nextTetromino must not be null");
        }
        if (field == null) {
            throw new NullPointerException("field must not be null");
        }
        if (score < 0) {
            throw new IllegalArgumentException("score must be non-negative");
        }
        if (level < 1) {
            throw new IllegalArgumentException("level must be at least 1");
        }
        if (totalLinesCleared < 0) {
            throw new IllegalArgumentException("totalLinesCleared must be non-negative");
        }
    }

    /**
     * 新しいゲームを初期化します。
     *
     * <p>初期状態：</p>
     * <ul>
     *   <li>status: PLAYING</li>
     *   <li>空のフィールド</li>
     *   <li>ランダムな最初のテトリミノ</li>
     *   <li>ランダムな次のテトリミノ</li>
     *   <li>score: 0</li>
     *   <li>level: 1</li>
     *   <li>totalLinesCleared: 0</li>
     * </ul>
     *
     * @return 初期化されたGameState
     */
    public static GameState initialize() {
        return new GameState(
                GameStatus.PLAYING,
                generateRandomTetromino(),
                generateRandomTetromino(),
                GameField.createEmpty(),
                0,
                1,
                0
        );
    }

    /**
     * ランダムなテトリミノを生成します。
     *
     * <p>7種類のテトリミノタイプからランダムに選択し、
     * 初期位置（x=4, y=0）、初期回転（DEG_0）で生成します。</p>
     *
     * @return ランダムに生成されたテトリミノ
     */
    private static Tetromino generateRandomTetromino() {
        TetrominoType[] types = TetrominoType.values();
        TetrominoType randomType = types[RANDOM.nextInt(types.length)];
        return new Tetromino(
                randomType,
                new Position(TETROMINO_SPAWN_X, TETROMINO_SPAWN_Y),
                Rotation.DEG_0
        );
    }

    /**
     * 現在のテトリミノを指定された方向に移動します。
     *
     * <p>移動可能な場合は、新しい位置のテトリミノを持つGameStateを返します。
     * 移動不可能な場合（境界外、衝突）は、元のGameStateをそのまま返します。</p>
     *
     * @param direction 移動方向（LEFT, RIGHT, DOWN）
     * @return 移動後のGameState（移動不可能な場合は元のGameState）
     * @throws NullPointerException directionがnullの場合
     */
    public GameState moveTetromino(Direction direction) {
        if (direction == null) {
            throw new NullPointerException("direction must not be null");
        }

        // 現在のテトリミノの位置を取得
        Position currentPos = currentTetromino.position();

        // 方向に応じて新しい位置を計算
        Position newPos = switch (direction) {
            case LEFT -> new Position(currentPos.x() - 1, currentPos.y());
            case RIGHT -> new Position(currentPos.x() + 1, currentPos.y());
            case DOWN -> new Position(currentPos.x(), currentPos.y() + 1);
        };

        // 新しい位置でテトリミノを作成
        Tetromino movedTetromino = new Tetromino(
                currentTetromino.type(),
                newPos,
                currentTetromino.rotation()
        );

        // フィールドに配置可能かチェック
        if (field.canPlace(movedTetromino)) {
            // 配置可能な場合は新しい位置のテトリミノで GameState を作成
            return new GameState(
                    status,
                    movedTetromino,
                    nextTetromino,
                    field,
                    score,
                    level,
                    totalLinesCleared
            );
        } else {
            // 配置不可能な場合は元の GameState を返す
            return this;
        }
    }

    /**
     * 現在のテトリミノを時計回りに90度回転します。
     *
     * <p>回転可能な場合は、回転後のテトリミノを持つGameStateを返します。
     * 回転不可能な場合（境界外、衝突）は、元のGameStateをそのまま返します。</p>
     *
     * @return 回転後のGameState（回転不可能な場合は元のGameState）
     */
    public GameState rotateTetromino() {
        // テトリミノを時計回りに回転
        Tetromino rotatedTetromino = currentTetromino.rotateClockwise();

        // フィールドに配置可能かチェック
        if (field.canPlace(rotatedTetromino)) {
            // 配置可能な場合は回転後のテトリミノで GameState を作成
            return new GameState(
                    status,
                    rotatedTetromino,
                    nextTetromino,
                    field,
                    score,
                    level,
                    totalLinesCleared
            );
        } else {
            // 配置不可能な場合は元の GameState を返す
            return this;
        }
    }

    /**
     * 現在のテトリミノを最下部まで落下させて固定します（ハードドロップ）。
     *
     * <p>処理内容：</p>
     * <ul>
     *   <li>テトリミノを可能な限り下まで移動</li>
     *   <li>フィールドに固定</li>
     *   <li>ライン消去処理</li>
     *   <li>次のテトリミノを生成</li>
     *   <li>スコア計算</li>
     * </ul>
     *
     * @return ハードドロップ後のGameState
     */
    public GameState hardDrop() {
        // 1. テトリミノを最下部まで落下
        Tetromino droppingTetromino = currentTetromino;
        while (true) {
            Position nextPos = new Position(
                    droppingTetromino.position().x(),
                    droppingTetromino.position().y() + 1
            );
            Tetromino nextTetromino = new Tetromino(
                    droppingTetromino.type(),
                    nextPos,
                    droppingTetromino.rotation()
            );

            if (field.canPlace(nextTetromino)) {
                droppingTetromino = nextTetromino;
            } else {
                break;  // これ以上落下できない
            }
        }

        // 2. フィールドに固定
        GameField fixedField = field.place(droppingTetromino);

        // 3. ライン消去処理
        ClearResult clearResult = fixedField.clearLines();

        // 4. スコア計算（ライン消去数に応じて）
        int earnedScore = calculateScore(clearResult.clearedLineCount());
        int newScore = score + earnedScore;
        int newTotalLinesCleared = totalLinesCleared + clearResult.clearedLineCount();

        // 5. 次のテトリミノを currentTetromino にして、新しい nextTetromino を生成
        return new GameState(
                status,
                nextTetromino,  // nextTetromino が currentTetromino になる
                generateRandomTetromino(),  // 新しい nextTetromino を生成
                clearResult.updatedField(),
                newScore,
                level,
                newTotalLinesCleared
        );
    }

    /**
     * ライン消去数に応じてスコアを計算します。
     *
     * <p>スコア計算式：</p>
     * <ul>
     *   <li>1ライン: 40点</li>
     *   <li>2ライン: 100点</li>
     *   <li>3ライン: 300点</li>
     *   <li>4ライン（テトリス）: 1200点</li>
     * </ul>
     *
     * @param clearedLines 消去ライン数
     * @return 獲得スコア
     */
    private int calculateScore(int clearedLines) {
        return switch (clearedLines) {
            case 0 -> 0;
            case 1 -> 40;
            case 2 -> 100;
            case 3 -> 300;
            case 4 -> 1200;
            default -> clearedLines * 40;  // 5ライン以上は1ライン40点で計算
        };
    }

    /**
     * 自動落下処理を実行します。
     *
     * <p>テトリミノを1マス下に移動します。移動不可能な場合は、
     * ハードドロップを実行します。</p>
     *
     * @return 自動落下後のGameState
     */
    public GameState processAutoDropTick() {
        // DOWN方向に移動を試みる
        GameState movedState = moveTetromino(Direction.DOWN);

        // 移動できた場合はそのまま返す
        if (movedState != this) {
            return movedState;
        }

        // 移動できなかった場合はハードドロップを実行
        return hardDrop();
    }

    /**
     * 現在のレベルに応じた自動落下間隔（ミリ秒）を返します。
     *
     * <p>レベルが上がるにつれて落下間隔が短くなり、ゲームが難しくなります。</p>
     * <ul>
     *   <li>レベル1: 1000ms</li>
     *   <li>レベル2: 900ms</li>
     *   <li>レベル3: 800ms</li>
     *   <li>...</li>
     *   <li>レベル10以上: 100ms</li>
     * </ul>
     *
     * @return 落下間隔（ミリ秒）
     */
    public int getDropInterval() {
        // レベル1: 1000ms, レベル10: 100ms
        int baseInterval = 1000;
        int decreasePerLevel = 100;
        int minInterval = 100;

        int interval = baseInterval - (level - 1) * decreasePerLevel;
        return Math.max(interval, minInterval);
    }
}
