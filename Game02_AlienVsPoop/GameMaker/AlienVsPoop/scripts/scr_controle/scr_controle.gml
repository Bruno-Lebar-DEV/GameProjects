function scr_controle(){	
}


function Controle() constructor{
	static is_attack = function(){	
	}
	
	static is_kick = function(){
	}
	
	static is_jump = function(){
	}
	
}

function verifica_toque(_id_obj = noone){
	var _max_devices = 4;

	for (var _i = 0; _i < _max_devices; _i++){
        var _touch_x = device_mouse_x_to_gui(_i);
        var _touch_y = device_mouse_y_to_gui(_i);

	   if (position_meeting(_touch_x, _touch_y, _id_obj)) 
		return true;
	}
	
	return false;
}