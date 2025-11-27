package com.example.tetris.domain;

/**
 * ライン消去処理の結果を表す不変レコードクラス。
 *
 * <p>ClearResultは、{@link GameField#clearLines()}メソッドの戻り値として使用され、
 * ライン消去後のフィールド状態と消去されたライン数を保持します。</p>
 *
 * <h3>消去ライン数の範囲：</h3>
 * <ul>
 *   <li>0: ライン消去なし</li>
 *   <li>1: シングル（100点）</li>
 *   <li>2: ダブル（300点）</li>
 *   <li>3: トリプル（500点）</li>
 *   <li>4: テトリス（800点）</li>
 * </ul>
 *
 * <h3>使用例：</h3>
 * <pre>{@code
 * GameField field = GameField.createEmpty();
 * // ... テトリミノを配置 ...
 * ClearResult result = field.clearLines();
 *
 * GameField updatedField = result.updatedField();
 * int linesCleared = result.clearedLineCount();
 *
 * if (linesCleared > 0) {
 *     System.out.println(linesCleared + "ライン消去しました！");
 * }
 * }</pre>
 *
 * @param updatedField ライン消去後の新しいGameField
 * @param clearedLineCount 消去されたライン数（0-4）
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public record ClearResult(GameField updatedField, int clearedLineCount) {
    // Record宣言により、以下が自動生成されます：
    // - private final GameField updatedField;
    // - private final int clearedLineCount;
    // - public GameField updatedField()
    // - public int clearedLineCount()
    // - public boolean equals(Object o)
    // - public int hashCode()
    // - public String toString()
}
