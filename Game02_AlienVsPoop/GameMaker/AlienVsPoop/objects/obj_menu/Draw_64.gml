draw_set_font(fnt_menu);

var _dist = 60;
var _largura = display_get_gui_width();
var _altura = display_get_gui_height();

var _x1 = _largura / 2;
var _y1 = _altura / 2;


for (var _i = 0; _i < op_max; _i++){
	draw_set_halign(fa_center);
	draw_set_valign(fa_middle);
	
	if(index == _i){
		draw_set_color(c_yellow);
	}else{
		draw_set_color(c_white);	
	}
	draw_text(_x1, _y1 + (_dist * _i),options[_i]);	
}



draw_set_font(-1);