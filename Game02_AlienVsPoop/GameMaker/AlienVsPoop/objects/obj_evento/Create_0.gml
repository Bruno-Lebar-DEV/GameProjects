fazer = true;
acao = noone;

qtd_spawn = 1;
txt_msg   = "";

acao_idle = function(){
}

acao_msg = function(){
	//show_message(txt_msg);	
}

acao_spawner = function(){
	var _inimigo = obj_enemy;
	var _qtd_inm = qtd_spawn/2;
	
	var _x = x + 250;
	var _y = y + (sprite_height/4);
	for (var _i = 0; _i < _qtd_inm; _i++){
		_inimigo = instance_create_layer(_x + (_i * 10) , _y + (_i * 20), "Inimigos", obj_enemy);
		_inimigo = instance_create_layer(_x + (_i * 10) + 50 , _y + (_i * 20), "Inimigos", obj_enemy);
		obj_game.inimigos_vivos+=2;
	}
	
	// Pare as entidades
	obj_game.cutscene = true;
	obj_camera.alvo_cutscene1 = _inimigo;
	obj_camera.alvo_cutscene2 = obj_player;
	obj_camera.estado = obj_camera.estado_cutscene;
	
}

switch param_evento{
	case "spawner":	
		acao = acao_spawner;
		qtd_spawn = param_qtd;
	break;
	case "msg":
		acao = acao_msg;
		txt_msg = param_txt;
	break;
}
	