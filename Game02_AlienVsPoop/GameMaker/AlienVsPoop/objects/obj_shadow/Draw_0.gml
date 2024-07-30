/// @description Inserir descrição aqui
// Você pode escrever seu código neste editor

with(obj_entity){
	var _scale = z * 0.003;

	// Desenhando a Sombra
	draw_sprite_ext(spr_sombra, 0, x, y, 0.4 + _scale, 0.4 + _scale , 0, c_black, 0.5);
}
