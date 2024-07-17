/// @description Inserir descrição aqui
// Você pode escrever seu código neste editor

var _data = event_data[? "event_type"];
var _msg	= event_data[? "message"];
var _elem = event_data[? "element_id"];

if (layer_instance_get_instance(_elem) ==id){
	if (_data == "sprite event"){
	
		if ( _msg == "atacar"){
	
			var _x1, _y1, _x2, _y2;
			_x1 = (sprite_get_bbox_left(sprite_index)	- sprite_xoffset) * face;
			_y1 = (sprite_get_bbox_top(sprite_index)	- sprite_yoffset);
			_x2 = (sprite_get_bbox_right(sprite_index)	- sprite_xoffset) * face;
			_y2 = (sprite_get_bbox_bottom(sprite_index)	- sprite_yoffset);
		
			atacar = new Dano(x + _x1, y + z + _y1, x + _x2 ,y + z + _y2);
		}
	}
}


