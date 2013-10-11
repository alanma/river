DROP TABLE IF EXISTS `elo`;
ALTER TABLE `lol_users` CHANGE `id` `id` BIGINT(20) NOT NULL;

CREATE TABLE IF NOT EXISTS `lol_users` (
  `id`            BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(127),
  `region`        VARCHAR(31),
  `streamer_name` VARCHAR(127),

  PRIMARY KEY (`id`),
  FOREIGN KEY (`streamer_name`) REFERENCES `streamers` (`name`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COLLATE =utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `elo` (
  `id`      BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20),
  `elo`     VARCHAR(15),
  `time`    TIMESTAMP DEFAULT 0,

  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `lol_users` (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COLLATE =utf8_unicode_ci;