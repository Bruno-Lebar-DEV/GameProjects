timer_sleep = game_get_speed(gamespeed_fps) * sleep_fade;
fade_delta = speed_fade;

alpha_max  = 1 - fade_delta;
alpha_min  = fade_delta;
fade_in	 = true;

alpha_fade = alpha_max;

