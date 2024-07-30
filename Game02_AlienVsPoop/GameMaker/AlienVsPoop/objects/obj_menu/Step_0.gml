if(keyboard_check_pressed(vk_up))
	up = true;
else
	up = false;

if(keyboard_check_pressed(vk_down))
	down = true;
else
	down = false;

if(keyboard_check_pressed(vk_enter))
	enter = true;
else
	enter = false;
			

if (up){
	index--;

	if(index < 0)
		index = op_max-1;	
}


if (down){
	index++;

	if(index >= op_max)
		index = 0;
}

if (enter){
	switch(index) {
		//"novo jogo"
		case 0:
			room_goto_next();
		break
	
		//"carregar jogo"
		case 1:
		break
	
		//"opções"
		case 2:
		break
	
		//"sair"
		case 3:
			game_end();
		break	
	}	
}