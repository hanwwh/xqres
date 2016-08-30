package com.restaurant.system.bean;

public class WeixinMessageBean {

	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private String MsgType;
	private String Content;
	private String MsgId;
	
	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		buffer.append("toUserName: ").append(ToUserName)
			  .append(", fromUserName: ").append(FromUserName)
			  .append(", createTime: ").append(CreateTime)
			  .append(", msgType: ").append(MsgType)
			  .append(", content: ").append(Content)
			  .append(", msgId: ").append(MsgId);
		
		return buffer.toString();
	}
}
