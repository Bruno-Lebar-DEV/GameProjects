
if (velx != 0) {
	face = sign(velx);
}

if(global.pause){
	image_speed = 0;
	velx = 0;
	vely = 0;
	exit;
}else{
	image_speed = 1;
}

if(obj_game.cutscene == true){
	velx = 0;
	vely = 0;
	exit;	
}



estado();

if (is_struct(hitbox)){
	hitbox.atualiza_posicao_hitbox(x, y+z);
}

if (is_struct(atacar)){
	atacar.atualiza_posicao();
	atacar.verifica_area_colisao();
}