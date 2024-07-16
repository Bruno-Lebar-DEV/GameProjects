/// @description Inserir descrição aqui
// Você pode escrever seu código neste editor

// Inherit the parent event
event_inherited();

up		= noone;
down		= noone;
left		= noone;
right	= noone;
jump		= noone;
punch	= noone;
kick		= noone;

atacar = noone;

controla_player = function(){
	// Define teclas de controle
	up		= keyboard_check(ord("W"));
	down		= keyboard_check(ord("S"));
	left		= keyboard_check(ord("A"));
	right	= keyboard_check(ord("D"));
	punch	= keyboard_check(ord("J"));	
	kick		= keyboard_check(ord("K"));
	jump		= keyboard_check_pressed(vk_space);	
	
	// Movimenta o Player
	velx = (right - left) * max_velx;
	vely = (down - up) * max_vely;	
}

// Criando estados do player
estado_idle = function(){
	sprite_index = spr_player_idle;
	
	controla_player();
	
	if (velx != 0 or vely != 0)
		estado = estado_walk;
	
	if(jump)
		estado = estado_jump;
	
	if(punch)
		estado = estado_jab;
	
	if(kick)
		estado = estado_kick;
}


estado_walk = function(){
	sprite_index = spr_player_walk;

	controla_player();

	if (velx == 0 && vely == 0)
		estado = estado_idle;	
	
	if(jump)
		estado = estado_jump;
	
	if(punch)
		estado = estado_punch;
	
	if(kick)
		estado = estado_kick;
}

estado_jump = function(){
	
	if(sprite_index != spr_player_jump && velz <= 0){

		if(sprite_index != spr_player_jump_punch){
			velz = - max_velz;
			sprite_index = spr_player_jump;
			image_index = 0;
		}

	}

	if (image_index > image_number - 1)
		image_index = image_number - 1;

	
	controla_player();

	aplica_gravidade(grav);
	
	
	if (velz > 0)
		sprite_index = spr_player_dive;
	
	if (punch)
		estado = estado_jump_punch;

	if (kick)
		estado = estado_jump_kick;
}


estado_dive = function(){
	sprite_index = spr_player_dive;
	
	aplica_gravidade(grav*2);
}


estado_punch = function(){
	
	velx = 0;
	vely = 0;
	_combo = keyboard_check(ord("J"));

	if (sprite_index != spr_player_punch){
		image_index = 0;
		sprite_index = spr_player_punch;
	}
	
	if(image_index >= image_number-1){	

		if (_combo){
			estado = estado_jab;
		}else{
			estado = estado_idle;
		}		
	}
}

estado_jab = function(){

	velx = 0;
	vely = 0;
	_combo = keyboard_check(ord("J"));

	if (sprite_index != spr_player_jab){
		image_index = 0;
		sprite_index = spr_player_jab;
	}
	
	if(image_index >= image_number-1){
	
	if (_combo){
			estado = estado_punch;
		}else{
			estado = estado_idle;
		}		
	}
	
}

estado_kick = function(){
	velx = 0;
	vely = 0;
	
	if (sprite_index != spr_player_kick){
		image_index = 0;
		sprite_index = spr_player_kick;
	}
	
	if(image_index >= image_number-1)
		estado = estado_idle;	
}


estado_jump_punch = function(){
	vely = 0;
	velz = 0;
	
	aplica_gravidade(grav*2);
	
	if (sprite_index != spr_player_jump_punch){
		image_index = 0;
		sprite_index = spr_player_jump_punch;
	}
	
	if (image_index >= image_number -1){
		estado = estado_dive;
	}
}


estado_jump_kick = function(){
	vely = 0;

	
	if (sprite_index != spr_player_jump_kick){
		image_index = 0;
		sprite_index = spr_player_jump_kick;
	}
	
	if (sprite_index == spr_player_jump_kick){
		image_index = image_number - 1;
		aplica_gravidade(grav*2);
		velx = face * max_velx/2;
	}	
}

ajusta_fundo = function(){
	var _background = layer_get_id("Background");
	
	var _x = camera_get_view_x(view_camera[0]);
	var _y = camera_get_view_y(view_camera[0]);

	layer_x(_background, _x / 4);
	layer_y(_background, _y / 4);
}




estado = estado_idle;