package com.example.tetris.domain;

import java.util.List;

/**
 * Tetrisゲームのテトリミノを表す不変レコードクラス。
 *
 * <p>Tetrominoは以下の3つの属性を持ちます：</p>
 * <ul>
 *   <li>{@link TetrominoType} - テトリミノの種類（I, O, T, S, Z, J, L）</li>
 *   <li>{@link Position} - ゲームフィールド上の位置</li>
 *   <li>{@link Rotation} - 回転状態（0°, 90°, 180°, 270°）</li>
 * </ul>
 *
 * <h3>不変性（Immutability）：</h3>
 * <p>Tetrominoは不変オブジェクトです。移動や回転の操作は新しいTetrominoインスタンスを返します。</p>
 *
 * <h3>使用例：</h3>
 * <pre>{@code
 * // I型テトリミノを位置(5, 0)、回転0度で生成
 * Tetromino tetromino = new Tetromino(TetrominoType.I, new Position(5, 0), Rotation.DEG_0);
 *
 * // 左に移動（新しいインスタンスを返す）
 * Tetromino moved = tetromino.moveLeft();
 *
 * // 時計回りに回転
 * Tetromino rotated = tetromino.rotateClockwise();
 *
 * // ブロック座標を取得
 * List<Position> blocks = tetromino.getBlockPositions();
 * }</pre>
 *
 * @param type     テトリミノの種類
 * @param position ゲームフィールド上の位置
 * @param rotation 回転状態
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public record Tetromino(TetrominoType type, Position position, Rotation rotation) {

    /**
     * テトリミノを左に1マス移動します。
     *
     * @return 左に移動した新しいTetrominoインスタンス
     */
    public Tetromino moveLeft() {
        return new Tetromino(type, new Position(position.x() - 1, position.y()), rotation);
    }

    /**
     * テトリミノを右に1マス移動します。
     *
     * @return 右に移動した新しいTetrominoインスタンス
     */
    public Tetromino moveRight() {
        return new Tetromino(type, new Position(position.x() + 1, position.y()), rotation);
    }

    /**
     * テトリミノを下に1マス移動します。
     *
     * @return 下に移動した新しいTetrominoインスタンス
     */
    public Tetromino moveDown() {
        return new Tetromino(type, new Position(position.x(), position.y() + 1), rotation);
    }

    /**
     * テトリミノを時計回りに90度回転します。
     *
     * @return 時計回りに90度回転した新しいTetrominoインスタンス
     */
    public Tetromino rotateClockwise() {
        return new Tetromino(type, position, rotation.clockwise());
    }

    /**
     * テトリミノを構成する4つのブロックの絶対座標を取得します。
     *
     * <p>各テトリミノ型と回転状態に応じた4つのブロック位置を計算します。
     * 座標は基準位置（position）からの相対位置として定義されています。</p>
     *
     * @return テトリミノを構成する4つのブロックの座標リスト
     */
    public List<Position> getBlockPositions() {
        List<Position> relativePositions = getRelativePositions(type, rotation);
        return relativePositions.stream()
                .map(rel -> new Position(position.x() + rel.x(), position.y() + rel.y()))
                .toList();
    }

    /**
     * テトリミノ型と回転状態に応じた相対座標リストを取得します。
     *
     * <p>各テトリミノの形状は、基準位置(0, 0)からの相対座標で定義されます。</p>
     *
     * @param type     テトリミノの種類
     * @param rotation 回転状態
     * @return 基準位置からの相対座標リスト（4つの座標）
     */
    private List<Position> getRelativePositions(TetrominoType type, Rotation rotation) {
        return switch (type) {
            case I -> getIShape(rotation);
            case O -> getOShape(rotation);
            case T -> getTShape(rotation);
            case S -> getSShape(rotation);
            case Z -> getZShape(rotation);
            case J -> getJShape(rotation);
            case L -> getLShape(rotation);
        };
    }

    /**
     * I型（棒型）テトリミノの相対座標を取得します。
     *
     * <pre>
     * 0度/180度:  90度/270度:
     * □□■□      □
     *             □
     *             ■
     *             □
     * </pre>
     *
     * @param rotation 回転状態
     * @return I型の相対座標リスト
     */
    private List<Position> getIShape(Rotation rotation) {
        return switch (rotation) {
            case DEG_0, DEG_180 -> List.of(
                    new Position(-1, 0),
                    new Position(0, 0),
                    new Position(1, 0),
                    new Position(2, 0)
            );
            case DEG_90, DEG_270 -> List.of(
                    new Position(0, -1),
                    new Position(0, 0),
                    new Position(0, 1),
                    new Position(0, 2)
            );
        };
    }

    /**
     * O型（正方形）テトリミノの相対座標を取得します。
     *
     * <pre>
     * ■□
     * □□
     * </pre>
     *
     * @param rotation 回転状態（O型は回転しても形状が変わらない）
     * @return O型の相対座標リスト
     */
    private List<Position> getOShape(Rotation rotation) {
        // O型は回転しても形状が変わらない
        return List.of(
                new Position(0, 0),
                new Position(1, 0),
                new Position(0, 1),
                new Position(1, 1)
        );
    }

    /**
     * T型テトリミノの相対座標を取得します。
     *
     * <pre>
     * 0度:     90度:    180度:   270度:
     * □■□     □        □       □
     *  □      □■      ■□□    ■□
     *         □               □
     * </pre>
     *
     * @param rotation 回転状態
     * @return T型の相対座標リスト
     */
    private List<Position> getTShape(Rotation rotation) {
        return switch (rotation) {
            case DEG_0 -> List.of(
                    new Position(-1, 0),
                    new Position(0, 0),
                    new Position(1, 0),
                    new Position(0, 1)
            );
            case DEG_90 -> List.of(
                    new Position(0, -1),
                    new Position(0, 0),
                    new Position(1, 0),
                    new Position(0, 1)
            );
            case DEG_180 -> List.of(
                    new Position(0, -1),
                    new Position(-1, 0),
                    new Position(0, 0),
                    new Position(1, 0)
            );
            case DEG_270 -> List.of(
                    new Position(0, -1),
                    new Position(-1, 0),
                    new Position(0, 0),
                    new Position(0, 1)
            );
        };
    }

    /**
     * S型テトリミノの相対座標を取得します。
     *
     * <pre>
     * 0度/180度:  90度/270度:
     *  ■□        □
     * □□        ■□
     *            □
     * </pre>
     *
     * @param rotation 回転状態
     * @return S型の相対座標リスト
     */
    private List<Position> getSShape(Rotation rotation) {
        return switch (rotation) {
            case DEG_0, DEG_180 -> List.of(
                    new Position(-1, 1),
                    new Position(0, 1),
                    new Position(0, 0),
                    new Position(1, 0)
            );
            case DEG_90, DEG_270 -> List.of(
                    new Position(0, -1),
                    new Position(0, 0),
                    new Position(1, 0),
                    new Position(1, 1)
            );
        };
    }

    /**
     * Z型テトリミノの相対座標を取得します。
     *
     * <pre>
     * 0度/180度:  90度/270度:
     * ■□         □
     *  □□       ■□
     *            □
     * </pre>
     *
     * @param rotation 回転状態
     * @return Z型の相対座標リスト
     */
    private List<Position> getZShape(Rotation rotation) {
        return switch (rotation) {
            case DEG_0, DEG_180 -> List.of(
                    new Position(-1, 0),
                    new Position(0, 0),
                    new Position(0, 1),
                    new Position(1, 1)
            );
            case DEG_90, DEG_270 -> List.of(
                    new Position(1, -1),
                    new Position(0, 0),
                    new Position(1, 0),
                    new Position(0, 1)
            );
        };
    }

    /**
     * J型テトリミノの相対座標を取得します。
     *
     * <pre>
     * 0度:     90度:    180度:   270度:
     * ■        □□      □       □
     * □□□     □      □□□    □
     *          □              □□
     * </pre>
     *
     * @param rotation 回転状態
     * @return J型の相対座標リスト
     */
    private List<Position> getJShape(Rotation rotation) {
        return switch (rotation) {
            case DEG_0 -> List.of(
                    new Position(-1, 0),
                    new Position(-1, 1),
                    new Position(0, 1),
                    new Position(1, 1)
            );
            case DEG_90 -> List.of(
                    new Position(0, -1),
                    new Position(1, -1),
                    new Position(0, 0),
                    new Position(0, 1)
            );
            case DEG_180 -> List.of(
                    new Position(-1, 0),
                    new Position(0, 0),
                    new Position(1, 0),
                    new Position(1, 1)
            );
            case DEG_270 -> List.of(
                    new Position(0, -1),
                    new Position(0, 0),
                    new Position(-1, 1),
                    new Position(0, 1)
            );
        };
    }

    /**
     * L型テトリミノの相対座標を取得します。
     *
     * <pre>
     * 0度:     90度:    180度:   270度:
     *     □    □        □       □□
     * □□□    □       □□□     □
     *          □□              □
     * </pre>
     *
     * @param rotation 回転状態
     * @return L型の相対座標リスト
     */
    private List<Position> getLShape(Rotation rotation) {
        return switch (rotation) {
            case DEG_0 -> List.of(
                    new Position(1, 0),
                    new Position(-1, 1),
                    new Position(0, 1),
                    new Position(1, 1)
            );
            case DEG_90 -> List.of(
                    new Position(0, -1),
                    new Position(0, 0),
                    new Position(0, 1),
                    new Position(1, 1)
            );
            case DEG_180 -> List.of(
                    new Position(-1, 0),
                    new Position(0, 0),
                    new Position(1, 0),
                    new Position(-1, 1)
            );
            case DEG_270 -> List.of(
                    new Position(-1, -1),
                    new Position(0, -1),
                    new Position(0, 0),
                    new Position(0, 1)
            );
        };
    }
}
