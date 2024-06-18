package com.example.live.function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pusher.rest.Pusher;


@Component
public class PusherSettings {
	/** Pusher App ID */
	@Value("705473")
    private String appId;

	/** Pusher Key */
	@Value("b9e4d6190581d989a6e2")
    private String key;

	/** Pusher Secret */
	@Value("629f95f4aa4563d80845")
    private String secret;

	/**
	 * Creates a new instance of the Pusher object to use its API
	 *
	 * @return An instance of the Pusher object
	 */
	public Pusher newInstance() {
		return new Pusher(appId, key, secret);
	}

	public String getPusherKey() {
		return key;
	}
}