//Desenhando a Entidade usando o eixo Z
draw_sprite_ext(sprite_index, image_index, x, y + z, face, image_yscale, image_angle, image_blend, image_alpha);

// Desenhando a Barra de Vida
if (life > 0) {
	var _vida = (life / max_life) * 100;	
	draw_healthbar(x-15, y+z-60, x+15, y+z - 65, _vida, c_black, c_red, c_green, false, true, true);
}

if(obj_game_rules.show_hitbox){
	
	// Desenha HitBox da Entidade
	hitbox.desenha_hitbox();
	
	if (is_struct(atacar)){
		// Desenha Area de Verificação do Ataque
		atacar.desenha_area_verificao();
	
		// Mostra HitBox do Ataque
		atacar.desenha_area_ataque();
	}
	
}
