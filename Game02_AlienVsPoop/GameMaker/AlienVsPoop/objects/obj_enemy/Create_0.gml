/// @description Inserir descrição aqui
// Você pode escrever seu código neste editor

// Inherit the parent event
event_inherited();

max_velx = 2;
max_vely = 2;

atacar = noone;

// Delay para rodar 
delay_estado =	game_get_speed(gamespeed_fps);
delay_ataque = game_get_speed(gamespeed_fps)/2;
timer_estado = delay_estado;

// Area de Visão
area_visao = 80;
alvo = obj_player;
distancia_ataque = 20;

// Criando os Estados
estado_idle = function(){
	velx = 0;
	vely = 0;
	timer_estado--;

	sprite_index = spr_enemy_idle;	
	
	if (persegue_player()){
		estado = estado_follow;
	} else {	
		if(timer_estado <= 0){
			timer_estado = delay_estado;
			estado = choose(estado_walk,estado_idle);				
		}
	}
}

estado_walk = function() { 
	timer_estado--;
	
	if(sprite_index != spr_enemy_walk){
		sprite_index = spr_enemy_walk;
		image_index = 0;	
		velx = random_range(-1,1);
		vely = random_range(-1,1);
	}
	
	if (persegue_player()){
		estado = estado_follow;
	} else {	
		if(timer_estado <= 0){
			timer_estado = delay_estado;
			estado = choose(estado_walk,estado_idle);				
		}
	}
	
}

estado_follow = function(){
	if (persegue_player() == false)
		show_message("Saio do Campo de Visao");
		//estado = estado_idle;
	
	
	timer_estado--;
	var _dir = point_direction(x, y, alvo.x, alvo.y);
	var _distx = point_distance(x, alvo.y, alvo.x, alvo.y);
	var _disty = point_distance(alvo.x, y, alvo.x, alvo.y);
	
	if (_disty >= 8) 		
		vely = lengthdir_y(choose(0.5,1), _dir);
	else 
		vely = 0;
	
	if (_distx >= distancia_ataque)
		velx = lengthdir_x(choose(0.5,1), _dir);
	 else 
		velx = 0;
		
	
	if (velx != 0 or vely != 0){
		if(sprite_index != spr_enemy_walk ){
			sprite_index = spr_enemy_walk;
			image_index = 0;			
		}
	}else {
		if(sprite_index != spr_enemy_idle){
			sprite_index = spr_enemy_idle;
			image_index = 0;			
		}
	}
	
	// timer para atacar
	if(timer_estado <= 0)
		if(_distx <= distancia_ataque)
			estado = estado_ataque;
}


estado_ataque = function() {
	velx = 0;
	vely = 0;
	if (sprite_index != spr_enemy_attack){
		image_index = 0;
		sprite_index = spr_enemy_attack;
	}	
	
	if(image_index >= image_number-1){
		timer_estado = delay_ataque;
		estado = estado_follow;
	}
}

persegue_player = function(){
	if(alvo == noone) return false;
	
	return collision_circle(x,y,area_visao,alvo,0,1);
}

estado = estado_idle;