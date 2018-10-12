package br.com.popcode.starwarswiki.helpers

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.TypeConverters
import android.content.Context
import br.com.popcode.starwarswiki.Constants
import br.com.popcode.starwarswiki.models.Character


@Database(entities = [Character::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

}


class DB(context: Context){
    val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DBname)
            .allowMainThreadQueries()
            .build()

    val characterDao = db.characterDao()
}