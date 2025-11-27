package com.example.tetris.domain;

/**
 * 10×20のゲームフィールドを表す不変レコードクラス。
 *
 * <p>GameFieldは、テトリスゲームのプレイフィールドの状態を管理します。
 * フィールドの各セルは、{@link Block}または null（空）の状態を持ちます。</p>
 *
 * <h3>フィールド仕様：</h3>
 * <ul>
 *   <li>幅: 10列</li>
 *   <li>高さ: 20行</li>
 *   <li>座標系: x軸（0-9）、y軸（0-19）</li>
 *   <li>null: 空のセル</li>
 *   <li>Block: 占有されたセル</li>
 * </ul>
 *
 * <h3>不変性（Immutability）：</h3>
 * <p>GameFieldは不変オブジェクトです。フィールドの変更は新しいGameFieldインスタンスを返します。</p>
 *
 * @param grid 10×20のブロック配列（null=空、Block=占有）
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public record GameField(Block[][] grid) {
    /**
     * フィールドの幅（列数）。
     */
    public static final int WIDTH = 10;

    /**
     * フィールドの高さ（行数）。
     */
    public static final int HEIGHT = 20;

    /**
     * 空のGameFieldを生成します。
     *
     * <p>すべてのセルがnull（空）の状態のフィールドを作成します。</p>
     *
     * @return 空のGameFieldインスタンス
     */
    public static GameField createEmpty() {
        Block[][] emptyGrid = new Block[HEIGHT][WIDTH];
        return new GameField(emptyGrid);
    }

    /**
     * 指定された座標のセルが占有されているかを確認します。
     *
     * @param x x座標（0-9）
     * @param y y座標（0-19）
     * @return セルが占有されている場合true、空またはフィールド外の場合false
     */
    public boolean isOccupied(int x, int y) {
        // 境界外チェック
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return false;
        }
        // セルが占有されているかチェック
        return grid[y][x] != null;
    }

    /**
     * テトリミノを配置可能かを確認します（衝突判定）。
     *
     * @param tetromino 配置を試みるテトリミノ
     * @return 配置可能な場合true、衝突する場合false
     */
    public boolean canPlace(Tetromino tetromino) {
        // テトリミノの各ブロック位置が配置可能かチェック
        for (Position blockPos : tetromino.getBlockPositions()) {
            int x = blockPos.x();
            int y = blockPos.y();

            // 境界外チェック
            if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
                return false;
            }

            // 既存のブロックとの衝突チェック
            if (isOccupied(x, y)) {
                return false;
            }
        }

        return true;
    }

    /**
     * テトリミノをフィールドに固定します。
     *
     * @param tetromino 固定するテトリミノ
     * @return テトリミノが固定された新しいGameField
     */
    public GameField place(Tetromino tetromino) {
        // 新しいグリッドを作成（ディープコピー）
        Block[][] newGrid = new Block[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            System.arraycopy(grid[y], 0, newGrid[y], 0, WIDTH);
        }

        // テトリミノの各ブロックを新しいグリッドに配置
        for (Position blockPos : tetromino.getBlockPositions()) {
            int x = blockPos.x();
            int y = blockPos.y();
            newGrid[y][x] = new Block(tetromino.type());
        }

        return new GameField(newGrid);
    }

    /**
     * 横一列が埋まったラインを消去します。
     *
     * @return ライン消去後のGameFieldと消去ライン数を含むClearResult
     */
    public ClearResult clearLines() {
        // 完全に埋まっているラインを検出
        java.util.List<Integer> linesToClear = new java.util.ArrayList<>();
        for (int y = 0; y < HEIGHT; y++) {
            if (isLineFull(y)) {
                linesToClear.add(y);
            }
        }

        // ライン消去がない場合は元のフィールドをそのまま返す
        if (linesToClear.isEmpty()) {
            return new ClearResult(this, 0);
        }

        // 新しいグリッドを作成
        Block[][] newGrid = new Block[HEIGHT][WIDTH];

        // ライン消去後のブロック配置を計算
        int newY = HEIGHT - 1;
        for (int oldY = HEIGHT - 1; oldY >= 0; oldY--) {
            // 消去されるラインはスキップ
            if (linesToClear.contains(oldY)) {
                continue;
            }

            // 消去されないラインを新しいグリッドにコピー
            System.arraycopy(grid[oldY], 0, newGrid[newY], 0, WIDTH);
            newY--;
        }

        // 残りの上部ラインは空（null）のまま

        return new ClearResult(new GameField(newGrid), linesToClear.size());
    }

    /**
     * 指定された行が完全に埋まっているかを確認します。
     *
     * @param y 行番号（0-19）
     * @return 行が完全に埋まっている場合true
     */
    private boolean isLineFull(int y) {
        for (int x = 0; x < WIDTH; x++) {
            if (grid[y][x] == null) {
                return false;
            }
        }
        return true;
    }
}
