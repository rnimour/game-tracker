--     val id: Long? = null,
--     var name: String,
--     var playStatus: playStatus = NOT_STARTED,
--     var releaseYear: Int,
--     var developer: String,
--     var series: String? = null,
--     var genre: String? = null,

insert into game (id,
                  name,
                  play_status,
                  release_year,
                  developer,
                  series,
                  genre)
values (1,
        'The Legend of Zelda: Breath of the Wild',
        'COMPLETED',
        2017,
        'Nintendo',
        'The Legend of Zelda',
        'Action-adventure');

insert into game (id,
                  name,
                  play_status,
                  release_year,
                  developer,
                  series,
                  genre)
values (2,
        'The Legend of Zelda: Tears of the Kingdom',
        'PLAYING',
        2022,
        'Nintendo',
        'The Legend of Zelda',
        'Action-adventure');

insert into game (id,
                  name,
                  play_status,
                  release_year,
                  developer,
                  series,
                  genre)
values (3,
        'Factorio',
        'PLAYING',
        2016,
        'Wube Software',
        null,
        'Simulation');

insert into game (id,
                  name,
                  play_status,
                  release_year,
                  developer,
                  series,
                  genre)
values (4,
        'Tunic',
        'COMPLETED',
        2022,
        'Isometricorp game',
        null,
        'Action-adventure');

insert into player (id,
                    name,
                    favorite_game_id)
values (-1, -- -1 because I don't want to create a separate sequence for player
        'ruud',
        3 -- Factorio
       );

insert into player_games (player_id, game_id) values (-1, 3);
