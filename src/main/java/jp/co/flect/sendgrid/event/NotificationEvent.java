package jp.co.flect.sendgrid.event;

public enum NotificationEvent {
	Processed,
	Dropped,
	Deferred,
	Delivered,
	Bounce,
	Click,
	Open,
	Unsubscribe,
	SpamReport
}
