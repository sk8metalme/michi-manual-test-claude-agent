package com.example.tetris.presentation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket通信とSTOMPメッセージングの設定クラス。
 *
 * <p>このクラスはTetrisゲームのリアルタイム通信を実現するための設定を提供します。</p>
 *
 * <h3>主な設定内容：</h3>
 * <ul>
 *   <li>WebSocketエンドポイント：{@code /ws/game}</li>
 *   <li>STOMPメッセージブローカー：{@code /topic} プレフィックス</li>
 *   <li>アプリケーション送信先：{@code /app} プレフィックス</li>
 *   <li>CORS設定：フロントエンド接続許可</li>
 * </ul>
 *
 * <h3>通信フロー：</h3>
 * <ol>
 *   <li>クライアントが {@code /ws/game} エンドポイントに接続</li>
 *   <li>クライアントが {@code /app/game/*} にメッセージ送信</li>
 *   <li>サーバーが {@code /topic/game/*} にブロードキャスト</li>
 * </ol>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-26
 * @see EnableWebSocketMessageBroker
 * @see WebSocketMessageBrokerConfigurer
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * STOMPエンドポイントを登録します。
     *
     * <p>クライアントはこのエンドポイントに接続してWebSocket通信を開始します。</p>
     *
     * @param registry STOMPエンドポイント登録用レジストリ
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/game")
                .setAllowedOriginPatterns("*")  // CORS設定：すべてのオリジンを許可（開発用）
                .withSockJS();                  // SockJS fallback有効化
    }

    /**
     * メッセージブローカーを設定します。
     *
     * <p>STOMPメッセージの送信先プレフィックスを定義します：</p>
     * <ul>
     *   <li>{@code /app}：クライアントからサーバーへのメッセージ送信先</li>
     *   <li>{@code /topic}：サーバーからクライアントへのブロードキャスト先</li>
     * </ul>
     *
     * @param registry メッセージブローカー設定用レジストリ
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}
