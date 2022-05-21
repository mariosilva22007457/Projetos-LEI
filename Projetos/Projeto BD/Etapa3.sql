--Etapa 3 do projeto
--Projeto realizado Por:
--Alexandre Costa 22007578
--Mário Silva 22007457



-- 10 -- Detecte nomes de artistas repetidos na tabela artista e elimine.



alter table artista add index nome_artistico1(nome_artistico);

DELETE artista1 FROM artista artista1 
INNER JOIN artista artista2 
WHERE artista1.nome_artistico = artista2.nome_artistico and artista1.id > artista2.id;
drop index nome_artistico1 on artista;



-- 11 -- Desenvolva um query que permita detectar se todos os valores de musica_id em load_song_artists existem na tabela musica.  


DELETE s
FROM load_song_artists s
WHERE NOT EXISTS (SELECT * FROM musica m WHERE s.musica_id = m.id);

-- 12 -- Desenvolva um query que junta as tabelas “load_song_artists” e “artista” usando o nome do artista como critério de junção.
select * from load_song_artists artista1
inner join artista artista2 on artista1. artists = artista2.nome_artistico order by artista2.id asc


-- 13 -- Insira dados em contribuição de artista usando o query anterior.
/* insert into contribuicao_artista(artista_id,musica_id,)

select temporaria.artists_id, temporaria.musica_id from temporaria
where temporaria. */


-- 14 -- Produza as seguintes pesquisas, que constam do enunciado de AED:
-- 14.1 --  Recebendo um ano, contar o número de canções editadas nesse ano;


@delimiter %%%;
CREATE FUNCTION getSongsFromYear(yearChoice int(10))
        RETURNS int(10) deterministic

BEGIN
        DECLARE songsOfYear int(10);

        SELECT COUNT(ano) INTO songsOfYear 
        FROM musica
        WHERE ano= yearChoice;

        RETURN songsOfYear ;
END;%%%
@delimiter ; 
%%%

SELECT getSongsFromYear(1999); 
-- Este select permite escolhendo um valor para um ano,obter o numero de canções que foram editadas nesse ano como é pedido na query.


-- 14.2 --  Contar o número de musicas editadas por cada ano;

SELECT count(ano), ano FROM musica GROUP BY ano HAVING COUNT(ano) > 0 ORDER BY count(ano) ASC;

-- 14.3 -- Contar o número de músicas com o mesmo titulo;

SELECT count(titulo), titulo FROM musica GROUP BY titulo HAVING COUNT(titulo) > 0 ORDER BY count(titulo) ASC; 
 
-- 14.4 --  Adicionar uma coluna na tabela musica para guardar a duração em Minuto:segundo

ALTER TABLE musica ADD (duracaoCalc VARCHAR(100) null)

@delimiter %%%;
CREATE  Procedure calcMusicDuration()
 NOT DETERMINISTIC
    READS SQL DATA
    begin
  UPDATE musica
        SET duracaoCalc = IF(ROUND(duracao / 1000 / 60) > 0 , CONCAT(" ",ROUND(duracao / 1000 / 60) , ":",ROUND((duracao / 1000) % 60)) , Concat(" 0 : ",ROUND((duracao / 1000) % 60)));

end;%%%
@delimiter; 

 call calcMusicDuration()
 
 
-- 14.5 -- Associar 20 rótulos a pelo menos 10 artistas diferentes, pelo alguns artistas têm
-- mais que um rótulo. Fazer uma pesquise que mostre as associações que foram feitas,
-- indicando o nome do artista e os rótulos que lhe foram associados, apresentando um
-- rótulo por linha. Usando PL/SQL ou uma função especial pode alterar o resultado
-- para apresentar todos os rótulos de um artista na mesma linha; 

@delimiter %%%;
CREATE Procedure insertUmRotulo()
 NOT DETERMINISTIC
    READS SQL DATA
BEGIN 
        DECLARE x int(10);
        set x=1;
        while x<=20 do

        insert into rotulo values(x,"");
        set x=x+1;

       end while;
END;
%%%
@delimiter ; 
%%%

call insertUmRotulo()

ALTER TABLE rotulo_artista
ADD COLUMN nome_artista VARCHAR(100)

INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (101,1,'The Jacksons');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (101,2,'The Jacksons');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (201,3,'Beastie Boys');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (201,4,'Beastie Boys');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (202,5,' The Prunes');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (202,6,' The Prunes');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (301,7,'Soda Stereo');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (301,8,'Soda Stereo');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (401,9,'Yusuf / Cat Stevens');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (401,10,'Yusuf / Cat Stevens');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (501,11,'Exquisite Classic');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (501,12,'Exquisite Classic');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (502,13,'Felix Mendelssohn');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (502,14,'Felix Mendelssohn');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (601,15,'Louis Prima');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (601,16,'Louis Prima');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (602,17,'Keely Smith');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (602,18,'Keely Smith');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (701,19,'Jo Dee Messina');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (701,20,'Jo Dee Messina');
INSERT INTO rotulo_artista (artista_id,rotulo_id,nome_artista) values (701,1,'Jo Dee Messina');

