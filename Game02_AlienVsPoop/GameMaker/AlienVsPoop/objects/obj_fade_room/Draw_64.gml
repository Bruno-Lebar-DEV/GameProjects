var _largura = display_get_gui_width();
var _altura = display_get_gui_height();

draw_set_alpha(alpha_fade);
draw_set_color(c_black);
draw_rectangle(0,0,_largura,_altura,false);
draw_set_alpha(1);
