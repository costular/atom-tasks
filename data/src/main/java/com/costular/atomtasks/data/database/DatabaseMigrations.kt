package com.costular.atomtasks.data.database

import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `_new_tasks` (`id` INTEGER PRIMARY KEY" +
                    " AUTOINCREMENT NOT NULL, `created_at` TEXT NOT NULL, `name` TEXT NOT NULL," +
                    " `date` TEXT NOT NULL, `is_done` INTEGER NOT NULL, `position`" +
                    " INTEGER NOT NULL DEFAULT 0)",
        )
        db.execSQL(
            "INSERT INTO `_new_tasks` (`id`,`created_at`,`name`,`date`," +
                    "`is_done`) SELECT `id`,`created_at`,`name`,`date`,`is_done` FROM `tasks`",
        )
        db.execSQL("DROP TABLE `tasks`")
        db.execSQL("ALTER TABLE `_new_tasks` RENAME TO `tasks`")
        db.execSQL(
            "UPDATE tasks SET position = (SELECT COUNT ( *) FROM tasks " +
                    "AS t WHERE t.ID <= tasks.ID);",
        )
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_tasks_created_at` " +
                    "ON `tasks` (`created_at`)",
        )
        db.execSQL(
            "CREATE UNIQUE INDEX IF NOT EXISTS `index_tasks_position_date` " +
                    "ON `tasks` (`position`, `date`)",
        )
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `_new_reminders` 
            (`reminder_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `time` TEXT NOT NULL,
             `date` TEXT NOT NULL, 
             `task_id` INTEGER NOT NULL)
             """
        )
        db.execSQL(
            """INSERT INTO `_new_reminders` (`reminder_id`,`time`,`date`,`task_id`) 
            SELECT `reminder_id`,`time`,`date`,`task_id` FROM `reminders`"""
        )
        db.execSQL("DROP TABLE reminders")
        db.execSQL("ALTER TABLE `_new_reminders` RENAME TO `reminders`")
    }
}

class Migration6To7Spec : AutoMigrationSpec {
    override fun onPostMigrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP INDEX IF EXISTS `index_tasks_created_at`;")
    }
}
