-- Etapa 2 do projeto
-- Projeto realizado Por:
-- Alexandre Costa 22007578
-- Mário Silva 22007457


--5
-- 5.1)  Detectar musica_id de load_song_detail que não existem em load_song e eliminar
DELETE f
FROM load_song_detail f
WHERE NOT EXISTS (SELECT null FROM load_song a WHERE f.musica_id = a.musica_id);


-- 5.2) Juntar musica com detalhe de musica usando o musica_id que já não tem valores repetidos. Só devem aparecer dados que existam nas duas tabelas

select load_song.musica_id, load_song.titulo, load_song.ano, load_song_detail.duracao, load_song_detail.letra_explicita, load_song_detail.popularidade, load_song_detail.grau_dancabilidade, load_song_detail.grau_vivacidade, load_song_detail.volume_som_medio
from load_song, load_song_detail
where load_song.musica_id=load_song_detail.musica_id;

-- 5.3) Em load_song_artists adicione a coluna artista_id e atribua valores com incrementos de 100 unidades.
update load_song_artists s, (select @row_count:=0) as init
set artista_id = (@row_count:=@row_count+100);


-- 5.4) Encontrar um mecanismo que permita listar o primeiro artista, ou o segundo, ou o nésimo,juntamente com com musica_id e artista_id inserido no passo anterior.  



CREATE TABLE IF NOT EXISTS SeparatedArtists
        select length(replace(str, ',', ', ')) - length(str) 
        , substring_index(substring_index(str,',',1),',',-1) as Loc1
        , substring_index(substring_index(str,',',2),',',-1) as Loc2
        , substring_index(substring_index(str,',',3),',',-1) as Loc3
        , substring_index(substring_index(str,',',4),',',-1) as Loc4
        , substring_index(substring_index(str,',',5),',',-1) as Loc5
        , substring_index(substring_index(str,',',6),',',-1) as Loc6
        , substring_index(substring_index(str,',',7),',',-1) as Loc7
        , substring_index(substring_index(str,',',8),',',-1) as Loc8
        , substring_index(substring_index(str,',',9),',',-1) as Loc9
        , substring_index(substring_index(str,',',10),',',-1) as Loc10
        , substring_index(substring_index(str,',',11),',',-1) as Loc11
        , substring_index(substring_index(str,',',12),',',-1) as Loc12
        , substring_index(substring_index(str,',',13),',',-1) as Loc13
        , substring_index(substring_index(str,',',14),',',-1) as Loc14
        , substring_index(substring_index(str,',',15),',',-1) as Loc15
        , substring_index(substring_index(str,',',16),',',-1) as Loc16
        , substring_index(substring_index(str,',',17),',',-1) as Loc17
        , substring_index(substring_index(str,',',18),',',-1) as Loc18
        , substring_index(substring_index(str,',',19),',',-1) as Loc19
        , substring_index(substring_index(str,',',20),',',-1) as Loc20
        , substring_index(substring_index(str,',',21),',',-1) as Loc21
        , substring_index(substring_index(str,',',22),',',-1) as Loc22
        , substring_index(substring_index(str,',',23),',',-1) as Loc23
        , substring_index(substring_index(str,',',24),',',-1) as Loc24
        , substring_index(substring_index(str,',',25),',',-1) as Loc25
        , substring_index(substring_index(str,',',26),',',-1) as Loc26
        , substring_index(substring_index(str,',',27),',',-1) as Loc27
        , substring_index(substring_index(str,',',28),',',-1) as Loc28
        , artista_id as artista_id
        , musica_id as musica_id
        
        from (
            select replace(concat(artists,','),',,',',') as str
            ,artista_id as artista_id
            ,musica_id as musica_id
            from load_song_artists
        ) normalized


create table temporaria(
        artists_id int,
        artists varchar(2000),
        musica_id varchar(2000)
);

INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 28, 
       Loc28, 
       musica_id
FROM SeparatedArtists
WHERE Loc28 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 27, 
       Loc27, 
       musica_id
FROM SeparatedArtists
WHERE Loc27 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 26, 
       Loc26, 
       musica_id
FROM SeparatedArtists
WHERE Loc26 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 25, 
       Loc25, 
       musica_id
FROM SeparatedArtists
WHERE Loc25 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 24, 
       Loc24, 
       musica_id
FROM SeparatedArtists
WHERE Loc24 != ''
 

INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 23, 
       Loc23, 
       musica_id
FROM SeparatedArtists
WHERE Loc23 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 22, 
       Loc22, 
       musica_id
