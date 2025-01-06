
estado = noone;
alvo   = noone;

alvo_cutscene1 = noone;
alvo_cutscene2 = noone;

// Estado Parado
estado_idle = function(){
	
	break_object();
	
	var _inimigos = obj_game.inimigos_vivos;
	
	
	if(_inimigos <= 0){
		estado = estado_follow_player;
	}
	
	if(keyboard_check_pressed(vk_enter)){
		estado = estado_follow_player;	
	}

}

estado_follow_player = function(){
	
	if (instance_exists(obj_player)){
		alvo = obj_player;
	}else{
		estado = estado_idle;
		exit;
	}
	
	x = lerp(x, alvo.x, 0.04);
	y = lerp(y, alvo.y + alvo.z, 0.04);

	if(keyboard_check_pressed(vk_enter)){
		estado = estado_idle;	
	}
}

break_object = function(_obj = obj_player){
	
	var _x1 = camera_get_view_x(view_camera[0]);
	var _y1 = camera_get_view_y(view_camera[0]);

	var _x2 = _x1 + camera_get_view_width(view_camera[0]);
	var _y2 = _y1 + camera_get_view_height(view_camera[0]);

	with(_obj){
		var _borda = sprite_width / 2;
		x = clamp(x, _x1 + _borda, _x2 - _borda);
		y = clamp(y, _y1, _y2);
	}

}

estado_cutscene = function(){	

	if(instance_exists(alvo_cutscene1)){
		
		var _x1 = alvo_cutscene1.x;
		var _y1 = alvo_cutscene1.y + alvo_cutscene1.z;
			
		if (distance_to_point(_x1, _y1) > 23.0){
			x = lerp(x, _x1, 0.02);
			y = lerp(y, _y1, 0.028);
		}else{
			alvo_cutscene1 = noone;
		}
		exit;
	}	
	
	if(instance_exists(alvo_cutscene2)){
		var _x2 = alvo_cutscene2.x;
		var _y2 = alvo_cutscene2.y + alvo_cutscene2.z;
	
		if (distance_to_point(_x2, _y2) > 23.0){
			x = lerp(x, _x2, 0.3);
			y = lerp(y, _y2, 0.3);
		}else{
			alvo_cutscene2 = noone;
		}
		exit;
	}
	
	obj_game.cutscene = false;
	estado = estado_idle;
	
}

// FUNÇÃO QUE AJUSTA O FUNDO (EFEITO PARALAX)
ajusta_fundo = function(){
	var _background = layer_get_id("Background");
	
	var _x = camera_get_view_x(view_camera[0]);
	var _y = camera_get_view_y(view_camera[0]);

	layer_x(_background, _x / 4);
	layer_y(_background, _y / 4);
}


estado = estado_follow_player;