package com.example.tetris.domain;

/**
 * テトリミノの回転状態を表す列挙型。
 *
 * <p>Tetrisでは、テトリミノは4つの回転状態（0°, 90°, 180°, 270°）を取ります。
 * この列挙型は回転操作とその状態管理を提供します。</p>
 *
 * <h3>回転状態の遷移：</h3>
 * <pre>
 * 時計回り（clockwise）:
 * 0° → 90° → 180° → 270° → 0° (循環)
 *
 * 反時計回り（counterclockwise）:
 * 0° → 270° → 180° → 90° → 0° (循環)
 * </pre>
 *
 * <h3>使用例：</h3>
 * <pre>{@code
 * Rotation rotation = Rotation.DEG_0;
 *
 * // 時計回りに90度回転
 * rotation = rotation.clockwise();  // → DEG_90
 *
 * // さらに時計回りに回転
 * rotation = rotation.clockwise();  // → DEG_180
 *
 * // 反時計回りに回転
 * rotation = rotation.counterclockwise();  // → DEG_90
 * }</pre>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public enum Rotation {
    /**
     * 0度（初期状態、回転なし）。
     */
    DEG_0,

    /**
     * 90度（時計回りに1回回転）。
     */
    DEG_90,

    /**
     * 180度（時計回りに2回回転）。
     */
    DEG_180,

    /**
     * 270度（時計回りに3回回転、または反時計回りに1回回転）。
     */
    DEG_270;

    /**
     * 時計回りに90度回転した次の回転状態を返します。
     *
     * <p>回転状態は循環します：</p>
     * <ul>
     *   <li>DEG_0 → DEG_90</li>
     *   <li>DEG_90 → DEG_180</li>
     *   <li>DEG_180 → DEG_270</li>
     *   <li>DEG_270 → DEG_0 (元に戻る)</li>
     * </ul>
     *
     * @return 時計回りに90度回転した回転状態
     */
    public Rotation clockwise() {
        return switch (this) {
            case DEG_0 -> DEG_90;
            case DEG_90 -> DEG_180;
            case DEG_180 -> DEG_270;
            case DEG_270 -> DEG_0;
        };
    }

    /**
     * 反時計回りに90度回転した次の回転状態を返します。
     *
     * <p>回転状態は循環します：</p>
     * <ul>
     *   <li>DEG_0 → DEG_270</li>
     *   <li>DEG_270 → DEG_180</li>
     *   <li>DEG_180 → DEG_90</li>
     *   <li>DEG_90 → DEG_0 (元に戻る)</li>
     * </ul>
     *
     * @return 反時計回りに90度回転した回転状態
     */
    public Rotation counterclockwise() {
        return switch (this) {
            case DEG_0 -> DEG_270;
            case DEG_270 -> DEG_180;
            case DEG_180 -> DEG_90;
            case DEG_90 -> DEG_0;
        };
    }
}
