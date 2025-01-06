
function scr_functions(){
}

// Função Aplica Gravidade e Retorna se a entide tocou o chão 
function aplica_gravidade(_grav = 0){
	
	// aplico gravidade na entidade
	z += velz;
		
	// se a entidade passou do chão
	if (z >= 0){
		
		// zero a velocidade de pulo
		velz = 0;
		
		// volto ele para o chão
		z = 0;
		
		// retorno que ele está no chão
		return true;
	}
	
	// aplico a gravidade na velocidade da entidade
	velz += _grav;
		
	// retorno que ele não chegou no chão
	return false;
}


function Dano(_x1 = 0, _y1 = 0, _x2 = 0, _y2 = 0, _obj = noone) constructor{
	x1 = _x1;
	y1 = _y1;
	x2 = _x2;
	y2 = _y2;
	
	entidade	= _obj;
	lado		= entidade.face;
	
	x1_ataque = entidade.x + (x1 * lado);
	y1_ataque = entidade.y + entidade.z + y1;
	x2_ataque = entidade.x + (x2 * lado);
	y2_ataque = entidade.y + entidade.z + y2;
	
	x1_verificacao = x1_ataque - (10 * lado);
	y1_verificacao = y1_ataque - 40;
	x2_verificacao = x2_ataque + (40 * lado);
	y2_verificacao = y2_ataque + 40;
	
	levaram_dano = [];
	
	// Debug da caixa de dano
	static desenha_area_ataque = function(){
		draw_set_color(c_red);
		draw_rectangle(x1_ataque, y1_ataque, x2_ataque, y2_ataque, true);
		draw_set_color(-1);
	}
	
	// Debug da caixa de Verificação de Colisao
	static desenha_area_verificao = function(){
		draw_set_color(c_blue);
		draw_rectangle(x1_verificacao, y1_verificacao, x2_verificacao, y2_verificacao, true);
		draw_set_color(-1);
	}
	
	static atualiza_posicao = function(){
		x1_ataque = entidade.x + (x1 * lado);
		y1_ataque = entidade.y + entidade.z + y1;
		x2_ataque = entidade.x + (x2 * lado);
		y2_ataque = entidade.y + entidade.z + y2;
	
		x1_verificacao = x1_ataque - (10 * lado);
		y1_verificacao = y1_ataque - 40;
		x2_verificacao = x2_ataque + (40 * lado);
		y2_verificacao = y2_ataque + 40;
	}
	
	static aplica_dano = function(_entidade = noone) {
		if(_entidade.estado != _entidade.estado_hurt){
			if(!array_contains(levaram_dano, _entidade)){
					
				_entidade.estado = _entidade.estado_hurt;
				_entidade.life -= random_range(1,5);

				array_push(levaram_dano, _entidade);

				if(_entidade.life <= 0)
					_entidade.estado = _entidade.estado_dead;
			}
		}
	}
	
	static verifica_area_colisao = function(){

		// CRIO UMA LISTA 
		var _lista = ds_list_create();
		
		// CHECO AS COLISOES E SALVO 
		var _outros = collision_rectangle_list(x1_verificacao, y1_verificacao, x2_verificacao, y2_verificacao, obj_entity, false, false, _lista, true);		
	
		// TAMANHO DA MINHA LISTA
		var _size = ds_list_size(_lista);
		
		
		// PERCORRO MINHA LISTA
		for (var _i = 0; _i < _size; _i++){
			
			// PEGO A ENTIDADE QUE COLIDIU
			var _entidade_colisao = _lista[| _i];
			
			// VERIFICO SE NÃO É O PAI DA ROTINA
			if (_entidade_colisao.object_index != entidade.object_index){
				
				var _other_hitbox = _entidade_colisao.hitbox.get_hitbox();
				
				var _dano = rectangle_in_rectangle(x1_ataque, y1_ataque, x2_ataque, y2_ataque
						,_other_hitbox[0],_other_hitbox[1],_other_hitbox[2],_other_hitbox[3]);
				
				if (_dano){
					aplica_dano(_entidade_colisao);
				}
			}	
		}	
			
	}
}

function HitBox(_x1 = 0, _y1 = 0, _x2 = 0, _y2 = 0, _scale = 1) constructor{
	scale		= _scale
	tam_hitbox_x	= _x2 * scale;
	tam_hitbox_y	= _y2 * scale;
	
	x1_hitbox = _x1 - (tam_hitbox_x/2);
	y1_hitbox = _y1 - (tam_hitbox_y + (tam_hitbox_y * (1-scale)));
	x2_hitbox = x1_hitbox + tam_hitbox_x;
	y2_hitbox = y1_hitbox + tam_hitbox_y;
	
	
	static atualiza_posicao_hitbox = function(_new_x, _new_y){
		x1_hitbox = _new_x - (tam_hitbox_x/2);
		y1_hitbox = _new_y - (tam_hitbox_y + (tam_hitbox_y * (1-scale)));
		x2_hitbox = x1_hitbox + tam_hitbox_x;
		y2_hitbox = y1_hitbox + tam_hitbox_y;
	}
	
	static desenha_hitbox = function(){
		draw_set_color(c_green);
		draw_rectangle(x1_hitbox, y1_hitbox, x2_hitbox, y2_hitbox, true);
		draw_set_color(-1);
	}
	
	static get_hitbox = function() {
		return [x1_hitbox, y1_hitbox, x2_hitbox, y2_hitbox];
	}
	
}
