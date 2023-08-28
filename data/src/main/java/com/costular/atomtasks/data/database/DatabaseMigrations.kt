package com.costular.atomtasks.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `_new_tasks` (`id` INTEGER PRIMARY KEY" +
                " AUTOINCREMENT NOT NULL, `created_at` TEXT NOT NULL, `name` TEXT NOT NULL," +
                " `date` TEXT NOT NULL, `is_done` INTEGER NOT NULL, `position`" +
                " INTEGER NOT NULL DEFAULT 0)",
        )
        database.execSQL(
            "INSERT INTO `_new_tasks` (`id`,`created_at`,`name`,`date`," +
                "`is_done`) SELECT `id`,`created_at`,`name`,`date`,`is_done` FROM `tasks`",
        )
        database.execSQL("DROP TABLE `tasks`")
        database.execSQL("ALTER TABLE `_new_tasks` RENAME TO `tasks`")
        database.execSQL(
            "UPDATE tasks SET position = (SELECT COUNT ( *) FROM tasks " +
                "AS t WHERE t.ID <= tasks.ID);",
        )
        database.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_tasks_created_at` " +
                "ON `tasks` (`created_at`)",
        )
        database.execSQL(
            "CREATE UNIQUE INDEX IF NOT EXISTS `index_tasks_position_date` " +
                "ON `tasks` (`position`, `date`)",
        )
    }
}
