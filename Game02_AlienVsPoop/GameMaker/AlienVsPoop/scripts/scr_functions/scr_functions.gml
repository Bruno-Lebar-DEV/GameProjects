// Os recursos de script mudaram para a v2.3.0; veja
// https://help.yoyogames.com/hc/en-us/articles/360005277377 para obter mais informações
function scr_functions(){
	
}

 function aplica_gravidade(_grav = 0){
	
	z += velz;
	
	if (z < 0){
		velz += _grav;
	} else {
		velz = 0;
		z = 0;
		estado = estado_idle;
	}
}


function Dano(_x1 = 0, _y1 = 0, _x2 = 0, _y2 = 0) constructor{
	x1 = _x1;
	y1 = _y1;
	x2 = _x2;
	y2 = _y2;

	// Debug da caixa de dano
	static desenha_area = function(){
		draw_rectangle(x1, y1, x2, y2, true);
	}
}