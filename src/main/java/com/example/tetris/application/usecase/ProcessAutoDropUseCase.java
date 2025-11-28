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
     * @param sessionId WebSocketセッションID（非null）
     * @return 自動落下処理後のゲーム状態のDTO
     * @throws NullPointerException sessionIdがnullの場合
     */
    GameStateDTO execute(String sessionId);
}
