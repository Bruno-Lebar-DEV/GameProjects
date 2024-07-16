/// @description Inserir descrição aqui
// Você pode escrever seu código neste editor

// Inherit the parent event
event_inherited();

// Area de Visao 
draw_circle(x,y,area_visao,true);

// Mostra HitBox do Ataque
if (is_struct(atacar)){
	atacar.desenha_area();
}