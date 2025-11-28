package com.example.tetris.application.port;

import com.example.tetris.adapter.outbound.ScoreEntity;

import java.util.List;

/**
 * スコアリポジトリのポートインターフェース。
 *
 * <p>ヘキサゴナルアーキテクチャにおける「ポート」として、
 * ドメイン層がデータ永続化層に依存しないようにするためのインターフェースです。</p>
 *
 * <h3>責務:</h3>
 * <ul>
 *   <li>スコアエンティティの保存</li>
 *   <li>過去10件の高スコア取得（降順）</li>
 * </ul>
 *
 * <h3>実装:</h3>
 * <p>このインターフェースは、{@code adapter.outbound}パッケージの
 * ScoreRepositoryAdapterによって実装されます。</p>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 * @see ScoreEntity
 */
public interface ScoreRepositoryPort {

    /**
     * スコアエンティティをデータベースに保存します。
     *
     * <p>新規保存時はIDが自動採番されます。
     * タイムスタンプは呼び出し側で設定済みである必要があります。</p>
     *
     * @param scoreEntity 保存するスコアエンティティ
     * @return 保存されたスコアエンティティ（IDが採番済み）
     */
    ScoreEntity save(ScoreEntity scoreEntity);

    /**
     * 過去10件の高スコアを降順で取得します。
     *
     * <p>スコアが同点の場合は、タイムスタンプの新しい順に表示されます。</p>
     *
     * @return 過去10件のスコアエンティティリスト（スコア降順）
     */
    List<ScoreEntity> findTop10ByOrderByScoreDesc();
}
