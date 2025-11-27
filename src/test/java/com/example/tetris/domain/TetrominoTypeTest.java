package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link TetrominoType}のテストクラス。
 *
 * <p>Tetrisゲームで使用される7種類のテトリミノ型が正しく定義されていることを検証します。</p>
 *
 * <h3>テスト対象の型：</h3>
 * <ul>
 *   <li>I型（棒型）</li>
 *   <li>O型（正方形）</li>
 *   <li>T型（T字型）</li>
 *   <li>S型（S字型）</li>
 *   <li>Z型（Z字型）</li>
 *   <li>J型（J字型）</li>
 *   <li>L型（L字型）</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 * @see TetrominoType
 */
class TetrominoTypeTest {

    /**
     * TetrominoTypeに7種類のテトリミノが定義されていることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>I, O, T, S, Z, J, L の7種類が存在すること</li>
     *   <li>各型がnullでないこと</li>
     *   <li>values()メソッドが7つの要素を返すこと</li>
     * </ul>
     */
    @Test
    void テトリミノ型は7種類定義されていること() {
        // When: TetrominoTypeの全要素を取得
        TetrominoType[] types = TetrominoType.values();

        // Then: 7種類のテトリミノが存在すること
        assertThat(types).hasSize(7);
        assertThat(types).containsExactlyInAnyOrder(
                TetrominoType.I,
                TetrominoType.O,
                TetrominoType.T,
                TetrominoType.S,
                TetrominoType.Z,
                TetrominoType.J,
                TetrominoType.L
        );
    }

    /**
     * 各テトリミノ型が個別に参照できることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>各型が定数として定義されていること</li>
     *   <li>nullでないこと</li>
     *   <li>valueOf()で文字列から変換できること</li>
     * </ul>
     */
    @Test
    void 各テトリミノ型が個別に参照できること() {
        // Then: 各型が個別に参照可能であること
        assertThat(TetrominoType.I).isNotNull();
        assertThat(TetrominoType.O).isNotNull();
        assertThat(TetrominoType.T).isNotNull();
        assertThat(TetrominoType.S).isNotNull();
        assertThat(TetrominoType.Z).isNotNull();
        assertThat(TetrominoType.J).isNotNull();
        assertThat(TetrominoType.L).isNotNull();
    }

    /**
     * valueOf()メソッドで文字列から型に変換できることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>文字列"I"からTetrominoType.Iに変換できること</li>
     *   <li>他の型も同様に変換できること</li>
     * </ul>
     */
    @Test
    void 文字列からテトリミノ型に変換できること() {
        // When/Then: 文字列から各型に変換できること
        assertThat(TetrominoType.valueOf("I")).isEqualTo(TetrominoType.I);
        assertThat(TetrominoType.valueOf("O")).isEqualTo(TetrominoType.O);
        assertThat(TetrominoType.valueOf("T")).isEqualTo(TetrominoType.T);
        assertThat(TetrominoType.valueOf("S")).isEqualTo(TetrominoType.S);
        assertThat(TetrominoType.valueOf("Z")).isEqualTo(TetrominoType.Z);
        assertThat(TetrominoType.valueOf("J")).isEqualTo(TetrominoType.J);
        assertThat(TetrominoType.valueOf("L")).isEqualTo(TetrominoType.L);
    }
}
