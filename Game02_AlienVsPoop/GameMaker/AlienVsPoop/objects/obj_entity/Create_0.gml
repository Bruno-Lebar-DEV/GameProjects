/// @description Inserir descrição aqui
// Você pode escrever seu código neste editor


velx		= 0;
vely		= 0;
velz		= 0;
max_velx	= 3;
max_vely	= 2;
max_velz	= 5;
grav		= 0.2;
z		= 0;

face		= 1;
estado	= noone;

atacar	= noone;

hitbox_scale = 1;

tam_hitbox_x = sprite_width*hitbox_scale;
tam_hitbox_y = sprite_height*hitbox_scale;

hitbox_x = x - (tam_hitbox_x/2);
hitbox_y = y+z - (tam_hitbox_y + (tam_hitbox_y * (1-hitbox_scale)));


estado_main = function() {
}

estado = estado_main;