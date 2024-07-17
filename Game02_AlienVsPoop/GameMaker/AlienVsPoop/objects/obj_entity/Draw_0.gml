/// @description Inserir descrição aqui
// Você pode escrever seu código neste editor


//Desenhando a Entidade usando o eixo Z
draw_sprite_ext(sprite_index, image_index, x, y + z, face, image_yscale, image_angle, image_blend, image_alpha);

//Desenha HitBox da Entidade
draw_rectangle(hitbox_x, hitbox_y, hitbox_x + tam_hitbox_x, hitbox_y + tam_hitbox_y, true);

// Mostra HitBox do Ataque
if (is_struct(atacar)){
	atacar.desenha_area();
}