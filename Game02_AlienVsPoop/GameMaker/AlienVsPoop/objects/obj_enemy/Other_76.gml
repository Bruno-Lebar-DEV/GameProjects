/// @description Inserir descrição aqui
// Você pode escrever seu código neste editor


var _data = event_data[? "event_type"];
var _msg	= event_data[? "message"];

if (_data == "sprite event"){
	
	if ( _msg == "atacar"){
		
		var _x1, _y1, _x2, _y2;
		_x1 = - sprite_xoffset + sprite_get_bbox_left(sprite_index);
		_y1 = - sprite_yoffset + sprite_get_bbox_top(sprite_index);
		_x2 = - sprite_xoffset + sprite_get_bbox_right(sprite_index);
		_y2 = - sprite_yoffset + sprite_get_bbox_bottom(sprite_index);
		
		atacar = new Dano(x + _x1, y + z + _y1, x + (_x2 * face),y + z + _y2);
	}
}