package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link Tetromino}のテストクラス。
 *
 * <p>Tetrominoエンティティの基本動作と各種操作を検証します。</p>
 *
 * <p>テスト対象：</p>
 * <ul>
 *   <li>Tetrominoの生成（type, position, rotation）</li>
 *   <li>移動操作（moveLeft, moveRight, moveDown）</li>
 *   <li>回転操作（rotateClockwise）</li>
 *   <li>ブロック座標取得（getBlockPositions）</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 * @see Tetromino
 */
class TetrominoTest {

    /**
     * Tetrominoが正しく生成されることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>type, position, rotationが正しく保持されること</li>
     * </ul>
     */
    @Test
    void Tetrominoを生成できること() {
        // Given/When: I型テトリミノを位置(5, 0)、回転0度で生成
        Tetromino tetromino = new Tetromino(TetrominoType.I, new Position(5, 0), Rotation.DEG_0);

        // Then: 各フィールドが正しく設定されること
        assertThat(tetromino.type()).isEqualTo(TetrominoType.I);
        assertThat(tetromino.position()).isEqualTo(new Position(5, 0));
        assertThat(tetromino.rotation()).isEqualTo(Rotation.DEG_0);
    }

    /**
     * Tetrominoが左に移動できることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>moveLeft()でx座標が-1されること</li>
     *   <li>y座標は変化しないこと</li>
     *   <li>元のインスタンスは変更されないこと（不変性）</li>
     * </ul>
     */
    @Test
    void 左に移動できること() {
        // Given: 位置(5, 10)のテトリミノ
        Tetromino tetromino = new Tetromino(TetrominoType.T, new Position(5, 10), Rotation.DEG_0);

        // When: 左に移動
        Tetromino moved = tetromino.moveLeft();

        // Then: x座標が1減り、y座標は不変
        assertThat(moved.position()).isEqualTo(new Position(4, 10));
        assertThat(moved.type()).isEqualTo(TetrominoType.T);
        assertThat(moved.rotation()).isEqualTo(Rotation.DEG_0);

        // Then: 元のテトリミノは変更されていない（不変性確認）
        assertThat(tetromino.position()).isEqualTo(new Position(5, 10));
    }

    /**
     * Tetrominoが右に移動できることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>moveRight()でx座標が+1されること</li>
     *   <li>y座標は変化しないこと</li>
     * </ul>
     */
    @Test
    void 右に移動できること() {
        // Given: 位置(5, 10)のテトリミノ
        Tetromino tetromino = new Tetromino(TetrominoType.S, new Position(5, 10), Rotation.DEG_90);

        // When: 右に移動
        Tetromino moved = tetromino.moveRight();

        // Then: x座標が1増え、y座標は不変
        assertThat(moved.position()).isEqualTo(new Position(6, 10));
        assertThat(moved.type()).isEqualTo(TetrominoType.S);
        assertThat(moved.rotation()).isEqualTo(Rotation.DEG_90);
    }

    /**
     * Tetrominoが下に移動できることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>moveDown()でy座標が+1されること</li>
     *   <li>x座標は変化しないこと</li>
     * </ul>
     */
    @Test
    void 下に移動できること() {
        // Given: 位置(5, 10)のテトリミノ
        Tetromino tetromino = new Tetromino(TetrominoType.Z, new Position(5, 10), Rotation.DEG_180);

        // When: 下に移動
        Tetromino moved = tetromino.moveDown();

        // Then: y座標が1増え、x座標は不変
        assertThat(moved.position()).isEqualTo(new Position(5, 11));
        assertThat(moved.type()).isEqualTo(TetrominoType.Z);
        assertThat(moved.rotation()).isEqualTo(Rotation.DEG_180);
    }

    /**
     * Tetrominoが複数回移動できることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>メソッドチェーンで連続移動できること</li>
     * </ul>
     */
    @Test
    void 複数回移動できること() {
        // Given: 位置(5, 5)のテトリミノ
        Tetromino tetromino = new Tetromino(TetrominoType.J, new Position(5, 5), Rotation.DEG_0);

        // When: 左2回、下3回移動
        Tetromino moved = tetromino.moveLeft().moveLeft().moveDown().moveDown().moveDown();

        // Then: 最終位置は(3, 8)
        assertThat(moved.position()).isEqualTo(new Position(3, 8));
    }

    /**
     * Tetrominoが時計回りに回転できることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>rotateClockwise()で回転状態が90度進むこと</li>
     *   <li>位置は変化しないこと</li>
     * </ul>
     */
    @Test
    void 時計回りに回転できること() {
        // Given: 回転0度のテトリミノ
        Tetromino tetromino = new Tetromino(TetrominoType.L, new Position(5, 10), Rotation.DEG_0);

        // When: 時計回りに回転
        Tetromino rotated = tetromino.rotateClockwise();

        // Then: 回転状態が90度になり、位置は不変
        assertThat(rotated.rotation()).isEqualTo(Rotation.DEG_90);
        assertThat(rotated.position()).isEqualTo(new Position(5, 10));
        assertThat(rotated.type()).isEqualTo(TetrominoType.L);
    }

