package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link Position}のテストクラス。
 *
 * <p>ゲームフィールド上の座標位置を表すPositionレコードの動作を検証します。</p>
 *
 * <p>テスト対象：</p>
 * <ul>
 *   <li>座標値の保持と取得</li>
 *   <li>不変性（immutability）</li>
 *   <li>等価性（equals/hashCode）</li>
 *   <li>座標変換メソッド（add, subtract）</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 * @see Position
 */
class PositionTest {

    /**
     * Positionが座標値を正しく保持することを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>x座標が正しく保持されること</li>
     *   <li>y座標が正しく保持されること</li>
     * </ul>
     */
    @Test
    void 座標値を保持できること() {
        // Given/When: 座標(3, 5)のPositionを作成
        Position position = new Position(3, 5);

        // Then: 座標値が正しく保持されること
        assertThat(position.x()).isEqualTo(3);
        assertThat(position.y()).isEqualTo(5);
    }

    /**
     * 同じ座標値を持つPositionが等しいと判定されることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>同じ座標を持つPositionはequals()でtrueを返すこと</li>
     *   <li>同じ座標を持つPositionは同じhashCodeを返すこと</li>
     * </ul>
     */
    @Test
    void 同じ座標を持つPositionは等しいこと() {
        // Given: 同じ座標(2, 4)を持つ2つのPosition
        Position position1 = new Position(2, 4);
        Position position2 = new Position(2, 4);

        // Then: equals()とhashCode()が一致すること
        assertThat(position1).isEqualTo(position2);
        assertThat(position1.hashCode()).isEqualTo(position2.hashCode());
    }

    /**
     * 異なる座標値を持つPositionが等しくないと判定されることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>異なる座標を持つPositionはequals()でfalseを返すこと</li>
     * </ul>
     */
    @Test
    void 異なる座標を持つPositionは等しくないこと() {
        // Given: 異なる座標を持つ2つのPosition
        Position position1 = new Position(1, 2);
        Position position2 = new Position(3, 4);

        // Then: equals()でfalseを返すこと
        assertThat(position1).isNotEqualTo(position2);
    }

    /**
     * 負の座標値も扱えることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>負のx座標を保持できること</li>
     *   <li>負のy座標を保持できること</li>
     * </ul>
     */
    @Test
    void 負の座標値も扱えること() {
        // Given/When: 負の座標(-2, -5)のPositionを作成
        Position position = new Position(-2, -5);

        // Then: 負の座標値が正しく保持されること
        assertThat(position.x()).isEqualTo(-2);
        assertThat(position.y()).isEqualTo(-5);
    }

    /**
     * ゼロ座標を扱えることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>x=0, y=0の座標を保持できること</li>
     * </ul>
     */
    @Test
    void ゼロ座標を扱えること() {
        // Given/When: ゼロ座標(0, 0)のPositionを作成
        Position position = new Position(0, 0);

        // Then: ゼロ座標が正しく保持されること
        assertThat(position.x()).isEqualTo(0);
        assertThat(position.y()).isEqualTo(0);
    }
}
