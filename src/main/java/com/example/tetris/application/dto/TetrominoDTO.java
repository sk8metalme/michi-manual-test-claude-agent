package com.example.tetris.application.dto;

/**
 * テトリミノを表すDTO(Data Transfer Object)。
 *
 * <p>ドメインモデルのTetrominoをアプリケーション層で扱うための
 * データ転送オブジェクトです。</p>
 *
 * <h3>フィールド:</h3>
 * <ul>
 *   <li>type: テトリミノのタイプ (I, O, T, S, Z, J, L)</li>
 *   <li>x: X座標 (0-9)</li>
 *   <li>y: Y座標 (0-19)</li>
 *   <li>rotation: 回転角度 (0, 90, 180, 270)</li>
 * </ul>
 *
 * @param type テトリミノのタイプ文字列
 * @param x X座標
 * @param y Y座標
 * @param rotation 回転角度(度数)
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public record TetrominoDTO(
        String type,
        int x,
        int y,
        int rotation
) {
}
