package com.example.tetris.application.dto;

import com.example.tetris.domain.GameStatus;

/**
 * ゲーム状態を表すDTO(Data Transfer Object)。
 *
 * <p>ドメインモデルのGameStateをアプリケーション層で扱うための
 * データ転送オブジェクトです。</p>
 *
 * <h3>フィールド:</h3>
 * <ul>
 *   <li>status: ゲームの進行状態</li>
 *   <li>currentTetromino: 現在操作中のテトリミノ</li>
 *   <li>nextTetromino: 次に落下するテトリミノ</li>
 *   <li>field: ゲームフィールドの2次元配列表現</li>
 *   <li>score: 現在のスコア</li>
 *   <li>level: 現在のレベル</li>
 *   <li>totalLinesCleared: 累計クリア済みライン数</li>
 * </ul>
 *
 * @param status ゲームの進行状態
 * @param currentTetromino 現在操作中のテトリミノ
 * @param nextTetromino 次に落下するテトリミノ
 * @param field ゲームフィールドの2次元配列(20行×10列、各セルはTetrominoTypeまたはnull)
 * @param score 現在のスコア
 * @param level 現在のレベル
 * @param totalLinesCleared 累計クリア済みライン数
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public record GameStateDTO(
        GameStatus status,
        TetrominoDTO currentTetromino,
        TetrominoDTO nextTetromino,
        String[][] field,  // TetrominoTypeの文字列表現 or null
        int score,
        int level,
        int totalLinesCleared
) {
}
