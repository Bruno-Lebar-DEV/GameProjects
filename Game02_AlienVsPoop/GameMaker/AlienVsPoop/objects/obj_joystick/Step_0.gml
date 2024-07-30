
//Movendo a bolinha na direção do mouse
var _mouse_x = device_mouse_x_to_gui(0);
var _mouse_y = device_mouse_y_to_gui(0);
var _mouse_click = mouse_check_button(mb_left);
var _mouse_pressed = mouse_check_button_pressed(mb_left);
var _mouse_sobre = point_in_circle(_mouse_x, _mouse_y, x1, y1,(sprite_size/2) * scale );

if (_mouse_sobre || fazer){
	if (_mouse_click){
		fazer = true;
		vel = min( point_distance(x1, y1, _mouse_x, _mouse_y), (sprite_size/2) * scale);
		dir = point_direction(x1, y1, _mouse_x, _mouse_y);	
	}
}

if (!_mouse_click){
	fazer = false;
	vel = 0;
}

x2 = lengthdir_x(vel, dir);
y2 = lengthdir_y(vel, dir);

var _vel_max = (sprite_size/2) * scale ;

velx = (x2 / _vel_max);
vely = (y2 / _vel_max);

