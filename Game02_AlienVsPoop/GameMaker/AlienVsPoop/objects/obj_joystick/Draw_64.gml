if(global.pause)
	exit;

// Desenho o meu circulo joystick
draw_sprite_ext(spr_joystick, 0, x1, y1, scale, scale, 0, c_white, .2); 

// Desenho a borda do circulo joystick
draw_circle(x1, y1, (sprite_size/2) * scale, true);

// Desenhando a bolinha do circulo joystick
draw_sprite_ext(spr_joystick, 0, x1 + x2, y1 + y2, scale/4, scale/4, 0, c_white, .8);

