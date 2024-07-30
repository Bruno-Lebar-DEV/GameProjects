
hovering = verifica_toque(id);

if (hovering && mouse_check_button_pressed(mb_left)) {
	clicked = true;
} 



if (mouse_check_button_released(mb_left)) 
{
	clicked = false;

	if (hovering) 
	{
		//audio_play_sound(snd_button, 1, false);
		active_button();
	}
	
} 