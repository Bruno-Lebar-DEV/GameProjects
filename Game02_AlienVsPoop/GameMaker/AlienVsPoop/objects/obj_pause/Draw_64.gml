var _largura = display_get_gui_width();
var _altura = display_get_gui_height();

if(global.pause){	
	draw_set_alpha(0.8);
	draw_set_color(c_black);
	draw_rectangle(0,0,_largura,_altura,false);
	draw_set_alpha(1);
	draw_set_color(c_white);
	draw_set_font(fnt_pause);
	draw_set_halign(fa_center);
	draw_set_valign(fa_middle);
	draw_text(_largura/2,_altura/2,"JOGO PAUSADO!");
	draw_set_font(-1);

}
