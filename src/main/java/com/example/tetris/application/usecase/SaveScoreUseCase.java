package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.dto.ScoreDTO;

/**
 * スコア保存ユースケースのインターフェース。
 *
 * <p>このユースケースは、ゲームオーバー時に最終スコアをデータベースに保存します。</p>
 *
 * <h3>責務:</h3>
 * <ul>
 *   <li>GameStateDTOからスコア情報を抽出</li>
 *   <li>タイムスタンプの自動付与</li>
 *   <li>ScoreEntityを生成してリポジトリに保存</li>
 *   <li>保存結果をScoreDTOに変換して返却</li>
 * </ul>
 *
 * <h3>要件:</h3>
 * <ul>
 *   <li>要件7.1: ゲームオーバー時に最終スコア、レベル、消去ライン数を保存</li>
 *   <li>要件7.2: スコア保存時にタイムスタンプを付与</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 */
public interface SaveScoreUseCase {

    /**
     * ゲーム状態からスコアを保存します。
     *
     * <p>このメソッドは以下の処理を実行します：</p>
     * <ol>
     *   <li>GameStateDTOからスコア、レベル、消去ライン数を抽出</li>
     *   <li>現在時刻をタイムスタンプとして付与</li>
     *   <li>ScoreEntityを生成</li>
     *   <li>ScoreRepositoryPortを介してデータベースに保存</li>
     *   <li>保存結果をScoreDTOに変換して返却</li>
     * </ol>
     *
     * @param gameStateDTO ゲーム状態DTO（スコア、レベル、消去ライン数を含む）
     * @return 保存されたスコアDTO（IDとタイムスタンプが付与済み）
     */
    ScoreDTO saveScore(GameStateDTO gameStateDTO);
}
