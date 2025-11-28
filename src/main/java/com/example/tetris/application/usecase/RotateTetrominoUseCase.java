package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;

/**
 * テトリミノ回転ユースケースのインターフェース。
 *
 * <p>このユースケースは、WebSocketセッションIDに紐づくゲーム状態を管理し、
 * テトリミノを時計回りに90度回転します。</p>
 *
 * <h3>責務:</h3>
 * <ul>
 *   <li>セッションIDベースのゲーム状態管理</li>
 *   <li>GameState.rotateTetromino()を呼び出してテトリミノを回転</li>
 *   <li>回転後の衝突判定結果のハンドリング（回転可/不可）</li>
 *   <li>ドメインモデルをGameStateDTOに変換</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public interface RotateTetrominoUseCase {

    /**
     * テトリミノを時計回りに90度回転します。
     *
     * <p>このメソッドは以下の処理を実行します：</p>
     * <ol>
     *   <li>セッションIDに紐づくGameStateを取得（存在しない場合は初期化）</li>
     *   <li>GameState.rotateTetromino()でテトリミノを回転</li>
     *   <li>回転後のGameStateをセッション管理領域に保存</li>
     *   <li>GameStateをGameStateDTOに変換して返却</li>
     * </ol>
     *
     * <p><strong>回転可否の判定:</strong></p>
     * <ul>
     *   <li>回転可能な場合：回転後の向きのテトリミノを持つGameStateを返す</li>
     *   <li>回転不可能な場合（境界外、衝突）：元のGameStateをそのまま返す</li>
     * </ul>
     *
     * @param sessionId WebSocketセッションID（非null）
     * @return 回転後のゲーム状態のDTO
     * @throws NullPointerException sessionIdがnullの場合
     */
    GameStateDTO execute(String sessionId);
}
