

if(fade_in){
	if(alpha_fade >= alpha_min){
		alpha_fade = max(0.0, alpha_fade - fade_delta);
	}else{
		fade_in = false;
	}
}else{
	if(timer_sleep >= 0){
		timer_sleep--;
	}else{
		if(alpha_fade <= alpha_max){
			alpha_fade = min(1, alpha_fade + fade_delta);
		}else{
			room_goto(next_room);
		}
	}
}

