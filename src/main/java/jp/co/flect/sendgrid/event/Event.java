package jp.co.flect.sendgrid.event;

public enum Event {
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