@delimiter %%%;
CREATE Procedure showAssociacaoDeRotulos()
        NOT DETERMINISTIC
        READS SQL DATA
begin
        SELECT rotulo_id, GROUP_CONCAT(nome_artista ORDER BY nome_artista ASC SEPARATOR ', ')
        from rotulo_artista 
        GROUP BY rotulo_id;
end;%%%
@delimiter;
%%%

call showAssociacaoDeRotulos()



-- 14.6 --  Escreva um query que conte o número de vezes que cada rótulo é usado,
-- mostrando o resultado por ordem decrescente;
INSERT INTO rotulo_musica (musica_id,rotulo_id) values ('000G1xMMuwxNHmwVsBdtj1',1);

SELECT r.rotulo_id, COUNT(r.rotulo_id+e.rotulo_id) AS 'Numero de vezes que cada rotulo é utilizado'
FROM rotulo_artista r inner join rotulo_musica e
GROUP BY rotulo_id 
ORDER BY rotulo_id DESC;


-- 14.7 -- Dado um inteiro N, e os anos X e Y, escrever um query que retorna as N músicas
-- mais dançáveis entre os anos X e Y (inclusive);


@delimiter %%%;
CREATE PROCEDURE mmostDancableBetweenYears(number int(10) ,firstYear int(10) ,secondYear int(10))
    NOT DETERMINISTIC
    READS SQL DATA
BEGIN
        SELECT * FROM musica 
        WHERE (ano>= firstYear and ano<=  secondYear)
        ORDER BY grau_dancabilidade DESC LIMIT number;

END;%%%
@delimiter ; 
%%%

-- Funcionamento:
--Numero de musicas que queremos obter, o primeiro ano do intervalo, ano final do intervalo de escolha
call mmostDancableBetweenYears(5000,1960,1970);



-- 14.8 --   Retorna todos os artistas que num dado período apenas lançaram uma música,
-- ordenados alfabeticamente pelo nome do artista.
/*
@delimiter %%%;
CREATE PROCEDURE artistsWithMostSongs(numero int(10), firstYear int(10), finalYear int(10) ) 
    NOT DETERMINISTIC
    READS SQL DATA
BEGIN
        SELECT nome_artistico , COUNT(c.artista_id) 
        FROM contribuicao_artista c inner join artista a where c.artista_id = a.id
        GROUP BY c.artista_id
        HAVING COUNT(c.artista_id) = 1
        ORDER BY nome_artistico;

END;%%%
@delimiter ; 
%%%

*/



-- 14.9 --  Considerando dois números inteiros A e B, retornar uma lista com os N artistas que
-- mais temas musicais têm, considerando apenas aqueles que têm entre A e B temas
--(ambos inclusive).

/*
@delimiter %%%;
CREATE Procedure mostSongsBetweenYears(number INT,firstNumber INT,secondNumber INT)
BEGIN
        SELECT musica_id, COUNT(musica_id) 
        FROM contribuicao_artista c inner join artista a where c.artista_id = a.id
        GROUP BY c.artista_id
        HAVING COUNT(c.artista_id) >= firstNumber and COUNT(c.artista_id) =< secondNumber limit number;


END;%%%
@delimiter ; 
%%%

call mostSongsBetweenYears(10,1,5)
*/



-- 14.10 --  Devolver os nomes dos rótulos que existam na base de dados associadas a
-- Artistas que tenham pelo menos uma música entre os anos A e B (ambos inclusive). A
-- lista deve estar ordenada por ordem decrescente do número de ocorrências de
-- musicas;




-- 14.11 
/* Produza um query que devolve o nome do artista e o ano em que publicou musica.
O resultado deve ser ordenado por artista e ano. Adicione um filtro que mostra
apenas resultados entre os anos A e B. Usando PL/SQL deve verificar se, para cada
artista, há publicações em todos os anos do intervalo, escrevendo esses artistas
numa tabela temporária. Juntando a tabela temporária com os artistas e as musicas,
calculando uma média da popularidade do artista tendo em conta a popularidade das
suas musicas. Mostre os artistas que publicaram consecutivamente entre os anos A e
B ordenando o resultado de forma decrescente de popularidade média.
 */


-- 14.12 -- 
/* Faça um query que conta o número de musicas de cada artista e guarde numa
tabela temporária os nomes dos artistas que têm mais de M temas (por exemplo 10).
Criar uma tabela que tem duas colunas: palavra e contador. Usando PL/SQL vai
contar o número de ocorrências de palavras nos nomes dos artistas que estão na
tabela temporária, atualizando o contador de palavras. Apenas devem ser
consideradas as palavras que tenham tamanho superior a L (por exemplo 3). No fim,
produza um query que determina as N palavras que mais vezes ocorrem,
apresentando o resultado por ordem decrescente de ocorrência de palavras.
*/