FROM SeparatedArtists
WHERE Loc22 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 21, 
       Loc21, 
       musica_id
FROM SeparatedArtists
WHERE Loc21 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 20, 
       Loc20, 
       musica_id
FROM SeparatedArtists
WHERE Loc20 != ''

        
INSERT INTO temporaria( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 19, 
       Loc19, 
       musica_id
FROM SeparatedArtists
WHERE Loc19 != ''

        
INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 18, 
       Loc18, 
       musica_id
FROM SeparatedArtists
WHERE Loc18 != ''

        
INSERT INTO temporaria( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 17, 
       Loc17, 
       musica_id
FROM SeparatedArtists
WHERE Loc17 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 16, 
       Loc16, 
       musica_id
FROM SeparatedArtists
WHERE Loc16 != ''


INSERT INTO temporaria( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 15, 
       Loc15, 
       musica_id
FROM SeparatedArtists
WHERE Loc15 != ''


INSERT INTO temporaria( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 14, 
       Loc14, 
       musica_id
FROM SeparatedArtists
WHERE Loc14 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 13, 
       Loc13, 
       musica_id
FROM SeparatedArtists
WHERE Loc13 != ''


INSERT INTO temporaria( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 12, 
       Loc12, 
       musica_id
FROM SeparatedArtists
WHERE Loc12 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 11, 
       Loc11, 
       musica_id
FROM SeparatedArtists
WHERE Loc11 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 10, 
       Loc10, 
       musica_id
FROM SeparatedArtists
WHERE Loc10 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 9, 
       Loc9, 
       musica_id
FROM SeparatedArtists
WHERE Loc9 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 8, 
       Loc8, 
       musica_id
FROM SeparatedArtists
WHERE Loc8 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 7, 
       Loc7, 
       musica_id
FROM SeparatedArtists
WHERE Loc7 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 6, 
       Loc6, 
       musica_id
FROM SeparatedArtists
WHERE Loc6 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 5, 
       Loc5, 
       musica_id
FROM SeparatedArtists
WHERE Loc5 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 4, 
       Loc4, 
       musica_id
FROM SeparatedArtists
WHERE Loc4 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 3, 
       Loc3, 
       musica_id
FROM SeparatedArtists
WHERE Loc3 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 2, 
       Loc2, 
       musica_id
FROM SeparatedArtists
WHERE Loc2 != ''


INSERT INTO temporaria ( 
      artists_id, 
      artists, 
      musica_id
      ) 
SELECT artista_id + 1, 
       Loc1, 
       musica_id
FROM SeparatedArtists
WHERE Loc1 != ''

ALTER TABLE temporaria ORDER BY artists_id ASC;



-- 5.5) Escrever query que mostra o artista_id com musica_id.

SELECT temporaria.artists_id, temporaria.musica_id FROM temporaria;



-- 6)  Criar todas as tabelas do modelo incluindo restrições PK, UK, NOT NULL, CHK e FK.



CREATE TABLE album(
        id int(10) unsigned,
        nome varchar(200),
        data_lancamento int(4),
        PRIMARY KEY (id)
);

CREATE TABLE musica(
        id varchar(22),
        titulo varchar(200),
        ano int(4),
        duracao int(10),
        letra_explicita tinyint(4),
        popularidade int(3),
        grau_dancabilidade double,
        grau_vivacidade double,
        volume_som_medio double,
        PRIMARY KEY (id)
);

CREATE TABLE musica_relacionada(
        musica_id1 varchar(22),
        musica_id2 varchar(22),
        descricao varchar(10000),
        FOREIGN KEY (musica_id1) REFERENCES musica(id),
        FOREIGN KEY (musica_id2) REFERENCES musica(id)
);
        

CREATE TABLE faixa(
        musica_id varchar(22),
        album_id int(10) unsigned,
        posicao int(3),
        descricao varchar(10000),
        FOREIGN KEY (musica_id) REFERENCES musica(id),
        FOREIGN KEY (album_id) REFERENCES album(id)
);

CREATE TABLE rotulo(
        id varchar(6),
        descricao varchar(200),
        PRIMARY KEY (id)
);

CREATE TABLE rotulo_musica(
        musica_id varchar(22),
        rotulo_id varchar(6),
        FOREIGN KEY (musica_id) REFERENCES musica(id),
        FOREIGN KEY (rotulo_id) REFERENCES rotulo(id)
);

CREATE TABLE artista(
        id int(10) unsigned,
        nome_artistico varchar(800),
        nome_real varchar(200),
        data_nascimento int(11),
        biografia varchar(10000),
        PRIMARY KEY (id)
);

CREATE TABLE rotulo_artista(
        artista_id int(10) unsigned,
        rotulo_id varchar(6),
        FOREIGN KEY (artista_id) REFERENCES artista(id),
        FOREIGN KEY (rotulo_id) REFERENCES rotulo(id)
);

CREATE TABLE tipo_contribuicao(
        id varchar(6),
        descricao varchar(100),
        PRIMARY KEY (id)
);

CREATE TABLE contribuicao_artista(
        artista_id int(10) unsigned,
        musica_id varchar(22),
        tipo_contribuicao_id varchar(6),
        descricao varchar(10000),
        FOREIGN KEY (artista_id) REFERENCES artista(id),
        FOREIGN KEY (musica_id) REFERENCES musica(id),
        FOREIGN KEY (tipo_contribuicao_id) REFERENCES tipo_contribuicao(id)
);


-- 7 -- Inserir dados na tabela musica executando o query que junta load_song com load_song_detail.

INSERT INTO musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio)
SELECT x2.musica_id,x2.titulo,x2.ano,x1.duracao,x1.letra_explicita,x1.popularidade,x1.grau_dancabilidade,x1.grau_vivacidade,x1.volume_som_medio FROM load_song_detail x1
JOIN load_song x2 ON x1.musica_id = x2.musica_id



-- 8 -- Inserir dados na tabela artista executando o query que seleciona o primeiro artista e o respetivo numero

INSERT INTO artista (id,nome_artistico)
SELECT artists_id, artists
FROM temporaria

UPDATE artista
SET nome_real="", data_nascimento=0, biografia="";


-- 9 --- Inserir dados nas restantes tabelas excepto contribuição artista.

INSERT INTO album (id,nome,data_lancamento) values (1,"Album1", 1999);
INSERT INTO album (id,nome,data_lancamento) values (2,"Album2", 1998);
INSERT INTO album (id,nome,data_lancamento) values (3,"Album3", 1997);
INSERT INTO album (id,nome,data_lancamento) values (4,"Album4", 1996);
INSERT INTO album (id,nome,data_lancamento) values (5,"Album5", 1995);

insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("y1","yo1",1999,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("y2","yo2",1998,3,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("y3","yo3",1997,2,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("y4","yo4",1996,1,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("y5","yo5",1999,6,1,4,1.0,1.0,1.0);

insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("z1","zo1",1999,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("z2","zo2",1999,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("z3","zo3",1999,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("z4","zo4",1999,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("z5","zo4",1999,5,1,4,1.0,1.0,1.0);

insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("t1","to1",1999,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("t2","to2",1999,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("t3","to3",1999,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("t4","to4",1999,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("t5","to5",1999,5,1,4,1.0,1.0,1.0);

insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("q1","qo1",1989,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("q2","qo2",1988,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("q3","qo3",1987,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("q4","qo4",1986,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("q5","qo5",1985,5,1,4,1.0,1.0,1.0);


insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("w1","wo1",1979,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("w2","wo2",1978,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("w3","wo3",1977,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("w4","wo4",1976,5,1,4,1.0,1.0,1.0);
insert into musica (id,titulo,ano,duracao,letra_explicita,popularidade,grau_dancabilidade,grau_vivacidade,volume_som_medio) values("w5","wo5",1975,5,1,4,1.0,1.0,1.0);

INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("y1",1,1,"yo1");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("y2",1,2,"yo2");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("y3",1,3,"yo3");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("y4",1,4,"yo4");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("y5",1,5,"yo5");

INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("z1",2,1,"zo1");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("z2",2,2,"zo2");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("z3",2,3,"zo3");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("z4",2,4,"zo4");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("z5",2,5,"zo5");

INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("t1",3,1,"to1");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("t2",3,2,"to2");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("t3",3,3,"to3");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("t4",3,4,"to4");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("t5",3,5,"to5");

INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("q1",4,1,"qo1");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("q2",4,2,"qo2");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("q3",4,3,"qo3");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("q4",4,4,"qo4");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("q5",4,5,"qo5");

INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("w1",5,1,"wo1");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("w2",5,2,"wo2");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("w3",5,3,"wo3");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("w4",5,4,"wo4");
INSERT INTO faixa (musica_id,album_id,posicao,descricao) values ("w5",5,5,"wo5");














