
/*/ ------------------------------------------
-- DEFINIÇÃO DE ATRIBUTOS DA MINHA ENTIDADE --
------------------------------------------ /*/

// ATRIBUTOS DE FISICA
z		= 0;
grav		= 0.2;
face		= 1;

// ATRIBUTOS DE MOVIMENTAÇÃO
max_velx	= 2;
max_vely	= 1;
max_velz	= 4;
velx		= 0;
vely		= 0;
velz		= 0;

// ATRIBUTOS DE VIDA 
max_life	= 100;
life		= 100;


// ATRIBUTOS DE CONTROLE DE ESTADO
estado	= noone;

// DEFINE A HITBOX DO JOGADOR
hitbox	= new HitBox(x, y+z, sprite_width, sprite_height, 1);
atacar	= noone;



/*/ ----------------------------------------
-- DEFINIÇÃO DE METODOS DA MINHA ENTIDADE --
---------------------------------------- /*/

estado_main = function() {
}


// INICIALIZO O MEU ESTADO
estado = estado_main;

