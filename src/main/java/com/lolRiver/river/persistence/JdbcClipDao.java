package com.lolRiver.river.persistence;

import com.lolRiver.river.models.Champion;
import com.lolRiver.river.models.Clip;
import com.lolRiver.river.models.Elo;
import com.lolRiver.river.models.Role;
import com.lolRiver.river.persistence.interfaces.ClipDao;
import com.lolRiver.river.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
/**
 * @author mxia (mxia@lolRiver.com)
 *         9/30/13
 */

@Repository
public class JdbcClipDao implements ClipDao {
    private static final Logger LOGGER = Logger.getLogger(JdbcClipDao.class);

    private static String GET_TOTAL_COUNT_SQL = "SELECT count(*) FROM clips";

    private static String GET_GIVEN_ID_SQL = "SELECT * FROM clips WHERE id = :id";

    private static String GET_CLIPS_HEAD_SQL = "SELECT * FROM clips";

    private static String INSERT_SQL = "INSERT INTO clips (video_id, game_id, streamer_name, start_time, end_time, length, views, url, champion_played, role_played, champion_faced, lane_partner_champion, enemy_lane_partner_champion, elo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private NamedParameterJdbcTemplate namedParamJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    private ClipRowMapper rowMapper = new ClipRowMapper();

    @Autowired
    public void init(final DataSource dataSource) {
        namedParamJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // helper method to handle results with num rows != 1
    private Clip queryForClip(final String sql, final Clip ap,
                              final RowMapper<Clip> rowMapper) {
        try {
            return namedParamJdbcTemplate.queryForObject(sql, new BeanPropertySqlParameterSource(ap), rowMapper);
        } catch (final EmptyResultDataAccessException e) {
            // not an error when no rows were found
            return null;
        }
    }

    @Override
    public int getNumTotalClips() {
        return jdbcTemplate.queryForInt(GET_TOTAL_COUNT_SQL);
    }

    @Override
    public Clip getClipFromId(int id) {
        final Clip queryObject = new Clip();
        queryObject.setId(id);

        return queryForClip(GET_GIVEN_ID_SQL, queryObject, rowMapper);
    }

    @Override
    public List<Clip> getClips(int offset, int size, String orderBy, boolean descending) {
        String query;
        if (descending) {
            query = GET_CLIPS_HEAD_SQL + " ORDER BY " + orderBy + " DESC LIMIT ?,?";
        } else {
            query = GET_CLIPS_HEAD_SQL + " ORDER BY " + orderBy + " ASC LIMIT ?,?";
        }
        final Object[] params = new Object[]{offset, size};
        return jdbcTemplate.query(query, params, rowMapper);
    }

    @Override
    public Clip insertClip(final Clip clip) {
        LOGGER.debug("Creating Clip with values: " + clip + " using query: " + INSERT_SQL);

        if (clip == null) {
            return null;
        }

        final KeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                    final PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, clip.getVideoId());
                    ps.setInt(2, clip.getGameId());
                    ps.setString(3, clip.getStreamerName());
                    ps.setTimestamp(4, clip.getStartTime());
                    ps.setTimestamp(5, clip.getEndTime());
                    ps.setInt(6, clip.getLength());
                    ps.setInt(7, clip.getViews());
                    ps.setString(8, clip.getUrl());

                    Champion championPlayed = clip.getChampionPlayed();
                    if (championPlayed == null) {
                        LOGGER.error("trying to insert clip with empty field championPlayed: " + clip);
                    }
                    ps.setString(9, championPlayed == null ? "" : championPlayed.getName().name());

                    Role rolePlayed = clip.getRolePlayed();
                    if (rolePlayed == null) {
                        LOGGER.error("trying to insert clip with empty field rolePlayed: " + clip);
                    }
                    ps.setString(10, rolePlayed == null ? "" : rolePlayed.getName().name());

                    Champion championFaced = clip.getChampionFaced();
                    if (championFaced == null) {
                        LOGGER.error("trying to insert clip with empty field championFaced: " + clip);
                    }
                    ps.setString(11, championFaced == null ? "" : championFaced.getName().name());

                    Champion lanePartnerChampion = clip.getLanePartnerChampion();
                    ps.setString(12, lanePartnerChampion == null ? "" : lanePartnerChampion.getName().name());

                    Champion enemyLanePartnerChampion = clip.getEnemyLanePartnerChampion();
                    ps.setString(13, enemyLanePartnerChampion == null ? "" : enemyLanePartnerChampion.getName().name());

                    Elo elo = clip.getElo();
                    if (elo == null) {
                        LOGGER.error("trying to insert clip with empty field elo: " + clip);
                    }
                    ps.setString(14, elo == null ? "" : elo.getName().name());
                    return ps;
                }
            }, holder);
        } catch (DuplicateKeyException e) {
            // ignore
            return null;
        }
        return getClipFromId(holder.getKey().intValue());
    }

    // Extract Clip results from a JDBC result set
    public final class ClipRowMapper implements RowMapper<Clip> {
        @Override
        public Clip mapRow(final ResultSet resultSet, final int i) throws SQLException {
            final int id = resultSet.getInt(Clip.ID_STRING);
            final int videoId = resultSet.getInt(Clip.VIDEO_ID_STRING);
            final int gameId = resultSet.getInt(Clip.GAME_ID_STRING);
            final String streamerName = resultSet.getString(Clip.STREAMER_NAME_STRING);
            final Timestamp startTime = resultSet.getTimestamp(Clip.START_TIME_STRING);
            final Timestamp endTime = resultSet.getTimestamp(Clip.END_TIME_STRING);
            final int length = resultSet.getInt(Clip.LENGTH_STRING);
            final int views = resultSet.getInt(Clip.VIEWS_STRING);
            final String url = resultSet.getString(Clip.URL_STRING);
            final String championPlayed = resultSet.getString(Clip.CHAMPION_PLAYED_STRING);
            final String rolePlayed = resultSet.getString(Clip.ROLE_PLAYED_STRING);
            final String championFaced = resultSet.getString(Clip.CHAMPION_FACED_STRING);
            final String lanePartnerChampion = resultSet.getString(Clip.LANE_PARTNER_CHAMPION_STRING);
            final String enemyLanePartnerChampion = resultSet.getString(Clip.ENEMY_LANE_PARTNER_CHAMPION_STRING);
            final String elo = resultSet.getString(Clip.ELO_STRING);

            // non-db variables
            final String timeSinceNowMessage = DateUtil.timeSinceNowMessage(startTime);

            return new Clip()
                   .setId(id)
                   .setVideoId(videoId)
                   .setGameId(gameId)
                   .setStreamerName(streamerName)
                   .setStartTime(startTime)
                   .setEndTime(endTime)
                   .setLength(length)
                   .setViews(views)
                   .setUrl(url)
                   .setChampionPlayed(Champion.fromString(championPlayed))
                   .setRolePlayed(Role.fromString(rolePlayed))
                   .setChampionFaced(Champion.fromString(championFaced))
                   .setLanePartnerChampion(Champion.fromString(lanePartnerChampion))
                   .setEnemyLanePartnerChampion(Champion.fromString(enemyLanePartnerChampion))
                   .setElo(Elo.fromString(elo))
                   .setTimeSinceNowMessage(timeSinceNowMessage);
        }
    }
}
