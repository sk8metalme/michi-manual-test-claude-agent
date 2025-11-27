package com.example.tetris.domain;

/**
 * ゲームフィールド上の1つのブロック（セル）を表す不変レコードクラス。
 *
 * <p>Blockは、固定されたテトリミノの各ブロックを表現します。
 * テトリミノの種類（{@link TetrominoType}）を保持し、描画時の色情報として使用されます。</p>
 *
 * <h3>不変性（Immutability）：</h3>
 * <p>Blockは不変オブジェクトです。一度作成されたら変更できません。</p>
 *
 * <h3>使用例：</h3>
 * <pre>{@code
 * // I型テトリミノのブロックを生成
 * Block block = new Block(TetrominoType.I);
 *
 * // ブロックの型を取得（色情報として使用）
 * TetrominoType type = block.type();
 * }</pre>
 *
 * @param type ブロックが由来するテトリミノの種類（色情報として使用）
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public record Block(TetrominoType type) {
    // Record宣言により、以下が自動生成されます：
    // - private final TetrominoType type;
    // - public TetrominoType type()
    // - public boolean equals(Object o)
    // - public int hashCode()
    // - public String toString()
}
