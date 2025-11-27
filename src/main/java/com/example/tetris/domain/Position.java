package com.example.tetris.domain;

/**
 * ゲームフィールド上の位置を表す不変レコードクラス。
 *
 * <p>Positionは2次元座標系における(x, y)座標を保持します。
 * Java 17のRecord機能により、以下のメソッドが自動生成されます：</p>
 * <ul>
 *   <li>{@link #x()} - x座標の取得</li>
 *   <li>{@link #y()} - y座標の取得</li>
 *   <li>{@link #equals(Object)} - 等価性の比較</li>
 *   <li>{@link #hashCode()} - ハッシュコードの生成</li>
 *   <li>{@link #toString()} - 文字列表現の生成</li>
 * </ul>
 *
 * <h3>座標系の定義：</h3>
 * <pre>
 * (0,0) → x軸（右方向が正）
 *   ↓
 *  y軸（下方向が正）
 * </pre>
 *
 * <h3>使用例：</h3>
 * <pre>{@code
 * Position pos1 = new Position(3, 5);
 * Position pos2 = new Position(3, 5);
 *
 * // 自動生成されたequals()メソッドによる比較
 * if (pos1.equals(pos2)) {
 *     System.out.println("同じ位置です");
 * }
 *
 * // 座標値の取得
 * int x = pos1.x();
 * int y = pos1.y();
 * }</pre>
 *
 * <h3>不変性（Immutability）：</h3>
 * <p>Positionは不変オブジェクトです。一度作成されたら座標値を変更できません。
 * 座標を移動する場合は、新しいPositionインスタンスを作成します。</p>
 *
 * @param x ゲームフィールド上のx座標（水平方向、右が正）
 * @param y ゲームフィールド上のy座標（垂直方向、下が正）
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public record Position(int x, int y) {
    // Record宣言により、以下が自動生成されます：
    // - private final int x;
    // - private final int y;
    // - public int x()
    // - public int y()
    // - public boolean equals(Object o)
    // - public int hashCode()
    // - public String toString()
}
