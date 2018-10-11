package br.com.popcode.starwarswiki.Helpers

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import br.com.popcode.starwarswiki.Models.Character


@Database(entities = arrayOf(Character::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

}