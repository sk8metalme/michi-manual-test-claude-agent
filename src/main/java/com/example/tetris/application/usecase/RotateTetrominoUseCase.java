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
 * <h3>セッション管理ポリシー:</h3>
 * <ul>
 *   <li>セッション作成: execute()呼び出し時に自動作成</li>
 *   <li>セッション削除: WebSocketセッション切断時にremoveSession()を呼び出す</li>
 *   <li>最大セッション数: 10000（上限に達した場合はIllegalStateExceptionをスロー）</li>
 * </ul>
 *
 * <h3>メモリ管理の注意:</h3>
 * <p><strong>重要:</strong> WebSocketセッション切断時に必ずremoveSession()を呼び出してください。
 * これを怠ると、セッションがメモリに蓄積し、メモリリークが発生します。</p>
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
     * @param sessionId WebSocketセッションID（非null、非空、最大256文字）
     * @return 回転後のゲーム状態のDTO
     * @throws NullPointerException sessionIdがnullの場合
     * @throws IllegalArgumentException sessionIdが空文字列、または256文字を超える場合
     * @throws IllegalStateException 最大セッション数（10000）に達している場合
     */
    GameStateDTO execute(String sessionId);

    /**
     * セッションを削除します。
     *
     * <p>WebSocketセッション切断時に呼び出してください。
     * セッションが存在しない場合は何も行いません。</p>
     *
     * <p><strong>重要:</strong> メモリリークを防ぐため、
     * WebSocketセッション切断イベントハンドラーで必ずこのメソッドを呼び出してください。</p>
     *
     * @param sessionId 削除するセッションID（非null）
     * @throws NullPointerException sessionIdがnullの場合
     */
    void removeSession(String sessionId);
}
