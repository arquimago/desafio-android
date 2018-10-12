package br.com.popcode.starwarswiki.helpers

import android.arch.persistence.room.*
import br.com.popcode.starwarswiki.models.Character

@Dao
interface CharacterDao{

    @Query("SELECT * FROM character")
    fun getAll() : MutableList<Character>

    @Query("SELECT * FROM character WHERE name LIKE :first")
    fun findByName(first: String) : Character?

    @Query("SELECT * FROM character WHERE name LIKE :first")
    fun searchByName(first: String) : MutableList<Character>

    @Query("UPDATE character SET favorite=:fav WHERE name=:charName")
    fun favChar(fav: Boolean, charName: String)

    @Query("SELECT * FROM character WHERE favorite=:isFav")
    fun getFavs(isFav: Boolean) : MutableList<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg char: Character)

    @Delete
    fun delete(char: Character)

}