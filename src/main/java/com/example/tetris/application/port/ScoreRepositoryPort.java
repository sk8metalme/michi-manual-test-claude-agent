package com.example.tetris.application.port;

import com.example.tetris.domain.Score;

import java.util.List;

/**
 * スコアリポジトリのポートインターフェース。
 *
 * <p>ヘキサゴナルアーキテクチャにおける「ポート」として、
 * ドメイン層がデータ永続化層に依存しないようにするためのインターフェースです。</p>
 *
 * <h3>責務:</h3>
 * <ul>
 *   <li>スコアドメインモデルの保存</li>
 *   <li>過去10件の高スコア取得（降順）</li>
 * </ul>
 *
 * <h3>実装:</h3>
 * <p>このインターフェースは、{@code adapter.outbound}パッケージの
 * ScoreRepositoryAdapterによって実装されます。</p>
 *
 * <h3>クリーンアーキテクチャ:</h3>
 * <ul>
 *   <li>ドメインモデル（Score）を使用</li>
 *   <li>永続化の詳細（ScoreEntity）はアダプター層で隠蔽</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 * @see Score
 */
public interface ScoreRepositoryPort {

    /**
     * スコアドメインモデルをデータベースに保存します。
     *
     * <p>新規保存時はIDが自動採番されます。
     * タイムスタンプは呼び出し側で設定済みである必要があります。</p>
     *
     * @param score 保存するスコアドメインモデル
     * @return 保存されたスコアドメインモデル（IDが採番済み）
     */
    Score save(Score score);

    /**
     * 過去10件の高スコアを降順で取得します。
     *
     * <p>スコアが同点の場合は、タイムスタンプの新しい順に表示されます。</p>
     *
     * @return 過去10件のスコアドメインモデルリスト（スコア降順）
     */
    List<Score> findTop10ByOrderByScoreDesc();
}
