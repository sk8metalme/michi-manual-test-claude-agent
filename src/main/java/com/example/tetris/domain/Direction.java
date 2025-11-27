package com.example.tetris.domain;

/**
 * テトリミノの移動方向を表す列挙型。
 *
 * <p>ユーザーの操作によるテトリミノの移動方向を定義します。</p>
 *
 * <h3>方向の種類：</h3>
 * <ul>
 *   <li>LEFT: 左移動</li>
 *   <li>RIGHT: 右移動</li>
 *   <li>DOWN: 下移動（ソフトドロップ）</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public enum Direction {
    /**
     * 左方向への移動。
     */
    LEFT,

    /**
     * 右方向への移動。
     */
    RIGHT,

    /**
     * 下方向への移動（ソフトドロップ）。
     */
    DOWN
}
