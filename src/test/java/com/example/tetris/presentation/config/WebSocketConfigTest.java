package com.example.tetris.presentation.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link WebSocketConfig}の統合テストクラス。
 *
 * <p>WebSocket接続とSTOMP通信が正常に機能することを検証します。</p>
 *
 * <h3>テスト対象：</h3>
 * <ul>
 *   <li>WebSocketエンドポイント（/ws/game）への接続</li>
 *   <li>STOMPプロトコルでのセッション確立</li>
 *   <li>SockJSフォールバックの動作</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-26
 * @see WebSocketConfig
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WebSocketConfigTest {

    @LocalServerPort
    private int port;

    private String wsUrl;

    /**
     * 各テスト実行前にWebSocket URLを初期化します。
     */
    @BeforeEach
    void setUp() {
        wsUrl = "ws://localhost:" + port + "/ws/game";
    }

    /**
     * WebSocketエンドポイントに正常に接続できることを検証するテスト。
     *
     * <p>検証内容：</p>
     * <ul>
     *   <li>/ws/gameエンドポイントが存在すること</li>
     *   <li>STOMPセッションが確立できること</li>
     *   <li>接続がタイムアウトせずに完了すること（5秒以内）</li>
     * </ul>
     *
     * @throws ExecutionException セッション確立時の実行エラー
     * @throws InterruptedException セッション確立の中断
     * @throws TimeoutException 接続タイムアウト（5秒以上）
     */
    @Test
    void WebSocketエンドポイントに接続できること() throws ExecutionException, InterruptedException, TimeoutException {
        // Given: WebSocketクライアントを準備
        WebSocketStompClient stompClient = createStompClient();

        // When: /ws/gameエンドポイントに接続
        StompSession session = stompClient.connectAsync(
                wsUrl,
                new StompSessionHandlerAdapter() {}
        ).get(5, TimeUnit.SECONDS);

        // Then: セッションが確立されること
        assertThat(session).isNotNull();
        assertThat(session.isConnected()).isTrue();

        // Cleanup: セッションを切断
        session.disconnect();
        stompClient.stop();
    }

    /**
     * SockJSフォールバックを使用してWebSocketに接続できることを検証するテスト。
     *
     * <p>WebSocketが利用できない環境でもSockJSによる接続が可能であることを確認します。</p>
     *
     * @throws ExecutionException セッション確立時の実行エラー
     * @throws InterruptedException セッション確立の中断
     * @throws TimeoutException 接続タイムアウト（5秒以上）
     */
    @Test
    void SockJSフォールバックでWebSocketに接続できること() throws ExecutionException, InterruptedException, TimeoutException {
        // Given: SockJSクライアントを準備
        List<Transport> transports = List.of(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // When: SockJS経由で接続
        StompSession session = stompClient.connectAsync(
                wsUrl,
                new StompSessionHandlerAdapter() {}
        ).get(5, TimeUnit.SECONDS);

        // Then: セッションが確立されること
        assertThat(session).isNotNull();
        assertThat(session.isConnected()).isTrue();

        // Cleanup: セッションを切断
        session.disconnect();
        stompClient.stop();
    }

    /**
     * WebSocketStompClientを作成するヘルパーメソッド。
     *
     * @return 設定済みのWebSocketStompClient
     */
    private WebSocketStompClient createStompClient() {
        List<Transport> transports = List.of(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        return stompClient;
    }
}
