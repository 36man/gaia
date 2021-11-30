package org.bravo.gaia.app.boot.i18n;

import java.io.Serializable;

/**
 * 返回给客户端的消息信息
 *
 * @author lijian
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 8117244181993856881L;

    /**
     * 响应状态码，一般自定义
     */
    private int messageCode;
    /**
     * 响应的状态文本信息
     */
    private String messageText;
    /**
     * 响应的数据信息
     */
    private Object data;

    public Message() {
    }

    public Message(int messageCode, String messageText) {
        this(messageCode, messageText, null);
    }

    public Message(int messageCode, String messageText, Object data) {
        this.messageCode = messageCode;
        this.messageText = messageText;
        this.data = data;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