    /**
     * Tetrominoが複数回回転できることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>4回回転すると元の向きに戻ること</li>
     * </ul>
     */
    @Test
    void 複数回回転できること() {
        // Given: 回転0度のテトリミノ
        Tetromino tetromino = new Tetromino(TetrominoType.O, new Position(5, 10), Rotation.DEG_0);

        // When: 4回時計回りに回転
        Tetromino rotated = tetromino.rotateClockwise().rotateClockwise()
                .rotateClockwise().rotateClockwise();

        // Then: 元の0度に戻る
        assertThat(rotated.rotation()).isEqualTo(Rotation.DEG_0);
    }

    /**
     * I型テトリミノのブロック座標が正しく取得できることを検証するテスト。
     *
     * <p>I型（棒型）の形状：</p>
     * <pre>
     * 0度:     90度:
     * □□□□   □
     *          □
     *          □
     *          □
     * </pre>
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>0度回転時の4つのブロック座標が正しいこと</li>
     *   <li>90度回転時の4つのブロック座標が正しいこと</li>
     * </ul>
     */
    @Test
    void I型テトリミノのブロック座標を取得できること() {
        // Given: 位置(5, 5)の0度回転I型テトリミノ
        Tetromino tetromino = new Tetromino(TetrominoType.I, new Position(5, 5), Rotation.DEG_0);

        // When: ブロック座標を取得
        List<Position> positions = tetromino.getBlockPositions();

        // Then: 4つの座標が水平に並ぶ
        assertThat(positions).hasSize(4);
        assertThat(positions).containsExactlyInAnyOrder(
                new Position(5, 5),   // 基準位置
                new Position(6, 5),   // 右
                new Position(4, 5),   // 左
                new Position(7, 5)    // 右2
        );

        // Given: 90度回転させたI型テトリミノ
        Tetromino rotated = tetromino.rotateClockwise();

        // When: ブロック座標を取得
        List<Position> rotatedPositions = rotated.getBlockPositions();

        // Then: 4つの座標が垂直に並ぶ
        assertThat(rotatedPositions).hasSize(4);
        assertThat(rotatedPositions).containsExactlyInAnyOrder(
                new Position(5, 5),   // 基準位置
                new Position(5, 4),   // 上
                new Position(5, 6),   // 下
                new Position(5, 7)    // 下2
        );
    }

    /**
     * O型テトリミノのブロック座標が正しく取得できることを検証するテスト。
     *
     * <p>O型（正方形）の形状：</p>
     * <pre>
     * □□
     * □□
     * </pre>
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>4つのブロック座標が2×2の正方形を形成すること</li>
     *   <li>回転しても形状が変わらないこと</li>
     * </ul>
     */
    @Test
    void O型テトリミノのブロック座標を取得できること() {
        // Given: 位置(5, 5)の0度回転O型テトリミノ
        Tetromino tetromino = new Tetromino(TetrominoType.O, new Position(5, 5), Rotation.DEG_0);

        // When: ブロック座標を取得
        List<Position> positions = tetromino.getBlockPositions();

        // Then: 2×2の正方形を形成
        assertThat(positions).hasSize(4);
        assertThat(positions).containsExactlyInAnyOrder(
                new Position(5, 5),   // 基準位置
                new Position(6, 5),   // 右
                new Position(5, 6),   // 下
                new Position(6, 6)    // 右下
        );

        // Given: 90度回転させたO型テトリミノ
        Tetromino rotated = tetromino.rotateClockwise();

        // When: ブロック座標を取得
        List<Position> rotatedPositions = rotated.getBlockPositions();

        // Then: 回転しても同じ形状（O型は回転で形状が変わらない）
        assertThat(rotatedPositions).hasSize(4);
        assertThat(rotatedPositions).containsExactlyInAnyOrder(
                new Position(5, 5),
                new Position(6, 5),
                new Position(5, 6),
                new Position(6, 6)
        );
    }

    /**
     * T型テトリミノのブロック座標が正しく取得できることを検証するテスト。
     *
     * <p>T型の形状：</p>
     * <pre>
     * 0度:    90度:   180度:  270度:
     * □□□    □       □     □
     *  □      □□     □□□   □□
     *         □              □
     * </pre>
     */
    @Test
    void T型テトリミノのブロック座標を取得できること() {
        // Given: 位置(5, 5)の0度回転T型テトリミノ
        Tetromino tetromino = new Tetromino(TetrominoType.T, new Position(5, 5), Rotation.DEG_0);

        // When: ブロック座標を取得
        List<Position> positions = tetromino.getBlockPositions();

        // Then: T字型を形成
        assertThat(positions).hasSize(4);
        assertThat(positions).containsExactlyInAnyOrder(
                new Position(5, 5),   // 基準位置（中央）
                new Position(4, 5),   // 左
                new Position(6, 5),   // 右
                new Position(5, 6)    // 下
        );
    }
}
