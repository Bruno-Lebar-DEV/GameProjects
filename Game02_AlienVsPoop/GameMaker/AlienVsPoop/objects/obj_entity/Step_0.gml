/// @description Inserir descrição aqui
// Você pode escrever seu código neste editor

if (velx != 0) {
	face = sign(velx);
}

estado();

hitbox_x = x - (tam_hitbox_x/2);
hitbox_y = y+z - (tam_hitbox_y + (tam_hitbox_y * (1-hitbox_scale)));