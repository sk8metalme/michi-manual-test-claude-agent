package com.example.tetris.domain;

/**
 * Tetrisゲームで使用されるテトリミノの7種類の型を定義する列挙型。
 *
 * <p>各テトリミノ型は固有の形状を持ち、4つのブロックで構成されます。</p>
 *
 * <h3>テトリミノの種類：</h3>
 * <ul>
 *   <li>{@link #I} - 棒型（4ブロックが一直線）</li>
 *   <li>{@link #O} - 正方形型（2×2の正方形）</li>
 *   <li>{@link #T} - T字型</li>
 *   <li>{@link #S} - S字型</li>
 *   <li>{@link #Z} - Z字型</li>
 *   <li>{@link #J} - J字型</li>
 *   <li>{@link #L} - L字型</li>
 * </ul>
 *
 * <h3>使用例：</h3>
 * <pre>{@code
 * TetrominoType type = TetrominoType.I;
 * if (type == TetrominoType.O) {
 *     // O型テトリミノの処理
 * }
 * }</pre>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public enum TetrominoType {
    /**
     * I型テトリミノ（棒型）。
     *
     * <p>形状：4つのブロックが水平または垂直に一直線に並ぶ。</p>
     * <pre>
     * □□□□  または  □
     *              □
     *              □
     *              □
     * </pre>
     */
    I,

    /**
     * O型テトリミノ（正方形型）。
     *
     * <p>形状：2×2の正方形。回転しても形状が変わらない。</p>
     * <pre>
     * □□
     * □□
     * </pre>
     */
    O,

    /**
     * T型テトリミノ（T字型）。
     *
     * <p>形状：T字の形状。</p>
     * <pre>
     * □□□
     *  □
     * </pre>
     */
    T,

    /**
     * S型テトリミノ（S字型）。
     *
     * <p>形状：S字の形状。</p>
     * <pre>
     *  □□
     * □□
     * </pre>
     */
    S,

    /**
     * Z型テトリミノ（Z字型）。
     *
     * <p>形状：Z字の形状。</p>
     * <pre>
     * □□
     *  □□
     * </pre>
     */
    Z,

    /**
     * J型テトリミノ（J字型）。
     *
     * <p>形状：J字の形状。</p>
     * <pre>
     * □
     * □□□
     * </pre>
     */
    J,

    /**
     * L型テトリミノ（L字型）。
     *
     * <p>形状：L字の形状。</p>
     * <pre>
     *     □
     * □□□
     * </pre>
     */
    L
}
