--Etapa 1 do projeto
--Projeto realizado Por:
--Alexandre Costa 22007578
--Mário Silva 22007457

--1. Os ficheiros foram carregados manualmente através do DBVisualizer.

--2. Criação indices
 alter table load_song  add index id_musica(musica_id);
 alter table load_song_artists add index id_musica(musica_id);
 alter table load_song_detail add index id_musica(musica_id);
 
--3. Limpar os dados carregados:
 --3.1.Retirar espaços no inicio e no fim;
 UPDATE load_song set titulo = TRIM(titulo);
 UPDATE load_song set musica_id = TRIM(musica_id);
 UPDATE load_song set ano = TRIM(ano);
 
  
 UPDATE load_song_artists set artists = TRIM(artists);
 UPDATE load_song_artists set musica_id = TRIM( musica_id );
 UPDATE load_song_detail set musica_id = TRIM( musica_id ); 
 
 --3.2.Retirar “ no inicio e no fim;
 
 UPDATE load_song set titulo = REPLACE(titulo,'"','');
  
 UPDATE load_song_artists set artists = REPLACE(artists,'"','');
 UPDATE load_song_artists set artists = REPLACE(artists,'\'','');
 
 
 --3.3.Retirar [ no inicio e ] no fim;
 
 UPDATE load_song set titulo = REPLACE(titulo,'[','');
 UPDATE load_song set titulo = REPLACE(titulo,']','');
 UPDATE load_song_artists set artists = REPLACE(artists,'[','');
 UPDATE load_song_artists set artists = REPLACE(artists,']','');

 
--3.4.Determine quantos artistas existem dentro de cada linha de load_artists. Ordene porordem decrescente do contador de artistas;
SELECT artists, 
LENGTH(artists) - LENGTH(REPLACE(artists,',',''))+1 AS 'Contador Artistas'
FROM load_song_artists
ORDER BY LENGTH(artists) - LENGTH(REPLACE(artists,',',''))+1  DESC;

--4
--4.1-Verificar se existem dados repetidos em musica_id nas 3 tabelas de load;
select musica_id, count(musica_id) as 'nr vezes que aparece repetido'
from load_song
group by musica_id
having count(musica_id) > 1;

select musica_id, count(musica_id) as 'nr vezes que aparece repetido'
from load_song_detail
group by musica_id
having count(musica_id) > 1;


select musica_id, count(musica_id) as 'nr vezes que aparece repetido'
from load_song_artists
group by musica_id
having count(musica_id) > 1;

--4.2--Eliminar dados repetidos nas 3 tabelas de load.

 alter table load_song add id int PRIMARY KEY auto_increment;
 alter table load_song_detail add id int PRIMARY KEY auto_increment;
 alter table load_song_artists add id int PRIMARY KEY auto_increment;
  
select musica_id
        from load_song_artists
        GROUP BY musica_id
        HAVING COUNT(musica_id) > 1;

DELETE artist1 FROM load_song artist1 
JOIN load_song artist2 
WHERE artist1.musica_id = artist2.musica_id AND artist1.id > artist2.id;

DELETE artist1 FROM load_song_artists artist1 
JOIN load_song_artists artist2 
WHERE artist1.musica_id = artist2.musica_id AND artist1.id > artist2.id;

DELETE artist1 FROM load_song_detail artist1 
JOIN load_song_detail artist2 
WHERE artist1.musica_id = artist2.musica_id AND artist1.id > artist2.id;
  


  
 
 
 