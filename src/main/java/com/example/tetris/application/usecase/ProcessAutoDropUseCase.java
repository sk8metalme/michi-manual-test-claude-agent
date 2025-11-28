package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;

/**
 * 自動落下処理ユースケースのインターフェース。
 *
 * <p>このユースケースは、WebSocketセッションIDに紐づくゲーム状態を管理し、
 * テトリミノの自動落下処理を実行します。</p>
 *
 * <h3>責務:</h3>
 * <ul>
 *   <li>セッションIDベースのゲーム状態管理</li>
 *   <li>GameState.processAutoDropTick()を呼び出してテトリミノを自動落下</li>
 *   <li>テトリミノ固定時のライン消去処理連携</li>
 *   <li>スコア加算とレベル更新ロジック統合</li>
 *   <li>ドメインモデルをGameStateDTOに変換</li>
 * </ul>
 *
 * <h3>自動落下処理の詳細:</h3>
 * <ul>
 *   <li>テトリミノを1マス下に移動を試みる</li>
 *   <li>移動可能な場合：移動後のGameStateを返す</li>
 *   <li>移動不可能な場合：ハードドロップを実行（固定→ライン消去→スコア計算→レベル更新）</li>
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
 * @since 2025-11-28
 */
public interface ProcessAutoDropUseCase {

    /**
     * 自動落下処理を実行します。
     *
     * <p>このメソッドは以下の処理を実行します：</p>
     * <ol>
     *   <li>セッションIDに紐づくGameStateを取得（存在しない場合は初期化）</li>
     *   <li>GameState.processAutoDropTick()でテトリミノを自動落下</li>
     *   <li>移動不可能な場合は固定→ライン消去→スコア計算→レベル更新</li>
     *   <li>処理後のGameStateをセッション管理領域に保存</li>
     *   <li>GameStateをGameStateDTOに変換して返却</li>
     * </ol>
     *
     * <p><strong>自動落下の動作:</strong></p>
     * <ul>
     *   <li>移動可能な場合：テトリミノを1マス下に移動</li>
     *   <li>移動不可能な場合：
     *     <ul>
     *       <li>テトリミノをフィールドに固定</li>
     *       <li>ライン消去処理を実行</li>
     *       <li>スコアを加算（消去ライン数に応じて）</li>
     *       <li>レベルを更新（累計クリア済みライン数に応じて）</li>
     *       <li>次のテトリミノを生成</li>
     *       <li>ゲームオーバー判定（新規テトリミノが配置不可能な場合）</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * @param sessionId WebSocketセッションID（非null、非空、最大256文字）
     * @return 自動落下処理後のゲーム状態のDTO
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
