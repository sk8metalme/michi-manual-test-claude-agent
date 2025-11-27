package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link Rotation}のテストクラス。
 *
 * <p>テトリミノの回転状態を表すRotation列挙型の動作を検証します。</p>
 *
 * <p>テスト対象：</p>
 * <ul>
 *   <li>4つの回転状態（0°, 90°, 180°, 270°）</li>
 *   <li>時計回り回転メソッド（clockwise）</li>
 *   <li>反時計回り回転メソッド（counterclockwise）</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 * @see Rotation
 */
class RotationTest {

    /**
     * Rotationに4つの回転状態が定義されていることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>DEG_0, DEG_90, DEG_180, DEG_270の4種類が存在すること</li>
     *   <li>values()メソッドが4つの要素を返すこと</li>
     * </ul>
     */
    @Test
    void 回転状態は4種類定義されていること() {
        // When: Rotationの全要素を取得
        Rotation[] rotations = Rotation.values();

        // Then: 4種類の回転状態が存在すること
        assertThat(rotations).hasSize(4);
        assertThat(rotations).containsExactlyInAnyOrder(
                Rotation.DEG_0,
                Rotation.DEG_90,
                Rotation.DEG_180,
                Rotation.DEG_270
        );
    }

    /**
     * 時計回り回転が正しく動作することを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>0° → 90° → 180° → 270° → 0° のサイクルを確認</li>
     * </ul>
     */
    @Test
    void 時計回りに回転できること() {
        // Given/When/Then: 0° → 90°
        assertThat(Rotation.DEG_0.clockwise()).isEqualTo(Rotation.DEG_90);

        // Then: 90° → 180°
        assertThat(Rotation.DEG_90.clockwise()).isEqualTo(Rotation.DEG_180);

        // Then: 180° → 270°
        assertThat(Rotation.DEG_180.clockwise()).isEqualTo(Rotation.DEG_270);

        // Then: 270° → 0° (1周して元に戻る)
        assertThat(Rotation.DEG_270.clockwise()).isEqualTo(Rotation.DEG_0);
    }

    /**
     * 反時計回り回転が正しく動作することを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>0° → 270° → 180° → 90° → 0° のサイクルを確認</li>
     * </ul>
     */
    @Test
    void 反時計回りに回転できること() {
        // Given/When/Then: 0° → 270°
        assertThat(Rotation.DEG_0.counterclockwise()).isEqualTo(Rotation.DEG_270);

        // Then: 270° → 180°
        assertThat(Rotation.DEG_270.counterclockwise()).isEqualTo(Rotation.DEG_180);

        // Then: 180° → 90°
        assertThat(Rotation.DEG_180.counterclockwise()).isEqualTo(Rotation.DEG_90);

        // Then: 90° → 0° (1周して元に戻る)
        assertThat(Rotation.DEG_90.counterclockwise()).isEqualTo(Rotation.DEG_0);
    }

    /**
     * 複数回回転させた結果が正しいことを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>4回時計回りに回転すると元に戻ること</li>
     *   <li>4回反時計回りに回転すると元に戻ること</li>
     * </ul>
     */
    @Test
    void 複数回回転させた結果が正しいこと() {
        // Given: 初期状態は0°
        Rotation rotation = Rotation.DEG_0;

        // When: 4回時計回りに回転
        rotation = rotation.clockwise().clockwise().clockwise().clockwise();

        // Then: 元の0°に戻る
        assertThat(rotation).isEqualTo(Rotation.DEG_0);

        // When: 4回反時計回りに回転
        rotation = rotation.counterclockwise().counterclockwise()
                .counterclockwise().counterclockwise();

        // Then: 元の0°に戻る
        assertThat(rotation).isEqualTo(Rotation.DEG_0);
    }

    /**
     * 時計回りと反時計回りが互いに逆操作であることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>時計回り後に反時計回りすると元に戻ること</li>
     * </ul>
     */
    @Test
    void 時計回りと反時計回りは逆操作であること() {
        // Given: 任意の回転状態（90°）
        Rotation rotation = Rotation.DEG_90;

        // When: 時計回り → 反時計回り
        Rotation result = rotation.clockwise().counterclockwise();

        // Then: 元の状態に戻る
        assertThat(result).isEqualTo(rotation);
    }
}
